package com.bsrubacky.tapeeater.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bsrubacky.tapeeater.database.TapeEaterDatabase
import com.bsrubacky.tapeeater.database.entities.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaListViewmodel(application: Application) : AndroidViewModel(application) {

    private val database = TapeEaterDatabase.getDatabase(application.applicationContext)

    private val _sortValue = MutableStateFlow(0)
    val sortValue = _sortValue.asStateFlow()

    private val _filterValue = MutableStateFlow(-1)
    val filterValue = _filterValue.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _pager = Pager(
        config = PagingConfig(25, enablePlaceholders = true)
    ) {
        val searchString = if (searchText.value.isBlank()) {
            "%%"
        } else {
            "%${searchText.value}%"
        }
        val filterString = if (filterValue.value == -1) {
            "%"
        } else {
            filterValue.value.toString()
        }
        when (sortValue.value) {
            3 -> database.mediaDao().searchDateDescending(searchString, filterString)
            2 -> database.mediaDao().searchDateAscending(searchString, filterString)
            1 -> database.mediaDao().searchAlphaDescending(searchString, filterString)
            else -> database.mediaDao().searchAlphaAscending(searchString, filterString)
        }
    }
    var mediaList = _pager.flow.cachedIn(viewModelScope)


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

    fun addMedia(toAdd: Media) {
        viewModelScope.launch(Dispatchers.IO) {
            database.mediaDao().insert(toAdd)
        }
    }
}
