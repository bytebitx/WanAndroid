package com.bytebitx.wanandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.bytebitx.base.BaseApplication
import com.bytebitx.base.base.BaseActivity
import com.bytebitx.base.util.AppUtil
import com.bytebitx.wanandroid.databinding.ActivitySplashBinding
import com.bytebitx.wanandroid.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private var textTypeface: Typeface?=null

    private var descTypeFace: Typeface?=null

    private var alphaAnimation: AlphaAnimation?=null

    init {
        textTypeface = Typeface.createFromAsset(BaseApplication.getContext().assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(BaseApplication.getContext().assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
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

    override fun inflateViewBinding() = ActivitySplashBinding.inflate(layoutInflater)
}