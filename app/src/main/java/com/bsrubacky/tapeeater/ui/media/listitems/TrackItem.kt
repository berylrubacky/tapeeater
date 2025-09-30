package com.bsrubacky.tapeeater.ui.media.listitems

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.database.entities.Track
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LazyItemScope.TrackItem(
    track: Track,
    scrobbleTrack: (Track) -> Unit,
    editTrack: (Long, Long) -> Unit
) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .animateItem()
    ) {
            val (position, info, buttons) = createRefs()
            Text(
                track.position.toString(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(15.dp)
                    .constrainAs(position) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
            Column(
                Modifier
                    .fillMaxWidth(.8f)
                    .padding(bottom = 5.dp)
                    .constrainAs(info) {
                        top.linkTo(parent.top)
                        start.linkTo(position.end)
                        end.linkTo(buttons.start)
                    }) {
                Text(
                    track.name,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .heightIn(0.dp, 70.dp)
                )
                Row(modifier = Modifier.heightIn(0.dp, 70.dp)) {
                    Text(
                        track.artist,
                        fontSize = 15.sp,
                    )
                    Text("  -  ", fontSize = 15.sp)
                    Text(
                        track.album,
                        fontSize = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Text(
                    "(${track.length.toDuration(DurationUnit.SECONDS)})",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.outline,
                )
            }
            Column(
                Modifier
                    .padding(bottom = 5.dp)
                    .constrainAs(buttons) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }) {
                IconButton({ scrobbleTrack(track) }) {
                    Icon(painterResource(R.drawable.button_upload), "Scrobble")
                }
                IconButton({ editTrack(track.id, track.mediaId) }) {
                    Icon(painterResource(R.drawable.button_edit), "Edit")
                }
            }
        }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun TrackItemPreview() {
    LazyColumn {
        items(1) {
            TrackItem(
                Track(
                    0,
                    0,
                    "Test Track 1",
                    "Test Artist",
                    "Test Album",
                    "",
                    132,
                    1
                ),
                {}
            ) { id, mediaId -> }
        }
    }
}

