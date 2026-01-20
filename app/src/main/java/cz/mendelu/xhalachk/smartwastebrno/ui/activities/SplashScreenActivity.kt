package cz.mendelu.xhalachk.smartwastebrno.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : ComponentActivity() {

    private val viewModel: SplashScreenActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.splashScreenState.collect { value ->
                    if (value.continueToApp){
                        continueHomeScreen()

                    }
                    if (value.runForAFirstTime) {
                        runAppIntro()
                    }
                }
            }
        }

    }

    private fun runAppIntro(){
        startActivity(AppIntroActivity.createIntent(this))
        finish()
    }

    private fun continueHomeScreen(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

