package com.bbgo.module_login.ui

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.bus.BusKey
import com.bbgo.common_base.bus.LiveDataBus
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.util.AppUtil
import com.bbgo.common_base.util.DialogUtil
import com.bbgo.module_login.R
import com.bbgo.module_login.bean.LoginData
import com.bbgo.module_login.databinding.ActivityLoginBinding
import com.bbgo.module_login.viewmodel.RegisterLoginViewModel
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

    override fun initView() {
        binding.actionBar.apply {
            tvTitle.text = getString(R.string.login)
            setSupportActionBar(binding.actionBar.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.btnLogin.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)

        ARouter.getInstance().inject(this)
    }

    override fun observe() {
        observe(registerLoginViewModel.registerLoginLiveData, ::handleRegister)
    }

    override fun initData() {
    }

    private fun handleRegister(resource: Resource<LoginData>) {
        when (resource) {
            is Resource.Loading -> {
                mDialog.show()
            }
            is Resource.DataError -> {
                mDialog.dismiss()
                resource.errorMsg?.let { showToast(it) }
            }
            is Resource.Success -> {
                mDialog.dismiss()
                if (resource.data == null) return
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

}