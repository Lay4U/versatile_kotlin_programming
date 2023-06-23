var doubleOfEven = mutableListOf<Int>()
for (i in 1..10) {
    if (i % 2 == 0) {
        doubleOfEven.add(i * 2)
    }
}


//fun isPrime(n: Int) = n > 1 && (2 until n).none({ i: Int -> n % i == 0})
fun isPrime(n: Int) = n > 1 && (2 until n).none { n % it == 0 }

//fun walk1To(action: (Int) -> Unit, n: Int) = (1..n).forEach{ action(it) }
fun walk1To(n: Int, action: (Int) -> Unit) = (1..n).forEach { action(it) }
//walk1To({ i -> print(i) }, 5)

walk1To(5) { i -> print(i) }

walk1To(5) { i ->
    print(i)
}

walk1To(5) { print(it) }
println()
fun walk1To2(n: Int, action: (Int) -> Unit) = (1..n).forEach { action(it) }
walk1To2(5) { i -> print(i)}
walk1To2(5, ::print)
walk1To2(5, System.out::println)

fun send(n: Int) = println(n)
walk1To2(5) { i -> send(i) }
walk1To2(5, this::send)

object Terminal {
    fun write(value: Int) = println(value)
}

walk1To2(5) { i -> Terminal.write(i) }
walk1To2(5, Terminal::write)

val names = listOf("Pam", "Pat", "Paul", "Paula")
println(names.find {name -> name.length == 5})
println(names.find{ name -> name.length==4})

fun predicateOfLength(length: Int): (String) -> Boolean {
    return { input: String -> input.length === length }
}

println(names.find(predicateOfLength(5)))
println(names.find(predicateOfLength(4)))

fun predicateOfLength2(length: Int) = { input: String -> input.length == length }

val checkLength5 = { name: String -> name.length === 5 }
println(names.find(checkLength5))

val checkLength5_2: (String) -> Boolean = { name -> name.length == 5 }

val checkLength5_3: (String) -> Boolean = { name: String -> name.length == 5 }
val checkLength5_4 = fun(name: String): Boolean { return name.length == 5}
names.find(fun(name: String): Boolean { return name.length == 5})

//val factor = 2
//val doubleIt = { e: Int -> e * factor }

var factor = 2
val doubled = listOf(1, 2).map { it * factor }
val doubledAlso = sequenceOf(1, 2).map { it * factor }
factor = 0
doubled.forEach { println(it) }
doubledAlso.forEach { println(it) }

fun invokeWith(n: Int, action: (Int) -> Unit) {
    println("enter invokedWith $n")
    action(n)
    println("exit invokedWith $n")
}

fun caller() {
    (1..3).forEach { i ->
        println("in forEach for$i")
        if (i == 2) { return }
        invokeWith(i) here@ {
            println("enter for $it")

//            if (it == 2) { return@invokeWith }
            if (it == 2) { return@here }

            println("exit for $it")
        }
    }

    println("end of caller")
}

caller()
println("after return from caller")

//fun invokeTwo(
inline fun invokeTwo(
    n: Int,
    action1: (Int) -> Unit,
    action2: (Int) -> Unit
): (Int) -> Unit{
    println("enter invokeTwo $n")
    action1(n)
    action2(n)
    println("exit invokeTwo $n")
    return { _: Int -> println("lambda returned from invokeTwo")}
}

fun callInvokeTwo() {
    invokeTwo(1, { i -> report(i) }, { i -> report(i) })
}
callInvokeTwo()

fun report(n: Int) {
    println("")
    println("called with $n, ")
    val stackTrace = RuntimeException().getStackTrace()
    println("Stack depth: ${stackTrace.size}")
    println("Partial listing of the stack:")
    stackTrace.forEach(::println)
}
