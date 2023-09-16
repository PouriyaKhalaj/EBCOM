package ir.pooriak.ebcom.di

import androidx.paging.ExperimentalPagingApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.pooriak.core.di.coreModuleList
import ir.pooriak.core.usecase.SchedulerProvider
import ir.pooriak.restaurant.di.restaurantModuleList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by POORIAK on 14,September,2023
 */
val schedulerProviderModule = module {
    single<SchedulerProvider> {
        object : SchedulerProvider {
            override val mainThread: Scheduler
                get() = AndroidSchedulers.mainThread()
            override val io: Scheduler
                get() = Schedulers.io()
            override val newThread: Scheduler
                get() = Schedulers.newThread()
            override val computation: Scheduler
                get() = Schedulers.computation()
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
val applicationModule = mutableListOf<Module>().apply {
    this.add(schedulerProviderModule)
    this.addAll(coreModuleList)
    this.addAll(restaurantModuleList)
}