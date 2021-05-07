package com.wdl.libnetwork

import java.net.URLEncoder

class UrlCreator {
    companion object {
        // 参数拼接
        fun createUrlFromParams(url: String, params: HashMap<String, Any>): String {
            val sb = StringBuilder(url)
            sb.append(
                if (url.indexOf("?") > 0 || url.indexOf("&") > 0) {
                    "&"
                } else {
                    "?"
                }
            )

            params.forEach {
                val value = URLEncoder.encode("${it.value}", "UTF-8")
                sb.append("${it.key}=${value}&")
            }

            sb.deleteCharAt(sb.length - 1)
            return sb.toString()

        }
    }
}