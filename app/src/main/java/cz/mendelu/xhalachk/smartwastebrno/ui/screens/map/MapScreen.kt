package cz.mendelu.xhalachk.smartwastebrno.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import cz.mendelu.xhalachk.smartwastebrno.constants.Constants
import cz.mendelu.xhalachk.smartwastebrno.map.CustomMapRenderer
import cz.mendelu.xhalachk.smartwastebrno.model.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.navigation.INavigationRouter
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.BaseScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.BottomBar
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.halfMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.primary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.secondary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.smallMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.tertiary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.textColor
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.navigation.MapScreenDestination

const val TestTagGoogleMap = "TestTagGoogleMap"

@Composable
fun MapScreen(
    navigation: INavigationRouter,
    destination: MapScreenDestination,
    viewModel: MapScreenViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        MapsInitializer.initialize(context)
    }

    LaunchedEffect(destination) {
        if (destination.latitude != null && destination.longitude != null) {
            viewModel.locationChanged(destination.latitude!!, destination.longitude!!)
        }
        if (destination.wasteType != null) {
            viewModel.wasteTypeSelected(destination.wasteType!!)
        }
        if (destination.imageUri != null) {
            viewModel.setIncomingImageUri(destination.imageUri)
        }
    }

    BaseScreen(
        topBarText = "Map",
        content = {
            MapScreenContent(
                paddingValues = it,
                actions = viewModel,
                data = state.value,

                navigation = navigation
            )
        },
        onBackClick = { navigation.returnBack() },
        bottomBar = {
            BottomAppBar(
                containerColor = secondary,
                contentColor = textColor()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    BottomBar(
                        onHomeClick = { navigation.navigateToHome() },
                        onMapClick = {navigation.navigateToMap(
                            latitude = Constants.LATITUDE,
                            longitude = Constants.LONGITUDE
                        )}
                    )
                }
            }
        }
    )
}

@OptIn(MapsComposeExperimentalApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreenContent(
    paddingValues: PaddingValues,
    actions: MapActions,
    data: MapScreenUIState,
    navigation: INavigationRouter
) {
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                mapToolbarEnabled = false,

            )
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(data.latitude, data.longitude),
            12f
        )
    }

    LaunchedEffect(data.latitude, data.longitude) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(LatLng(data.latitude, data.longitude), 12f)
        )
    }


    LaunchedEffect(data.isWasteSaved) {
        if (data.isWasteSaved) {
            navigation.navigateToHome()
        }
    }

    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var clusterManager by remember { mutableStateOf<ClusterManager<WasteCollection>?>(null) }
    var customRenderer by remember { mutableStateOf<CustomMapRenderer?>(null) }
    val context = LocalContext.current


    LaunchedEffect(data.collections) {
        clusterManager?.let { manager ->
            manager.clearItems()
            manager.addItems(data.collections)
            manager.cluster()
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize().testTag(TestTagGoogleMap),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ) {
            MapEffect { map ->
                if (googleMap == null) {
                    googleMap = map
                }

                if (clusterManager == null) {
                    clusterManager = ClusterManager<WasteCollection>(context, map)
                    customRenderer = CustomMapRenderer(context, map, clusterManager!!)


                    clusterManager?.apply {
                       // algorithm = GridBasedAlgorithm()
                        renderer = customRenderer

                        // -
                        setOnClusterItemClickListener { item ->
                            actions.onMarkerClick(item)
                            true
                        }
                    }
                    clusterManager?.addItems(data.collections)
                    clusterManager?.cluster()
                }

                map.setOnCameraIdleListener {
                    clusterManager?.cluster()
                }
            }
        }

        if (data.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (data.selectedCollection != null) {
            ModalBottomSheet(
                onDismissRequest = { actions.onDismissSheet() },
                containerColor = Color.White,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 32.dp)
                ) {

                    InfoLabel(text = stringResource(R.string.address))
                    InfoContainer(text = data.selectedCollection!!.name)

                    Spacer(modifier = Modifier.height(halfMargin))

                    InfoLabel(text = stringResource(R.string.type))
                    InfoContainer(text = getLocalizedContainerName(data.selectedCollection!!.wasteType))

                    Spacer(modifier = Modifier.height(halfMargin))

                    InfoLabel(text = stringResource(R.string.volume))
                    InfoContainer(text ="${data.selectedCollection!!.volume} l")

                    Spacer(modifier = Modifier.height(halfMargin))

                    if(data.imageUri != null) {
                        Button(
                            onClick = {
                                actions.onSortClicked()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = tertiary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.sort),
                                color = primary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun InfoLabel(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = smallMargin / 2, start =  smallMargin / 2)
    )
}

@Composable
fun InfoContainer(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = halfMargin),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = primary,
            fontSize = 16.sp
        )
    }
}

@Composable
fun getLocalizedContainerName(apiString: String): String {
    val resId = when (apiString) {
        "Sklo barevné", "Sklo" -> R.string.glass
        "Papír" -> R.string.paper
        "Plasty, nápojové kartony a hliníkové plechovky od nápojů" -> R.string.plastic
        "Biologický odpad" -> R.string.bio
        else -> R.string.mixed_waste
    }
    return stringResource(id = resId)
}