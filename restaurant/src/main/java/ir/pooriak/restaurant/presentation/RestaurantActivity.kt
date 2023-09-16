package ir.pooriak.restaurant.presentation

import android.os.Bundle
import ir.pooriak.core.view.activity.BaseActivity
import ir.pooriak.restaurant.R

class RestaurantActivity : BaseActivity() {

    override fun showToolbar(): Boolean = true

    override fun doOnCreate(savedInstanceState: Bundle?) = Unit

    override fun navigationGraph(): Int = R.navigation.restaurant_nav_graph

    override fun doOnResume() = Unit

    override fun doOnBackPressed(): Boolean = true
}