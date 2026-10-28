package com.vanhan.mobileapps.calculator

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

import kotlin.time.Duration.Companion.milliseconds

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CoroutineTest {
    fun threadForKotlin() {
        val thread = Thread {
            println("thread running: ${Thread.currentThread().name}")
        }
        thread.start()
        thread.join()
    }

    fun coroutineCheckOld() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            println("running on coroutine")
            suspendCoroutine()
        }
    }

    fun coroutineCheck() = runTest {
        val scope = this
        scope.launch {
            println("running on coroutine")
        }
    }

    suspend fun suspendCoroutine() {
        delay(1000.milliseconds)
        println("running on coroutine")
    }

    @Test
    fun threadKotlinRun() {
        threadForKotlin()
    }

    @Test
    fun testCoroutineOld() {
        //Todo: make log appear on test
        coroutineCheck()
    }

    @Test
    fun testCoroutine() {
        coroutineCheck()
    }

    @Test
    fun testSuspend() = runTest {
        //todo: is "afterCoroutine" called after "suspendCoroutine"
        suspendCoroutine()
        println("called after afterCoroutine")
    }

    @Test
    fun testAsync() = runTest{
        val result = async {
            delay(1000.milliseconds)
            10 + 20
        }
        println("doing other work...")

        println("Result = ${result.await()}")
    }

    @Test
    fun testPararelAsync() = runTest{
        val result = async {
            delay(1000.milliseconds)
            10 + 20
        }
        println("doing other work...")

        println("Result = ${result.await()}")
    }
}