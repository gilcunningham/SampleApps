package gil.sample.mvvm.model

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gil.sample.mvvm.model.UsersRepositoryCr
import gil.sample.mvvm.service.UserServiceCr
import javax.inject.Singleton

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