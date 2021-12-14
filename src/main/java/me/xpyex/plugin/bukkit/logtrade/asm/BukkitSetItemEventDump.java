package me.xpyex.plugin.bukkit.logtrade.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class BukkitSetItemEventDump implements Opcodes {

    public static byte[] dump() {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", null, "org/bukkit/event/entity/EntityEvent", null);

        cw.visitSource("BukkitSetItemEvent.java", null);

        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "HANDLER_LIST", "Lorg/bukkit/event/HandlerList;", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "item", "Lorg/bukkit/inventory/ItemStack;", null, null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/bukkit/entity/HumanEntity;Lorg/bukkit/inventory/ItemStack;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(18, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESPECIAL, "org/bukkit/event/entity/EntityEvent", "<init>", "(Lorg/bukkit/entity/Entity;)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(19, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitFieldInsn(PUTFIELD, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", "item", "Lorg/bukkit/inventory/ItemStack;");
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(20, l2);
            mv.visitInsn(RETURN);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "Lme/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent;", null, l0, l3, 0);
            mv.visitLocalVariable("entity", "Lorg/bukkit/entity/HumanEntity;", null, l0, l3, 1);
            mv.visitLocalVariable("item", "Lorg/bukkit/inventory/ItemStack;", null, l0, l3, 2);
            mv.visitMaxs(2, 3);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getEntity", "()Lorg/bukkit/entity/HumanEntity;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(24, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", "entity", "Lorg/bukkit/entity/Entity;");
            mv.visitTypeInsn(CHECKCAST, "org/bukkit/entity/HumanEntity");
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lme/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getItem", "()Lorg/bukkit/inventory/ItemStack;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(28, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", "item", "Lorg/bukkit/inventory/ItemStack;");
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lme/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getHandlers", "()Lorg/bukkit/event/HandlerList;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(33, l0);
            mv.visitFieldInsn(GETSTATIC, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", "HANDLER_LIST", "Lorg/bukkit/event/HandlerList;");
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lme/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "getHandlerList", "()Lorg/bukkit/event/HandlerList;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(37, l0);
            mv.visitFieldInsn(GETSTATIC, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", "HANDLER_LIST", "Lorg/bukkit/event/HandlerList;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "getEntity", "()Lorg/bukkit/entity/Entity;", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(11, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", "getEntity", "()Lorg/bukkit/entity/HumanEntity;", false);
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lme/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(13, l0);
            mv.visitTypeInsn(NEW, "org/bukkit/event/HandlerList");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "org/bukkit/event/HandlerList", "<init>", "()V", false);
            mv.visitFieldInsn(PUTSTATIC, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", "HANDLER_LIST", "Lorg/bukkit/event/HandlerList;");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
