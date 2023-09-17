package ir.pooriak.core.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ir.pooriak.core.R
import ir.pooriak.core.view.activity.BaseActivity

/**
 * Created by POORIAK on 13,September,2023
 */

abstract class BaseFragment : Fragment() {

    protected val disposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        visibilityToolbarAction(true)
        needHideToolbarEndIcon(true)
        setupView()
        setupUiListener()
        setupObservers()
    }

    protected abstract fun setupView()
    protected abstract fun setupUiListener()
    protected abstract fun setupObservers()


    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    protected fun hideKeyboard() {
        if (activity != null && requireActivity().window != null) {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
        }
    }

    protected fun hideSoftKeyboard() {
        if (requireActivity().window != null) {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
        }
    }

    protected fun showKeyBoard() {
        if (activity != null) {
            activity?.window
                ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    protected fun visibilityToolbarAction(show: Boolean) {
        val activity = requireActivity()
        if (activity is BaseActivity)
            activity.visibilityToolbarAction(show)
    }

    private fun needHideToolbarEndIcon(show: Boolean) {
        (activity as? BaseActivity)?.needHideToolbarEndIcon(show)
    }

    protected fun toolbarActionIcon(@DrawableRes icon: Int, onClick: (View) -> Unit) {
        val activity = requireActivity()
        if (activity is BaseActivity)
            activity.toolbarActionIcon(icon, onClick)
    }

    protected fun toolbarActionIcon(@DrawableRes icon: Int) {
        val activity = requireActivity()
        if (activity is BaseActivity)
            activity.toolbarActionIcon(icon)
    }

    protected fun toolbarEndIcon(@DrawableRes icon: Int, onClick: ((View) -> Unit)? = null) {
        val activity = requireActivity()
        if (activity is BaseActivity)
            activity.toolbarEndIcon(icon, onClick)
    }

    protected fun showToolbar(show: Boolean) {
        val activity = requireActivity()
        if (activity is BaseActivity)
            activity.needHideToolbar(show.not())
    }

    fun toolbarTitle(
        @StringRes title: Int,
        @StyleRes textAppearance: Int = R.style.ebcom_Text_EnBold16sp
    ) {
        val activity = requireActivity()
        if (activity is BaseActivity)
            activity.toolbarTitle(title, textAppearance)
    }

    fun toolbarTitle(
        title: String,
        @StyleRes textAppearance: Int = R.style.ebcom_Text_EnBold16sp
    ) {
        val activity = requireActivity()
        if (activity is BaseActivity)
            activity.toolbarTitle(title, textAppearance)
    }

}