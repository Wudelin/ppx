package com.wdl.libnetwork.request

import com.wdl.libnetwork.Request
import okhttp3.FormBody

class PostRequest<T>(url: String) :Request<T,PostRequest<T>>(url) {
    override fun generalRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val formBody = FormBody.Builder()
        mParams.forEach{
            formBody.add(it.key,"${it.value}")
        }
        return builder.url(url).post(formBody.build()).build()
    }
}