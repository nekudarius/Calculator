package com.vanhan.mobileapps.calculator.learning.kotlintono

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlin.time.Duration.Companion.milliseconds

class ApplicationTeamRepository {
    fun getApplicationTeam(): Flow<ApplicationTeam> {

        val name = flow {
            delay(2000.milliseconds)
            emit("Kholish")
            emit("Aser")
            emit("Mul")
            throw IllegalStateException ("bomb!!")
        }
            .take(1)
            .map { name ->
                name.uppercase()
            }
            .filter { name ->
                name.length > 5
            }
            .transform { name ->
                emit("$name Ulil")
            }

        val nik = flow {
            delay(2000.milliseconds)

            val result = "21080003"
            Log.d("EmployeeRepository", "nik: $result")

            emit(result)
        }

        val history = flow {
            delay(2000.milliseconds)

            val result = listOf(
                "iPrint",
                "Finder",
                "Creative",
            )

            Log.d("EmployeeRepository", "history: $result")

            emit(result)
        }
            .transform { history ->
                emit(
                    history + "Smart Panel"
                )
            }

        return combine(
            name,
            nik,
            history
        ) { name, nik, history ->

            ApplicationTeam(
                name = name,
                nik = nik,
                history = history
            )
        }
    }
}