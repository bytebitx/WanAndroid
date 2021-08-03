package com.bbgo.wanandroid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.lifecycleScope
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.logD
import com.bbgo.wanandroid.databinding.ActivitySplashBinding
import com.bbgo.wanandroid.main.MainActivity
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import java.lang.NullPointerException
import java.lang.NumberFormatException
import kotlin.coroutines.EmptyCoroutineContext

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            delay(5000)
            logD("SplashActivity", "this is ${Thread.currentThread().name}")
            finish()
        }

        /*lifecycleScope.launch {
            val time = System.currentTimeMillis()
            val task1 = withContext(Dispatchers.IO) {
                delay(2000)
                logD("1.执行task1.... [当前线程为：${Thread.currentThread().name}]")
                "one"
            }
            val task2 = withContext(Dispatchers.IO) {
                delay(1000)
                logD("2.执行task2.... [当前线程为：${Thread.currentThread().name}]")
                "two"
            }
            logD("task1 = $task1  , task2 = $task2 , 耗时 ${System.currentTimeMillis()-time} ms  [当前线程为：${Thread.currentThread().name}]")

        }*/

        /*lifecycleScope.launch {
            val time = System.currentTimeMillis()

            val task1 = async(Dispatchers.IO) {
                delay(2000)
                logD("1.执行task1.... [当前线程为：${Thread.currentThread().name}]")
                "async task one"
            }
            val task2 = async(Dispatchers.IO) {
                delay(1000)
                logD("2.执行task2.... [当前线程为：${Thread.currentThread().name}]")
                "async task two"
            }
            logD("task1 = ${task1.await()}  , task2 = ${task2.await()} , 耗时 ${System.currentTimeMillis()-time} ms  [当前线程为：${Thread.currentThread().name}]")

        }*/


    }

}