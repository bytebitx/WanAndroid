package com.bytebitx.content.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bytebitx.base.base.BaseActivity
import com.bytebitx.base.bus.BusKey
import com.bytebitx.base.bus.LiveDataBus
import com.bytebitx.base.constants.Constants
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.base.event.MessageEvent
import com.bytebitx.base.ext.showToast
import com.bytebitx.base.util.AppUtil
import com.bytebitx.base.util.SettingUtil
import com.bytebitx.service.collect.CollectService
import com.bytebitx.content.R
import com.bytebitx.content.databinding.ActivityContentBinding
import com.bytebitx.content.ext.getAgentWeb
import com.bytebitx.content.webclient.WebClientFactory
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import com.just.agentweb.WebChromeClient

@Route(path = RouterPath.Content.PAGE_CONTENT)
class ContentActivity : BaseActivity<ActivityContentBinding>(){

    private var mAgentWeb: AgentWeb? = null

    @Autowired
    lateinit var title: String
    @Autowired
    lateinit var url: String
    @Autowired
    lateinit var id: String
    @Autowired
    lateinit var position: String
    @Autowired
    lateinit var isCollect: String


    @Autowired
    lateinit var collectService: CollectService

    private lateinit var menu: Menu

    companion object {

        fun start(context: Context?, position: Int, id: Int, title: String, url: String, bundle: Bundle? = null) {
            Intent(context, ContentActivity::class.java).run {
                putExtra(Constants.CONTENT_ID_KEY, id)
                putExtra(Constants.CONTENT_TITLE_KEY, title)
                putExtra(Constants.CONTENT_URL_KEY, url)
                putExtra(Constants.POSITION, position)
                context?.startActivity(this, bundle)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        ARouter.getInstance().inject(this)
        initBus()
        binding.actionBar.apply {
            title = ""//getString(R.string.loading)
            setSupportActionBar(binding.actionBar.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.actionBar.tvTitle.apply {
//            text = getString(R.string.agentweb_loading)
            visibility = View.VISIBLE
            postDelayed({
                binding.actionBar.tvTitle.isSelected = true
            }, 2000)
        }
        initWebView()
    }

    /**
     * 初始化 WebView
     */
    private fun initWebView() {

        val webView = NestedScrollAgentWebView(this)

        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        mAgentWeb = url.getAgentWeb(
            this,
            binding.clMain,
            layoutParams,
            webView,
            WebClientFactory.create(url),
            mWebChromeClient,
            SettingUtil.getColor())

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //    WebView.setWebContentsDebuggingEnabled(true)
        //}

        mAgentWeb?.webCreator?.webView?.apply {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }


    private val mWebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            binding.actionBar.tvTitle.text = title
        }
    }

    private fun initBus() {
        LiveDataBus.get().with(BusKey.COLLECT, MessageEvent::class.java).observe(this) {
            handleCollect(it)
        }
    }

    private fun handleCollect(event: MessageEvent) {
        when (event.type) {
            Constants.CollectType.UNKNOWN -> {
                ARouter.getInstance().build(RouterPath.LoginRegister.PAGE_LOGIN).navigation()
            }
            else -> {
                val menuItem = menu.findItem(R.id.action_like)
                if (event.type == Constants.CollectType.COLLECT) {
                    showToast(getString(R.string.collect_success))
                    menuItem.title = getString(R.string.action_un_like)
                    isCollect = "true"
                    return
                }
                showToast(getString(R.string.cancel_collect_success))
                menuItem.title = getString(R.string.action_like)
                isCollect = "false"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        this.menu = menu
        val title = if (isCollect.toBoolean()) {
            getString(R.string.action_un_like)
        } else {
            getString(R.string.action_like)
        }
        val menuItem = menu.findItem(R.id.action_like)
        menuItem.title = title
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(
                        R.string.share_article_url,
                        getString(R.string.app_name), title, url
                    ))
                    type = Constants.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.action_share)))
                }
                return true
            }
            R.id.action_like -> {
                if (AppUtil.isLogin) {
                    if (id == "-1") return true
                    if (isCollect.toBoolean()) {
                        collectService.unCollect(-1, position.toInt(), id.toInt())
                    } else {
                        collectService.collect(-1, position.toInt(), id.toInt())
                    }
                } else {
                    ARouter.getInstance().build(RouterPath.LoginRegister.PAGE_LOGIN).navigation()
                    showToast(resources.getString(R.string.login_tint))
                }
                return true
            }
            R.id.action_browser -> {
                Intent().run {
                    action = "android.intent.action.VIEW"
                    data = Uri.parse(url)
                    startActivity(this)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        mAgentWeb?.let {
            if (!it.back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (mAgentWeb?.handleKeyEvent(keyCode, event)!!) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun inflateViewBinding() = ActivityContentBinding.inflate(layoutInflater)

}
