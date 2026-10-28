package com.vanhan.mobileapps.calculator.learning.kotlintono

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vanhan.mobileapps.calculator.learning.training.ApplicationTeamEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration.Companion.milliseconds

//data class ApplicationTeam(
//    val name: String,
//    val nik: String,
//    val history: List<String>
//)

class CoroutineStructuredViewModel(
    private val repository: ApplicationTeamRepository = ApplicationTeamRepository()
): ViewModel() {
//    private val _stateApplicationTeam = MutableStateFlow<ApplicationTeam?>(null)
//    val stateApplicationTeam: StateFlow<ApplicationTeam?> = _stateApplicationTeam.asStateFlow()

    private val _event = MutableSharedFlow<ApplicationTeamEvent>()
    val event: SharedFlow<ApplicationTeamEvent> = _event.asSharedFlow()

    val stateApplicationTeam: StateFlow<ApplicationTeam?> =
        repository
            .getApplicationTeam()
            .onEach {
                _event.emit(
                    ApplicationTeamEvent.ApplicationTeamLoaded
                )
            }
            .catch { e ->
                emit(
                    ApplicationTeam(
                        name = e.message.toString(),
                        nik = "",
                        history = emptyList()
                    )
                )

                _event.emit(
                    ApplicationTeamEvent.Error(
                        e.message?: "Unknown error"
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )



//    init {
////        nameOfMemberApplicationTeam()
//        getApplicationMemberTeam()
//    }
//
//    private fun nameOfMemberApplicationTeam() {
//        viewModelScope.launch {
//            cancel()
//            supervisorScope {
//                val memberName = async {
//                    delay(2000.milliseconds)
//                    throw Exception("failed read name")
//                    "Darius"
//                }.apply {
//                    invokeOnCompletion {
//                        cause ->
//                            Log.e("CoroutineLog", cause.toString())
//                    }
//                }
//
//                val memberNik = async {
//                    delay(2000.milliseconds)
//                    "22120005"
//                }.apply {
//                    invokeOnCompletion {
//                            cause ->
//                        Log.e("CoroutineLog", cause.toString())
//                    }
//                }
//
//                val memberHistory = async {
//                    delay(2000.milliseconds)
//                    listOf("absent", "break", "presentation")
//                }.apply {
//                    invokeOnCompletion {
//                            cause ->
//                        Log.e("CoroutineLog", cause.toString())
//                    }
//                }
//
//                var result: ApplicationTeam? = null
//                try {
//                    result = ApplicationTeam(
//                        memberName.await(),
//                        memberNik.await(),
//                        memberHistory.await()
//                    )
//                } catch (e: Exception) {
//                    result = ApplicationTeam(
//                        e.message.toString(),
//                        memberNik.await(),
//                        memberHistory.await()
//                    )
//                }
//
////                val result = ApplicationTeam(
////                    memberName.await(),
////                    memberNik.await(),
////                    memberHistory.await()
////                )
//
//                _stateApplicationTeam.value = result
//            }
//        }
//    }


    //learing cak ulil
//    private fun getApplicationMemberTeam() {
//        viewModelScope.launch {
//            repository
//                .getApplicationTeam()
//                .catch { e ->
//                    _stateApplicationTeam.value = ApplicationTeam(
//                        name = e.message.toString(),
//                        nik = "",
//                        history = emptyList()
//                    )
////                    Log.e("Darius", "caught upstream error: ${it.message}")
//
//                    _event.emit(
//                        ApplicationTeamEvent.Error(
//                            e.message?: "Unknown error"
//                        )
//                    )
//                }
//                .collect { applicationTeam ->
//                _stateApplicationTeam.value = applicationTeam
//
//                _event.emit(
//                    ApplicationTeamEvent.ApplicationTeamLoaded
//                )
//            }
//        }
//    }
}