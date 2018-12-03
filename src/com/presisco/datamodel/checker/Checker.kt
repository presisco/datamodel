package com.presisco.datamodel.checker

interface Checker<T> {
    fun check(item: T): Boolean

    fun checkAny(item: Any?): Boolean
}