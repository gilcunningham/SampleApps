package gil.sample.mvvm.service.api

import gil.sample.mvvm.service.data.User
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface UserApiRx: BaseApi {

    @GET("/public/v2/users")
    fun fetchUsers() : Observable<List<User>>
}