package me.klop233;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
public class EventsListener implements Listener {
    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
        // 如果该玩家不在白名单内且白名单为开启状态 则不接受这名玩家的连接
        if (!Main.config.getStringList("Players").contains(e.getName()) && Main.config.getBoolean("Enable")) {
            e.disallow((Result) null, Main.getLang("msg.kick"));
        }
    }
}
