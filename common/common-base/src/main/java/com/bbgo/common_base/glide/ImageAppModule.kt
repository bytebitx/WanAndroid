package com.bbgo.common_base.glide

import android.content.Context
import com.bbgo.common_base.glide.okhttp.OkHttpUrlLoader
import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.common_base.util.FileUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


@GlideModule
class ImageAppModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //设置缓存大小为20mb
//        val memoryCacheSizeBytes = 1024 * 1024 * 20 // 20mb
//        //设置内存缓存大小
//        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
        val calculator = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(5.toFloat())
            .setBitmapPoolScreens(5.toFloat())
            .build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))

        if (FileUtil.isExistExternalStore()) {
            builder.setDiskCache(
                ExternalPreferredCacheDiskCacheFactory(context)
            )
        } else {
            builder.setDiskCache(
                InternalCacheDiskCacheFactory(context)
            )
        }
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(
            ServiceCreators.httpClient))
    }
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}