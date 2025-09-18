package com.bsrubacky.tapeeater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bsrubacky.tapeeater.ui.MediaDetail
import com.bsrubacky.tapeeater.ui.MediaDetailScreen
import com.bsrubacky.tapeeater.ui.MediaList
import com.bsrubacky.tapeeater.ui.MediaListScreen
import com.bsrubacky.tapeeater.ui.TapeEaterTheme

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
                            MediaListScreen(this@SharedTransitionLayout,this@composable) { id ->
                                navController.navigate(MediaDetail(id = id))
                            }
                        }
                        composable<MediaDetail> { MediaDetailScreen(this@SharedTransitionLayout,this@composable) }
                    }
                }
            }
        }
    }
}