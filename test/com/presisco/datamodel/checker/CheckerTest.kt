package com.presisco.datamodel.checker

import org.junit.Test
import kotlin.test.expect

class CheckerTest {

    @Test
    fun comparableCheckerTest() {
        expect(true) { ComparableChecker(0, 10).check(0) }
        expect(true) { ComparableChecker(0, 10).check(10) }
        expect(10) { ComparableChecker(0, 10).trim(11) }
        expect(10) { ComparableChecker(0, 10).trimAny(11) }
        expect(true) { ComparableChecker(-10.0, 10.0).check(10.0) }
        expect(false) { ComparableChecker(-10.0, 10.0).check(10.1) }
        expect(10.0) { ComparableChecker(-10.0, 10.0).trim(10.1) }
    }

    @Test
    fun stringCheckerTest() {
        expect(true) { StringChecker(0..10, true).check(null) }
        expect(false) { StringChecker(0..10, false).check(null) }
        expect(false) { StringChecker(0..10, false).checkAny(null) }
        expect(false) { StringChecker(5..10, false).check("a") }
        expect(false) { StringChecker(5..10, false).check("aaaaaaaaaaa") }
        expect(false) { StringChecker(0..4, false).check("中文测试") }
        expect("中") { StringChecker(0..4, false).trimAny("中文测试") }
        expect(true) { StringChecker(5..28, false, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}:\\d{3}").check("2018-10-30 01:23:45:678") }
        expect(false) { StringChecker(5..10, false, "[\\d+]AA").check("12AA34AA") }
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

        expect(false) { identityChecker.check(sampleMap) }
        expect("jame") { identityChecker.trimAny(sampleMap)["name"] }
        expect(false) { identityChecker.check(undefinedSample) }
        expect(false) { identityChecker.check(lackSample) }
        expect(mapOf("name" to "", "age" to 90)) { identityChecker.default() }
    }
}