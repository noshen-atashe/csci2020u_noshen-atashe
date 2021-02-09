/*
 * ProGuard -- shrinking, optimization, obfuscation, and preverification
 *             of Java bytecode.
 *
 * Copyright (c) 2002-2019 Guardsquare NV
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package proguard.classfile.kotlin.obfuscate;

import proguard.classfile.*;
import proguard.classfile.constant.Constant;
import proguard.classfile.editor.CodeAttributeEditor;
import proguard.classfile.instruction.Instruction;
import proguard.classfile.instruction.visitor.*;
import proguard.classfile.util.*;
import proguard.classfile.visitor.*;
import proguard.optimize.peephole.*;

import java.util.Arrays;

import static proguard.configuration.ConfigurationLoggingInstructionSequenceConstants.*;

public class InstructionSequenceObfuscator
extends      SimplifiedVisitor
implements   ClassVisitor,
             MemberVisitor
{

    private final PeepholeOptimizer peepholeOptimizer;

    public InstructionSequenceObfuscator(ReplacementSequences replacementSequences)
    {
        BranchTargetFinder  branchTargetFinder  = new BranchTargetFinder();
        CodeAttributeEditor codeAttributeEditor = new CodeAttributeEditor();

        peepholeOptimizer = new PeepholeOptimizer(
                                branchTargetFinder, codeAttributeEditor,
                                new MyInstructionSequenceReplacer(
                                    replacementSequences.getConstants(),
                                    replacementSequences.getSequences(),
                                    branchTargetFinder,
                                    codeAttributeEditor
                                ));
    }

    // Implementations for ClassVisitor.

    @Override
    public void visitProgramClass(ProgramClass programClass)
    {
        programClass.methodsAccept(this);
    }

    @Override
    public void visitLibraryClass(LibraryClass libraryClass) { }

    // Implementations for MemberVisitor.

    @Override
    public void visitAnyMember(Clazz clazz, Member member) { }

    @Override
    public void visitProgramMethod(ProgramClass programClass, ProgramMethod programMethod)
    {
        programMethod.attributesAccept(programClass, peepholeOptimizer);
    }

    // Helper classes.

    private static class MyInstructionSequenceReplacer extends MultiInstructionVisitor
    {

        MyInstructionSequenceReplacer(Constant[]          constants,
                                      Instruction[][][]   insSequences,
                                      BranchTargetFinder  branchTargetFinder,
                                      CodeAttributeEditor codeAttributeEditor)
        {
            super(createInstructionSequenceReplacers(constants, insSequences, branchTargetFinder, codeAttributeEditor));
        }

        private static InstructionVisitor[] createInstructionSequenceReplacers(Constant[]          constants,
                                                                               Instruction[][][]   insSequences,
                                                                               BranchTargetFinder  branchTargetFinder,
                                                                               CodeAttributeEditor codeAttributeEditor)
        {
            InstructionVisitor[] isReplacers = new InstructionSequenceReplacer[insSequences.length];

            Arrays.setAll(
                isReplacers,
                index -> new InstructionSequenceReplacer(constants,
                                                         insSequences[index][0],
                                                         constants,
                                                         insSequences[index][1],
                                                         branchTargetFinder,
                                                         codeAttributeEditor,
                                                         null)
            );

            return isReplacers;
        }
    }
}
