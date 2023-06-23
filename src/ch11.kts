val numbers = listOf(10, 12, 15, 17, 18, 19)

for (i in numbers) {
    if (i % 2 == 0){
        print("$i, ")
    }
}
println()
numbers.filter { e -> e % 2 == 0 }
    .forEach { e -> print("$e, ")}

val doubled = mutableListOf<Int>()
for (i in numbers) {
    if (i % 2 == 0) {
        doubled.add(i * 2)
    }
}
println(doubled)

val doubledEven = numbers.filter { e -> e % 2 == 0 }
    .map { e -> e * 2 }
println(doubledEven)

data class Person(val firstName: String, val age: Int)
val people = listOf(
    Person("Sara", 12),
    Person("Jill", 51),
    Person("Paula", 23),
    Person("Paul", 25),
    Person("Mani", 12),
    Person("Jack", 70),
    Person("Sue", 10)
)

val result = people.filter { person -> person.age > 20 }
    .map { person -> person.firstName }
    .map { name -> name.toUpperCase() }
    .reduce { names, name -> "$names, $name"}
//    .joinToString(", ")
println(result)

val totalAge = people.map { person -> person.age }
    .reduce { total, age -> total + age }
//    .sum()
println(totalAge)

val nameOfFirstAdult = people.filter { person -> person.age > 17 }
    .map { person -> person.firstName }
    .first()
//    .last()
println(nameOfFirstAdult)

val families = listOf(
    listOf(Person("Jack", 40), Person("Jill", 40)),
    listOf(Person("Eve", 18), Person("Adam", 18)))
println(families)
println(families.size)
println(families.flatten().size)

val namesAndReversed = people.map { person -> person.firstName }
    .map(String::toLowerCase)
    .map { name -> listOf(name, name.reversed())}
    .flatten()
println(namesAndReversed.size)

val namesAndReversedFlatMap = people.map { person -> person.firstName }
    .map(String::toLowerCase)
    .flatMap { name -> listOf(name, name.reversed()) }
println(namesAndReversedFlatMap)

val namesSortedByAge = people.filter { person -> person.age > 17 }
    .sortedBy { person -> person.age }
//    .sortedByDescending { person -> person.age }
    .map { person -> person.firstName }
println(namesSortedByAge)

val groupBy1stLetter = people.groupBy { person -> person.firstName.first() }
println(groupBy1stLetter)

val namesBy1stLetter = people.groupBy( { person -> person.firstName.first() }) {
    person -> person.firstName
}
println(namesBy1stLetter)

fun isAdult(person: Person): Boolean {
    println("isAdult called for ${person.firstName}")
    return person.age > 17
}
fun fetchFirstName(person: Person): String {
    println("fetchFirstName called for ${person.firstName}")
    return person.firstName
}
//val nameOfFirstAdult2 = people
//    .filter(::isAdult)
//    .map(::fetchFirstName)
//    .first()
//println(nameOfFirstAdult)

val nameOfFirstAdult2 = people.asSequence()
    .filter(::isAdult)
    .map(::fetchFirstName)
    .first()
println(nameOfFirstAdult)

fun isPrime(n: Long) = n > 1 && (2 until n).none { i -> n % i == 0L}

tailrec fun nextPrime(n: Long): Long = if (isPrime(n + 1)) n + 1 else nextPrime(n + 1)

val primes = generateSequence(5, ::nextPrime)

println(primes.take(10).toList())

val primes2 = sequence {
    var i: Long = 0
    while (true) {
        i++
        if (isPrime(i)){
            yield(i)
        }
    }
}

println(primes2.drop(2).take(8).toList())

