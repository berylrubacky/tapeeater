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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsrubacky.tapeeater.R

@Composable
fun MediaItem(){
    Box(Modifier.padding(horizontal = 10.dp, vertical = 2.dp).fillMaxWidth().height(50.dp)){
        Icon(painterResource(R.drawable.minidisc),"Cassette", modifier = Modifier.align(Alignment.CenterStart).width(50.dp).padding(start = 5.dp),tint = MaterialTheme.colorScheme.primary)
        Text(text= "Signals", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, fontSize = 23.sp)
        Icon(painterResource(R.drawable.button_upload),"Scrobble", modifier = Modifier.align(Alignment.CenterEnd).padding(end = 5.dp))
        HorizontalDivider(modifier =Modifier.align(Alignment.BottomCenter))
    }
}

@Preview
@Composable
fun MediaItemPreview(){
    MediaItem()
}