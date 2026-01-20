package cz.mendelu.xhalachk.smartwastebrno.ui.screens

import cz.mendelu.xhalachk.smartwastebrno.MainDispatcherRule
import cz.mendelu.xhalachk.smartwastebrno.database.FakeWasteCollectionLocalRepositoryImpl
import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.detail.DetailScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: IWasteCollectionLocalRepository
    private lateinit var viewModel: DetailScreenViewModel

    @Before
    fun setup() {
        fakeRepository = FakeWasteCollectionLocalRepositoryImpl()
        viewModel = DetailScreenViewModel(fakeRepository)

    }


    @Test
    fun loadData_successfully_updates_state_with_waste_collection() = runTest {

        val testItem = WasteCollection(id = 1L, type = "Glass", date = 1000L, imageUrl = "url")

        fakeRepository.insertWasteCollection(testItem)
        advanceUntilIdle()

        viewModel.loadData(1L)

        advanceUntilIdle()

        val currentState = viewModel.uiState.value

        assertEquals(1L, currentState.wasteCollection?.id)
        assertFalse(currentState.isLoading)
    }
}