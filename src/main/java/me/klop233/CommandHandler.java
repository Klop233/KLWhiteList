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
        if (args.length == 0){ // 如果参数长度为0
            sender.sendMessage(Main.getLang("msg.commands.whitelist.usage"));
            return true;
        }

        // 子命令
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
        if (args.length >= 2) { // 当参数足够才执行
            if (Main.config.getList("Players").contains(args[1])) {
                sender.sendMessage(Main.getLang("msg.commands.add.failed"));
            } else {
                List<String> WList_ = Main.config.getStringList("Players");
                WList_.add(args[1]);
                Main.config.set("Players", WList_);
                Main.getInstance().saveConfig();
                Main.getInstance().getLogger().info(Main.config.getStringList("Players").toString());
                sender.sendMessage(Main.getLang("msg.commands.add.success")
                                   .replace("%player%", args[1]));
                Main.getInstance().saveConfig();
            }
        } else { // 参数不足则发送帮助信息
            sender.sendMessage(Main.getLang("msg.commands.add.usage"));
        }
    }

    public void removeExec(CommandSender sender, String[] args) {
        if (args.length >= 2){ // 当参数足够才执行
            if (!Main.config.getList("Players").contains(args[1])) {
                sender.sendMessage(Main.getLang("msg.commands.remove.failed"));
            } else {
                List<String> WList_ = Main.config.getStringList("Players");
                WList_.remove(args[1]);
                Main.config.set("Players", WList_);
                sender.sendMessage(Main.getLang("msg.commands.remove.success")
                                   .replace("%player%", args[1]));
                Main.getInstance().saveConfig();
            }
        } else { // 参数不足则发送帮助信息
            sender.sendMessage(Main.getLang("msg.commands.remove.usage"));
        }
    }

    public void listExec(CommandSender sender) {
        String result = Main.getLang("msg.commands.list.result");
        StringBuilder players = new StringBuilder(" ");
        for (String s : Main.config.getStringList("Players")) {
            players.append(s).append(" ");
        }
        // 将语言文件的 %player_list% 替换并发送
        sender.sendMessage(result.replace("%player_list%", players.toString()));
        Main.getInstance().saveConfig();
    }

    public void switchExec(CommandSender sender) {
        // 将 Enable 替换为相反值
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
