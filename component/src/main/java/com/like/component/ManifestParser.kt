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
            mContext.packageManager.getApplicationInfo(
                mContext.packageName,
                PackageManager.GET_META_DATA
            )
        } catch (e: Exception) {
            throw RuntimeException("组件中的AndroidManifest.xml下没有配置meta-data标签", e)
        }
        appInfo.metaData?.apply {
            this.keySet().forEach {
                if (TAG_MODULE_APPLICATION == this.get(it)) {
                    createModuleApplication(it)?.let { moduleApplication ->
                        moduleApplications[it] = moduleApplication
                    }
                }
            }
        }
        return moduleApplications
    }

    private fun createModuleApplication(className: String): IModuleApplication? =
        try {
            Class.forName(className).newInstance() as IModuleApplication?
        } catch (e: Exception) {
            throw RuntimeException("实例化组件中实现${TAG_MODULE_APPLICATION}接口的Application失败", e)
        }
}