package app.storytel.haris.com.commands

import android.content.Context
import androidx.fragment.app.Fragment
import app.storytel.haris.com.base.BaseActivity

class AddFragmentCommand(
    private val context: Context,
    private val fragment: Fragment
) : Command {

    override fun execute() {
        (context as BaseActivity).addFragment(fragment)
    }
}