package com.wdl.libnetwork.request

import com.wdl.libnetwork.Request
import com.wdl.libnetwork.UrlCreator

class GetRequest<T>(url: String) : Request<T,GetRequest<T>>(url) {
    override fun generalRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        return builder.get().url(UrlCreator.createUrlFromParams(url, mParams)).build()
    }
}