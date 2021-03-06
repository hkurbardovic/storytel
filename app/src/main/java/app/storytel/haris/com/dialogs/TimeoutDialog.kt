package app.storytel.haris.com.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import app.storytel.haris.com.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TimeoutDialog : DialogFragment() {

    interface ClickListener {
        fun onRetryClicked()
        fun onDismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val builder = MaterialAlertDialogBuilder(
            context,
            R.style.ThemeOverlay_MaterialComponents_Dialog_Alert
        )

        builder.setTitle(context.getString(R.string.dialog_timeout_title))
        builder.setMessage(context.getString(R.string.dialog_timeout_message))

        builder.setPositiveButton(context.getString(R.string.dialog_timeout_positive_button)) { _, _ ->
            (context as ClickListener).onRetryClicked()
        }

        builder.setNegativeButton(context.getString(R.string.dialog_timeout_negative_button), null)

        return builder.create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val context = context ?: return
        (context as ClickListener).onDismiss()
    }

    companion object {
        fun newInstance(): TimeoutDialog = TimeoutDialog()

        const val TAG = "RetryDialog"
    }
}