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
- Timer — 创建在一个指定的延迟之后发射单个数据的Observable
- Start — 创建发射一个函数的返回值的Observable



### 变换操作
- Buffer — 缓存，可以简单的理解为缓存，它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个
- FlatMap — 扁平映射，将Observable发射的数据变换为Observables集合，然后将这些Observable发射的数据平坦化的放进一个单独的Observable，可以认为是一个将嵌套的数据结构展开的过程。
- GroupBy — 分组，将原来的Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，每一个Observable发射一组不同的数据
- Map — 映射，通过对序列的每一项都应用一个函数变换Observable发射的数据，实质是对序列中的每一项执行一个函数，函数的参数就是这个数据项
- Scan — 扫描， 连续地对数据序列的每一项应用一个函数，然后连续发射结果,每一项结果基于之前的结果.累加器函数.
- Window — 窗口，定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，而不是每次发射一项。类似于Buffer，但Buffer发射的是数据，Window发射的是Observable，每一个Observable发射原始Observable的数据的一个子集



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
