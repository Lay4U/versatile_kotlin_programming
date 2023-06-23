val oneToFive: IntRange = 1..5
val aToE: CharRange = 'a'..'e'
val seekHelp: ClosedRange<String> = "hell".."help"

for (i in  1..5) { print("$i, ") }
println()
for (ch in 'a'..'e') { print("$ch, ") }
println()
//for (word in "hell".."help") { print("$word, ") } Third party class injecting

for (i in 5.downTo(1)) { print("$i, ") }
println()
for (i in 5 downTo 1) { print("$i, ") }
println()

for (i in 1 until 5) { print("$i, ") }
println()
for(i in 1 until 10 step 3) { print("$i, ") }
println()
for(i in 10 downTo 0 step 3) { print("$i, ") }
println()

for (i in (1..9).filter { it % 3 == 0 || it % 5 == 0}) { print("$i, ") }
println()

val array = arrayOf(1,2,3)
for (e in array) { print("$e, ") }
println()

val list = listOf(1,2,3)
println(list.javaClass)
for(e in list) { print("$e, ") }
println()

val names = listOf("Tom", "Jerry", "Spike")
for (index in names.indices) {
    println("Position of ${names.get(index)} is $index")
}
for ((index, name) in names.withIndex()) {
    println("Position of $name is $index")
}

fun isAlive_if(alive: Boolean, numberOfLiveNeighbors: Int): Boolean {
    if (numberOfLiveNeighbors < 2) { return false }
    if (numberOfLiveNeighbors > 3) { return false }
    if (numberOfLiveNeighbors == 3) { return true }
    return alive && numberOfLiveNeighbors == 2
}

fun isAlive(alive: Boolean, numberOfLiveNeighbors: Int): Boolean = when{
    numberOfLiveNeighbors < 2 -> false
    numberOfLiveNeighbors > 3 -> false
    numberOfLiveNeighbors == 3 -> true
    else -> alive && numberOfLiveNeighbors == 2
}

fun whatToDo(dayOfWeek: Any) = when (dayOfWeek) {
    "Saturday", "Sunday" -> "Relax"
    in listOf("Monday", "Tuesday", "Wednesday", "Thursday") -> "Work hard"
    in 2..4 -> "Work hard"
    "Friday" -> "Party"
    is String -> "Waht?"
    else -> "No Clue"
}

println(whatToDo("Sunday"))
println(whatToDo("Wednesday"))
println(whatToDo(3))
println(whatToDo("Friday"))
println(whatToDo("Munday"))
println(whatToDo(8))

fun printWhatToDo(dayOfWeek: Any){
    when (dayOfWeek){
        "Saturday", "Sunday" -> println("Relax")
        in listOf("Monday", "Tuesday", "Wednesday", "THursday") -> println("Work hard")
        in 2..4 -> println("Work hard")
        "Friday" -> println("Party")
        is String -> println("Waht?")
    }
}

fun systemInfo_old(): String{
    val numberOfCores = Runtime.getRuntime().availableProcessors()
    return when (numberOfCores) {
        1 -> "1 core, packing this one to the museum"
        in 2..16 -> "You have $numberOfCores cores"
        else -> "$numberOfCores cores!, I want your machine"
    }
}

fun systemInfo(): String =
    when (val numberOfCores = Runtime.getRuntime().availableProcessors()) {
        1 -> "1 core, packing this one to the museum"
        in 2..16 -> "You have $numberOfCores cores"
        else -> "$numberOfCores cores!, I want your machine"
    }

println(systemInfo())



