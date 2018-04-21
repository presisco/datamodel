package com.presisco.datamodel.mapmodel

import org.junit.Test
import kotlin.test.expect

class FieldDefinitionTest {

    @Test
    fun validate() {
        val stringDefinition = StringDefinition(true, 4)
        val notNullStringDef = StringDefinition(false, 16)
        val str1: String? = null
        val str2 = "abc"
        val str3 = "abcde"

        expect(true, { stringDefinition.withinDefinitionAny(str1) })
        expect(false, { notNullStringDef.withinDefinitionAny(null) })
        expect(true, { stringDefinition.withinDefinitionAny(str2) })
        expect(false, { stringDefinition.withinDefinitionAny(str3) })

        var trimmedStr = StringDefinition(false, 3).trim(str3)
        println("trimmed text: $trimmedStr, length: ${trimmedStr.length}")

        val badStr = "\r瓠?瓠?瓠?瓠?瓠?瓠?瓠?瓠?瓠?瓠?瓠?瓠"
        println("bad text: $badStr, length: ${badStr.length}")
        val subStr = badStr.substring(0..19)
        println("sub text: $subStr, length: ${subStr.length}")
        val trimStr = badStr.trim()
        println("trim text: $trimStr, length: ${trimStr.length}")
    }

}