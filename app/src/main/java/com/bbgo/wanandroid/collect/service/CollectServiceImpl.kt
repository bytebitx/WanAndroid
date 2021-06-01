package com.bbgo.wanandroid.collect.service

import android.annotation.SuppressLint
import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_service.collect.CollectService
import com.bbgo.wanandroid.collect.repository.CollectRepository
import com.bbgo.wanandroid.collect.viewmodel.CollectViewModel

/**
 *  author: wangyb
 *  date: 2021/5/27 7:31 下午
 *  description: todo
 */
@Route(path = Constants.SERVICE_COLLECT)
class CollectServiceImpl : CollectService{

    @SuppressLint("StaticFieldLeak")
    private var context: Context? = null
    private val viewModel by lazy { CollectViewModel(CollectRepository.getInstance()) }

    override fun collect(position: Int,pageId: Int) {
        this.context?.showToast("collect")
        viewModel.collectArticle(position, pageId)
    }

    override fun unCollect(position: Int,pageId: Int) {
        this.context?.showToast("unCollect")
        viewModel.unCollectArticle(position, pageId)
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
//        EventBus.getDefault().post(MessageEvent(CollectViewModel.UNCOLLECT, position, pageId))
    }

    override fun init(context: Context?) {
        this.context = context
    }
}