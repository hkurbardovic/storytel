package app.storytel.haris.com.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.storytel.haris.com.commands.BackPressedCommand
import app.storytel.haris.com.commands.ClickCommands
import app.storytel.haris.com.commands.RetryCommand
import app.storytel.haris.com.databinding.FragmentTimeoutBinding
import app.storytel.haris.com.details.DetailsActivity
import app.storytel.haris.com.di.DaggerFragment
import app.storytel.haris.com.scrolling.ScrollingActivity

class TimeoutFragment : DaggerFragment() {

    override fun isDi() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTimeoutBinding =
            FragmentTimeoutBinding.inflate(inflater, container, false)

        val activity = activity ?: return binding.root

        binding.clickListener =
            ClickCommands(listOf(BackPressedCommand(activity), RetryCommand(activity)))

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val context = context ?: return
        if (context is ScrollingActivity) context.hideFab() else (context as DetailsActivity).hideFab()
    }

    override fun onStop() {
        super.onStop()
        val context = context ?: return
        if (context is ScrollingActivity) context.showFab() else (context as DetailsActivity).showFab()
    }

    companion object {
        fun newInstance() = TimeoutFragment()
    }
}