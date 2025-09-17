package com.bsrubacky.tapeeater.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.bsrubacky.tapeeater.database.entities.Media
import com.bsrubacky.tapeeater.ui.MediaDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class MediaDetailViewmodel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val passed = savedStateHandle.toRoute<MediaDetail>()

    //temporary hardcoding to figure out ui movements before setting up database properly
    val mediaList = listOf(
        Media(0, "Wolfpack", 1),
        Media(1, "How To Drive A Bus", 0),
        Media(2, "Furfag", 2),
        Media(3, "Log off and Go Outside!", 3),
        Media(
            4,
            "The Circus Egotistica; or, How I Spent Most of my Life as a Lost Cause",
            2,
            lastPlayed = Date().time
        ),
        Media(5, "Sutured Self", 3),
        Media(6, "All These Faces", 3),
        Media(7, "Partners", 3)
    )

    private val _media = MutableStateFlow(mediaList[passed.id])
    val media = _media.asStateFlow()

    private val _hasScrobbles = MutableStateFlow(false)
    val hasScrobbles = _hasScrobbles.asStateFlow()

    private val _hasTracks = MutableStateFlow(false)
    val hasTracks = _hasTracks.asStateFlow()

    fun scrobbleMedia() {
        _media.value.lastPlayed = Date().time
        _hasScrobbles.value = true
    }
}