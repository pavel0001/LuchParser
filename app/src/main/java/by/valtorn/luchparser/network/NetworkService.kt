package by.valtorn.luchparser.network

import by.valtorn.luchparser.network.model.Modem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Suppress("Unused")
object NetworkService {
    private const val BASE_URL = "https://backend.luch-system.ru/v1/" //modemId=794B001&perPage=1

    private val mRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val modemApiService = mRetrofit.create(ModemApi::class.java)

    suspend fun getModemData(id: String): List<Modem>? {
        return withContext(Dispatchers.IO) {
            val res = modemApiService.getModemInfo(id).body()
            res?.map {
                it.toModem()
            }
        }
    }
}