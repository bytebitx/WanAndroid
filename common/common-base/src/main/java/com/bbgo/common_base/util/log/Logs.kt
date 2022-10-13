package com.bbgo.common_base.util.log

import com.bbgo.common_base.BuildConfig
import timber.log.Timber

object Logs {

    private var TAG = "WanAndroid"
    
    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    @JvmStatic
    fun tag(tag: String) {
        TAG = tag
    }

    @JvmStatic
    fun v(message: String?, vararg args: Any?) {
        Timber.tag(TAG).v(message, args)
    }

    @JvmStatic
    fun v(tag: String = TAG, message: String?, vararg args: Any?) {
        Timber.tag(tag).v(message, args)
    }

    @JvmStatic
    fun v(tag: String = TAG, t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(tag).v(t, message, *args)
    }

    @JvmStatic
    fun v(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).v(t, message, *args)
    }

    @JvmStatic
    fun v(t: Throwable?) {
        Timber.tag(TAG).v(t)
    }

    @JvmStatic
    fun v(tag: String = TAG, t: Throwable?) {
        Timber.tag(tag).v(t)
    }

    @JvmStatic
    fun d(message: String?, vararg args: Any?) {
        Timber.tag(TAG).d(message, *args)
    }

    @JvmStatic
    fun d(tag: String = TAG, message: String?, vararg args: Any?) {
        Timber.tag(tag).d(message, *args)
    }

    @JvmStatic
    fun d(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).v(t, message, *args)
    }

    @JvmStatic
    fun d(tag: String = TAG, t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(tag).v(t, message, *args)
    }

    @JvmStatic
    fun d(t: Throwable?) {
        Timber.tag(TAG).d(t)
    }

    @JvmStatic
    fun d(tag: String = TAG, t: Throwable?) {
        Timber.tag(tag).d(t)
    }

    @JvmStatic
    fun i(message: String?, vararg args: Any?) {
        Timber.tag(TAG).i(message, *args)
    }

    @JvmStatic
    fun i(tag: String = TAG, message: String?, vararg args: Any?) {
        Timber.tag(tag).i(message, *args)
    }

    @JvmStatic
    fun i(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).i(t, message, *args)
    }

    @JvmStatic
    fun i(tag: String = TAG, t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(tag).i(t, message, *args)
    }

    @JvmStatic
    fun i(t: Throwable?) {
        Timber.tag(TAG).i(t)
    }

    @JvmStatic
    fun i(tag: String = TAG, t: Throwable?) {
        Timber.tag(tag).i(t)
    }

    @JvmStatic
    fun w(message: String?, vararg args: Any?) {
        Timber.tag(TAG).w(message, *args)
    }

    @JvmStatic
    fun w(tag: String = TAG, message: String?, vararg args: Any?) {
        Timber.tag(tag).w(message, *args)
    }

    @JvmStatic
    fun w(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).w(t, message, *args)
    }

    @JvmStatic
    fun w(tag: String = TAG, t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(tag).w(t, message, *args)
    }

    @JvmStatic
    fun w(t: Throwable?) {
        Timber.tag(TAG).w(t)
    }

    @JvmStatic
    fun w(tag: String = TAG, t: Throwable?) {
        Timber.tag(tag).w(t)
    }

    @JvmStatic
    fun e(message: String?, vararg args: Any?) {
        Timber.tag(TAG).e(message, *args)
    }

    @JvmStatic
    fun e(tag: String = TAG, message: String?, vararg args: Any?) {
        Timber.tag(tag).e(message, *args)
    }

    @JvmStatic
    fun e(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).e(message, *args)
    }

    @JvmStatic
    fun e(tag: String = TAG, t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(tag).e(message, *args)
    }

    @JvmStatic
    fun e(t: Throwable?) {
        Timber.tag(TAG).e(t)
    }

    @JvmStatic
    fun e(tag: String = TAG, t: Throwable?) {
        Timber.tag(tag).e(t)
    }
    
}