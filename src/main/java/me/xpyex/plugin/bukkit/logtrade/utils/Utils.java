package me.xpyex.plugin.bukkit.logtrade.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.xpyex.plugin.bukkit.logtrade.file.HandleConfig;
import me.xpyex.plugin.bukkit.logtrade.file.HandleLog;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {
    private static final String[] checkString = "1234567890abcdefklonmr".split("");

    public static String getTimeOfNow() {
        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return Format.format(new Date());
    }

    public static String getColorMsg(String msg) {
        if (msg == null) {
            throw new NullPointerException("获取彩色字符串出现空，请联系开发者 QQ1723275529");
        }
        for (String s : checkString) {
            msg = msg.replace("&" + s.toLowerCase(), "§" + s.toLowerCase());
            msg = msg.replace("&" + s.toUpperCase(), "§" + s.toUpperCase());
        }
        return msg;
    }

    public static void autoSendMsg(CommandSender target, String... msg) {
        for (String s : msg) {
            target.sendMessage(getColorMsg(s));
        }
    }

    public static File getLogFile(Player player) {
        return new File("plugins/LogTrade/logs/" + player.getName() + ".log");
    }

    public static void logToFileNow(Player player, boolean remove) {
        try {
            File logFile = getLogFile(player);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            PrintWriter out = new PrintWriter(logFile, "UTF-8");
            for (String s : HandleLog.PLAYER_LIST.get(player)) {
                out.println(s);
            }
            out.flush();
            out.close();
            if (remove) {
                HandleLog.PLAYER_LIST.remove(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean needLog(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }
        if (HandleConfig.config.getJSONObject("TypeWhiteList").getBoolean("Enabled")) {
            if (HandleConfig.config.getJSONObject("TypeWhiteList").getJSONArray("Types").contains(item.getType().toString())) {
                return true;
            }
        }
        if (HandleConfig.config.getJSONObject("ItemWhiteList").getBoolean("Enabled")) {
            if (!item.hasItemMeta()) {
                return false;
            }
            for (Object V : HandleConfig.config.getJSONObject("ItemWhiteList").getJSONArray("Items")) {
                if (V instanceof JSONObject) {
                    JSONObject itemInfo = (JSONObject) V;
                    if (itemInfo.getString("Type").equals(item.getType().toString()) && itemInfo.getString("Name").equals(item.getItemMeta().getDisplayName()) && itemInfo.getString("Lores").equals(item.getItemMeta().hasLore() ? item.getItemMeta().getLore().toString() : "<无>")) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }
}
