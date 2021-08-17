package com.bbgo.common_base.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.Exception
import java.lang.NullPointerException
import java.util.HashMap

class LiveDataBus private constructor() {

    private val bus: MutableMap<String, BusMutableLiveData<Any>>

    init {
        bus = HashMap()
    }

    private object SingletonHolder {
        val DEFAULT_BUS = LiveDataBus()
    }

    companion object {
        fun get(): LiveDataBus {
            return SingletonHolder.DEFAULT_BUS
        }
    }

    fun <T> with(key: String, type: Class<T>?): MutableLiveData<T> {
        if (!bus.containsKey(key)) {
            val mutableLiveData = BusMutableLiveData<Any>()
            bus[key] = mutableLiveData
            return mutableLiveData as MutableLiveData<T>
        }
        return bus[key] as MutableLiveData<T>
    }

    fun with(key: String): MutableLiveData<Any> {
        return with(key, Any::class.java)
    }

    private class ObserverWrapper<T>(val observer: Observer<in T>) : Observer<T> {
        override fun onChanged(t: T) {
            if (isCallOnObserve) {
                return
            }
            observer.onChanged(t)
        }

        private val isCallOnObserve: Boolean
            get() {
                val stackTrace = Thread.currentThread().stackTrace
                if (stackTrace.isNotEmpty()) {
                    for (element in stackTrace) {
                        if ("androidx.lifecycle.LiveData" == element.className && "observeForever" == element.methodName) {
                            return true
                        }
                    }
                }
                return false
            }

    }

    private class BusMutableLiveData<T> : MutableLiveData<T>() {
        private val observerMap: MutableMap<Observer<in T>, Observer<in T>> = HashMap()
        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            try {
                hook(observer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun observeForever(observer: Observer<in T>) {
            if (!observerMap.containsKey(observer)) {
                observerMap[observer] = ObserverWrapper(observer)
            }
            observerMap[observer]?.let { super.observeForever(it) }
        }

        override fun removeObserver(observer: Observer<in T>) {
            val realObserver = if (observerMap.containsKey(observer)) {
                observerMap.remove(observer) as Observer<in T>
            } else {
                observer
            }
            super.removeObserver(realObserver)
        }

        private fun hook(observer: Observer<in T>) {
            kotlin.runCatching {
                //get wrapper's version
                val classLiveData = LiveData::class.java
                val fieldObservers = classLiveData.getDeclaredField("mObservers")
                fieldObservers.isAccessible = true
                val objectObservers = fieldObservers[this]
                val classObservers: Class<*> = objectObservers.javaClass
                val methodGet = classObservers.getDeclaredMethod("get", Any::class.java)
                methodGet.isAccessible = true
                val objectWrapperEntry = methodGet.invoke(objectObservers, observer)
                var objectWrapper: Any? = null
                if (objectWrapperEntry is Map.Entry<*, *>) {
                    objectWrapper = objectWrapperEntry.value
                }
                if (objectWrapper == null) {
                    throw NullPointerException("Wrapper can not be bull!")
                }
                val classObserverWrapper = objectWrapper.javaClass.superclass
                val fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion")
                fieldLastVersion.isAccessible = true
                //get livedata's version
                val fieldVersion = classLiveData.getDeclaredField("mVersion")
                fieldVersion.isAccessible = true
                val objectVersion = fieldVersion[this]
                //set wrapper's version
                fieldLastVersion[objectWrapper] = objectVersion
            }.onFailure {

            }

        }
    }
}