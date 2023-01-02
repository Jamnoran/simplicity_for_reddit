package com.simplicity.simplicityaclientforreddit.main.io.retrofit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.simplicity.simplicityaclientforreddit.main.base.BaseViewModel
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class CustomResponse<T>(val viewModel: BaseViewModel<*>) : CustomCallback<T> {
    override fun onSuccess(responseBody: T) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            success(responseBody)
        }
        viewModel.isFetching.postValue(false)
    }

    abstract fun success(responseBody: T)

    override fun onUnauthorized() {
        viewModel.unAuthorizedError.postValue(Unit)
        viewModel.isFetching.postValue(false)
    }

    override fun onFailed(throwable: Throwable) {
        viewModel.networkError.postValue(Unit)
        viewModel.isFetching.postValue(false)
        Log.e("CustomResponse", "Error parsing request : ", throwable)
    }
}
abstract class CustomResponseCompose<T>(val viewModel: BaseLogic) : CustomCallback<T> {
    override fun onSuccess(responseBody: T) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            success(responseBody)
        }
        viewModel.isFetching.postValue(false)
    }

    abstract fun success(responseBody: T)
    abstract fun failed(reason: String)

    override fun onUnauthorized() {
        viewModel.unAuthorizedError.postValue(Unit)
        viewModel.isFetching.postValue(false)
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            failed("Not authorized")
        }
    }

    override fun onFailed(throwable: Throwable) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            failed(throwable.localizedMessage)
        }
        viewModel.networkError.postValue(Unit)
        viewModel.isFetching.postValue(false)
        Log.e("CustomResponse", "Error parsing request : ", throwable)
    }
}

abstract class CustomResponseList<T>(val viewModel: BaseViewModel<*>) : CustomCallbackList<T> {
    override fun onSuccess(responseBody: ArrayList<T>) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            success(responseBody)
        }
        viewModel.isFetching.postValue(false)
    }

    abstract fun success(responseBody: ArrayList<T>)

    override fun onUnauthorized() {
        viewModel.unAuthorizedError.postValue(Unit)
        viewModel.isFetching.postValue(false)
    }

    override fun onFailed(throwable: Throwable) {
        viewModel.networkError.postValue(Unit)
        viewModel.isFetching.postValue(false)
    }
}
abstract class CustomResponseListCompose<T>(val viewModel: BaseLogic) : CustomCallbackList<T> {
    override fun onSuccess(responseBody: ArrayList<T>) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            success(responseBody)
        }
        viewModel.isFetching.postValue(false)
    }

    abstract fun success(responseBody: ArrayList<T>)

    override fun onUnauthorized() {
        viewModel.unAuthorizedError.postValue(Unit)
        viewModel.isFetching.postValue(false)
    }

    override fun onFailed(throwable: Throwable) {
        viewModel.networkError.postValue(Unit)
        viewModel.isFetching.postValue(false)
    }
}
