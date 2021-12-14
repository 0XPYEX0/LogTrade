package me.xpyex.plugin.bukkit.logtrade.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getTimeOfNow() {
        SimpleDateFormat Format = new SimpleDateFormat("HH:mm:ss");
        return Format.format(new Date());
    }
}
