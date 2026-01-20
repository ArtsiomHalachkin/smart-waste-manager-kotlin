package cz.mendelu.xhalachk.smartwastebrno.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.mendelu.xhalachk.smartwastebrno.navigation.HomeScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavGraph
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.SmartWasteBrnoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartWasteBrnoTheme {
                NavGraph(startDestination = HomeScreenDestination)
            }
        }
    }
}
