package com.shdwrealm.custodian;

import com.shdwrealm.custodian.PlayerDeath;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Random;


public final class Custodian extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Starting up Custodian ...");

        // get world paths
        File world = new File(getServer().getWorldContainer().getAbsoluteFile() + "/world");
        File nether = new File(getServer().getWorldContainer().getAbsoluteFile() +  "/world_nether");
        File end = new File(getServer().getWorldContainer().getAbsoluteFile() +  "/world_the_end");

        System.out.println("World Path: " + world.getAbsolutePath());

        // check if world needs to be reset
        File resetWorld = new File(world, "//RESET");

        System.out.println("Reset file path: " + resetWorld.getAbsolutePath());

        if (resetWorld.exists()) {
            System.out.println("Reset file detected, deleting worlds ...");
            deleteWorld(world);
            deleteWorld(nether);
            deleteWorld(end);
            System.out.println("Successfully deleted all worlds.");

            // create playerdata folder in worlds, because playerdata can't be saved without one
            File playerDataPath = new File(getServer().getWorldContainer().getAbsoluteFile() + "/world/playerdata");
            if (!playerDataPath.exists()){
                System.out.println("Playerdata folder is missing! Creating folder ...");
                playerDataPath.mkdirs();
                System.out.println("Created playerdata folder");
            }
        } else {
            System.out.println("No reset file detected.");
        }

        getServer().getPluginManager().registerEvents((Listener)new PlayerDeath(this), (Plugin)this);

        System.out.println("Registered PlayerDeath event.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean deleteWorld(File worldPath) {
        if (worldPath.exists()) {
            File[] files = worldPath.listFiles();
            for (File file : files) {
                try {
                    //System.out.println("Deleted " + file.getName());
                    if (file.isDirectory()) {
                        deleteWorld(file);
                    } else {
                        file.delete();
                    }
                } catch (Exception e) {
                    System.out.println("Error when deleting file:" + e);
                }
            }
        }
        return worldPath.delete();
    }
}
