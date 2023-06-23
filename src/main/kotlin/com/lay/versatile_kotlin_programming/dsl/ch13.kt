package com.lay.versatile_kotlin_programming.dsl

import com.lay.versatile_kotlin_programming.dsl.DateUtil.Tense.ago
import com.lay.versatile_kotlin_programming.dsl.DateUtil.Tense.from_now
import java.util.*
import kotlin.system.measureTimeMillis

infix fun Int.days(timing: DateUtil.Tense) = DateUtil(this, timing)

class DateUtil(val number: Int, val tense: Tense) {
    enum class Tense {
        ago, from_now
    }

    override fun toString(): String {
        val today = Calendar.getInstance()
        when (tense) {
            ago -> today.add(Calendar.DAY_OF_MONTH, -number)
            from_now -> today.add(Calendar.DAY_OF_MONTH, number)
        }
        return today.time.toString()
    }
}


//infix fun String.meeting(block: () -> Unit) {
//    println("step 1 accomplished")
//}
//"Release Planning" meeting{}

//class Meeting
//infix fun String.meeting(block: Meeting.() -> Unit) {
//    val meeting = Meeting()
//    meeting.block()
//    println(meeting)
//}

class Meeting(val title: String) {
    //    var startTime: String = ""
//    var endTime: String = ""
//    var start = this;
//    var end = this;
    val start = StartTime()
    val end = EndTime()

    //    private fun convertToString(time: Double) = String.format("%.02f", time) 3
//    fun at(time: Double) { startTime = convertToString(time) } 1
//    fun by(time: Double) { endTime = convertToString(time) } 1
//    infix fun at(time: Double) { startTime = convertToString(time)} 2
//    infix fun by(time: Double) { endTime = convertToString(time)} 2
//    override fun toString() = "$title Meeting starts ${startTime ends $endTime" 3
    override fun toString() =
        "$title Meeting starts ${start.time} ends ${end.time}"
}

infix fun String.meeting(block: Meeting.() -> Unit) {
    val meeting = Meeting(this)
    meeting.block()
    println(meeting)
}

open class MeetingTime(var time: String = "") {
    protected fun convertToString(time: Double) = String.format("%.02f", time)
}

class StartTime : MeetingTime() {
    infix fun at(theTime: Double) {
        time = convertToString(theTime)
    }
}

class EndTime : MeetingTime() {
    infix fun by(theTime: Double) {
        time = convertToString(theTime)
    }
}


fun main_() {
    println(2 days ago)
    println(3 days from_now)

//    "Release Planning" meeting {
//        println("With in lambda: $this")
//    }

//    "Release Planning" meeting {
//        at(14.30)
//        by(15.30)
//    }

//    "Release Planning" meeting {
//        this at 14.30
//        this by 15.20
//    }

    "Release Planning" meeting {
        start at 14.30
        end by 15.30
    }


}

val langsAndAuthors = mapOf("JavaScript" to "Eich", "Java" to "Gosling", "Ruby" to "Matz")

//val xmlString = xml {
//    root("languages") {
//        langsAndAuthors.forEach { name, author ->
//            {
//                element("language", "name" to name) {
//                    element("author", text(author))
//                }
//            }
//        }
//    }
//}

fun xml(block: XMLBuilder.() -> Node): Node = XMLBuilder().run(block)

@XMLMarker
class XMLBuilder {
    fun root(rootElementName: String, block: Node.() -> Unit): Node =
        Node(rootElementName).apply(block)
}

@XMLMarker
class Node(val name: String) {
    var attributes: Map<String, String> = mutableMapOf()
    var children: List<Node> = listOf()
    var textValue: String = ""

    fun text(value: String) {
        textValue = value
    }

    fun element(
        childName: String,
        vararg attributeValues: Pair<String, String>,
        block: Node.() -> Unit
    ): Node {
        val child = Node(childName)
        attributeValues.forEach { child.attributes += it }
        children += child
        return child.apply(block)
    }

    fun toString(indentation: Int): String {
        val attributesValues = if (attributes.isEmpty()) "" else
            attributes.map { "${it.key}='${it.value}'" }.joinToString(" ", " ")
        val DEPTH = 2
        val indent = " ".repeat(indentation)
        return if (!textValue.isEmpty())
            "$indent<$name$attributesValues>$textValue</$name>"
        else
            """$indent<$name$attributesValues>
                |${children.joinToString("\n") { it.toString(indentation + DEPTH) }}
                |$indent</$name>""".trimMargin()
    }

    override fun toString() = toString(0)
}


val xmlString = xml { root("languages") {
    langsAndAuthors.forEach { name, author -> element("languages", "name" to name) {
        element("author") { text(author) }
//        root("oops") {}
        this@xml.root("oops"){}
    } }
} }

@DslMarker
annotation class XMLMarker

fun main() {
    println(xmlString)
    println(measureTimeMillis {  })
}