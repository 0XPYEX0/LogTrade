package me.xpyex.plugin.bukkit.logtrade.event;

import me.Zrips.TradeMe.Events.TradeFinishEvent;

import me.xpyex.plugin.bukkit.logtrade.file.HandleConfig;
import me.xpyex.plugin.bukkit.logtrade.file.HandleLog;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HandleTradeMe implements Listener {
    @EventHandler
    public void onTrade(TradeFinishEvent event) {
        if (HandleConfig.config.getJSONObject("LogConfig").getBoolean("LogTradeMe")) {
            HandleLog.log("玩家 " + event.getPlayer1() + " 与玩家 " + event.getPlayer2() + " 交易，购买了: " + event.getP1trade().getBuyingItems());
            HandleLog.log("玩家 " + event.getPlayer1() + " 与玩家 " + event.getPlayer2() + " 交易，出售了: " + event.getP1trade().getTradedItemsList());
        }
    }
}
