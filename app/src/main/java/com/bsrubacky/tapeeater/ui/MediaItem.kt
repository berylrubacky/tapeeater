package com.bsrubacky.tapeeater.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsrubacky.tapeeater.R
import com.bsrubacky.tapeeater.database.entities.Media

@Composable
fun MediaItem(media: Media){
    val icon = when(media.type){
        0 -> R.drawable.vinyl
        1 -> R.drawable.cassette
        2 -> R.drawable.cd
        3 -> R.drawable.minidisc
        else -> R.drawable.cd
    }
    val iconName = when(media.type){
        0 -> R.string.vinyl
        1 -> R.string.cassette
        2 -> R.string.cd
        3 -> R.string.minidisc
        else -> R.string.cd
    }
    Box(Modifier
        .padding(horizontal = 10.dp, vertical = 2.dp)
        .fillMaxWidth()
        .height(50.dp)){
        Icon(painterResource(icon), stringResource(iconName), modifier = Modifier
            .align(Alignment.CenterStart)
            .width(50.dp)
            .padding(start = 5.dp),tint = MaterialTheme.colorScheme.primary)
        Text(text= media.name, modifier = Modifier
            .align(Alignment.Center)
            .fillMaxWidth(.80f)
            .padding(start = 20.dp), overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center, fontSize = 23.sp)
        Icon(painterResource(R.drawable.button_upload),"Scrobble", modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 5.dp))
        HorizontalDivider(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Preview
@Composable
fun MediaItemPreview(){
    val media = Media(0,"Wolfpack",1)
    MediaItem(media)
}