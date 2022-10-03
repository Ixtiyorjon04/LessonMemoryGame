package uz.gita.lessonmemorygame.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.gita.lessonmemorygame.shared.MySharedPreference

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MySharedPreference.init(this)

    }
}