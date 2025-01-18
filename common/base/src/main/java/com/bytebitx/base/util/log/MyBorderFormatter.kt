package com.bytebitx.base.util.log

import com.elvishew.xlog.formatter.border.BorderFormatter
import com.elvishew.xlog.internal.SystemCompat

/**
 * @Author: Wangyb
 * @Date:2022-12-06
 * @Description:
 */
class MyBorderFormatter : BorderFormatter {
    private val VERTICAL_BORDER_CHAR = '│'

    private val TOP_LEFT_CORNER = '┌'
    private val DOUBLE_DIVIDER = "────────────────────────────────────────────────────────"

    private val MIDDLE_CORNER = '├'
    private val BOTTOM_LEFT_CORNER = '└';

    // Length: 100.
    private val TOP_HORIZONTAL_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER

    // Length: 99.
    private val DIVIDER_HORIZONTAL_BORDER = MIDDLE_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER

    // Length: 100.
    private val BOTTOM_HORIZONTAL_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER


    override fun format(segments: Array<String?>): String {
        if (segments.isEmpty()) {
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
        msgBuilder.append(TOP_HORIZONTAL_BORDER)
            .append(SystemCompat.lineSeparator)
        for (i in 0 until nonNullCount) {
            msgBuilder.append(appendVerticalBorder(nonNullSegments[i]))
            if (i != nonNullCount - 1) {
                msgBuilder.append(SystemCompat.lineSeparator)
                    .append(DIVIDER_HORIZONTAL_BORDER)
                    .append(SystemCompat.lineSeparator)
            } else {
                msgBuilder.append(SystemCompat.lineSeparator)
                    .append(BOTTOM_HORIZONTAL_BORDER)
            }
        }
        return msgBuilder.toString()
    }

    /**
     * Add {@value #VERTICAL_BORDER_CHAR} to each line of msg.
     *
     * @param msg the message to add border
     * @return the message with {@value #VERTICAL_BORDER_CHAR} in the start of each line
     */
    private fun appendVerticalBorder(msg: String?): String {
        val borderedMsgBuilder = StringBuilder(msg!!.length + 10)
        val lines = msg!!.split(SystemCompat.lineSeparator.toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
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