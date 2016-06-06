## RxJavaLearn
codes of RxJava


### 操作符决策树

- 直接创建一个Observable（创建操作）
- 组合多个Observable（组合操作）
- 对Observable发射的数据执行变换操作（变换操作）
- 从Observable发射的数据中取特定的值（过滤操作）
- 转发Observable的部分值（条件/布尔/过滤操作）
- 对Observable发射的数据序列求值（算术/聚合操作）   



### 创建操作
- Create — 通过调用观察者的方法从头创建一个Observable
- Empty/Never/Throw — 创建行为受限的特殊Observable
- Defer — 在观察者订阅之前不创建这个Observable，为每一个观察者创建一个*新的*Observable
- Just — 将对象或者对象集合转换为一个会发射这些对象的Observable
- From — 将其它的对象或数据结构转换为Observable
- Range — 创建发射指定范围的整数序列的Observable
- Interval — 创建一个定时发射整数序列的Observable
- Repeat — 创建重复发射特定的数据或数据序列的Observable
- Start — 创建发射一个函数的返回值的Observable
- Timer — 创建在一个指定的延迟之后发射单个数据的Observable



### 变换操作


### 过滤操作


### 组合操作


### 错误处理


### 辅助操作


### 条件和布尔操作


### 算术和聚合操作


### 连接操作
 
 
### 转换操作


## Thanks To
[操作符分类](https://mcxiaoke.gitbooks.io/rxdocs/content/Operators.html)<br>
[木水川](http://mushuichuan.com/tags/RxJava/)
