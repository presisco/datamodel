package com.presisco.datamodel.checker

open class FlatMapChecker(
        vararg checkers: Pair<String, Checker<*>>
) : Checker<Map<String, *>>() {
    private val checkerMap = mutableMapOf<String, Checker<*>>()

    init {
        checkerMap.putAll(checkers)
    }

    override fun check(item: Map<String, *>) = checkWithError(item).isEmpty()

    fun checkWithError(item: Map<String, *>): Set<Error> {
        val errors = mutableSetOf<Error>()

        for ((key, value) in item) {
            checkerMap[key]?.let {
                if (!it.checkAny(value))
                    errors.add(Error.FORMAT)
            } ?: errors.add(Error.UNDEFINED)
        }
        if (lackKeys(item)) {
            errors.add(Error.LACK)
        }
        return errors
    }

    override fun trim(item: Map<String, *>): Map<String, *> {
        val trimmedMap = hashMapOf<String, Any?>()
        for ((key, value) in item) {
            checkerMap[key]?.let {
                trimmedMap[key] = if (!it.checkAny(value)) {
                    it.trimAny(value)
                } else {
                    value
                }
            }
        }
        return trimmedMap
    }

    override fun default(): Map<String, *> {
        val defaultMap = hashMapOf<String, Any?>()

        checkerMap.forEach { key, checker -> defaultMap[key] = checker.default() }

        return defaultMap
    }

    fun trimUndefinedKeys(item: Map<String, *>): Map<String, *> {
        val trimmedMap = hashMapOf<String, Any?>()

        checkerMap.keys.forEach {
            trimmedMap[it] = item[it]
        }

        return trimmedMap
    }

    fun fillLackedKeys(item: Map<String, *>): Map<String, *> {
        val trimmedMap = hashMapOf<String, Any?>()

        checkerMap.keys.forEach {
            trimmedMap[it] = if (item.containsKey(it)) {
                item[it]
            } else {
                checkerMap[it]!!.trimAny(null)
            }
        }

        return trimmedMap
    }

    open fun lackKeys(item: Map<String, *>): Boolean {
        val definedKeys = checkerMap.keys
        val itemKeys = item.keys
        return definedKeys.minus(itemKeys).isNotEmpty()
    }

    open fun hasUndefinedKeys(item: Map<String, *>): Boolean {
        val definedKeys = checkerMap.keys
        val itemKeys = item.keys
        return itemKeys.minus(definedKeys).isNotEmpty()
    }

    companion object {
        enum class Error { NONE, UNDEFINED, LACK, FORMAT }
    }
}