package com.bbgo.common_base.base

import android.Manifest
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bbgo.common_base.R
import com.bbgo.common_base.util.SettingUtil
import com.bbgo.common_base.util.StatusBarUtil
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 *  author: wangyb
 *  date: 2021/5/20 10:03 上午
 *  description: todo
 */
open class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    companion object {
        const val PERMISSION_REQUEST_CODE = 1000
    }

    /**
     * theme color
     */
    private var themeColor: Int = SettingUtil.getColor()

    private val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    open fun requestPermission() {
        EasyPermissions.requestPermissions(this,
            "该Demo需要获取位置权限，以便于能正常的使用该Demo",
            PERMISSION_REQUEST_CODE, *perms)
    }

    @AfterPermissionGranted(PERMISSION_REQUEST_CODE)
    private fun methodRequiresPermission() {
        if (!EasyPermissions.hasPermissions(this, *perms)) {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                .setTitle("权限请求")
                .setRationale("未获取位置权限，此应用可能无法正常运行，请打开应用程序设置允许使用位置权限")
                .build().show()
        } else {
            requestPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
        }
    }

}