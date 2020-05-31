package app.storytel.candidate.com.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.storytel.candidate.com.base.BaseFragment
import app.storytel.candidate.com.commands.BackPressedCommand
import app.storytel.candidate.com.commands.ClickCommands
import app.storytel.candidate.com.commands.RetryCommand
import app.storytel.candidate.com.databinding.FragmentTimeoutBinding
import app.storytel.candidate.com.details.DetailsActivity
import app.storytel.candidate.com.scrolling.ScrollingActivity

class TimeoutFragment : BaseFragment() {

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