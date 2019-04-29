package ua.com.ponomarenko.carmarket.data.network

import androidx.lifecycle.LiveData

/**
 * Created by Ponomarenko Oleh on 4/25/2019.
 */
interface NetworkDataSource {

    val manufacturers: LiveData<Map<String, String>>

    suspend fun getManufacturers()

    val types: LiveData<Map<String, String>>

    suspend fun getTypes(manufacturerKey: String?)

    val years: LiveData<Map<String, String>>

    suspend fun getYears(manufacturerKey: String?, typeKey: String?)
}