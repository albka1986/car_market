package ua.com.ponomarenko.carmarket.data.repository

import androidx.lifecycle.LiveData

/**
 * Created by Ponomarenko Oleh on 4/25/2019.
 */
interface CarMarketRepository {
    suspend fun getManufacturers(): LiveData<Map<String, String>>
    suspend fun getTypes(manufacturerKey: String?): LiveData<Map<String, String>>
    suspend fun getYears(manufacturer: String?, type: String?): LiveData<Map<String, String>>
}