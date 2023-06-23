/*
for(int i = 0; i < names.size(); i++){
    System.out.println(names.get(i))
}
*/

val names = listOf("Tom", "Jerry")
println(names.javaClass)
for ((index, value) in names.withIndex()){
    println("$index $value")
}



val airportCodes = listOf("LAX", "SFO", "PDX", "SEA")
val temperatures = airportCodes.map { code -> code to getTemperatureAtAirport(code) }
for (temp in temperatures) {
    println("Airport: ${temp.first}: Temperature: ${temp.second}")
}

fun getTemperatureAtAirport(code: String): String = "${Math.round(Math.random() * 30) + code.count()} C"

var friends = arrayOf("Tintin", "Snowy", "Haddock", "Calculus")
println(friends::class)
println(friends.javaClass)
println("${friends[0]} and ${friends[1]}")

val numbers = intArrayOf(1,2,3)
println(numbers::class)
println(numbers.javaClass)

println(numbers.size)
println(numbers.average())

println(Array(5) { i -> (i+1) * (i+1) }.sum())

val fruits: List<String> = listOf("Apple", "Banana", "Grape")
println(fruits)

println("first's ${fruits[0]}, that's ${fruits.get(0)}")

println(fruits.contains("Apple"))
println("Apple" in fruits)

//fruits.add("Orange") // error

val fruits2 = fruits + "Orange"
println(fruits)
println(fruits2)

val noBanana = fruits - "Banana"
println(noBanana)

println(fruits::class)
println(fruits.javaClass)

val fruits3: MutableList<String> = mutableListOf("Apple", "Banana", "Grape")
println(fruits3::class)

fruits3.add("Orange")
println(fruits3)

val ffruits: Set<String> = setOf("Apple", "Banana", "Apple")
println(ffruits)

val sites = mapOf("pragprog" to "https://www.pragprog.com",
    "agiledeveloper" to "https://agiledeveloper.com")
println(sites.size)

println(sites.containsKey("agiledeveloper"))
println(sites.containsValue("http://www.example.com"))
println(sites.contains("agiledeveloper"))
println("agiledeveloper" in sites)

val pragProgSite: String? = sites.get("pragprog")

val agiledeveloper = sites.getOrDefault("agiledeveloper", "http://www.example.com")

val sitesWithExample = sites + ("example" to "http://www.example.com")
val withoutAgileDeveloper = sites - "agiledeveloper"

for (entry in sites){
    println("${entry.key} --- ${entry.value}")
}

for ((key, value) in sites) {
    println("$key --- $value")
}


