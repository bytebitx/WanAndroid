package com.bbgo.wanandroid.collect.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_service.collect.CollectService
import com.bbgo.wanandroid.collect.viewmodel.CollectViewModel
import com.bbgo.wanandroid.event.MessageEvent
import com.bbgo.wanandroid.home.ui.HomeFragment
import kotlinx.coroutines.Job
import org.greenrobot.eventbus.EventBus

/**
 *  author: wangyb
 *  date: 2021/5/27 7:31 下午
 *  description: todo
 */
@Route(path = Constants.SERVICE_COLLECT)
class CollectServiceImpl : CollectService {

    private var context: Context? = null

    override fun collect(position: Int,pageId: Int) {
        this.context?.showToast("collect")
        EventBus.getDefault().post(MessageEvent(CollectViewModel.COLLECT, position, pageId))
    }

    override fun unCollect(position: Int,pageId: Int) {
        this.context?.showToast("unCollect")
        EventBus.getDefault().post(MessageEvent(CollectViewModel.UNCOLLECT, position, pageId))
    }

    override fun init(context: Context?) {
        this.context = context
    }
}