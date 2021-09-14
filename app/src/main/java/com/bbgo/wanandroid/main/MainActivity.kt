package com.bbgo.wanandroid.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.bus.BusKey
import com.bbgo.common_base.bus.LiveDataBus
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.event.ScrollEvent
import com.bbgo.common_base.ext.*
import com.bbgo.common_base.util.AppUtil
import com.bbgo.common_base.util.DialogUtil
import com.bbgo.common_base.util.SettingUtil
import com.bbgo.common_service.login.LoginOutService
import com.bbgo.wanandroid.R
import com.bbgo.wanandroid.databinding.ActivityMainBinding
import com.bbgo.wanandroid.databinding.NavHeaderMainBinding
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.Main.PAGE_MAIN)
@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderBinding: NavHeaderMainBinding

    @Autowired(name = RouterPath.LoginRegister.SERVICE_LOGOUT)
    lateinit var loginOutService: LoginOutService

    //默认为0
    private var mIndex = 0
    //退出时间
    private var mExitTime: Long = 0

    private var homeFragment: BaseFragment? = null
    private var weChatFragment: BaseFragment? = null
    private var sysFragment: BaseFragment? = null
    private var squareFragment: BaseFragment? = null
    private var projectFragment: BaseFragment? = null

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        navHeaderBinding = NavHeaderMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.addHeaderView(navHeaderBinding.root)
        binding.bottomNavigation.selectedItemId = mIndex
        binding.bottomNavigation.labelVisibilityMode = LABEL_VISIBILITY_LABELED
        switchFragment(mIndex)
        initView()
        ARouter.getInstance().inject(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val userId = intent.getStringExtra("userId")
        val userName = intent.getStringExtra("userName")
        if (userId.isNullOrEmpty()) return
        navHeaderBinding.userIdLayout.visibility = View.VISIBLE
        navHeaderBinding.tvUserId.text = userId
        navHeaderBinding.tvUsername.text = userName
        binding.navView.menu.findItem(R.id.nav_logout).title = getString(R.string.nav_logout)
        AppUtil.isLogin = true
    }

    override fun observeViewModel() {
        observe(mainViewModel.logOutLiveData, ::handleLogOut)
    }

    private fun handleLogOut(status: Resource<String>) {
        when (status) {
            is Resource.Loading -> {
                mDialog.show()
            }
            is Resource.DataError -> {
                mDialog.dismiss()
                status.errorMsg?.let { showToast(it) }
            }
            else -> {
                mDialog.dismiss()
                showToast(getString(R.string.logout_success))
                navHeaderBinding.tvUsername.text = getString(R.string.go_login)
                navHeaderBinding.userIdLayout.visibility = View.GONE
                navHeaderBinding.tvUserGrade.text = getString(R.string.nav_line_2)
                navHeaderBinding.tvUserRank.text = getString(R.string.nav_line_2)

                Prefs.clear()
                binding.navView.menu.findItem(R.id.nav_logout).title = getString(R.string.login)
                AppUtil.isLogin = false
            }
        }
    }

    private fun initView() {
        binding.actionBar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this.toolbar)
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home ->
                    switchFragment(Constants.FragmentIndex.HOME_INDEX)
                R.id.action_wechat ->
                    switchFragment(Constants.FragmentIndex.WECHAT_INDEX)
                R.id.action_system ->
                    switchFragment(Constants.FragmentIndex.SYS_INDEX)
                R.id.action_square ->
                    switchFragment(Constants.FragmentIndex.SQUARE_INDEX)
                R.id.action_project ->
                    switchFragment(Constants.FragmentIndex.PROJECT_INDEX)
            }
            true
        }

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.actionBar.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        setUserInfo()

        navHeaderBinding.root.setOnClickListener {
            if (AppUtil.isLogin) {
                return@setOnClickListener
            }
            ARouter.getInstance().build(RouterPath.LoginRegister.PAGE_LOGIN).navigation()
        }

        binding.floatingBtn.setOnClickListener(onFABClickListener)

    }

    private fun setUserInfo() {
        var userName = Prefs.getString(Constants.USER_NAME)
        if (userName.isEmpty()) {
            userName = getString(R.string.go_login)
        }
        AppUtil.isLogin = true
        val userId = Prefs.getString(Constants.USER_ID)
        navHeaderBinding.userIdLayout.visibility = View.VISIBLE
        navHeaderBinding.tvUserId.text = userId
        navHeaderBinding.tvUsername.text = userName

        navHeaderBinding.root.setOnClickListener {
            if (userName.isEmpty()) {
                ARouter.getInstance().build(RouterPath.LoginRegister.PAGE_LOGIN).navigation()
                binding.drawerLayout.closeDrawers()
            }
        }
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        when(position) {
            Constants.FragmentIndex.HOME_INDEX ->
                homeFragment?.let {
                    transaction.show(it)
                } ?: kotlin.run {
                    ARouter.getInstance().build(RouterPath.Home.PAGE_HOME).navigation()
                        ?.let {
                            homeFragment = it as BaseFragment
                            homeFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.app_name)
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }
            Constants.FragmentIndex.WECHAT_INDEX ->
                weChatFragment?.let { transaction.show(it) } ?: kotlin.run {
                    ARouter.getInstance().build(RouterPath.WeChat.PAGE_WECHAT).navigation()
                        ?.let {
                            weChatFragment = it as BaseFragment
                            weChatFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.wechat)
                                weChatFragment = it
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }

            Constants.FragmentIndex.SYS_INDEX ->
                sysFragment?.let { transaction.show(it) } ?: kotlin.run {
                    ARouter.getInstance().build(RouterPath.Sys.PAGE_SYS).navigation()
                        ?.let {
                            sysFragment = it as BaseFragment
                            sysFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.knowledge_system)
                                sysFragment = it
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }
            Constants.FragmentIndex.SQUARE_INDEX ->
                squareFragment?.let { transaction.show(it) } ?: kotlin.run {
                    ARouter.getInstance().build(RouterPath.Square.PAGE_SQUARE).navigation()
                        ?.let {
                            squareFragment = it as BaseFragment
                            squareFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.square)
                                squareFragment = it
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }
            Constants.FragmentIndex.PROJECT_INDEX ->
                projectFragment?.let { transaction.show(it) } ?: kotlin.run {
                    ARouter.getInstance().build(RouterPath.Project.PAGE_PROJECT).navigation()
                        ?.let {
                            projectFragment = it as BaseFragment
                            projectFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.project)
                                projectFragment = it
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }
        }
        mIndex = position
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        weChatFragment?.let { transaction.hide(it) }
        sysFragment?.let { transaction.hide(it) }
        squareFragment?.let { transaction.hide(it) }
        projectFragment?.let { transaction.hide(it) }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when(item.itemId) {
            R.id.nav_collect -> {
                ARouter.getInstance().build(RouterPath.Compose.PAGE_COMPOSE)
                    .navigation()
            }
            R.id.nav_night_mode -> {
                if (SettingUtil.getIsNightMode()) {
                    SettingUtil.setIsNightMode(false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    SettingUtil.setIsNightMode(true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
                recreate()
            }
            R.id.nav_setting -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    AppUtil.requestIgnoreBatteryOptimizations(this)
                }
            }
            R.id.nav_logout -> {
                if (AppUtil.isLogin) {
                    mainViewModel.logOut(loginOutService)
                } else {
                    ARouter.getInstance().build(RouterPath.LoginRegister.PAGE_LOGIN).navigation()
                    binding.drawerLayout.closeDrawers()
                }

            }
        }
        binding.drawerLayout.closeDrawers()
        transaction.commitAllowingStateLoss()
        return false
    }

    override fun recreate() {
        kotlin.runCatching {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            homeFragment?.let {
                fragmentTransaction.remove(it)
            }
            squareFragment?.let {
                fragmentTransaction.remove(it)
            }
            sysFragment?.let {
                fragmentTransaction.remove(it)
            }
            projectFragment?.let {
                fragmentTransaction.remove(it)
            }
            weChatFragment?.let {
                fragmentTransaction.remove(it)
            }
            fragmentTransaction.commitAllowingStateLoss()
        }.onFailure {
            it.printStackTrace()
        }
        super.recreate()
    }

    /**
     * FAB 监听
     */
    private val onFABClickListener = View.OnClickListener {
        when (mIndex) {
            Constants.FragmentIndex.HOME_INDEX -> {
                LiveDataBus.get().with(BusKey.SCROLL_TOP).value = ScrollEvent(0)
            }
            Constants.FragmentIndex.WECHAT_INDEX -> {
                LiveDataBus.get().with(BusKey.SCROLL_TOP).value = ScrollEvent(1)
            }
            Constants.FragmentIndex.SYS_INDEX -> {
                LiveDataBus.get().with(BusKey.SCROLL_TOP).value = ScrollEvent(2)
            }
            Constants.FragmentIndex.SQUARE_INDEX -> {
                LiveDataBus.get().with(BusKey.SCROLL_TOP).value = ScrollEvent(3)
            }
            Constants.FragmentIndex.PROJECT_INDEX -> {
                LiveDataBus.get().with(BusKey.SCROLL_TOP).value = ScrollEvent(4)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currTabIndex", mIndex)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
//                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}