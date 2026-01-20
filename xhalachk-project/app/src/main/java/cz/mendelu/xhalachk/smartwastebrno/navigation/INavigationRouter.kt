package cz.mendelu.xhalachk.smartwastebrno.navigation


import androidx.navigation.NavController

interface INavigationRouter {
    fun returnBack()
    fun navigateToMap(latitude: Double?, longitude: Double?, wasteType: String? = null, imageUri: String? = null)
    fun getNavController(): NavController
    fun navigateToHome()
    fun navigateToPhotoPicker()
    fun navigateToCategory()
    fun navigateToDetail(id: Long)
    fun navigateToDetection(imageUri: String)
}

