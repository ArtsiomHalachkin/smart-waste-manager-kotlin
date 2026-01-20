package cz.mendelu.xhalachk.smartwastebrno.ui.screens.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.constants.Constants
import cz.mendelu.xhalachk.smartwastebrno.navigation.INavigationRouter
import cz.mendelu.xhalachk.smartwastebrno.ui.elements.BaseScreen
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.basicMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.halfMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.primary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.secondary

@Composable
fun CategoryScreen(
    navigation: INavigationRouter,
    viewModel: CategoryScreenViewModel = hiltViewModel()
) {
    BaseScreen(
        topBarText = stringResource(R.string.category),
        onBackClick = { navigation.returnBack() }
    ) { paddingValues ->
        CategoryScreenContent(
            paddingValues = paddingValues,
            onCategoryClick = { category ->
                navigation.navigateToMap(
                    latitude = Constants.LATITUDE,
                    longitude = Constants.LONGITUDE,
                    wasteType = category
                )
            }
        )
    }
}

@Composable
fun CategoryScreenContent(
    paddingValues: PaddingValues,
    onCategoryClick: (String) -> Unit
) {
    val categories = listOf(
        stringResource(R.string.glass),
        stringResource(R.string.plastic),
        stringResource(R.string.paper),
        stringResource(R.string.bio)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(halfMargin)
    ) {
        categories.forEach { category ->
            CategoryButton(
                text = category,
                onClick = { onCategoryClick(category) }
            )
            Spacer(modifier = Modifier.height(halfMargin))
        }
    }
}

@Composable
fun CategoryButton(
    text: String,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = secondary),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = basicMargin),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                color = primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = null,
                tint = primary
            )
        }
    }
}