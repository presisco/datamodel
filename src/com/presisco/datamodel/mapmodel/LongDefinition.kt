package com.presisco.datamodel.mapmodel

import kotlin.math.pow

open class LongDefinition(
        nullable: Boolean,
        maxSize: Int
) : FieldDefinition<Long>(
        Long::class.java,
        nullable,
        maxSize
) {
    private val smallest = (10.0.pow(maxSize) * (-1) + 1).toLong()
    private val biggest = (10.0.pow(maxSize) - 1).toLong()

    override fun withinDefinition(data: Long?): Boolean {
        data ?: return nullable
        return data in smallest..biggest
    }

    override fun trim(data: Long) = data.coerceIn(smallest..biggest)
}