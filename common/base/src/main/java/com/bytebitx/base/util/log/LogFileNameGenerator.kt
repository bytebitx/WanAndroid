package com.geely.common.log

import com.elvishew.xlog.printer.file.naming.FileNameGenerator
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: Wangyb
 * @Date:2022-12-19
 * @Description:
 */
class LogFileNameGenerator : FileNameGenerator {

    private val mLocalDateFormat = object : ThreadLocal<SimpleDateFormat?>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-MM-dd", Locale.US)
            }
        }

    override fun isFileNameChangeable() = true

    override fun generateFileName(logLevel: Int, timestamp: Long): String {
        val sdf = mLocalDateFormat.get()
        sdf!!.timeZone = TimeZone.getDefault()
        return sdf.format(Date(timestamp)) + ".log"
    }
}