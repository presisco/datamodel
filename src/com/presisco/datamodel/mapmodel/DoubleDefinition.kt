package com.presisco.datamodel.mapmodel

import kotlin.math.pow

open class DoubleDefinition(
        nullable: Boolean,
        maxSize: Int,
        fractionalSize: Int
) : FieldDefinition<Double>(
        type = Double::class.java,
        nullable = nullable,
        maxSize = maxSize,
        fractionSize = fractionalSize
) {
    private val smallest = 10.0.pow(maxSize - fractionSize) * (-1) + 1
    private val biggest = 10.0.pow(maxSize - fractionSize) - 1

    override fun withinDefinition(data: Double?): Boolean {
        data ?: return nullable
        return data in smallest..biggest
    }

    override fun trim(data: Double) = data.coerceIn(smallest..biggest)
}