package com.bbgo.module_compose.activity

import android.app.Activity
import android.os.Bundle
import android.text.Html
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.ext.logD
import com.bbgo.module_compose.R
import com.bbgo.module_compose.bean.ArticleDetail
import com.bbgo.module_compose.theme.*
import com.bbgo.module_compose.util.InjectorUtil
import com.bbgo.module_compose.viewmodel.ComposeViewModel

@Route(path = RouterPath.Compose.PAGE_COMPOSE)
class ComposeActivity : AppCompatActivity() {

    @ExperimentalFoundationApi
    val composeViewModel: ComposeViewModel by viewModels { InjectorUtil.getComposeViewModelFactory() }

    @ExperimentalFoundationApi
    lateinit var context: Activity

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        composeViewModel.getArticles(0)
        setContent {
            WanAndroidTheme {
                RenderTopAppBar(composeViewModel)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun RenderTopAppBar(composeViewModel: ComposeViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.nav_my_collect),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                backgroundColor = colorResource(id = R.color.colorPrimary)
            )
        },
    ){
        Request(composeViewModel)
    }
}

@ExperimentalFoundationApi
@Composable
fun Request(composeViewModel: ComposeViewModel) {
    val liveData by composeViewModel.articleLiveData.observeAsState()
    logD("======================")
    logD(liveData?.data)
    liveData?.data?.let {
        RenderArticleList(it)
    }
}

@ExperimentalFoundationApi
@Composable
fun RenderArticleList(articles: List<ArticleDetail>) {
    LazyColumn {
        items(articles.size) {
            articles.forEach {
                ItemCard(articleDetail = it)
            }
        }
    }

    /*LazyColumn {
        items(articles) { articleDetail ->
            ItemCard(articleDetail = articleDetail)
        }
    }*/
}

@ExperimentalFoundationApi
@Composable
fun ItemCard(articleDetail: ArticleDetail) {
    val dp2 = dimensionResource(id = R.dimen.dp_2)
    val dp5 = dimensionResource(id = R.dimen.dp_5)
    val dp10 = dimensionResource(id = R.dimen.dp_10)
    Surface(elevation = dimensionResource(id = R.dimen.dp_1),
    ) {
        Column() {
            Spacer(modifier = Modifier.padding(vertical = dp5))
            Row {
                Row(modifier = Modifier.weight(1f)) {
                    if (articleDetail.top == "1") {
                        Spacer(modifier = Modifier.padding(horizontal = dp5))
                        Text(
                            text = "??????",
                            color = Color.Red,
                            fontSize = sp10,
                            modifier = Modifier
                                .border(dp05, Color.Red, shape = RoundedCornerShape(dp2))
                                .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                        )
                    }
                    if (articleDetail.fresh) {
                        Spacer(modifier = Modifier.padding(horizontal = dp5))
                        Text(
                            text = "???",
                            color = Color.Red,
                            fontSize = sp10,
                            modifier = Modifier
                                .border(dp05, Color.Red, shape = RoundedCornerShape(dp2))
                                .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                        )
                    }

                    if (articleDetail.tags.isNotEmpty()) {
                        Spacer(modifier = Modifier.padding(horizontal = dp5))
                        Text(
                            text = articleDetail.tags[0].name,
                            color = Color.Red,
                            fontSize = sp12,
                            modifier = Modifier
                                .border(dp05, Color.Red, shape = RoundedCornerShape(dp2))
                                .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                        )
                    }

                    Spacer(modifier = Modifier.padding(horizontal = dp5))
                    Text(
                        text = articleDetail.author,
                        color = colorResource(id = R.color.Grey600),
                        fontSize = sp12,
                        modifier = Modifier
                            .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                    )
                }

                Row(modifier = Modifier.padding(end = dp5)) {
                    Text(
                        text = articleDetail.niceDate,
                        color = colorResource(id = R.color.Grey600),
                        fontSize = sp12,
                        modifier = Modifier
                            .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                    )
                }

            }

            Spacer(modifier = Modifier.padding(vertical = dp2))

            Row(horizontalArrangement = Arrangement.Start) {
                Spacer(modifier = Modifier.padding(horizontal = dp5))
                Text(
                    text = Html.fromHtml(articleDetail.title).toString(),
                    color = colorResource(id = R.color.item_title),
                    fontSize = sp16,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = dp5),
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.padding(vertical = dp5))

            val chapterName = when {
                articleDetail.superChapterName.isNotEmpty() and articleDetail.chapterName.isNotEmpty() ->
                    "${articleDetail.superChapterName} / ${articleDetail.chapterName}"
                articleDetail.superChapterName.isNotEmpty() -> articleDetail.superChapterName
                articleDetail.chapterName.isNotEmpty() -> articleDetail.chapterName
                else -> ""
            }

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = chapterName,
                    color = colorResource(id = R.color.item_title),
                    fontSize = sp12,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = dp10)
                )

                val imgId = if (articleDetail.collect) {
                    R.drawable.ic_like
                } else {
                    R.drawable.ic_like_not
                }


                Image(
                    painter = painterResource(id = imgId),
                    contentDescription = null,
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .weight(0.1f)
                        .size(26.dp)
                        .padding(end = dp5)
                        .clickable {

                        }
                )
            }

            Spacer(modifier = Modifier.padding(vertical = dp5))

            Divider(thickness = 0.2.dp)

        }
    }
}

@Composable
fun TestItemCard() {
    val dp2 = dimensionResource(id = R.dimen.dp_2)
    val dp5 = dimensionResource(id = R.dimen.dp_5)
    Surface(shape = MaterialTheme.shapes.medium,
        elevation = dimensionResource(id = R.dimen.dp_1),
    ) {
        Column() {
            Spacer(modifier = Modifier.padding(vertical = dp2))
            Row {
                Row(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.padding(horizontal = dp5))
                    Text(
                        text = "??????",
                        color = Color.Red,
                        fontSize = sp10,
                        modifier = Modifier
                            .border(dp05, Color.Red, shape = RectangleShape)
                            .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = dp5))
                    Text(
                        text = "???",
                        color = Color.Red,
                        fontSize = sp10,
                        modifier = Modifier
                            .border(dp05, Color.Red, shape = RectangleShape)
                            .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = dp5))
                    Text(
                        text = "?????????TAG",
                        color = Color.Red,
                        fontSize = sp10,
                        modifier = Modifier
                            .border(dp05, Color.Red, shape = RectangleShape)
                            .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = dp5))
                    Text(
                        text = "?????????",
                        color = colorResource(id = R.color.Grey600),
                        fontSize = sp12,
                        modifier = Modifier
                            .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                    )
                }

                Row {
                    Spacer(modifier = Modifier.padding(horizontal = dp20))
                    Text(
                        text = "?????????",
                        color = colorResource(id = R.color.Grey600),
                        fontSize = sp12,
                        modifier = Modifier
                            .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                    )
                }

            }

            Spacer(modifier = Modifier.padding(vertical = dp2))

            Row(horizontalArrangement = Arrangement.Start) {
                Spacer(modifier = Modifier.padding(horizontal = dp5))
                Text(
                    text = "Compose ??????????????????????????????????????????????????????UI???????????????????????? Android Views ??????????????? Android Views ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????",
                    color = colorResource(id = R.color.item_title),
                    fontSize = sp16,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(end = dp5),
                    textAlign = TextAlign.Start
                )
            }

            Spacer(modifier = Modifier.padding(vertical = dp2))

            /*Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "?????? / ??????",
                                color = colorResource(id = R.color.item_title),
                                fontSize = sp8,
                                textAlign = TextAlign.Center,
                            )
                        },
                        actions = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_like_not),
                                contentDescription = null,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                        },
                        backgroundColor = Color.White
                    )
                },
            ) {}*/

            Row {
                Text(
                    text = "?????? / ??????",
                    color = colorResource(id = R.color.item_title),
                    fontSize = sp12,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = dp10)
                )


                Image(
                    painter = painterResource(id = R.drawable.ic_like_not),
                    contentDescription = null,
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .weight(0.1f)
                        .size(20.dp)
                        .padding(end = dp5)
                        .clickable {

                        }
                )
            }

        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
//        ArticleList(articleList = mutableListOf())
    TestItemCard()
}


