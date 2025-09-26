package com.bsrubacky.tapeeater.ui.media

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.database.entities.Media
import com.bsrubacky.tapeeater.ui.TapeEaterTheme
import com.bsrubacky.tapeeater.viewmodels.MediaDetailViewmodel
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class MediaDetail(val id: Long)

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediaDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    back: () -> Unit,
) {
    val viewmodel = viewModel<MediaDetailViewmodel>()
    val media by viewmodel.media.collectAsState()
    val hasScrobbles by viewmodel.hasScrobbles.collectAsState()
    val hasTracks by viewmodel.hasTracks.collectAsState()

    MediaDetailContent(
        media,
        hasScrobbles,
        hasTracks,
        viewmodel::scrobbleMedia,
        viewmodel::deleteMedia,
        viewmodel::editMedia,
        sharedTransitionScope,
        animatedContentScope,
        back
    )

}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediaDetailContent(
    media: Media,
    hasScrobbles: Boolean,
    hasTracks: Boolean,
    scrobbleMedia: () -> Unit,
    deleteMedia: () -> Unit,
    editMedia: (Media) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    back: () -> Unit
) {

    var editMediaDialog by remember { mutableStateOf(false) }
    var deleteMediaDialog by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            ConstraintLayout(Modifier.padding(10.dp)) {
                val (header, info, tracks) = createRefs()
                with(sharedTransitionScope) {
                    ConstraintLayout(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.inversePrimary,
                                shape = ShapeDefaults.Medium
                            )
                            .padding(10.dp)
                            .zIndex(1f)
                            .sharedBounds(
                                sharedTransitionScope.rememberSharedContentState(key = "box-${media.id}"),
                                animatedContentScope
                            )
                            .constrainAs(header) {}
                    )
                    {
                        val (type, name) = createRefs()
                        val icon = when (media.type) {
                            0 -> R.drawable.vinyl
                            1 -> R.drawable.cassette
                            2 -> R.drawable.cd
                            3 -> R.drawable.minidisc
                            else -> R.drawable.cd
                        }
                        val iconName = when (media.type) {
                            0 -> R.string.vinyl
                            1 -> R.string.cassette
                            2 -> R.string.cd
                            3 -> R.string.minidisc
                            else -> R.string.cd
                        }
                        Image(
                            painterResource(icon),
                            contentDescription = stringResource(iconName),
                            modifier = Modifier
                                .padding(5.dp)
                                .width(100.dp)
                                .sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "image-${media.id}"),
                                    animatedContentScope
                                )
                                .constrainAs(type) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                }
                                .testTag("media-type"),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                        )
                        Text(
                            media.name,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 30.sp,
                            lineHeight = 30.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                                .height(IntrinsicSize.Min)
                                .sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "name-${media.id}"),
                                    animatedContentScope
                                )
                                .constrainAs(name) {
                                    start.linkTo(type.end)
                                    end.linkTo(parent.end)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                }
                                .testTag("media-name"))
                    }
                }
                LazyRow(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.secondary,
                            shape = ShapeDefaults.Medium
                        )
                        .padding(top = 25.dp, bottom = 5.dp)
                        .constrainAs(info) {
                            top.linkTo(header.bottom, (-20).dp)
                        })
                {
                    item {
                        Text(
                            pluralStringResource(R.plurals.scrobble, media.plays, media.plays),
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.animateItem().testTag("scrobbles")
                        )
                    }
                    if (hasTracks) {
                        item {
                            Icon(
                                painterResource(R.drawable.icon_time),
                                stringResource(R.string.length),
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier
                                    .animateItem()
                                    .padding(end = 5.dp).testTag("length-icon")
                            )
                            Text(
                                "1:17:48",
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.animateItem().testTag("length")
                            )
                        }
                    }
                    if (hasScrobbles) {
                        item {
                            Icon(
                                painterResource(R.drawable.icon_music_history),
                                stringResource(R.string.last_scrobbled),
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier
                                    .animateItem()
                                    .padding(end = 5.dp).testTag("last-scrobbled-icon")
                            )
                            val sdf = SimpleDateFormat("EEE hh:mm a", Locale.getDefault())
                            Text(
                                sdf.format(media.lastPlayed),
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.animateItem().testTag("last-scrobbled")
                            )
                        }
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxWidth().constrainAs(tracks){
                    top.linkTo(info.bottom,10.dp)
                    bottom.linkTo(parent.bottom)
                })
                {
                    item{
                        IconButton(
                            {},
                            modifier = Modifier.fillMaxWidth()
                                .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp)))
                        {
                            Icon(painterResource(R.drawable.button_add),"Add Track")
                        }
                    }
                }
            }
            HorizontalFloatingToolbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = -ScreenOffset),
                expanded = true,
                floatingActionButton = {
                    FloatingToolbarDefaults.VibrantFloatingActionButton(onClick = { scrobbleMedia() }) {
                        Icon(
                            painterResource(R.drawable.button_upload),
                            "Scrobble"
                        )
                    }
                }) {

                IconButton(onClick = {}) {
                    Icon(
                        painterResource(R.drawable.button_cloud_download),
                        "Pull Data From Cloud"
                    )
                }
                IconButton(
                    onClick = { deleteMediaDialog = true },
                    modifier = Modifier.testTag("delete-button"))
                {
                    AnimatedVisibility(
                        !deleteMediaDialog, enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        with(sharedTransitionScope) {
                            Icon(
                                painterResource(R.drawable.button_delete),
                                stringResource(R.string.delete_media),
                                modifier = Modifier.sharedBounds(
                                    rememberSharedContentState(key = "delete-media"),
                                    this@AnimatedVisibility,
                                    clipInOverlayDuringTransition = OverlayClip(
                                        RoundedCornerShape(
                                            16.dp
                                        )
                                    )
                                )
                            )
                        }
                    }
                }

                IconButton(
                    onClick = { editMediaDialog = true },
                    modifier = Modifier.testTag("edit-button")
                ) {
                    AnimatedVisibility(
                        !editMediaDialog, enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        with(sharedTransitionScope) {
                            Icon(
                                painterResource(R.drawable.button_edit),
                                stringResource(R.string.edit_media),
                                modifier = Modifier.sharedBounds(
                                    rememberSharedContentState(key = "add-media"),
                                    this@AnimatedVisibility,
                                    clipInOverlayDuringTransition = OverlayClip(
                                        RoundedCornerShape(
                                            16.dp
                                        )
                                    )
                                )
                            )

                        }
                    }
                }
            }
        }
    }

    AddEditMediaDialog(
        media,
        { editMediaDialog = false },
        onSave = { media ->
            editMediaDialog = false
            editMedia(media)
        },
        sharedTransitionScope,
        editMediaDialog
    )
    DeleteMediaDialog(
        media,
        { deleteMediaDialog = false },
        onConfirm = { media ->
            deleteMediaDialog = false
            deleteMedia()
            back()
        },
        sharedTransitionScope,
        deleteMediaDialog
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun MediaDetailPreview() {
    val media = Media(0, "Wolfpack", 1)
    val hasScrobbles = false
    val hasTracks = false
    val navController = rememberNavController()

    SharedTransitionLayout {
        TapeEaterTheme {
            NavHost(navController, startDestination = MediaDetail(0)) {
                composable<MediaDetail> {
                    MediaDetailContent(
                        media,
                        hasScrobbles,
                        hasTracks,
                        {},
                        {},
                        { media: Media -> },
                        this@SharedTransitionLayout,
                        this@composable
                    ) { }
                }
            }
        }
    }
}

