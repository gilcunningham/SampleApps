package gil.sample.mvvm.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gil.sample.mvvm.data.model.User
import javax.inject.Singleton

@Dao
@Singleton
interface UserDao {

    @Query("SELECT * FROM users ORDER BY name")
    fun getUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)
}