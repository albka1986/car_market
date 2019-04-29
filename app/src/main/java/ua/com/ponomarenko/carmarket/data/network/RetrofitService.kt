package ua.com.ponomarenko.carmarket.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ua.com.ponomarenko.carmarket.data.network.repsonse.BaseResponse

const val API_KEY = "coding-puzzle-client-449cc9d"

interface RetrofitService {

    @GET("manufacturer")
    fun getManufacturersAsync(@Query("page") page: Int = 0, @Query("pageSize") pageSize: Int = 999): Deferred<Response<BaseResponse>>

    @GET("main-types")
    fun getTypesAsync(@Query("manufacturer") manufacturer: String?, @Query("page") page: Int = 0, @Query("pageSize") pageSize: Int = 999): Deferred<Response<BaseResponse>>

    @GET("built-dates")
    fun getBuiltYearsAsync(@Query("manufacturer") manufacturer: String?, @Query("main-type") mainType: String?): Deferred<Response<BaseResponse>>

    companion object {
        operator fun invoke(
                connectivityInterceptor: ConnectivityInterceptor
        ): RetrofitService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("wa_key", API_KEY)
                        .build()
                val request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .url(url)
                        .build()

                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(connectivityInterceptor)
                    .addInterceptor(logging)
                    .build()


            return Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://api-aws-eu-qa-1.auto1-test.com/v1/car-types/")
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                    .create(RetrofitService::class.java)


        }
    }
}