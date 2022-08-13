package com.bbgo.common_base.util

import com.bbgo.common_base.BuildConfig
import timber.log.Timber

object Logs {

    private const val TAG = "WanAndroid"
    
    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseReportingTree())
        }
    }

    /** Log a verbose message with optional format args. */
    fun v(message: String?, vararg args: Any?) {
        Timber.tag(TAG).v(message, args)
    }

    /** Log a verbose exception and a message with optional format args. */
    fun v(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).v(t, message, *args)
    }

    /** Log a verbose exception. */
    fun v(t: Throwable?) {
        Timber.tag(TAG).v(t)
    }

    /** Log a debug message with optional format args. */
    fun d(message: String?, vararg args: Any?) {
        Timber.tag(TAG).d(message, *args)
    }

    /** Log a debug exception and a message with optional format args. */
    fun d(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).v(t, message, *args)
    }

    /** Log a debug exception. */
    fun d(t: Throwable?) {
        Timber.tag(TAG).d(t)
    }

    /** Log an info message with optional format args. */
    fun i(message: String?, vararg args: Any?) {
        Timber.tag(TAG).i(message, *args)
    }

    /** Log an info exception and a message with optional format args. */
    fun i(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).i(t, message, *args)
    }

    /** Log an info exception. */
    fun i(t: Throwable?) {
        Timber.tag(TAG).i(t)
    }

    /** Log a warning message with optional format args. */
    fun w(message: String?, vararg args: Any?) {
        Timber.tag(TAG).w(message, *args)
    }

    /** Log a warning exception and a message with optional format args. */
    fun w(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).w(t, message, *args)
    }

    /** Log a warning exception. */
    fun w(t: Throwable?) {
        Timber.tag(TAG).w(t)
    }

    /** Log an error message with optional format args. */
    fun e(message: String?, vararg args: Any?) {
        Timber.tag(TAG).e(message, *args)
    }

    /** Log an error exception and a message with optional format args. */
    fun e(t: Throwable?, message: String?, vararg args: Any?) {
        Timber.tag(TAG).e(message, *args)
    }

    /** Log an error exception. */
    fun e(t: Throwable?) {
        Timber.tag(TAG).e(t)
    }
    
}