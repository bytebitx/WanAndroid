package com.bbgo.common_base.util

import android.content.Context
import android.widget.ImageView
import com.bbgo.common_base.BaseApplication
import com.bbgo.common_base.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Created by chenxz on 2018/6/12.
 */
object ImageLoader {

    // 1.开启无图模式 2.非WiFi环境 不加载图片
    private val isLoadImage = !SettingUtil.getIsNoPhotoMode() || NetWorkUtil.isWifi(BaseApplication.getContext())

    /**
     * 加载图片
     * @param context
     * @param url
     * @param iv
     */
    fun load(context: Context, url: String?, iv: ImageView) {
        if (isLoadImage) {
            Glide.with(context).clear(iv)
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.bg_placeholder)
            Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(options)
                .into(iv)
        }

    }

}