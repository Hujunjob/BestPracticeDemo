package com.hiscene.demo2.kotlin2

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Created by junhu on 2019-11-01
 */


class MyClass {
    var name = "name"
//    object A{
//        fun say(){
//            println("say")
//        }
//    }

    companion object {
        fun say() {

        }
    }

}

class B {
    companion object : Factory<B> {
        override fun create(): B = B()
    }
}

interface Factory<T> {
    fun create(): T
}

interface Base {
    fun print()
}

class BaseImp(var x: String) : Base {
    override fun print() {
        println(x)
    }
}

class Derived(b: Base) : Base by b

class Example {
    var p: String by Delegate()
    var q: String by Delegate()
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef,委托了${property.name} 属性"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的${property.name} 属性被赋予值：$value")
    }
}

val lazyValue: String by lazy {
    println("compute")
    "Hello"
}

var a = 10
val num: Int by lazy {
    a = a + 1
    a
}

var name: String by Delegates.observable("初始值", { property, oldValue, newValue ->
    println("prop ${property.name}, old value = $oldValue,new value=$newValue")
})

class UserInfo {
    var name: String by Delegates.observable("胡军", { prop, old, new ->
        println("old=$old,new=$new")
    })
}

fun main() {
    var myClass = MyClass()
//    MyClass.A.say()

    MyClass.say()

    var b = MyClass.Companion

    MyClass.Companion.say()

    var bimp = BaseImp("hello")
    var derived = Derived(bimp)
    derived.print()

    var dd = Derived(object : Base {
        override fun print() {
            println("object")
        }
    })
    dd.print()

    var e = Example()
    e.p
    println(e.p)
    e.p = "hujun"

    println(e.p)

    println(e.q)

    println(lazyValue)
    println(lazyValue)
    println(lazyValue)

    a++
    println(num)
    a++
    println(num)
    a++
    println(num)

    println(name)
    name = "新的值1"
    name = "新的值2"

    var map = mutableMapOf("name" to "胡军", "url" to "www")
//    var map  = mapOf("name" to "www")
    var parse = Parse(map)
    println(parse.toString())
    map.put("name", "胡军1")
//    map.put("name" , "胡军1")

    println(parse.toString())

    var foo = Foo()
    println(foo.bar2)
}

class Parse(var map: Map<String, Any?>) {
    val name: String by map
    val url: String by map

    override fun toString(): String {
        return "$name,$url"
    }
}


class Foo {
    fun isValid(): Boolean {

        return false
    }

    fun doSome() {


    }

    var bar: String? = ""
    var bar2: String by Delegates.notNull()

}

fun example(computeFoo: () -> Foo) {
    val mf by lazy(computeFoo)

    var flag = true
    if (flag && mf.isValid()){
        mf.doSome()
    }
}