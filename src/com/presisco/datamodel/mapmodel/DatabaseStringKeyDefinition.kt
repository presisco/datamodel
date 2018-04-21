package mapmodel

import com.presisco.datamodel.mapmodel.ByteStringDefinition

open class DatabaseStringKeyDefinition(
        nullable: Boolean,
        maxSize: Int
) : ByteStringDefinition(
        nullable,
        maxSize
) {
    override fun withinDefinition(data: String?): Boolean {
        if (data != null && data.isEmpty()){
            return false
        }
        return super.withinDefinition(data)
    }
}