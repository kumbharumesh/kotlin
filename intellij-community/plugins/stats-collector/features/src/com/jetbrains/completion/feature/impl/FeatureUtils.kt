/*
 * Copyright 2000-2018 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jetbrains.completion.feature.impl


object FeatureUtils {
    const val UNDEFINED: String = "UNDEFINED"
    const val INVALID_CACHE: String = "INVALID_CACHE"

    const val OTHER: String = "OTHER"
    const val NONE: String = "NONE"

    const val ML_RANK: String = "ml_rank"
    const val BEFORE_ORDER: String = "before_rerank_order"

    const val DEFAULT: String = "default"

    fun getOtherCategoryFeatureName(name: String): String = "$name=$OTHER"
    fun getUndefinedFeatureName(name: String): String = "$name=$UNDEFINED"

    fun asRelevanceMap(relevanceObjects: List<Pair<String, Any?>>): MutableMap<String, Any> {
        val relevanceMap = mutableMapOf<String, Any>()
        for (pair in relevanceObjects) {
            val name = pair.first.normalized()
            val value = pair.second
            if (value == null) continue
            if (name == "proximity") {
                val proximityMap = value.toString().asProximityMap("prox")
                relevanceMap.putAll(proximityMap)
            }
            else {
                relevanceMap[name] = value
            }
        }

        return relevanceMap
    }

    fun normalizeFeatureName(name: String): String = name.normalized()

    private fun String.normalized(): String {
        return substringBefore('@')
    }

    /**
     * Proximity features now came like [samePsiFile=true, openedInEditor=false], need to convert to proper map
     */
    private fun String.asProximityMap(prefix: String): Map<String, Any> {
        val items = this.replace("[", "").replace("]", "").split(",")

        return items.map {
            val (key, value) = it.trim().split("=")
            "${prefix}_$key" to value
        }.toMap()
    }
}
