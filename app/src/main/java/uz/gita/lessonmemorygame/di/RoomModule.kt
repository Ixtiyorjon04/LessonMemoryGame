package uz.gita.lessonmemorygame.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
//import uz.gita.ExamplyPizza.data.room.AppDataBase
//import uz.gita.ExamplyPizza.data.room.PizzaDao
//import uz.gita.ExamplyPizza.data.room.UserDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
//    @Provides
//    @Singleton
//    fun provideRoom(@ApplicationContext context: Context): AppDataBase {
//        return Room.databaseBuilder(context, AppDataBase::class.java, "app_db").build()
//    }
//
//    @Provides
//    fun provideUserDao(appDataBase: AppDataBase): UserDao = appDataBase.getUserDao()
//
//    @Provides
//    fun providePizzaDao(appDataBase: AppDataBase): PizzaDao = appDataBase.getPizzaDao()
}