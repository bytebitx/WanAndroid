package com.bytebitx.base.util

import android.graphics.Color
import com.bytebitx.base.BaseApplication
import com.bytebitx.base.R
import com.bytebitx.base.ext.Prefs

/**
 *  author: wangyb
 *  date: 2021/5/24 2:19 下午
 *  description: todo
 */
object SettingUtil {

    /**
     * 获取是否开启无图模式
     */
    fun getIsNoPhotoMode(): Boolean {
        return Prefs.getBoolean("switch_noPhotoMode", false) //&& NetWorkUtil.isMobile(App.context)
    }

    /**
     * 获取是否开启显示首页置顶文章，true: 不显示  false: 显示
     */
    fun getIsShowTopArticle(): Boolean {
        return Prefs.getBoolean("switch_show_top", false)
    }

    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = BaseApplication.getContext().resources.getColor(R.color.colorPrimary)
        val color = Prefs.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        Prefs.putInt("color", color)
    }

    /**
     * 获取是否开启导航栏上色
     */
    fun getNavBar(): Boolean {
        return Prefs.getBoolean("nav_bar", false)
    }

    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        Prefs.putBoolean("switch_nightMode", flag)
    }

    /**
     * 获取是否开启夜间模式
     */
    fun getIsNightMode(): Boolean {
        return Prefs.getBoolean("switch_nightMode", false)
    }

    /**
     * 获取是否开启自动切换夜间模式
     */
    fun getIsAutoNightMode(): Boolean {
        return Prefs.getBoolean("auto_nightMode", false)
    }

    fun getNightStartHour(): String {
        return Prefs.getString("night_startHour", "22")
    }

    fun setNightStartHour(nightStartHour: String) {
        Prefs.putString("night_startHour", nightStartHour)
    }

    fun getNightStartMinute(): String {
        return Prefs.getString("night_startMinute", "00")
    }

    fun setNightStartMinute(nightStartMinute: String) {
        Prefs.putString("night_startMinute", nightStartMinute)
    }

    fun getDayStartHour(): String {
        return Prefs.getString("day_startHour", "06")
    }

    fun setDayStartHour(dayStartHour: String) {
        Prefs.putString("day_startHour", dayStartHour)
    }

    fun getDayStartMinute(): String {
        return Prefs.getString("day_startMinute", "00")
    }

    fun setDayStartMinute(dayStartMinute: String) {
        Prefs.putString("day_startMinute", dayStartMinute)
    }
}