package ir.pooriak.core.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat.setTextAppearance
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ir.pooriak.core.R
import ir.pooriak.core.databinding.ActivityBaseBinding
import java.util.Locale

/**
 * Created by POORIAK on 13,September,2023
 */
abstract class BaseActivity : AppCompatActivity() {

    lateinit var baseBinding: ActivityBaseBinding
    private val localeDelegate = LocaleUtilActivityDelegateImpl()

    protected var navController: NavController? = null

    protected var showToolbarActionIcon: Boolean = true

    abstract fun showToolbar(): Boolean
    abstract fun doOnCreate(savedInstanceState: Bundle?)
    abstract fun navigationGraph(): Int?
    abstract fun doOnResume()
    abstract fun doOnBackPressed(): Boolean


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(localeDelegate.attachBaseContext(newBase))
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        localeDelegate.onCreate(this)
        updateLocale(Locale.ENGLISH)

        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        initializeNavigationGraph()

        initializeView()
        doOnCreate(savedInstanceState)
    }

    override fun onResume() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        super.onResume()
        localeDelegate.onResumed(this)
        doOnResume()
    }

    override fun onPause() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
        )
        super.onPause()
        localeDelegate.onPaused()
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        doOnBackPressed()
        hideKeyboard(this)
    }

    fun goToActivity(intent: Intent) {
        goToActivity(intent, 0, 0)
    }

    private fun goToActivity(intent: Intent, enterAnim: Int, exitAnim: Int) {
        startActivity(intent)
        overridePendingTransition(enterAnim, exitAnim)
    }

    fun toolbarActionIcon(@DrawableRes icon: Int, onClick: (View) -> Unit = { onBackPressed() }) {
        baseBinding.appBarLayout.visibility = View.VISIBLE
        baseBinding.toolbarAction.setImageDrawable(ContextCompat.getDrawable(this, icon))
        baseBinding.toolbarAction.setOnClickListener(onClick)
    }

    fun toolbarEndIcon(@DrawableRes icon: Int, onClick: ((View) -> Unit)? = null) {
        baseBinding.appBarLayout.visibility = View.VISIBLE
        needHideToolbarEndIcon(false)
        baseBinding.toolbarEnd.setImageDrawable(ContextCompat.getDrawable(this, icon))
        baseBinding.toolbarEnd.setOnClickListener(onClick)
    }

    fun needHideToolbarEndIcon(show: Boolean) {
        baseBinding.toolbarEnd.visibility = if (show) View.GONE else View.VISIBLE
    }

    fun needHideToolbar(show: Boolean) {
        baseBinding.appBarLayout.visibility = if (show) View.GONE else View.VISIBLE
    }

    fun toolbarTitle(@StringRes title: Int, @StyleRes textAppearance: Int) {
        toolbarTitle(getString(title), textAppearance)
    }

    fun toolbarTitle(title: String, @StyleRes textAppearance: Int) {
        baseBinding.appBarLayout.visibility = View.VISIBLE
        baseBinding.toolbarTitle.text = title
        setTextAppearance(baseBinding.toolbarTitle, textAppearance)
    }

    private fun initializeNavigationGraph() {
        navigationGraph()?.let {
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).let { navHostFragment ->
                navController = navHostFragment.navController
                navController?.setGraph(it)
            }
        }
    }

    private fun updateLocale(locale: Locale) {
        localeDelegate.setLocale(this, locale)
    }

    private fun initializeView() {
        initializeToolbar()
    }

    private fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                activity.window.decorView.windowToken, 0
            )
        }
    }

    private fun initializeToolbar() {
        baseBinding.appBarLayout.visibility = View.VISIBLE
        visibilityToolbarAction()
        setSupportActionBar(baseBinding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
            setDisplayShowTitleEnabled(false)
        }

        baseBinding.toolbar.navigationIcon = null
        baseBinding.appBarLayout.visibility = if (showToolbar()) View.VISIBLE else View.GONE

        baseBinding.toolbarAction.setOnClickListener {
            hideKeyboard(this)
            onBackPressed()
        }
    }

    fun visibilityToolbarAction(show: Boolean = true) {
        showToolbarActionIcon = show
        baseBinding.toolbarAction.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun createIntent(context: Context, action: String, extras: Bundle? = null) =
        Intent(action).setPackage(context.packageName).also { intent ->
            extras?.let { intent.putExtras(it) }
        }
}

