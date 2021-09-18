package com.bbgo.common_base.interceptor

import com.alibaba.android.arouter.utils.ClassUtils
import com.bbgo.common_base.BaseApplication
import com.bbgo.common_base.util.AppUtil

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/9/18 9:37 上午
 */
class RefletionUtils {

    companion object {

        private val classPath = "com.android.processor.apt.Login$\$AppUtil"

        private val classList = mutableListOf<String>()

        fun getRequireLoginPages(): List<String> {
            kotlin.runCatching {
                val clzList = ClassUtils.getFileNameByPackageName(BaseApplication.getContext(), "com.android.processor.apt")
                for(name in clzList) {
                    val clz = Class.forName(name)
                    val method = clz.getDeclaredMethod("getRequireLoginList")
                    method.isAccessible = true
                    val list = method.invoke(null) as List<String>
                    if (list.isEmpty()) continue
                    classList.addAll(list)
                }
                return classList
            }.onFailure {
                return emptyList()
            }
            return emptyList()
        }

        fun getLoginField(): Boolean {
            kotlin.runCatching {
                val clz = Class.forName(classPath)
                val method = clz.getDeclaredMethod("getLoginFieldClass")
                method.isAccessible = true
                val targetClzString = method.invoke(null) as String
                val targetClz = Class.forName(targetClzString)
                val field = targetClz.getDeclaredField("isLogin")
                field.isAccessible = true
                return field.getBoolean(targetClz)
                /*val clzList = ClassUtils.getFileNameByPackageName(BaseApplication.getContext(), "com.android.processor.apt")
                for(name in clzList) {
                    val clz = Class.forName(name)
                    val method = clz.getDeclaredMethod("getLoginFieldClass")
                    method.isAccessible = true
                    val targetClzString = method.invoke(null) as String
                    if (targetClzString == "null") {
                        continue
                    }
                    val targetClz = Class.forName(targetClzString)
                    val field = targetClz.getDeclaredField("isLogin")
                    field.isAccessible = true
                    return field.getBoolean(targetClz)
                }
                return false*/
            }.onFailure {
                return false
            }
            return false
        }
    }

}