package com.presisco.datamodel.mapmodel

import kotlin.math.pow

open class IntDefinition(
        nullable: Boolean,
        maxSize: Int
) : FieldDefinition<Int>(
        Int::class.java,
        nullable,
        maxSize
) {
    private val smallest = (10.0.pow(maxSize) * (-1) + 1).toInt()
    private val biggest = (10.0.pow(maxSize) - 1).toInt()

    override fun withinDefinition(data: Int?): Boolean {
        data ?: return nullable
        return data in smallest..biggest
    }

    override fun trim(data: Int) = data.coerceIn(smallest..biggest)
}