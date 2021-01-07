package com.like.component

import android.app.Application
import android.content.Context

/**
 * 组件中的 Application 的代理类，用于管理所有组件的 Application 的生命周期。
 */
class ModuleApplicationDelegate : IModuleApplication {
    private val mModuleApplications = mutableMapOf<String, IModuleApplication>()

    override fun attachBaseContext(base: Context?) {
        base ?: return
        mModuleApplications.putAll(ManifestParser(base).parseModuleApplicationsFromMetaData())
        mModuleApplications.forEach {
            it.value.attachBaseContext(base)
        }
    }

    override fun onCreate(application: Application) {
        mModuleApplications.forEach {
            it.value.onCreate(application)
        }
    }

    override fun onTerminate(application: Application) {
        mModuleApplications.forEach {
            it.value.onTerminate(application)
        }
    }

    /**
     * 获取组件Application的实例。在ManifestParser中解析的时候实例化的。
     */
    fun getModuleApplication(className: String): IModuleApplication? = mModuleApplications[className]

}