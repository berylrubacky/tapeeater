package com.bsrubacky.tapeeater.api.lastFM.requests

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bsrubacky.tapeeater.api.lastFM.LastFMRequest
import com.bsrubacky.tapeeater.api.lastFM.responses.AuthResponse
import com.bsrubacky.tapeeater.api.lastFM.responses.Error
import com.bsrubacky.tapeeater.api.lastFM.responses.Session
import retrofit2.Response

private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

class AuthenticationRequest(val context: Context, username: String, password: String) : LastFMRequest<AuthResponse>() {
    init {
        params.put("method", "auth.getMobileSession")
        params.put("username", username)
        params.put("password",password)
        params.put("api_sig", sign())
    }

    override val name = "Authentication Request"

    override suspend fun call(): Response<AuthResponse> = api.authentication(params)

    override suspend fun manageResponse(response: Response<AuthResponse>) {
       val lfm = response.body()
        if(lfm!= null){
            if(response.isSuccessful){
                val session = lfm.response as Session
                context.dataStore.edit { preferences ->
                    preferences[stringPreferencesKey("lastFM_sk")] = session.key
                }
            }else{
                val error = lfm.response as Error
            }
        }
    }
}