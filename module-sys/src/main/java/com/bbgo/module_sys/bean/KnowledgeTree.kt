package com.bbgo.module_sys.bean


data class KnowledgeTreeBody(
    val `data`: List<KnowledgeTree>,
    val errorCode: Int = 0,
    val errorMsg: String = "",
)


data class KnowledgeTree(
    val children: List<KnowledgeDetail>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class KnowledgeDetail(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)