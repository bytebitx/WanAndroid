package com.bbgo.common_base.pool

import android.util.Log
import androidx.annotation.NonNull
import com.bbgo.common_base.util.Logs
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/9/24 4:13 下午
 */
class AppThreadFactory : ThreadFactory {
    private val TAG = "AppThreadFactory"
    private val threadNumber = AtomicInteger(1)
    private val group: ThreadGroup
    private val namePrefix: String
    override fun newThread(@NonNull runnable: Runnable?): Thread {
        val threadName = namePrefix + threadNumber.getAndIncrement()
        Logs.i("Thread production, name is [$threadName]")
        val thread = Thread(group, runnable, threadName, 0)
        if (thread.isDaemon) {   //设为非后台线程
            thread.isDaemon = false
        }
        if (thread.priority != Thread.NORM_PRIORITY) { //优先级为normal
            thread.priority = Thread.NORM_PRIORITY
        }

        // 捕获多线程处理中的异常
        thread.uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread, ex ->
            Logs.i("Running task appeared exception! Thread [" + thread.name + "], because [" + ex.message + "]")
        }
        return thread
    }

    companion object {
        private val poolNumber = AtomicInteger(1)
    }

    init {
        val s = System.getSecurityManager()
        group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup
        namePrefix = "App task pool No." + poolNumber.getAndIncrement() + ", thread No."
    }
}