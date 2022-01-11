package me.xpyex.plugin.bukkit.logtrade.event;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.xpyex.plugin.bukkit.logtrade.LogTrade;
import me.xpyex.plugin.bukkit.logtrade.file.HandleConfig;
import me.xpyex.plugin.bukkit.logtrade.file.HandleLog;
import me.xpyex.plugin.bukkit.logtrade.utils.ASMUtils;
import me.xpyex.plugin.bukkit.logtrade.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class HandleEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
            if (Utils.needLog(event.getItemDrop().getItemStack())) {
                    HandleLog.log(event.getPlayer(), "玩家 " + event.getPlayer().getDisplayName() + " 在 " + event.getPlayer().getLocation() + " 位置丢出道具: " + event.getItemDrop().getItemStack());
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPickUp(PlayerPickupItemEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
            if (Utils.needLog(event.getItem().getItemStack())) {
                    HandleLog.log(event.getPlayer(), "玩家 " + event.getPlayer().getDisplayName() + " 在 " + event.getPlayer().getLocation() + " 位置拾取道具: " + event.getItem().getItemStack());
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInvClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv == null) {
            return;
        }
        final ItemStack[] item = {event.getCurrentItem().clone(), event.getCursor().clone()};
        HumanEntity player = event.getWhoClicked();
        Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
            if (inv.getType() == InventoryType.CRAFTING || inv.getType() == InventoryType.WORKBENCH) {
                if (event.getAction().toString().contains("PICKUP")) {
                    if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                        if (Utils.needLog(item[0])) {
                            HandleLog.log(player, "玩家 " + player.getName() + " 合成了 " + item[0] + "\n玩家当前坐标 " + player.getLocation());
                        }
                    }
                }
                return;
            }
            if (HandleConfig.config.getJSONObject("LogConfig").getBoolean("LogInvGet")) {
                if (inv != player.getInventory()) {
                    if (Utils.needLog(item[0])) {
                        if (event.getAction().toString().contains("PICKUP")) {
                            HandleLog.log(player, "玩家 " + player.getName() + " 从 " + inv.getTitle() + " 界面取出了: " + item[0] + "\n玩家当前坐标 " + player.getLocation());
                        } else if (event.getClick().isShiftClick() || event.getClick().isKeyboardClick()) {
                            HandleLog.log(player, "玩家 " + player.getName() + " 从 " + player.getOpenInventory().getTitle() + " 取出了: " + item[0] + "\n玩家当前坐标 " + player.getLocation());
                        }
                    }
                } else {
                    if (Utils.needLog(item[1])) {
                        if (event.getAction().toString().contains("PLACE")) {
                            HandleLog.log(player, "玩家 " + player.getName() + " 向 自身背包 放入了: " + item[1] + "\n玩家当前坐标 " + player.getLocation());
                        }
                    }
                }
            }
            if (HandleConfig.config.getJSONObject("LogConfig").getBoolean("LogInvDrop")) {
                if (inv == player.getInventory()) {
                    if (Utils.needLog(item[0])) {
                        if (event.getAction().toString().contains("PICKUP")) {
                            HandleLog.log(player, "玩家 " + player.getName() + " 从 自身背包 取出了: " + item[0] + "\n玩家当前坐标 " + player.getLocation());
                        } else if (event.getClick().isShiftClick() || event.getClick().isKeyboardClick()) {
                            HandleLog.log(player, "玩家 " + player.getName() + " 向 " + player.getOpenInventory().getTitle() + " 放入了: " + item[0] + "\n玩家当前坐标 " + player.getLocation());
                        }
                    }
                } else {
                    if (Utils.needLog(item[1])) {
                        if (event.getAction().toString().contains("PLACE")) {
                            HandleLog.log(player, "玩家 " + player.getName() + " 向 " + inv.getTitle() + " 界面放入了: " + item[1] + "\n玩家当前坐标 " + player.getLocation());
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    public void onBukkitSetItem(BukkitSetItemEvent event) {
        HumanEntity player = event.getEntity();
        ItemStack item = event.getItem();
        if (player instanceof Player && item != null && item.getType() != Material.AIR) {
            Plugin plugin = ASMUtils.trace();
            Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
                if (Utils.needLog(item)) {
                    HandleLog.log(player, "插件 " + (plugin == null ? "未知" : plugin.getName()) + " 给予玩家 " + player.getName() + " 道具: " + item);
                }
            });
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
            if (!HandleLog.PLAYER_LIST.containsKey(event.getPlayer())) {
                HandleLog.PLAYER_LIST.put(event.getPlayer(), new ConcurrentLinkedQueue<>());
            }
            final File log = Utils.getLogFile(event.getPlayer());
            if (!log.exists()) {
                try {
                    log.createNewFile();
                    LogTrade.LOGGER.info("为玩家 " + event.getPlayer() + " 创建专属日志文件");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(LogTrade.INSTANCE, () -> {
            Utils.logToFileNow(event.getPlayer(), true);
        });
    }
}
