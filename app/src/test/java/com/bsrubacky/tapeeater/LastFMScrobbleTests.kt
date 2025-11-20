package com.bsrubacky.tapeeater

import com.bsrubacky.tapeeater.api.lastFM.LastFMScrobbleBuilder
import com.bsrubacky.tapeeater.database.entities.Track
import org.junit.Assert
import org.junit.Test

class LastFMScrobbleTests {

    @Test
    fun addingTimestampToScrobble(){
        val track = Track(0, 0,"","","","",100,0)
        val builder = LastFMScrobbleBuilder(listOf(track))
        builder.addTimes()
        Assert.assertNotEquals(0,builder.tracks[0].timestamp)
    }

    @Test
    fun addingTimestampToMultipleScrobbles(){
        val builder = LastFMScrobbleBuilder(
            listOf(
                Track(0,0,"","","","",100,0),
                Track(0,0,"","","","",100,1)
            )
        )
        builder.addTimes()
        Assert.assertEquals(1, builder.tracks[0].position)
        Assert.assertEquals(builder.tracks[0].timestamp-100, builder.tracks[1].timestamp)
    }

    @Test
    fun noSplit1Track(){
        val builder = LastFMScrobbleBuilder(listOf(Track(0,0,"","","","",100,0)))
        val returnedTracks = builder.splitTracks()
        Assert.assertEquals(1,returnedTracks.size)
        Assert.assertEquals(1, returnedTracks[0].size)
    }

    @Test
    fun noSplitAt50Tracks(){
        val tracks = mutableListOf<Track>()
        for(i in 0..49){
            tracks.add(Track(0,0,"","","","",100,i))
        }
        val builder = LastFMScrobbleBuilder(tracks)
        val returnedTracks = builder.splitTracks()
        Assert.assertEquals(1,returnedTracks.size)
        Assert.assertEquals(50, returnedTracks[0].size)
    }

    @Test
    fun splitAt51Tracks(){
        val tracks = mutableListOf<Track>()
        for(i in 0..50){
            tracks.add(Track(0,0,"","","","",100,i))
        }
        val builder = LastFMScrobbleBuilder(tracks)
        val returnedTracks = builder.splitTracks()
        Assert.assertEquals(2,returnedTracks.size)
        Assert.assertEquals(50, returnedTracks[0].size)
        Assert.assertEquals(1, returnedTracks[1].size)
    }
    @Test
    fun splitAt101Tracks(){
        val tracks = mutableListOf<Track>()
        for(i in 0..100){
            tracks.add(Track(0,0,"","","","",100,i))
        }
        val builder = LastFMScrobbleBuilder(tracks)
        val returnedTracks = builder.splitTracks()
        Assert.assertEquals(3,returnedTracks.size)
        Assert.assertEquals(50, returnedTracks[0].size)
        Assert.assertEquals(50, returnedTracks[1].size)
        Assert.assertEquals(1, returnedTracks[2].size)
    }

}