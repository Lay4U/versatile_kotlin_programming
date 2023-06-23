package com.agiledeveloper.airportstatus

import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.verify




class AirportTest : StringSpec() {
    val iah = Airport("IAH", "Houston", true)
    val iad = Airport("IAD", "Dulles", false)
    val ord = Airport("ORD", "Chicago O'Hare", true)

    override fun beforeTest(testCase: TestCase){
        mockkObject(Airport)
    }

    override fun afterTest(testCase: TestCase, result: TestResult){
        clearAllMocks()
    }

    init {
        "canary test should pass" {
            true shouldBe true
        }

        "create Airport" {
            iah.code shouldBe "IAH"
            iad.name shouldBe "Dulles"
            ord.delay shouldBe true
        }

        "sort empty list should return an empty list" {
            Airport.sort(listOf<Airport>()) shouldBe listOf<Airport>()
        }

        "sort list with one Airport should return the given Airpot" {
            Airport.sort(listOf(iad)) shouldBe listOf(iad)
        }

        "sort pre-sorted list list should return the given list" {
            Airport.sort(listOf(iad, iah)) shouldBe listOf(iad, iah)
        }

        "sort airports should return airports in sorted order of name" {
            Airport.sort(listOf(iah, iad, ord)) shouldBe listOf(ord, iad, iah)
        }

        "sort airports by name" {
            forall(
                row(listOf(), listOf()),
                row(listOf(iad), listOf(iad)),
                row(listOf(iad, iah), listOf(iad, iah)),
                row(listOf(iad, iah, ord), listOf(ord, iad, iah))) { input, result ->
                Airport.sort(input) shouldBe result
            }
        }

        "getAirportData invokes fetchData" {
            every{ Airport.fetchData("IAD") } returns
                    """{"IATA":"IAD", "NAME": "Dulles", "delay": false}"""
            Airport.getAirportData("IAD")
            verify { Airport.fetchData("IAD") }
        }

        "getAirportData extracts Airport from JSON returned by fetchData" {
            every{ Airport.fetchData("IAD") } returns
                    """{"IATA":"IAD", "NAME": "Dulles", "delay": false}"""
            Airport.getAirportData("IAD") shouldBe iad
            verify { Airport.fetchData("IAD") }
        }

        "getAirportData handles error fetching data" {
            every { Airport.fetchData("ERR") } returns "{}"
            Airport.getAirportData("ERR") shouldBe
                    Airport("ERR", "Invalid Airport", false)
            verify { Airport.fetchData("ERR")}
        }

    }
}
