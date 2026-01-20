package cz.mendelu.xhalachk.smartwastebrno.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.basicMargin
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.smallMargin

@Composable
fun BottomBar(
    onHomeClick: () -> Unit,
    onMapClick: () -> Unit
){
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .padding(vertical = smallMargin),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = onHomeClick,
            modifier = Modifier.padding(horizontal = basicMargin)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = stringResource(R.string.home),
            )
        }

        IconButton(
            onClick = onMapClick,
            modifier = Modifier.padding(horizontal = basicMargin)
        ) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = stringResource(R.string.map),
            )
        }
    }
}
