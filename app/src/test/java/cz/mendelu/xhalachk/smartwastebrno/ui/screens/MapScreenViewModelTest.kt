package cz.mendelu.xhalachk.smartwastebrno.ui.screens

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.communication.CommunicationResult
import cz.mendelu.xhalachk.smartwastebrno.communication.IWasteCollectionRemoteRepository
import cz.mendelu.xhalachk.smartwastebrno.model.WasteApiResponse
import cz.mendelu.xhalachk.smartwastebrno.MainDispatcherRule
import cz.mendelu.xhalachk.smartwastebrno.communication.FakeWasteCollectionRemoteRepositoryImpl
import cz.mendelu.xhalachk.smartwastebrno.database.FakeWasteCollectionLocalRepositoryImpl
import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.map.MapScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MapScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MapScreenViewModel

    private lateinit var fakeRemoteRepository: IWasteCollectionRemoteRepository
    private lateinit var fakeLocalRepository: IWasteCollectionLocalRepository

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockContentResolver: ContentResolver

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        whenever(mockContext.contentResolver).thenReturn(mockContentResolver)

        fakeLocalRepository = FakeWasteCollectionLocalRepositoryImpl()
        fakeRemoteRepository = FakeWasteCollectionRemoteRepositoryImpl()

        viewModel = MapScreenViewModel(fakeRemoteRepository, fakeLocalRepository, mockContext)
    }

    @Test
    fun init_loads_collections_successfully_from_fake() = runTest {

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.loading)

        assertEquals(1, state.collections.size)
        assertEquals("Test Bin 1", state.collections[0].name)
    }

    @Test
    fun test_onMarkerClick_sets_selected_collection() = runTest {
        advanceUntilIdle()

        val collection = viewModel.uiState.value.collections[0]

        val handled = viewModel.onMarkerClick(collection)

        assertTrue(handled)
        assertEquals(collection, viewModel.uiState.value.selectedCollection)
    }


}