package com.bytebitx.base.constants

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
        const val PAGE_LOGIN = "/$LOGIN_REGISTER/login"
        const val PAGE_REGISTER = "/$LOGIN_REGISTER/register"
        const val SERVICE_LOGOUT = "/$LOGIN_REGISTER/logout"
    }

    /**
     * 主页
     */
    object Main {
        private const val MAIN = "app_main"
        const val PAGE_MAIN = "/$MAIN/main"
    }

    object Collect {
        private const val COLLECT = "module_collect"
        const val SERVICE_COLLECT = "/$COLLECT/collect"
    }

    /**
     * 内容 web 组件
     */
    object Content {
        private const val CONTENT = "module_content"
        const val PAGE_CONTENT = "/$CONTENT/content"
    }

    /**
     * 首页 组件
     */
    object Home {
        private const val HOME = "module_home"
        const val PAGE_HOME = "/$HOME/home"
    }

    /**
     * 微信公众号 组件
     */
    object WeChat {
        private const val WECHAT = "module_wechat"
        const val PAGE_WECHAT = "/$WECHAT/wechat"
    }

    /**
     * 体系 组件
     */
    object Sys {
        private const val SYS = "module_sys"
        const val PAGE_SYS = "/$SYS/sys"
    }

    /**
     * 体系 组件
     */
    object Square {
        private const val SQUARE = "module_square"
        const val PAGE_SQUARE = "/$SQUARE/square"
    }

    /**
     * 项目 组件
     */
    object Project {
        private const val PROJECT = "module_project"
        const val PAGE_PROJECT = "/$PROJECT/project"
    }

    /**
     * compose 组件
     */
    object Compose {
        private const val COMPOSE = "module_compose"
        const val PAGE_COMPOSE = "/$COMPOSE/compose"
    }

    object Media {
        private const val MEDIA = "module_media"
        const val PAGE_VIDEO = "/$MEDIA/video"
    }

}