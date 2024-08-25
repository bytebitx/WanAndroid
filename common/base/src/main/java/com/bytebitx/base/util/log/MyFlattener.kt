package com.bytebitx.base.util.log

import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.flattener.Flattener2
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: Wangyb
 * @Date:2022-12-06
 * @Description:
 */
class MyFlattener : Flattener2 {

    override fun flatten(timeMillis: Long, logLevel: Int, tag: String?, message: String?): CharSequence {
        return (getCurrDate(timeMillis)
                + '|' + LogLevel.getLevelName(logLevel)
                + '|' + tag
                + '|' + message)
    }

    private fun getCurrDate(timeMillis: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(timeMillis)
    }
}