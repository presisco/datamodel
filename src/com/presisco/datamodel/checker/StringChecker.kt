package com.presisco.datamodel.checker

class StringChecker(
        private val size: IntRange,
        private val nullable: Boolean = false,
        regexStr: String = ""
) : Checker<String?> {
    private val regex = regexStr.toRegex()

    override fun check(item: String?): Boolean {
        if (item == null) {
            return nullable
        }

        if (item.length !in size)
            return false

        if (!regex.matches(item))
            return false

        return true
    }

    override fun checkAny(item: Any?) = check(item as String?)
}