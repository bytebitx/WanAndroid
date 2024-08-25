package com.bytebitx.base.util.log

import com.elvishew.xlog.formatter.border.BorderFormatter
import com.elvishew.xlog.internal.SystemCompat

/**
 * @Author: Wangyb
 * @Date:2022-12-06
 * @Description:
 */
class MyBorderFormatter : BorderFormatter {
    private val VERTICAL_BORDER_CHAR = '║'

    // Length: 100.
    private val TOP_HORIZONTAL_BORDER = "╔═════════════════════════════════════════════════" +
            "══════════════════════════════════════════════════"

    // Length: 99.
    private val DIVIDER_HORIZONTAL_BORDER = "╟─────────────────────────────────────────────────" +
            "──────────────────────────────────────────────────"

    // Length: 100.
    private val BOTTOM_HORIZONTAL_BORDER = "╚═════════════════════════════════════════════════" +
            "══════════════════════════════════════════════════"


    override fun format(segments: Array<out String>?): String {
        if (segments == null || segments.size == 0) {
            return ""
        }

        val nonNullSegments = arrayOfNulls<String>(segments.size)
        var nonNullCount = 0
        for (segment in segments) {
            if (segment != null) {
                nonNullSegments[nonNullCount++] = segment
            }
        }
        if (nonNullCount == 0) {
            return ""
        }

        val msgBuilder = StringBuilder()
        msgBuilder.append("     ").append(SystemCompat.lineSeparator)
        msgBuilder.append(TOP_HORIZONTAL_BORDER).append(SystemCompat.lineSeparator)
        for (i in 0 until nonNullCount) {
            msgBuilder.append(nonNullSegments[i]?.let { appendVerticalBorder(it) })
            if (i != nonNullCount - 1) {
                msgBuilder.append(SystemCompat.lineSeparator).append(DIVIDER_HORIZONTAL_BORDER)
                    .append(SystemCompat.lineSeparator)
            } else {
                msgBuilder.append(SystemCompat.lineSeparator).append(BOTTOM_HORIZONTAL_BORDER)
            }
        }
        return msgBuilder.toString()
    }

    private fun appendVerticalBorder(msg: String): String? {
        val borderedMsgBuilder = java.lang.StringBuilder(msg.length + 10)
        val lines = msg.split(SystemCompat.lineSeparator.toRegex()).toTypedArray()
        var i = 0
        val N = lines.size
        while (i < N) {
            if (i != 0) {
                borderedMsgBuilder.append(SystemCompat.lineSeparator)
            }
            val line = lines[i]
            borderedMsgBuilder.append(VERTICAL_BORDER_CHAR).append(line)
            i++
        }
        return borderedMsgBuilder.toString()
    }
}