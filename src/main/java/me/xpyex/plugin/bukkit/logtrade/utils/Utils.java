package me.xpyex.plugin.bukkit.logtrade.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.inventory.ItemStack;

public class Utils {
    public static String getTimeOfNow() {
        SimpleDateFormat Format = new SimpleDateFormat("dd:mm:ss");
        return Format.format(new Date());
    }
    public static boolean isNormalItem(ItemStack i) {
        return (i.getItemMeta().hasDisplayName() || i.getEnchantments().size() != 0 || i.getItemMeta().getLore().size() != 0);
    }
}
