package ru.samarin.kotlinapp.ui.splash

import org.koin.android.viewmodel.ext.android.viewModel
import ru.samarin.kotlinapp.ui.base.BaseActivity
import ru.samarin.kotlinapp.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val model: SplashViewModel by viewModel()

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        model.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }
}