package com.presisco.datamodel.mapmodel

open class ByteStringDefinition(
        nullable: Boolean,
        maxSize: Int
) : FieldDefinition<String>(
        String::class.java,
        nullable,
        maxSize
) {
    override fun withinDefinition(data: String?): Boolean {
        data ?: return nullable
        val bytes = data.toByteArray()
        return bytes.size <= maxSize
    }

    override fun trim(data: String): String {
        if(data.isEmpty())
            return data
        val bytes = data.toByteArray()
        val limited = ByteArray(maxSize, { bytes[it] })
        return String(limited)
    }
}