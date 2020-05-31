package app.storytel.candidate.com.settings

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import app.storytel.candidate.com.R
import app.storytel.candidate.com.base.BaseActivity
import app.storytel.candidate.com.databinding.ActivitySettingsBinding
import app.storytel.candidate.com.utilities.PreferencesUtils

class SettingsActivity : BaseActivity() {

    override fun isDI() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySettingsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_settings)

        binding.timeoutSwitch.isChecked = PreferencesUtils.isTimeoutDialog(this)
        binding.timeoutSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesUtils.saveIsTimeoutDialog(this, isChecked)
        }
    }
}