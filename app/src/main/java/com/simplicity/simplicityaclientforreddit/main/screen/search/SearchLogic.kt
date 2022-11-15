package com.simplicity.simplicityaclientforreddit.main.screen.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.search.SearchRedditResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<List<String>>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<List<String>>> = _stateFlow

    fun start() {
        background {
            // Do something in the background
            foreground {
                _stateFlow.emit(UiState.Success(emptyList()))
            }
        }
    }

    fun updatedInput(query: String) {
        val call = APIAuthenticated().searchReddits(query)
        call.enqueue(object : Callback<SearchRedditResponse> {
            override fun onResponse(
                call: Call<SearchRedditResponse>,
                response: Response<SearchRedditResponse>
            ) {
                viewModelScope.launch(Dispatchers.IO) {
                    response.body()?.names?.let { data ->
                        _stateFlow.emit(UiState.Success(data))
                    }
                    setIsFetching(false)
                }
            }

            override fun onFailure(call: Call<SearchRedditResponse>, t: Throwable) {
                Log.e("SearchLogic", "Error : ", t)
                setIsFetching(false)
            }
        })
    }
}
