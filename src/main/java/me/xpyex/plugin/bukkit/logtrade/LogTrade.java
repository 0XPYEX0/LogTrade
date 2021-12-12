package me.xpyex.plugin.bukkit.logtrade;

import me.xpyex.plugin.bukkit.logtrade.event.HandleEvent;
import me.xpyex.plugin.bukkit.logtrade.event.HandleTradeMe;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class LogTrade extends JavaPlugin {
    public static LogTrade INSTANCE;
    public static Logger LOGGER;

    @Override
    public void onEnable() {
        INSTANCE = this;
        LOGGER = getLogger();
        load();
        LOGGER.info("已加载");
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        LOGGER.info("已卸载");
        // Plugin shutdown logic
    }

    public static void load() {
        if (Bukkit.getPluginManager().isPluginEnabled("TradeMe")) {
            Bukkit.getPluginManager().registerEvents(new HandleTradeMe(), INSTANCE);
            LOGGER.info("已注册TradeMe相关监听器");
        }
        Bukkit.getPluginManager().registerEvents(new HandleEvent(), INSTANCE);
        LOGGER.info("所有监听器注册完成");
    }
}
