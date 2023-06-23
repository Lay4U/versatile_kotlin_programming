package com.agiledeveloper.airportstatus

import com.agiledeveloper.airportstatus.Airport.Companion.disableSslVerification
import io.kotlintest.data.forall
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class AirportIntegrationTest: StringSpec() {
    init {
        "fetchData returns response from URL" {

            forall(
                row("IAD", "Dulles"),
                row("SFO", "San Francisco"),
                row("ORD", "Chicago")
            ) { code, partialName ->
                Airport.fetchData(code) shouldContain partialName
            }
        }
    }
}