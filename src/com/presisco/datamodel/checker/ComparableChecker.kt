package com.presisco.datamodel.checker


open class ComparableChecker<T>(
        val min: Comparable<T>,
        val max: Comparable<T>,
        val nullable: Boolean = false,
        val default: Comparable<T> = max
) : Checker<Comparable<T>?>() {
    override fun check(item: Comparable<T>?): Pair<Boolean, String> {
        val isValid: Boolean
        val message: String
        if (item == null) {
            if (nullable) {
                isValid = true
                message = ""
            } else {
                isValid = false
                message = "is null"
            }
        } else {
            if (item >= min as T && item <= max as T) {
                isValid = true
                message = ""
            } else {
                isValid = false
                message = "not in range"
            }
        }
        return Pair(isValid, message)
    }

    override fun trim(item: Comparable<T>?): Comparable<T> {
        return if (item == null) {
            default()
        } else if (item > max as T) {
            max
        } else if (item < min as T) {
            min
        } else {
            item
        }
    }

    override fun default() = default
}