package com.bsrubacky.tapeeater.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsrubacky.tapeeater.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediaListScreen() {

    Scaffold{ paddingValues ->
        Box(Modifier.padding(paddingValues).fillMaxWidth().fillMaxHeight()){
            LazyColumn(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                item{
                    Box(Modifier.padding(horizontal = 10.dp, vertical = 2.dp).fillMaxWidth().height(50.dp)){
                        Icon(painterResource(R.drawable.cassette),"Cassette", modifier = Modifier.align(Alignment.CenterStart).width(50.dp).padding(start = 5.dp), tint = MaterialTheme.colorScheme.primary)
                        Text(text= "Wolfpack", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, fontSize = 23.sp)
                        Icon(painterResource(R.drawable.button_upload),"Scrobble", modifier = Modifier.align(Alignment.CenterEnd).padding(end = 5.dp))
                        HorizontalDivider(modifier =Modifier.align(Alignment.BottomCenter))
                    }
                }
                item{
                    Box(Modifier.padding(horizontal = 10.dp, vertical = 2.dp).fillMaxWidth().height(50.dp)){
                        Icon(painterResource(R.drawable.minidisc),"Cassette", modifier = Modifier.align(Alignment.CenterStart).width(50.dp).padding(start = 5.dp),tint = MaterialTheme.colorScheme.primary)
                        Text(text= "Signals", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, fontSize = 23.sp)
                        Icon(painterResource(R.drawable.button_upload),"Scrobble", modifier = Modifier.align(Alignment.CenterEnd).padding(end = 5.dp))
                        HorizontalDivider(modifier =Modifier.align(Alignment.BottomCenter))
                    }
                }
                item{
                    Box(Modifier.padding(horizontal = 10.dp, vertical = 2.dp).fillMaxWidth().height(50.dp)){
                        Icon(painterResource(R.drawable.vinyl),"Cassette", modifier = Modifier.align(Alignment.CenterStart).width(50.dp).padding(start = 5.dp),tint = MaterialTheme.colorScheme.primary)
                        Text(text= "Furfag", modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center, fontSize = 23.sp)
                        Icon(painterResource(R.drawable.button_upload),"Scrobble", modifier = Modifier.align(Alignment.CenterEnd).padding(end = 5.dp))
                        HorizontalDivider(modifier =Modifier.align(Alignment.BottomCenter))
                    }
                }
                item{
                    Box(Modifier.padding(horizontal = 10.dp, vertical = 2.dp).fillMaxWidth().height(50.dp)){
                        Icon(painterResource(R.drawable.cd),"Cassette", modifier = Modifier.align(Alignment.CenterStart).width(50.dp).padding(start = 5.dp),tint = MaterialTheme.colorScheme.primary)
                        Text(text= "Swear I Saw Your Mouth Move", modifier = Modifier.align(Alignment.Center).fillMaxWidth(.80f).padding(start = 20.dp), overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center, fontSize = 23.sp)
                        Icon(painterResource(R.drawable.button_upload),"Scrobble", modifier = Modifier.align(Alignment.CenterEnd).padding(end = 5.dp))
                        HorizontalDivider(modifier =Modifier.align(Alignment.BottomCenter))
                    }
                }
            }
            HorizontalFloatingToolbar(
                expanded = true,
                floatingActionButton = {
                    FloatingToolbarDefaults.VibrantFloatingActionButton(onClick = {}){
                        Icon(painterResource(R.drawable.button_add),"Add Media")
                    }
                                       },
                modifier = Modifier.align(Alignment.BottomCenter).offset(y = -ScreenOffset),
                colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors()
            ) {
                IconButton(onClick = {}) {
                    Icon(painterResource(R.drawable.button_settings), "")
                }
                IconButton(onClick = {}) {
                    Icon(painterResource(R.drawable.button_filter),"Filter Media")
                }
                IconButton(onClick = {}) {
                    Icon(painterResource(R.drawable.button_search), "Search Media")
                }
            }
        }

    }

}

@Composable
@Preview
fun MediaListScreenPreview() {
    TapeEaterTheme {
        MediaListScreen()
    }
}
