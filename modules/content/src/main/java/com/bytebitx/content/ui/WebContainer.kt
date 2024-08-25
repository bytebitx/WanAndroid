package com.bytebitx.content.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.bytebitx.base.util.ColorUtil
import com.bytebitx.base.util.SettingUtil
import com.bytebitx.content.R

/**
 * @author chenxz
 * @date 2019/11/24
 * @desc WebContainer
 */
class WebContainer @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CoordinatorLayout(context, attrs, defStyleAttr) {

    private var mDarkTheme: Boolean = false

    private var mMaskColor = Color.TRANSPARENT

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (mDarkTheme) {
            canvas.drawColor(mMaskColor)
        }
    }

}
