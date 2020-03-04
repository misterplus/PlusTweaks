package com.misterplus.plustweaks.coremod;

import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_4;
import static org.objectweb.asm.Opcodes.ICONST_M1;

import com.misterplus.plustweaks.PlusTweaks;
import com.misterplus.plustweaks.config.Configs;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class PlusTweaksASMTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.world.end.DragonFightManager")) {
            if (Configs.portalSettings.noEndPortal) {
                PlusTweaks.logger.info("About to patch DragonFight class: " + name);
                return patchEndPortal(name, basicClass);
            }
        }
        else if (transformedName.equals("net.minecraft.block.BlockLiquid")) {
            if (Configs.genericSettings.noCobbleGen) {
                PlusTweaks.logger.info("About to patch BlockLiquid class" + name);
                return patchLiquidMix(name, basicClass);
            }
        }
        return basicClass;
    }

    public byte[] patchEndPortal(String name, byte[] bytes) {

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        for (MethodNode m : classNode.methods) {
            int opcode_index = -1;
            String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, m.name, m.desc);

            if (!"func_186094_a".equals(methodName) && !"generatePortal".equals(methodName))
                continue;

            PlusTweaks.logger.info("About to patch generatePortal method: " + m.name);
            AbstractInsnNode currentNode;
            AbstractInsnNode targetNode = null;

            Iterator<AbstractInsnNode> iter = m.instructions.iterator();

            int index = -1;

            while (iter.hasNext()) {
                index++;
                currentNode = iter.next();
                if (currentNode.getOpcode() == ILOAD) {
                    targetNode = currentNode;
                    opcode_index = index;
                }
            }
            AbstractInsnNode nextNode = targetNode.getNext();

            AbstractInsnNode remMode1 = m.instructions.get(opcode_index);
            m.instructions.remove(remMode1);

            InsnList toInject = new InsnList();
            toInject.add(new InsnNode(ICONST_0));
            m.instructions.insertBefore(nextNode, toInject);
            PlusTweaks.logger.info("generatePortal Patch complete!");
            break;
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    public byte[] patchLiquidMix(String name, byte[] bytes) {

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        for (MethodNode m : classNode.methods) {
            int opcode_index = -1;
            String methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, m.name, m.desc);

            if (!"func_176365_e".equals(methodName) && !"checkForMixing".equals(methodName))
                continue;

            PlusTweaks.logger.info("About to patch checkForMixing method: " + m.name);
            AbstractInsnNode currentNode;
            AbstractInsnNode targetNode = null;

            Iterator<AbstractInsnNode> iter = m.instructions.iterator();

            int index = -1;

            while (iter.hasNext()) {
                index++;
                currentNode = iter.next();
                if (currentNode.getOpcode() == ICONST_4) {
                    targetNode = currentNode;
                    opcode_index = index;
                }
            }
            AbstractInsnNode nextNode = targetNode.getNext();

            AbstractInsnNode remMode1 = m.instructions.get(opcode_index);
            m.instructions.remove(remMode1);

            InsnList toInject = new InsnList();
            toInject.add(new InsnNode(ICONST_M1));
            m.instructions.insertBefore(nextNode, toInject);
            PlusTweaks.logger.info("checkForMixing Patch complete!");
            break;
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}

