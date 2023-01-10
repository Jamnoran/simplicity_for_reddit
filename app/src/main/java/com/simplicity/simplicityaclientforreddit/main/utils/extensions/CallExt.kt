package com.simplicity.simplicityaclientforreddit.main.utils.extensions

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T : Any, R : Any?> Call<T>.fold(
    onSuccess: (T) -> R,
    onFailure: (APIException) -> R
) {
    enqueue(object : Callback<T> {
        override fun onResponse(
            call: Call<T>,
            response: Response<T>
        ) {
            if (response.body() != null) {
                onSuccess.invoke(response.body()!!)
            } else {
                onFailure.invoke(APIException())
            }
            response.body()?.let { onSuccess.invoke(it) }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e("SearchLogic", "Error : ", t)
            onFailure.invoke(APIException(t))
        }
    })
}

class APIException(val throwable: Throwable? = null)
