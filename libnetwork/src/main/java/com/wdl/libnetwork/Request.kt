package com.wdl.libnetwork

import androidx.annotation.IntDef
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.ParameterizedType


@Suppress("UNCHECKED_CAST")
abstract class Request<T, R : Request<T, R>> constructor(protected var url: String) : Cloneable {
    // header
    protected val mHeaders = HashMap<String, String>()

    // 参数
    protected val mParams = HashMap<String, Any>()

    private var cacheKey: String? = null

    companion object {
        // 只访问缓存
        const val CACHE_ONLY = 1

        // 先访问缓存，在做网络请求，成功后缓存本地
        const val CACHE_FIRST = 2

        // 只访问服务器，不做任何存储
        const val NET_ONLY = 3

        // 访问网络成功后缓存
        const val NET_CACHE = 4
    }

    @IntDef(CACHE_ONLY, CACHE_FIRST, NET_ONLY, NET_CACHE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class CacheStrategy

    fun addHeader(key: String, head: String): R {
        mHeaders[key] = head
        return this as R
    }

    fun addParam(key: String, value: Any): R {
        mParams[key] = value
        return this as R
    }

    fun cacheKey(key: String): R {
        this.cacheKey = key
        return this as R
    }

    /**
     * callback为空时代表同步，否则代表异步
     */
    fun execute(callback: JsonCallback<T>? = null) {
        val call = getCall()
        if (callback == null) {

        } else {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val resp = ApiResponse<T>()
                    resp.message = e.message
                    callback.onError(resp)
                }

                override fun onResponse(call: Call, response: Response) {
                    val resp = parseResp(response, callback)
                    if (resp.success) {
                        callback.onError(resp)
                    } else {
                        callback.onSuccess(resp)
                    }
                }

            })
        }


    }

    private fun parseResp(response: Response, callback: JsonCallback<T>): ApiResponse<T> {
        var message: String? = null
        val status = response.code
        val success = response.isSuccessful
        if (success) {
            val content = response.body?.string()
            // 获取泛型类型
            val paramsType: ParameterizedType =
                callback.javaClass.genericSuperclass as ParameterizedType
            val type = paramsType.actualTypeArguments[0]

        }
        return ApiResponse<T>()

    }

    private fun getCall(): Call {
        val builder = okhttp3.Request.Builder()
        addHeaders(builder)
        val request = generalRequest(builder)
        return ApiService.okHttpClient.newCall(request)
    }

    abstract fun generalRequest(builder: okhttp3.Request.Builder): okhttp3.Request

    private fun addHeaders(builder: okhttp3.Request.Builder) {
        mHeaders.entries.forEach {
            builder.addHeader(it.key, it.value)
        }
    }
}