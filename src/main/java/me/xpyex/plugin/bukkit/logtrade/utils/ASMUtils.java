package me.xpyex.plugin.bukkit.logtrade.utils;

import me.xpyex.plugin.bukkit.logtrade.LogTrade;
import me.xpyex.plugin.bukkit.logtrade.asm.BukkitSetItemEventDump;
import me.xpyex.plugin.bukkit.logtrade.asm.ClassVisitorCraftInventoryPlayer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ASMUtils{

    /**
     * NMS版本
     */
    public static final String NMS_VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];

    /**
     * 类加载器
     */
    private static final ClassLoader CONTEXT_CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    public static boolean ASMLoad() {
        // 插件加载时 进行字节码修改

        // 玩家背包实现类的类名
        String className = String.format("org.bukkit.craftbukkit.%s.inventory.CraftInventoryPlayer", NMS_VERSION);

        try {
            // 加载BukkitSetItemEvent类
            loadClass("me.xpyex.plugin.bukkit.logtrade.event.BukkitSetItemEvent", BukkitSetItemEventDump.dump());
            // 修改 CraftInventoryPlayer类
            ClassReader classReader = new ClassReader(className);
            ClassWriter classWriter = new ClassWriter(classReader, 0);
            ClassVisitor classVisitor = new ClassVisitorCraftInventoryPlayer(className, classWriter);
            classReader.accept(classVisitor, 0);
            byte[] bytes = classWriter.toByteArray();
            // 加载 CraftInventoryPlayer类
            loadClass(className, bytes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 追踪
     *
     * @return 最初的插件
     */
    public static Plugin trace() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        // 从最前面找 最初的调用者
        for (int i = stackTrace.length - 1; i >= 0; i--) {
            try {
                ClassLoader classLoader = Class.forName(stackTrace[i].getClassName()).getClassLoader();
                // 不能找到自己
                if (classLoader == null || classLoader.equals(LogTrade.CLASS_LOADER)) {
                    continue;
                }

                // 找这个 plugin 字段
                Field pluginField = classLoader.getClass().getDeclaredField("plugin");
                pluginField.setAccessible(true);
                return (Plugin) pluginField.get(classLoader);
            } catch (ClassNotFoundException | IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchFieldException | ClassCastException e) {
                // 找不到plugin字段说明 不是 PluginClassLoader 类
            }

        }
        return null;
    }

    /**
     * 运行中动态加载字节码
     *
     * @param className 类名
     * @param bytes     字节码
     */
    public static Class<?> loadClass(String className, byte[] bytes) {
        try {
            Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
            return (Class<?>) defineClass.invoke(CONTEXT_CLASS_LOADER, className, bytes, 0, bytes.length);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("字节码加载失败!", e);
        }
    }
}
