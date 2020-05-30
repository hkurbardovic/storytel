package app.storytel.candidate.com.base

import android.content.Intent
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import app.storytel.candidate.com.R
import app.storytel.candidate.com.utilities.inTransaction
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    private fun isFragmentInBackStack(fragment: Fragment): Boolean {
        if (supportFragmentManager.backStackEntryCount == 0) return false

        val lastFragment =
            supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)

        return lastFragment.name == fragment::class.java.name
    }

    fun launchActivity(activity: Class<*>, clearTask: Boolean = false, bundle: Bundle? = null) {
        val intent: Intent = if (clearTask) {
            Intent(this, activity).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        } else {
            Intent(this, activity)
        }

        if (bundle != null) intent.putExtras(bundle)

        startActivity(intent)
    }

    fun addFragment(fragment: Fragment) {
        if (fragment.isAdded || isFragmentInBackStack(fragment)) return
        supportFragmentManager.inTransaction {
            setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )

            addToBackStack(fragment::class.java.name)
            add(R.id.fragment_container, fragment)
        }
    }

    fun showMessage(message: String?, coordinatorLayout: CoordinatorLayout) {
        if (message == null) return
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show()
    }
}