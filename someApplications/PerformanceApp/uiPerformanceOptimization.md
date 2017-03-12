## **前言：**
本篇文章主要学习UI性能优化。

## **1. 应用UI卡顿原理分析**
### **1.1 原理** 
用户感觉到卡顿的原理就是在渲染时出现了掉帧。Android系统要求 1s内渲染60帧，即渲染1帧必须要在16ms完成。如果你的界面没有在16ms内准备好一帧，所以就不会进行渲染，屏幕也就不会刷新。结果就是用户就是盯着一帧看了32ms，而不是16ms，因为刷新频率的不一致，就使用户感知到了卡顿。

### **1.2 为什么是60fps** 
官方出的性能优化视频[Android Performance Patterns: Why 60fps?](https://www.youtube.com/watch?v=CaMTIgxCSqU) 解释说：

*While 60 frames per second is actually the sweet pot. Great, smooth motion without all the tricks. And most humans can't perceive the benefits of going higher than this number.*
60fps是最恰当的帧率，是用户能感知到的最流畅的帧率，而且人眼和大脑之间的协作无法感知到超过 60fps的画面更新。
*Now, it's worth noting that the human eye is very discerning when it comes to inconsistencies in these frame rates*
但值得注意的是人眼却能够感受到刷新频率不一致带来的卡顿现象，比如一会60fps，一会30fps，这是能够被感受到的，即卡顿。
*As an app developer, your goal is clear. Keep your app at 60 frames per second. that's means you have got 16 milliseconds per frame to do all of your work.That is input, computing, network and rendering every frame to stay fluid for your users.*
所以作为开发者，你必须要保证你的所有操作在16ms内完成包括输入、计算、网络、渲染等这些操作，才能保证应用使用过程的流畅性。

在知道了卡顿是因为掉帧后，我们

## **2. 导致卡顿的原因**













































<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />

>参考：

-  [Android应用开发性能优化完全分析](http://blog.csdn.net/yanbober/article/details/48394201)
- [Android界面性能调优手册](https://androidtest.org/android-graphics-performance-pattens/)

---