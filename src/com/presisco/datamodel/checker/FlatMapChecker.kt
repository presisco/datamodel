package com.presisco.datamodel.checker

open class FlatMapChecker(
        vararg checkers: Pair<String, Checker<*>>
) : Checker<Map<String, *>>() {
    private val checkerMap = mutableMapOf<String, Checker<*>>()

    init {
        checkerMap.putAll(checkers)
    }

    override fun check(item: Map<String, *>): Pair<Boolean, String> {
        val errors = checkWithError(item)
        return if (errors.isEmpty()) {
            Pair(true, "")
        } else {
            Pair(false, errors.toString())
        }
    }

    fun checkWithError(item: Map<String, *>): Set<Pair<String, String>> {
        val errors = mutableSetOf<Pair<String, String>>()

        for ((key, value) in item) {
            checkerMap[key]?.let {
                val result = it.checkAny(value)
                if (!result.first)
                    errors.add(Pair(key, result.second))
            } ?: errors.add(Pair(key, "undefined"))
        }
        val absent = absentKeys(item)
        if (absent.isNotEmpty()) {
            errors.addAll(absent.map { Pair(it, "absent") })
        }
        return errors
    }

    override fun trim(item: Map<String, *>): Map<String, *> {
        val trimmedMap = hashMapOf<String, Any?>()
        for ((key, value) in item) {
            checkerMap[key]?.let {
                trimmedMap[key] = if (!it.checkAny(value).first) {
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

    fun fillAbsentKeys(item: Map<String, *>): Map<String, *> {
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

    open fun absentKeys(item: Map<String, *>): Set<String> {
        val definedKeys = checkerMap.keys
        val itemKeys = item.keys
        return definedKeys.minus(itemKeys)
    }

    open fun hasUndefinedKeys(item: Map<String, *>): Boolean {
        val definedKeys = checkerMap.keys
        val itemKeys = item.keys
        return itemKeys.minus(definedKeys).isNotEmpty()
    }
}