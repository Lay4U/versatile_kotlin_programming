package com.lay.versatile_kotlin_programming

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.*
import java.net.URL
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import kotlin.system.measureTimeMillis

class Weather(@Json(name = "Temp") val temperature: Array<String>)
class Airport(
    @Json(name = "IATA") val code: String,
    @Json(name = "Name") val name: String,
    @Json(name = "Delay") val delay: Boolean,
    @Json(name = "Weather") val weather: Weather
) {
    companion object {
        fun getAirportData(code: String): Airport? {
            val url = "https://soa.smext.faa.gov/asws/api/airport/status/$code"
            return Klaxon().parse<Airport>(URL(url).readText())
        }
    }
}

fun main() {
    disableSslVerification()
    AirportInfo()
    AirportInfoAsync()
    LaunchErr();
    LaunchErrHandle();
    AsyncErr();
    cancelAndSuspension();
    doNotDistrub();
    cancellationBidirectional()
    cancellationSupervisor()
    cancellationTimeout()
}

fun cancellationTimeout() {
    runBlocking {
        val handler = CoroutineExceptionHandler { _, ex ->
            println("Exception handled: ${ex.message}")
        }
        val job = launch(Dispatchers.IO + handler) {
            withTimeout(3000) {
                launch { fetchResponse(200, 5000) }
                launch { fetchResponse(201, 1000) }
                launch { fetchResponse(202, 2000) }
            }
        }
        job.join()
    }
}

fun cancellationSupervisor() {
    runBlocking {
        val handler = CoroutineExceptionHandler { _, ex ->
            println("Exception handled: ${ex.message}")
        }
        val job = launch(Dispatchers.IO + handler) {
            supervisorScope {
                launch { fetchResponse(200, 5000) }
                launch { fetchResponse(202, 1000) }
                launch { fetchResponse(404, 2000) }

            }
        }
        Thread.sleep(4000)
        println("200 should still be running at this time")
        println("let the parent cancel now")
        job.cancel()
        job.join()
    }
}

fun cancellationBidirectional() {
    runBlocking {
        val handler = CoroutineExceptionHandler { _, ex ->
            println("Exception handled: ${ex.message}")
        }
        val job = launch(Dispatchers.IO + SupervisorJob() + handler) {
            launch { fetchResponse(200, 5000) }
            launch { fetchResponse(202, 1000) }
            launch { fetchResponse(404, 2000) }
        }
        job.join()
    }
}

suspend fun fetchResponse(code: Int, delay: Int) = coroutineScope {
    try {
        val response = async {
            URL("http://httpstat.us/$code?sleep=$delay").readText()
        }.await()
        println(response)
    } catch (ex: CancellationException) {
        println("${ex.message} for fetchResponse $code")
    }
}


fun doNotDistrub() {
    runBlocking {
        val job = launch(Dispatchers.Default) {
            launch { doWork(1, 3000) }
            launch { doWork(2, 1000) }
        }
        Thread.sleep(2000)
        job.cancel()
        println("cancelling")
        job.join()
        println("done")
    }
}

suspend fun doWork(id: Int, sleep: Long) = coroutineScope {
    try {
        println("$id: entered $sleep")
        delay(sleep)
        println(("$id: finished nap $sleep"))
        withContext(NonCancellable) {
            println("$id: do not disturb, please")
            delay(5000)
            println("$id: OK, you can talk to me now")
        }
        println("$id: outside the restricted context")
        println("$id: isActive: $isActive")
    } catch (ex: CancellationException) {
        println("$id: doWork($sleep) was cancelled")
    }
}

fun cancelAndSuspension() {
    println()
    runBlocking {
        val job = launch(Dispatchers.Default) {
            launch { compute(checkActive = false) }
            launch { compute(checkActive = true) }
            launch { fetchResponse(callAsync = false) }
            launch { fetchResponse(callAsync = true) }
        }
        println("Let them run...")
        Thread.sleep(1000)
        println("Ok, That's enough, cancel")
        job.cancelAndJoin()
    }
}

val url = "http://httpstat.us/200?sleep=2000"
fun getResponse() = java.net.URL(url).readText()
suspend fun fetchResponse(callAsync: Boolean) = coroutineScope {
    try {
        val response = if (callAsync) {
            async { getResponse() }.await()
        } else {
            getResponse();
        }
        println(response)
    } catch (ex: CancellationException) {
        println("fetchResponse called with callAsync: ${ex.message}")
    }

}

suspend fun compute(checkActive: Boolean) = coroutineScope {
    var count = 0;
    val max = Integer.MAX_VALUE
    while (if (checkActive) {
            isActive
        } else (count < max)
    ) {
        count++
    }
    if (count == max) {
        println("compute, checkActive $checkActive ignored cancellation")
    } else {
        println("compute, checkActive $checkActive bailed out early")
    }
}

private fun AsyncErr() {
    runBlocking {
        val airportCodes = listOf("LAX", "SF-", "PD-", "SEA")
        val airportData = airportCodes.map { anAirportCode ->
            async(Dispatchers.IO + SupervisorJob()) {
                Airport.getAirportData(anAirportCode)
            }
        }
        for (anAirportData in airportData) {
            try {
                val airport = anAirportData.await()
                println("${airport?.code} ${airport?.delay}")
            } catch (ex: Exception) {
                println("Error: ${ex.message?.substring(0..28)}")
            }
        }
    }
}

private fun LaunchErrHandle() {
    runBlocking {
        val handler = CoroutineExceptionHandler { context, ex ->
            println("Caught ${context[CoroutineName]} ${ex.message?.substring(0..28)}")
        }
        try {
            val airportCodes = listOf("LAX", "SF-", "PD-", "SEA")
            val jobs: List<Job> = airportCodes.map { anAirportCode ->
                launch(Dispatchers.IO + CoroutineName(anAirportCode) + handler + SupervisorJob()) {
                    val airport = Airport.getAirportData(anAirportCode)
                    println("${airport?.code} delay: ${airport?.delay}")
                }
            }
            jobs.forEach { it.join() }
            jobs.forEach { println("Cancelled: ${it.isCancelled}") }
        } catch (ex: Exception) {
            println("ERROR: ${ex.message}")
        }
    }
}

private fun LaunchErr() {
    runBlocking {
        try {
            val airportCodes = listOf("LAX", "SF-", "PD-", "SEA")
            val jobs: List<Job> = airportCodes.map { anAirportCode ->
                launch(Dispatchers.IO + SupervisorJob()) {
                    val airport = Airport.getAirportData(anAirportCode)
                    println("${airport?.code} delay: ${airport?.delay}")
                }
            }
            jobs.forEach { it.join() }
            jobs.forEach { println("Cancelled: ${it.isCancelled}") }
        } catch (ex: Exception) {
            println("ERROR: ${ex.message}")
        }
    }
}

private fun AirportInfoAsync() {
    runBlocking {
        val format = "%-10s%-20s%-10s"
        println(String.format(format, "Code", "Temperature", "Delay"))
        val time = measureTimeMillis {
            val airportCodes = listOf("LAX", "SFO", "PDX", "SEA")
            val airportData: List<Deferred<Airport?>> =
                airportCodes.map { anAirportCode ->
                    async(Dispatchers.IO) {
                        Airport.getAirportData(anAirportCode)
                    }
                }
            airportData
                .mapNotNull { anAirportData -> anAirportData.await() }
                .forEach { anAirport ->
                    println(
                        String.format(
                            format, anAirport.code,
                            anAirport.weather.temperature[0], anAirport.delay
                        )
                    )
                }
        }
        println("Time taken $time ms")
    }
}

private fun AirportInfo() {
    val format = "%-10s%-20s%-10s"
    println(String.format(format, "Code", "Temperature", "Delay"))
    val time = measureTimeMillis {
        val airportCodes = listOf("LAX", "SFO", "PDX", "SEA")
        val airportData: List<Airport> =
            airportCodes.mapNotNull { anAirportCode ->
                Airport.getAirportData(anAirportCode)
            }
        airportData.forEach { anAirport ->
            println(
                String.format(
                    format, anAirport.code,
                    anAirport.weather.temperature[0], anAirport.delay
                )
            )
        }
    }
    println("Time taken $time ms")
}


fun disableSslVerification() {
    try {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate>? {
                    return null
                }

                override fun checkClientTrusted(
                    certs: Array<X509Certificate>, authType: String
                ) {
                }

                override fun checkServerTrusted(
                    certs: Array<X509Certificate>, authType: String
                ) {
                }
            }
        )
        val sc = SSLContext.getInstance("SSL")
        sc.init(null, trustAllCerts, SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
        val allHostsValid = HostnameVerifier { hostname, session -> true }
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
