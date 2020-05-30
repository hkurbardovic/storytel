package app.storytel.candidate.com.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.storytel.candidate.com.commands.BackPressedCommand
import app.storytel.candidate.com.commands.ClickCommands
import app.storytel.candidate.com.commands.RetryCommand
import app.storytel.candidate.com.databinding.FragmentTimeoutBinding

class TimeoutFragment : Fragment() {

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

    companion object {
        fun newInstance() = TimeoutFragment()
    }
}