package com.example.animedekho.data.remote

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


sealed class DataResult<out T> {

    data object Loading : DataResult<Nothing>()

    data class Success<out T>(
        val data: T
    ) : DataResult<T>()

    data class Error(
        val message: String = "Something went wrong"
    ) : DataResult<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<DataResult<T>>  = map<T, DataResult<T>> { response ->
    Log.d("AnimeRepo", "Response: $response")
    DataResult.Success(response)
}
    .onStart { emit(DataResult.Loading) }
        .catch { exception ->
            Log.e("AnimeRepo", "Error fetching anime list", exception)
            emit(DataResult.Error())
        }
