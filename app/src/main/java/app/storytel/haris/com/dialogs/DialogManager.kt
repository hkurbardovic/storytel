package app.storytel.haris.com.dialogs

import androidx.fragment.app.FragmentManager
import app.storytel.haris.com.di.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class DialogManager @Inject constructor() {

    private var timeoutDialog: TimeoutDialog? = null

    fun showTimeoutDialog(fragmentManager: FragmentManager) {
        val retryDialog = timeoutDialog ?: TimeoutDialog.newInstance()
        if (retryDialog.dialog?.isShowing == true) return

        retryDialog.show(fragmentManager, TimeoutDialog.TAG)

        this.timeoutDialog = retryDialog
    }
}