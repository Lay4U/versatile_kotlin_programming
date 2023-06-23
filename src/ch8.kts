import java.lang.RuntimeException

interface Remote {
    fun up()
    fun down()
    fun doubleUp(){
        up()
        up()
    }

    companion object{
        fun combine(first: Remote, second: Remote): Remote = object: Remote{
            override fun up() {
                first.up()
                second.up()
            }
            override fun down(){
                first.down()
                second.down()
            }
        }
    }
}

class TV_old {
    var volume = 0
}

class TVRemote_old(val tv: TV_old): Remote {
    override fun up() { tv.volume++ }
    override fun down() { tv.volume--}
}

val tv = TV_old()
val remote: Remote = TVRemote_old(tv)
println("Volume: ${tv.volume}")
remote.up()
println("after increasing: ${tv.volume}")
remote.doubleUp()
println("After doubleUp: ${tv.volume}")

val anotherTV = TV_old()
val combinedRemote = Remote.combine(remote, TVRemote_old(anotherTV))
combinedRemote.up()
println(tv.volume)
println(anotherTV.volume)

abstract class Musician(val name: String, val activeFrom: Int){
    abstract fun instrumentType(): String
}

class Cellist(name: String, activeFrom: Int): Musician(name, activeFrom){
    override fun instrumentType() = "String"
}
val ma = Cellist("Yo-Yo Ma", 1969)

class TV{
    //    private var volume = 0
//    val remote: Remote
//        get() = TVRemote()
//    override fun toString(): String = "Volume: ${volume}"
//    inner class TVRemote: Remote {
//        override fun up() { volume++ }
//        override fun down() { volume-- }
//        override fun toString() = "Remote: ${this@TV.toString()}"
//    }
    private var volume= 0
    val remote: Remote get() = object: Remote {
        override fun up() { volume++ }
        override fun down() { volume-- }
        override fun toString() = "Remote: ${this@TV.toString()}"
    }
    override fun toString(): String = "Volume: ${volume}"
}

val tv2 = TV()
val remote2 = tv2.remote
println("$tv")
remote2.up()
println("After increasing: $tv")
remote2.doubleUp()
println("After doubleUp: $tv")

open class Vehicle(val year: Int, open var color: String){
    open val km = 0
    final override fun toString() = "year: $year, color: $color, KM: $km"
    fun repaint(newColor: String) {
        color = newColor
    }
}

open class Car(year: Int, color: String): Vehicle(year, color){
    override var km: Int = 0
        set(value) {
            if (value < 1) {
                throw RuntimeException("KM must be positive")
            }
            field = value
        }
    fun drive(distance: Int){
        km += distance
    }
}

var car = Car(2019, "Orage")
println(car.year)
println(car.color)
car.drive(10)
println(car)
try {
    car.drive(-30)
}catch(ex: RuntimeException){
    println(ex.message)
}

class FamilyCar(year: Int, color: String): Car(year, color) {
    override var color: String
        get() = super.color
        set(value) {
            if (value.isEmpty()) {
                throw RuntimeException("Color required")
            }
            super.color = value
        }
}

val familyCar = FamilyCar(2019, "Green")
println(familyCar.color)
try{
    familyCar.repaint("")
}catch(ex: RuntimeException){
    println(ex.message)
}

//sealed class Card(val suit: String)
//class Ace(suit: String): Card(suit)
//class King(suit: String): Card(suit)
//class King(suit: String): Card(suit) {
//    override fun toString() = "King of $suit"
//}
//class Queen(suit: String): Card(suit) {
//    override fun toString() = "Queen of $suit"
//}
//class Jack(suit: String): Card(suit) {
//    override fun toString() = "Jack of $suit"
//}
//class Pip(suit: String, val number: Int): Card(suit) {
//    init {
//        if (number < 2 || number > 10) {
//            throw RuntimeException("Pip has to be between 2 adn 10")
//            }
//    }
//}

//fun process(card: Card) = when(card) {
//    is Ace -> "${card.javaClass.name} of ${card.suit}"
//    is King, is Queen, is Jack -> "$card"
//    is Pip -> "${card.number} of ${card.suit}"
//    else -> throw RuntimeException("Unknown card")
//}
//
//println(process(Ace("Diamond")))
//println(process(Queen("Clubs")))
//println(process(Pip("Spades", 2)))
//println(process(Pip("Hearts", 6)))

enum class Suit{ CLUBS, DIAMONDS, HEARTS, APADES }
sealed class Card(val suit: Suit)
class Ace(suit: Suit): Card(suit)
class King(suit: Suit): Card(suit)
class King(suit: Suit): Card(suit) {
    override fun toString() = "King of $suit"
}
class Queen(suit: Suit): Card(suit) {
    override fun toString() = "Queen of $suit"
}
class Jack(suit: Suit): Card(suit) {
    override fun toString() = "Jack of $suit"
}

enum class Suit2(val symbol: Char) {
    CLUBS('♣'),
    DIAMONDS('♦'),
    HEARTS('♥'){
        override fun display() = "$symbol $name ❤️"
    },
    SPADES('♠');
    open fun display() = "$symbol $name"
}

for (suit in Suit2.values()) {
    println(suit.display())
}


