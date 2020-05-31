package app.storytel.haris.com.utilities

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtils {

    private const val PREFERENCES_NAME = "storytel_preferences"

    private const val KEY_IS_TIMEOUT_DIALOG = "preference_is_timeout_dialog"

    private fun getPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * Saves the provided key-value pair in shared preferences.
     *
     * @param key       The name of the preference
     * @param value     The new value of the preference
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not implemented yet!")
        }
    }

    /**
     * Returns the value on a given key.
     *
     * @param key           The preference name
     * @param defaultValue  Optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    private inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? =
        when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: 0) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: 0f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: 0) as T?
            else -> throw UnsupportedOperationException("Not implemented yet!")
        }

    fun saveIsTimeoutDialog(context: Context, value: Boolean) {
        getPreferences(context)[KEY_IS_TIMEOUT_DIALOG] = value
    }

    fun isTimeoutDialog(context: Context) =
        getPreferences(context).getBoolean(KEY_IS_TIMEOUT_DIALOG, false)
}