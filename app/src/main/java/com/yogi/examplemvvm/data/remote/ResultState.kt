package com.yogi.examplemvvm.data.remote

/**
 * Created by oohyugi on 2019-05-01.
 * github: https://github.com/oohyugi
 */
sealed class ResultState<out T : Any?> {
    data class Success<out T : Any?>(val data: T?) : ResultState<T>()
    data class Error(val errorMessage: String?) : ResultState<Nothing>()
}