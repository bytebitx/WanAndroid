package com.bbgo.common_base.ext

import com.bbgo.common_base.BuildConfig
import com.orhanobut.logger.Logger

/**
 *  author: wangyb
 *  date: 3/29/21 6:01 PM
 *  description: 日志工具类
 */

private const val VERBOSE = 1
private const val DEBUG = 2
private const val INFO = 3
private const val WARN = 4
private const val ERROR = 5

private val level = if (BuildConfig.DEBUG) VERBOSE else WARN

fun logV(tag: String, msg: String?) {
    if (level <= VERBOSE) {
        Logger.v(tag, msg.toString())
    }
}

fun logD(tag: String, msg: String?) {
    if (level <= DEBUG) {
        Logger.d(tag, msg.toString())
    }
}

fun logD(tag: String, obj: Any?) {
    if (level <= DEBUG) {
        Logger.d(tag, obj)
    }
}

fun logD(obj: Any?) {
    if (level <= DEBUG) {
        Logger.d(obj)
    }
}

fun logI(tag: String, msg: String?) {
    if (level <= INFO) {
        Logger.i(tag, msg.toString())
    }
}

fun logW(tag: String, msg: String?, tr: Throwable? = null) {
    if (level <= WARN) {
        if (tr == null) {
            Logger.w(tag, msg.toString())
        } else {
            Logger.w(tag, msg.toString(), tr)
        }
    }
}

fun logE(tag: String, msg: String?, tr: Throwable) {
    if (level <= ERROR) {
        Logger.e(tag, msg.toString(), tr)
    }
}

