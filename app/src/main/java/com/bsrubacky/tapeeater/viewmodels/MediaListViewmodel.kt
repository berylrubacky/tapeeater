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

    //temporary hardcoding to figure out ui movements before setting up database properly
    val media = listOf(
        Media(0, "Wolfpack", 1),
        Media(1, "How To Drive A Bus", 0),
        Media(2, "Furfag", 2),
        Media(3, "Log off and Go Outside!", 3),
        Media(4, "The Circus Egotistica; or, How I Spent Most of my Life as a Lost Cause", 2),
        Media(5, "Sutured Self", 3),
        Media(6, "All These Faces", 3),
        Media(7, "Partners", 3)
    )

    private val _sortValue = MutableStateFlow(0)
    val sortValue = _sortValue.asStateFlow()

    private val _filterValue = MutableStateFlow(-1)
    val filterValue = _filterValue.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _mediaList = MutableStateFlow(media)
    val mediaList = combine(filterValue, searchText, _mediaList) { filterType, text, medias ->
        if (filterType == -1) {
            if (text.isBlank()) {
                medias.sortedBy { it.name }
            } else {
                medias.filter { media ->
                    media.name.uppercase().contains(text.trim().uppercase())
                }.sortedBy { it.name }
            }
        } else if (text.isBlank()) {
            medias.filter { media ->
                media.type == filterType
            }.sortedBy { it.name }
        } else {
            medias.filter { media ->
                media.name.uppercase().contains(text.trim().uppercase()) && media.type == filterType
            }.sortedBy { it.name }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _mediaList.value
    )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onFilterChange(filter: Int) {
        if (filterValue.value == filter) {
            _filterValue.value = -1
        } else {
            _filterValue.value = filter
        }
    }

    fun onSortChange(sort: Int) {
        _sortValue.value = sort
    }
}