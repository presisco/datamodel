package com.presisco.datamodel.mapmodel

import kotlin.math.pow

open class ShortDefinition(
        nullable: Boolean,
        maxSize: Int
) : FieldDefinition<Short>(
        Short::class.java,
        nullable,
        maxSize
) {
    private val smallest = (10.0.pow(maxSize) * (-1) + 1).toShort()
    private val biggest = (10.0.pow(maxSize) - 1).toShort()

    override fun withinDefinition(data: Short?): Boolean {
        data ?: return nullable
        return data in smallest..biggest
    }

    override fun trim(data: Short) = data.coerceIn(smallest, biggest)
}