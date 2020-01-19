package com.appsfactory.lastfm.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.di.Injectable
import com.appsfactory.lastfm.util.Dialogs

abstract class BaseFragment : Fragment(), Injectable {

    private var baseViewModel: BaseViewModel? = null

    abstract fun initUI()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel = initViewModel()
        initUI()
        if (baseViewModel != null) {
            baseViewModel?.stateLiveData?.observe(viewLifecycleOwner, Observer { state ->
                when (state?.apiState) {
                    ApiState.SHOW_LOADER -> {
                        when (state.data) {
                            LoadingType.PAGE_LOADING -> {
                                showPageLoader()
                            }
                            LoadingType.FULL_LOADING -> {
                                showFullLoader()
                            }
                        }
                    }
                    ApiState.HIDE_LOADER -> {
                        when (state.data) {
                            LoadingType.PAGE_LOADING -> {
                                hidePageLoader()
                            }
                            LoadingType.FULL_LOADING -> {
                                hideFullLoader()
                            }
                        }
                    }
                    ApiState.NETWORK_ERROR -> {
                        hideAllLoaders()
                        onError(
                            getString(R.string.error),
                            getString(R.string.network_error),
                            getString(R.string.ok),
                            true,
                            Runnable {
                            })
                    }
                    ApiState.GENERAL_ERROR -> {
                        hideAllLoaders()
                        onGeneralError()
                    }
                    ApiState.ERROR -> {
                        hideAllLoaders()
                        onError(
                            getString(R.string.error),
                            state.message?.let { it } ?: getString(R.string.some_error_occurred),
                            getString(R.string.ok),
                            true,
                            Runnable {

                            })
                    }
                    ApiState.SESSION_EXPIRED -> {
                        hideAllLoaders()
                        // handle session expiry
                    }
                    else -> {
                        hideAllLoaders()
                        onGeneralError()
                    }
                }
            })
        }
    }

    private fun hideAllLoaders(){
        hideFullLoader()
        hidePageLoader()
    }
    private fun hideFullLoader() {
        (activity as BaseActivity).showPageLoader(false)
    }

    private fun hidePageLoader() {

    }


    open fun showPageLoader() {

    }

    open fun showFullLoader() {
        (activity as BaseActivity).showPageLoader(show = true)
    }


    open fun onError(
        title: String,
        message: String,
        positiveText: String,
        cancelable: Boolean,
        runnable: Runnable
    ) {
        activity?.let {
            Dialogs.showGeneralErrorDialog(
                it,
                title,
                message,
                positiveText,
                cancelable,
                runnable
            ).show()
        }
    }

    open fun onGeneralError() {
        onError(
            getString(R.string.error),
            getString(R.string.some_error_occurred),
            getString(R.string.ok),
            true,
            Runnable {
            }
        )
    }

    abstract fun initViewModel(): BaseViewModel?

}