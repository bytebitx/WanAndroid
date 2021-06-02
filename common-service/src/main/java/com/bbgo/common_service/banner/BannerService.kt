package com.bbgo.common_service.banner

import com.alibaba.android.arouter.facade.template.IProvider
import com.bbgo.common_service.banner.bean.Banner

/**
 *  author: wangyb
 *  date: 2021/6/2 11:12 上午
 *  description: todo
 */
interface BannerService : IProvider {
    suspend fun insertBanners(banners: List<Banner>)
}