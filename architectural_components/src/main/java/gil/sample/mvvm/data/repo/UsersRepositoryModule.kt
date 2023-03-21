package gil.sample.mvvm.data.repo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gil.sample.mvvm.data.dao.UserDao
import gil.sample.mvvm.data.service.UserServiceCr
import javax.inject.Singleton

/**
 * User repository module provider.
 */
@Module
@InstallIn(SingletonComponent::class)
object UsersRepositoryModule {

    @Provides
    @Singleton
    fun provideUsersRepositoryCr(
        userService: UserServiceCr,
        userDao: UserDao
    ): UsersRepositoryCr {
        return UsersRepositoryCr(userService, userDao)
    }
}