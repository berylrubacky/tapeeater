package com.bsrubacky.tapeeater.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.ui.menu.FilterSortItem
import com.bsrubacky.tapeeater.viewmodels.MediaListViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaListScreen() {

    val viewmodel = viewModel<MediaListViewmodel>()

    val searchText by viewmodel.searchText.collectAsState()
    val filterValue by viewmodel.filterValue.collectAsState()
    val mediaList by viewmodel.mediaList.collectAsState()
    var filtersExpanded by remember { mutableStateOf(false) }
    var sortExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ConstraintLayout(Modifier.fillMaxWidth()) {
                val (search, settings,filters) = createRefs()
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
                                    IconButton(
                                        onClick = { filtersExpanded = true },
                                        colors = if (filterValue == -1) {
                                            IconButtonDefaults.iconButtonColors()
                                        } else {
                                            IconButtonDefaults.filledIconButtonColors()
                                        }
                                    ) {
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
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(.85f)
                        .constrainAs(search) {
                            start.linkTo(parent.start, 15.dp)
                            end.linkTo(settings.start, 5.dp)
                            bottom.linkTo(parent.bottom)
                        }
                )
                Box(modifier = Modifier.constrainAs(filters){
                    end.linkTo(search.end)
                    top.linkTo(search.bottom)
                }){
                    DropdownMenu(
                        expanded = filtersExpanded,
                        onDismissRequest = { filtersExpanded = false }
                    ) {
                        AnimatedVisibility(filterValue != -1) {
                            FilterSortItem(
                                painterResource(R.drawable.button_filter_off),
                                stringResource(R.string.clear)
                            ) {
                                viewmodel.onFilterChange(-1)
                            }
                        }
                        FilterSortItem(
                            painterResource(R.drawable.vinyl),
                            stringResource(R.string.vinyl),
                            filterValue == 0
                        ) {
                            viewmodel.onFilterChange(0)
                        }
                        FilterSortItem(
                            painterResource(R.drawable.cassette),
                            stringResource(R.string.cassette),
                            filterValue == 1
                        ) {
                            viewmodel.onFilterChange(1)
                        }
                        FilterSortItem(
                            painterResource(R.drawable.cd),
                            stringResource(R.string.cd),
                            filterValue == 2
                        ) {
                            viewmodel.onFilterChange(2)
                        }
                        FilterSortItem(
                            painterResource(R.drawable.minidisc),
                            stringResource(R.string.minidisc),
                            filterValue == 3
                        ) {
                            viewmodel.onFilterChange(3)
                        }
                    }
                    DropdownMenu(
                        expanded = sortExpanded,
                        onDismissRequest = { sortExpanded = false }
                    ) {
                        FilterSortItem(
                            painterResource(R.drawable.button_asc),
                            "A-Z",
                            filterValue == 0
                        ) {
                        }
                        FilterSortItem(
                            painterResource(R.drawable.button_dsc),
                            "A-Z",
                            filterValue == 0
                        ) {
                        }
                        FilterSortItem(
                            painterResource(R.drawable.button_asc),
                            "Date",
                            filterValue == 0
                        ) {
                        }
                        FilterSortItem(
                            painterResource(R.drawable.button_dsc),
                            "Date",
                            filterValue == 0
                        ) {
                        }
                    }
                }
                IconButton(onClick = {}, modifier = Modifier
                    .padding(5.dp)
                    .constrainAs(settings) {
                        end.linkTo(parent.end)
                        bottom.linkTo(search.bottom, 5.dp)
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
                items(mediaList, key = {it.id}) { media ->
                    val icon = when(media.type){
                        0 -> R.drawable.vinyl
                        1 -> R.drawable.cassette
                        2 -> R.drawable.cd
                        3 -> R.drawable.minidisc
                        else -> R.drawable.cd
                    }
                    val iconName = when(media.type){
                        0 -> R.string.vinyl
                        1 -> R.string.cassette
                        2 -> R.string.cd
                        3 -> R.string.minidisc
                        else -> R.drawable.cd
                    }
                    Box(Modifier
                        .padding(horizontal = 10.dp, vertical = 2.dp)
                        .fillMaxWidth()
                        .height(50.dp).animateItem()){
                        Icon(painterResource(icon), stringResource(iconName), modifier = Modifier
                            .align(Alignment.CenterStart)
                            .width(50.dp)
                            .padding(start = 5.dp),tint = MaterialTheme.colorScheme.primary)
                        Text(text= media.name, modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(.80f)
                            .padding(start = 20.dp), overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center, fontSize = 23.sp)
                        Icon(painterResource(R.drawable.button_upload),"Scrobble", modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 5.dp))
                        HorizontalDivider(modifier = Modifier.align(Alignment.BottomCenter))
                    }
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
