package cz.mendelu.xhalachk.smartwastebrno.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cz.mendelu.xhalachk.smartwastebrno.mock.DatabaseMock
import cz.mendelu.xhalachk.smartwastebrno.navigation.CategoryScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.HomeScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavGraph
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavigationRouterImpl
import cz.mendelu.xhalachk.smartwastebrno.navigation.PhotoPickerScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.ui.activities.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UITestHomeScreen {

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
    fun test_home_screen_displays_lazy_list() {
        launchHomeScreen()

        with(composeRule) {
            val historyWaste = DatabaseMock.all

            onNodeWithTag(TestTagHomeScreenLazyList).assertExists()
            onNodeWithTag(TestTagHomeScreenLazyList).assertIsDisplayed()

            historyWaste.forEach { waste ->
                onNodeWithTag(TestTagHomeScreenLazyList)
                    .performScrollToNode(hasText(waste.type))
                onNode(hasText(waste.type)).assertIsDisplayed()
            }
        }
    }

    @Test
    fun test_fab_click_opens_bottom_sheet() {
        launchHomeScreen()

        with(composeRule) {
            onNodeWithTag(TestTagHomeScreenFab).assertExists()
            onNodeWithTag(TestTagHomeScreenFab).performClick()

            waitForIdle()

            onNodeWithTag(TestTagHomeScreenBottomSheet).assertIsDisplayed()
            onNodeWithTag(TestTagHomeScreenScanButton).assertIsDisplayed()
            onNodeWithTag(TestTagHomeScreenCategoryButton).assertIsDisplayed()
        }
    }

    @Test
    fun test_category_screen_navigation_from_home(){
        launchHomeScreen()

        with(composeRule) {
            onNodeWithTag(TestTagHomeScreenFab).performClick()
            waitForIdle()

            onNodeWithTag(TestTagHomeScreenCategoryButton).performClick()
            waitForIdle()

            val backStackEntry = navController.currentBackStackEntry
            val destinationObject = backStackEntry?.toRoute<CategoryScreenDestination>()

            assertNotNull("Should have navigated to CategoryScreen", destinationObject)
            assertEquals(CategoryScreenDestination, destinationObject)
        }
    }

    @Test
    fun test_photo_picker_screen_navigation_from_home(){
        launchHomeScreen()

        with(composeRule) {
            onNodeWithTag(TestTagHomeScreenFab).performClick()
            waitForIdle()

            onNodeWithTag(TestTagHomeScreenScanButton).performClick()
            waitForIdle()

            val backStackEntry = navController.currentBackStackEntry
            val destinationObject = backStackEntry?.toRoute<PhotoPickerScreenDestination>()

            assertNotNull("Should have navigated to PhotoPickerScreen", destinationObject)
            assertEquals(PhotoPickerScreenDestination, destinationObject)
        }
    }

    private fun launchHomeScreen() {
        composeRule.activity.setContent {
            MaterialTheme {
                navController = rememberNavController()
                val router = remember { NavigationRouterImpl(navController) }
                NavGraph(
                    navController = navController,
                    navigation = router,
                    startDestination = HomeScreenDestination
                )
            }
        }
    }
}