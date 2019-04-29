package ua.com.ponomarenko.carmarket.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.com.ponomarenko.carmarket.data.network.NetworkDataSource

class CarMarketRepositoryImpl(private val networkDataSource: NetworkDataSource) : CarMarketRepository {

    override suspend fun getManufacturers(): LiveData<Map<String, String>> {
        return withContext(Dispatchers.IO) {
            initManufacturerData()
            return@withContext networkDataSource.manufacturers
        }
    }

    override suspend fun getTypes(manufacturerKey: String?): LiveData<Map<String, String>> {
        return withContext(Dispatchers.IO) {
            initTypeData(manufacturerKey)
            return@withContext networkDataSource.types
        }
    }

    override suspend fun getYears(manufacturer: String?, type: String?): LiveData<Map<String, String>> {
        return withContext(Dispatchers.IO) {
            initYears(manufacturer, type)
            return@withContext networkDataSource.years
        }
    }

    private suspend fun initManufacturerData() {
        fetchManufacturers()
    }


    private suspend fun initTypeData(manufacturerKey: String?) {
        fetchTypes(manufacturerKey)
    }

    private suspend fun initYears(manufacturer: String?, type: String?) {
        fetchYears(manufacturer, type)
    }

    private suspend fun fetchManufacturers() {
        networkDataSource.getManufacturers()
    }

    private suspend fun fetchTypes(manufacturerKey: String?) {
        networkDataSource.getTypes(manufacturerKey)
    }

    private suspend fun fetchYears(manufacturer: String?, type: String?) {
        networkDataSource.getYears(manufacturer, type)
    }

}