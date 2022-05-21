package me.klop233;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin{
    public static FileConfiguration config;
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("KL WhiteList is Loading");
        saveDefaultConfig();
        config = getConfig();
        getCommand("whitelist").setExecutor(new CommandHandler());
        Bukkit.getPluginManager().registerEvents(new EventsListener(), this);
        getLogger().info("KL WhiteList is Loaded");
    }

    public static Main getInstance (){
        return instance;
    }

    public static String getLang(String path) {
        if (config.getString(path) == null)
            return "§你奶奶的把语言文件搞坏了";
        return config.getString(path).replace("&", "§");
    }

    public static List<String> getListLang(String path) {
        return null;
    }
}
