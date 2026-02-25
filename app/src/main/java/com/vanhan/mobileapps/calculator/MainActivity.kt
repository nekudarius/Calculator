package com.vanhan.mobileapps.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vanhan.mobileapps.calculator.data.math.MathEngineImpl
import com.vanhan.mobileapps.calculator.ui.screen.CalculatorScreen
import com.vanhan.mobileapps.calculator.ui.screen.CalculatorViewModel
import com.vanhan.mobileapps.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold { innerPadding ->
                    val vm = remember {
                        CalculatorViewModel(
                            MathEngineImpl()
                        )
                    }
                    CalculatorScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        vm = vm
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        Greeting("Android")
    }
}