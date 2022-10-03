package uz.gita.lessonmemorygame.domain.usecases

import kotlinx.coroutines.flow.Flow
import uz.gita.lessonmemorygame.data.models.GameModel
import uz.gita.lessonmemorygame.data.models.Level

interface GameUseCase {

    fun getDataByLevel(level: Level): Flow<ArrayList<GameModel>>
}