package gil.sample.mvvm.service.api

import gil.sample.mvvm.service.data.User
import retrofit2.Response
import retrofit2.http.GET

/**
 * A [User]s Api intended for coroutines demo flow.
 */
interface UserApiCr : BaseApi {

    @GET("/public/v2/users")
    suspend fun fetchUsers(): Response<List<User>>

    @GET("/public/v2/users")
    suspend fun fetchUsersFlow(): List<User>

}
