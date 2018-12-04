package com.presisco.datamodel.checker

abstract class Checker<T> {
    abstract fun check(item: T): Boolean

    fun checkAny(item: Any?) = check(item as T)

    abstract fun trim(item: T): T

    fun trimAny(item: Any?) = trim(item as T)
}