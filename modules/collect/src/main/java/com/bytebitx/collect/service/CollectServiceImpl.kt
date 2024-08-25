package com.bytebitx.collect.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.service.collect.CollectService
import com.bytebitx.collect.repository.CollectRepository
import com.bytebitx.collect.viewmodel.CollectViewModel

/**
 *  author: wangyb
 *  date: 2021/5/27 7:31 下午
 *  description: todo
 */
@Route(path = RouterPath.Collect.SERVICE_COLLECT)
class CollectServiceImpl : CollectService{

    private val viewModel = CollectViewModel(CollectRepository.getInstance())

    override fun collect(indexPage: Int, position: Int,pageId: Int) {
        viewModel.collectArticle(indexPage, position, pageId)
    }

    override fun unCollect(indexPage: Int, position: Int,pageId: Int) {
        viewModel.unCollectArticle(indexPage, position, pageId)
//        viewModelScope.launch {
//            CollectRepository.getInstance().unCollectArticle(pageId)
//                .catch {
//
//                }
//                .collectLatest {
//                    if (it.errorCode != 0) {
//                        context?.showToast("取消收藏失败")
//                        return@collectLatest
//                    }
//                    context?.getString(R.string.cancel_collect_success)?.let { context?.showToast(it) }
//                }
//        }
    }

    override fun init(context: Context?) {
    }
}