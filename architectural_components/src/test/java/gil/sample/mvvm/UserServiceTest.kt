package gil.sample.mvvm

import gil.sample.mvvm.Blocktility.Companion.block
import gil.sample.mvvm.data.model.User
import gil.sample.mvvm.data.service.UserServiceCr
import gil.sample.mvvm.data.service.UserServiceRx
import gil.sample.mvvm.data.service.api.UserApiCr
import gil.sample.mvvm.data.service.helper.ApiHelper
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserServiceTest {

    // rx
    private val scheduler by lazy { Schedulers.trampoline() }

    var users: List<User>? = null
    var throwable: Throwable? = null
    var disposable: Disposable? = null
    var completed = false

    @Before
    fun setUp() {
        // rx
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { scheduler }
        RxJavaPlugins.setIoSchedulerHandler { scheduler }
        RxJavaPlugins.setComputationSchedulerHandler { scheduler }
    }

    @After
    fun tearDown() {
        // rx
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun testFetchUsersCr() = runBlocking {
        val service = setupUserServiceCr()
        val users = service.fetchUsers()
        assertNotNull(users)
        assertTrue(users.isNotEmpty())
    }

    @Test
    fun testFetchUsersRx() {
        val service = UserServiceRx()
        val users = service.fetchUsers()
        assertNotNull(users)
    }

    @Test
    fun testFetchUsersRxConsumer() {
        val service = setupUserServiceRx()

        service.fetchUsersConsumer(
            { users ->
                this.users = users
            },
            { throwable ->
                this.throwable = throwable
            }
        )

        block().until { users != null || throwable != null }

        assertNotNull(users)
        users?.isNotEmpty()?.let { assertTrue(it) }
        assertNull(throwable)
    }

    @Test
    fun testFetchUsersRxLambda() {
        val service = setupUserServiceRx()
        service.fetchUsersLambda { users ->
            this.users = users
        }

        block().until { users != null }

        assertNotNull(users)
        users?.isNotEmpty()?.let { assertTrue(it) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchUsersRxObserver() = runTest {
        val service = setupUserServiceRx()

        service.fetchUsersObserver(
            object : Observer<List<User>> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe: $d")
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    throwable = e
                    println("onError: $e")
                }

                override fun onComplete() {
                    completed = true
                    println("*** onComplete")
                }

                override fun onNext(usersList: List<User>) {
                    users = usersList
                }
            }
        )

        block().until { completed }

        assertTrue(completed)
        assertNotNull(disposable)
        assertNull(throwable)
        assertNotNull(users)
        users?.isNotEmpty()?.let { assertTrue(it) }
    }

    private fun setupUserServiceCr(): UserServiceCr {
        val serviceApi = ApiHelper.instance(UserApiCr::class.java)
        return UserServiceCr(serviceApi)
    }

    private fun setupUserServiceRx(): UserServiceRx {
        users = null
        throwable = null
        disposable = null
        completed = false

        return UserServiceRx()
    }
}