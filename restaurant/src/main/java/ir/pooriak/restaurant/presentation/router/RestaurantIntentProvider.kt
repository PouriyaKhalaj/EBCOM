package ir.pooriak.restaurant.presentation.router

import ir.pooriak.core.view.activity.BaseActivity

/**
 * Created by POORIAK on 15,September,2023
 */

/**
 * Restaurant Intents
 */
private const val IntentFilter_Restaurant = "ir.pooriak.ebcom.RESTAURANT"

fun restaurantIntent(activity: BaseActivity) =
    activity.createIntent(activity, IntentFilter_Restaurant)