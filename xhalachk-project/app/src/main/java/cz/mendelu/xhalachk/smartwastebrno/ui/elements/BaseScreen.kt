package cz.mendelu.xhalachk.smartwastebrno.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.secondary
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    topBarText: String,
    onBackClick: (() -> Unit)? = null,
    showLoading: Boolean = false,
    placeholderScreenContent: PlaceholderScreenContent? = null,
    floatingActionButton: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit
){

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = topBarText) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = secondary,
                    titleContentColor = textColor(),
                    navigationIconContentColor = textColor(),
                    actionIconContentColor = textColor()
                ),
                navigationIcon = {
                if (onBackClick != null){
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            }, actions = actions)
        },
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar
    ) {
        if (showLoading) {
            LoadingScreen()
        } else if (placeholderScreenContent != null){
            PlaceholderScreen(
                paddingValues = it,
                placeholderScreenContent = placeholderScreenContent)
        } else {
            content(it)
        }
    }

}