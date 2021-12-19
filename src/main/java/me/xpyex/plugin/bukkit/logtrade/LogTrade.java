package me.xpyex.plugin.bukkit.logtrade;

import java.io.PrintWriter;

import me.xpyex.plugin.bukkit.logtrade.commands.HandleCmd;
import me.xpyex.plugin.bukkit.logtrade.event.HandleEvent;
import me.xpyex.plugin.bukkit.logtrade.file.HandleAllFile;
import me.xpyex.plugin.bukkit.logtrade.file.HandleLog;
import me.xpyex.plugin.bukkit.logtrade.utils.ASMUtils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class LogTrade extends JavaPlugin {
    public static LogTrade INSTANCE;
    public static Logger LOGGER;
    public static ClassLoader CLASS_LOADER;

    @Override
    public void onEnable() {
        INSTANCE = this;
        LOGGER = getLogger();
        CLASS_LOADER = getClassLoader();
        if (!load()) {
            LOGGER.warning("加载已停止");
            Bukkit.getScheduler().cancelTasks(INSTANCE);
            Bukkit.getPluginManager().disablePlugin(INSTANCE);
            return;
        }
        getCommand("LogTrade".toLowerCase()).setExecutor(new HandleCmd());
        LOGGER.info("注册命令完成");
        LOGGER.info("已加载");
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        LOGGER.info("已卸载");
        try {
            HandleLog.PLAYER_LIST.forEach((player, list) -> {
                try {
                    PrintWriter out = new PrintWriter("plugins/LogTrade/logs/" + player.getName() + ".log");
                    for (String s : list) {
                        out.println(s);
                    }
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogTrade.LOGGER.warning("请将该错误报告至开发者，在修复之前将不会记录");
            Bukkit.getScheduler().cancelTasks(LogTrade.INSTANCE);
        }
        // Plugin shutdown logic
    }

    public static boolean load() {
        if (!HandleAllFile.loadAllFiles()) {
            LOGGER.warning("配置文件出现错误，请检查配置文件，或联系开发者处理 QQ1723275529");
            return false;
        }
        LOGGER.info("配置文件加载完成");
        if (!ASMUtils.ASMLoad()) {
            LOGGER.warning("ASM注入失败，请联系开发者处理 QQ1723275529");
            return false;
        }
        LOGGER.info("ASM修改完成");
        Bukkit.getPluginManager().registerEvents(new HandleEvent(), INSTANCE);
        LOGGER.info("所有监听器注册完成");
        return true;
    }
}
