package com.bsrubacky.tapeeater.ui.media

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.database.entities.Track
import com.bsrubacky.tapeeater.ui.helpers.LengthOutputTransformation
import com.bsrubacky.tapeeater.ui.helpers.NumberTransformation
import com.bsrubacky.tapeeater.ui.helpers.PositionTransformation
import com.bsrubacky.tapeeater.ui.media.dialogs.DeleteDialog
import com.bsrubacky.tapeeater.viewmodels.TrackViewmodel
import kotlinx.serialization.Serializable
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Serializable
data class AddEditTrackDialog(val id: Long, val mediaId: Long)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AddEditTrackDialogScreen(
    sharedTransitionScope: SharedTransitionScope,
    back: () -> Unit
) {
    val viewmodel = viewModel<TrackViewmodel>()
    val track by viewmodel.track.collectAsState()
    val numOfTracks by viewmodel.numOfTracks.collectAsState()
    val isLoaded by viewmodel.isLoaded.collectAsState()
    if (isLoaded) {
        AddEditTrackDialogContent(
            track,
            numOfTracks,
            { track ->
                if (viewmodel.isEdit) {
                    viewmodel.editTrack(track)
                } else {
                    viewmodel.addTrack(track)
                }
            },
            viewmodel::deleteTrack,
            viewmodel.isEdit,
            sharedTransitionScope,
            back
        )
    } else {
        AddEditTrackDialogLoading(
            viewmodel.isEdit,
            back
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddEditTrackDialogLoading(
    isEdit: Boolean,
    back: () -> Unit
) {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(IntrinsicSize.Min)
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
                    .testTag("add-edit-track-dialog")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        {
                            back()
                        },
                        modifier = Modifier.testTag("cancel-button")
                    ) {
                        Icon(
                            painterResource(R.drawable.button_close),
                            "Close Dialog",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        stringResource(
                            if (isEdit) {
                                R.string.edit_track
                            } else {
                                R.string.add_track
                            }
                        ),
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    if (isEdit) {
                        TextButton({}, enabled = false) {
                            Text(stringResource(R.string.delete))
                        }
                    }
                    TextButton(
                        {},
                        enabled = false,
                        modifier = Modifier.testTag("save-button")
                    ) { Text(stringResource(R.string.save)) }
                }
                LoadingIndicator(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(1f)
                        .height(150.dp)
                        .width(150.dp)
                )
            }
        }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AddEditTrackDialogContent(
    track: Track,
    numOfTracks: Int,
    addEditTrack: (Track) -> Unit,
    deleteTrack: () -> Unit,
    isEdit: Boolean,
    sharedTransitionScope: SharedTransitionScope,
    back: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var deleteTrackDialog by remember { mutableStateOf(false) }
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .height(IntrinsicSize.Min)
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
                    .testTag("add-edit-track-dialog")
            ) {
                val positionFieldState =
                    rememberTextFieldState(
                        if (isEdit) {
                            track.position
                        } else {
                            (numOfTracks + 1)
                        }.toString()
                    )
                val trackFieldState = rememberTextFieldState(track.name)
                val artistFieldState = rememberTextFieldState(track.artist)
                val albumFieldState = rememberTextFieldState(track.album)
                val albumArtistFieldState = rememberTextFieldState(track.albumArtist)
                val lengthFieldState = rememberTextFieldState(
                    if (track.length == 0L) {
                        ""
                    } else {
                        track.length.toDuration(DurationUnit.SECONDS).toString().replace(
                            Regex("[ ms]"), ""
                        )
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        {
                            back()
                            keyboardController?.hide()
                        },
                        modifier = Modifier.testTag("cancel-button")
                    ) {
                        Icon(
                            painterResource(R.drawable.button_close),
                            "Close Dialog",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        stringResource(
                            if (isEdit) {
                                R.string.edit_track
                            } else {
                                R.string.add_track
                            }
                        ),
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    if (isEdit) {
                        TextButton({
                            deleteTrackDialog = true
                        }) {
                            Text(stringResource(R.string.delete))
                        }
                    }
                    TextButton(
                        {
                            track.position = positionFieldState.text.toString().toInt()
                            track.name = trackFieldState.text.toString()
                            track.artist = artistFieldState.text.toString()
                            track.album = albumFieldState.text.toString()
                            track.albumArtist = albumArtistFieldState.text.toString()
                            val trackLength = lengthFieldState.text
                            val size = trackLength.length
                            var formattedLength = ""
                            for (i in (size - 1) downTo 0) {
                                if (i == size - 1) {
                                    formattedLength = "S"
                                }
                                if (i == size - 3) {
                                    formattedLength = "M$formattedLength"
                                }
                                if (i == size - 5) {
                                    formattedLength = "H$formattedLength"
                                }
                                formattedLength =
                                    trackLength[i].toString() + formattedLength
                            }
                            formattedLength = "PT$formattedLength"
                            track.length =
                                Duration.parseIsoString(formattedLength).inWholeSeconds
                            addEditTrack(track)
                            keyboardController?.hide()
                            back()
                        },
                        modifier = Modifier.testTag("save-button")
                    ) { Text(stringResource(R.string.save)) }

                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(20.dp)
                ) {
                    TextField(
                        positionFieldState,
                        label = {
                            Text(
                                if (isEdit) {
                                    stringResource(R.string.positions, numOfTracks)
                                } else {
                                    if (numOfTracks > 0) {
                                        stringResource(R.string.positions, numOfTracks + 1)
                                    } else {
                                        stringResource(R.string.position)
                                    }
                                }
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        inputTransformation = PositionTransformation(numOfTracks, isEdit),
                        enabled = numOfTracks > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("track-position-input")
                    )
                    TextField(
                        trackFieldState,
                        label = { Text(stringResource(R.string.track_name)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("track-name-input")
                    )
                    TextField(
                        artistFieldState,
                        label = { Text(stringResource(R.string.artist)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("track-artist-input")
                    )
                    TextField(
                        albumFieldState,
                        label = { Text(stringResource(R.string.album)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("track-album-input")
                    )
                    TextField(
                        albumArtistFieldState,
                        label = { Text(stringResource(R.string.album_artist)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("track-album-artist-input")
                    )
                    TextField(
                        lengthFieldState,
                        outputTransformation = LengthOutputTransformation(),
                        inputTransformation = InputTransformation.maxLength(6).then(
                            NumberTransformation()
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(stringResource(R.string.length)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("track-duration-input")
                    )
                }
            }
            DeleteDialog(
                track.name,
                0,
                { deleteTrackDialog = false },
                onConfirm = {
                    deleteTrackDialog = false
                    deleteTrack()
                    back()
                },
                sharedTransitionScope,
                deleteTrackDialog
            )

        }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun AddEditTrackDialogPreview() {

    SharedTransitionLayout {
        AddEditTrackDialogContent(
            Track(0, 0, "Test", "", "", "", 0, 0),
            0,
            {},
            {},
            true,
            this@SharedTransitionLayout,
        ) {}
    }
}

@Composable
@Preview
fun AddEditTrackLoadingPreview() {
    AddEditTrackDialogLoading(
        true
    ) {}
}
