package com.lay.versatile_kotlin_programming

import kotlinx.coroutines.*
import java.util.concurrent.Executors

fun task1() {
    println("start task1 in Thread ${Thread.currentThread()}")
    println("end task1 in Thread ${Thread.currentThread()}")
}

fun task2() {
    println("start task2 in Thread ${Thread.currentThread()}")
    println("end task2 in Thread ${Thread.currentThread()}")
}

class Compute{
    fun compute1(n: Long): Long = n * 2
    suspend fun compute2(n: Long): Long{
        val factor = 2
        println("$n received : Thread: ${Thread.currentThread()}")
        delay(n * 1000)
        val result = n * factor
        println("$n, returning $result: Thread: ${Thread.currentThread()}")
        return result
    }
}

fun run2() = runBlocking<Unit> {
    val compute = Compute()
    launch(Dispatchers.Default) {
        compute.compute2(2)
    }
    launch(Dispatchers.Default) {
        compute.compute2(1)
    }
}

fun main() {
    run()
    runBlocking()
    println()
    launch()
    interleave()
    context()
    singleThread()
    multiThread()
    coroutinestart()
    withContext()
    asyncawait()
    run2()
    primes()
    forstringrange()

}

private fun forstringrange() {
    for (word in "hell".."help") {
        println(word)
    }
}

operator fun ClosedRange<String>.iterator(): Iterator<String> = iterator {
    val next = StringBuilder(start)
    val last = endInclusive
    while (last >= next.toString() && last.length >= next.length) {
        val result = next.toString()
        val lastCharacter = next.last()
        if (lastCharacter < Char.MAX_VALUE) {
            next.setCharAt(next.length - 1, lastCharacter + 1)
        } else{
            next.append(Char.MIN_VALUE)
        }
        yield(result)
    }
}

private fun primes() {
    for (prime in primes(start = 17)) {
        println("Received $prime")
        if (prime > 30) break
    }
}

fun primes(start: Int): Sequence<Int> = sequence {
    println("Starting to look")
    var index = start
    while (true) {
        if (index > 1 && ( 2 until index).none { i -> index % i == 0 }) {
            yield(index)
            println("Generating next after $index")
        }
        index++
    }
}



private fun asyncawait() {
    runBlocking {
        val count: Deferred<Int> = async(Dispatchers.Default) {
            println("fetching in ${Thread.currentThread()}")
            Runtime.getRuntime().availableProcessors()
        }
        println("Called the function in ${Thread.currentThread()}")
        println("Number of cores is ${count.await()}")
    }
}

private fun withContext() {
    runBlocking(CoroutineName("top")) {
        println("starting in Thread ${Thread.currentThread()}")
        withContext(Dispatchers.Default) { task1() }
        launch (Dispatchers.Default + CoroutineName("task runner")) { task2() }
        println("ending in Thread ${Thread.currentThread()}")
    }
}

private fun coroutinestart() {
    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        .asCoroutineDispatcher().use { context ->
            println("start")
            runBlocking {
                @OptIn(ExperimentalCoroutinesApi::class)
                launch(context = context, start = CoroutineStart.UNDISPATCHED) { task1() }
                launch { task2() }
                println("called task1 and task2 from ${Thread.currentThread()}")
            }
            println("done")
        }
}

private fun multiThread() {
    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        .asCoroutineDispatcher().use { context ->
        println("start")
        runBlocking {
            launch(context) { task1() }
            launch { task2() }
            println("called task1 and task2 from ${Thread.currentThread()}")
        }
        println("done")
    }
}

private fun singleThread() {
    Executors.newSingleThreadExecutor().asCoroutineDispatcher().use { context ->
        println("start")
        runBlocking {
            launch(context) { task1() }
            launch { task2() }
            println("called task1 and task2 from ${Thread.currentThread()}")
        }
        println("done")
    }
}

private fun context() {
    runBlocking {
        launch(Dispatchers.Default) { task1() }
        launch { task2() }
        println("called task1 and task2 from ${Thread.currentThread()}")
    }
}

private fun interleave() {
    suspend fun task1() {
        println("start task1 in Thread ${Thread.currentThread()}")
        yield()
        println("end task1 in Thread ${Thread.currentThread()}")
    }

    suspend fun task2() {
        println("start task2 in Thread ${Thread.currentThread()}")
        yield()
        println("end task2 in Thread ${Thread.currentThread()}")
    }
    println("start")
    runBlocking {
        launch { task1() }
        launch { task2() }
        println("called task1 and task2 from ${Thread.currentThread()}")
    }
    println("done")
}

private fun launch() {
    runBlocking {
        launch { task1() }
        launch { task2() }
        println("called task1 and task2 from ${Thread.currentThread()}")
    }
    println("done")
}

private fun runBlocking() {
    runBlocking {
        task1()
        task2()
    }
}

private fun run() {
    println("start")
    run {
        task1()
        task2()
        println("called task1 and task2 from ${Thread.currentThread()}")
    }
    println("done")
}
