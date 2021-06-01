package com.bbgo.common_base.constants

/**
 *  author: wangyb
 *  date: 2021/5/21 4:38 下午
 *  description: todo
 */
object Constants {
    const val DB_INSTANCE = "DB_INSTANCE"
    const val TOKEN = "token"
    const val USER_NAME = "username"
    const val PASSWORD = "password"
    const val APP_TAG = "WanAndroid"

    const val BUGLY_ID = "76e2b2867d"

    const val BASE_URL = "https://www.wanandroid.com/"

    const val LOGIN_KEY = "login"
    const val USERNAME_KEY = "username"
    const val PASSWORD_KEY = "password"
    const val TOKEN_KEY = "token"
    const val HAS_NETWORK_KEY = "has_network"

    const val TODO_NO = "todo_no"
    const val TODO_ADD = "todo_add"
    const val TODO_DONE = "todo_done"

    /**
     * url key
     */
    const val CONTENT_URL_KEY = "url"
    /**
     * title key
     */
    const val CONTENT_TITLE_KEY = "title"
    /**
     * id key
     */
    const val CONTENT_ID_KEY = "id"
    /**
     * id key
     */
    const val CONTENT_CID_KEY = "cid"
    /**
     * share key
     */
    const val CONTENT_SHARE_TYPE = "text/plain"
    /**
     * content data key
     */
    const val CONTENT_DATA_KEY = "content_data"

    const val TYPE_KEY = "type"

    const val SEARCH_KEY = "search_key"

    const val TODO_TYPE = "todo_type"
    const val TODO_BEAN = "todo_bean"

    const val POSITION = "POSITION"

    object Type {
        const val COLLECT_TYPE_KEY = "collect_type"
        const val ABOUT_US_TYPE_KEY = "about_us_type_key"
        const val SETTING_TYPE_KEY = "setting_type_key"
        const val SEARCH_TYPE_KEY = "search_type_key"
        const val ADD_TODO_TYPE_KEY = "add_todo_type_key"
        const val SEE_TODO_TYPE_KEY = "see_todo_type_key"
        const val EDIT_TODO_TYPE_KEY = "edit_todo_type_key"
        const val SHARE_ARTICLE_TYPE_KEY = "share_article_type_key"
        const val SCAN_QR_CODE_TYPE_KEY = "scan_qr_code_type_key"
    }

    /**
     * 不同的module，一级目录必现不能相同
     */
    const val NAVIGATION_TO_LOGIN = "/login/module/navigate"
    const val NAVIGATION_TO_REGISTER = "/register/module/navigate"
    const val NAVIGATION_TO_MAIN = "/main/module/navigate"
    const val NAVIGATION_TO_CONTENT = "/content/module/navigate"
    const val NAVIGATION_TO_HOME_FRG = "/home/fragment/navigate"
    const val NAVIGATION_TO_SQUARE_FRG = "/square/fragment/navigate"
    const val NAVIGATION_TO_WECHAT_FRG = "/wechat/fragment/navigate"

    const val SERVICE_COLLECT = "/collect/service"
    const val SERVICE_LOGOUT = "/logout/service"

    object CollectType {
        const val COLLECT = "COLLECT"
        const val UNCOLLECT = "UNCOLLECT"
        const val UNKNOWN = "UNKNOWN"
    }
}