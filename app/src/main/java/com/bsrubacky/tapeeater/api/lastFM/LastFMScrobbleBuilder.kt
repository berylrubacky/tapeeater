package com.bsrubacky.tapeeater.api.lastFM

import android.content.Context
import com.bsrubacky.tapeeater.api.ScrobbleBuilder
import com.bsrubacky.tapeeater.api.lastFM.requests.ScrobbleRequest
import com.bsrubacky.tapeeater.database.entities.Track
import java.util.Date

class LastFMScrobbleBuilder(tracks: List<Track>) : ScrobbleBuilder<LastFM> (tracks) {

    override fun build(context: Context): List<ScrobbleRequest>{
        addTimes()
        val scrobbleRequests = mutableListOf<ScrobbleRequest>()
        splitTracks().forEach {
            scrobbleRequests.add(ScrobbleRequest(context, *it.toTypedArray()))
        }
        return scrobbleRequests
    }

    fun addTimes(){
        var currentTime = Date().time/1000
        val tracksSorted = tracks.sortedByDescending {
            it.position
        }
        tracksSorted.forEach {
            currentTime = currentTime - it.length
            it.timestamp = currentTime
        }
        tracks = tracksSorted
    }

    fun splitTracks():List<List<Track>>{
        val tracksSplit = mutableListOf<List<Track>>()
        for (i in tracks.indices step 50) {
            if(i+50 > tracks.size){
                tracksSplit.add(tracks.subList(i,tracks.size))
            }else{
                tracksSplit.add(tracks.subList(i,i+50))
            }
        }
        return tracksSplit
    }
}
