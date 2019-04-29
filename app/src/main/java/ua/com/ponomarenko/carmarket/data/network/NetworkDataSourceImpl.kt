package ua.com.ponomarenko.carmarket.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.com.ponomarenko.carmarket.internal.NoConnectivityException

private const val TAG = "NetworkDataSource"

class NetworkDataSourceImpl(private val retrofitService: RetrofitService) : NetworkDataSource {

    private val _downloadedManufacturers = MutableLiveData<Map<String, String>>()

    override val manufacturers: LiveData<Map<String, String>>
        get() = _downloadedManufacturers

    override suspend fun getManufacturers() {
        try {
            val fetchedManufacturerResponse = retrofitService.getManufacturersAsync().await()
            if (fetchedManufacturerResponse.isSuccessful) {
                fetchedManufacturerResponse.body()?.let {
                    _downloadedManufacturers.postValue(it.wkda)
                    Log.d(TAG, "FetchedL ${it.wkda}")
                }
            } else {
                Throwable(fetchedManufacturerResponse.message())
            }
        } catch (e: NoConnectivityException) {
            Log.e(TAG, e.localizedMessage)
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage)
        }
    }


    private val _downloadedTypes = MutableLiveData<Map<String, String>>()

    override val types: LiveData<Map<String, String>>
        get() = _downloadedTypes

    override suspend fun getTypes(manufacturerKey: String?) {
        try {
            val fetchedResponse = retrofitService.getTypesAsync(manufacturerKey).await()
            if (fetchedResponse.isSuccessful) {
                fetchedResponse.body()?.let {
                    _downloadedTypes.postValue(it.wkda)
                    Log.d(TAG, "FetchedL ${it.wkda}")
                }
            } else {
                Throwable(fetchedResponse.message())
            }
        } catch (e: NoConnectivityException) {
            Log.e(TAG, "No connection", e)
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage)
        }
    }

    private val _downloadBuiltYears = MutableLiveData<Map<String, String>>()

    override val years: LiveData<Map<String, String>>
        get() = _downloadBuiltYears

    override suspend fun getYears(manufacturerKey: String?, typeKey: String?) {
        try {
            val fetchedResponse = retrofitService.getBuiltYearsAsync(manufacturerKey, typeKey).await()

            if (fetchedResponse.isSuccessful) {
                fetchedResponse.body()?.let { _downloadBuiltYears.postValue(it.wkda) }
            } else {
                Throwable(fetchedResponse.message())
            }

        } catch (e: NoConnectivityException) {
            Log.e(TAG, "No connection", e)
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage)
        }
    }
}