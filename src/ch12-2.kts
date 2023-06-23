import java.lang.StringBuilder

val format = "%-10s%-10s%-10s%-10s"
val str = "context"
val result6 = "RESULT"
fun toString() = "lexical"
println(String.format("%-10s%-10s%-10s-%-10s%-10s",
    "Method", "Argument", "Receiver", "Return", "Result"))
println("=========================")
val result1 = str.let { arg ->
    print(String.format(format, "let", arg, this, result6))
    result6
}
println(String.format("%-10s", result1))

val result2 = str.also { arg ->
    print(String.format(format, "also", arg, this, result6))
    result6
}
println(String.format("%-10s", result2))

val result3 = str.run {
    print(String.format(format, "run", "N/A", this, result6))
    result6
}
println(String.format("%-10s", result3))

val result4 = str.apply {
    print(String.format(format, "apply", "N/A", this, result6))
    result6
}
println(String.format("%-10s", result4))

class Mailer {
    val details = StringBuilder()
    fun from(addr: String) = details.append("from $addr...\n")
    fun to(addr: String) = details.append("to $addr...\n")
    fun subject(line: String) = details.append("subject $line...\n")
    fun body(message: String) = details.append("body $message...\n")
    fun send() = "...sending...\n$details"
}

val mailer = Mailer()
mailer.from("builder@agiledeveloper.com")
mailer.to("venkats@agiledeveloper.com")
mailer.subject("Your code sucks")
mailer.body("...detalis...")
val result = mailer.send()
println(result)

val mailer2 =
    Mailer()
        .apply { from("builder@agiledeveloper.com") }
        .apply { to("venkats@agiledeveloper.com")}
        .apply { subject("Your code sucks") }
        .apply { body("...details...") }
val resultt = mailer.send()
println(resultt)

val mailer3 = Mailer().apply {
    from("builder@agiledeveloper.com")
    to("venkats@agiledeveloper.com")
    subject("Your code sucks")
    body("...details...")
}
val resultt2 = mailer.send()
println(resultt2)

var resultt3 = Mailer().run {
    from("builder@agiledeveloper.com")
    to("venkats@agiledeveloper.com")
    subject("Your code sucks")
    body("...details...")
    send()
}
println(resultt3)

fun createMailer() = Mailer()
fun prepareAndSend(mailer: Mailer) = mailer.run {
    from("builder@agiledeveloper.com")
    to("venkats@agiledeveloper.com")
    subject("Your code sucks")
    body("...details...")
    send()
}

val mailer4 = createMailer()
val resultt4 = prepareAndSend(mailer4)
println(resultt4)

val resullllllt = createMailer().let { mailer ->
    prepareAndSend(mailer)
}

val resullllllt2 = createMailer().let {
    prepareAndSend(it)
}

val resullllllt3 = createMailer().let(::prepareAndSend)

fun prepareMailer(mailer: Mailer):Unit {
    mailer.run{
        from("builder@agiledeveloper.com")
        to("venkats@agiledeveloper.com")
        subject("Your code sucks")
        body("...details...")
    }
}
fun sendMail(mailer: Mailer): Unit {
    mailer.send()
    println("Mail send")
}

val mailer5 = createMailer()
prepareMailer(mailer5)
sendMail(mailer5)

createMailer()
    .also(::prepareMailer)
    .also(::sendMail)

var length = 100

//var printIt: (Int) -> Unit = { n: Int ->
//    println("n is $n, length is $length")
//}
//printIt(6)
var printIt: String.(Int) -> Unit = { n: Int ->
    println("n is $n, length is $length")
}
"Hello".printIt(6)

println()
fun top(func: String.() -> Unit) = "hello".func()
fun nested(func: Int.() -> Unit) = (-2).func()
top {
    println("In outer lambda $this and $length")
    nested {
        println("In inner lambda $this and ${toDouble()}")
        println("from inner through receiver of outer: ${length}")
        println("from inner to outer reciver ${this@top}")
        println("from inner to outer reciver ${this@top.length}")
    }
}




