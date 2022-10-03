package uz.gita.lessonmemorygame.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.lessonmemorygame.domain.repositories.AppRepository
import uz.gita.lessonmemorygame.domain.repositories.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @[Binds Singleton]
    fun bindAppRepository(impl: AppRepositoryImpl) : AppRepository

}