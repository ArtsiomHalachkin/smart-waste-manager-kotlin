
package cz.mendelu.xhalachk.smartwastebrno.ui.screens.detection
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.constants.Constants
import cz.mendelu.xhalachk.smartwastebrno.navigation.DetectionScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.INavigationRouter
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.BaseScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.LoadingScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker.PhotoPickerScreenActions
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker.PhotoPickerScreenUIState
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker.PhotoPickerScreenViewModel
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.background
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.basicMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.halfMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.primary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.secondary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.tertiary
@Composable
fun DetectionScreen(
    navigation: INavigationRouter,
    destination: DetectionScreenDestination,
    viewModel: DetectionScreenViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(destination) {
        if (destination.imageUri != null) {
            viewModel.setIncomingImageUri(destination.imageUri)
            viewModel.startAnalyze()
        }
    }
    BaseScreen(
        topBarText = stringResource(id = R.string.detection_screen),
        onBackClick = { navigation.returnBack() }
    ) { paddingValues ->
        Box(modifier = Modifier.background(background)) {
            DetectionScreenContent(
                paddingValues = paddingValues,
                data = state.value,
                actions = viewModel,
                navigation = navigation
            )
        }
    }
}
@Composable
fun DetectionScreenContent(
    paddingValues: PaddingValues,
    data: DetectionScreenUIState,
    actions: DetectionScreenActions,
    navigation: INavigationRouter
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(halfMargin),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(secondary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            if (data.imageUri != null) {
                AsyncImage(
                    model = data.imageUri!!.toUri(),
                    contentDescription = stringResource(id = R.string.selected_image),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = stringResource(id = R.string.placeholder_image),
                    modifier = Modifier.size(120.dp),
                    alpha = 0.5f,
                    colorFilter = ColorFilter.tint(secondary)
                )
            }
        }
        Spacer(modifier = Modifier.height(halfMargin))
        if(data.analysisPerformed) {
            Text(
                text = stringResource(id = R.string.detected),
                style = MaterialTheme.typography.titleMedium,
                color = tertiary,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(halfMargin))
            Card(
                colors = CardDefaults.cardColors(containerColor = secondary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = basicMargin),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = getLocalizedWasteName(data.resultText),
                        color = primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    IconButton(
                        onClick = {navigation.navigateToMap(
                            latitude = Constants.LATITUDE,
                            longitude = Constants.LONGITUDE,
                            wasteType = data.resultText,
                            imageUri = data.imageUri.toString()
                        )}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = stringResource(id = R.string.navigate_to_map),
                            tint = primary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        } else {
            LoadingScreen()
        }
    }
}
@Composable
fun getLocalizedWasteName(resultText: String): String {
    val resourceId = when (resultText) {
        "Glass" -> R.string.glass
        "Plastic" -> R.string.plastic
        "Metal" -> R.string.metal
        "Paper" -> R.string.paper
        "Bio Waste" -> R.string.bio
        else -> R.string.mixed_waste
    }
    return stringResource(id = resourceId)
}

