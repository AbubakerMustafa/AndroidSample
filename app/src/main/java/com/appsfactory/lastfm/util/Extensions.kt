package com.appsfactory.lastfm.util

import android.app.Activity
import android.content.Context
import android.util.EventLogTags
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


fun <K, V> lazyMap(initializer: (K) -> V): Map<K, V> {
    val map = mutableMapOf<K, V>()
    return map.withDefault { key ->
        val newValue = initializer(key)
        map[key] = newValue
        return@withDefault newValue
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun withDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("ImageView with drawable same as drawable with id $id")
    }

    override fun matchesSafely(view: View): Boolean {
        val context = view.context
        val expectedBitmap = ContextCompat.getDrawable(context, id)?.toBitmap()
        return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
    }
}
