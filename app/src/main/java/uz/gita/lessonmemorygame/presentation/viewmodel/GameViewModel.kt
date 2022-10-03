package uz.gita.lessonmemorygame.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.lessonmemorygame.data.models.GameModel
import uz.gita.lessonmemorygame.data.models.Level

interface GameViewModel {
     val barstatus:Int
    val gameModeLiveData: LiveData<List<GameModel>>

    fun getDataByLevel(level: Level)

    fun btnClicked(state: Boolean, position: Int)
}