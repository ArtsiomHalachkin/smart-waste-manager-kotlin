package cz.mendelu.xhalachk.smartwastebrno.ui.activities
import androidx.lifecycle.ViewModel
import cz.mendelu.xhalachk.smartwastebrno.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppIntroViewModel @Inject constructor(private val dataStoreRepository: IDataStoreRepository) : ViewModel() {

    suspend fun setFirstRun(){
        dataStoreRepository.setFirstRun()
    }
}
