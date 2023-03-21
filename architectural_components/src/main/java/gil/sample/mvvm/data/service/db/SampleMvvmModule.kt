package gil.sample.mvvm.data.service.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserServiceModule {

    @Provides
    @Singleton
    fun provideSampleMvvmDatabase(@ApplicationContext appContext: Context): SampleMvvmDatabase {
        return Room.databaseBuilder(
            appContext,
            SampleMvvmDatabase::class.java,
            SampleMvvmDatabase.DATABASE_NAME
        ).build()
    }
}