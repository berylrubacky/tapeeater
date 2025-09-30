package com.bsrubacky.tapeeater.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bsrubacky.tapeeater.database.TapeEaterDatabase
import com.bsrubacky.tapeeater.database.entities.Track
import com.bsrubacky.tapeeater.ui.media.AddEditTrackDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrackViewmodel(application: Application, savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application)  {
    private val passed = savedStateHandle.toRoute<AddEditTrackDialog>()
    private val database = TapeEaterDatabase.getDatabase(application.applicationContext)
    val isEdit = passed.id != 0L
    private val _track = MutableStateFlow(Track(0,passed.mediaId,"","","","",0,1))
    val track = _track.asStateFlow()
    private val _isLoaded = MutableStateFlow(false)
    val isLoaded = _isLoaded.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if(isEdit) {
                _track.value = database.trackDao().select(passed.id)
            }
            _numOfTracks.value = database.trackDao().countTracksFromMedia(passed.mediaId)
            _isLoaded.value = true
        }
    }

    private val _numOfTracks = MutableStateFlow(0)
    val numOfTracks = _numOfTracks.asStateFlow()

    fun addTrack(toAdd: Track){
        viewModelScope.launch(Dispatchers.IO) {
            database.trackDao().insert(toAdd)
        }
    }
    fun editTrack(toEdit: Track){
        viewModelScope.launch(Dispatchers.IO) {
            database.trackDao().update(toEdit)
        }
    }
    fun deleteTrack(){
        viewModelScope.launch(Dispatchers.IO){
            database.trackDao().delete(_track.value)
        }
    }
}