package com.bsrubacky.tapeeater.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsrubacky.tapeeater.database.entities.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MediaListViewmodel : ViewModel() {

    val media = listOf(
        Media(0, "Wolfpack", 1),
        Media(1, "How To Drive A Bus", 0),
        Media(2, "Furfag", 2),
        Media(3, "Log off and Go Outside!", 3),
        Media(4, "Swear I Saw Your Mouth Move", 2),
        Media(5, "Sutured Self", 3),
        Media(6, "All These Faces", 3),
        Media(7, "Partners",3)
    )

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _mediaList = MutableStateFlow(media)
    val mediaList = searchText.combine(_mediaList){ text, medias ->
        if(text.isBlank()){
            medias
        }
        medias.filter{ media ->
            media.name.uppercase().contains(text.trim().uppercase())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _mediaList.value
    )

    fun onSearchTextChange(text: String){
        _searchText.value = text
    }
}