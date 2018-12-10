package com.presisco.datamodel.checker

abstract class Checker<T> {
    abstract fun check(item: T): Boolean

    fun checkAny(item: Any?): Boolean {
        return try {
            check(item as T)
        } catch (e: Exception) {
            false
        }
    }

    abstract fun trim(item: T): T

    abstract fun default(): T

    fun trimAny(item: Any?) = trim(item as T)
}