package com.shdwrealm.custodian;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public final class Custodian extends JavaPlugin {

    private Logger logger = null;

    protected void logMessage(String message){
        logger.log(new LogRecord(Level.ALL,message));
    }
    @Override
    public void onEnable() {
        logger = this.getLogger();
        logMessage("Starting up Custodian ...");

        // get world paths
        File world = new File(getServer().getWorldContainer().getAbsoluteFile() + "/world");
        File nether = new File(getServer().getWorldContainer().getAbsoluteFile() +  "/world_nether");
        File end = new File(getServer().getWorldContainer().getAbsoluteFile() +  "/world_the_end");
        logMessage("World Path: " + world.getAbsolutePath());

        // check if world needs to be reset
        File resetWorld = new File(world, "//RESET");

        logMessage("Reset file path: " + resetWorld.getAbsolutePath());

        if (resetWorld.exists()) {
            logMessage("Reset file detected, deleting worlds ...");
            deleteWorld(world);
            deleteWorld(nether);
            deleteWorld(end);
            logMessage("Successfully deleted all worlds.");

            // create playerdata folder in worlds, because playerdata can't be saved without one
            File playerDataPath = new File(getServer().getWorldContainer().getAbsoluteFile() + "/world/playerdata");
            if (!playerDataPath.exists()){
                logMessage("Playerdata folder is missing! Creating folder ...");
                playerDataPath.mkdirs();
                logMessage("Created playerdata folder");
            }
        } else {
            logMessage("No reset file detected.");
        }

        getServer().getPluginManager().registerEvents((Listener)new PlayerJoin(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerDeath(this), (Plugin)this);

        logMessage("Registered PlayerDeath event.");
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
                    if (file.isDirectory()) {
                        deleteWorld(file);
                    } else {
                        file.delete();
                    }
                } catch (Exception e) {
                    logMessage("Error when deleting file:" + e);
                }
            }
        }
        return worldPath.delete();
    }
}
