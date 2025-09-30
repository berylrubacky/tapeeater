package com.bsrubacky.tapeeater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bsrubacky.tapeeater.ui.media.MediaDetail
import com.bsrubacky.tapeeater.ui.media.MediaDetailScreen
import com.bsrubacky.tapeeater.ui.media.MediaList
import com.bsrubacky.tapeeater.ui.media.MediaListScreen
import com.bsrubacky.tapeeater.ui.TapeEaterTheme
import com.bsrubacky.tapeeater.ui.media.AddEditTrackDialog
import com.bsrubacky.tapeeater.ui.media.AddEditTrackDialogScreen

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TapeEaterTheme {
                SharedTransitionLayout {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = MediaList) {
                        composable<MediaList> {
                            MediaListScreen(
                                this@SharedTransitionLayout,
                                this@composable)
                            { id ->
                                navController.navigate(MediaDetail(id = id))
                            }
                        }
                        composable<MediaDetail> {
                            MediaDetailScreen(
                                this@SharedTransitionLayout,
                                this@composable,
                                {id, mediaId ->
                                    navController.navigate(AddEditTrackDialog(id, mediaId))
                                })
                            { navController.popBackStack() }
                        }
                        composable<AddEditTrackDialog> {
                            AddEditTrackDialogScreen(this@SharedTransitionLayout)
                            {navController.popBackStack()}
                        }
                    }
                }
            }
        }
    }
}
