/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.codegen.jdk.junit5

import org.jetbrains.kotlin.codegen.jdk.performFilter
import org.junit.platform.runner.JUnitPlatform

class JUnitPlatformRunnerForCustomJdk(testClass: Class<*>) : JUnitPlatform(testClass) {
    init {
        performFilter(testClass, this)
    }
}
