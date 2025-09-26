package com.bsrubacky.tapeeater.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.bsrubacky.tapeeater.database.entities.Media
import com.bsrubacky.tapeeater.ui.media.MediaList
import com.bsrubacky.tapeeater.ui.media.MediaListContent
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MediaListScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_display_media(){
        val mediaList = listOf(Media(0,"Test",0),
            Media(1,"Test2",1),
            Media(2,"Test3",2),
            Media(3,"Test4",3))
        composeTestRule.setContent {
            val lazyPagingItems = flowOf(
                PagingData.from(
                    mediaList
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaList) {
                    composable<MediaList> {
                        MediaListContent("",
                            0,
                            0,
                            lazyPagingItems,
                            {string->},
                            {int->},
                            {int->},
                            {media->},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("media-list")
            .onChildren().apply {
                fetchSemanticsNodes().forEachIndexed { i, _ ->
                    val typeName = when(mediaList[i].type){
                        0 -> "Vinyl"
                        1 -> "Cassette"
                        2 -> "CD"
                        3 -> "Minidisc"
                        else -> "CD"
                    }
                    get(i).onChildren().onFirst().assertTextEquals(mediaList[i].name)
                        .assertContentDescriptionEquals(typeName)
                    get(i).onChildren().onLast().assertContentDescriptionEquals("Scrobble")
                }
            }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_add_media(){
        var returnedMedia: Media? = null
        composeTestRule.setContent {
            val lazyPagingItems = flowOf(
                PagingData.from(
                    listOf<Media>()
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaList) {
                    composable<MediaList> {
                        MediaListContent("",
                            0,
                            0,
                            lazyPagingItems,
                            {string->},
                            {int->},
                            {int->},
                            {media-> returnedMedia = media},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("add-media-button").performClick()
        composeTestRule.onNodeWithTag("add-edit-media-dialog").assertExists()
        composeTestRule.onNodeWithTag("media-name-input").performTextInput("Test")
        composeTestRule.onNodeWithTag("minidisc-button").performClick()
        composeTestRule.onNodeWithTag("save-button").performClick()
        Assert.assertNotNull(returnedMedia)
        Assert.assertEquals("Test",returnedMedia!!.name)
        Assert.assertEquals(3, returnedMedia.type)
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_cancel_add_media(){
        var returnedMedia: Media? = null
        composeTestRule.setContent {
            val lazyPagingItems = flowOf(
                PagingData.from(
                    listOf<Media>()
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaList) {
                    composable<MediaList> {
                        MediaListContent("",
                            0,
                            0,
                            lazyPagingItems,
                            {string->},
                            {int->},
                            {int->},
                            {media-> returnedMedia = media},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("add-media-button").performClick()
        composeTestRule.onNodeWithTag("add-edit-media-dialog").assertExists()
        composeTestRule.onNodeWithTag("media-name-input").performTextInput("Test")
        composeTestRule.onNodeWithTag("vinyl-button").performClick()
        composeTestRule.onNodeWithTag("cancel-button").performClick()
        Assert.assertNull(returnedMedia)
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_search(){
        var returnedSearch:String? = null
        composeTestRule.setContent {
            var searchControl by remember { mutableStateOf("")}
            val lazyPagingItems = flowOf(
                PagingData.from(
                    listOf<Media>()
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaList) {
                    composable<MediaList> {
                        MediaListContent(searchControl,
                            0,
                            0,
                            lazyPagingItems,
                            {string->
                                searchControl = string
                                returnedSearch = string
                            },
                            {int->},
                            {int->},
                            {media->},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("search-input").performTextInput("Test")
        Assert.assertNotNull(returnedSearch)
        Assert.assertEquals("Test", returnedSearch)
        composeTestRule.onNodeWithTag("search-button").performClick()
        Assert.assertEquals("", returnedSearch)
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_filters(){
        var returnedFilter:Int? = null
        composeTestRule.setContent {
            var filterControl by remember { mutableStateOf(0)}
            val lazyPagingItems = flowOf(
                PagingData.from(
                    listOf<Media>()
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaList) {
                    composable<MediaList> {
                        MediaListContent("",
                            filterControl,
                            0,
                            lazyPagingItems,
                            {string->},
                            {int->
                                filterControl = int
                                returnedFilter = int
                            },
                            {int->},
                            {media->},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("filter-button").performClick()
        composeTestRule.onNodeWithTag("filter-dropdown")
            .onChildAt(3)
            .performClick()
        Assert.assertNotNull(returnedFilter)
        Assert.assertEquals(2, returnedFilter)
        composeTestRule.onNodeWithTag("filter-dropdown")
            .onChildAt(0)
            .onChildAt(0)
            .performClick()
        Assert.assertEquals(-1, returnedFilter)
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_sort(){
        var returnedSort: Int? = null
        composeTestRule.setContent {
            var sortControl by remember { mutableStateOf(0)}
            val lazyPagingItems = flowOf(
                PagingData.from(
                    listOf<Media>()
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaList) {
                    composable<MediaList> {
                        MediaListContent("",
                            0,
                            sortControl,
                            lazyPagingItems,
                            {string->},
                            {int->},
                            {int->
                                sortControl = int
                                returnedSort = int},
                            {media->},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("sort-button").performClick()
        composeTestRule.onNodeWithTag("sort-dropdown")
            .onChildAt(0)
            .performClick()
        Assert.assertEquals(1, returnedSort)
        composeTestRule.onNodeWithTag("sort-dropdown")
            .onChildAt(0)
            .performClick()
        Assert.assertEquals(0, returnedSort)
    }
}
