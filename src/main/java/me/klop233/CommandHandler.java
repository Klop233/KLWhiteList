package me.klop233;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

public class CommandHandler implements CommandExecutor {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (args.length == 0){
            sender.sendMessage("用法: /wl help");
            return true;
        }

        if ("help".equalsIgnoreCase(args[0])){
            sender.sendMessage("");
        } else if ("add".equalsIgnoreCase(args[0])) {
            addExec(sender, args);
        } else if ("remove".equalsIgnoreCase(args[0])) {
            removeExec(sender, args);
        } else if ("list".equalsIgnoreCase(args[0])) {
            listExec(sender);
        } else if ("switch".equalsIgnoreCase(args[0])) {
            switchExec(sender);
        } else if ("save".equalsIgnoreCase(args[0])) {
            saveExec(sender);
        }

        return true;
    }

    public void addExec(CommandSender sender, String[] args) {
        if (args.length >= 2) {
            if (Main.config.getList("Players").contains(args[1])) {
                sender.sendMessage(Main.getLang("msg.commands.add.failed"));
            } else {
                List<String> WList_ = Main.config.getStringList("Players");
                WList_.add(args[1]);
                Main.config.set("Players", WList_);
                Main.getInstance().saveConfig();
                Main.getInstance().getLogger().info(Main.config.getStringList("Players").toString());
                sender.sendMessage(Main.getLang("msg.commands.add.success").replace("%player%", args[1]));
            }
        } else {
            sender.sendMessage(Main.getLang("msg.commands.add.usage"));
        }
    }

    public void removeExec(CommandSender sender, String[] args) {
        if (args.length >= 2){
            if (!Main.config.getList("Players").contains(args[1])) {
                sender.sendMessage(Main.getLang("msg.commands.remove.failed"));
            } else {
                List<String> WList_ = Main.config.getStringList("Players");
                WList_.remove(args[1]);
                Main.config.set("Players", WList_);
                sender.sendMessage(Main.getLang("msg.commands.remove.success").replace("%player%", args[1]));
            }
        } else {
            sender.sendMessage(Main.getLang("msg.commands.remove.usage"));
        }
    }

    public void listExec(CommandSender sender) {
        String result = Main.getLang("msg.commands.list.result");
        StringBuilder players = new StringBuilder(" ");
        for (String s : Main.config.getStringList("Players")) {
            players.append(s).append(" ");
        }
        sender.sendMessage(result.replace("%player_list%", players.toString()));
    }

    public void switchExec(CommandSender sender) {
        Main.config.set("Enable", !Main.config.getBoolean("Enable"));
        sender.sendMessage(String.valueOf(Main.config.getBoolean("Enable"))
                           .replace("true", Main.getLang("msg.commands.switch.result_1"))
                           .replace("false", Main.getLang("msg.commands.switch.result_2"))
        );
    }

    public void saveExec(CommandSender sender) {
        Main.getInstance().saveConfig();
        sender.sendMessage(Main.getLang("msg.commands.save.success"));
    }
}
