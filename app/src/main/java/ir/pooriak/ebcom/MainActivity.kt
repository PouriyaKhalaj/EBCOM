package ir.pooriak.ebcom

import android.os.Bundle
import ir.pooriak.core.view.activity.BaseActivity
import ir.pooriak.restaurant.presentation.router.restaurantIntent

class MainActivity : BaseActivity() {
    override fun showToolbar(): Boolean = true

    override fun doOnCreate(savedInstanceState: Bundle?) = Unit

    override fun navigationGraph(): Int? = null

    override fun doOnResume() = Unit

    override fun doOnBackPressed(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goToActivity(restaurantIntent(this))
    }
}