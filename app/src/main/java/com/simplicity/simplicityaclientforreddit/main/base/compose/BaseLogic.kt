package com.simplicity.simplicityaclientforreddit.main.base.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIAuthenticatedInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers.CommentSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseLogic : ViewModel() {
    val isFetching = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Unit>()
    val unAuthorizedError = MutableLiveData<Unit>()

    fun API(
        responseType: Type,
        commentSerializer: CommentSerializer?
    ): APIInterface {
        isFetching.postValue(true)
        return RetrofitClientInstance.getRetrofitInstanceWithCustomConverter(
            responseType,
            commentSerializer
        ).create(APIInterface::class.java)
    }

    fun API(): APIInterface {
        isFetching.postValue(true)
        return RetrofitClientInstance.getRetrofitInstance().create(APIInterface::class.java)
    }

    fun APIAuthenticated(): APIAuthenticatedInterface {
        isFetching.postValue(true)
        return RetrofitClientInstance.getRetrofitAuthenticatedInstance().create(
            APIAuthenticatedInterface::class.java
        )
    }

    interface BaseInput

    fun isFetching(): LiveData<Boolean> {
        return isFetching
    }

    fun setIsFetching(value: Boolean) {
        isFetching.postValue(value)
    }

    fun networkError(): LiveData<Unit> {
        return networkError
    }

    fun unAuthorizedError(): LiveData<Unit> {
        return unAuthorizedError
    }

    fun foreground(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context, start, block)

    fun background(blocking: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            blocking.invoke()
        }
    }
}
