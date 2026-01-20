package cz.mendelu.xhalachk.smartwastebrno

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.mock.DatabaseMock
import cz.mendelu.xhalachk.smartwastebrno.navigation.DetailScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.HomeScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavGraph
import cz.mendelu.xhalachk.smartwastebrno.navigation.NavigationRouterImpl
import cz.mendelu.xhalachk.smartwastebrno.ui.activities.MainActivity
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.detail.TestTagWasteDetailDate
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.detail.TestTagWasteDetailType
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.home.TestTagHomeScreenLazyList
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.home.TestTagLazyListItemNavigationButton
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltAndroidTest
class UITestDetailScreen {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: IWasteCollectionLocalRepository

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
    }
    @Test
    fun test_detail_screen_navigation_from_home() {
        launchHomeScreen()

        with(composeRule) {
            val targetWaste = DatabaseMock.all.first { it.id?.toInt() == 1 }

            waitForIdle()
            onNodeWithTag(TestTagHomeScreenLazyList)
                .performScrollToNode(hasText(targetWaste.type))
            onNode(hasText(targetWaste.type)).assertIsDisplayed()

            onNode(
                hasTestTag(TestTagLazyListItemNavigationButton)
                    .and(hasAnyDescendant(hasText(targetWaste.type))),
                useUnmergedTree = true
            ).performClick()
            waitForIdle()

            val currentBackStackEntry = navController.currentBackStackEntry

            val actualDestination = currentBackStackEntry?.toRoute<DetailScreenDestination>()

            val expectedDestination = DetailScreenDestination(id = targetWaste.id!!)
            assertTrue(
                "Expected to be on DetailScreen with id ${targetWaste.id}, but was $actualDestination",
                actualDestination == expectedDestination
            )
            onNodeWithTag(TestTagWasteDetailType).assertTextEquals(targetWaste.type)


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
