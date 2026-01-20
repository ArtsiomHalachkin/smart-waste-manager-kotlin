package cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.constants.Constants
import cz.mendelu.xhalachk.smartwastebrno.navigation.INavigationRouter
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.BaseScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.background
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.basicMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.halfMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.primary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.secondary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.smallMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.tertiary

@Composable
fun PhotoPickerScreen(
    navigation: INavigationRouter,
    viewModel: PhotoPickerScreenViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> viewModel.onImageSelected(uri) }
    )

    BaseScreen(
        topBarText = stringResource(id = R.string.photo_picker),
        onBackClick = { navigation.returnBack() }
    ) { paddingValues ->
        Box(modifier = Modifier.background(background)) {
            PhotoPickerScreenContent(
                paddingValues = paddingValues,
                data = state.value,
                actions = viewModel,
                onPickPhotoClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                navigation = navigation
            )
        }
    }
}

@Composable
fun PhotoPickerScreenContent(
    paddingValues: PaddingValues,
    data: PhotoPickerScreenUIState,
    actions: PhotoPickerScreenActions,
    onPickPhotoClick: () -> Unit,
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
            if (data.selectedImageUri != null) {
                AsyncImage(
                    model = data.selectedImageUri,
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


            Button(
                onClick = onPickPhotoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = tertiary,
                    contentColor = primary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.pick_photo),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

        Spacer(modifier = Modifier.height(halfMargin))


       if (data.selectedImageUri != null) {

            Button(
                onClick = { navigation.navigateToDetection(data.selectedImageUri.toString()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = tertiary,
                    contentColor = primary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.analyze),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(halfMargin))
        }
    }
}

