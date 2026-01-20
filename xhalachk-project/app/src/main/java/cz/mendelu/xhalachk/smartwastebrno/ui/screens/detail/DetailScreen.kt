package cz.mendelu.xhalachk.smartwastebrno.ui.screens.detail

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.navigation.DetailScreenDestination
import cz.mendelu.xhalachk.smartwastebrno.navigation.INavigationRouter
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.BaseScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.LoadingScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.basicMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.darkTextColor
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.halfMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.primary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.secondary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.textColor
import java.nio.file.WatchEvent
import java.text.SimpleDateFormat
import java.util.Locale

const val TestTagWasteDetailType = "TestTagWasteDetailType"
const val TestTagWasteDetailDate = "TestTagWasteDetailDate"


@Composable
fun DetailScreen(
    navigation: INavigationRouter,
    destination: DetailScreenDestination,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(destination) {
        if(destination.id != null){
            viewModel.loadData(destination.id!!)
        }
    }

    BaseScreen(
        topBarText = stringResource(R.string.detail_screen),
        onBackClick = { navigation.returnBack() }
    ) { paddingValues ->

        if (!state.value.isLoading) {
            val item = state.value.wasteCollection!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(halfMargin)
            ) {


                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = stringResource(R.string.selected_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(halfMargin))
                )

                Spacer(modifier = Modifier.height(basicMargin))

                Text(
                    text = stringResource(R.string.waste_type),
                    fontSize = 18.sp,
                    color =Color.Black,
                    fontWeight = FontWeight.Medium
                )
                DetailInfoBox(text = getLocalizedContainerName(item.type), testTag = TestTagWasteDetailType)

                Spacer(modifier = Modifier.height(halfMargin))

                val dateString = if (item.date != null) {
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(item.date)
                } else {
                    stringResource(R.string.unknown)
                }

                Text(
                    text = stringResource(R.string.date),
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                DetailInfoBox(text = dateString, testTag = TestTagWasteDetailDate)
            }
        } else {
            LoadingScreen()
        }
    }
}



@Composable
fun DetailInfoBox(text: String, testTag: String = "") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(
                color = secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp)
        ,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier.testTag(testTag),
            text = text,
            color = textColor(),
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