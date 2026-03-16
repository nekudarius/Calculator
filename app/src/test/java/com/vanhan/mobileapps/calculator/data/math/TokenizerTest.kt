package com.vanhan.mobileapps.calculator.data.math

import com.vanhan.mobileapps.calculator.domain.math.CalculatorOperator
import com.vanhan.mobileapps.calculator.domain.math.CalculatorToken
import org.junit.Assert.*
import org.junit.Test
import kotlin.test.assertFailsWith


class TokenizerTest {

    // Helper to make tokens more concise
    private fun n(text: String) = CalculatorToken.Number(text)
    private fun op(c: Char) = CalculatorToken.Op(CalculatorOperator.fromChar(c)!!)

    //Tokenizer.tokenize is the item that we want to evaluate
    private fun executeTokenGenerator(expression: String) : List<CalculatorToken> {
        return Tokenizer.tokenize(expression)
    }

    @Test
    fun `tokenize simple number`() {
        val result = executeTokenGenerator("123")
        assertEquals(
            listOf(n("123")),
            result
        )
    }

    @Test
    fun `tokenize decimal number`() {
        val result = executeTokenGenerator("3.14")
        assertEquals(
            listOf(n("3.14")),
            result
        )
    }

    @Test
    fun `tokenize simple addition`() {
        val result = executeTokenGenerator("1+2")
        assertEquals(
            listOf(
                n("1"),
                op('+'),
                n("2")
            ),
            result
        )
    }

    @Test
    fun `tokenize simple subtraction`() {
        val result = executeTokenGenerator("1-2")
        assertEquals(
            listOf(
                n("1"),
                op('-'),
                n("2")
            ),
            result
        )

        val result2 = Tokenizer.tokenize("-1-2")
        assertEquals(
            listOf(
                CalculatorToken.Op(CalculatorOperator.MINUS),
                CalculatorToken.Number("1"),
                CalculatorToken.Op(CalculatorOperator.MINUS),
                CalculatorToken.Number("2")
            ),
            result2
        )
    }

    @Test
    fun `tokenize simple multiplication`() {
        val result = executeTokenGenerator("1*2")
        assertEquals(
            listOf(
                n("1"),
                op('*'),
                n("2")
            ),
            result
        )
    }

    @Test
    fun `tokenize simple division`() {
        val result = executeTokenGenerator("1/2")
        assertEquals(
            listOf(
                n("1"),
                op('/'),
                n("2")
            ),
            result
        )
    }

    @Test
    fun `tokenize multiple operators`() {
        val result = executeTokenGenerator("12.5*3-7")
        assertEquals(
            listOf(
                n("12.5"),
                op('*'),
                n("3"),
                op('-'),
                n("7")
            ),
            result
        )
    }

    @Test
    fun `tokenize ignores spaces`() {
        val result = executeTokenGenerator(" 10 +  20 ")

        assertEquals(
            listOf(
                n("10"),
                op('+'),
                n("20")
            ),
            result
        )
    }

    @Test
    fun `error when unknown char found`() {
        assertFailsWith<IllegalStateException> {
            executeTokenGenerator("2 & 3")
        }
    }

    @Test
    fun `error when using letter`() {
        assertFailsWith<IllegalStateException> {
            executeTokenGenerator("2+3 hello")
        }
    }

    @Test
    fun `error when number has two dots`() {
        assertFailsWith<IllegalArgumentException> {
            executeTokenGenerator("1..2")
        }
    }

    @Test
    fun `error when expression is empty`() {
        assertFailsWith<IllegalArgumentException> {
            executeTokenGenerator("")
        }
    }

    @Test
    fun `error when only dot`() {
        assertFailsWith<IllegalArgumentException> {
            executeTokenGenerator(".")
        }
    }
}
