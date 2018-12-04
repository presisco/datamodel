package com.presisco.datamodel.checker


open class ComparableChecker<T>(
        val min: Comparable<T>,
        val max: Comparable<T>,
        val nullable: Boolean = false,
        val default: Comparable<T> = max
) : Checker<Comparable<T>?>() {
    override fun check(item: Comparable<T>?): Boolean {
        if (item == null)
            return nullable
        return (item >= min as T && item <= max as T)
    }

    override fun trim(item: Comparable<T>?): Comparable<T> {
        return if (item == null) {
            default
        } else if (item > max as T) {
            max
        } else if (item < min as T) {
            min
        } else {
            item
        }
    }
}