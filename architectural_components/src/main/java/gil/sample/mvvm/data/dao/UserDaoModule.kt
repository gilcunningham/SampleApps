package gil.sample.mvvm.data.dao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gil.sample.mvvm.data.service.db.SampleMvvmDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserDaoModule {

    @Provides
    @Singleton
    fun provideUserDao(db: SampleMvvmDatabase): UserDao = db.userDao()

}