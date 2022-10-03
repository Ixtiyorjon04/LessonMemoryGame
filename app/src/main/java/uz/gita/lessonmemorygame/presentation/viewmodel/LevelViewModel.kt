package uz.gita.lessonmemorygame.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.lessonmemorygame.data.models.Level

interface LevelViewModel {
    val openGameLiveData: LiveData<Level>

    fun openGame(level: Level)
}