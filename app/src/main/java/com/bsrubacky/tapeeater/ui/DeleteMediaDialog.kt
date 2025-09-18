package com.bsrubacky.tapeeater.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.database.entities.Media

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DeleteMediaDialog(
    media: Media,
    onDismissRequest: () -> Unit,
    onConfirm: (Media) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    isVisible: Boolean
) {
    with(sharedTransitionScope) {
        AnimatedContent(
            targetState = isVisible,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "MediaEditDetails"
        ) { visible ->
            Box(Modifier.fillMaxSize()) {
                if (visible) {
                    ConstraintLayout {
                        val (dialog, scrim) = createRefs()
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    onDismissRequest()
                                }
                                .background(Color.Black.copy(alpha = 0.5f))
                                .constrainAs(scrim) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.8f)
                                .height(IntrinsicSize.Min)
                                .sharedBounds(
                                    rememberSharedContentState(key = "delete-media"),
                                    this@AnimatedContent
                                )
                                .constrainAs(dialog) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            Text(stringResource(R.string.delete_dialog_title), fontSize = 25.sp, modifier = Modifier.padding(20.dp))
                            Text(stringResource(R.string.delete_dialog_prompt,media.name),modifier = Modifier.padding(start = 20.dp,end=20.dp))
                            Row(
                                Modifier
                                    .align(Alignment.End)
                                    .padding(20.dp)
                            ) {
                                TextButton(
                                    {
                                        onDismissRequest() }
                                ) { Text(stringResource(R.string.cancel)) }
                                TextButton(
                                    {
                                        onConfirm(media)
                                    }
                                ) { Text(stringResource(R.string.delete)) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun DeleteMediaDialogPreview(){
    SharedTransitionLayout {
        DeleteMediaDialog(media = Media(1,"Wolfpack",2),{},{},this@SharedTransitionLayout,true)
    }
}
