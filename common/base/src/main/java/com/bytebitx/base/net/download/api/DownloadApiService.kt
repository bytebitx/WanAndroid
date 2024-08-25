package com.bytebitx.base.net.download.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface DownloadApiService {


    @Streaming //添加这个注解用来下载大文件
    @GET
    suspend fun downloadFile(@Url fileUrl: String): ResponseBody
}