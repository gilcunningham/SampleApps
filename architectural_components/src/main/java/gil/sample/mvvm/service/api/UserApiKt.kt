package gil.sample.mvvm.service.api;

import gil.sample.mvvm.service.data.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface UserApiKt : BaseApi {

    @GET("/public/v2/users")
    suspend fun fetchUsers(): List<User>

    //TODO get adapter for Flow
    @GET("/public/v2/users")
    suspend fun fetchUsersFlow(): Flow<List<User>>
}
