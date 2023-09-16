package ir.pooriak.restaurant.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.pooriak.core.view.fragment.BaseFragment
import ir.pooriak.restaurant.databinding.FragmentDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : BaseFragment() {
    private var _mBinding: FragmentDetailBinding? = null
    private val binding get() = _mBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        TODO("Not yet implemented")
    }

    override fun setupUiListener() {
        TODO("Not yet implemented")
    }

    override fun setupObservers() {
        TODO("Not yet implemented")
    }
}