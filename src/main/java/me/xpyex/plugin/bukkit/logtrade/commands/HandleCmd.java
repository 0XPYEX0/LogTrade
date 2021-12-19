package me.xpyex.plugin.bukkit.logtrade.commands;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.PrintWriter;
import me.xpyex.plugin.bukkit.logtrade.LogTrade;
import me.xpyex.plugin.bukkit.logtrade.file.HandleConfig;
import me.xpyex.plugin.bukkit.logtrade.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandleCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("LogTrade.admin".toLowerCase())) {
            Utils.autoSendMsg(sender, "&c你没有权限");
            return true;
        }
        if (args.length == 0) {
            Utils.autoSendMsg(sender,
                    "&9你目前可用的命令:",
                    "&a/" + label + "&b reload &f- &e重载配置文件");
            if (sender instanceof Player) {
                Utils.autoSendMsg(sender,
                        "&a/" + label + "&b addItem &f- &e添加手持道具到记录白名单&7[需启用对应白名单功能]",
                        "&a/" + label + "&b addType &f- &e添加手持道具类型到记录白名单&7[需启用对应白名单功能]");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!HandleConfig.reloadConfig()) {
                Utils.autoSendMsg(sender, "&c重载出错", "&c请检查后台报错尝试解决，或联系开发者 QQ1723275529");
            } else {
                Utils.autoSendMsg(sender, "&a重载成功");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("addItem")) {
            if (!(sender instanceof Player)) {
                Utils.autoSendMsg(sender, "&c这个命令仅允许玩家执行");
                return true;
            }
            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
                if (item == null || item.getType() == Material.AIR) {
                    Utils.autoSendMsg(sender, "&c手持道具为空...");
                    return;
                }
                if (!item.hasItemMeta()) {
                    Utils.autoSendMsg(sender, "&c该物品无任何附加属性...", "&6将添加至类型白名单");
                    Bukkit.dispatchCommand(sender, label + " addType");
                    return;
                }
                if (!HandleConfig.config.getJSONObject("ItemWhiteList").getBoolean("Enabled")) {
                    Utils.autoSendMsg(sender, "&c该功能未启用");
                    return;
                }
                JSONObject itemInfo = new JSONObject();
                itemInfo.put("Name", item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : "<无>");
                itemInfo.put("Lores", item.getItemMeta().hasLore() ? item.getItemMeta().getLore().toString() : "<无>");
                itemInfo.put("Type", item.getType().toString());
                HandleConfig.config.getJSONObject("ItemWhiteList").getJSONArray("Items").add(itemInfo);
                Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
                    try {
                        PrintWriter out = new PrintWriter(HandleConfig.CONFIG_FILE, "UTF-8");
                        out.write(JSON.toJSONString(HandleConfig.config, true));
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Utils.autoSendMsg(sender, "&a添加完成");
            });
            return true;
        }
        if (args[0].equalsIgnoreCase("addType")) {
            if (!(sender instanceof Player)) {
                Utils.autoSendMsg(sender, "&c这个命令仅允许玩家执行");
                return true;
            }
            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
                if (item == null || item.getType() == Material.AIR) {
                    Utils.autoSendMsg(sender, "&c手持道具为空...");
                    return;
                }
                if (!HandleConfig.config.getJSONObject("TypeWhiteList").getBoolean("Enabled")) {
                    Utils.autoSendMsg(sender, "该功能未启用");
                    return;
                }
                HandleConfig.config.getJSONObject("TypeWhiteList").getJSONArray("Types").add(item.getType().toString());
                Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
                    try {
                        PrintWriter out = new PrintWriter(HandleConfig.CONFIG_FILE, "UTF-8");
                        out.write(JSON.toJSONString(HandleConfig.config, true));
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Utils.autoSendMsg(sender, "&a添加完成");
            });
            return true;
        }
        return true;
    }
}
