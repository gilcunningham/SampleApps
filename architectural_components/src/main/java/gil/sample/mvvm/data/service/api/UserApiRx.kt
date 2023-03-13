package gil.sample.mvvm.data.service.api

import gil.sample.mvvm.data.model.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

/**
 * A [User]s Api intended for Rx demo flow.
 */
interface UserApiRx : BaseApi {
    @GET("/public/v2/users")
    fun fetchUsers(): Observable<List<User>>
}