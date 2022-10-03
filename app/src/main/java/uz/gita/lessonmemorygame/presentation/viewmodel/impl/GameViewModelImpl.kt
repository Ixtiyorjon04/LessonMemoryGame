package uz.gita.lessonmemorygame.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.lessonmemorygame.data.models.GameModel
import uz.gita.lessonmemorygame.data.models.Level
import uz.gita.lessonmemorygame.domain.usecases.GameUseCase
import uz.gita.lessonmemorygame.presentation.viewmodel.GameViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl @Inject constructor(private val useCase: GameUseCase) : GameViewModel, ViewModel() {
    override val barstatus=0


    override val gameModeLiveData: MutableLiveData<List<GameModel>> = MutableLiveData()


    override fun getDataByLevel(level: Level) {
        useCase.getDataByLevel(level)
            .onEach {
                gameModeLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    override fun btnClicked(state: Boolean, position: Int) {

    }


}