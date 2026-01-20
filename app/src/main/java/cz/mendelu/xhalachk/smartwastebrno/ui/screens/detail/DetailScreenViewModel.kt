package cz.mendelu.xhalachk.smartwastebrno.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.home.HomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository: IWasteCollectionLocalRepository
) : ViewModel(), DetailScreenActions {

    private val _uiState: MutableStateFlow<DetailScreenUIState> =
        MutableStateFlow(value = DetailScreenUIState())

    val uiState: StateFlow<DetailScreenUIState> get() = _uiState

    override fun loadData(id: Long) {
        viewModelScope.launch {
           val wasteCollection = repository.getWasteCollectionById(id)
            _uiState.value = _uiState.value.copy(
                wasteCollection = wasteCollection,
                isLoading = false
            )
        }
    }
}