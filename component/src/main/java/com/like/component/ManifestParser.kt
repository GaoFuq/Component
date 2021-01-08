package com.like.component

import android.content.Context
import android.content.pm.PackageManager

/**
 * 从合并后的AndroidManifest.xml文件中解析出所有组件中实现了 IModuleApplication 接口的类
 */
internal class ManifestParser(private val mContext: Context) {
    private val TAG_MODULE_APPLICATION = IModuleApplication::class.java.simpleName

    fun parseModuleApplicationsFromMetaData(): Map<String, IModuleApplication> {
        val moduleApplications = mutableMapOf<String, IModuleApplication>()
        val appInfo = try {
            mContext.packageManager.getApplicationInfo(mContext.packageName, PackageManager.GET_META_DATA)
        } catch (e: Exception) {
            throw RuntimeException("组件中的 AndroidManifest.xml 下没有配置 meta-data 标签", e)
        }
        appInfo.metaData.keySet().forEach { className ->
            if (TAG_MODULE_APPLICATION == appInfo.metaData.get(className)) {
                moduleApplications[className] = try {
                    Class.forName(className).newInstance() as? IModuleApplication
                } catch (e: Exception) {
                    null
                } ?: throw RuntimeException("实例化组件中实现 $TAG_MODULE_APPLICATION 接口的 Application 失败")
            }
        }
        return moduleApplications
    }

}