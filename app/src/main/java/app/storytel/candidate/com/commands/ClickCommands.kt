package app.storytel.candidate.com.commands

import android.view.View

class ClickCommands(private val commands: List<Command>) : View.OnClickListener {
    override fun onClick(v: View?) {
        for (command in commands) {
            command.execute()
        }
    }
}