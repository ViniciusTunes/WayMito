package waymito.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import waymito.WayMito;
import waymito.objects.User;

import java.util.ArrayList;
import java.util.List;

public class MitoInventory implements InventoryProvider {
    private FileConfiguration config = WayMito.configManager.getConfig("config");
    private FileConfiguration messages = WayMito.configManager.getConfig("messages");

    public static SmartInventory getInventory() {
        return SmartInventory.builder()
                .id("mitoinventory")
                .provider(new MitoInventory() {
                }).size(5, 9)
                .title(WayMito.configManager.getConfig("config").getString("menu-name"))
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        ItemStack userItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) userItem.getItemMeta();

        if (!WayMito.userStorage.hasMito()) {
            skullMeta.setDisplayName("§c§lNão existe nenhum mito.");
        } else {
            User user = WayMito.userStorage.getUser(WayMito.userStorage.getUuid());
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(user.getUuid()));
            skullMeta.setDisplayName("§5§lMITO: §f" + Bukkit.getOfflinePlayer(user.getUuid()).getName());
            List<String> lores = new ArrayList<>();
            lores.add("");
            lores.add("§fKills: §b" + user.getManyKills());
            skullMeta.setLore(lores);
        }

        userItem.setItemMeta(skullMeta);

        contents.set(2, 4, ClickableItem.of(userItem, e -> {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
        }));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
