package com.vanhan.mobileapps.calculator.ui.screen

import com.vanhan.mobileapps.calculator.domain.math.FakeMathEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import kotlin.test.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CalculatorViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var engine: FakeMathEngine
    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        engine = FakeMathEngine()
        viewModel = CalculatorViewModel(engine)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onNumberClick able to append digit`() = runTest {
        viewModel.onNumberClick('2')
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("2", viewModel.uiState.value.expression)
    }

    @Test
    fun `onDotClick able to append dot`() = runTest {
        viewModel.onNumberClick('1')
        viewModel.onDotClick()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("1.", viewModel.uiState.value.expression)
    }

    @Test
    fun `onDotClick only allow one dot for one operand`() = runTest {
        viewModel.onNumberClick('1')
        viewModel.onDotClick()
        viewModel.onDotClick()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("1.", viewModel.uiState.value.expression)
    }

    @Test
    fun `onDotClick on empty expression will add 0 and dot`() = runTest {
        viewModel.onDotClick()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("0.", viewModel.uiState.value.expression)
    }

    @Test
    fun `onOperatorClick able to append operator`() = runTest {
        viewModel.onNumberClick('1')
        viewModel.onOperatorClick('+')
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("1+", viewModel.uiState.value.expression)
    }

    @Test
    fun `onOperatorClick replaces previous operator`() = runTest {
        viewModel.onNumberClick('1')
        viewModel.onOperatorClick('+')
        viewModel.onOperatorClick('×')
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("1×", viewModel.uiState.value.expression)
    }

    @Test
    fun `onDelete removes last char`() = runTest {
        viewModel.onNumberClick('9')
        viewModel.onNumberClick('8')
        viewModel.onOperatorClick('+')
        viewModel.onDelete()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("98", viewModel.uiState.value.expression)

        viewModel.onDelete()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("9", viewModel.uiState.value.expression)
    }

    @Test
    fun `onClear resets calculator state`() = runTest {
        viewModel.onNumberClick('9')
        viewModel.onNumberClick('8')
        viewModel.onOperatorClick('+')
        viewModel.onClear()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("", viewModel.uiState.value.expression)
        assertEquals("0", viewModel.uiState.value.result)
        assertEquals(null, viewModel.uiState.value.error)

    }

    @Test
    fun `onEvaluate success update result`() = runTest {
        engine.result = "12"

        viewModel.onNumberClick('1')
        viewModel.onNumberClick('2')
        viewModel.onEvaluate()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("12", viewModel.uiState.value.result)
        assertEquals(null, viewModel.uiState.value.error)
    }

    @Test
    fun `onEvaluate error message`() = runTest {
        engine.throwException = ArithmeticException("Divide by zero")

        viewModel.onNumberClick('1')
        viewModel.onEvaluate()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Cannot divide by 0", viewModel.uiState.value.error)
    }

    @Test
    fun `onEvaluate error invalid input`() = runTest {
        engine.throwException = IllegalArgumentException("Invalid")

        viewModel.onNumberClick('1')
        viewModel.onEvaluate()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Input not valid", viewModel.uiState.value.error)
    }

}