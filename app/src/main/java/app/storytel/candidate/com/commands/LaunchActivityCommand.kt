package app.storytel.candidate.com.commands

import android.content.Context
import android.os.Bundle
import app.storytel.candidate.com.base.BaseActivity

class LaunchActivityCommand(
    private val context: Context,
    private val activity: Class<*>,
    private val clearTask: Boolean = false,
    private val bundle: Bundle? = null
) : Command {
    override fun execute() {
        (context as BaseActivity).launchActivity(activity, clearTask, bundle)
    }
}