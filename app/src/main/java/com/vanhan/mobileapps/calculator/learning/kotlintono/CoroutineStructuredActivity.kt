package com.vanhan.mobileapps.calculator.learning.kotlintono

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vanhan.mobileapps.calculator.learning.training.ApplicationTeamEvent

class CoroutineActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoroutineStructuredActivity()
                }

            }
        }

    }
}

@Composable
fun CoroutineStructuredActivity(
    viewModel: CoroutineStructuredViewModel = CoroutineStructuredViewModel()
) {
//    val dataTable by viewModel.stateApplicationTeam.collectAsState()
    val applicationTeam by viewModel.stateApplicationTeam.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.event.collect { event ->
            when (event) {
                ApplicationTeamEvent.ApplicationTeamLoaded -> {
                    Toast.makeText(
                        context,
                        "ApplicationTeamLoaded",
                        Toast.LENGTH_SHORT)
                        .show()
                }

                is ApplicationTeamEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.msg,
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
//    if (dataTable == null) {
//        Column {
//            CircularProgressIndicator()
//        }
//    } else {
//        val data = dataTable!!
//        Column {
//            Text(
//                text = "Name: ${data.name}"
//            )
//            Text(
//                text = "NIK: ${data.nik}"
//            )
//            Text(
//                text = "History: ${data.history}"
//            )
//
//        }
//    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Name: ${applicationTeam?.name?: "Loading..."}"
        )
        Text(
            text = "Nik: ${applicationTeam?.nik?: "LOADING..."}"
        )
        Text(
            text = "History: ${applicationTeam?.history?: "LoADing..."}"
        )
    }
}