package app.storytel.candidate.com.dialogs

import androidx.fragment.app.FragmentManager
import javax.inject.Inject

class DialogManager @Inject constructor() {

    private var timeoutDialog: TimeoutDialog? = null

    fun showTimeoutDialog(fragmentManager: FragmentManager) {
        val retryDialog = timeoutDialog ?: TimeoutDialog.newInstance()
        if (retryDialog.isAdded) return

        retryDialog.show(fragmentManager, TimeoutDialog.TAG)

        this.timeoutDialog = retryDialog
    }
}