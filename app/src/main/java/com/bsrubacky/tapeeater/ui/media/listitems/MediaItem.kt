package com.bsrubacky.tapeeater.ui.media.listitems

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.database.entities.Media

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LazyItemScope.MediaItem(
    media: Media,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navToDetail: (Long) -> Unit
) {
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
        else -> R.drawable.cd
    }
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .height(45.dp)
            .animateItem()
    ) {
        with(sharedTransitionScope) {
            val (click, scrobble, divider) = createRefs()
            ConstraintLayout(
                Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .clickable {
                        navToDetail(media.id)
                    }
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "box-${media.id}"),
                        animatedVisibilityScope
                    )
                    .constrainAs(click) {
                        start.linkTo(parent.start)
                        end.linkTo(scrobble.start)
                    }
                    .fillMaxWidth(.85f)
                    .testTag("media-item")
            ) {
                val (image, name) = createRefs()
                Icon(
                    painterResource(icon),
                    stringResource(iconName),
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "image-${media.id}"),
                            animatedVisibilityScope
                        )
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        },
                    tint = MaterialTheme.colorScheme.primary,

                    )
                Text(
                    text = media.name,
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "name-${media.id}"),
                            animatedVisibilityScope
                        )
                        .constrainAs(name) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(image.end)
                            end.linkTo(parent.end)
                        },
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontSize = 23.sp,
                    lineHeight = 23.sp
                )
            }
            IconButton(
                onClick = {}, modifier = Modifier
                    .constrainAs(scrobble) {
                        end.linkTo(parent.end)
                    }) {
                Icon(painterResource(R.drawable.button_upload), "Scrobble")
            }
            HorizontalDivider(modifier = Modifier.constrainAs(divider) {
                top.linkTo(click.bottom)
            })
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun MediaItemPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(true) {
            LazyColumn {
                items(1) {
                    MediaItem(
                        Media(0, "Test", 0),
                        this@SharedTransitionLayout,
                        this@AnimatedVisibility
                    ) { long -> }
                }
            }
        }
    }
}
