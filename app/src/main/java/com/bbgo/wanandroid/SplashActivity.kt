package com.bbgo.wanandroid

import android.content.Intent
import android.os.Bundle
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.ext.logD
import com.bbgo.wanandroid.databinding.ActivitySplashBinding
import com.bbgo.wanandroid.main.MainActivity
import kotlinx.coroutines.*

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val job by lazy { Job() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(job + Dispatchers.Main).launch {
            logD("SplashActivity", Thread.currentThread().name)
            delay(1000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            delay(3000)
            logD("SplashActivity", "this is ${Thread.currentThread().name}")
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}