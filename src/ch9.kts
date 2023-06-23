import kotlin.properties.Delegates
import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable
import kotlin.reflect.KProperty

interface Worker {
    fun work()
    fun takeVacation()
    fun fileTimeSheet() = println("Why? Really?")
}

interface Assistant {
    fun doChores()
    fun fileTimeSheet() = println("No escape from that")
}

open class JavaProgrammer : Worker {
    override fun work() = println("...write Java...")
    override fun takeVacation() = println("...code at the beach...")
}

class CSharpProgrammer : Worker {
    override fun work() = println("...write c#...")
    override fun takeVacation() = println("...code at the ranch...")
}

//class Manager: JavaProgrammer()
//val doe = Manager()
//doe.work()
//val coder: JavaProgrammer = doe

//class Manager(val worker: Worker){
//    fun work() = worker.work()
//    fun takeVacation() = worker.work()
//}
//val doe = Manager(JavaProgrammer())
//doe.work()

//class Manager(): Worker by JavaProgrammer()

//val doe = Manager()
//doe.work()
//val coder: JavaProgrammer = doe // tpye mismatch

//class Manager(val staff: Worker): Worker by staff{
//    fun meeting() = println("organizing with ${staff::class.simpleName}")
//}

//val doe = Manager(CSharpProgrammer())
//val roe = Manager(JavaProgrammer())
//doe.work()
//doe.meeting()
//roe.work()
//roe.meeting()

class DepartmentAssistant : Assistant {
    override fun doChores() = println("routine stuff")
}

class Manager(val staff: Worker, val assistant: Assistant) :
    Worker by staff, Assistant by assistant {
    override fun takeVacation() = println("of course")
    override fun fileTimeSheet() {
        print("manually forwarding this...")
        assistant.fileTimeSheet()
    }
}

val doe = Manager(CSharpProgrammer(), DepartmentAssistant())
doe.work()
doe.takeVacation()
doe.doChores()
doe.fileTimeSheet()

//class PoliteString(var content: String) {
//    operator fun getValue(thisRef: Any?, property: KProperty<*>) =
//        content.replace("stupid", "s*****")
//    operator fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
//        content = value
//    }
//}

//var comment: String by PoliteString("Some nice message")
//println(comment)
//comment = "This is stupid"
//println(comment)
//println("comment is of length: ${comment.length}")

//fun beingpolite(content: String) = PoliteString(content)


class PoliteString(val dataSource: MutableMap<String, Any>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>) =
        (dataSource[property.name] as? String)?.replace("stupid", "s*****") ?: ""

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        dataSource[property.name] = value
    }
}

class PostComment(dataSource: MutableMap<String, Any>) {
    val title: String by dataSource
    var likes: Int by dataSource
    val comment: String by PoliteString(dataSource)
    override fun toString() = "Title: $title Likes: $likes Comment: $comment"
}

val data = listOf(
    mutableMapOf(
        "title" to "Using Delegation",
        "likes" to 2,
        "comment" to "Keep it simple, stupid",
    ),
    mutableMapOf(
        "title" to "Using Inheritance",
        "likes" to 1,
        "comment" to "Prefer Deletgation where possible"
    )
)

val forPost1 = PostComment(data[0].toMutableMap())
val forPost2 = PostComment(data[1].toMutableMap())
forPost1.likes++
println(forPost1)
println(forPost2)

fun getTemperature(city: String): Double {
    println("fetch from webservice for $city")
    return 30.0
}

val showTemperature = false
val city = "Boulder"
if (showTemperature && getTemperature(city) > 20)
    println("Warm")
else
    println("Nothing to report")

/*
val temperature = getTEmperature(city)
if (showTemperature && temperature > 20)
    println("Warm")
else
    println("Nothing to report")
*/

val temperature by lazy { getTemperature(city) }
if (showTemperature && temperature > 20)
    println("Warm")
else
    println("Nothing to report")

var count_observable by observable(0) { property, oldValue, newValue ->
    println("Property: $property Old: $oldValue New: $newValue")
}
println("The value of count is: $count_observable")
count_observable++
println("The value of count is: $count_observable")
count_observable--
println("The value of count is: $count_observable")

var count by vetoable(0) { property, oldValue, newValue ->
    println("Property: $property Old: $oldValue New: $newValue")
    newValue > oldValue
}
println("The value of count is: $count")
count++
println("The value of count is: $count")
count--
println("The value of count is: $count")


