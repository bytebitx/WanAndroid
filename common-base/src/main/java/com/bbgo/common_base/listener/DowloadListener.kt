package com.bbgo.common_base.listener

import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.logE
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/8/27 3:33 下午
 */
class DowloadListener : FileDownloadListener() {
    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
    }

    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
        logD("soFarBytes = $soFarBytes , totalBytes = $totalBytes")
    }

    override fun completed(task: BaseDownloadTask) {
        logD(task.targetFilePath)
    }

    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
    }

    override fun error(task: BaseDownloadTask?, e: Throwable) {
        logE("TEST", e.message, e )
    }

    override fun warn(task: BaseDownloadTask?) {
    }
}