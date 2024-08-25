package com.bytebitx.base.net.download

interface DownloadListener {
    fun onStart()
    fun onProgress(progress: Int, total: Float)
    fun onFinish(path: String, url: String)
    fun onError(msg: String?)
}