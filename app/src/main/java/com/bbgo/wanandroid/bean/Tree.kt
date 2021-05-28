package com.bbgo.wanandroid.bean

data class Trees(
    val `data`: List<TreeData>,
    val errorCode: Int,
    val errorMsg: String
)

data class TreeData(
    val children: List<Children>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class ProjectResponse(
    val `data`: List<Children>,
    val errorCode: Int,
    val errorMsg: String
)

data class Children(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)