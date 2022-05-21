package me.klop233;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class CommandHandler implements CommandExecutor {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) { // 如果参数长度为0
            sender.sendMessage(Main.getLang("msg.commands.whitelist.usage"));
            return true;
        }

        // 子命令
        if ("help".equalsIgnoreCase(args[0])) {
            for (String s : Main.config.getStringList("msg.help"))
                sender.sendMessage(s.replace("&", "§"));
        } else if ("add".equalsIgnoreCase(args[0])) {
            addExec(sender, args);
        } else if ("remove".equalsIgnoreCase(args[0])) {
            removeExec(sender, args);
        } else if ("list".equalsIgnoreCase(args[0])) {
            listExec(sender);
        } else if ("switch".equalsIgnoreCase(args[0])) {
            switchExec(sender);
        } else if ("check".equalsIgnoreCase(args[0])) {
            checkExec(sender, args);
        } else if ("reload".equalsIgnoreCase(args[0])) {
            reloadExec(sender);
        }

        return true;
    }

    public void addExec(CommandSender sender, String[] args) {
        if (args.length >= 2) { // 当参数足够才执行

            if (args.length != 2)
                sender.sendMessage(Main.getLang("msg.batch"));

            for (int i=1;i<args.length;i++){
                if (Main.config.getList("Players").contains(args[i])) {
                    sender.sendMessage(Main.getLang("msg.commands.add.failed"));
                } else {
                    List<String> WList_ = Main.config.getStringList("Players");
                    // 去空格并添加
                    WList_.add(args[i].replace(" ", ""));
                    Main.config.set("Players", WList_);
                    Main.getInstance().saveConfig();
                    sender.sendMessage(Main.getLang("msg.commands.add.success")
                            .replace("%player%", args[i]));
                    Main.getInstance().saveConfig();
                }
            }

        } else { // 参数不足则发送帮助信息
            sender.sendMessage(Main.getLang("msg.commands.add.usage"));
        }
    }

    public void removeExec(CommandSender sender, String[] args) {
        if (args.length >= 2) { // 当参数足够才执行

            if (args.length != 2)
                sender.sendMessage(Main.getLang("msg.batch"));

            for (int i=1;i<args.length;i++){
                if (!Main.config.getList("Players").contains(args[i])) {
                    sender.sendMessage(Main.getLang("msg.commands.remove.failed"));
                } else {
                    List<String> WList_ = Main.config.getStringList("Players");
                    // 去空格并删除
                    WList_.remove(args[i].replace(" ", ""));
                    Main.config.set("Players", WList_);
                    sender.sendMessage(Main.getLang("msg.commands.remove.success")
                            .replace("%player%", args[i]));
                    Main.getInstance().saveConfig();
                }
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

    public void checkExec(CommandSender sender, String[] args) {
        if (args.length >= 2) { // 当参数足够才执行

            if (args.length != 2)
                sender.sendMessage(Main.getLang("msg.batch"));

            for (int i=1;i<args.length;i++){
                if (Main.config.getStringList("Players").contains(args[i].replace(" ", ""))) {
                    sender.sendMessage(Main.getLang("msg.commands.check.result_1")
                                       .replace("%player%", args[i]));
                } else {
                    sender.sendMessage(Main.getLang("msg.commands.check.result_2")
                                       .replace("%player%", args[i]));
                }
            }

        } else { // 参数不足则发送帮助信息
            sender.sendMessage(Main.getLang("msg.commands.check.usage"));
        }
    }

    public void reloadExec(CommandSender sender) {
        Main.getInstance().reloadConfig();
        sender.sendMessage(Main.getLang("msg.commands.reload.success"));
    }
}
