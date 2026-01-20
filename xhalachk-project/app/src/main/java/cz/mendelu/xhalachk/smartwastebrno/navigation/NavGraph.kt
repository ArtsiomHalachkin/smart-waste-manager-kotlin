package cz.mendelu.xhalachk.smartwastebrno.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.category.CategoryScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.detail.DetailScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.detection.DetectionScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.home.HomeScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.map.MapScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker.PhotoPickerScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember {
        NavigationRouterImpl(navController)
    },
    startDestination:  Any = HomeScreenDestination

) {

    NavHost(navController = navController, startDestination = startDestination) {

        composable<MapScreenDestination> { backStackEntry ->
            val destination : MapScreenDestination = backStackEntry.toRoute()
            MapScreen(navigation = navigation, destination = destination)
        }

        composable<HomeScreenDestination>{
            HomeScreen(navigation = navigation)
        }

        composable<PhotoPickerScreenDestination>{
             PhotoPickerScreen(navigation = navigation)
        }

        composable<CategoryScreenDestination>{
            CategoryScreen(navigation = navigation)
        }

        composable<DetailScreenDestination> { backStackEntry ->
            val destination : DetailScreenDestination = backStackEntry.toRoute()
            DetailScreen(
                navigation = navigation,
                destination = destination
            )
        }

        composable<DetectionScreenDestination> { backStackEntry ->
            val destination : DetectionScreenDestination = backStackEntry.toRoute()
            DetectionScreen(navigation = navigation, destination = destination)
        }
    }
}