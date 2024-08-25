package com.bytebitx.base.util

import java.security.MessageDigest
import java.util.*

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/8/27 4:33 下午
 */
class MD5Utils {

    companion object {
        //生成MD5
        fun getMD5(message: String): String {
            var md5 = ""
            try {
                val md = MessageDigest.getInstance("MD5") // 创建一个md5算法对象
                val messageByte = message.toByteArray(charset("UTF-8"))
                val md5Byte = md.digest(messageByte) // 获得MD5字节数组,16*8=128位
                md5 = bytesToHex(md5Byte) // 转换为16进制字符串
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return md5
        }

        // 二进制转十六进制
        private fun bytesToHex(bytes: ByteArray): String {
            val hexStr = StringBuffer()
            var num: Int
            for (i in bytes.indices) {
                num = bytes[i].toInt()
                if (num < 0) {
                    num += 256
                }
                if (num < 16) {
                    hexStr.append("0")
                }
                hexStr.append(Integer.toHexString(num))
            }
            return hexStr.toString().uppercase(Locale.getDefault())
        }
    }
}