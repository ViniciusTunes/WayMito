package waymito.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetNpcCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!sender.hasPermission("mito.admin")) {
                sender.sendMessage("§b§lSYSTEM §fVocê não tem permissão.");
                return true;
            }
            return true;
        }
        return false;
    }
}
