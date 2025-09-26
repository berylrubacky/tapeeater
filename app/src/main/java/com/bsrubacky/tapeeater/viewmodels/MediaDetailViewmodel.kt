package com.bsrubacky.tapeeater.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bsrubacky.tapeeater.database.TapeEaterDatabase
import com.bsrubacky.tapeeater.database.entities.Media
import com.bsrubacky.tapeeater.ui.media.MediaDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class MediaDetailViewmodel(application: Application, savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application) {
    private val passed = savedStateHandle.toRoute<MediaDetail>()
    private val database = TapeEaterDatabase.getDatabase(application.applicationContext)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _media.value = database.mediaDao().select(passed.id)
        }
    }

    private val _media = MutableStateFlow(Media(-1, "", -1))
    val media = _media.asStateFlow()

    private val _hasScrobbles = MutableStateFlow(false)
    val hasScrobbles = _hasScrobbles.asStateFlow()

    private val _hasTracks = MutableStateFlow(false)
    val hasTracks = _hasTracks.asStateFlow()

    fun scrobbleMedia() {
        _media.value.lastPlayed = Date().time
        _hasScrobbles.value = true
    }

    fun editMedia(toEdit: Media) {
        viewModelScope.launch(Dispatchers.IO) {
            database.mediaDao().update(toEdit)
        }
    }

    fun deleteMedia() {
        viewModelScope.launch(Dispatchers.IO) {
            database.mediaDao().delete(_media.value)
        }
    }
}
