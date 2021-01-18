/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen.jdk.junit5

import org.jetbrains.kotlin.codegen.jdk.RunOnlyJdk6Test
import org.jetbrains.kotlin.codegen.jdk.SeparateJavaProcessHelper
import org.jetbrains.kotlin.test.runners.codegen.BlackBoxCodegenTestGenerated
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.platform.suite.api.IncludeClassNamePatterns
import org.junit.platform.suite.api.SelectClasses
import org.junit.runner.RunWith

/*
 * NB: ALL NECESSARY FLAGS ARE PASSED THROUGH Gradle
 */

@SelectClasses(BlackBoxCodegenTestGenerated::class)
@IncludeClassNamePatterns(".*Test.*Generated")
@RunWith(JUnitPlatformRunnerForCustomJdk::class)
abstract class CustomJvmTargetOnJvmBaseTest

@RunOnlyJdk6Test
@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget6OnJvm6 : CustomJvmTargetOnJvmBaseTest() {
    companion object {
        private val separateJavaProcessHelper = SeparateJavaProcessHelper()

        @JvmStatic
        @BeforeAll
        fun setUp() {
            separateJavaProcessHelper.setUp()
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            separateJavaProcessHelper.tearDown()
        }
    }
}

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget8OnJvm8 : CustomJvmTargetOnJvmBaseTest()

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget6OnJvm11 : CustomJvmTargetOnJvmBaseTest()

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget8OnJvm11 : CustomJvmTargetOnJvmBaseTest()

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget11OnJvm11 : CustomJvmTargetOnJvmBaseTest()

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget6OnJvmLast : CustomJvmTargetOnJvmBaseTest()

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget8OnJvmLast : CustomJvmTargetOnJvmBaseTest()

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTargetLastOnJvmLast : CustomJvmTargetOnJvmBaseTest()

//TODO: delete old tasks
@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget6OnJvm9 : CustomJvmTargetOnJvmBaseTest()

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget8OnJvm9 : CustomJvmTargetOnJvmBaseTest()

@RunWith(JUnitPlatformRunnerForCustomJdk::class)
class JvmTarget9OnJvm9 : CustomJvmTargetOnJvmBaseTest()

