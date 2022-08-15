package com.bbgo.common_base.util.log

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
    fun v(message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).v(message, args)
    }

    /** Log a verbose exception and a message with optional format args. */
    fun v(t: Throwable?, message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).v(t, message, *args)
    }

    /** Log a verbose exception. */
    fun v(t: Throwable?, tag: String = TAG) {
        Timber.tag(tag).v(t)
    }

    /** Log a debug message with optional format args. */
    fun d(message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).d(message, *args)
    }

    /** Log a debug exception and a message with optional format args. */
    fun d(t: Throwable?, message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).v(t, message, *args)
    }

    /** Log a debug exception. */
    fun d(t: Throwable?, tag: String = TAG) {
        Timber.tag(tag).d(t)
    }

    /** Log an info message with optional format args. */
    fun i(message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).i(message, *args)
    }

    /** Log an info exception and a message with optional format args. */
    fun i(t: Throwable?, message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).i(t, message, *args)
    }

    /** Log an info exception. */
    fun i(t: Throwable?, tag: String = TAG) {
        Timber.tag(tag).i(t)
    }

    /** Log a warning message with optional format args. */
    fun w(message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).w(message, *args)
    }

    /** Log a warning exception and a message with optional format args. */
    fun w(t: Throwable?, message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).w(t, message, *args)
    }

    /** Log a warning exception. */
    fun w(t: Throwable?, tag: String = TAG) {
        Timber.tag(tag).w(t)
    }

    /** Log an error message with optional format args. */
    fun e(message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).e(message, *args)
    }

    /** Log an error exception and a message with optional format args. */
    fun e(t: Throwable?, message: String?, vararg args: Any?, tag: String = TAG) {
        Timber.tag(tag).e(message, *args)
    }

    /** Log an error exception. */
    fun e(t: Throwable?, tag: String = TAG) {
        Timber.tag(tag).e(t)
    }
    
}