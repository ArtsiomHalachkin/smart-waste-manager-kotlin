package cz.mendelu.xhalachk.smartwastebrno.ui.screens.category

import androidx.lifecycle.ViewModel
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.detail.DetailScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<DetailScreenUIState> =
        MutableStateFlow(value = DetailScreenUIState())

    val uiState: StateFlow<DetailScreenUIState> get() = _uiState




}
