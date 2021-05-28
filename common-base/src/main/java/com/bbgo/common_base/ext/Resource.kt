package com.bbgo.common_base.ext

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
        val data: T? = null,
        val errorCode: Int? = null,
        val errorMsg: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class DataError<T>(errorCode: Int, errorMsg: String? = "") : Resource<T>(null, errorCode, errorMsg)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorCode $errorMsg]"
            is Loading<T> -> "Loading"
        }
    }
}
