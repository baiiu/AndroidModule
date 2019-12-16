# 字节码插桩采坑笔记

https://juejin.im/post/5aa0e7eff265da2395308f48
https://juejin.im/post/5c380b0be51d45043142bb9a


1. AOP
2. Android打包流程
3. ASM、Javassist等字节码注入技术

## 1. 自定义Gradle插件
http://kvh.io/cn/embrace-android-studio-gradle-plugin.html

[官方文档](https://gradle.org/guides/?q=Plugin%20Development)
[官方文档](https://docs.gradle.org/current/userguide/custom_plugins.html)
[Maven Publish](https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven)

## 2. Android打包流程、gradle是怎么组织起来的

### 2.1 build过程中所有task
./gradlew tasks，显示所有task
[Gradle 生命周期](https://www.heqiangfly.com/2016/03/18/development-tool-gradle-lifecycle/)
[Gradle 生命周期](https://juejin.im/post/5afec54951882542715001f2)
[Gradle 使用指南](https://www.heqiangfly.com/categories/Gradle/)

### 2.2 Android打包流程
[Android Build System 构建系统](https://blog.csdn.net/u014099894/article/details/51072084)
[详解](https://www.jianshu.com/p/019c735050e0)
[打包流程梳理](http://mouxuejie.com/blog/2016-08-04/build-and-package-flow-introduction/)
[AppPlugin.groovy源码](https://android.googlesource.com/platform/tools/build/+/master/gradle/src/main/groovy/com/android/build/gradle/AppPlugin.groovy?autodive=0%2F%2F%2F)
[手动构建apk](https://www.jianshu.com/p/5a126550920f)

## 3. AOP、ASM、Javassist、AspectJ等字节码注入技术
[Java字节码增强探秘](https://mp.weixin.qq.com/s/CH9D-E7fxuu462Q2S3t0AA)
[AOP 的利器：ASM 3.0 介绍](https://www.ibm.com/developerworks/cn/java/j-lo-asm30/)

## 4. 使用ASM
- 页面(Activity、Fragment)的打开事件
- 各种点击事件的统计,包括但不限于Click LongClick TouchEvent
- Debug期需要统计各个方法的耗时。注意这里的方法包括接入的第三方SDK的方法。

[Transform API](https://juejin.im/post/5ccd41066fb9a03204595a91)
