package com.vanhan.mobileapps.calculator.ui.screen

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vanhan.mobileapps.calculator.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `test`() {
        composeRule.onNodeWithTag("btn_2").performClick()
        composeRule.onNodeWithTag("btn_plus").performClick()
        composeRule.onNodeWithTag("btn_3").performClick()
        composeRule.onNodeWithTag("btn_equals").performClick()

        composeRule
            .onNodeWithTag("txt_result")
            .assertTextEquals("5")
    }
}