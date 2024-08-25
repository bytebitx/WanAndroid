package com.bytebitx.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bytebitx.base.base.BaseActivity
import com.bytebitx.base.bus.BusKey
import com.bytebitx.base.bus.LiveDataBus
import com.bytebitx.base.constants.Constants
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.ext.observe
import com.bytebitx.base.ext.showToast
import com.bytebitx.base.util.AppUtil
import com.bytebitx.base.util.DialogUtil
import com.bytebitx.login.R
import com.bytebitx.login.bean.LoginData
import com.bytebitx.login.databinding.ActivityLoginBinding
import com.bytebitx.login.viewmodel.RegisterLoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 2021/5/21 11:31 上午
 *  description: todo
 */
@Route(path = RouterPath.LoginRegister.PAGE_LOGIN)
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(), View.OnClickListener {

    @Inject
    lateinit var registerLoginViewModel: RegisterLoginViewModel

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }
    @Autowired
    lateinit var routerPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        observe()
    }

    private fun initView() {
        binding.actionBar.apply {
            tvTitle.text = getString(R.string.login)
            setSupportActionBar(binding.actionBar.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.btnLogin.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)

        ARouter.getInstance().inject(this)
    }

    private fun observe() {
        observe(registerLoginViewModel.registerLoginLiveData, ::handleRegister)
    }

    private fun handleRegister(resource: Resource<LoginData>) {
        when (resource) {
            is Resource.Loading -> {
                mDialog.show()
            }
            is Resource.Error -> {
                mDialog.dismiss()
                showToast(resource.exception.toString())
            }
            is Resource.Success -> {
                mDialog.dismiss()
                AppUtil.isLogin = true
                ARouter.getInstance().build(routerPath).navigation()
                LiveDataBus.get().with(BusKey.LOGIN_SUCCESS).value = Any()
                finish()
            }
        }
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            binding.btnLogin.id -> {
                if (binding.etUsername.text.toString().isEmpty()) {
                    showToast(getString(R.string.username_not_empty))
                    return
                }
                if (binding.etPassword.text.toString().isEmpty()) {
                    showToast(getString(R.string.password_not_empty))
                    return
                }
                registerLoginViewModel.login(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
            binding.tvSignUp.id -> {
                Intent(this, RegisterActivity::class.java).apply {
                    putExtra(Constants.ROUTER_PATH, routerPath)
                    startActivity(this)
                }
            }
            else -> {

            }
        }
    }

    override fun inflateViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

}