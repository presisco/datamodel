package com.presisco.datamodel.checker

open class ComparableChecker<T>(
        private val min: Comparable<T>,
        private val max: Comparable<T>
) : Checker<Comparable<T>>() {
    override fun check(item: Comparable<T>) = (item >= min as T && item <= max as T)
    override fun trim(item: Comparable<T>): Comparable<T> {
        return if (item >= min as T && item <= max as T) {
            item
        } else {
            max
        }
    }
}