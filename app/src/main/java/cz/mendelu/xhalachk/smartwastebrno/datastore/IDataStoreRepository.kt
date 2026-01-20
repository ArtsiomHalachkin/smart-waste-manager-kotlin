package cz.mendelu.xhalachk.smartwastebrno.datastore

interface IDataStoreRepository {
    suspend fun setFirstRun()
    suspend fun getFirstRun(): Boolean
}
