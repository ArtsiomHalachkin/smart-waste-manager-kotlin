package cz.mendelu.xhalachk.smartwastebrno.model

import java.util.Date

data class SortingItem(
    val id: Int,
    val type: String,
    val date: Date
)
