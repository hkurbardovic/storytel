package app.storytel.haris.com.settings

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import app.storytel.haris.com.R
import app.storytel.haris.com.base.BaseActivity
import app.storytel.haris.com.databinding.ActivitySettingsBinding
import app.storytel.haris.com.utilities.PreferencesUtils

class SettingsActivity : BaseActivity() {

    override fun isDI() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySettingsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_settings)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.timeoutSwitch.isChecked = PreferencesUtils.isTimeoutDialog(this)
        binding.timeoutSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferencesUtils.saveIsTimeoutDialog(this, isChecked)
        }
    }
}