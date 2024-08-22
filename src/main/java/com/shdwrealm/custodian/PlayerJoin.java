package com.shdwrealm.custodian;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    Custodian plugin;

    public PlayerJoin(Custodian main) {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // enable hearts tab scoreboard
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard objectives add health health");
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard objectives setdisplay list health");
    }
}
