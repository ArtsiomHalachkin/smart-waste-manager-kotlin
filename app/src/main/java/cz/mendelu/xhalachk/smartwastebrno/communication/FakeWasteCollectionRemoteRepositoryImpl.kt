package cz.mendelu.xhalachk.smartwastebrno.communication

import cz.mendelu.xhalachk.smartwastebrno.mock.APIMock
import cz.mendelu.xhalachk.smartwastebrno.model.WasteApiResponse
import javax.inject.Inject

class FakeWasteCollectionRemoteRepositoryImpl @Inject constructor() : IWasteCollectionRemoteRepository {
    override suspend fun getCollections(offset: Int): CommunicationResult<WasteApiResponse> {

        return CommunicationResult.Success(APIMock.getWasteCollectionMockResponse())

    }
}