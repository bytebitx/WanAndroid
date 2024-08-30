package com.bytebitx.media.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bytebitx.base.base.BaseActivity
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.media.databinding.ActivityVideoBinding
import com.bytebitx.media.widget.AndroidMediaController

@Route(path = RouterPath.Media.PAGE_VIDEO)
class VideoActivity : BaseActivity<ActivityVideoBinding>() {

    private var mMediaController: AndroidMediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        mMediaController = AndroidMediaController(this, false)
        binding.videoView.setMediaController(mMediaController)
//        binding.videoView.setVideoPath("http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4")
//        binding.videoView.setVideoPath("https://media.w3.org/2010/05/sintel/trailer.mp4")
        binding.videoView.setVideoPath("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4")
        binding.videoView.start()
    }

    override fun initObserver() {
    }

    override fun initData() {
    }

}