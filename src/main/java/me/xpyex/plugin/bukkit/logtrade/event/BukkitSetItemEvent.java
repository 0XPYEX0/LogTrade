package me.xpyex.plugin.bukkit.logtrade.event;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * BukkitSetItemEvent
 */
public class BukkitSetItemEvent extends EntityEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final ItemStack item;

    public BukkitSetItemEvent(HumanEntity entity, ItemStack item) {
        super(entity);
        this.item = item;
    }

    @Override
    public HumanEntity getEntity() {
        return (HumanEntity) entity;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
