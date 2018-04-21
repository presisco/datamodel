package com.presisco.datamodel.mapmodel

import java.io.Serializable

abstract class FieldDefinition<T>(
        val type: Class<T>,
        val nullable: Boolean,
        val maxSize: Int,
        val fractionSize: Int
) : Serializable {
    constructor(type: Class<T>, nullable: Boolean, size: Int) : this(type, nullable, size, -1)

    fun withinDefinitionAny(data: Any?): Boolean = withinDefinition(data as T)

    abstract fun withinDefinition(data: T?): Boolean

    fun trimAny(data: Any): Any = trim(data as T) as Any

    abstract fun trim(data: T): T
}