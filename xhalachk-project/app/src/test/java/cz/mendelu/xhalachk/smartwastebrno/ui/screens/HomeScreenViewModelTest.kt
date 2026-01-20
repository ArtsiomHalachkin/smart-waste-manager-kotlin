package cz.mendelu.xhalachk.smartwastebrno.ui.screens

import cz.mendelu.xhalachk.smartwastebrno.MainDispatcherRule
import cz.mendelu.xhalachk.smartwastebrno.database.FakeWasteCollectionLocalRepositoryImpl
import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.home.HomeScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: IWasteCollectionLocalRepository
    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setUp() {
        fakeRepository = FakeWasteCollectionLocalRepositoryImpl()
        viewModel = HomeScreenViewModel(fakeRepository)
    }

    @Test
    fun init_loads_sorting_history_from_fake_repository() = runTest {
        val testItem = WasteCollection(id = 1L, type = "Glass", date = 1000L, imageUrl = "url")
        fakeRepository.insertWasteCollection(testItem)


        advanceUntilIdle()

        val history = viewModel.uiState.value.sortingHistory
        assertEquals(1, history.size)
        assertEquals(testItem, history[0])
    }

    @Test
    fun onDeleteItem_removes_item_from_repository() = runTest {
        val itemToDelete = WasteCollection(id = 1L, type = "Glass", date = 123L, imageUrl = "")
        fakeRepository.insertWasteCollection(itemToDelete)

        advanceUntilIdle()

        viewModel.onDeleteItem(itemToDelete)
        advanceUntilIdle()

        val history = viewModel.uiState.value.sortingHistory
        assertTrue( history.isEmpty())
    }

    @Test
    fun onShowSheet_updates_state() = runTest {

        viewModel.onShowSheet()

        assertTrue(viewModel.uiState.value.showSheet)
    }

    @Test
    fun onHideSheet_updates_state() = runTest {
        viewModel.onShowSheet()

        viewModel.onHideSheet()

        assertFalse(viewModel.uiState.value.showSheet)
    }
}