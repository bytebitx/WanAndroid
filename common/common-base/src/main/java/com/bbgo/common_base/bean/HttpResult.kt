package com.bbgo.common_base.bean

import androidx.annotation.Keep

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/8/13 11:41 上午
 */
@Keep
class HttpResult<T>(val data: T) : BaseBean()

