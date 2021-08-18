package com.bbgo.wanandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.bbgo.common_base.BaseApplication
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.common_base.notchlib.NotchScreenManager
import com.bbgo.common_base.util.AppUtil
import com.bbgo.wanandroid.databinding.ActivitySplashBinding
import com.bbgo.wanandroid.main.MainActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    private var textTypeface: Typeface?=null

    private var descTypeFace: Typeface?=null

    private var alphaAnimation: AlphaAnimation?=null

    init {
        textTypeface = Typeface.createFromAsset(BaseApplication.getContext().assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(BaseApplication.getContext().assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotchScreenManager.getInstance().setDisplayInNotch(this)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

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

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.tvAppName.typeface = textTypeface
        binding.tvSplashDesc.typeface = descTypeFace
        binding.tvVersionName.text = "v${AppUtil.appVersionName}"

        //渐变展示启动屏
        alphaAnimation= AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectTo()
            }

            override fun onAnimationRepeat(animation: Animation) {
            }

            override fun onAnimationStart(animation: Animation) {
            }
        })
        binding.ivLogo.startAnimation(alphaAnimation)
    }

    private fun redirectTo() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}