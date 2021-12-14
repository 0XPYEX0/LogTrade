package me.xpyex.plugin.bukkit.logtrade.event;

import me.xpyex.plugin.bukkit.logtrade.file.HandleConfig;
import me.xpyex.plugin.bukkit.logtrade.file.HandleLog;
import me.xpyex.plugin.bukkit.logtrade.utils.ASMUtils;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class HandleEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent event) {
        if (HandleConfig.config.getJSONObject("LogConfig").getBoolean("LogDrop")) {
            HandleLog.log("玩家 " + event.getPlayer().getDisplayName() + " 在 " + event.getPlayer().getLocation() + " 位置丢出道具: " + event.getItemDrop().getItemStack());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPickUp(PlayerPickupItemEvent event) {
        if (HandleConfig.config.getJSONObject("LogConfig").getBoolean("LogDrop")) {
            HandleLog.log("玩家 " + event.getPlayer().getDisplayName() + " 在 " + event.getPlayer().getLocation() + " 位置拾取道具: " + event.getItem().getItemStack());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInvClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }
        if (event.getClickedInventory().getType() == InventoryType.CRAFTING || event.getClickedInventory().getType() == InventoryType.WORKBENCH) {
            if (HandleConfig.config.getJSONObject("LogConfig").getBoolean("LogCraft")) {
                if (event.getAction().toString().contains("PICKUP")) {
                    if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                        HandleLog.log("玩家 " + event.getWhoClicked().getName() + " 合成了 " + event.getCurrentItem() + "\n玩家当前坐标 " + event.getWhoClicked().getLocation());
                    }
                }
            }
            return;
        }
        if (HandleConfig.config.getJSONObject("LogConfig").getBoolean("LogInvGet")) {
            if (event.getClickedInventory() != event.getWhoClicked().getInventory()) {
                if (event.getAction().toString().contains("PICKUP")) {
                    HandleLog.log("玩家 " + event.getWhoClicked().getName() + " 从 " + event.getClickedInventory().getTitle() + " 界面取出了: " + event.getCurrentItem() + "\n玩家当前坐标 " + event.getWhoClicked().getLocation());
                } else if (event.getClick().isShiftClick() || event.getClick().isKeyboardClick()) {
                    HandleLog.log("玩家 " + event.getWhoClicked().getName() + " 从 " + event.getWhoClicked().getOpenInventory().getTitle() + " 取出了: " + event.getCurrentItem() + "\n玩家当前坐标 " + event.getWhoClicked().getLocation());
                }
            } else {
                if (event.getAction().toString().contains("PLACE")) {
                    HandleLog.log("玩家 " + event.getWhoClicked().getName() + " 向 自身背包 放入了: " + event.getCursor() + "\n玩家当前坐标 " + event.getWhoClicked().getLocation());
                }
            }
        }
        if (HandleConfig.config.getJSONObject("LogConfig").getBoolean("LogInvDrop")) {
            if (event.getClickedInventory() == event.getWhoClicked().getInventory()) {
                if (event.getAction().toString().contains("PICKUP")) {
                    HandleLog.log("玩家 " + event.getWhoClicked().getName() + " 从 自身背包 取出了: " + event.getCursor() + "\n玩家当前坐标 " + event.getWhoClicked().getLocation());
                } else if (event.getClick().isShiftClick() || event.getClick().isKeyboardClick()) {
                    HandleLog.log("玩家 " + event.getWhoClicked().getName() + " 向 " + event.getWhoClicked().getOpenInventory().getTitle() + " 放入了: " + event.getCurrentItem() + "\n玩家当前坐标 " + event.getWhoClicked().getLocation());
                }
            } else {
                if (event.getAction().toString().contains("PLACE")) {
                    HandleLog.log("玩家 " + event.getWhoClicked().getName() + " 向 " + event.getClickedInventory().getTitle() + " 界面放入了: " + event.getCursor() + "\n玩家当前坐标 " + event.getWhoClicked().getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onBukkitSetItem(BukkitSetItemEvent event) {
        HumanEntity player = event.getEntity();
        ItemStack item = event.getItem();
        if (player instanceof Player && item != null && item.getType() != Material.AIR) {
            Plugin plugin = ASMUtils.trace();
            HandleLog.log("插件 " + (plugin == null ? "未知" : plugin.getName()) + " 给予玩家 " + player.getName() + " 道具: " + item);
        }
    }
}