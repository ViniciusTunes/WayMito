package waymito.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import waymito.inventories.MitoInventory;

public class MitoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (command.getName().equalsIgnoreCase("mito")) {
                try {
                    MitoInventory.getInventory().open(p);
                } catch (Exception e) {
                    sender.sendMessage("§b§lSYSTEM §fFalha ao abrir o inventário!");
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
