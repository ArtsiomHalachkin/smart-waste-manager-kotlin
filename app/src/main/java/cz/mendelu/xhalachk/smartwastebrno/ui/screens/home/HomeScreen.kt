package cz.mendelu.xhalachk.smartwastebrno.ui.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navArgument
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.constants.Constants
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.extensions.getValue
import cz.mendelu.xhalachk.smartwastebrno.extensions.removeValue
import cz.mendelu.xhalachk.smartwastebrno.model.Location
import cz.mendelu.xhalachk.smartwastebrno.model.SortingItem
import cz.mendelu.xhalachk.smartwastebrno.navigation.INavigationRouter
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.BaseScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.BottomBar
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.basicMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.halfMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.logoSize
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.secondary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.smallMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.textColor
import java.text.SimpleDateFormat
import java.util.Locale

const val TestTagHomeScreenLazyList = "TestTagHomeScreenLazyList"
const val TestTagHomeScreenFab = "HomeScreenFab"
const val TestTagHomeScreenBottomSheet = "HomeScreenBottomSheet"
const val TestTagHomeScreenScanButton = "HomeScreenScanButton"
const val TestTagHomeScreenCategoryButton = "HomeScreenCategoryButton"

const val TestTagLazyListItemNavigationButton  = "TestTagLazyListItemNavigationButton"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigation: INavigationRouter,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        val mapLocationResult = navigation.getNavController().getValue<String>(Constants.LOCATION)
        mapLocationResult?.value?.let {
            val moshi: Moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<Location> = moshi.adapter(Location::class.java)
            val location = jsonAdapter.fromJson(it)
            navigation.getNavController().removeValue<Double>(Constants.LOCATION)
        }
    }



    BaseScreen(
        topBarText = stringResource(R.string.app_name),
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
                        onHomeClick = { /* Already on home */ },
                        onMapClick = {navigation.navigateToMap(
                            latitude = Constants.LATITUDE,
                            longitude = Constants.LONGITUDE
                        )}
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onShowSheet() },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.testTag(TestTagHomeScreenFab)
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add), tint = MaterialTheme.colorScheme.primary)
            }
        }
    ) { paddingValues ->
        HomeScreenContent(
            paddingValues = paddingValues,
            data = uiState,
            actions = viewModel,
            navigation = navigation
        )
    }

    if (uiState.showSheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onHideSheet() },
            containerColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.testTag(TestTagHomeScreenBottomSheet)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(halfMargin),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.choose_way),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(halfMargin))

                Button(
                    modifier = Modifier.fillMaxWidth().testTag(TestTagHomeScreenScanButton),
                    onClick = {
                        viewModel.onHideSheet()
                        navigation.navigateToPhotoPicker()
                              },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text(stringResource(R.string.scan_a_photo), color = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.height(smallMargin))

                Button(
                    modifier = Modifier.fillMaxWidth().testTag(TestTagHomeScreenCategoryButton),
                    onClick = {
                        viewModel.onHideSheet()
                        navigation.navigateToCategory()
                              },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text(stringResource(R.string.choose_category), color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    data: HomeScreenUIState,
    actions: HomeScreenActions,
    navigation: INavigationRouter
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(halfMargin),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(logoSize)
        )

        Spacer(modifier = Modifier.height(halfMargin))

        Text(
            text = "Last sorting:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(smallMargin))

        LazyColumn(
            modifier = Modifier.testTag(TestTagHomeScreenLazyList),
            verticalArrangement = Arrangement.spacedBy(smallMargin)) {
            items(data.sortingHistory) { item ->
                SortingHistoryItem(item = item, actions = actions, navigation = navigation)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingHistoryItem(item: WasteCollection, actions: HomeScreenActions, navigation: INavigationRouter) {
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    Card(
        onClick = { navigation.navigateToDetail(item.id!!) },
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestTagLazyListItemNavigationButton),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier.padding(halfMargin),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                actions.onDeleteItem(item)
            })
            {
                Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete), tint = Color.White)
            }

            Spacer(modifier = Modifier.width(halfMargin))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = getLocalizedContainerName(item.type), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                Text(text = dateFormatter.format(item.date), fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(R.string.navigate),
                tint = MaterialTheme.colorScheme.primary
            )
        }
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