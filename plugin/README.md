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
[Gradle 生命周期](https://www.heqiangfly.com/2016/03/18/development-tool-gradle-lifecycle/)
[Gradle 生命周期](https://juejin.im/post/5afec54951882542715001f2)

./gradlew tasks，显示所有task

2.1 打印Android build过程中所有task


[手动构建apk](https://www.jianshu.com/p/5a126550920f)
[Gradle 使用指南](https://www.heqiangfly.com/categories/Gradle/)


## 3. AOP、ASM、Javassist、AspectJ等字节码注入技术
[Java字节码增强探秘](https://mp.weixin.qq.com/s/CH9D-E7fxuu462Q2S3t0AA)

## 4. 使用ASM
- 页面(Activity、Fragment)的打开事件
- 各种点击事件的统计,包括但不限于Click LongClick TouchEvent
- Debug期需要统计各个方法的耗时。注意这里的方法包括接入的第三方SDK的方法。

