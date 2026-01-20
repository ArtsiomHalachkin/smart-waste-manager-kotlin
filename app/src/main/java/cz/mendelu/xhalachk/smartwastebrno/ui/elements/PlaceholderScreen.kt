package cz.mendelu.xhalachk.smartwastebrno.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import cz.mendelu.xhalachk.smartwastebrno.ui.theme.basicMargin

data class PlaceholderScreenContent(
    val image: Int? = null,
    val text: String? = null
)


@Composable
fun PlaceholderScreen(
    paddingValues: PaddingValues,
    placeholderScreenContent: PlaceholderScreenContent
){
    Box(modifier = Modifier.fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = basicMargin,
            end = basicMargin,
            bottom = 0.dp
        )) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            if (placeholderScreenContent.image != null){
                Image(
                    painter = painterResource(placeholderScreenContent.image),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(300.dp)
                )

            }
            if (placeholderScreenContent.text != null){
                Text(text = placeholderScreenContent.text)
            }
        }
    }

}







