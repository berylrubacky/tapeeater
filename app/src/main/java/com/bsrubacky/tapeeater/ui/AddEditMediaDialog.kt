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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.database.entities.Media


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AddEditMediaDialog(
    media: Media = Media(-1, "", -1),
    onDismissRequest: () -> Unit,
    onSave: (Media) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    isVisible: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    with(sharedTransitionScope) {
        AnimatedContent(
            targetState = isVisible,
            transitionSpec = {fadeIn() togetherWith fadeOut()},
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
                                    keyboardController?.hide()
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
                                .padding(5.dp)
                                .sharedBounds(
                                    rememberSharedContentState(key = "add-media"),
                                    this@AnimatedContent
                                ).constrainAs(dialog) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            val textFieldState = rememberTextFieldState(media.name)
                            TextField(
                                textFieldState,
                                label = { Text("Media Name") },
                                modifier = Modifier.fillMaxWidth().padding(20.dp)
                            )
                            var type by remember { mutableIntStateOf(media.type) }
                            LazyHorizontalStaggeredGrid(
                                rows = StaggeredGridCells.FixedSize(30.dp),
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalItemSpacing = 5.dp,
                                modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .height(90.dp)
                                    .padding(start= 20.dp,end= 20.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                item {
                                    InputChip(
                                        onClick = {
                                            type = 0
                                        }, selected = (type == 0),
                                        label = { Text(stringResource(R.string.vinyl)) },
                                        avatar = {
                                            Icon(
                                                painterResource(R.drawable.vinyl),
                                                stringResource(R.string.vinyl),
                                                modifier = Modifier.height(20.dp)
                                            )
                                        })
                                }
                                item {
                                    InputChip(
                                        onClick = {
                                            type = 2
                                        }, selected = (type == 2),
                                        label = { Text(stringResource(R.string.cd)) },
                                        avatar = {
                                            Icon(
                                                painterResource(R.drawable.cd),
                                                stringResource(R.string.cd),
                                                modifier = Modifier.height(20.dp)
                                            )
                                        })
                                }
                                item {
                                    InputChip(
                                        onClick = {
                                            type = 3
                                        }, selected = (type == 3),
                                        label = { Text(stringResource(R.string.minidisc)) },
                                        avatar = {
                                            Icon(
                                                painterResource(R.drawable.minidisc),
                                                stringResource(R.string.minidisc),
                                                modifier = Modifier.height(20.dp)
                                            )
                                        })
                                }
                                item {
                                    InputChip(
                                        onClick = {
                                            type = 1
                                        }, selected = (type == 1),
                                        label = { Text(stringResource(R.string.cassette)) },
                                        avatar = {
                                            Icon(
                                                painterResource(R.drawable.cassette),
                                                stringResource(R.string.cassette),
                                                modifier = Modifier.height(20.dp)
                                            )
                                        })
                                }
                            }
                            Row(
                                Modifier
                                    .align(Alignment.End)
                                    .padding(20.dp)
                            ) {
                                TextButton(
                                    {
                                        keyboardController?.hide()
                                        onDismissRequest() }
                                ) { Text("Cancel") }
                                TextButton(
                                    {
                                        media.name = textFieldState.text.toString()
                                        media.type = type
                                        keyboardController?.hide()
                                        onSave(media)
                                    },
                                    enabled = (type != -1 && !textFieldState.text.toString()
                                        .isEmpty())
                                ) { Text("Save") }
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
fun AddEditMediaDialogPreview(){
    SharedTransitionLayout {
        AddEditMediaDialog(media = Media(1,"Wolfpack",2),{},{},this@SharedTransitionLayout,true)
    }
}