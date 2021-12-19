package me.xpyex.plugin.bukkit.logtrade.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 这个类用来修改字节码
 */
public class ClassVisitorCraftInventoryPlayer extends ClassVisitor {

    private static final int VERSION = Opcodes.ASM6;
    private final String className;

    public ClassVisitorCraftInventoryPlayer(String className, ClassVisitor classVisitor) {
        super(VERSION, classVisitor);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        // 如果方法名是 setItem 并且 方法的参数是 int, ItemStack 返回值是 void
        if ("setItem".equals(name) && "(ILorg/bukkit/inventory/ItemStack;)V".equals(descriptor)) {
            return new MethodVisitor(VERSION, methodVisitor) {
                @Override
                public void visitInsn(int opcode) {
                    // 在方法返回前插入代码
                    if (Opcodes.RETURN == opcode) {
                        // Call BukkitSetItemEvent
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/bukkit/Bukkit", "getPluginManager", "()Lorg/bukkit/plugin/PluginManager;", false);
                        mv.visitTypeInsn(Opcodes.NEW, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent");
                        mv.visitInsn(Opcodes.DUP);
                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className.replace(".", "/"), "getHolder", "()Lorg/bukkit/entity/HumanEntity;", false);
                        mv.visitVarInsn(Opcodes.ALOAD, 2);
                        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "me/xpyex/plugin/bukkit/logtrade/event/BukkitSetItemEvent", "<init>", "(Lorg/bukkit/entity/HumanEntity;Lorg/bukkit/inventory/ItemStack;)V", false);
                        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "org/bukkit/plugin/PluginManager", "callEvent", "(Lorg/bukkit/event/Event;)V", true);
                    }
                    super.visitInsn(opcode);
                }
            };
        }
        return methodVisitor;
    }
}
