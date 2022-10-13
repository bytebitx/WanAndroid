package com.bbgo.common_base.util

import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import com.bbgo.common_base.BaseApplication
import java.io.File
import java.math.BigDecimal


class FileUtils {

    companion object {

        /**
         * 外置存储卡的路径
         * /storage/emulated/0
         */
        fun getExternalStorePath(): String? {
            return if (isExistExternalStore()) {
                Environment.getExternalStorageDirectory().absolutePath
            } else null
        }

        /**
         * 外置存储卡文件路径
         * /storage/emulated/0/Android/data/com.bbgo.wanandroid/files
         */
        @JvmStatic
        fun getExternalFilePath(): String? {
            return BaseApplication.getContext().getExternalFilesDir(null)?.absolutePath
        }

        /**
         * 外置存储卡缓存的路径
         * /storage/emulated/0/Android/data/com.bbgo.wanandroid/cache
         */
        fun getExternalCachePath(): String? {
            return if (isExistExternalStore()) {
                return BaseApplication.getContext().externalCacheDir?.absolutePath
            } else null
        }

        /**
         * 内置存储卡文件的路径
         * /data/user/0/com.bbgo.wanandroid/files
         */
        fun getInternalFilePath(): String {
            return BaseApplication.getContext().filesDir.absolutePath
        }

        /**
         * 内置存储卡缓存的路径
         * /data/user/0/com.bbgo.wanandroid/cache
         */
        fun getInternalCachePath(): String {
            return BaseApplication.getContext().cacheDir.absolutePath
        }

        /**
         * 优先使用外置存储卡，没有则使用内部存储卡
         */
        fun getStoreFilePath(): String? {
            return if (isExistExternalStore()) {
                getExternalFilePath()
            } else {
                getInternalFilePath()
            }
        }

        /**
         * 优先使用外置存储卡，没有则使用内部存储卡
         */
        fun getStoreCachePath(): String? {
            return if (isExistExternalStore()) {
                getExternalCachePath()
            } else {
                getInternalCachePath()
            }
        }

        /**
         * 获取指定路径
         */
        fun getFilePath(dir: String): String? {
            val filePath = if (isExistExternalStore()) {
                BaseApplication.getContext().getExternalFilesDir(dir)?.absolutePath
            } else {
                BaseApplication.getContext().filesDir.absolutePath + File.separator + dir
            }
            val file = File(filePath)
            if (!file.exists()) {
                file.mkdir()
            }
            return filePath
        }

        /**
         * 是否有外存卡
         */
        fun isExistExternalStore(): Boolean {
            return Environment.getExternalStorageState() == MEDIA_MOUNTED
        }

        /**
         * 格式化单位
         */
        fun formatSize(size: Double): String {
            val kiloByte = size / 1024
            if (kiloByte < 1) {
                return "0KB"
            }
            val megaByte = kiloByte / 1024
            if (megaByte < 1) {
                val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
            }
            val gigaByte = megaByte / 1024
            if (gigaByte < 1) {
                val result2 = BigDecimal(java.lang.Double.toString(megaByte))
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
            }
            val teraBytes = gigaByte / 1024
            if (teraBytes < 1) {
                val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
            }
            val result4 = BigDecimal(teraBytes)
            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
        }

        /**
         * 获取文件名，不包含后缀
         *
         * @param file 文件
         * @return 文件名
         */
        fun getFileNameNotSuffix(file: File?): String {
            return if (file == null || !file.exists()) {
                ""
            } else getStringNotSuffix(file.name)
        }

        /**
         * 获取字符串，不包含后缀
         *
         * @param str 字符串 适用于文件名
         * @return 文件名
         */
        fun getStringNotSuffix(str: String): String {
            val indexOf = str.lastIndexOf(".")
            return str.substring(0, if (indexOf < 0) str.length - 1 else indexOf)
        }
    }
}