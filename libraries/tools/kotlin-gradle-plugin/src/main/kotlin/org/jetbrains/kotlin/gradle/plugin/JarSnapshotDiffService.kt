/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.plugin

import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.jetbrains.kotlin.incremental.*
import java.io.File

abstract class JarSnapshotDiffService() : BuildService<JarSnapshotDiffService.Parameters> {
    abstract class Parameters : BuildServiceParameters {
        abstract val caches: IncrementalCachesManager<*>
        abstract val reporter: ICReporter
        abstract val sourceFilesExtensions: List<String>
    }

    val caches: IncrementalCachesManager<*> = parameters.caches
    val reporter: ICReporter = parameters.reporter
    val sourceFilesExtensions: List<String> = parameters.sourceFilesExtensions

    companion object {
        //Store list of changed lookups
        val diffCache: MutableMap<Pair<JarSnapshot, JarSnapshot>, DirtyFilesContainer> = mutableMapOf()
        //TODO for test only
        fun compareJarsInternal(caches: IncrementalCachesManager<*>, reporter: ICReporter, sourceFilesExtensions: List<String>,
                                snapshot: JarSnapshot, newJar: JarSnapshot) =
            diffCache.computeIfAbsent(Pair(snapshot, newJar)) { (snapshotJar, actualJar) ->
                DirtyFilesContainer(caches, reporter, sourceFilesExtensions)
                    .also {
                        it.addByDirtyClasses(snapshotJar.fqNames.minus(actualJar.fqNames))
                        it.addByDirtyClasses(actualJar.fqNames.minus(snapshotJar.fqNames))
                        it.addByDirtySymbols(snapshotJar.symbols.minus(actualJar.symbols))
                        it.addByDirtySymbols(actualJar.symbols.minus(snapshotJar.symbols))
                    }
            }
    }

//        TODO add proper sync
    @Synchronized
    fun compareJarsInternal(snapshot: JarSnapshot, newJar: JarSnapshot): DirtyFilesContainer {
        return diffCache.computeIfAbsent(Pair(snapshot, newJar)) { (snapshotJar, actualJar) ->
            DirtyFilesContainer(caches, reporter, sourceFilesExtensions)
                .also {
                    it.addByDirtyClasses(snapshotJar.fqNames.minus(actualJar.fqNames))
                    it.addByDirtyClasses(actualJar.fqNames.minus(snapshotJar.fqNames))
                    it.addByDirtySymbols(snapshotJar.symbols.minus(actualJar.symbols))
                    it.addByDirtySymbols(actualJar.symbols.minus(snapshotJar.symbols))
                }

        }
    }



}