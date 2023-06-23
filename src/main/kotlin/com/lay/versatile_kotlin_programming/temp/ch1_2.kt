package com.lay.versatile_kotlin_programming.temp


fun hello() {
    println("Hello World")
}

fun script() {
    java.io.File(".")
        .walk()
        .filter { file -> file.extension == "kt" }
        .forEach { println(it) }
}

fun type() {
    val greet = "hello"
    println(greet)
    println(greet::class)
    println(greet::javaClass)
}

fun nofluff() {
    println("nofluff called")
    throw RuntimeException("oops")
}

fun executenofluff() {
    println("not in a function, calling nofluff")
    try {
        nofluff()
    } catch (ex: Exception) {
        val stackTrace = ex.getStackTrace()
        println(stackTrace[0])
        println(stackTrace[1])
    }
}

fun nocatch() {
    println("Lemme take a nap")
    Thread.sleep(1000)
    println("ah that feels good")
}

fun unused() {
    fun compute(n: Int) = 0
    println(compute(4))
}

fun mutate() {
    var factor = 2
    fun doubleIt(n: Int) = n * factor
    factor = 0
    println(doubleIt(2))
}

fun equality() {
    println("hi" == "hi")
    println("hi" == "Hi")
    println(null == "hi")
    println("hi" == null)
    println(null == null)
}

fun stringTemplate() {
    val price = 12.25
    val taxRate = 0.08
    val output = "The amount $price after tax comes to $${price * (1 + taxRate)}"
    val disclaimer = "The amount is in US$, that's right in \$only"
    println(output)
    println(disclaimer)
}

fun mutateConfusion() {
    var factor = 2
    fun doubleIt(n: Int) = n * factor
    var message = "The factor is $factor"
    println(message)
    factor = 0
    println(doubleIt(2))
    println(message)
}

fun memo() {
    val name = "Eve"

    val memo = """
        Dear $name, a quick reminder about the
        party we have scheduled next Tuesday at
        The 'Low Ceremony Cafe' at Noon. | Please plan to."""

    println(memo)
}

fun createMemoFor(name: String): String {
    if (name == "Eve") {
        val memo = """Dear $name, a quick reminder about the
            |party we have scheduled next Tuesday at
            |The 'Low Ceremony Cafe' at Noon. | Please plan to.
        """
        return memo.trimMargin()
    }

    return ""
}

fun canVote(name: String, age: Int): String {
    var status: String
    if (age > 17) {
        status = "yes, please vote"
    } else {
        status = "nope, please come back"
    }

    return "$name, $status"
}


fun status(name: String, age: Int): String = if (age > 17) "$name yes, pleas vote" else "$name nope, please come back"

fun tryExpr(blowup: Boolean): Int {
    return try {
        if (blowup) {
            throw RuntimeException("fail")
        }
        2
    } catch (ex: Exception) {
        4
    } finally {
        6
    }
}


fun main() {
    hello()
    script()
    type()
    executenofluff()
    nocatch()
    unused()
    mutate()
    equality()
    stringTemplate()
    mutateConfusion()
    memo()
    println(createMemoFor("Eve"))
    println(canVote("Eve", 12))
    println(status("Eve", 12))
    println(tryExpr(false))
    println(tryExpr(true))
}