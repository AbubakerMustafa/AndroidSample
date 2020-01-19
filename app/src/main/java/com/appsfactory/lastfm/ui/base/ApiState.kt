package com.appsfactory.lastfm.ui.base

enum class ApiState {
    SHOW_LOADER,
    HIDE_LOADER,
    NETWORK_ERROR,
    ERROR,
    GENERAL_ERROR,
    SESSION_EXPIRED,
    NO_DATA,
    SUCCESS
}