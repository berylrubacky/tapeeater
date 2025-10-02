package com.bsrubacky.tapeeater.api

sealed class OrchestratorResponse<out R> {
    data class Loading(val loading: Boolean = true) : OrchestratorResponse<Int>()
    data class Success<out Int>(val successCode: Int) : OrchestratorResponse<Int>()
    data class Error(val exception: Throwable) : OrchestratorResponse<Nothing>()
}