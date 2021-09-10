package com.bbgo.common_base.constants

/**
 * @Description: Arouter路由表
 * 不同的module，一级目录必须不能相同
 *
 * @Author: wangyuebin
 * @Date: 2021/9/10 4:00 下午
 */
class RouterPath {

    /**
     * 登录注册 组件
     */
    object LoginRegister {
        private const val LOGIN_REGISTER = "module_login"
        const val PAGE_LOGIN = "/login/$LOGIN_REGISTER"
        const val PAGE_REGISTER = "/register/$LOGIN_REGISTER"

        const val SERVICE_LOGOUT = "/logout/$LOGIN_REGISTER"
    }

    /**
     * 主页
     */
    object Main {
        private const val MAIN = "main"
        const val PAGE_MAIN = "/main/$MAIN"

        const val SERVICE_BANNER = "/banner/$MAIN"
        const val SERVICE_COLLECT = "/collect/$MAIN"
    }

    /**
     * 内容 web 组件
     */
    object Content {
        private const val CONTENT = "module_content"
        const val PAGE_CONTENT = "/content/$CONTENT"
    }


    /**
     * 首页 组件
     */
    object Home {
        private const val HOME = "module_home"
        const val PAGE_HOME = "/home/$HOME"
    }

    /**
     * 微信公众号 组件
     */
    object WeChat {
        private const val WECHAT = "module_wechat"
        const val PAGE_WECHAT = "/wechat/$WECHAT"
    }

    /**
     * 体系 组件
     */
    object Sys {
        private const val SYS = "module_sys"
        const val PAGE_SYS = "/sys/$SYS"
    }

    /**
     * 体系 组件
     */
    object Square {
        private const val SQUARE = "module_square"
        const val PAGE_SQUARE = "/square/$SQUARE"
    }

    /**
     * 项目 组件
     */
    object Project {
        private const val PROJECT = "module_project"
        const val PAGE_PROJECT = "/project/$PROJECT"
    }

    /**
     * compose 组件
     */
    object Compose {
        private const val COMPOSE = "module_compose"
        const val PAGE_COMPOSE = "/compose/$COMPOSE"
    }

}