## **前言**
RxJava学了一阵子，也终于将它用到实际开发中去了。和Retrofit连起来用简直赞到不行，越写越惊叹它的神奇、方便和简洁，有时候令人拍案称奇。下面这句话很确切的描述了这种编程方式：

*Functional Reactive Programming (FRP) offers a fresh perspective on solving modern programming problems. Once understood, it can greatly simplify your project, especially when it comes to code dealing with asynchronous events with nested callbacks, complex list filtering/transformation or timing concerns.*
响应式编程提供了一种新的思路去编程。一旦掌握，它极大的简化了你的代码，尤其简化了异步处理、嵌套回调、复杂的数据变化这些代码。那么什么是响应式编程。

## **1. 什么是响应式编程,即Rx**
*ReactiveX is a library for composing asynchronous and event-based programs by using observable sequences.*
*It extends the observer pattern to support sequences of data and/or events and adds operators that allow you to compose sequences together declaratively while abstracting away concerns about things like low-level threading, synchronization, thread-safety, concurrent data structures, and non-blocking I/O.*
*This looks a lot like your standard observer pattern but it differs in one key way -Observables often don't start emitting items until someone explicitly subscribes to them.*

- Rx是一个使用可观察数据流进行异步编程的编程接口，ReactiveX结合了观察者模式、迭代器模式和函数式编程的精华。与异步数据流交互的编程范式
- 它是基于事件流的、异步的，使用函数式编程的、并可以通过操作符来对事件流进行变化，不用考虑线程切换、同步异步、线程安全和线程阻塞问题
- 它是一种可扩展的观察者模式，一直到被订阅时才响应 (*often* Observable有热和冷之分，这取决于该Observable的实现方式，参考文末链接)。
- 程序以流的形式，传递数据的改变
- Rx就是与异步数据流交互的编程范式.


ReactiveX是一种编程范式、思想、框架，它有多种实现，我们用的就是RxJava，其他语言实现的有RxJS,RxSwift等。在开始介绍RxJava之前我想介绍一下函数式编程，因为这是另外一种编程思想，理解这种思想大有裨益。

## **2. 什么是函数式编程**
这里仅仅讲的是个人理解。因为觉得这种编程思想太简洁所以斗胆介绍一下。
根据查的资料，应该有至少三种编程思想：

- 命令式编程 *Imperative programming*
-  逻辑式编程 *Object-oriented Programming*
- 函数式编程 *Functional Programming*

命令式编程关心解决问题的步骤，面向对象编程是也是一种命令式编程，面向过程的C语言必然也是了。
而函数式编程关心数据的映射，即一种东西和另一种东西之间的对应关系。它的主要思想是**把运算过程尽量写成一系列嵌套的函数调用**。

比如这种运算：(1 + 2) * 3 - 4 ;
之前我们写是这样：

         int a = 1 + 2; int b = a * 3; int result = b - 4; System.out.println(b);
然而用函数式编程思想写就是这样：

       System.out.print(subtract( multiply(add(1,2)，3),4) );

全部变为了函数调用，这样看起来也简洁、见名之意。(示例来自阮一峰博客)

看到这，回顾一下，使用RxJava时是不是全部调用各种操作符进行处理，这就是对事件流进行运算啊，全部调用函数进行处理。

关于更多函数式编程请看文末链接，理解它能更好的帮助我们理解Rx，并且在之后的编程实践中也可以用上。下面就介绍理解RxJava必不可少的一步：可扩展的观察者模式。

## **3. 什么是Rx的可扩展的观察者模式**
官方文档上说 `It extends the observer pattern *to support sequences of data ...` 这就是说 **Rx 扩展了观察者模式。**
### 3.1 **观察者模式定义：**
- 一个目标对象管理所有相依于它的观察者对象，并且在它本身的状态改变时主动发出通知。这通常透过呼叫各观察者所提供的方法来实现。此种模式通常被用来实时事件处理系统。
- 定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖它的对象都会得到通知并且被自动更新。

观察者模式面向的需求： A对象（观察者）对B对象（被观察者）的某种变化高度敏感，需要在B变化的一瞬间做出反应。
被观察者.registerObserver(观察者)，当被观察者发生变化时，立即通知观察者做出反应。

### **3.2 RxJava的观察者模式**
RxJava 有四个基本概念：

- Observable(被观察者)
- Observer(观察者)
- subscribe(订阅，监听)
- Event(事件)

通过Observable.subscribe(Observer)来将Observable注册到Observer上。

RxJava的事件回调方法有三个： `onNext()`，`onError()`，`onComplete()`。
其中onError()和onComplete()是互斥的，在一个队列中只能一个方法被回调。

了解了RxJava是如何扩展了观察者模式后，终于能来看什么是RxJava了。

## **4. 什么是RxJava**
*RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.*
RxJava就是ReactiveX的Java实现。上面也说了，ReactiveX只是一个编程范式，可以被多种语言实现。

恩，然后，这里并不介绍如何创建、使用RxJava，这些都可以参考文末链接，扔物线那边文章太好了，看完就会。那下面就直接介绍RxJava的操作符。

## **5. RxJava的操作符**
*ReactiveX provides a collection of operators with which you can filter, select, transform, combine, and compose Observables. This allows for efficient execution and composition.*
ReactiveX 提供了一系列操作符来对事件流进行过滤、筛选、变换、合并等操作组合事件流，使事件流的处理更为高效。

### **5.1 操作符类别：**
下面列出类别里面的操作符，几乎学习RxJava就是学习操作符，操作符用熟了Rx就熟了。

- 创建操作
create、defer、empty、never、throw、from、Interval、just、range、repeat、start、timer
- 变换操作
buffer、flatmap、groupBy、map、scan、window
- 过滤操作
debounce、distinct、distinctUtilChanged、elementAt、filter、first、ignoreElements、last、sample、skip、skipLast、take、takeLast、throttleWithTimeout、throttleFirst
- 组合操作
and、then、when、combineLatest、join、merge、startWith、switch、zip
- 错误处理
onErrorReturn、onErrorRusumeNext、onExceptionResumeNext、retry、retryWhen
- 辅助操作
delay、delaySubscription、doOnEach、doOnNext、doOnSubscribe、doOnSubscribe、doOnUnSubscribe、doOnError、doOnComplete、doOnComplete、finallyDo、doAfterTerminate、materialize、dematerialize、ObserveOn、SubscribeOn、timeInterval、timeout、timestamp、using
- 条件和布尔操作
all、amb、contains、isEmpty、defaultIsEmpty、sequenceEqual、skipUntil、skipWhile、takeUntil、takeWhile
- 算术和聚合操作
concat、count、reduce、collect
- 连接操作
publish、connect、refCount、replay
- 转换操作
to
- 自定义操作符
lift、compose

### **5.2 操作符决策树：**
- 直接创建一个Observable（创建操作）
- 组合多个Observable（组合操作）
- 对Observable发射的数据执行变换操作（变换操作）
- 从Observable发射的数据中取特定的值（过滤操作）
- 转发Observable的部分值（条件/布尔/过滤操作）
- 对Observable发射的数据序列求值（算术/聚合操作）

更详细决策树：[A Decision Tree of Observable Operators)](http://reactivex.io/documentation/operators.html)

关于操作符的学习，请看文末链接，都是超级好的文章。下面看看RxJava的Schedulers。

## **6. RxJava的线程调度**
RxJava的线程调度器有这么几种：
- Schedulers.computation( )	在计算线程执行
- Schedulers.from(executor)	指定线程池
- Schedulers.immediate( )	 当前线程执行
- Schedulers.io( )	 IO线程
- Schedulers.newThread( ) 新开子线程
- Schedulers.trampoline( ) 在当前线程排队，当其它排队的任务完成后开始执行
- AndroidSchedulers.mainThread() 在Android的UI线程执行

subscribeOn() 设置 **被观察者** 所在的线程，事件产生线程，当使用了多个subscribeOn()的时候，只有第一个 subscribeOn() 起作用。
observeOn() 设置 **观察者**、**下一步操作** 运行的线程，事件消费线程。

Rx强大之处就是对于流的异步处理，使用线程调度器和subscribeOn()、observeOn()很方便的就对流进行线程的切换，丝毫不用考虑太多，就这么简单。更详细的线程调度器请参考 扔物线文章(写的太清晰)。到这儿基本上就对RxJava有大概的认识了，下面介绍RxJava的相关库。

## **7. Rx相关库**
自己目前用到的是这些：

- RxLifecycle 很方便的对Subscriber解绑，否则容易内存泄露
- RxBinding 对Android View的封装，很方便使用操作符对UI事件进行处理
- RxBus 替代EventBus

使用这些库能更大方便我们的开发过程，这些库都使用自定义操作符写的，我们也可以定义需要的操作符方便开发。之后学了自定义操作符后再添进来，现在只有个了解。下面再说说Lambda表达式，来方便书写。

##  **8. 使用Java8新特性**
这块可以在官方文档上看到：[官方文档](https://developer.android.com/preview/j8-jack.html)。在Android studio中开启Java8新特性目前只有两个特性向下兼容到低版本。`Lambda expressions`和 `Method References`。

有两种开启方式：

1. 使用N Preview 编译，同时 [enable the Jack toolchain](https://developer.android.com/preview/j8-jack.html#configuration)。
2. 使用RetroLambda插件，这个之前就有


### 8.1 Lambda表达式
因为RxJava大量使用了函数式编程，会出现很多内部类，就会使代码被拉长，有时候会看不到主要逻辑。这时使用Lambda表达式会使代码清晰很多。

- Lambda 表达式让你能够将函数作为方法参数，或者将代码作为数据对待。
- Lambda 表达式由三个部分组成：第一部分为一个括号内用逗号分隔的形式参数，参数是函数式接口里的方法参数；第二部分为一个箭头符号：->；第三部分为方法体，可以是表达式和代码块。

	      (parameters) -> { statements; }


### 8.2 Method References方法引用
方法引用使得开发者可以直接引用现存的方法、Java类的构造方法或者实例对象。(参考文末链接，有很详细的说明。)
有四种类型的引用：

- 构造器引用
**Class<T>::new**
- 静态方法引用
 **Class::static_method**
- 某个类的成员方法的引用
**Class::method**
- 某个实例对象的成员方法的引用
**instance::method**

使用这些新特性开发的时候能大大减少代码量，只不过要对那些操作符很熟悉，要不然看到Lambda表达式有时候确实不知道它在干嘛，可读性低。但代码总是越写越熟练的。


## **结语**
RxJava毕竟是一种新的编程框架、思想，使用越多越熟练。Everything is a stream. 学习过程中多看示例多敲代码，自己的练习代码在GitHub上：[RxJavaLearn](https://github.com/baiiu/LearnTechDemo/tree/master/RxJavaLearn) .

用一句话结尾吧：
*On a more conceptual level,Subscribers are supposed to be the thing that*reacts*, not the thing that mutates.*
从更高的层面来看，Subscribers更应该做出响应，而不是变化。



<br >


>参考：
>
>- 官网：
http://reactivex.io
>- 这两篇文章看了就能对Rx有基本的了解以及学习方向：Observable + Observer + Operator
[给 Android 开发者的 RxJava 详解](http://gank.io/post/560e15be2dca930e00da1083#toc_25)
 [深入浅出RxJava（一：基础篇）](http://blog.csdn.net/lzyzsd/article/details/41833541)
> - 最全面资料
[Awesome-RxJava](https://github.com/lzyzsd/Awesome-RxJava)
[官方文档](http://reactivex.io/documentation/observable.html)
[官方文档 中文](https://mcxiaoke.gitbooks.io/rxdocs/content/index.html)
[Intro To RxJava 系列教程 总结](http://blog.chengyunfeng.com/?p=983)
>- 操作符
[操作符分类](https://mcxiaoke.gitbooks.io/rxdocs/content/Operators.html)<br>[木水川](http://mushuichuan.com/tags/RxJava/)
>- 热和冷的Observable
[Hot vs Cold Observables](https://medium.com/@benlesh/hot-vs-cold-observables-f8094ed53339#.dsnkxyixv)
[RxJS Cold vs. Hot Observables](https://github.com/Reactive-Extensions/RxJS/blob/master/doc/gettingstarted/creating.md#cold-vs-hot-observables)
>- 关于RxJava的一本书
[RxJava Essentials](http://rxjava.yuxingxin.com/)
>- 源码分析：
[彻底搞懂 RxJava — 基础篇](http://diordna.sinaapp.com/?p=896)
[彻底搞懂 RxJava — 中级篇](http://diordna.sinaapp.com/?p=910)
[彻底搞懂 RxJava — 高级篇](http://diordna.sinaapp.com/?p=912)
[RxJava基本流程和lift源码分析](http://blog.csdn.net/lzyzsd/article/details/50110355)
>- 好文：
[What is Functional Reactive Programming?](https://www.bignerdranch.com/blog/what-is-functional-reactive-programming/)
[那些年我们错过的响应式编程](http://www.devtf.cn/?p=174)
> - 观察者模式
[设计模式之观察者模式](http://www.jianshu.com/p/d55ee6e83d66)
>- 函数式编程
[函数式编程初探](http://www.ruanyifeng.com/blog/2012/04/functional_programming.html)
[什么是函数式编程思维](https://www.zhihu.com/question/19732025)
> - 结合Retrofit:
https://gank.io/post/56e80c2c677659311bed9841
> - 示例:
https://github.com/kaushikgopal/RxJava-Android-Samples
https://github.com/rengwuxian/RxJavaSamples
>- Java8 新特性
[Java 8 新特性概述](https://www.ibm.com/developerworks/cn/java/j-lo-jdk8newfeature/)
[【译】Java 8的新特性—终极版](http://www.jianshu.com/p/5b800057f2d8)
[Method References](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html)
