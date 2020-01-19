package com.appsfactory.lastfm.ui.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.appsfactory.lastfm.R
import com.appsfactory.lastfm.databinding.ActivityBaseBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity : DaggerAppCompatActivity() {

    var searchView: SearchView? = null
    private lateinit var activityBaseBinding: ActivityBaseBinding
    private var toolbar: Toolbar? = null
    private var toolbarTitle: TextView? = null

    @Inject
    open lateinit var navigator: Navigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        setToolbar(R.layout.toolbar)
    }

    open fun replaceFragment(
        fragment: BaseFragment,
        addToBackStack: Boolean,
        frameId: Int,
        tag: String
    ) {
        if (supportFragmentManager != null) {
            var fragmentExist = supportFragmentManager.findFragmentByTag(tag)
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (fragmentExist == null)
                fragmentExist = fragment
            fragmentTransaction.replace(frameId, fragmentExist, tag)
            if (addToBackStack)
                fragmentTransaction.addToBackStack(fragmentExist.javaClass.simpleName)
            if (!isFinishing) {
                fragmentTransaction.commit()
            }
        }
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub
* so that, we can make other activity implementations looks like normal activity subclasses.
*/
    override fun setContentView(layoutResID: Int) {
        if (this::activityBaseBinding.isInitialized) {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            val stubView = inflater.inflate(layoutResID, activityBaseBinding.viewStub, false)
            activityBaseBinding.viewStub.removeAllViews()
            activityBaseBinding.viewStub.addView(stubView, lp)
        } else {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View) {
        if (this::activityBaseBinding.isInitialized) {
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            activityBaseBinding.viewStub.removeAllViews()
            activityBaseBinding.viewStub.addView(view, lp)
        }
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        if (this::activityBaseBinding.isInitialized) {
            activityBaseBinding.viewStub.removeAllViews()
            activityBaseBinding.viewStub.addView(view, params)
        }
    }

    open fun setToolbar(layoutId: Int) {
        setToolbar(layoutId, true)
    }

    open fun setToolbar(layoutId: Int, showBackButton: Boolean) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val stubView = inflater.inflate(layoutId, activityBaseBinding.flToolbar, false)
        setToolbar(stubView, lp, showBackButton)
    }

    open fun setToolbar(stubView: View, lp: ViewGroup.LayoutParams, showBackButton: Boolean) {
        activityBaseBinding.flToolbar.removeAllViews()
        activityBaseBinding.flToolbar.addView(stubView, lp)
        activityBaseBinding.flToolbar.visibility = View.VISIBLE
        toolbar = stubView.findViewById(R.id.toolbar)
        toolbarTitle = stubView.findViewById(R.id.toolbar_title)
        searchView = stubView.findViewById(R.id.search_view)
        if (toolbar == null || toolbarTitle == null)
            throw Exception("Please provide toolbar and toolbarTitle in the xml layout file")

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = ""
        }
        if (showBackButton)
            setBackButtonDrawable(R.drawable.abc_ic_ab_back_material)
        searchView?.let {
            it.setIconifiedByDefault(true)
            it.queryHint = getString(R.string.search_artist)
            it.layoutParams = Toolbar.LayoutParams(Gravity.END)
        }
    }

    open fun setSearchQueryListener(listener: SearchView.OnQueryTextListener) {
        searchView?.setOnQueryTextListener(listener)
    }

    open fun showToolbar(showToolbar: Boolean) {
        activityBaseBinding.flToolbar.visibility = if (showToolbar) View.VISIBLE else View.GONE
    }

    open fun setBackButtonDrawable(backButtonDrawable: Int) {
        showBackButton(true)
        toolbar?.setNavigationIcon(backButtonDrawable)
        toolbar?.setNavigationOnClickListener {
            handleBackPressed()
        }
    }

    private fun handleBackPressed() {
        if (activityBaseBinding.flProgress.visibility == View.VISIBLE) {
            showPageLoader(false)
        } else {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (activityBaseBinding.flProgress.visibility == View.VISIBLE) {
            showPageLoader(false)
        } else
            super.onBackPressed()
    }

    open fun setToolbarTitle(title: String) {
        toolbarTitle?.let {
            it.text = title
        }
    }

    open fun showBackButton(showBackButton: Boolean) {
        if (!showBackButton) {
            toolbar?.navigationIcon = null
            supportActionBar?.setIcon(null)
        }
        supportActionBar?.setHomeButtonEnabled(showBackButton)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
        actionBar?.setHomeButtonEnabled(showBackButton)
        actionBar?.setDisplayHomeAsUpEnabled(showBackButton)
        actionBar?.setDisplayHomeAsUpEnabled(showBackButton)
    }

    open fun showPageLoader(show: Boolean) {
        showPageLoader(show, true)
    }

    open fun showPageLoader(show: Boolean, hideBackground: Boolean) {
        if (show) {
            activityBaseBinding.flProgress.visibility = View.VISIBLE
            //   showToolbar(false)
            if (hideBackground) {
                activityBaseBinding.viewStub.visibility = View.GONE
            }
        } else {
            activityBaseBinding.flProgress.visibility = View.GONE
            activityBaseBinding.viewStub.visibility = View.VISIBLE
        }
    }
}