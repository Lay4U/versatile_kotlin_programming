//fun greet() = "hello"
//fun greet(): String = "hello"
//println(greet())

fun sayHello() = println("Well, hello")
val message: Unit = sayHello()
println("The result of SayHello is $message")

fun greet(name: String): String = "Hello $name"
println(greet("Eve"))

fun max(numbers: IntArray): Int {
    var large = Int.MIN_VALUE
    for (number in numbers) {
        large = if (number > large) number else large
    }
    return large
}

println(max(intArrayOf(1, 5, 2, 12, 7, 3)))

fun f1() = 2
fun f2() = { 2 }
fun f3(factor: Int) = { n: Int -> n * factor }
println(f1())
println(f2())
println(f2()())
println(f3(2))
println(f3(2)(3))

fun greet(name: String, msg: String = "Hello"): String = "$msg $name"
println(greet("Eve"))
println(greet("Eve", "Howdy"))

fun greet2(name: String, msg: String = "Hi ${name.length}") = "$msg $name"
println(greet2("Scott", "Howdy"))
println(greet2("Scott"))

fun createPerson(name: String, age: Int = 1, height: Int, weight: Int){
    println("$name $age $height $weight")
}

createPerson("Jake", 12, 152, 43)
createPerson(name = "Jake", age = 12, weight = 43, height = 152)


fun max2(vararg numbers: Int): Int {
    var large = Int.MIN_VALUE
    for (number in numbers) {
        large = if (number > large) number else large
    }
    return large
}

val values = intArrayOf(1, 21 ,3)
fun spread(vararg numbers: Int){
    for (number in numbers) {
        println(number)
    }
}
spread(*values)

fun getFullName() = Triple("Jake", "Scott", "Hans")

val result = getFullName()
val firstName = result.first
val middleName = result.second
val lastName = result.third
println("$firstName $middleName $lastName")

val (first, middle, last) = getFullName()
println("$first $middle $last")

