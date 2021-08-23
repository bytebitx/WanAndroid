package com.bbgo.common_base.ext

import com.tencent.mmkv.MMKV

object Prefs {

    private val mmkv = MMKV.defaultMMKV()!!

    fun putInt(key: String, value: Int) {
        mmkv.encode(key, value)
    }

    fun getInt(key: String, defaultValue: Int = -1): Int {
        return mmkv.decodeInt(key, defaultValue)
    }

    fun putIntAsync(key: String, value: Int) {
        mmkv.putInt(key, value).apply()
    }

    fun putString(key: String, value: String) {
        mmkv.encode(key, value)
    }

    fun putStringAsync(key: String, value: String) {
        mmkv.putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return mmkv.decodeString(key, defaultValue) ?: defaultValue
    }

    fun putDouble(key: String, value: Double) {
        mmkv.encode(key, value)
    }

    fun putDoubleAsync(key: String, value: Float) {
        mmkv.putFloat(key, value).apply()
    }

    fun getDouble(key: String, defaultValue: Double = 0.00): Double {
        return mmkv.decodeDouble(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        mmkv.encode(key, value)
    }

    fun putLongAsync(key: String, value: Long) {
        mmkv.putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return mmkv.decodeLong(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        mmkv.encode(key, value)
    }

    fun putBooleanAsync(key: String, value: Boolean) {
        mmkv.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return mmkv.decodeBool(key, defaultValue)
    }

    fun clear() {
        mmkv.clear().apply()
    }
}