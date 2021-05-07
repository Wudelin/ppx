package com.wdl.libnetwork.convert


import com.alibaba.fastjson.JSON
import java.lang.reflect.Type
import java.util.*

class JsonConvert:Convert<Any?> {
    override fun convert(content: String, type: Type): Any? {
        val parseObject = JSON.parseObject(content)
        val data = parseObject["data"]
        if (data != null){
            return JSON.parseObject(data.toString(),type)
        }
        return null
    }

    override fun convert(content: String, clazz: Class<*>): Any? {
        val parseObject = JSON.parseObject(content)
        val data = parseObject["data"]
        if (data != null){
            return JSON.parseObject(data.toString(),clazz)
        }
        return null
    }
}