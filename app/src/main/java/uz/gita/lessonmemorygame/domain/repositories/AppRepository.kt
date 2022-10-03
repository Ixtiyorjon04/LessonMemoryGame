package uz.gita.lessonmemorygame.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.gita.lessonmemorygame.data.models.GameModel
import uz.gita.lessonmemorygame.data.models.Level

interface AppRepository {
    fun loadDataByLevel(level: Level): Flow<List<GameModel>>
}