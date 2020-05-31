package app.storytel.haris.com.commands

import android.app.Activity

class BackPressedCommand(private val activity: Activity) : Command {

    override fun execute() {
        activity.onBackPressed()
    }
}