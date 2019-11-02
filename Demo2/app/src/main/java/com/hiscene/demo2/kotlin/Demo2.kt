package com.hiscene.demo2.kotlin


/**
 * Created by junhu on 2019-11-01
 */

sealed class Expr

open class Box<T>(var t: T)

fun <T> boxIn(t: T) = Box(t)

fun <T : Box<T>> box() {
//    Comparable
//    Int
}

fun <T> box3() where T : Box<T>, T : Comparable<T> {

}

enum class Color {
    RED, BLUE
}

enum class Color2(val rgb: Int) {
    RED(0xff0000),
    BLUE(0x0000ff)
}

inline fun testInline(){
    println("test inline")
}

interface IH{
    fun  say();
}

fun main() {
    var a = null
    println(a.toString())

    var box = Box(1)
    var box2 = Box("hello")

    box.t
    box2.t

    var bb = boxIn(1)

    var color = Color.BLUE
    color.ordinal
    color.name
    var arrary = Color.values()
    for (c in arrary) {
//        println(c)
    }

    var array = enumValues<Color>()
    println(array.joinToString {
        it.name
    })
    testInline()

    var boxx: Box<String> = object : Box<String>("hello"){

    }

    var ih : IH = object :IH{
        override fun say() {

        }
    }

    var obj = object {
        var name = "hujun"
        var age = 1
    }

    obj.name
    obj.age

    var user = User
    var user2 = User
    user.x = 10
    println(user2.x)

}

class Test{
    private fun foo() = object {
        var x = "x"
    }

    fun foo2() = Box(1)

    fun foo1() = object {
        var x = "x"
    }

    fun bar(){
        foo().x
        foo1()
        foo()
        foo2().t
    }
}

object User{
    var x = 1
}