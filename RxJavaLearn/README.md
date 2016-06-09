## RxJavaLearn
codes of RxJava

Functional Reactive Programming (FRP) offers a fresh perspective on solving modern programming problems. 
Once understood, it can greatly simplify your project, especially when it comes to code dealing with asynchronous events with nested callbacks, complex list filtering/transformation or timing concerns.


On a more conceptual level,Subscribersare supposed to be the thing thatreacts, not the thing thatmutates.
Subscribers更应该做出响应，而不是变化。



### 操作符决策树

- 直接创建一个Observable（创建操作）
- 组合多个Observable（组合操作）
- 对Observable发射的数据执行变换操作（变换操作）
- 从Observable发射的数据中取特定的值（过滤操作）
- 转发Observable的部分值（条件/布尔/过滤操作）
- 对Observable发射的数据序列求值（算术/聚合操作）   



### 创建操作
用于创建Observable的操作符


- Create
通过调用观察者的方法从头创建一个Observable
- Empty/Never/Throw
创建行为受限的特殊Observable
- Defer
在观察者订阅之前不创建这个Observable，为每一个观察者创建一个*新的*Observable
- Just
将对象或者对象集合转换为一个会发射这些对象的Observable
- From 
将其它的对象或数据结构转换为Observable
- Range
创建发射指定范围的整数序列的Observable,range操作符,发射从start开始的count个数
- Interval
间隔一定时间发送一个数字,从0开始.本身运行在 Schedulers.computation() 线程内
- Repeat
Repeat作用在Observable上,会对其重复发射count次
- Timer
Timer会在指定时间后发射一个数字0，注意其也是运行在computation Scheduler



### 变换操作
对Observable发射的数据进行变换


- Buffer
缓存，可以简单的理解为缓存，它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个
- FlatMap
扁平映射，将Observable发射的数据变换为Observables集合，然后将这些Observable发射的数据平坦化的放进一个单独的Observable，可以认为是一个将嵌套的数据结构展开的过程。
- GroupBy 
分组，将原来的Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，每一个Observable发射一组不同的数据
- Map
映射，通过对序列的每一项都应用一个函数变换Observable发射的数据，实质是对序列中的每一项执行一个函数，函数的参数就是这个数据项
- Scan
扫描， 连续地对数据序列的每一项应用一个函数，然后连续发射结果,每一项结果基于之前的结果.累加器函数.
- Window
窗口，定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，而不是每次发射一项。类似于Buffer，但Buffer发射的是数据，Window发射的是Observable，每一个Observable发射原始Observable的数据的一个子集



### 过滤操作
转化操作符，能将数据转化为我们想要的格式，但是如果数据集合里面有一些我们想要过滤掉的数据怎么办？
这时候我们就需要使用过滤操作符:**用于从Observable发射的数据中进行选择.**


- throttleWithTimeout 
源Observable每次发射出来一个数据后就会进行计时,如果在设定好的时间结束前源Observable有新的数据发射出来，这个数据就会被丢弃，同时重新开始计时。
- Debounce 
只有在空闲了一段时间后才发射数据，通俗的说，就是如果一段时间没有操作，就执行一次操作.还可以根据一个函数来进行限流。这个函数的返回值是一个临时Observable，
如果源Observable在发射一个新的数据的时候，上一个数据根据函数所生成的临时Observable还没有结束,没有调用onComplete，那么上一个数据就会被过滤掉。如果是最后一个,还是会发射.
- Distinct
去重，过滤掉所有的重复数据项
- DistinctUtilChanged 
过滤掉相邻的重复项
- ElementAt
取值，取特定位置的数据项
- Filter
过滤，过滤掉没有通过谓词测试的数据项，只发射通过测试的
- First
只取满足条件的第一个数据.可以和BlockingObservable连用.
可以Observable.toBlocking或者BlockingObservable.from方法来将一个Observable对象转化为BlockingObservable对象
- Last
末项，只发射最后一条数据.
- IgnoreElements
忽略所有的数据，只保留终止通知(onError或onCompleted)
- Sample 
取样，定期发射最新的数据，等于是数据抽样，有的实现里叫ThrottleFirst
- throttleFirst 
会定期发射这个时间段里源Observable发射的第一个数据
- Skip
跳过前面的若干项数据
- SkipLast 
跳过后面的若干项数据
- Take 
只保留前面的若干项数据
- TakeLast 
只保留后面的若干项数据




### 组合操作
- And/Then/When 
通过模式(And条件)和计划(Then次序)组合两个或多个Observable发射的数据集
- CombineLatest 
当两个Observables中的任何一个发射了一个数据时，通过一个指定的函数组合每个Observable发射的最新数据（一共两个数据），然后发射这个函数的结果

    必须满足两个条件:
    1)所有的Observable都发射过数据。
    2)满足条件1的时候任何一个Observable发射一个数据，就将所有Observable最新发射的数据按照提供的函数组装起来发射出去。
    
    在这两个条件下,可能会忽略掉一些发射的数据.


- Join 
无论何时，如果一个Observable发射了一个数据项，只要在另一个Observable发射的数据项定义的时间窗口内，就将两个Observable发射的数据合并发射
    
    参数说明:
    1)源Observable所要组合的目标Observable
    2)一个函数，就收从源Observable发射来的数据，并返回一个Observable，这个Observable的生命周期决定了源Observable发射出来数据的有效期
    3)一个函数，就收从目标Observable发射来的数据，并返回一个Observable，这个Observable的生命周期决定了目标Observable发射出来数据的有效期
    4)一个函数，接收从源Observable和目标Observable发射来的数据，并返回最终组合完的数据。

- Merge 
将两个Observable发射的数据组合并成一个
Merge可能会让合并的Observables发射的数据交错（可以使用Concat操作符,不会让数据交错，它会按顺序一个接着一个发射多个Observables的发射物）。
- StartWith 
在发射原来的Observable的数据序列之前，先发射一个指定的数据序列或数据项
    
    在数据序列的开头插入一条指定的项
    你也可以传递一个Observable给startWith，
    它会将那个Observable的发射物插在原始Observable发射的数据序列之前.这可以看作是Concat的反转。

- Switch,在RxJava的实现为SwitchOnNext
将一个发射Observable序列的Observable转换为这样一个Observable：它逐个发射那些Observable最近发射的数据
用来将一个发射多个小Observable的源Observable转化为一个Observable，然后发射这多个小Observable所发射的数据。
需要注意的就是，如果一个小的Observable正在发射数据的时候，源Observable又发射出一个新的小Observable，则前一个Observable发射的数据会被抛弃，直接发射新的小Observable所发射的数据。

- Zip RxJava的实现由 zip,zipWith两个操作符
打包，使用一个指定的函数将多个Observable发射的数据组合在一起，然后将这个函数的结果作为单项数据发射
Zip操作符将多个Observable发射的数据按顺序组合起来，每个数据只能组合一次，而且都是有序的。
**最终组合的数据的数量由发射数据最少的Observable来决定。**


### 错误处理
这些操作符用于从错误通知中恢复

- Catch 
捕获，继续序列操作，将错误替换为正常的数据，从onError通知中恢复

    - onErrorReturn
        当发生错误的时候，让Observable发射一个预先定义好的数据并正常地终止,不会抛出异常
    - onErrorResumeNext
        当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
    - onExceptionResumeNext
        类似于OnErrorResume,不同之处在于其会对onError抛出的数据类型做判断，
        如果是Exception，也会使用另外一个Observable代替原Observable继续发射数据，
        否则会将错误分发给Subscriber。

- Retry 
重试，如果Observable发射了一个错误通知，重新订阅它，期待它正常终止
    - retry
        Retry操作符在发生错误的时候会重新进行订阅,而且可以重复多次，
        所以发射的数据可能会产生重复。如果重复指定次数还有错误的话就会将错误返回给观察者,会掉onError
    - retryWhen
        当错误发生时，retryWhen会接收onError的throwable作为参数，并根据定义好的函数返回一个Observable，如果这个Observable发射一个数据，就会重新订阅。
        需要注意的是使用retryWhen的时候,因为每次重新订阅都会产生错误，所以作为参数的obserbvable会不断地发射数据，使用zipWith操作符可以限制重新订阅的次数，否则会无限制地重新订阅。
        会正常结束,调用onCompleted


### 辅助操作
- Delay 
延迟一段时间发射结果数据
- Do 
注册一个动作占用一些Observable的生命周期事件，相当于Mock某个操作
- Materialize/Dematerialize 
将发射的数据和通知都当做数据发射，或者反过来
- ObserveOn 
指定观察者观察Observable的调度程序（工作线程）
- Serialize 
强制Observable按次序发射数据并且功能是有效的
- Subscribe 
收到Observable发射的数据和通知后执行的操作
- SubscribeOn 
指定Observable应该在哪个调度程序上执行
- TimeInterval 
将一个Observable转换为发射两个数据之间所耗费时间的Observable
- Timeout 
添加超时机制，如果过了指定的一段时间没有发射数据，就发射一个错误通知
- Timestamp 
给Observable发射的每个数据项添加一个时间戳
- Using 
创建一个只在Observable的生命周期内存在的一次性资源


### 条件和布尔操作


### 算术和聚合操作


### 连接操作
 
 
### 转换操作


## Thanks To
[操作符分类](https://mcxiaoke.gitbooks.io/rxdocs/content/Operators.html)<br>
[木水川](http://mushuichuan.com/tags/RxJava/)
