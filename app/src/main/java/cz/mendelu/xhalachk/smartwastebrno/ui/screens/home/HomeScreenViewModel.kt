package cz.mendelu.xhalachk.smartwastebrno.ui.screens.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.model.SortingItem
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.map.MapScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val localRepository: IWasteCollectionLocalRepository
) : ViewModel(), HomeScreenActions {

    private val _uiState: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(value = HomeScreenUIState())

    val uiState: StateFlow<HomeScreenUIState> get() = _uiState

    init {
        loadSortingHistory()
    }

    private fun loadSortingHistory() {
        viewModelScope.launch {
            localRepository.getAllWasteCollections().collect { wasteCollections ->
                _uiState.value = _uiState.value.copy(
                    sortingHistory = wasteCollections
                )
            }

        }
    }

    override fun onShowSheet() {
        _uiState.value = _uiState.value.copy(showSheet = true)
    }

    override fun onHideSheet() {
        _uiState.value = _uiState.value.copy(showSheet = false)
    }

    override fun onDeleteItem(item: WasteCollection) {
        viewModelScope.launch {
            localRepository.deleteWasteCollection(item)
        }
    }

}
