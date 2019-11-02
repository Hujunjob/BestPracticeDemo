package com.hiscene.demo2.kotlin

/**
 * Created by junhu on 2019-11-01
 */

interface Base {
    fun toast() {}
}

class BaseImp : Base {
    var a = 0
        set(value) {
            a
        }
        get() {
            field
            return 0
        }

    override fun toast() {
        println("baseimp toast")
    }
}

fun BaseImp.say() = {
    println("say hello")
}

fun BaseImp.say2() {
    println("say2 hello")
}

class C {
    fun toast() {

    }

    fun BaseImp.say() {
        this@C.toast()
    }

    fun callB(base: BaseImp) {
        base.say()
    }
}

var <T>List<T>.lastIndex: Int
    get() = size - 1
    set(value) {}

class BaseImp2 : Base {
    override fun toast() {
        println("baseimp2 toast")
    }
}

class Proxy(base: Base) : Base by base

open class Person(var name: String, var age: Int) {
    open fun toast() {
        println("person toast")
    }
}

fun Any?.toString(): String {
    if (this == null) {
        return "null"
    }
    return toString()
}

class Student : Person {
    constructor(name: String, age: Int, score: Int) : super(name, age)
}

class Teacher(name: String, age: Int, var num: String) : Person(name, age)

class BasePerson(name: String, age: Int) : Base, Person(name, age) {
    override fun toast() {
        super<Base>.toast()
        super<Person>.toast()
    }
}

open class Foo {
    open val x: Int
        get() {
            return 1
        }
}

class Bar : Foo() {
    override var x = 2
}

class Bar2 : Foo()

data class UserInfo(var name: String, var age: Int, var score: Int)

fun main() {
    var base = BaseImp()
    base.a = 1
    var proxy = Proxy(base)
    proxy.toast()
    base.say().invoke()
    base.say2()

    var base2 = BaseImp2()
    var proxy2 = Proxy(base2)
    proxy2.toast()

    var person = Person("hujun", 18)
    println(person.name)

    var student = Student("hujun", 18, 100)

    var teacher = Teacher("h", 1, "1")
    teacher.num

    var bp = BasePerson("h", 1)
    bp.toast()
    bp.name

    var bar1 = Bar()
    var bar2 = Bar2()
    bar1.x
    bar2.x

    var list = ArrayList<String>()
    list.add("1")
    list.size
    list.lastIndex

//    var a = null
//    println(a.toString())

    var userInfo = UserInfo("h", 1, 1)
    val (n, a) = userInfo
    println("$n,$a")
    var u1 = userInfo.copy()

    testInline()
}