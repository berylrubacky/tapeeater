package com.bsrubacky.tapeeater.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.bsrubacky.tapeeater.database.entities.Media
import com.bsrubacky.tapeeater.database.entities.Track
import com.bsrubacky.tapeeater.ui.media.MediaDetail
import com.bsrubacky.tapeeater.ui.media.MediaDetailContent
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MediaDetailScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_display_media(){
        val media = Media(0,"Test",0)
        composeTestRule.setContent {
            val lazyPagingItems = flowOf(
                PagingData.from(
                    listOf<Track>(
                    )
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaDetail(0)) {
                    composable<MediaDetail> {
                        MediaDetailContent(media,
                            false,
                            0,
                            lazyPagingItems,
                            {},
                            {},
                            {media->},
                            {id,mediaId->},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("media-type")
            .assertContentDescriptionEquals("Vinyl")
        composeTestRule.onNodeWithTag("media-name")
            .assertTextEquals(media.name)
        composeTestRule.onNodeWithTag("scrobbles")
            .assertTextEquals("${media.plays} Scrobbles")
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_edit_media(){
        val media = Media(0,"Test",0)
        var returnedMedia:Media? = null
        composeTestRule.setContent {
            val lazyPagingItems = flowOf(
                PagingData.from(
                    listOf<Track>(
                    )
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaDetail(0)) {
                    composable<MediaDetail> {
                        MediaDetailContent(media,
                            false,
                            0,
                            lazyPagingItems,
                            {},
                            {},
                            {media-> returnedMedia = media},
                            {id,mediaId->},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("edit-button").performClick()
        composeTestRule.onNodeWithTag("add-edit-media-dialog").assertExists()
        composeTestRule.onNodeWithTag("media-name-input").performTextClearance()
        composeTestRule.onNodeWithTag("media-name-input").performTextInput("Test 2")
        composeTestRule.onNodeWithTag("cd-button").performClick()
        composeTestRule.onNodeWithTag("save-button").performClick()
        Assert.assertNotNull(returnedMedia)
        Assert.assertEquals("Test 2",returnedMedia!!.name)
        Assert.assertEquals(2, returnedMedia.type)
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun test_delete_media(){
        val media = Media(0,"Test",0)
        var triggeredDelete = false
        composeTestRule.setContent {
            val lazyPagingItems = flowOf(
                PagingData.from(
                    listOf<Track>(
                    )
                ),
            ).collectAsLazyPagingItems()
            val navController = rememberNavController()
            SharedTransitionLayout {
                NavHost(navController, startDestination = MediaDetail(0)) {
                    composable<MediaDetail> {
                        MediaDetailContent(media,
                            false,
                            0,
                            lazyPagingItems,
                            {},
                            {triggeredDelete = true},
                            {media->},
                            {id,mediaId->},
                            this@SharedTransitionLayout,
                            this@composable) { }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("delete-button").performClick()
        composeTestRule.onNodeWithTag("delete-dialog").assertExists()
        composeTestRule.onNodeWithTag("delete-confirm-button").performClick()
        Assert.assertTrue(triggeredDelete)
    }
}
