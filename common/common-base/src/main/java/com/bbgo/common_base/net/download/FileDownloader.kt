package com.bbgo.common_base.net.download

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.common_base.net.download.api.DownloadApiService
import com.bbgo.common_base.util.Logs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileDownloader {

    private val service = ServiceCreators.create(DownloadApiService::class.java)

    private var url: String = ""
    private var path: String = ""
    private var listener: DownloadListener? = null
    private var downloadLength: Long = 0
    private var contentLength: Long = 0

    fun create(url: String) : FileDownloader {
        FileDownloader.url = url
        return this
    }

    fun setPath(path: String): FileDownloader {
        FileDownloader.path = path
        return this
    }

    fun setListener(listener: DownloadListener): FileDownloader {
        FileDownloader.listener = listener
        return this
    }

    suspend fun start() = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            listener?.onStart()
            val responseBody = service.downloadFile(url)
            contentLength = responseBody.contentLength()
            val file = File(path)
            write(responseBody.byteStream(), file)
            listener?.onFinish(path, url)
        }.onFailure {
            listener?.onError(it.message)
            Logs.e(it, "download file fail, it's reason is ${it.message}")
        }
    }

    private fun write(inputStream: InputStream, file: File) {
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file, true)
            val buffer = ByteArray(4096)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                fos.write(buffer, 0, len)
                downloadLength += len
                notifyProgress()
            }
            fos.flush()
        } catch (e: IOException) {
            throw IOException(e.message)
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun notifyProgress() {
        val progress = ((downloadLength.toFloat() / contentLength.toFloat()) * 100).toInt()
        listener?.onProgress(progress, contentLength.toFloat())
    }
}