package com.bbgo.wanandroid.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.wanandroid.R
import com.bbgo.wanandroid.bean.LoginData
import com.bbgo.wanandroid.databinding.ActivityRegisterBinding
import com.bbgo.wanandroid.login.viewmodel.RegisterLoginViewModel
import com.bbgo.wanandroid.main.MainActivity
import com.bbgo.wanandroid.util.InjectorUtil

/**
 *  author: wangyb
 *  date: 2021/5/27 2:47 下午
 *  description: todo
 */
class RegisterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    private val registerLoginViewModel by viewModels<RegisterLoginViewModel> {
        InjectorUtil.getLoginViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.actionBar.apply {
            tvTitle.text = getString(R.string.register)
            setSupportActionBar(binding.actionBar.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding.btnRegister.setOnClickListener(this)
        binding.tvSignIn.setOnClickListener(this)
    }

    override fun observeViewModel() {
        observe(registerLoginViewModel.registerLoginLiveData, ::handleRegister)
    }

    private fun handleRegister(resource: Resource<LoginData>) {
        when (resource) {
            is Resource.Loading -> {

            }
            is Resource.DataError -> {
                resource.errorMsg?.let { showToast(it) }
            }
            is Resource.Success -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            binding.btnRegister.id -> {
                if (binding.etUsername.text.toString().isEmpty()) {
                    showToast(getString(R.string.username_not_empty))
                    return
                }
                if (binding.etPassword.text.toString().isEmpty()) {
                    showToast(getString(R.string.password_not_empty))
                    return
                }
                if (binding.etPassword2.text.toString().isEmpty()) {
                    showToast(getString(R.string.confirm_password_not_empty))
                    return
                }
                if (binding.etPassword.text.toString() != binding.etPassword2.text.toString()) {
                    showToast(getString(R.string.password_cannot_match))
                    return
                }
                registerLoginViewModel.register(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etPassword2.text.toString()
                )
            }
            binding.tvSignIn.id -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            else -> {

            }
        }
    }
}