package cz.mendelu.xhalachk.smartwastebrno.communication



import cz.mendelu.xhalachk.smartwastebrno.model.WasteApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header

interface WasteCollectionAPI {

    @Headers("Content-Type: application/json")
    @GET("query")
    suspend fun getCollections(
        @Query("where") where: String = "1=1",
        @Query("outFields") outFields: String = "ObjectId,nazev,komodita_odpad_separovany,objem",
        @Query("outSR") outSR: String = "4326",
        @Query("f") format: String = "json",
        @Query("resultOffset") offset: Int
    ): Response<WasteApiResponse>



}