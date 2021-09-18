
package com.bbgo.common_base.util

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.bbgo.apt_annotation.InjectLogin
import com.bbgo.common_base.BaseApplication
import com.bbgo.common_base.ext.Prefs
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.logE
import com.bbgo.common_base.ext.logW
import java.io.File
import java.util.*


/**
 * 应用程序全局的通用工具类，功能比较单一，经常被复用的功能，应该封装到此工具类当中，从而给全局代码提供方面的操作。
 *
 * @author wangyb
 * @since 17/2/18
 */
object AppUtil {

    private var TAG = "AppUtil"

    /**
     * 获取当前应用程序的包名。
     *
     * @return 当前应用程序的包名。
     */
    val appPackage: String
        get() = BaseApplication.getContext().packageName

    /**
     * 获取当前应用程序的名称。
     * @return 当前应用程序的名称。
     */
    val appName: String
        get() = BaseApplication.getContext().resources.getString(BaseApplication.getContext().applicationInfo.labelRes)

    /**
     * 获取当前应用程序的版本名。
     * @return 当前应用程序的版本名。
     */
    val appVersionName: String
        get() = BaseApplication.getContext().packageManager.getPackageInfo(appPackage, 0).versionName

    /**
     * 获取当前应用程序的版本号。
     * @return 当前应用程序的版本号。
     */
    val appVersionCode: Long
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BaseApplication.getContext().packageManager.getPackageInfo(appPackage, 0).longVersionCode
        } else {
            BaseApplication.getContext().packageManager.getPackageInfo(appPackage, 0).versionCode.toLong()
        }

    /**
     * 获取开眼应用程序的版本名。
     * @return 开眼当前应用程序的版本名。
     */
    val eyepetizerVersionName: String
        get() = "6.3.01"

    /**
     * 获取开眼应用程序的版本号。
     * @return 开眼当前应用程序的版本号。
     */
    val eyepetizerVersionCode: Long
        get() = 6030012

    /**
     * 获取设备的的型号，如果无法获取到，则返回Unknown。
     * @return 设备型号。
     */
    val deviceModel: String
        get() {
            var deviceModel = Build.MODEL
            if (TextUtils.isEmpty(deviceModel)) {
                deviceModel = "unknown"
            }
            return deviceModel
        }

    /**
     * 获取设备的品牌，如果无法获取到，则返回Unknown。
     * @return 设备品牌，全部转换为小写格式。
     */
    val deviceBrand: String
        get() {
            var deviceBrand = Build.BRAND
            if (TextUtils.isEmpty(deviceBrand)) {
                deviceBrand = "unknown"
            }
            return deviceBrand.toLowerCase(Locale.getDefault())
        }

    private var deviceSerial: String? = null

    /**
     * 获取设备的序列号。如果无法获取到设备的序列号，则会生成一个随机的UUID来作为设备的序列号，UUID生成之后会存入缓存，
     * 下次获取设备序列号的时候会优先从缓存中读取。
     * @return 设备的序列号。
     */
    @SuppressLint("HardwareIds")
    fun getDeviceSerial(): String {
        if (deviceSerial == null) {
            var deviceId: String? = null
//            val appChannel = getApplicationMetaData("APP_CHANNEL")
            kotlin.runCatching {
                deviceId = Settings.Secure.getString(
                    BaseApplication.getContext().contentResolver,
                    Settings.Secure.ANDROID_ID
                )
                deviceId?.let {
                    if (it.length < 255) {
                        deviceSerial = deviceId
                        return deviceSerial.toString()
                    }
                }

            }.onFailure {
                logE(TAG, "get android_id with error", it)
            }

            var uuid = Prefs.getString("uuid", "")
            if (uuid.isNotEmpty()) {
                deviceSerial = uuid
                return deviceSerial.toString()
            }
            uuid = UUID.randomUUID().toString().replace("-", "").uppercase(Locale.getDefault())
            Prefs.putString("uuid", uuid)
            deviceSerial = uuid
            return deviceSerial.toString()
        } else {
            return deviceSerial.toString()
        }
    }

    /**
     * 获取资源文件中定义的字符串。
     *
     * @param resId
     * 字符串资源id
     * @return 字符串资源id对应的字符串内容。
     */
    fun getString(resId: Int): String {
        return BaseApplication.getContext().resources.getString(resId)
    }

    /**
     * 获取资源文件中定义的字符串。
     *
     * @param resId
     * 字符串资源id
     * @return 字符串资源id对应的字符串内容。
     */
    fun getDimension(resId: Int): Int {
        return BaseApplication.getContext().resources.getDimensionPixelOffset(resId)
    }

    /**
     * 获取指定资源名的资源id。
     *
     * @param name
     * 资源名
     * @param type
     * 资源类型
     * @return 指定资源名的资源id。
     */
    fun getResourceId(name: String, type: String): Int {
        return BaseApplication.getContext().resources.getIdentifier(name, type, appPackage)
    }

    /**
     * 获取AndroidManifest.xml文件中，<application>标签下的meta-data值。
     *
     * @param key
     *  <application>标签下的meta-data健
     */
    fun getApplicationMetaData(key: String): String? {
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = BaseApplication.getContext().packageManager.getApplicationInfo(
                appPackage, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            logW(TAG, e.message, e)
        }
        if (applicationInfo == null) return ""
        return applicationInfo.metaData.getString(key)
    }

    /**
     * 判断某个应用是否安装。
     * @param packageName
     * 要检查是否安装的应用包名
     * @return 安装返回true，否则返回false。
     */
    fun isInstalled(packageName: String): Boolean {
        val packageInfo: PackageInfo? = try {
            BaseApplication.getContext().packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
        return packageInfo != null
    }

    /**
     * 获取当前应用程序的图标。
     */
    fun getAppIcon(): Drawable {
        val packageManager = BaseApplication.getContext().packageManager
        val applicationInfo = packageManager.getApplicationInfo(appPackage, 0)
        return packageManager.getApplicationIcon(applicationInfo)
    }

    /**
     * 判断手机是否安装了QQ。
     */
    fun isQQInstalled() = isInstalled("com.tencent.mobileqq")

    /**
     * 判断手机是否安装了微信。
     */
    fun isWechatInstalled() = isInstalled("com.tencent.mm")

    /**
     * 判断手机是否安装了微博。
     * */
    fun isWeiboInstalled() = isInstalled("com.sina.weibo")

    @InjectLogin
    var isLogin = false

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        var isIgnoring = false
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager?
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(appPackage)
        }
        return isIgnoring
    }

    /**
     * 将应用加入后台白名单，提高应用存活率
     */
    @SuppressLint("BatteryLife")
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestIgnoreBatteryOptimizations(context: Context) {
        if (isIgnoringBatteryOptimizations(context)) {
            return
        }
        kotlin.runCatching {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:$appPackage")
            context.startActivity(intent)
        }.onFailure {
            it.printStackTrace()
        }
    }

    fun goToSetting(context: Context) {
        if (Build.BRAND == null) {
            return
        }
        when(Build.BRAND.lowercase(Locale.getDefault())) {
            "realme" -> goOPPOSetting(context)
            "oppo" -> goOPPOSetting(context)
            "huawei" -> goHuaWeiSetting(context)
            "honor" -> goHuaWeiSetting(context)
            "xiaomi" -> goXiaoMiSetting(context)
            "vivo" -> goVivoSetting(context)
            "meizu" -> goFlyeMeSetting(context)
            "samsung" -> goSamSungSetting(context)
        }
    }

    private fun goOPPOSetting(context: Context) {
        var intent = Intent()
        try {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("packageName", appPackage)
            val comp = ComponentName(
                "com.color.safecenter",
                "com.color.safecenter.permission.PermissionManagerActivity"
            )
            intent.component = comp
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("pkg_name", context.packageName)
                intent.putExtra("class_name", "com.welab.notificationdemo.MainActivity")
                val comp = ComponentName(
                    "com.coloros.notificationmanager",
                    "com.coloros.notificationmanager.AppDetailPreferenceActivity"
                )
                intent.component = comp
                context.startActivity(intent)
            } catch (e1: Exception) {
                // 否则跳转到应用详情
                intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        }
    }

    private fun goVivoSetting(context: Context) {
        var localIntent: Intent
        try {
            if (Build.MODEL.contains("Y85") && !Build.MODEL.contains("Y85A") || Build.MODEL.contains(
                    "vivo Y53L"
                )
            ) {
                localIntent = Intent()
                localIntent.setClassName(
                    "com.vivo.permissionmanager",
                    "com.vivo.permissionmanager.activity.PurviewTabActivity"
                )
                localIntent.putExtra("packagename", context.packageName)
                localIntent.putExtra("tabId", "1")
                context.startActivity(localIntent)
            } else {
                localIntent = Intent()
                localIntent.setClassName(
                    "com.vivo.permissionmanager",
                    "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"
                )
                localIntent.action = "secure.intent.action.softPermissionDetail"
                localIntent.putExtra("packagename", context.packageName)
                context.startActivity(localIntent)
            }
        } catch (e: Exception) {
            // 否则跳转到应用详情
            localIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            localIntent.data = uri
            context.startActivity(localIntent)
        }
    }

    private fun goHuaWeiSetting(context: Context) {
        var componentName: ComponentName? = null
        val sdkVersion = Build.VERSION.SDK_INT
        try {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //跳自启动管理
            if (sdkVersion >= 28) { //9:已测试
                componentName =
                    ComponentName.unflattenFromString("com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity") //跳自启动管理
            } else if (sdkVersion >= 26) { //8：已测试
                componentName =
                    ComponentName.unflattenFromString("com.huawei.systemmanager/.appcontrol.activity.StartupAppControlActivity")
            } else if (sdkVersion >= 23) { //7.6：已测试
                componentName =
                    ComponentName.unflattenFromString("com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity")
            } else if (sdkVersion >= 21) { //5
                componentName =
                    ComponentName.unflattenFromString("com.huawei.systemmanager/com.huawei.permissionmanager.ui.MainActivity")
            }
            //componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");//锁屏清理
            intent.component = componentName
            context.startActivity(intent)
        } catch (e: Exception) {
            //跳转失败
            // 否则跳转到应用详情
            val intentDetail = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intentDetail.data = uri
            context.startActivity(intentDetail)
        }
    }

    private fun goXiaoMiSetting(context: Context) {
        try {
            // MIUI 8
            val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
            localIntent.setClassName(
                "com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity"
            )
            localIntent.putExtra("extra_pkgname", context.packageName)
            context.startActivity(localIntent)
        } catch (e: Exception) {
            try {
                // MIUI 5/6/7
                val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
                localIntent.setClassName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
                )
                localIntent.putExtra("extra_pkgname", context.packageName)
                context.startActivity(localIntent)
            } catch (e1: Exception) {
                // 否则跳转到应用详情
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        }
    }

    private fun goSamSungSetting(context: Context) {
        val intent = Intent()
        var componentName: ComponentName? = null
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //跳自启动管理
            componentName =
                ComponentName.unflattenFromString("com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity")
            intent.component = componentName
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                componentName = ComponentName(
                    "com.samsung.android.sm_cn",
                    "com.samsung.android.sm.ui.ram.AutoRunActivity"
                )
                intent.component = componentName
                context.startActivity(intent)
            } catch (e1: Exception) {
                //跳转失败
                // 否则跳转到应用详情
                val intentDetail = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intentDetail.data = uri
                context.startActivity(intentDetail)
            }
        }
    }

    private fun goFlyeMeSetting(context: Context) {
        val intent = Intent()
        var componentName: ComponentName? = null
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //跳自启动管理
            componentName =
                ComponentName.unflattenFromString("com.meizu.safe/.permission.SmartBGActivity") //跳转到后台管理页面
            intent.component = componentName
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                componentName =
                    ComponentName.unflattenFromString("com.meizu.safe/.permission.PermissionMainActivity") //跳转到手机管家
                intent.component = componentName
                context.startActivity(intent)
            } catch (e1: Exception) {
                //跳转失败
                // 否则跳转到应用详情
                val intentDetail = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intentDetail.data = uri
                context.startActivity(intentDetail)
            }
        }
    }

    fun installApk(context: Context, downloadApk: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val file = File(downloadApk)
        logD("安装路径==$downloadApk")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val apkUri: Uri = FileProvider.getUriForFile(
                context,
                "$appPackage.fileprovider",
                file
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val uri = Uri.fromFile(file)
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }
}