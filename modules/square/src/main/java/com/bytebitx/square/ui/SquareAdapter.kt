package com.bytebitx.square.ui

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bytebitx.base.util.ImageLoader
import com.bytebitx.square.R
import com.bytebitx.square.bean.ArticleDetail
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class SquareAdapter(datas: MutableList<ArticleDetail>)
    : BaseQuickAdapter<ArticleDetail, BaseViewHolder>(R.layout.item_square_list, datas) {

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
        if (item.envelopePic.isNotEmpty()) {
            holder.getView<ImageView>(R.id.iv_article_thumbnail)
                    .visibility = View.VISIBLE
            context.let {
                ImageLoader.load(it, item.envelopePic, holder.getView(R.id.iv_article_thumbnail))
            }
        } else {
            holder.getView<ImageView>(R.id.iv_article_thumbnail)
                    .visibility = View.GONE
        }
        val tvFresh = holder.getView<TextView>(R.id.tv_article_fresh)
        if (item.fresh) {
            tvFresh.visibility = View.VISIBLE
        } else {
            tvFresh.visibility = View.GONE
        }
        val tv_top = holder.getView<TextView>(R.id.tv_article_top)
        if (item.top == "1") {
            tv_top.visibility = View.VISIBLE
        } else {
            tv_top.visibility = View.GONE
        }
        val tvArticleTag = holder.getView<TextView>(R.id.tv_article_tag)
        if (item.tags.isNotEmpty()) {
            tvArticleTag.visibility = View.VISIBLE
            tvArticleTag.text = item.tags[0].name
        } else {
            tvArticleTag.visibility = View.GONE
        }
    }

}
