package cz.mendelu.xhalachk.smartwastebrno.mock

import androidx.compose.runtime.mutableStateListOf
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import kotlinx.coroutines.flow.MutableStateFlow

object DatabaseMock {

    val all = listOf(
        WasteCollection(
            id = 1L,
            type = "Plast",
            date = System.currentTimeMillis(),
            imageUrl = ""
        ),

        WasteCollection(
            id = 2L,
            type = "Bio",
            date = System.currentTimeMillis(),
            imageUrl = ""
        ),

        WasteCollection(
            id = 3L,
            type = "Papir",
            date = System.currentTimeMillis(),
            imageUrl = ""
        ),

        WasteCollection(
            id = 4L,
            type = "Sklo",
            date = System.currentTimeMillis(),
            imageUrl = ""
        )

    )

    private var localDB = MutableStateFlow(all)
    fun getDatabaseMock() = localDB
}