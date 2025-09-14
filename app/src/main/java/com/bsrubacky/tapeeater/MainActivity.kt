package com.bsrubacky.tapeeater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bsrubacky.tapeeater.ui.MediaListScreen
import com.bsrubacky.tapeeater.ui.TapeEaterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            TapeEaterTheme {
                MediaListScreen()
            }
        }
    }
}