package com.simplicity.simplicityaclientforreddit.main.screen.authenticate.result

import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.APIInterface
import com.simplicity.simplicityaclientforreddit.main.io.retrofit.RetrofitClientInstance
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.models.external.AccessToken
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.usecases.authentication.GetAccessTokenAuthenticationUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.authentication.GetAccessTokenBodyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationResultLogic : BaseComposeLogic<AuthenticationResultInput>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    override fun ready(input: AuthenticationResultInput) {
        background {
            authenticate(input)
        }
    }

    private fun authenticate(input: AuthenticationResultInput) {
        val code = SettingsSP().loadSetting(SettingsSP.KEY_CODE, "Default")
        Log.i(TAG, "Authenticating with code: $code")
        code?.let {
            val service = RetrofitClientInstance.getRetrofitInstance().create(APIInterface::class.java)
            val call = service.accessToken(GetAccessTokenAuthenticationUseCase().getAuth(), GetAccessTokenBodyUseCase().getBody(code))
            call.enqueue(object : Callback<AccessToken> {
                override fun onResponse(
                    call: Call<AccessToken>,
                    response: Response<AccessToken>
                ) {
                    response.body()?.let { data ->
                        Log.i(TAG, "Data in response: $data")
                        data.accessToken?.let { SettingsSP().saveSetting(SettingsSP.KEY_ACCESS_TOKEN, it) }
                        data.refreshToken?.let { SettingsSP().saveSetting(SettingsSP.KEY_REFRESH_TOKEN, it) }
//                        data.expiresIn?.let { _expiresIn.postValue(it) }
//                        data.scope?.let { _scope.postValue(it) }
//                        data.tokenType?.let { _tokenType.postValue(it) }
                        SettingsSP().saveSetting(SettingsSP.KEY_CODE, null)
                        foreground {
                            input.navController.popBackStack(route = NavRoute.AUTHENTICATION.path, inclusive = true)
                        }
                    }
                }

                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    Log.e(TAG, "Error : ", t)
                }
            })
        }
    }

    companion object {
        private const val TAG = "AuthenticationResultLogic"
    }
}
