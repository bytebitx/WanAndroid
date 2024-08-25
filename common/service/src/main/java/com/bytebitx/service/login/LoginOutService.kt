package com.bytebitx.service.login

import com.alibaba.android.arouter.facade.template.IProvider
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 2021/5/31 2:16 下午
 *  description: todo
 */
interface LoginOutService : IProvider {

    suspend fun logOut(): Flow<String>

}