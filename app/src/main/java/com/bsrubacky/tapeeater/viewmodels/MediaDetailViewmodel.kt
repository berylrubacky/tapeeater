package com.bsrubacky.tapeeater.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
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

    val length = database.trackDao().sumLength(passed.id)

    private val _pager = Pager(
        config = PagingConfig(25, enablePlaceholders = true)
    ) {
        database.trackDao().selectAllTracksWithMedia(passed.id)
    }
    val trackList = _pager.flow.cachedIn(viewModelScope)

    private val _hasScrobbles = MutableStateFlow(false)
    val hasScrobbles = _hasScrobbles.asStateFlow()

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
