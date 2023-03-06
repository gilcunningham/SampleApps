package gil.sample.mvvm.service.adapter

import java.io.IOException
import java.lang.RuntimeException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

class ResultCallAdapterFactory: CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }
        val upperBound = getParameterUpperBound(0, returnType)

        return if (upperBound is ParameterizedType && upperBound.rawType == Result::class.java) {
            object : CallAdapter<Any, Call<Result<*>>> {
                override fun responseType(): Type = getParameterUpperBound(0, upperBound)

                override fun adapt(call: Call<Any>): Call<Result<*>> =
                    ResultCall(call) as Call<Result<*>>
            }
        } else {
            null
        }
    }

    internal class ResultCall<T>(val delegate: Call<T>) :
        Call<Result<T>> {

        override fun enqueue(callback: Callback<Result<T>>) {
            delegate.enqueue(
                object : Callback<T> {
                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        if (response.isSuccessful) {
                            callback.onResponse(
                                this@ResultCall,
                                Response.success(
                                    response.code(),
                                    Result.success(response.body()!!)
                                )
                            )
                        } else {
                            callback.onResponse(
                                this@ResultCall,
                                Response.success(
                                    Result.failure(
                                        HttpException(response)
                                    )
                                )
                            )
                        }
                    }

                    override fun onFailure(call: Call<T>, t: Throwable) {
                        val errorMessage = when (t) {
                            is IOException -> "No internet connection"
                            is HttpException -> "Something went wrong!"
                            else -> t.localizedMessage
                        }
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(RuntimeException(errorMessage, t)))
                        )
                    }
                }
            )
        }

        override fun isExecuted(): Boolean {
            return delegate.isExecuted
        }

        override fun execute(): Response<Result<T>> {
            return Response.success(Result.success(delegate.execute().body()!!))
        }

        override fun cancel() {
            delegate.cancel()
        }

        override fun isCanceled(): Boolean {
            return delegate.isCanceled
        }

        override fun clone(): Call<Result<T>> {
            return ResultCall(delegate.clone())
        }

        override fun request(): Request {
            return delegate.request()
        }

        override fun timeout(): Timeout {
            return delegate.timeout()
        }
    }
}