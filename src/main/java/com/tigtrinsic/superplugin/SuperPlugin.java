package com.tigtrinsic.superplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class SuperPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("YourPlugin has been enabled!");
        // Register the event listener
        getServer().getPluginManager().registerEvents(new PlayerClickListener(), this);

        // Here you can register commands, events, etc.
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("YourPlugin has been disabled.");

        // Any cleanup code when the plugin is disabled or server is stopping
    }

    // You can add more methods here, such as command executors, event listeners, etc.
}
