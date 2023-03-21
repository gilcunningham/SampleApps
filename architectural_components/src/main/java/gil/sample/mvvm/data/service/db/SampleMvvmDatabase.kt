package gil.sample.mvvm.data.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import gil.sample.mvvm.data.dao.UserDao
import gil.sample.mvvm.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class SampleMvvmDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "sample-mvvm-db"
    }
}