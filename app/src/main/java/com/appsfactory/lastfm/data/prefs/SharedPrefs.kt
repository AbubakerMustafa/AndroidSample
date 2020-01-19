package com.appsfactory.lastfm.data.prefs

import android.content.Context
import android.content.SharedPreferences

object PreferenceKeys {
    const val PREFS_FILENAME = "com.appsfactory.lastfm.prefs"

}

class SharedPrefsDataSource(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PreferenceKeys.PREFS_FILENAME, 0)
}