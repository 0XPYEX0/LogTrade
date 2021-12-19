package me.xpyex.plugin.bukkit.logtrade.file;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import me.xpyex.plugin.bukkit.logtrade.LogTrade;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.xpyex.plugin.bukkit.logtrade.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class HandleLog {
    static {
        TimeZone.setDefault(Calendar.getInstance().getTimeZone());
    }
    private final static File LOG_FOLDER = new File("plugins/LogTrade/logs");
    public final static HashMap<Player, ConcurrentLinkedQueue<String>> PLAYER_LIST = new HashMap<>();

    public static void log(Player player, String msg) {
        if (!PLAYER_LIST.containsKey(player)) {
            PLAYER_LIST.put(player, new ConcurrentLinkedQueue<>());
        }
        PLAYER_LIST.get(player).add("[" + Utils.getTimeOfNow() + "] " + msg);
        if (HandleConfig.config.getBoolean("ConsoleLog")) {
            LogTrade.LOGGER.info(msg);
        }
    }

    public static void log(HumanEntity player, String msg) {
        if (player instanceof Player) {
            log((Player)player, msg);
        }
    }

    public static boolean init() {
        if (!LOG_FOLDER.exists()) {
            LOG_FOLDER.mkdirs();
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(LogTrade.INSTANCE, () -> {
            PLAYER_LIST.forEach((player, strings) -> {
                try {
                    final File log = Utils.getLogFile(player);
                    if (!log.exists()) {
                        log.createNewFile();
                    }
                    PrintWriter out = new PrintWriter(log, "UTF-8");
                    for (String s : strings) {
                        out.println(s);
                    }
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }, 20 * 60, 20 * 60);
        return true;
    }
}
