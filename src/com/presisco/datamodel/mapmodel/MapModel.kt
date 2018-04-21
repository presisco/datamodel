package com.presisco.datamodel.mapmodel

import java.io.Serializable

open class MapModel(
        private val fields: HashMap<String, FieldDefinition<*>>
) : Serializable {

    fun getFieldCount() = fields.size

    fun getFieldNames() = fields.keys

    fun getTypeClass(field: String) = fields[field]!!.type

    fun isNullable(field: String) = fields[field]!!.nullable

    fun maxSize(field: String) = fields[field]!!.maxSize

    fun maxFractionSize(field: String) = fields[field]!!.fractionSize

    fun getDefinition(field: String) = fields[field]!!
}