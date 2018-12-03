package com.presisco.datamodel.checker

class FlatMapChecker(
        vararg checkers: Pair<String, Checker<*>>
) : Checker<Map<String, *>> {
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

    override fun checkAny(item: Any?) = check(item as Map<String, *>)
}