package me.xpyex.plugin.bukkit.logtrade.file;

import me.xpyex.plugin.bukkit.logtrade.LogTrade;
import me.xpyex.plugin.bukkit.logtrade.utils.Utils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HandleLog {
    private final static File LOG_FOLDER = new File("plugins/LogTrade/logs");
    private final static File LOG_FILE = new File("plugins/LogTrade/logs/" + (new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date())) + ".log");
    public final static ConcurrentLinkedQueue<String> LOG_LIST = new ConcurrentLinkedQueue<>();
    public static boolean init() {
        try {
            if (!LOG_FOLDER.exists()) {
                LOG_FOLDER.mkdirs();
            }
            LOG_FILE.createNewFile();
            LogTrade.LOGGER.info("本次记录文件为: " + LOG_FILE.getName());
            Bukkit.getScheduler().runTaskTimerAsynchronously(LogTrade.INSTANCE, () -> {
                try {
                    PrintWriter out = new PrintWriter(LOG_FILE, "UTF-8");
                    for (String s : LOG_LIST) {
                        out.println(s);
                    }
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogTrade.LOGGER.warning("请将该错误报告至开发者，在修复之前将不会记录");
                    Bukkit.getScheduler().cancelTasks(LogTrade.INSTANCE);
                }
            }, 20 * 60, 20 * 60);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void log(String msg) {
        LOG_LIST.add(Utils.getTimeOfNow() + msg);
    }
}
