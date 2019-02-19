package com.presisco.datamodel.checker

open class StringChecker(
        val size: IntRange = 0..Int.MAX_VALUE,
        val nullable: Boolean = false,
        val regexStr: String = "",
        val default: String = ""
) : Checker<String?>() {
    private val regex = regexStr.toRegex()

    override fun check(item: String?): Pair<Boolean, String> {
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
            val rawLength = item.toByteArray().size
            if (rawLength !in size) {
                isValid = false
                message = "not in range"
            } else {
                if (regexStr.isNotEmpty() && !regex.matches(item)) {
                    isValid = false
                    message = "bad format"
                } else {
                    isValid = true
                    message = ""
                }
            }
        }
        return Pair(isValid, message)
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