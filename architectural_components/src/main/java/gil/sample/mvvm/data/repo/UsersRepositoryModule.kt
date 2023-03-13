package gil.sample.mvvm.data.repo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gil.sample.mvvm.data.service.UserServiceCr

/**
 * User repository module provider.
 */
@Module
@InstallIn(SingletonComponent::class)
object UsersRepositoryModule {

    @Provides
    //@Singleton
    fun provideUsersRepositoryCr(userService: UserServiceCr): UsersRepositoryCr {
        return UsersRepositoryCr(userService)
    }
}