package cz.mendelu.xhalachk.smartwastebrno.ui.screens

import android.content.Context
import android.content.res.AssetManager
import com.google.mlkit.vision.objects.DetectedObject
import cz.mendelu.xhalachk.smartwastebrno.MainDispatcherRule
import cz.mendelu.xhalachk.smartwastebrno.analyzers.CustomModelObjectDetector
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.detection.DetectionScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.io.ByteArrayInputStream

@OptIn(ExperimentalCoroutinesApi::class)
class DetectionScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: DetectionScreenViewModel

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockAssets: AssetManager

    @Mock
    private lateinit var mockCustomDetector: CustomModelObjectDetector

    private val fakeLabels = "bottle\ncan\napple\nnewspaper"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // Mock the AssetManager to prevent crash in 'init'
        whenever(mockContext.assets).thenReturn(mockAssets)

        val inputStream = ByteArrayInputStream(fakeLabels.toByteArray())
        whenever(mockAssets.open("labels.txt")).thenReturn(inputStream)

        viewModel = DetectionScreenViewModel(mockContext, mockCustomDetector)
    }

    @Test
    fun test_classify_waste_by_text_maps_raw_labels_to_correct_categories() {
        assertEquals("Plastic", viewModel.classifyWasteByText("plastic bottle"))
        assertEquals("Glass", viewModel.classifyWasteByText("wine pitcher"))
        assertEquals("Metal", viewModel.classifyWasteByText("aluminum can"))
        assertEquals("Bio Waste", viewModel.classifyWasteByText("rotten apple"))
        assertEquals("Mixed Waste", viewModel.classifyWasteByText("unknown object"))
    }

    @Test
    fun test_set_incoming_image_uri_updates_state_and_resets_analysis() = runTest {
        val testUri = "file://test/image.jpg"

        viewModel.setIncomingImageUri(testUri)

        val state = viewModel.uiState.value
        assertEquals(testUri, state.imageUri)
        assertEquals("", state.resultText)
        assertFalse(state.analysisPerformed)
    }

    @Test
    fun test_handle_detection_results_with_empty_list_returns_no_objects_detected() = runTest {
        viewModel.handleDetectionResults(emptyList())

        val state = viewModel.uiState.value
        assertEquals("No objects detected.", state.resultText)
        assertTrue(state.analysisPerformed)
    }

    @Test
    fun test_handle_detection_results_maps_detected_object_index_to_waste_type_correctly() = runTest {
        val targetIndex = 0
        val expectedLabelName = "bottle"
        val expectedWasteType = "Plastic"

        val mockLabel = mock(DetectedObject.Label::class.java)
        whenever(mockLabel.index).thenReturn(targetIndex)
        whenever(mockLabel.text).thenReturn(expectedLabelName)

        val mockObject = mock(DetectedObject::class.java)
        whenever(mockObject.labels).thenReturn(listOf(mockLabel))

        viewModel.handleDetectionResults(listOf(mockObject))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue( state.analysisPerformed)
        assertEquals( expectedWasteType, state.resultText)
    }

    @Test
    fun test_handle_detection_results_handles_unknown_index_gracefully() = runTest {
        val outOfBoundsIndex = 999

        val mockLabel = mock(DetectedObject.Label::class.java)
        whenever(mockLabel.index).thenReturn(outOfBoundsIndex)

        val mockObject = mock(DetectedObject::class.java)
        whenever(mockObject.labels).thenReturn(listOf(mockLabel))

        viewModel.handleDetectionResults(listOf(mockObject))

        assertEquals("Mixed Waste", viewModel.uiState.value.resultText)
    }
}