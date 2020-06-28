package ru.samarin.kotlinapp.ui.splash

import ru.samarin.kotlinapp.ui.base.BaseViewState

class SplashViewState (authenticated: Boolean? = null, error: Throwable? = null) :
        BaseViewState<Boolean?>(authenticated,error)