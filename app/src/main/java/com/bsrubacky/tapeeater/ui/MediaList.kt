package com.bsrubacky.tapeeater.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.viewmodels.MediaListViewmodel

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MediaListScreen() {

    val viewmodel = viewModel<MediaListViewmodel>()

    val searchText by viewmodel.searchText.collectAsState()
    val mediaList by viewmodel.mediaList.collectAsState()

    Scaffold(
        topBar = {
            ConstraintLayout(Modifier.fillMaxWidth()) {
                val (search, settings) = createRefs()
                val searchColors = SearchBarDefaults.colors()
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = searchText,
                            onQueryChange = viewmodel::onSearchTextChange,
                            onSearch = viewmodel::onSearchTextChange,
                            expanded = false,
                            onExpandedChange = { expanded: Boolean -> },
                            colors = searchColors.inputFieldColors,
                            leadingIcon = {
                                Icon(painterResource(R.drawable.button_search), "Search Media")
                            },
                            trailingIcon = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        painterResource(R.drawable.button_filter),
                                        "Filter Media"
                                    )
                                }
                            }

                        )
                    },
                    expanded = false,
                    onExpandedChange = { expanded: Boolean -> },
                    shape = SearchBarDefaults.inputFieldShape,
                    colors = searchColors,
                    tonalElevation = SearchBarDefaults.TonalElevation,
                    shadowElevation = SearchBarDefaults.ShadowElevation,
                    windowInsets = SearchBarDefaults.windowInsets,
                    content = { },
                    modifier = Modifier.padding(5.dp).fillMaxWidth(.85f).constrainAs(search){
                        start.linkTo(parent.start, 15.dp)
                        end.linkTo(settings.start, 5.dp)
                        bottom.linkTo(parent.bottom)
                    }
                )
                IconButton(onClick = {}, modifier = Modifier.padding(5.dp).constrainAs(settings){
                    end.linkTo(parent.end)
                    bottom.linkTo(search.bottom,5.dp)
                }) {
                    Icon(painterResource(R.drawable.button_settings), "")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(painterResource(R.drawable.button_add), "Add Media")
            }
        }) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                items(mediaList) { media ->
                    MediaItem(media)
                }
            }
        }

    }

}

@Composable
@Preview
fun MediaListScreenPreview() {
    TapeEaterTheme {
        MediaListScreen()
    }
}
