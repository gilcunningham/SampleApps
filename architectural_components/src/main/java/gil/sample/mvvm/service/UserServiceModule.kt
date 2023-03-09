package gil.sample.mvvm.service

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gil.sample.mvvm.service.api.UserApiCr
import gil.sample.mvvm.service.helper.ApiHelper
import javax.inject.Singleton

/**
 * User Services available for Hilt to inject.
 */

@Module
@InstallIn(SingletonComponent::class)
object UserServiceModule {

    @Provides
    //@Singleton
    fun provideUserServiceCr(): UserServiceCr {
        return UserServiceCr(
            ApiHelper.instance(UserApiCr::class.java)
        )
    }

    //@Provides
    //@Singleton
    //fun provideUserServiceRx(): UserServiceRx {
    //    return UserServiceRx(
    //        ApiHelper.instance(UserApiRx::class.java)
    //    )
    //}
}