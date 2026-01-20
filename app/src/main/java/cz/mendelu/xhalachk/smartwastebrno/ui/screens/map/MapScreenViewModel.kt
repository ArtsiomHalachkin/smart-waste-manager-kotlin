package cz.mendelu.xhalachk.smartwastebrno.ui.screens.map


import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.communication.CommunicationResult
import cz.mendelu.xhalachk.smartwastebrno.communication.IWasteCollectionRemoteRepository
import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.model.Location
import cz.mendelu.xhalachk.smartwastebrno.model.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.model.WasteCollectionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MapScreenViewModel @Inject constructor(
    private val wasteCollectionRemoteRepository: IWasteCollectionRemoteRepository,
    private val localRepository: IWasteCollectionLocalRepository,
    @ApplicationContext private val context: Context
) : ViewModel(), MapActions {

    private val _uiState = MutableStateFlow(MapScreenUIState())
    val uiState: StateFlow<MapScreenUIState> = _uiState

    private var allCollections: List<WasteCollection> = emptyList()

    override fun locationChanged(latitude: Double, longitude: Double) {
        _uiState.value = _uiState.value.copy(
            latitude = latitude,
            longitude = longitude,
            locationChanged = true
        )
    }

    override fun wasteTypeSelected(wasteType: String) {
        _uiState.value = _uiState.value.copy(
            filter = wasteType
        )
        applyFilter()
    }

    override fun onMarkerClick(collection: WasteCollection): Boolean {
        _uiState.value = _uiState.value.copy(
            selectedCollection = collection
        )

        return true
    }

    override fun onDismissSheet() {
        _uiState.value = _uiState.value.copy(
            selectedCollection = null
        )
    }


    override fun onSortClicked() {
        val selectedBin = _uiState.value.selectedCollection ?: return
        val wasteType = selectedBin.wasteType

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)

            var uploadedImageUrl: String? = null

            if (_uiState.value.imageUri != null) {
                uploadedImageUrl = uploadImage(Uri.parse(_uiState.value.imageUri))
            }

            val historyItem = cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection(
                type = wasteType,
                date = System.currentTimeMillis(),
                imageUrl = uploadedImageUrl!!
            )

            localRepository.insertWasteCollection(historyItem)

            _uiState.value = _uiState.value.copy(loading = false, isWasteSaved = true)
            onDismissSheet()
        }
    }

    private suspend fun uploadImage(uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                val bytes = context.contentResolver.openInputStream(uri)?.use {
                    it.readBytes()
                } ?: return@withContext null

                val fileName = "sort_${System.currentTimeMillis()}.jpg"

                val success = localRepository.uploadImage(bytes, fileName)

                if (success) {
                    return@withContext localRepository.getPublicUrl(fileName)

                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override fun setIncomingImageUri(uriString: String?) {
        _uiState.value = _uiState.value.copy(
            imageUri = uriString
        )
    }

    private fun applyFilter() {
        if (allCollections.isEmpty()) return

        val currentFilter = _uiState.value.filter

        val filteredList = if (currentFilter.isNullOrEmpty() || currentFilter == "Mixed Waste") {
            allCollections
        } else {
            val targetApiString = when {
                currentFilter.contains("Glass", ignoreCase = true) -> "Sklo barevné"
                currentFilter.contains("Paper", ignoreCase = true) -> "Papír"
                currentFilter.contains("Plastic", ignoreCase = true) -> "Plasty, nápojové kartony a hliníkové plechovky od nápojů"
                currentFilter.contains("Metal", ignoreCase = true) -> "Plasty, nápojové kartony a hliníkové plechovky od nápojů"
                currentFilter.contains("Bio", ignoreCase = true) -> "Biologický odpad"
                else -> ""
            }

            if (targetApiString.isNotEmpty()) {
                // Filter the Master List
                allCollections.filter {
                    it.wasteType.equals(targetApiString, ignoreCase = true)
                }
            } else {
                allCollections
            }
        }

        _uiState.value = _uiState.value.copy(
            collections = filteredList
        )
    }

    init {
        viewModelScope.launch {
            val rawResult = wasteCollectionRemoteRepository.getCollections(offset = 0)

            when (rawResult) {
                is CommunicationResult.ConnectionError -> {
                    _uiState.value = _uiState.value.copy(
                        error = MapScreenError(R.string.no_internet_connection)
                    )
                }

                is CommunicationResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = MapScreenError(R.string.failed_to_load_collections)
                    )
                }

                is CommunicationResult.Exception -> {
                    _uiState.value = _uiState.value.copy(
                        error = MapScreenError(R.string.exception)
                    )
                }

                is CommunicationResult.Success -> {
                    val parsedData = rawResult.data

                    val cleanList = parsedData.features.map { feature ->
                        WasteCollection(
                            id = feature.attributes.id,
                            name = feature.attributes.name ?: "unknown",
                            wasteType = feature.attributes.wasteCommodity ?: "unknown",
                            latitude = feature.geometry.latitude,
                            longitude = feature.geometry.longitude,
                            volume = feature.attributes.volume ?: 0
                        )
                    }

                    allCollections = cleanList

                    _uiState.value = _uiState.value.copy(
                        loading = false
                    )

                    applyFilter()
                }
            }
        }
    }
}