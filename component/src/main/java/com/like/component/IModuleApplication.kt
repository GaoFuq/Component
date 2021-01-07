package com.like.component

import android.app.Application
import android.content.Context

/**
 * 组件化架构时，组件的 Application 实现它。
 *
 * 注意：
 * 1、实现类必须要有一个 public 的无参构造函数，用于反射构造组件 Application 的实例。
 * 2、必须在组件的 AndroidManifest.xml 文件中进行如下配置：
 * <meta-data
 * android:name="实现类的全限定类名"
 * android:value="IModuleApplication" />
 */
interface IModuleApplication {

    fun attachBaseContext(base: Context?)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)

}