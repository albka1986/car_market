package ua.com.ponomarenko.carmarket.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.com.ponomarenko.carmarket.data.network.NetworkDataSource
import ua.com.ponomarenko.carmarket.data.repository.CarMarketRepository

/**
 * Created by Ponomarenko Oleh on 4/25/2019.
 */
class MainViewModelFactory(private val carMarketRepository: CarMarketRepository, private val networkDataSource: NetworkDataSource) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(carMarketRepository) as T
    }
}