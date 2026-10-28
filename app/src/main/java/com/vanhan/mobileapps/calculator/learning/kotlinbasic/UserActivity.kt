package com.vanhan.mobileapps.calculator.learning.kotlinbasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
class UserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm: UserViewModel by viewModels() {
            UserViewModelFactory(
                UserRepository(UserApi())
            )
        }

        enableEdgeToEdge()
        setContent {
            val dashboard = vm.dashboardFlow.collectAsStateWithLifecycle()
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Text(
                        text = "email: "
                    )
                    Text(
                        text = dashboard.value.user.email
                    )
                }
            }
        }
    }
}