package cz.mendelu.xhalachk.smartwastebrno.navigation


import androidx.navigation.NavController
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import cz.mendelu.xhalachk.smartwastebrno.constants.Constants
import cz.mendelu.xhalachk.smartwastebrno.extensions.removeValue
import cz.mendelu.xhalachk.smartwastebrno.model.Location

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToMap(latitude: Double?, longitude: Double?, wasteType: String?, imageUri: String?) {
        navController.navigate(MapScreenDestination(latitude, longitude, wasteType, imageUri))
    }

    override fun getNavController(): NavController {
        return navController
    }


    override fun navigateToHome() {
        navController.navigate(HomeScreenDestination) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    }

    override fun navigateToPhotoPicker() {
        navController.navigate(PhotoPickerScreenDestination){
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    }

    override fun navigateToCategory() {
        navController.navigate(CategoryScreenDestination){
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    override fun navigateToDetail(id: Long) {
        navController.navigate(DetailScreenDestination(
            id = id
        ))

    }

    override fun navigateToDetection(imageUri: String) {
        navController.navigate(DetectionScreenDestination(imageUri))
    }


}