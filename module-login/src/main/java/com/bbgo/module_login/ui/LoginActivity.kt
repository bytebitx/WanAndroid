package com.bbgo.module_login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.util.DialogUtil
import com.bbgo.module_login.R
import com.bbgo.module_login.bean.LoginData
import com.bbgo.module_login.databinding.ActivityLoginBinding
import com.bbgo.module_login.util.InjectorUtil
import com.bbgo.module_login.viewmodel.RegisterLoginViewModel

/**
 *  author: wangyb
 *  date: 2021/5/21 11:31 上午
 *  description: todo
 */
@Route(path = Constants.NAVIGATION_TO_LOGIN)
class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    private val registerLoginViewModel by viewModels<RegisterLoginViewModel> {
        InjectorUtil.getLoginViewModelFactory()
    }

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionBar.apply {
            tvTitle.text = getString(R.string.login)
            setSupportActionBar(binding.actionBar.toolbar)
        }

        binding.btnLogin.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)
    }

    override fun observeViewModel() {
        observe(registerLoginViewModel.registerLoginLiveData, ::handleRegister)
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
                ARouter.getInstance().build(Constants.NAVIGATION_TO_MAIN).navigation()
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
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            else -> {

            }
        }
    }
}