package waymito.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import waymito.WayMito;
import waymito.managers.MitoManager;

public class SetMitoCommand implements CommandExecutor {
    private final MitoManager mitoManager = WayMito.mitoManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!sender.hasPermission("mito.admin")) {
                sender.sendMessage("§b§lSYSTEM §fVocê não tem permissão.");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("§b§lMITO §f/setmito <nome>");
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§b§lSYSTEM §fJogador não encontrado!");
                return true;
            }
            try {
                Bukkit.getLogger().info(String.valueOf(player.getUniqueId()));
                mitoManager.setMito(player.getUniqueId(), true);
                sender.sendMessage("§b§lSYSTEM §fO jogador " + target.getName() + " foi definido como mito!");
                Bukkit.getServer().broadcastMessage("§b§lMITO §fO novo mito é " + target.getName() + "!");
            } catch (Exception e) {
                sender.sendMessage("§c§lERRO! Ocorreu um erro ao definir o status de mito.");
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
