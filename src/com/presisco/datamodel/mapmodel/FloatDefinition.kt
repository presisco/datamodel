package com.presisco.datamodel.mapmodel

import kotlin.math.pow

open class FloatDefinition(
        nullable: Boolean,
        maxSize: Int,
        fractionalSize: Int
) : FieldDefinition<Float>(
        type = Float::class.java,
        nullable = nullable,
        maxSize = maxSize,
        fractionSize = fractionalSize
) {
    private val smallest = 10.0f.pow(maxSize - fractionSize) * (-1) + 1
    private val biggest = 10.0f.pow(maxSize - fractionSize) - 1

    override fun withinDefinition(data: Float?): Boolean {
        data ?: return nullable
        return data in smallest..biggest
    }

    override fun trim(data: Float) = data.coerceIn(smallest..biggest)
}