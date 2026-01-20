package cz.mendelu.xhalachk.smartwastebrno.communication


import cz.mendelu.xhalachk.smartwastebrno.communication.CommunicationResult.*
import cz.mendelu.xhalachk.smartwastebrno.model.WasteApiResponse
import cz.mendelu.xhalachk.smartwastebrno.model.WasteCollectionResponse
import javax.inject.Inject

class WasteCollectionRemoteRepositoryImpl @Inject constructor(
    private val api: WasteCollectionAPI): IWasteCollectionRemoteRepository {

    override suspend fun getCollections(offset: Int): CommunicationResult<WasteApiResponse> {
        return processResponse {
            api.getCollections(offset = offset)
        }
    }


}