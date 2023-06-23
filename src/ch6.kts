import java.lang.RuntimeException

//fun nickName(name: String): String {
//    if (name == "William") {
//        return "Bill"
//    }
//    return null
//}

//fun nickName(name: String): String? {
//    if (name == "William") {
//        return "Bill"
//    }
//    return null
//}

fun nickName(name: String?): String {
    if (name == "William") {
        return "Bill"
    }
//    val result = name?.reversed()?.toUpperCase()
//    return if (result == null) "Joker" else result
    return name?.reversed()?.toUpperCase() ?: "Joker"
}

fun nickName2(name: String?) = when (name) {
    "William" -> "Bill"
    null -> "Joker"
    else -> name.reversed().toUpperCase()
}

println("Nickname for William is ${nickName("William")}")
println("Nickname for Venkat is ${nickName("Venkat")}")
//println("Nickname for null is ${nickName(null)}")

class Animal(val age: Int) {
    //    override operator fun equals(other: Any?) = other is Animal
//    override operator fun equals(other: Any?):Boolean {
//        return if (other is Animal) age == other.age else false
//    }
    override operator fun equals(other: Any?) = other is Animal && age == other.age
}

val greet: Any = "hello"
val odie: Any = Animal(3)
val toto: Any = Animal(3)
println(odie == greet)
println(odie == toto)

fun fetchMessage(id: Int): Any =
    if (id == 1) "Record found" else StringBuilder("data not found")

for (id in 1..2) {
//    println("Message length: ${(fetchMessage(id) as? String).length ?: "---"}")
}


open class Fruit
class Banana : Fruit()
class Apple : Fruit()

fun receiveFruits(fruits: List<Fruit>) {
    println("Number of fruits: ${fruits.size}")
}


val bananas: List<Banana> = listOf()
receiveFruits(bananas)

//fun copyFromTo(from: Array<Fruit>, to: Array<Fruit>){
//    for (i in 0 until from.size) {
//        to[i] = from[i]
//    }
//}

//fun copyFromTo(from: Array<out Fruit>, to: Array<Fruit>){
//    for (i in 0 until from.size) {
//        to[i] = from[i]
//    }
//}

fun copyFromTo(from: Array<out Fruit>, to: Array<in Fruit>) {
    for (i in 0 until from.size) {
        to[i] = from[i]
    }
}


val fruitsBasket1 = Array<Fruit>(3) { _ -> Fruit() }
val fruitsBasket2 = Array<Fruit>(3) { _ -> Fruit() }
copyFromTo(fruitsBasket1, fruitsBasket2)

val fruitsBasket = Array<Fruit>(3) { _ -> Fruit() }
val bananaBasket = Array<Banana>(3) { _ -> Banana() }
copyFromTo(bananaBasket, fruitsBasket) // error type mismatch -> out 으로 해결

val things = Array<Any>(3) { _ -> Fruit() }
copyFromTo(bananaBasket, things)

fun <T> useAndClose(input: T)
        where T : AutoCloseable,
              T : Appendable {
    input.append("there")
    input.close()
}

val writer = java.io.StringWriter()
writer.append("hello ")
useAndClose(writer)
println(writer)

fun printValues(values: Array<*>){
    for (value in values) {
        print(value)
    }
}
printValues(arrayOf(1,2))

abstract class Book(val name: String)
class Fiction(name: String) : Book(name)
class NonFiction(name: String) : Book(name)

val books: List<Book> = listOf(
    Fiction("Moby Dic"), NonFiction("Learn to Code"), Fiction("LOTR")
)

fun <T> findFirst_old(books: List<Book>, ofClass: Class<T>): T{
    val selected = books.filter { book -> ofClass.isInstance(book) }
    if(selected.size == 0){
        throw RuntimeException("Not found")
    }
    return ofClass.cast(selected[0])
}

println(findFirst_old(books, NonFiction::class.java).name)

inline fun <reified T> findFirst(books: List<Book>): T {
    val selected = books.filter { book -> book is T}
    if(selected.size == 0){
        throw RuntimeException("Not Found")
    }
    return selected[0] as T
}

println(findFirst<NonFiction>(books).name)
