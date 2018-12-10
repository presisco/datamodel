package com.presisco.datamodel.checker

open class StringChecker(
        val size: IntRange,
        val nullable: Boolean = false,
        val regexStr: String = "",
        val default: String = ""
) : Checker<String?>() {
    private val regex = regexStr.toRegex()

    override fun check(item: String?): Boolean {
        if (item == null) {
            return nullable
        }

        val rawLength = item.toByteArray().size
        if (rawLength !in size)
            return false

        if (regexStr.isNotEmpty() && !regex.matches(item))
            return false

        return true
    }

    override fun trim(item: String?): String? {
        if (item == null) {
            return if (nullable) {
                item
            } else {
                default()
            }
        }

        val rawLength = item.toByteArray().size
        if (rawLength !in size) {
            return item.substring(0.until(size.last / (rawLength / item.length)))
        }

        if (regexStr.isNotEmpty() && !regex.matches(item)) {
            return default()
        }

        return item
    }

    override fun default() = default
}