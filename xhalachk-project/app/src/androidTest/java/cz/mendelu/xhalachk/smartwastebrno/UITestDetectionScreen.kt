package cz.mendelu.xhalachk.smartwastebrno


import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.navigation.DetectionScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.MapScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavigationRouterImpl
import cz.mendelu.xhalachk.smartwastebrno.ui.activities.MainActivity
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.detection.DetectionScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UITestDetectionScreen {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_detection_shows_result_and_navigates() {
        val dummyUri = "file://dummy/image.jpg"
        val startDestination = DetectionScreenDestination(imageUri = dummyUri)

        launchDetectionScreenWithNavHost(startDestination)

        with(composeRule) {
            val expectedResultText = activity.getString(R.string.plastic)

            waitUntil(timeoutMillis = 5000) {
                onAllNodesWithText(expectedResultText).fetchSemanticsNodes().isNotEmpty()
            }
            onNodeWithText(expectedResultText).assertIsDisplayed()

            val mapDesc = activity.getString(R.string.navigate_to_map)
            onNodeWithContentDescription(mapDesc).assertIsDisplayed()
            onNodeWithContentDescription(mapDesc).performClick()

            waitForIdle()

            val currentBackStackEntry = navController.currentBackStackEntry
            val targetDestination = currentBackStackEntry?.toRoute<MapScreenDestination>()

            Assert.assertNotNull("Should have navigated to MapScreen", targetDestination)

            Assert.assertEquals("Plastic", targetDestination?.wasteType)
            Assert.assertEquals(dummyUri, targetDestination?.imageUri)
        }
    }

    @Test
    fun test_navigation_to_map_sends_correct_arguments() {
        val dummyUri = "file://dummy/image.jpg"
        val destination = DetectionScreenDestination(imageUri = dummyUri)
        launchDetectionScreenWithNavHost(destination)

        with(composeRule) {
            val expectedResultText = activity.getString(R.string.plastic)
            waitUntil(timeoutMillis = 5000) {
                onAllNodesWithText(expectedResultText).fetchSemanticsNodes()
                    .isNotEmpty()
            }

            val mapDesc = activity.getString(R.string.navigate_to_map)
            onNodeWithContentDescription(mapDesc).performClick()

            val backStackEntry = navController.currentBackStackEntry
            val args = backStackEntry?.arguments
            val route = backStackEntry?.destination?.route

            Assert.assertTrue(route?.contains("MapScreenDestination") == true)

            Assert.assertEquals("Plastic", args?.getString("wasteType"))
            Assert.assertEquals(dummyUri, args?.getString("imageUri"))
        }
    }

    private fun launchDetectionScreenWithNavHost(destination: DetectionScreenDestination) {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                val router = remember { NavigationRouterImpl(navController) }

                NavHost(
                    navController = navController,
                    startDestination = destination
                ) {
                    composable<DetectionScreenDestination> {
                        DetectionScreen(
                            navigation = router,
                            destination = destination
                        )
                    }

                    composable<MapScreenDestination> {
                        Box(Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}
