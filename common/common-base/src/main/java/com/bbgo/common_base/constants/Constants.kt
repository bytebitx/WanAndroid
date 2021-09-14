package com.bbgo.common_base.constants

/**
 *  author: wangyb
 *  date: 2021/5/21 4:38 下午
 *  description: todo
 */
object Constants {
    const val DB_INSTANCE = "DB_INSTANCE"
    const val TOKEN = "token"
    const val USER_ID = "userid"
    const val USER_NAME = "username"
    const val PASSWORD = "password"
    const val APP_TAG = "WanAndroid"

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

    const val POSITION = "position"

    const val COLLECT = "isCollect"


    object CollectType {
        const val COLLECT = "COLLECT"
        const val UNCOLLECT = "UNCOLLECT"
        const val UNKNOWN = "UNKNOWN"
    }

    object FragmentIndex {
        const val HOME_INDEX = 0
        const val WECHAT_INDEX = 1
        const val SYS_INDEX = 2
        const val SQUARE_INDEX = 3
        const val PROJECT_INDEX = 4
    }
}