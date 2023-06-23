package com.lay.versatile_kotlin_programming

import java.math.BigInteger
import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis


fun sort(numbers: List<Int>): List<Int> =
    if (numbers.isEmpty())
        numbers
    else {
        val pivot = numbers.first()
        val tail = numbers.drop(1)
        val lessOrEqual = tail.filter{e-> e <= pivot}
        val larger = tail.filter{e -> e > pivot}
        sort(lessOrEqual) + pivot + sort(larger)
    }

fun factorialRec(n: Int): BigInteger =
    if (n <= 0) 1.toBigInteger() else n.toBigInteger() * factorialRec(n - 1)


//tailrec fun factorial(n: Int,
//                      result: BigInteger = 1.toBigInteger()): BigInteger =
//    if (n <= 0) result else factorial(n - 1, result * n.toBigInteger())

//println(factorial(100))


object Factorial {
    fun factorialRec(n: Int): BigInteger =
        if (n <= 0) 1.toBigInteger() else n.toBigInteger() * factorialRec(n - 1)

    tailrec fun factorial(n: Int,
                          result: BigInteger = 1.toBigInteger()): BigInteger =
        if (n <= 0) result else factorial(n - 1, result * n.toBigInteger())
}

fun fib(n: Int): Long = when (n) {
    0, 1 -> 1L
    else -> fib(n - 1) + fib(n - 2)
}

fun <T, R> ((T) -> R).memoize(): ((T) -> R) {
    val original = this
    val cache = mutableMapOf<T, R>()
    return { n: T -> cache.getOrPut(n) { original(n)} }
}

class Memoize<T, R>(val func: (T) -> R) {
    val cache = mutableMapOf<T, R>()
    operator fun getValue(thisRef: Any?, property: KProperty<*>) =
        {n: T -> cache.getOrPut(n) { func(n) } }
}

val prices = mapOf(1 to 2, 2 to 4, 3 to 6, 4 to 7, 5 to 10, 6 to 17, 7 to 17)
val maxPrice: (Int) -> Int by Memoize { length: Int ->
    val priceAtLength = prices.getOrDefault(length, 0)
    (1 until length).fold(priceAtLength) {
            max, cutLength ->
        val cutPrice = maxPrice(cutLength) + maxPrice(length - cutLength)
        Math.max(cutPrice, max)
    }
}

fun main(){
    println(sort(listOf(12, 5, 15, 12, 8, 19)))
    println(factorialRec(3))

    println(measureTimeMillis { fib(40)})
    println(measureTimeMillis { fib(45)})

    lateinit var fib: (Int) -> Long
    fib = {
            n: Int ->
        when (n) {
            0, 1 -> 1L
            else -> fib(n - 1) + fib(n - 2)
        }
    }.memoize()
    println(measureTimeMillis { fib(45) })
    println(measureTimeMillis { fib(500) })

    val fib2: (Int) -> Long by Memoize { n: Int ->
        when(n) {
            0, 1 -> 1L
            else -> fib(n - 1) + fib(n - 2)
        }
    }

    println(measureTimeMillis { fib2(45) })
    println(measureTimeMillis { fib2(500) })


    for (i in 1..7) {
        println("For length $i max price is ${maxPrice(i)}")
    }
}

class Any {
}