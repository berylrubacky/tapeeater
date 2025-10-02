package com.bsrubacky.tapeeater.api.lastFM.requests

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bsrubacky.tapeeater.api.lastFM.LastFM
import com.bsrubacky.tapeeater.api.lastFM.LastFMRequest
import com.bsrubacky.tapeeater.database.entities.Track
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

class ScrobbleRequest(private val context: Context, vararg tracks: Track): LastFMRequest<String>() {

    init{

    }

    override suspend fun setupRequest(api: LastFM) {
        super.setupRequest(api)
        val sessionKey = context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("lastFM_sk")] ?: ""
        }.single()
        params.put("sk", sessionKey)
    }

    override val name = "Scrobble Request"

    override suspend fun call(): retrofit2.Response<String> = TODO()

    override suspend fun manageResponse(response: retrofit2.Response<String>) {

    }
}