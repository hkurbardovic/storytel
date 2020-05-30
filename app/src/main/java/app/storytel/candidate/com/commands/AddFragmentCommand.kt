package app.storytel.candidate.com.commands

import android.content.Context
import androidx.fragment.app.Fragment
import app.storytel.candidate.com.base.BaseActivity

class AddFragmentCommand(
    private val context: Context,
    private val fragment: Fragment
) : Command {

    override fun execute() {
        (context as BaseActivity).addFragment(fragment)
    }
}