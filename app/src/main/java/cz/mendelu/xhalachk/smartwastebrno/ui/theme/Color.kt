package cz.mendelu.xhalachk.smartwastebrno.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val primary =  Color(0xFFFFFFFF)
val secondary =Color(0xFF456882)
val tertiary = Color(0xFF1B3C53)
val background = Color(0xFFFFFFFF)

val lightTextColor = Color(0xFFFFFFFF)
val darkTextColor = Color(0xFFFFFFFF)

@Composable
fun textColor() = if (isSystemInDarkTheme()) darkTextColor else lightTextColor
