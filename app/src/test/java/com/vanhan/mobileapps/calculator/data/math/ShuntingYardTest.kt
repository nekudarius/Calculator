package com.vanhan.mobileapps.calculator.data.math

import com.vanhan.mobileapps.calculator.domain.math.CalculatorOperator
import com.vanhan.mobileapps.calculator.domain.math.CalculatorToken
import org.junit.Assert.*
import org.junit.Test

class ShuntingYardTest {

    // Helper to make tokens more concise
    private fun n(text: String) = CalculatorToken.Number(text)
    private fun op(c: Char) = CalculatorToken.Op(CalculatorOperator.fromChar(c)!!)

    // ShuntingYard.toRpn is the item that we want to evaluate
    private fun executeShuntingYardRpn(token: List<CalculatorToken>) : List<CalculatorToken> {
        return ShuntingYard.toRpn(token)
    }

    @Test
    fun `single number stays as-is`() {
        // 42 -> 42
        val input = listOf(n("42"))
        val rpn = executeShuntingYardRpn(input)
        assertEquals(listOf(n("42")), rpn)
    }

    @Test
    fun `simple addition becomes postfix`() {
        // 3 + 4  ->  3 4 +
        val input = listOf(n("3"), op('+'), n("4"))
        val rpn = executeShuntingYardRpn(input)
        assertEquals(listOf(n("3"), n("4"), op('+')), rpn)
    }

    @Test
    fun `operator as first expression`() {
        val input = listOf(op('-'), n("1"), op('+'), n("2"))
        val rpn = executeShuntingYardRpn(input)
        assertEquals(listOf(n("1"), op('-'), n("2"), op('+')),
            rpn
        )
    }

    @Test
    fun `when higher precedence is on the right side`() {
        // 3 + 4 * 2  ->  3 4 2 * +
        val input = listOf(n("3"), op('+'), n("4"), op('*'), n("2"))
        val rpn = executeShuntingYardRpn(input)
        assertEquals(
            listOf(n("3"), n("4"), n("2"), op('*'), op('+')),
            rpn
        )
    }

    @Test
    fun `when higher precedence is on the left side`() {
        // 3*4 + 2  ->  3 4 * 2 +
        val input = listOf(n("3"), op('*'), n("4"), op('+'), n("2"))
        val rpn = executeShuntingYardRpn(input)
        assertEquals(
            listOf(n("3"), n("4"), op('*'), n("2"), op('+')),
            rpn
        )
    }

    @Test
    fun `operators with the same precedence must be evaluated left associatively`() {
        // --------- TEST 1 --------- //
        // 5 - 3 + 2  ->  5 3 - 2 +
        val inputTest1 = listOf(n("5"), op('-'), n("3"), op('+'), n("2"))
        val rpnTest1 = executeShuntingYardRpn(inputTest1)
        assertEquals(
            listOf(n("5"), n("3"), op('-'), n("2"), op('+')),
            rpnTest1
        )

        // --------- TEST 2 --------- //
        // 8 * 4 * 2  ->  8 4 * 2 *
        val inputTest2 = listOf(n("8"), op('*'), n("4"), op('*'), n("2"))
        val rpnTest2 = executeShuntingYardRpn(inputTest2)
        assertEquals(
            listOf(n("8"), n("4"), op('*'), n("2"), op('*')),
            rpnTest2
        )

        // --------- TEST 3 --------- //
        // 8 / 4 / 2  ->  8 4 / 2 /
        val inputTest3 = listOf(n("8"), op('/'), n("4"), op('/'), n("2"))
        val rpnTest3 = executeShuntingYardRpn(inputTest3)
        assertEquals(
            listOf(n("8"), n("4"), op('/'), n("2"), op('/')),
            rpnTest3
        )

    }

    @Test
    fun `mixed precedence resolves correctly in longer expression`() {
        // --------- TEST 1 --------- //
        // 10 - 2 * 3 + 4 * 2  ->  10 2 3 * - 4 2 * +
        val inputTest1 = listOf(n("10"), op('-'), n("2"), op('*'), n("3"), op('+'), n("4"), op('*'), n("2"))
        val rpnTest1 = executeShuntingYardRpn(inputTest1)
        assertEquals(
            listOf(n("10"), n("2"), n("3"), op('*'), op('-'), n("4"), n("2"), op('*'), op('+')),
            rpnTest1
        )

        // --------- TEST 2 --------- //
        // 10 - 2 + 5 * 3 + 4 / 2 * 3  ->  10 2 - 5 3 * + 4 2 / 3 * +
        val inputTest2 = listOf(n("10"), op('-'), n("2"), op('+'), n("5"), op('*'),
            n("3"), op('+'), n("4"), op('/'), n("2"), op('*'), n("3"))
        val rpnTest2 = executeShuntingYardRpn(inputTest2)
        assertEquals(
            listOf(n("10"), n("2"),op('-'), n("5"), n("3"), op('*'),
                op('+'), n("4"), n("2"), op('/'), n("3"), op('*'), op('+')),
            rpnTest2
        )
    }

    @Test
    fun `two consecutive operators are handled by precedence and associativity`() {
        // Tokens: 1, +, -, 2  ->  1 + 2 -
        // (plus is popped by minus because of same precedence & LEFT)
        val input = listOf(n("1"), op('+'), op('-'), n("2"))
        val rpn = executeShuntingYardRpn(input)
        assertEquals(
            listOf(n("1"), op('+'), n("2"), op('-')),
            rpn
        )
    }

    @Test
    fun `empty input will give result empty output`() {
        val rpn = executeShuntingYardRpn(emptyList())
        assertEquals(emptyList<CalculatorToken>(), rpn)
    }

}