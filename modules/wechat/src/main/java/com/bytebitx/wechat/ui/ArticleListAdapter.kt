package com.bytebitx.wechat.ui

import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bytebitx.base.util.ImageLoader
import com.bytebitx.wechat.R
import com.bytebitx.wechat.bean.ArticleDetail
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class ArticleListAdapter(datas: MutableList<ArticleDetail>)
    : BaseQuickAdapter<ArticleDetail, BaseViewHolder>(R.layout.item_article_list, datas) {

    override fun convert(holder: BaseViewHolder, item: ArticleDetail) {
        val authorStr = if (item.author.isNotEmpty()) item.author else item.shareUser
        holder.setText(R.id.tv_article_title, Html.fromHtml(item.title))
                .setText(R.id.tv_article_author, authorStr)
                .setText(R.id.tv_article_date, item.niceDate)
                .setImageResource(R.id.iv_like,
                        if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not
                )
        val chapterName = when {
            item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty() ->
                "${item.superChapterName} / ${item.chapterName}"
            item.superChapterName.isNotEmpty() -> item.superChapterName
            item.chapterName.isNotEmpty() -> item.chapterName
            else -> ""
        }
        holder.setText(R.id.tv_article_chapterName, chapterName)

        if (!TextUtils.isEmpty(item.envelopePic)) {
            holder.getView<ImageView>(R.id.iv_article_thumbnail)
                    .visibility = View.VISIBLE
            ImageLoader.load(context, item.envelopePic, holder.getView(R.id.iv_article_thumbnail))
        } else {
            holder.getView<ImageView>(R.id.iv_article_thumbnail)
                    .visibility = View.GONE
        }
    }

}