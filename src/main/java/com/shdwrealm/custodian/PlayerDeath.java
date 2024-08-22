package com.shdwrealm.custodian;

import com.shdwrealm.custodian.Custodian;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;

public class PlayerDeath implements Listener {

    Custodian plugin;

    public PlayerDeath(Custodian main) {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            System.out.println("Running Custodian ...");

            String kickMessage = event.getDeathMessage();

            System.out.println("Kicking out online players ...");
            for (Player plyr : Bukkit.getServer().getOnlinePlayers()) {
                plyr.kickPlayer(kickMessage);
            }
            System.out.println("Kicked out all players");

            World world = Bukkit.getWorld("world");
            File worldPath = world.getWorldFolder();
            File resetWorld = new File(worldPath, "//RESET");

            try {
                resetWorld.createNewFile();
                System.out.println("Successfully created reset file");
            } catch (IOException e) {
                System.out.println("Failed to create reset file");
            }

            Bukkit.getServer().shutdown();
        }, 100L);
    }
}
