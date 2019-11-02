package com.hiscene.demo2.kotlin2

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

interface Base{
    fun print()
}

class BaseImp(var x:String):Base{
    override fun print() {
        println(x)
    }
}

class Derived(b:Base):Base by b

class Example{
    var p:String by Delegate()
    var q:String by Delegate()
}

class Delegate{
    operator fun getValue(thisRef:Any?,property:KProperty<*>):String{
        return "$thisRef,委托了${property.name} 属性"
    }

    operator fun setValue(thisRef: Any?,property: KProperty<*>,value:String){
        println("$thisRef 的${property.name} 属性被赋予值：$value")
    }
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

    var dd =  Derived(object : Base{
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
}