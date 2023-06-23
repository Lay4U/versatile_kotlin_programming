import java.lang.RuntimeException

fun drawCircle() {
    val circle = object {
        val x = 10
        val y = 20
        val radius = 30
    }

    println("Circle x: ${circle.x} y: ${circle.y} radius: ${circle.radius}")
}

drawCircle()

fun createRunnable(): Runnable{
    val runnable = object: Runnable {
        override fun run() { println("You called...")}
    }
    return runnable
}

val aRunnable = createRunnable()
aRunnable.run()


fun createRunnable2(): Runnable = object: Runnable, AutoCloseable {
    override fun run() { println("You called...")}
    override fun close() { println("closing...")}
}

object Util {
    fun numberOfprocessor() = Runtime.getRuntime().availableProcessors()
}
println(Util.numberOfprocessor())

object Sun : Runnable {
    val radiusInKm = 696000
    var coreTemperatureInC = 15000000
    override fun run() { println("spin..")}
}
fun moveIt(runnable: Runnable){
    runnable.run()
}
println(Sun.radiusInKm)
moveIt(Sun)

class Car_0(val yearOfMake: Int)
// equals public class Car public constructor(public val yearOfMake: Int)

val car_0 = Car_0(2019)
println(car_0.yearOfMake)

class Car_1(val yaearOfMake: Int, var color: String)
val car_1 = Car_1(2019, "Red")
car_1.color = "Green"
println(car_1.color)

class Car_2(val yeaerOfMake: Int, theColor: String) {
    var fuelLevel = 100
    var color = theColor
        set(value) {
            if (value.isBlank()){
                throw RuntimeException("no empty, please")
            }
            field = value
        }
}

var car_2 = Car_2(2019, "Red")
car_2.color = "Green"
car_2.fuelLevel--
println(car_2.fuelLevel)
try {
    car_2.color = ""
}catch(ex: Exception){
    println(ex.message)
}
println(car_2.color)


class Car_3(val yearOfMake: Int, theColor: String) {
    var fuelLevel = 100
        private set
    var color = theColor
    set(value) {
        if (value.isBlank()) {
            throw RuntimeException("no empty, please")
        }
        field = value
    }

    init {
        if (yearOfMake < 2020) { fuelLevel = 90 }
    }

    var temp = if (yearOfMake < 2020) 90 else 100
}

class Person(val first: String, val last: String){
    var fulltime = true
    var location: String = "-"
    constructor(first: String, last: String, fte: Boolean): this(first, last) {
        fulltime = fte
    }
    constructor(
        first: String, last: String, loc: String): this(first, last, false) {
            location = loc
        }
    override fun toString() = "$first $last $fulltime $location"

    internal fun fullName() = "$last, $first"
    private fun yearsOfService(): Int = throw RuntimeException("not implemented")
}
println(Person("Jane", "Doe"))
println(Person("Jane", "Doe", false))
println(Person("Jane", "Doe", "home"))

inline class SSN(val id: String)
fun receiveSSN(ssn: SSN){
    println("Received $ssn")
}

receiveSSN(SSN("111-11-1111"))

class MachineOperator private constructor (val name: String){
    fun checkIn() = checkedIn++
    fun checkout() = checkedOut--
//    companion object {
//    companion object MachineOperatorFactory {
//        var checkedIn = 0
//        var checkedOut = 0
//        fun minimumBreak() = "15 minutes every 2 hours"
//    }

    companion object{
        var checkedIn = 0
        var checkedOut = 0
        fun minimumBreak() = "15 minutes every 2 hours"

        fun create(name: String): MachineOperator {
            val instance = MachineOperator(name)
            instance.checkIn()
            return instance
        }
    }
}

//MachineOperator("Mater").checkIn()
println(MachineOperator.minimumBreak())
println(MachineOperator.checkedIn)

//val ref = MachineOperator.Companion
//val ref = MachineOperator.MachineOperatorFactory

val operator = MachineOperator.create("Mater")
println(operator.checkIn())

class PriorityPair<T: Comparable<T>>(member1: T, member2: T){
    val first: T
    val second: T
    init {
        if (member1 >= member2) {
            first = member1
            second = member2
        }else {
            first = member2
            second = member1
        }
    }
    override fun toString() = "${first}, ${second}"
}


println(PriorityPair(2, 1))
println(PriorityPair("A","B"))

data class Task(val id: Int, val name: String, val completed: Boolean, val assigned: Boolean){

}
val task1 = Task(1, "Create Project", false, true)
println(task1)
println("Name: ${task1.name}")


val task1Completed = task1.copy(completed = true, assigned = false)
println(task1Completed)


val (id, _, _, isAssigned) = task1
println("Id: $id Assigend: $isAssigned")


