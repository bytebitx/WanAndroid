package com.bbgo.wanandroid.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_service.banner.BannerService
import com.bbgo.common_service.banner.bean.Banner
import com.bbgo.wanandroid.local.DBUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *  author: wangyb
 *  date: 2021/6/2 11:15 上午
 *  description: todo
 */
@Route(path = RouterPath.Main.SERVICE_BANNER)
class BannerServiceImpl : BannerService {

    override suspend fun insertBanners(banners: List<Banner>) {
        flow<Void> {
            DBUtil.getInstance().runInTransaction {
                banners.forEach {
                    DBUtil.getInstance().bannerDao().insertAll(it)
                }
            }
        }.flowOn(Dispatchers.IO)
            .catch {  }
            .collectLatest {  }
    }

    override fun init(context: Context?) {
    }
}