package cz.mendelu.xhalachk.smartwastebrno

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cz.mendelu.xhalachk.smartwastebrno.navigation.CategoryScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.MapScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavGraph
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavigationRouterImpl
import cz.mendelu.xhalachk.smartwastebrno.ui.activities.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UITestCategoryScreen {

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
    fun test_category_screen_displays_all_categories() {
        launchCategoryScreen()

        val categories = listOf(
            R.string.glass,
            R.string.plastic,
            R.string.paper,
            R.string.bio
        )

        categories.forEach { resId ->
            val text = composeRule.activity.getString(resId)
            composeRule.onNodeWithText(text).assertIsDisplayed()
        }
    }

    @Test
    fun test_category_click_navigates_to_map_with_filter() {
        launchCategoryScreen()

        with(composeRule) {
            val glassText = activity.getString(R.string.glass)

            onNodeWithText(glassText).assertIsDisplayed()
            onNodeWithText(glassText).performClick()

            waitForIdle()

            val currentEntry = navController.currentBackStackEntry
            val destination = currentEntry?.toRoute<MapScreenDestination>()

            assertTrue("Should have navigated to MapScreen", destination != null)

        }
    }

    private fun launchCategoryScreen() {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                val router = remember { NavigationRouterImpl(navController) }
                NavGraph(
                    navController = navController,
                    navigation = router,
                    startDestination = CategoryScreenDestination
                )
            }
        }
    }
}