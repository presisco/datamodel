package com.presisco.datamodel.mapmodel

open class StringDefinition(
        nullable: Boolean,
        maxSize: Int
) : FieldDefinition<String>(
        type = String::class.java,
        nullable = nullable,
        size = maxSize
) {
    override fun withinDefinition(data: String?): Boolean {
        data ?: return nullable
        return data.length <= maxSize
    }

    override fun trim(data: String) = data.trim().substring(0.until(maxSize))
}
