package com.vanhan.mobileapps.calculator.learning.training

sealed interface ApplicationTeamEvent {
    data object ApplicationTeamLoaded : ApplicationTeamEvent

    data class Error(
        val msg: String
    ) : ApplicationTeamEvent
}