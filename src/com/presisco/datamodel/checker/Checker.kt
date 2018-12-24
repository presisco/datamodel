package com.presisco.datamodel.checker

abstract class Checker<T> {
    abstract fun check(item: T): Pair<Boolean, String>

    fun checkAny(item: Any?): Pair<Boolean, String> {
        return try {
            check(item as T)
        } catch (e: Exception) {
            Pair(false, e.message.toString())
        }
    }

    abstract fun trim(item: T): T

    abstract fun default(): T

    fun trimAny(item: Any?) = trim(item as T)
}