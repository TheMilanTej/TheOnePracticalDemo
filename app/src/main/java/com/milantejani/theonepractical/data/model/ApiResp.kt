package com.milantejani.theonepractical.data.model

sealed interface ApiResp<out T> {

    data class Success<T>(val data: T) : ApiResp<T>

    data class Error(val message: String) : ApiResp<Nothing>

    object Loading : ApiResp<Nothing>

}