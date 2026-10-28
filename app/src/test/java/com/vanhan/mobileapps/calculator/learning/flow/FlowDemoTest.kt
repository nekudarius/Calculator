package com.vanhan.mobileapps.calculator.learning.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class FlowDemoTest {
    private fun log(msg: String) = println("[DEMO] $msg")

    @Test
    fun `cold flow test` () = runBlocking {
        val coldFlow = flow {
            log("STARTED")
            emit(1)
            emit(2)
            emit(3)
            emit(3)
            emit(4)
        }

        log("first collector")
        coldFlow.collect {
            log("Collected: $it")
        }

        log("second collector")
        coldFlow.collect {
            log("Collected from B: $it")
        }
    }

    @Test
    fun `cold flow test operator` () = runBlocking {
        val coldFlow = flow {
            log("STARTED")
            emit(1)
            emit(2)
            emit(3)
            emit(3)
            emit(4)
        }

        log("first collector")
        coldFlow.collect {
            log("Collected: $it")
        }

        log("second collector")
        coldFlow.collect {
            log("Collected from B: $it")
        }
    }

    @Test
    fun `flow operator demo` () = runBlocking {
        flowOf(1,2,3,4,5,6)
            .filter { it % 2 == 0 }
            .also { log("filter applied (even number only)") }
            .map { it * 10 }
            .also { log("map applied (multiply by 10") }
            .transform {
                emit(it + 5)
                emit(it + 2)
            }
            .take(3)
            .also { log("transform (plus 5") }
            .collect { log("result: $it") }
    }
    @Test
    fun `combine vs zip`() = runBlocking {
        val a = flow {
            emit("A")
            delay(100.milliseconds)
            emit("B")
            delay(100.milliseconds)
            emit("C")
        }

        val b = flow {
            delay(50.milliseconds)
            emit(1)
            delay(100.milliseconds)
            emit(2)
        }

        log("--combine--")
//        a.combine(b) {
        a.zip(b) {
            x, y -> "$x$y"
        }.collect {  result ->
            log("combine: $result")
        }
    }
}