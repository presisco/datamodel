package com.presisco.datamodel.checker

import org.junit.Test
import kotlin.test.expect

class CheckerTest {

    @Test
    fun comparableCheckerTest() {
        expect(true) { ComparableChecker(0, 10).check(0).first }
        expect(true) { ComparableChecker(0, 10).check(10).first }
        expect(10) { ComparableChecker(0, 10).trim(11) }
        expect(10) { ComparableChecker(0, 10).trimAny(11) }
        expect(true) { ComparableChecker(-10.0, 10.0).check(10.0).first }
        expect(Pair(false, "not in range")) { ComparableChecker(-10.0, 10.0).check(10.1) }
        expect(10.0) { ComparableChecker(-10.0, 10.0).trim(10.1) }
    }

    @Test
    fun stringCheckerTest() {
        expect(true) { StringChecker(0..10, true).check(null).first }
        expect(Pair(false, "is null")) { StringChecker(0..10, false).check(null) }
        expect(Pair(false, "is null")) { StringChecker(0..10, false).checkAny(null) }
        expect(Pair(false, "not in range")) { StringChecker(5..10, false).check("a") }
        expect(Pair(false, "not in range")) { StringChecker(5..10, false).check("aaaaaaaaaaa") }
        expect(Pair(false, "not in range")) { StringChecker(0..4, false).check("中文测试") }
        expect("中") { StringChecker(0..4, false).trimAny("中文测试") }
        expect(true) { StringChecker(5..28, false, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}:\\d{3}").check("2018-10-30 01:23:45:678").first }
        expect(Pair(false, "bad format")) { StringChecker(5..10, false, "[\\d+]AA").check("12AA34AA") }
    }

    @Test
    fun flatMapCheckerTest() {
        val sampleMap = mapOf(
                "name" to "james",
                "age" to 18
        )

        val undefinedSample = mapOf(
                "name" to "james",
                "age" to 18,
                "sex" to "male"
        )

        val lackSample = mapOf(
                "name" to "james",
                "sex" to "male"
        )

        val identityChecker = FlatMapChecker(
                "name" to StringChecker(0..4, false),
                "age" to ComparableChecker(0, 90)
        )

        expect(false) { identityChecker.check(sampleMap).first }
        expect("jame") { identityChecker.trimAny(sampleMap)["name"] }
        expect(false) { identityChecker.check(undefinedSample).first }
        expect(false) { identityChecker.check(lackSample).first }
        expect(mapOf("name" to "", "age" to 90)) { identityChecker.default() }
    }
}