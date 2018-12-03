package com.presisco.datamodel.checker

import org.junit.Test
import kotlin.test.expect

class CheckerTest {

    @Test
    fun comparableCheckerTest() {
        expect(true) { ComparableChecker(0, 10).check(0) }
        expect(true) { ComparableChecker(0, 10).check(10) }
        expect(true) { ComparableChecker(-10.0, 10.0).check(10.0) }
        expect(false) { ComparableChecker(-10.0, 10.0).check(10.1) }
    }

    @Test
    fun stringCheckerTest() {
        expect(true) { StringChecker(0..10, true).check(null) }
        expect(false) { StringChecker(0..10, false).check(null) }
        expect(false) { StringChecker(5..10, false).check("a") }
        expect(false) { StringChecker(5..10, false).check("aaaaaaaaaaa") }
        expect(true) { StringChecker(5..28, false, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}:\\d{3}").check("2018-10-30 01:23:45:678") }
        expect(false) { StringChecker(5..10, false, "[\\d+]AA").check("12AA34AA") }
    }
}