
import android.content.Context
import com.dkbrothers.apps.demosforrealproject.R


fun Context.savePreference(llave: String, valor: String) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(llave, valor)
    editor.apply()
}

fun Context.savePreference(llave: String, valor: Boolean) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences),
            Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(llave, valor)
    editor.apply()
}
fun Context.savePreference(llave: String, valor: Long) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences),
        Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putLong(llave, valor)
    editor.apply()
}

fun Context.savePreference(llave: String, valor: Int) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences),
            Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt(llave, valor)
    editor.apply()
}

fun Context.getStringPreference(llave: String, defValue: String): String {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getString(llave, defValue)!!
}

fun Context.getBooleanPreference(llave: String, defValue: Boolean): Boolean {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(llave, defValue)
}

fun Context.getPreference(llave: String): String {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getString(llave, "")!!
}

fun Context.getLongPreference(llave: String, defValue: Long): Long {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    return sharedPreferences.getLong(llave, defValue)
}

fun Context.deletePrefence(llave: String) {
    val sharedPreferences = this.getSharedPreferences(this.getString(R.string.key_preferences), Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove(llave)
    editor.apply()
}