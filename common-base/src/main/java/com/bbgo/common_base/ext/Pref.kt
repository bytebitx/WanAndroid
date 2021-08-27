package com.bbgo.common_base.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bbgo.common_base.BaseApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object Pref {

    /**
     * dataStore 存入数据默认就是异步，没有同步方法
     * 取数据异步通过Flow实现
     */
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    /**
     * 写入数据默认就是异步
     */
    suspend fun put(key: String, value: Any) {
        when(value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
        }
    }

    private suspend fun putInt(key: String, value: Int) {
        BaseApplication.getContext().dataStore.edit { settings ->
            settings[intPreferencesKey(key)] = value
        }
    }

    private suspend fun putLong(key: String, value: Long) {
        BaseApplication.getContext().dataStore.edit { settings ->
            settings[longPreferencesKey(key)] = value
        }
    }

    private suspend fun putFloat(key: String, value: Float) {
        BaseApplication.getContext().dataStore.edit { settings ->
            settings[floatPreferencesKey(key)] = value
        }
    }

    private suspend fun putString(key: String, value: String) {
        BaseApplication.getContext().dataStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value
        }
    }

    private suspend fun putBoolean(key: String, value: Boolean) {
        BaseApplication.getContext().dataStore.edit { settings ->
            settings[booleanPreferencesKey(key)] = value
        }
    }

    /************************************************************************************/
    /**
     * 获取数据是同步还是异步由启动协程决定
     */

    fun getInt(key: String, defaultValue: Int) : Flow<Int> {
        return BaseApplication.getContext().dataStore.data
            .map { preferences ->
                preferences[intPreferencesKey(key)] ?: defaultValue
            }
    }

    fun getLong(key: String, defaultValue: Long) : Flow<Long> {
        return BaseApplication.getContext().dataStore.data
            .map { preferences ->
                preferences[longPreferencesKey(key)] ?: defaultValue
            }
    }

    fun getFloat(key: String, defaultValue: Float) : Flow<Float> {
        return BaseApplication.getContext().dataStore.data
            .map { preferences ->
                preferences[floatPreferencesKey(key)] ?: defaultValue
            }
    }

    fun getString(key: String, defaultValue: String) : Flow<String> {
        return BaseApplication.getContext().dataStore.data
            .map { preferences ->
                preferences[stringPreferencesKey(key)] ?: defaultValue
            }
    }

    fun getBoolean(key: String, defaultValue: Boolean) : Flow<Boolean> {
        return BaseApplication.getContext().dataStore.data
            .map { preferences ->
                preferences[booleanPreferencesKey(key)] ?: defaultValue
            }
    }
}