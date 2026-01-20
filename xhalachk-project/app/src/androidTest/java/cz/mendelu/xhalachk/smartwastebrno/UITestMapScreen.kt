package cz.mendelu.xhalachk.smartwastebrno

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cz.mendelu.xhalachk.smartwastebrno.navigation.MapScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavGraph
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavigationRouterImpl
import cz.mendelu.xhalachk.smartwastebrno.ui.activities.MainActivity
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.map.MapScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.map.TestTagGoogleMap
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UITestMapScreen {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_map_screen_displays_map() {
        val destination = MapScreenDestination(
            latitude = 49.1951,
            longitude = 16.6068,
            wasteType = null,
            imageUri = null
        )

        launchMapScreen(destination)
        composeRule.onNodeWithTag(TestTagGoogleMap).assertIsDisplayed()
    }


    private fun launchMapScreen(destination: MapScreenDestination) {
        composeRule.activity.setContent {
            MaterialTheme {
                val navController = rememberNavController()
                val router = remember { NavigationRouterImpl(navController) }

                MapScreen(
                    navigation = router,
                    destination = destination
                )
            }
        }
    }
}
