#### 最新版本

模块|Component
---|---
最新版本|[![Download](https://jitpack.io/v/like5188/Component.svg)](https://jitpack.io/#like5188/Component)

## 功能介绍
1、提供了组件化相关的支持。

## 使用方法：

1、引用

在Project的gradle中加入：
```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
在Module的gradle中加入：
```groovy
    dependencies {
        implementation 'com.github.like5188:Component:版本号'
    }
```

2、壳工程的 Application 继承 com.like.component.BaseComponentApplication。

3、组件的 Application 实现 com.like.component.IModuleApplication。
注意：
    ①、实现类必须要有一个 public 的无参构造函数，用于反射构造组件 Application 的实例。
    ②、必须在组件的 AndroidManifest.xml 文件中进行如下配置：
    <meta-data
        android:name="实现类的全限定类名"
        android:value="IModuleApplication" />