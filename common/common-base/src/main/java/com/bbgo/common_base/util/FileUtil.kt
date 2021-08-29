package com.bbgo.common_base.util

import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import com.bbgo.common_base.BaseApplication
import com.bbgo.common_base.listener.DowloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.orhanobut.logger.Logger
import java.io.File


class FileUtil {

    companion object {

        /**
         * 外置存储卡的路径
         */
        fun getExternalStorePath(): String? {
            return if (isExistExternalStore()) {
                Environment.getExternalStorageDirectory().absolutePath
            } else null
        }

        /**
         * 内置存储卡的路径
         * /data/user/0/com.bbgo.wanandroid/files
         */
        fun getInternalStorePath(): String {
            return BaseApplication.getContext().filesDir.absolutePath
        }

        /**
         * 优先使用外置存储卡，没有则使用内部存储卡
         */
        fun getStorePath(): String {
            return if (isExistExternalStore()) {
                Environment.getExternalStorageDirectory().absolutePath
            } else {
                getInternalStorePath()
            }
        }

        /**
         * 外置存储卡缓存的路径
         */
        fun getExternalStoreCachePath(): String? {
            return if (isExistExternalStore()) {
                return BaseApplication.getContext().externalCacheDir?.absolutePath
            } else null
        }

        /**
         * 内置存储卡的路径
         */
        fun getInternalStoreCachePath(): String {
            return BaseApplication.getContext().cacheDir.absolutePath
        }

        /**
         * 优先使用外置存储卡，没有则使用内部存储卡
         */
        fun getStoreCachePath(): String? {
            return if (isExistExternalStore()) {
                BaseApplication.getContext().externalCacheDir?.absolutePath
            } else {
                getInternalStoreCachePath()
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
            Logger.d("filePath=${filePath}")
            Logger.d("filePath2=${Environment.getExternalStorageDirectory().absolutePath}")

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

        fun downloadFile(url: String, localPath: String) {
            FileDownloader.getImpl().create(url)
                .setPath(localPath)
                .setListener(DowloadListener())
                .start()
        }
    }
}