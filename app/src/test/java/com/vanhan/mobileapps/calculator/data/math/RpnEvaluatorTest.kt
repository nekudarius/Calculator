package com.vanhan.mobileapps.calculator.data.math

import com.vanhan.mobileapps.calculator.domain.math.CalculatorOperator
import com.vanhan.mobileapps.calculator.domain.math.CalculatorToken
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertFailsWith

class RpnEvaluatorTest {

    // ----------- Helper to make tokens more concise
    private fun n(text: String) = CalculatorToken.Number(text)
    private fun op(c: Char) = CalculatorToken.Op(CalculatorOperator.fromChar(c)!!)

    // ----------- Helper to Comparing BigDecimals without scale sensitivity
    private fun bd(s: String) = s.toBigDecimal()
    private fun assertBD(expected: BigDecimal, actual: BigDecimal, msg: String? = null) {
        if (msg == null) {
            assertEquals(0, expected.compareTo(actual))
        } else {
            assertEquals(msg, 0, expected.compareTo(actual))
        }
    }

    // RpnEvaluator.evaluate is the item that we want to evaluate
    private fun executeRpnEvaluator(rpn: List<CalculatorToken>) : BigDecimal {
        return RpnEvaluator.evaluate(rpn)
    }

    // --------- Normal Operation --------- //
    @Test
    fun `simple addition`() {
        // 1 + 2 => 1 2 + => 3
        val rpn = listOf(n("1"), n("2"), op('+'))
        val result = executeRpnEvaluator(rpn)
        assertBD(bd("3"), result)
    }

    @Test
    fun `simple subtraction`() {
        // 1 - 2 => 1 2 - => -1
        val rpn = listOf(n("1"), n("2"), op('-'))
        val result = executeRpnEvaluator(rpn)
        assertBD(bd("-1"), result)
    }

    @Test
    fun `simple multiplication`() {
        // 3 * 5 => 3 5 * => 15
        val rpn = listOf(n("3"), n("5"), op('*'))
        val result =  executeRpnEvaluator(rpn)
        assertBD(bd("15"), result)
    }

    @Test
    fun `simple division`() {
        // 5 / 2 => 5 2 / => 2.5
        val rpn = listOf(n("5"), n("2"), op('/'))
        val result = executeRpnEvaluator(rpn)
        assertBD(bd("2.5"), result)
    }

    @Test
    fun `multiple equation`() {
        // 6 - 2 + 4 * 5 / 2 => 6 2 - 4 5 * 2 / + => 14
        val rpn = listOf(n("6"), n("2"), op('-'), n("4"), n("5"), op('*'), n("2"), op('/'), op('+'))
        val result = executeRpnEvaluator(rpn)
        assertBD(bd("14"), result)
    }

    // --------- Abnormal Operation --------- //
    @Test
    fun `one operand value is valid`() {
        val rpn = listOf(n("3"))
        val result = executeRpnEvaluator(rpn)
        assertEquals(bd("3"), result)
    }

    @Test
    fun `invalid result when only operand exist`() {
        val rpn = listOf(n("3"), n("4"))
        val result = assertFailsWith<IllegalArgumentException> {
            executeRpnEvaluator(rpn)
        }
        assertEquals("Invalid result", result.message)
    }

    @Test
    fun `empty RPN`() {
        val ex = assertFailsWith<IllegalArgumentException> {
            executeRpnEvaluator(emptyList())
        }
        assertEquals("Invalid result", ex.message)
    }

    @Test
    fun `operator missing one operand`() {
        val rpn = listOf(n("3"), op('+'))
        val result = assertFailsWith<IllegalArgumentException> {
            executeRpnEvaluator(rpn)
        }
        assertEquals("Invalid expression", result.message)
    }

    @Test
    fun `minus as first expression`() {
        // - 5 + 3 => 5 - 3 +
        val rpn = listOf(n("5"), op('-'), n("3"), op('+'))
        val result = assertFailsWith<IllegalArgumentException> {
            executeRpnEvaluator(rpn)
        }
        assertEquals("Invalid expression", result.message)
    }

    @Test
    fun `divide by zero`() {
        // 10 / 0 => 10 0 /
        val rpn = listOf(n("10"), n("0"), op('/'))
        val result = assertFailsWith<IllegalArgumentException> {
            executeRpnEvaluator(rpn)
        }
        assertEquals("Divide by zero", result.message)
    }
}