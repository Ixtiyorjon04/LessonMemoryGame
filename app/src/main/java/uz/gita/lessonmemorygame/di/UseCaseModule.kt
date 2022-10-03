package uz.gita.lessonmemorygame.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.lessonmemorygame.domain.usecases.GameUseCase
import uz.gita.lessonmemorygame.domain.usecases.impl.GameUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGameUseCase(impl: GameUseCaseImpl): GameUseCase
}