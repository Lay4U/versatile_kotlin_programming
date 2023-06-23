import java.lang.StringBuilder

//bigInteger1.multiply(bigInteger2)
//bigInteger1 * bigInteger2

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
    Pair(first + other.first, second + other.second)

data class Complex(val real: Int, val imaginary: Int) {
    operator fun times(other: Complex) =
        Complex(real * other.real - imaginary * other.imaginary,
            real * other.imaginary + imaginary * other.real)

    private fun sign() = if (imaginary < 0) "-" else "+"
    override fun toString() = "$real ${sign()} ${kotlin.math.abs(imaginary)}i"
}

println(Complex(4, 2) * Complex(-3, 4))
println(Complex(1, 2) * Complex(-3, 4))

class Counter(val value: Int){
    operator fun inc() = Counter(value + 1)
    operator fun dec() = Counter(value - 1)
    override fun toString() = "${value}"
}

var counter = Counter(2)
println(counter)
println(++counter)
print(counter)
println(counter++)
println(counter)

data class Point2(val x: Int, val y: Int)
data class Circle(val cx: Int, val cy: Int, val radius: Int)

operator fun Circle.contains(point2: Point2) =
    (point2.x - cx) * (point2.x - cx) + (point2.y - cy) * (point2.y - cy) <= radius * radius

val circle = Circle(100, 100, 25)
val point1 = Point2(110, 110)
val point2 = Point2(10, 100)
println(circle.contains(point1))
println(circle.contains(point2))

println(point1 in circle)
println(point2 in circle)

val Circle.area: Double get() = Math.PI * radius * radius

val circle2 = Circle(100, 100, 25)
println("Area is ${circle.area}")

fun String.isPalindrome(): Boolean {
    return reversed() == this
}

fun String.shout() = toUpperCase()

val str = "dad"
println(str.isPalindrome())
println(str.shout())

operator fun ClosedRange<String>.iterator() =
    object: Iterator<String> {
        private val next = StringBuilder(start)
        private val last = endInclusive
        override fun hasNext() =
            last >= next.toString() && last.length >= next.length
        override fun next(): String {
            val result = next.toString()
            val lastCharacter = next.last()
            if(lastCharacter < Char.MAX_VALUE) {
                next.setCharAt(next.length - 1, lastCharacter + 1)
            }else{
                next.append(Char.MIN_VALUE)
            }
            return result
        }
    }


for (word in "hell".."help") { print("$word, ") }


fun String.Companion.toURL(link: String) = java.net.URL(link)

val url: java.net.URL = String.toURL("http://www.google.com")

class Point(x: Int, y: Int){
    private val pair = Pair(x, y)
    private val firstsign = if (pair.first < 0) "" else "+"
    private val secondsign = if (pair.second < 0) "" else "+"
    override fun toString() = pair.point2String()
    fun Pair<Int, Int>.point2String() =
        "(${firstsign}${first}, ${this@Point.secondsign}${this.second}"
}

fun <T, R, U> ((T) -> R).andThen(next: (R) -> U): (T) -> U =
    { input: T -> next(this(input))}

//operator infix fun Circle.contains(point: Point): Boolean =
//    (point.x - cx) * (point.x - cx) + (point.y - cy) * (point.y - cy) <= radius * radius


