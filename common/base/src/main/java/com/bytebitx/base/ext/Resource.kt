package com.bytebitx.base.ext

// A generic class that contains data and status about loading this data.
sealed class Resource<out T: Any> {
    data class Success<out T: Any>(val data: T) : Resource<T>()
    data class Loading(val nothing: Nothing? = null) : Resource<Nothing>()
    data class Error(val exception: Throwable) : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=${exception}]"
            is Loading -> "Loading"
        }
    }
}
