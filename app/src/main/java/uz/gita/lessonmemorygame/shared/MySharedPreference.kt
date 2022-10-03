package uz.gita.lessonmemorygame.shared

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.ProgressBar

class MySharedPreference {

    companion object {
        private lateinit var sharedPreference: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private var shpIns: MySharedPreference? = null

        fun init(context: Context) {
            shpIns = MySharedPreference()
            sharedPreference = context.getSharedPreferences("shp", Activity.MODE_PRIVATE)
            editor = sharedPreference.edit()
        }

        fun getInstance() = shpIns!!
    }

    fun setWakeUpTime(text: Int) {
        editor.putInt("wakeUp", text).apply()
    }

    fun setSleepstatus(text: Long) {
        editor.putLong("status", text).apply()
    }

    fun getWakeUpTime(prgBarHorizontal: ProgressBar): Int? {
        return sharedPreference.getInt("wakeUp",0)
    }

    fun setSleepTime(text: String) {
        editor.putString("sleep", text).apply()
    }

    fun getSleepTime(): String? {
        return sharedPreference.getString("sleep", "")
    }

    fun getSleepstatus(): Long {
        return sharedPreference.getLong("status", 0)
    }
}