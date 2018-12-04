package com.presisco.datamodel.checker

open class FlatMapChecker(
        vararg checkers: Pair<String, Checker<*>>
) : Checker<Map<String, *>>() {
    private val checkerMap = mutableMapOf<String, Checker<*>>()

    init {
        checkerMap.putAll(checkers)
    }

    override fun check(item: Map<String, *>): Boolean {
        for ((key, value) in item) {
            if (!checkerMap[key]!!.checkAny(value))
                return false
        }
        return true
    }

    override fun trim(item: Map<String, *>): Map<String, *> {
        val trimmedMap = mutableMapOf<String, Any?>()
        for ((key, value) in item) {
            val checker = checkerMap[key]!!
            trimmedMap[key] = if (!checker.checkAny(value)) {
                checker.trimAny(value)
            } else {
                value
            }
        }
        return trimmedMap
    }
}