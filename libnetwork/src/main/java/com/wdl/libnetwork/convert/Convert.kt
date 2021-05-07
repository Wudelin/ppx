package com.wdl.libnetwork.convert

import java.lang.reflect.Type

interface Convert<T> {
    fun convert(content: String, type: Type): T
    fun convert(content: String, clazz: Class<*>): T
}