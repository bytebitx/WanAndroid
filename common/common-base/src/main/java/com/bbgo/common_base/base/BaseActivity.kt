package com.bbgo.common_base.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bbgo.common_base.R
import com.bbgo.common_base.util.SettingUtil
import com.bbgo.common_base.util.StatusBarUtil
import com.bbgo.library_statusbar.NotchScreenManager

/**
 *  author: wangyb
 *  date: 2021/5/20 10:03 上午
 *  description: todo
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * theme color
     */
    private var themeColor: Int = SettingUtil.getColor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotchScreenManager.getInstance().setDisplayInNotch(this)
        initColor()
        observeViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    open fun observeViewModel() {
    }

    open fun initColor() {
        themeColor = if (!SettingUtil.getIsNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this, themeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(themeColor))
        }
    }

}