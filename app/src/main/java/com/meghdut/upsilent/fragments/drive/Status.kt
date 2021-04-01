package com.meghdut.upsilent.fragments.drive

import java.lang.Exception

sealed class Status<T> {

    class Success<T>(val data: T) : Status<T>()

    class Error<T>(val error: Exception, val message: String = "") : Status<T>()
}
