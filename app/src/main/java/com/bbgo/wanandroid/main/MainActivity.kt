package com.bbgo.wanandroid.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Prefs
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.util.DialogUtil
import com.bbgo.common_service.login.LoginOutService
import com.bbgo.wanandroid.R
import com.bbgo.wanandroid.bean.UserInfo
import com.bbgo.wanandroid.databinding.ActivityMainBinding
import com.bbgo.wanandroid.databinding.NavHeaderMainBinding
import com.bbgo.wanandroid.util.InjectorUtil
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Route(path = Constants.NAVIGATION_TO_MAIN)
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderBinding: NavHeaderMainBinding
    @Autowired(name = Constants.SERVICE_LOGOUT)
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

    private val mainViewModel by viewModels<MainViewModel> { InjectorUtil.getHomeViewModelFactory() }

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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mainViewModel.getUserInfo()
    }

    override fun observeViewModel() {
        mainViewModel.getUserInfo()
        observe(mainViewModel.userInfoLiveData, ::handleUserInfo)
    }

    private fun handleUserInfo(status: Resource<UserInfo>) {
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
                navHeaderBinding.userIdLayout.visibility = View.VISIBLE
                navHeaderBinding.tvUserId.text = status.data?.userId.toString()
                navHeaderBinding.tvUserGrade.text = status.data?.coinCount.toString()
                navHeaderBinding.tvUserRank.text = status.data?.rank.toString()
                navHeaderBinding.tvUsername.text = Prefs.getString(Constants.USER_NAME)
            }
        }
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
                    switchFragment(0)
                R.id.action_wechat ->
                    switchFragment(1)
                R.id.action_system ->
                    switchFragment(2)
                R.id.action_square ->
                    switchFragment(3)
                R.id.action_project ->
                    switchFragment(4)
            }
            true
        }

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.actionBar.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        setUserInfo()

    }

    private fun setUserInfo() {
        var userName = Prefs.getString(Constants.USER_NAME)
        if (userName.isEmpty()) {
            userName = getString(R.string.go_login)
        }
        navHeaderBinding.tvUsername.text = userName

        navHeaderBinding.root.setOnClickListener {
            if (userName.isEmpty()) {
                ARouter.getInstance().build(Constants.NAVIGATION_TO_LOGIN).navigation()
                binding.drawerLayout.closeDrawers()
            }
        }
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        when(position) {
            0 ->
                homeFragment?.let {
                    transaction.show(it)
                } ?: kotlin.run {
                    ARouter.getInstance().build(Constants.NAVIGATION_TO_HOME_FRG).navigation()
                        ?.let {
                            homeFragment = it as BaseFragment
                            homeFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.app_name)
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }
            1 ->
                weChatFragment?.let { transaction.show(it) } ?: kotlin.run {
                    ARouter.getInstance().build(Constants.NAVIGATION_TO_WECHAT_FRG).navigation()
                        ?.let {
                            weChatFragment = it as BaseFragment
                            weChatFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.wechat)
                                weChatFragment = it
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }

            2 ->
                sysFragment?.let { transaction.show(it) } ?: kotlin.run {
                    ARouter.getInstance().build(Constants.NAVIGATION_TO_SYS_FRG).navigation()
                        ?.let {
                            sysFragment = it as BaseFragment
                            sysFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.knowledge_system)
                                sysFragment = it
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }
            3 ->
                squareFragment?.let { transaction.show(it) } ?: kotlin.run {
                    ARouter.getInstance().build(Constants.NAVIGATION_TO_SQUARE_FRG).navigation()
                        ?.let {
                            squareFragment = it as BaseFragment
                            squareFragment?.let {
                                binding.actionBar.tvTitle.text = getString(R.string.square)
                                squareFragment = it
                                transaction.add(R.id.container, it, null)
                            }
                        }
                }
            4 ->
                projectFragment?.let { transaction.show(it) } ?: kotlin.run {
                    ARouter.getInstance().build(Constants.NAVIGATION_TO_PROJECT_FRG).navigation()
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
            R.id.nav_score -> {
//                val intent = Intent(this, GankActivity::class.java)
//                startActivity(intent)
            }
            R.id.nav_collect -> {
                ARouter.getInstance().build(Constants.NAVIGATION_TO_COLLECT)
                    .navigation()
            }
            R.id.nav_share -> {
//                val intent = Intent(this, WanAndroidActivity::class.java)
//                startActivity(intent)
            }
            R.id.nav_todo -> {
//                val intent = Intent(this, DoubanActivity::class.java)
//                startActivity(intent)
            }
            R.id.nav_setting -> {
//                homeFragment?.let { transaction.show(it) } ?: HomeFragment.getInstance().let {
//                    homeFragment = it
//                    transaction.add(R.id.container, it, null)
//                }
            }
            R.id.nav_logout -> {
                lifecycleScope.launch {
                    loginOutService.logOut()
                        .catch {

                        }
                        .collectLatest {
                            handleLogOut(Resource.Success(it))
                        }
                }
//                registerLoginViewModel.logOut()
            }
        }
        binding.drawerLayout.closeDrawers()
        transaction.commitAllowingStateLoss()
        return false
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