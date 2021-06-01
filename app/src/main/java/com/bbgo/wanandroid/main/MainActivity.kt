package com.bbgo.wanandroid.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Mmkv
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.util.DialogUtil
import com.bbgo.common_service.login.LoginOutService
import com.bbgo.common_service.test.TestService
import com.bbgo.common_service.test.bean.TestContentBean
import com.bbgo.wanandroid.R
import com.bbgo.wanandroid.databinding.ActivityMainBinding
import com.bbgo.wanandroid.databinding.NavHeaderMainBinding
import com.bbgo.wanandroid.square.ui.SquareFragment
import com.bbgo.wanandroid.wechat.ui.WeChatFragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.google.android.material.navigation.NavigationView

@Route(path = Constants.NAVIGATION_TO_MAIN)
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderBinding: NavHeaderMainBinding
    @Autowired(name = Constants.SERVICE_LOGOUT)
    lateinit var loginOutService: LoginOutService
    @Autowired(name = "/app/TestService")
    lateinit var testBeanService: TestService

    //默认为0
    private var mIndex = 0
    //退出时间
    private var mExitTime: Long = 0

    private var homeFragment: BaseFragment? = null
    private var squareFragment: SquareFragment? = null
    private var weChatFragment: WeChatFragment? = null

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }

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

    override fun observeViewModel() {
//        observe(registerLoginViewModel.logOutLiveData, ::handleLogOut)
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
                R.id.action_square ->
                    switchFragment(1)
                R.id.action_wechat ->
                    switchFragment(2)
//                R.id.action_system ->
//                    switchFragment(3)
//                R.id.action_project ->
//                    switchFragment(4)
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
        var userName = Mmkv.decodeString(Constants.USER_NAME)
        if (userName.isNullOrEmpty()) {
            userName = getString(R.string.go_login)
        }
        navHeaderBinding.tvUsername.text = userName
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        when(position) {
            0 ->
                /*homeFragment?.let {
                    transaction.show(it)
                } ?: HomeFragment.getInstance().let {
                    binding.actionBar.tvTitle.text = getString(R.string.app_name)
                    homeFragment = it
                    transaction.add(R.id.container, it, null)
                }*/
                homeFragment?.let {
                    transaction.show(it)
                } ?: kotlin.run {
                    homeFragment = ARouter.getInstance().build(Constants.NAVIGATION_TO_HOME_FRG).navigation() as BaseFragment
                    homeFragment?.let {
                        binding.actionBar.tvTitle.text = getString(R.string.app_name)
                        transaction.add(R.id.container, it, null)
                    }
                }
            1 ->
                /*squareFragment?.let { transaction.show(it) } ?: SquareFragment().let {
                    binding.actionBar.tvTitle.text = getString(R.string.square)
                    squareFragment = it
                    transaction.add(R.id.container, it, null)
                }*/
                squareFragment?.let { transaction.show(it) } ?: kotlin.run {
                    squareFragment = ARouter.getInstance().build(Constants.NAVIGATION_TO_SQUARE_FRG).navigation() as SquareFragment
                    squareFragment?.let {
                        binding.actionBar.tvTitle.text = getString(R.string.square)
                        squareFragment = it
                        transaction.add(R.id.container, it, null)
                    }
                }
            2 ->
                /*weChatFragment?.let { transaction.show(it) } ?: WeChatFragment().let {
                    binding.actionBar.tvTitle.text = getString(R.string.wechat)
                    weChatFragment = it
                    transaction.add(R.id.container, it, null)
                }*/
                weChatFragment?.let { transaction.show(it) } ?: kotlin.run {
                    weChatFragment = ARouter.getInstance().build(Constants.NAVIGATION_TO_WECHAT_FRG).navigation() as WeChatFragment
                    weChatFragment?.let {
                        binding.actionBar.tvTitle.text = getString(R.string.wechat)
                        weChatFragment = it
                        transaction.add(R.id.container, it, null)
                    }
                }
            3 ->
                weChatFragment?.let { transaction.show(it) } ?: kotlin.run {
                    weChatFragment = ARouter.getInstance().build(Constants.NAVIGATION_TO_WECHAT_FRG).navigation() as WeChatFragment
                    weChatFragment?.let {
                        binding.actionBar.tvTitle.text = getString(R.string.wechat)
                        weChatFragment = it
                        transaction.add(R.id.container, it, null)
                    }
                }
            4 ->
                weChatFragment?.let { transaction.show(it) } ?: kotlin.run {
                    weChatFragment = ARouter.getInstance().build(Constants.NAVIGATION_TO_WECHAT_FRG).navigation() as WeChatFragment
                    weChatFragment?.let {
                        binding.actionBar.tvTitle.text = getString(R.string.wechat)
                        weChatFragment = it
                        transaction.add(R.id.container, it, null)
                    }
                }
        }
        mIndex = position
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        squareFragment?.let { transaction.hide(it) }
        weChatFragment?.let { transaction.hide(it) }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when(item.itemId) {
            R.id.nav_score -> {
//                val intent = Intent(this, GankActivity::class.java)
//                startActivity(intent)
            }
            R.id.nav_collect -> {
//                val intent = Intent(this, QsbkActivity::class.java)
//                startActivity(intent)
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
                testBeanService.insertTest(TestContentBean(1, "211"))
                loginOutService.logOut()

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