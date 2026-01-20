package cz.mendelu.xhalachk.smartwastebrno.communication

import cz.mendelu.xhalachk.smartwastebrno.model.WasteApiResponse
import cz.mendelu.xhalachk.smartwastebrno.model.WasteCollectionResponse


interface IWasteCollectionRemoteRepository : IBaseRemoteRepository {

    suspend fun getCollections(offset: Int): CommunicationResult<WasteApiResponse>




}