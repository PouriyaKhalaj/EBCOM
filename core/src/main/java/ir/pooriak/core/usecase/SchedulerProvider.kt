package ir.pooriak.core.usecase

import io.reactivex.rxjava3.core.Scheduler

/**
 * Created by POORIAK on 13,September,2023
 */
interface SchedulerProvider {
    val mainThread: Scheduler
    val io: Scheduler
    val newThread: Scheduler
    val computation: Scheduler
}