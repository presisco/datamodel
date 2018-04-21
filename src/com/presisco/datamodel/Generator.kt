package com.presisco.datamodel

import java.util.*

abstract class Generator<T>(
        protected val rand: Random = Random(System.currentTimeMillis()),
        protected val config: Map<String, Any> = mapOf()
) {

    abstract fun getObject(): T

    fun <V> fromConfig(key: String, default: V) = if (config.containsKey(key)) config[key] as V else default

    fun randDouble(roof: Double) = rand.nextDouble() * roof

    fun stringFromConfig(key: String, default: String) = fromConfig(key, default)

    fun intFromConfig(key: String, default: Int) = fromConfig(key, default)

    fun doubleFromConfig(key: String, default: Double) = fromConfig(key, default)

    fun floatFromConfig(key: String, default: Float) = fromConfig(key, default)

    fun shortFromConfig(key: String, default: Short) = fromConfig(key, default)

    fun byteFromConfig(key: String, default: Byte) = fromConfig(key, default)

    fun booleanFromCOnfig(key: String, default: Byte) = fromConfig(key, default)
}