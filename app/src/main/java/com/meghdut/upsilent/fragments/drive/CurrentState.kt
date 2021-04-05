package com.meghdut.upsilent.fragments.drive

sealed class CurrentState<out T> {

    class Error(val exception: Exception) : CurrentState<Nothing>()
    class Success<T>(val result: T) : CurrentState<T>()
    object Loading : CurrentState<Nothing>()

}