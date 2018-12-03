package com.presisco.datamodel.checker

class ComparableChecker<T>(
        private val min: Comparable<T>,
        private val max: Comparable<T>
) : Checker<Comparable<T>> {
    override fun check(item: Comparable<T>) = (item >= min as T && item <= max as T)
    override fun checkAny(item: Any?) = check(item as Comparable<T>)
}