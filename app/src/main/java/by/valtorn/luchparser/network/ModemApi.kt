package by.valtorn.luchparser.network

import by.valtorn.luchparser.network.model.ApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ModemApi {

    @GET("messages")
    suspend fun getModemInfo(@Query("modemId") id: String): Response<List<ApiData>?>

}