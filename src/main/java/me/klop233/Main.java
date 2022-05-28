package me.klop233;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;


public class Main extends JavaPlugin{
    public static FileConfiguration config;
    public static Main instance;

    @Override
    public void onEnable() {
        getLogger().info("KL WhiteList is Loading");

        instance = this;
        saveDefaultConfig();
        config = getConfig();
        // 注册命令
        getCommand("whitelist").setExecutor(new CommandHandler());
        // 注册时间监听器
        Bukkit.getPluginManager().registerEvents(new EventsListener(), this);
        // 注册命令补全器
        Objects.requireNonNull(Bukkit.getPluginCommand("whitelist")).setTabCompleter(new TabHandler());

        getLogger().info("KL WhiteList is Loaded");
    }

    public static Main getInstance (){
        return instance;
    }

    public static String getLang(String path) {
        // 如果获取到的语言为null
        if (config.getString(path) == null)
            return "&c语言文件异常\n缺少&2" + path;
        // 替换颜色代码并返回
        return config.getString(path).replace("&", "§");
    }

    @Deprecated
    public static List<String> getListLang(String path) {
        return null;
    }


}
