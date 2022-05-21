package me.klop233;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
public class EventsListener implements Listener {
    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
        if (!Main.config.getStringList("Players").contains(e.getName()) && Main.config.getBoolean("Enable")) {
            e.disallow((Result) null, ChatColor.RED + Main.config.getString("msg.kick"));
        }
    }
}
