/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.backend

import org.jetbrains.kotlin.fir.resolve.firSymbolProvider
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes
import org.jetbrains.kotlin.ir.declarations.IrConstructor
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.utils.addToStdlib.firstIsInstance

class Fir2IrBuiltIns(
    private val components: Fir2IrComponents,
    private val provider: Fir2IrSpecialSymbolProvider?
) : Fir2IrComponents by components {
    init {
        provider?.initComponents(components)
    }

    private val enhancedNullabilityAnnotationSymbol by lazy {
        annotationSymbolById(CompilerConeAttributes.EnhancedNullability.ANNOTATION_CLASS_ID)
    }

    internal fun enhancedNullabilityAnnotationConstructorCall(): IrConstructorCall? =
        enhancedNullabilityAnnotationSymbol?.toConstructorCall()

    private val flexibleNullabilityAnnotationSymbol by lazy {
        annotationSymbolById(CompilerConeAttributes.FlexibleNullability.ANNOTATION_CLASS_ID)
    }

    internal fun flexibleNullabilityAnnotationConstructorCall(): IrConstructorCall? =
        flexibleNullabilityAnnotationSymbol?.toConstructorCall()

    private val jvmWildcardAnnotationSymbol by lazy {
        annotationSymbolById(CompilerConeAttributes.JvmWildcard.ANNOTATION_CLASS_ID)
    }

    internal fun jvmWildcardAnnotationConstructorCall(): IrConstructorCall? =
        jvmWildcardAnnotationSymbol?.toConstructorCall()

    private val jvmSuppressWildcardsAnnotationSymbol by lazy {
        annotationSymbolById(CompilerConeAttributes.JvmWildcard.SUPPRESS_CLASS_ID)
    }

    internal fun jvmSuppressWildcardsAnnotationConstructorCall(suppressIt: Boolean): IrConstructorCall? =
        jvmSuppressWildcardsAnnotationSymbol?.toConstructorCall()?.apply {
            putValueArgument(0, IrConstImpl.boolean(startOffset, endOffset, irBuiltIns.booleanType, suppressIt))
        }

    private fun annotationSymbolById(id: ClassId): IrClassSymbol? =
        provider?.getClassSymbolById(id) ?: session.firSymbolProvider.getClassLikeSymbolByFqName(id)?.toSymbol(
            session, classifierStorage, ConversionTypeContext.DEFAULT
        ) as? IrClassSymbol

    private fun IrClassSymbol.toConstructorCall(): IrConstructorCallImpl =
        IrConstructorCallImpl.fromSymbolOwner(defaultType, owner.declarations.firstIsInstance<IrConstructor>().symbol)
}