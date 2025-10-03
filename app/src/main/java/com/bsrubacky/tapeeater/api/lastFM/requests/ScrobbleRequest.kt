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
        if(tracks.size>1) {
            tracks.forEachIndexed { index, track ->
                params.put("artist[$index]", track.artist)
                params.put("track[$index]", track.name)
                params.put("timestamp[$index]", track.timestamp.toString())
                params.put("album[$index]", track.album)
                params.put("duration[$index]", track.length.toString())
                if(track.albumArtist.isNotBlank()) {
                    params.put("albumArtist[$index]", track.albumArtist)
                }
            }
        }else{
            params.put("artist", tracks[0].artist)
            params.put("track", tracks[0].name)
            params.put("timestamp", tracks[0].timestamp.toString())
            params.put("album", tracks[0].album)
            params.put("duration", tracks[0].length.toString())
            if(tracks[0].albumArtist.isNotBlank()){
                params.put("albumArtist", tracks[0].albumArtist)
            }
        }
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