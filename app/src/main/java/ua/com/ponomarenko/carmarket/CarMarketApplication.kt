package ua.com.ponomarenko.carmarket

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ua.com.ponomarenko.carmarket.data.network.*
import ua.com.ponomarenko.carmarket.data.repository.CarMarketRepository
import ua.com.ponomarenko.carmarket.data.repository.CarMarketRepositoryImpl
import ua.com.ponomarenko.carmarket.ui.main.MainViewModelFactory

/**
 * Created by Ponomarenko Oleh on 4/25/2019.
 */
class CarMarketApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@CarMarketApplication))

        bind() from singleton { RetrofitService(instance()) }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<NetworkDataSource>() with singleton { NetworkDataSourceImpl(instance()) }
        bind<CarMarketRepository>() with singleton { CarMarketRepositoryImpl(instance()) }
        bind() from provider { MainViewModelFactory(instance(), instance()) }

    }
}