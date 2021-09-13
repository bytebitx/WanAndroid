package com.bbgo.module_home.repository

import com.bbgo.common_service.banner.bean.Banner
import com.bbgo.module_home.local.DBUtil
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
@ActivityRetainedScoped
class HomeLocalRepository @Inject constructor(){

    fun insertBanners(banners: List<Banner>) {
        DBUtil.getInstance().runInTransaction {
            banners.forEach {
                DBUtil.getInstance().bannerDao().insertAll(it)
            }
        }
    }
}