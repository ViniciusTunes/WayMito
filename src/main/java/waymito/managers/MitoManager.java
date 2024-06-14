package waymito.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import waymito.WayMito;
import waymito.objects.User;

import java.util.UUID;

public class MitoManager {

    public void setMito(UUID uuid, boolean isMito) {
        try {
            User user = WayMito.userStorage.getUser(uuid);
            if (WayMito.userStorage == null) {
                throw new IllegalStateException("userStorage não foi inicializado!");
            }

            if (user == null) {
                Bukkit.getConsoleSender().sendMessage("Usuário não foi encontrado.");
            }
            user.setMito(isMito);
            WayMito.userDAO.setUser(String.valueOf(uuid), user);

            Player p = Bukkit.getPlayer(user.getUuid());
            if (isMito) {
                Bukkit.broadcastMessage("§b§lMITO §a" + p.getName() + " §fagora é um mito!");
                p.sendMessage("§b§lMITO §fParabéns! Você agora é um mito!");
            } else {
                Bukkit.broadcastMessage("§b§lMITO §a" + p.getName() + " §fnão é mais um mito!");
                p.sendMessage("§b§lMITO §fVocê não é mais um mito.");
            }
        } catch (Exception e) {
            Bukkit.broadcastMessage("§c§lERRO! Ocorreu um erro ao acessar o banco de dados.");
            e.printStackTrace();
        }
    }

    private static void applyVisualTag(User user) {
        if (PlaceholderAPI.isRegistered("mito_prefix")) {
            Player player = Bukkit.getPlayer(user.getUuid());
            if (player != null) {
                String prefix = PlaceholderAPI.setPlaceholders(player, "%mito_prefix%");
                if (prefix != null && !prefix.isEmpty()) {
                    player.setDisplayName(prefix + " " + player.getName());
                }
            }
        }
    }

    private static void removeVisualTag(User user) {
        if (PlaceholderAPI.isRegistered("mito_prefix")) {
            Player player = Bukkit.getPlayer(user.getUuid());
            if (player != null) {
                player.setDisplayName(player.getName());
            }
        }
    }
}
