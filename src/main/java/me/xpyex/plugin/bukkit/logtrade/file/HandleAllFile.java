package me.xpyex.plugin.bukkit.logtrade.file;

import java.io.File;

import me.xpyex.plugin.bukkit.logtrade.LogTrade;

public class HandleAllFile {
    public final static File ROOT = new File("plugins/LogTrade");

    public static boolean loadAllFiles() {
        if (!ROOT.exists()) {
            ROOT.mkdirs();
            LogTrade.LOGGER.info("正在生成配置文件");
        }
        return (HandleConfig.loadConfig() && HandleLog.init());
    }
}
