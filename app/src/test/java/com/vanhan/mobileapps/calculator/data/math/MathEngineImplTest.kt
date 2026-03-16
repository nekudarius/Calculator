package com.vanhan.mobileapps.calculator.data.math

import org.junit.Assert.*
import org.junit.Test
import kotlin.test.assertFailsWith

class MathEngineImplTest {
    private val engine = MathEngineImpl()

    // --------- Normalize UI Symbol ------------ //
    @Test
    fun `normalize times symbol`() {
        // 5×3 -> 15
        val result = engine.evaluate("5×3")
        assertEquals("15", result)
    }
    @Test
    fun `normalize divide symbol`() {
        // 10÷4 -> 2.5
        val result = engine.evaluate("10÷4")
        assertEquals("2.5", result)
    }

    // --------- Normal Operation ------------ //
    @Test
    fun `simple addition`() {
        val result = engine.evaluate("1+2")
        assertEquals("3", result)
    }

    @Test
    fun `simple subtraction`() {
        val result = engine.evaluate("1-2")
        assertEquals("-1", result)
    }

    @Test
    fun `simple multiplication`() {
        val result = engine.evaluate("3*5")
        assertEquals("15", result)
    }

    @Test
    fun `simple division`() {
        val result = engine.evaluate("10/2")
        assertEquals("5", result)
    }

    @Test
    fun `equation with higher precedence`() {
        // --------- TEST 1 --------- //
        // 3 + 4 * 2 = 11
        val result = engine.evaluate("3+4*2")
        assertEquals("11", result)

        // --------- TEST 2 --------- //
        // 3 * 4 + 2 = 14
        val resultTest2 = engine.evaluate("3*4+2")
        assertEquals("14", resultTest2)

        // --------- TEST 3 --------- //
        // 3 + 4 / 2 = 5
        val resultTest3 = engine.evaluate("3+4/2")
        assertEquals("5", resultTest3)

        // --------- TEST 4 --------- //
        // 4 / 2 + 3 = 5
        val resultTest4 = engine.evaluate("4/2+3")
        assertEquals("5", resultTest4)
    }

    @Test
    fun `multiple equation`() {
        val result = engine.evaluate("6-2+4*5/2")
        assertEquals("14", result)
    }

    // --------- formatting ------------ //
    @Test
    fun `strip trailing zeros on integer results`() {
        // 2.0 + 3.0 => 5
        val result = engine.evaluate("2.0+3.0")
        assertEquals("5", result)
    }

    @Test
    fun `half up precision 16 for 1_3`() {
        // use MathContext(16, HALF_UP) in RpnEvaluator
        val out = engine.evaluate("1/3")
        assertEquals("0.3333333333333333", out)
    }


    // --------- Error case ------------ //
    @Test
    fun `divide by zero throws IllegalArgumentException`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            engine.evaluate("10/0")
        }
        assertEquals("Divide by zero", ex.message)
    }

    @Test
    fun `empty expression string throws Empty expression`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            engine.evaluate("")
        }
        assertEquals("Empty expression", ex.message)
    }

    @Test
    fun `spaces only throws Empty expression`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            engine.evaluate("    ")
        }
        assertEquals("Empty expression", ex.message)
    }

    @Test
    fun `unary minus not throws Invalid expression`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            engine.evaluate("-5+3")
        }
        assertEquals("Invalid expression", ex.message)
    }

    @Test
    fun `invalid number text throw Invalid number`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            engine.evaluate("1..2")
        }
        assertEquals("Invalid number: 1..2", ex.message)
    }

    @Test
    fun `invalid letter throw Invalid Unknown Char`() {
        val ex = assertFailsWith<IllegalStateException> {
            engine.evaluate("2+3 hello")
        }
        assertEquals("Unknown char: h", ex.message)
    }
}