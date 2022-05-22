package me.klop233;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

public class TabHandler implements TabCompleter {
    private static final Map<UUID, Boolean> QUERY_BUFFER = new HashMap<>();

    @Override
    @ParametersAreNonnullByDefault
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            // 控制台返回null
            return null;
        }
        if (args.length == 0) {
            List<String> result = new ArrayList<>();
            result.add("add");
            result.add("remove");
            result.add("check");
            result.add("list");
            result.add("switch");
            return result;
        } else {
            if ("add".equalsIgnoreCase(args[0])
                    || "remove".equalsIgnoreCase(args[0])
                    || "check".equalsIgnoreCase(args[0])) {
                // Tab返回配置文件内已有的白名单玩家
                return new ArrayList<>(Main.config.getStringList("Players"));
            }
        }
        return null;
    }
}
