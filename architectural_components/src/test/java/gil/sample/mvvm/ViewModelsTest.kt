package gil.sample.mvvm

import gil.sample.mvvm.viewmodel.UsersRxViewModel
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

class ViewModelsTest {

    val viewModel = UsersRxViewModel()

    // rx
    private val scheduler by lazy { Schedulers.trampoline() }

    // coroutines
    //@OptIn(ExperimentalCoroutinesApi::class)
    //private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        // rx
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { scheduler }
        RxJavaPlugins.setIoSchedulerHandler { scheduler }
        RxJavaPlugins.setComputationSchedulerHandler { scheduler }

        // coroutines
        //Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        // rx
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()

        // coroutines
        //Dispatchers.resetMain()
        //d/ispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testDoingWork() {
        viewModel.onUpdateUsersRx()

        //viewModel.doingWork.value?.let { assertTrue(it) }

        /**
        viewModel.onUpdateUsersRx()
        viewModel.doingWork.value?.let { assertTrue(it) }

        viewModel.onUpdateUsersRx()
        viewModel.doingWork.value?.let { assertTrue(it) }

        viewModel.onUpdateUsersRx()
        viewModel.doingWork.value?.let { assertTrue(it) }

        viewModel.apply {

        }
         **/
    }

    //@Test
    //fun test
}