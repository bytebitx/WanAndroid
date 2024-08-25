package com.bytebitx.home.repository

import com.bytebitx.home.bean.Banner
import com.bytebitx.home.local.DBUtil
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