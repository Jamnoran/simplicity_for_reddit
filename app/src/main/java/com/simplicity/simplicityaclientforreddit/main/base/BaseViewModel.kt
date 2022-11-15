package com.simplicity.simplicityaclientforreddit.main.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIAuthenticatedInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers.CommentSerializer
import kotlinx.coroutines.*
import java.lang.reflect.Type
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel<VB : ViewBinding> : ViewModel() {
    val isFetching = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Unit>()
    val unAuthorizedError = MutableLiveData<Unit>()
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    fun API(
        responseType: Type,
        commentSerializer: CommentSerializer?
    ): APIInterface {
        isFetching.postValue(true)
        return com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance.getRetrofitInstanceWithCustomConverter(
            responseType,
            commentSerializer
        ).create(APIInterface::class.java)
    }

    fun API(): APIInterface {
        isFetching.postValue(true)
        return com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance.getRetrofitInstance().create(APIInterface::class.java)
    }

    fun APIAuthenticated(): APIAuthenticatedInterface {
        isFetching.postValue(true)
        return com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance.getRetrofitAuthenticatedInstance().create(APIAuthenticatedInterface::class.java)
    }

    fun initBinding(binding: VB?) {
        _binding = binding
    }

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

    // Coroutines
    fun launchCoroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context, start, block)

    fun coroutine(
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT, block)
}
