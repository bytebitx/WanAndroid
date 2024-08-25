package com.bytebitx.project.bean

import androidx.annotation.Keep
import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

@Keep
@Entity(tableName = "project_tree")
data class ProjectBean(
    @PrimaryKey var id: Int = 0,
    @Ignore var children: List<Any>? = null,
    var courseId: Int = 0,
    var name: String = "",
    var order: Int = 0,
    @ColumnInfo(name = "parent_chapter_id") var parentChapterId: Int = 0,
    @ColumnInfo(name = "user_control_set_top") var userControlSetTop: Boolean = false,
    var visible: Int = 0
)

@Keep
data class ArticleData(
    var curPage: Int,
    var datas: MutableList<ArticleDetail>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)

@Keep
@Entity(tableName = "article_detail")
data class ArticleDetail(
    @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "apk_link") var apkLink: String = "",
    var audit: Int = 0,
    var author: String = "",
    @ColumnInfo(name = "can_edit") var canEdit: Boolean = false,
    @ColumnInfo(name = "chapter_id") var chapterId: Int = 0,
    @ColumnInfo(name = "chapter_name") var chapterName: String = "",
    var collect: Boolean = false,
    @ColumnInfo(name = "course_id") var courseId: Int = 0,
    var desc: String = "",
    @ColumnInfo(name = "desc_md") var descMd: String = "",
    @ColumnInfo(name = "envelope_pic") var envelopePic: String = "",
    var fresh: Boolean = false,
    var host: String = "",
    var link: String = "",
    @ColumnInfo(name = "nice_date") var niceDate: String = "",
    @ColumnInfo(name = "nice_share_date") var niceShareDate: String = "",
    var origin: String = "",
    var prefix: String = "",
    @ColumnInfo(name = "project_link") var projectLink: String = "",
    @ColumnInfo(name = "publish_time") var publishTime: Long = 0,
    @ColumnInfo(name = "real_super_chapter_id") var realSuperChapterId: Int = 0,
    @ColumnInfo(name = "self_visible") var selfVisible: Int = 0,
    @ColumnInfo(name = "share_date") var shareDate: Long = 0,
    @ColumnInfo(name = "share_user") var shareUser: String = "",
    @ColumnInfo(name = "super_chapter_id") var superChapterId: Int = 0,
    @ColumnInfo(name = "super_chapter_name") var superChapterName: String = "",
    @Ignore var tags: List<Tag>? = null,
    var title: String = "",
    var type: Int = 0,
    @ColumnInfo(name = "user_id") var userId: Int = 0,
    var visible: Int = 0,
    var zan: Int = 0,
    var top: String = "",
    @ColumnInfo(name = "local_path") var localPath: String = ""
)

@Keep
@Entity(tableName = "tag",
    foreignKeys = [
        ForeignKey(
    entity = ArticleDetail::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("article_id"),
    onDelete = CASCADE,)],
    indices = [Index(value = arrayOf("article_id"), unique = true)]
)
data class Tag(
    @PrimaryKey(autoGenerate = true)var id: Long,
    @ColumnInfo(name = "article_id") var artileId: Int,
    var name: String,
    var url: String
)

/**
 * 连表查询，需要定义一个中间bean，具体用法详见
 * https://developer.android.google.cn/training/data-storage/room/relationships?hl=zh-cn
 */
@Keep
data class ArticleDetailWithTag(
    @Embedded var articleDetail: ArticleDetail,
    @Relation(
        parentColumn = "id",
        entityColumn = "article_id"
    )
    var tags: List<Tag>?
)