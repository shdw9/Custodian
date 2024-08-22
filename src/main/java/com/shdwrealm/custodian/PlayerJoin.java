package com.shdwrealm.custodian;

import com.shdwrealm.custodian.Custodian;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.GameRule;
import org.bukkit.World;

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
