package app.storytel.haris.com.commands

import android.content.Context
import app.storytel.haris.com.details.DetailsActivity
import app.storytel.haris.com.scrolling.ScrollingActivity

class RetryCommand(private val context: Context) : Command {

    override fun execute() {
        if (context is ScrollingActivity) context.loadData() else (context as DetailsActivity).loadData()
    }
}