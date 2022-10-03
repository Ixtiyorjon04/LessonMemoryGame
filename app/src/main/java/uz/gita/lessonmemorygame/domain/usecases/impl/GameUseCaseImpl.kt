package uz.gita.lessonmemorygame.domain.usecases.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.lessonmemorygame.data.models.GameModel
import uz.gita.lessonmemorygame.data.models.Level
import uz.gita.lessonmemorygame.domain.repositories.AppRepository
import uz.gita.lessonmemorygame.domain.usecases.GameUseCase
import javax.inject.Inject

class GameUseCaseImpl @Inject constructor(private val appRepository: AppRepository) : GameUseCase {

    override fun getDataByLevel(level: Level): Flow<ArrayList<GameModel>> = flow {

        val result = ArrayList<GameModel>()
        val list = appRepository.loadDataByLevel(level)

        list.collect {
            result.addAll(it)
            result.addAll(it)
        }
        result.shuffle()
        emit(result)
    }
        .flowOn(Dispatchers.Default)
}