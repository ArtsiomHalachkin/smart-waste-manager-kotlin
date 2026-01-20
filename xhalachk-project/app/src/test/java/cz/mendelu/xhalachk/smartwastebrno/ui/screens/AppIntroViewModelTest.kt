package cz.mendelu.xhalachk.smartwastebrno.ui.screens

import cz.mendelu.xhalachk.smartwastebrno.datastore.IDataStoreRepository
import cz.mendelu.xhalachk.smartwastebrno.ui.activities.AppIntroViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class AppIntroViewModelTest {

    private lateinit var viewModel: AppIntroViewModel
    private lateinit var dataStoreRepository: IDataStoreRepository

    @Before
    fun setUp() {
        dataStoreRepository = mock()
        viewModel = AppIntroViewModel(dataStoreRepository)
    }

    @Test
    fun `setFirstRun should call datastore repository`() = runTest {
        viewModel.setFirstRun()

        verify(dataStoreRepository).setFirstRun()
    }


}