package com.bbgo.common_base.util

import android.graphics.Color
import com.bbgo.common_base.BaseApplication
import com.bbgo.common_base.R
import com.bbgo.common_base.ext.Mmkv

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
        return Mmkv.getBoolean("switch_noPhotoMode", false) //&& NetWorkUtil.isMobile(App.context)
    }

    /**
     * 获取是否开启显示首页置顶文章，true: 不显示  false: 显示
     */
    fun getIsShowTopArticle(): Boolean {
        return Mmkv.getBoolean("switch_show_top", false)
    }

    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = BaseApplication.getContext().resources.getColor(R.color.colorPrimary)
        val color = Mmkv.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        Mmkv.edit().putInt("color", color).apply()
    }

    /**
     * 获取是否开启导航栏上色
     */
    fun getNavBar(): Boolean {
        return Mmkv.getBoolean("nav_bar", false)
    }

    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        Mmkv.edit().putBoolean("switch_nightMode", flag).apply()
    }

    /**
     * 获取是否开启夜间模式
     */
    fun getIsNightMode(): Boolean {
        return Mmkv.getBoolean("switch_nightMode", false)
    }

    /**
     * 获取是否开启自动切换夜间模式
     */
    fun getIsAutoNightMode(): Boolean {
        return Mmkv.getBoolean("auto_nightMode", false)
    }

    fun getNightStartHour(): String {
        return Mmkv.getString("night_startHour", "22")!!
    }

    fun setNightStartHour(nightStartHour: String) {
        Mmkv.edit().putString("night_startHour", nightStartHour).apply()
    }

    fun getNightStartMinute(): String {
        return Mmkv.getString("night_startMinute", "00")!!
    }

    fun setNightStartMinute(nightStartMinute: String) {
        Mmkv.edit().putString("night_startMinute", nightStartMinute).apply()
    }

    fun getDayStartHour(): String {
        return Mmkv.getString("day_startHour", "06")!!
    }

    fun setDayStartHour(dayStartHour: String) {
        Mmkv.edit().putString("day_startHour", dayStartHour).apply()
    }

    fun getDayStartMinute(): String {
        return Mmkv.getString("day_startMinute", "00")!!
    }

    fun setDayStartMinute(dayStartMinute: String) {
        Mmkv.edit().putString("day_startMinute", dayStartMinute).apply()
    }
}