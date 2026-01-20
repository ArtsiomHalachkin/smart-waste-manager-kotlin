package cz.mendelu.xhalachk.smartwastebrno.ui.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import cz.mendelu.xhalachk.smartwastebrno.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppIntroActivity : AppIntro() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, AppIntroActivity::class.java)
        }
    }

    private val viewModel: AppIntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showAppIntro()
    }

    private fun showAppIntro() {
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.intro_title_1),
                description = getString(R.string.intro_text_1),
                titleColor = ContextCompat.getColor(this, R.color.textColor),
                descriptionColor = ContextCompat.getColor(this, R.color.textColor),
                imageDrawable = R.drawable.recycle
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.intro_title_2),
                description = getString(R.string.intro_text_2),
                titleColor = ContextCompat.getColor(this, R.color.textColor),
                descriptionColor = ContextCompat.getColor(this, R.color.textColor),
                imageDrawable = R.drawable.ai
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                title = getString(R.string.intro_title_3),
                description = getString(R.string.intro_text_3),
                titleColor = ContextCompat.getColor(this, R.color.textColor),
                descriptionColor = ContextCompat.getColor(this, R.color.textColor),
                imageDrawable = R.drawable.map
            )
        )

        showSeparator(true)
        setSeparatorColor(ContextCompat.getColor(this, R.color.textColor))
        setColorDoneText(ContextCompat.getColor(this, R.color.textColor))
        setDoneText(getString(R.string.continue_to_app))
        setIndicatorColor(
            ContextCompat.getColor(this, R.color.textColor),
            ContextCompat.getColor(this, android.R.color.darker_gray))
        setNextArrowColor(ContextCompat.getColor(this, R.color.textColor))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        continueToMainActivity()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        continueToMainActivity()
    }

    private fun continueToMainActivity() {
        lifecycleScope.launch {
            viewModel.setFirstRun()
        }.invokeOnCompletion {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
