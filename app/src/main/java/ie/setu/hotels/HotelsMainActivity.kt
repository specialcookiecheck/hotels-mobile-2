package ie.setu.hotels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.hotels.ui.screens.home.HomeScreen
import ie.setu.hotels.ui.theme.HotelsTheme

@AndroidEntryPoint
class HotelsMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HotelsTheme { HomeScreen() }
        }
    }
}
