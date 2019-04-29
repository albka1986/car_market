package ua.com.ponomarenko.carmarket.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import ua.com.ponomarenko.carmarket.data.repository.CarMarketRepository
import ua.com.ponomarenko.carmarket.internal.lazyDeferred

/**
 * Created by Ponomarenko Oleh on 4/25/2019.
 */
class MainViewModel(private val carMarketRepository: CarMarketRepository) : ViewModel() {

    fun getTypesAsync(manufacturerKey: String?) = GlobalScope.async(start = CoroutineStart.LAZY) {
        carMarketRepository.getTypes(manufacturerKey)
    }

    fun getYearsAsync(manufacturer: String?, type: String?) = GlobalScope.async(start = CoroutineStart.LAZY) {
        carMarketRepository.getYears(manufacturer, type)
    }

    val manufacturers by lazyDeferred {
        carMarketRepository.getManufacturers()
    }
}