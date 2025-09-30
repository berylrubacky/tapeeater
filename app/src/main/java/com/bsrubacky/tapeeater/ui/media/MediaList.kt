package com.bsrubacky.tapeeater.ui.media

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.database.entities.Media
import com.bsrubacky.tapeeater.ui.TapeEaterTheme
import com.bsrubacky.tapeeater.ui.media.dialogs.AddEditMediaDialog
import com.bsrubacky.tapeeater.ui.media.listitems.MediaItem
import com.bsrubacky.tapeeater.ui.menu.FilterItem
import com.bsrubacky.tapeeater.ui.menu.SortItem
import com.bsrubacky.tapeeater.viewmodels.MediaListViewmodel
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable


@Serializable
object MediaList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MediaListScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navToDetail: (Long) -> Unit
) {

    val viewmodel = viewModel<MediaListViewmodel>()

    val searchText by viewmodel.searchText.collectAsState()
    val filterValue by viewmodel.filterValue.collectAsState()
    val sortValue by viewmodel.sortValue.collectAsState()
    val mediaList = viewmodel.mediaList.collectAsLazyPagingItems()
    MediaListContent(
        searchText,
        filterValue,
        sortValue,
        mediaList,
        viewmodel::onSearchTextChange,
        viewmodel::onFilterChange,
        viewmodel::onSortChange,
        viewmodel::addMedia,
        sharedTransitionScope,
        animatedVisibilityScope,
        navToDetail
        )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MediaListContent(
    searchText: String,
    filterValue: Int,
    sortValue: Int,
    mediaList: LazyPagingItems<Media>,
    onSearchTextChange: (String) -> Unit,
    onFilterChange: (Int) -> Unit,
    onSortChange: (Int) -> Unit,
    addMedia: (Media) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navToDetail: (Long) -> Unit
){

    var filtersExpanded by remember { mutableStateOf(false) }
    var sortExpanded by remember { mutableStateOf(false) }
    var addMediaDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            ConstraintLayout(Modifier.fillMaxWidth()) {
                val (search, settings, dropdowns) = createRefs()
                val searchColors = SearchBarDefaults.colors()
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = searchText,
                            onQueryChange = { text ->
                                onSearchTextChange(text)
                                mediaList.refresh()
                            },
                            onSearch = { text ->
                                onSearchTextChange(text)
                                mediaList.refresh()
                            },
                            expanded = false,
                            onExpandedChange = { expanded: Boolean -> },
                            colors = searchColors.inputFieldColors,
                            leadingIcon = {
                                IconButton(onClick = { onSearchTextChange("") }, Modifier.testTag("search-button")) {
                                    AnimatedVisibility(
                                        searchText.isBlank(),
                                        enter = expandIn(expandFrom = Alignment.Center),
                                        exit = shrinkOut(shrinkTowards = Alignment.Center)
                                    ) {
                                        Icon(
                                            painterResource(R.drawable.button_search),
                                            stringResource(R.string.search_media)
                                        )
                                    }
                                    AnimatedVisibility(
                                        !searchText.isBlank(),
                                        enter = expandIn(expandFrom = Alignment.Center),
                                        exit = shrinkOut(shrinkTowards = Alignment.Center)
                                    ) {
                                        Icon(
                                            painterResource(R.drawable.button_close),
                                            stringResource(R.string.clear_search)
                                        )
                                    }
                                }
                            },
                            trailingIcon = {
                                ConstraintLayout {
                                    val (filter, sort) = createRefs()
                                    IconButton(
                                        onClick = { filtersExpanded = true },
                                        colors = if (filterValue == -1) {
                                            IconButtonDefaults.iconButtonColors()
                                        } else {
                                            IconButtonDefaults.filledIconButtonColors()
                                        },
                                        modifier = Modifier.constrainAs(filter) {
                                            end.linkTo(sort.start)
                                        }.testTag("filter-button")
                                    ) {
                                        Icon(
                                            painterResource(R.drawable.button_filter),
                                            stringResource(R.string.filter_media)
                                        )
                                    }
                                    IconButton(
                                        onClick = { sortExpanded = true },
                                        colors = IconButtonDefaults.iconButtonColors(),
                                        modifier = Modifier.constrainAs(sort) {
                                            end.linkTo(parent.end)
                                        }.testTag("sort-button")
                                    ) {
                                        Icon(
                                            painterResource(R.drawable.button_sort),
                                            stringResource(R.string.sort_media)
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.testTag("search-input")
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
                Box(modifier = Modifier.constrainAs(dropdowns) {
                    end.linkTo(search.end)
                    top.linkTo(search.bottom)
                })
                {
                    DropdownMenu(
                        expanded = filtersExpanded,
                        onDismissRequest = { filtersExpanded = false },
                        modifier = Modifier.testTag("filter-dropdown")
                    )
                    {
                        AnimatedVisibility(filterValue != -1) {
                            FilterItem(
                                painterResource(R.drawable.button_filter_off),
                                stringResource(R.string.clear_filters)
                            ) {
                                onFilterChange(-1)
                                mediaList.refresh()
                            }
                        }
                        FilterItem(
                            painterResource(R.drawable.vinyl),
                            stringResource(R.string.vinyl),
                            filterValue == 0
                        ) {
                            onFilterChange(0)
                            mediaList.refresh()
                        }
                        FilterItem(
                            painterResource(R.drawable.cassette),
                            stringResource(R.string.cassette),
                            filterValue == 1
                        ) {
                            onFilterChange(1)
                            mediaList.refresh()
                        }
                        FilterItem(
                            painterResource(R.drawable.cd),
                            stringResource(R.string.cd),
                            filterValue == 2
                        ) {
                            onFilterChange(2)
                            mediaList.refresh()
                        }
                        FilterItem(
                            painterResource(R.drawable.minidisc),
                            stringResource(R.string.minidisc),
                            filterValue == 3
                        ) {
                            onFilterChange(3)
                            mediaList.refresh()
                        }
                    }
                    DropdownMenu(
                        expanded = sortExpanded,
                        onDismissRequest = { sortExpanded = false },
                        modifier = Modifier.testTag("sort-dropdown")
                    )
                    {
                        SortItem(
                            painterResource(R.drawable.label_alphabetical),
                            stringResource(R.string.sort_alpha),
                            if (sortValue == 1) {
                                painterResource(R.drawable.button_sort_desc)
                            } else {
                                painterResource(R.drawable.button_sort_asc)
                            },
                            if (sortValue == 1) {
                                stringResource(R.string.descending)
                            } else {
                                stringResource(R.string.ascending)
                            },
                            sortValue == 0 || sortValue == 1
                        ) {
                            if (sortValue == 0) {
                                onSortChange(1)
                            } else {
                                onSortChange(0)
                            }
                            mediaList.refresh()
                        }
                        SortItem(
                            painterResource(R.drawable.label_history),
                            stringResource(R.string.sort_history),
                            if (sortValue == 3) {
                                painterResource(R.drawable.button_sort_desc)
                            } else {
                                painterResource(R.drawable.button_sort_asc)
                            },
                            if (sortValue == 3) {
                                stringResource(R.string.descending)
                            } else {
                                stringResource(R.string.ascending)
                            },
                            sortValue == 2 || sortValue == 3
                        ) {
                            if (sortValue == 2) {
                                onSortChange(3)
                            } else {
                                onSortChange(2)
                            }
                            mediaList.refresh()
                        }
                    }
                }

                IconButton(
                    onClick = {}, modifier = Modifier
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
            AnimatedVisibility(
                !addMediaDialog, enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                with(sharedTransitionScope) {
                    FloatingActionButton(
                        onClick = { addMediaDialog = true },
                        modifier = Modifier.sharedBounds(
                            rememberSharedContentState(key = "add-media"),
                            this@AnimatedVisibility,
                            clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(16.dp))
                        ).testTag("add-media-button")
                    ) {
                        Icon(painterResource(R.drawable.button_add), "Add Media")
                    }
                }
            }
        }) { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.testTag("media-list"))
            {
                if(mediaList.itemCount>0){
                    item {
                        HorizontalDivider()
                    }
                }
                items(mediaList.itemCount, key = { mediaList[it]!!.id }) { index ->
                    MediaItem(mediaList[index]!!, sharedTransitionScope, animatedVisibilityScope, navToDetail)
                }
            }
        }

    }

    AddEditMediaDialog(
        onDismissRequest = { addMediaDialog = false },
        onSave = { media ->
            addMedia(media)
            addMediaDialog = false
        },
        sharedTransitionScope = sharedTransitionScope,
        isVisible = addMediaDialog
    )

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun MediaListScreenPreview() {
    val lazyPagingItems = flowOf(
    PagingData.from(
        listOf(
            Media(0,"Test",0),
            Media(1,"Test2",1),
            Media(2,"Test3",2),
            Media(3,"Test4",3)
        )
    ),
    ).collectAsLazyPagingItems()
    val navController = rememberNavController()

    SharedTransitionLayout {
        TapeEaterTheme {
            NavHost(navController, startDestination = MediaList) {
                composable<MediaList> {
                    MediaListContent(
                        "",
                        -1,
                        -1,
                        lazyPagingItems,
                        {string->},
                        {int->},
                        {int->},
                        {media->},
                        this@SharedTransitionLayout,
                        this@composable
                    ) { int -> }
                }
            }
        }
    }
}
