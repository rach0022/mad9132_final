package ravi.anatolie.finalproject
// created by Ravi and Anatolie on December 11th, 2020
/*
 * Created by Tony Davidson on Nov 12, 2020
 *
 *
 * Note: All changes you make in a SharedPreferences editor are batched,
 * they are not copied back to the original SharedPreferences until you call commit() or apply()
 * commit is immediate and can cause blocking
 * apply runs in the background therefore it is non-blocking
 *
 * All modifications to the preferences must go through an Editor object so that the preference
 * values remain in a consistent state and control when they are committed to storage.
 * Objects that are returned from the various get methods must be treated as immutable by the App
 *
*/

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(private val context: Context = TheApp.context) {
    // region Properties
    private val preferencesName = context.getString(R.string.app_name) // use the app name
    private val sharedPref: SharedPreferences = context.getSharedPreferences(preferencesName,
            Context.MODE_PRIVATE)
    // endregion

    // region Methods
    fun contains(KEY_NAME: String): Boolean{
        return sharedPref.contains(KEY_NAME)
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun getAll(): Map<String, *>{
        return sharedPref.all
    }



    // region Set methods
    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    // **** you need to add set methods for Int, Long, Float, Boolean and Set<String>

    // endregion

    // region Get methods
    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    // **** you need to add get methods for Int?, Long?, Float?, Boolean? and Set<String>?

    fun setValueString(KEY_NAME: String, text: String){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    fun setValueInt(KEY_NAME: String, value: Int){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    fun setValueFloat(KEY_NAME: String, value: Float){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putFloat(KEY_NAME, value)
        editor.apply()
    }

    fun setValueBoolean(KEY_NAME: String, value: Boolean){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, value)
        editor.apply()
    }

    fun getValueInt(KEY_NAME: String): Int? {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueFloat(KEY_NAME: String): Float? {
        return sharedPref.getFloat(KEY_NAME, 0.0f)
    }

    fun getValueLong(KEY_NAME: String): Long? {
        return sharedPref.getLong(KEY_NAME, 0)
    }

    fun getValueBoolean(KEY_NAME: String): Boolean? {
        return sharedPref.getBoolean(KEY_NAME, false)
    }

    // endregion
    // endregion
}