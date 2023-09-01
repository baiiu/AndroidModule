package com.baiiu.java

open class ParentA {
    init {
        println("ParentA构造")
        aaaaa()
    }
    open fun aaaaa() {
    }
}
class ChildA : ParentA() {
    private val a by lazy { 1 }
    init {
        println("ChildA构造")
    }
    override fun aaaaa() {
        println(a)
    }
}
class TeastSafe {
    private val a by lazy { 1 }

    init {
        println(a)
    }
}
fun main(args: Array<String>) {
    TeastSafe()
    ChildA()
}