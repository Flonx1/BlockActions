package wtf.flonxi.removes;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import wtf.flonxi.Main;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Crafts implements Listener {
    private final Main plugin;

    public Crafts(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (!(e.getInventory() instanceof CraftingInventory)) return;
        CraftingInventory inv = (CraftingInventory) e.getInventory();

        if (e.getWhoClicked().hasPermission(plugin.craftConfig.getString("craft-permission"))) return;

        ItemStack[] matrix = inv.getMatrix(); // slots 1–9 (0-indexed)

        Set<String> keys = plugin.craftConfig.getConfigurationSection("CRAFTS").getKeys(false);
        for (String materialName : keys) {
            Material mat = Material.matchMaterial(materialName);
            if (mat == null) continue;

            List<Integer> slots = plugin.craftConfig.getIntegerList("CRAFTS." + materialName);
            boolean matched = true;

            for (int i : slots) {
                int index = i - 1; // config uses 1-based indexing
                if (index < 0 || index >= matrix.length) {
                    matched = false;
                    break;
                }
                ItemStack item = matrix[index];
                if (item == null || item.getType() != mat) {
                    matched = false;
                    break;
                }
            }

            if (matched) {
                e.setCancelled(true);
                String msg = plugin.craftConfig.getString("access-denied-message");
                if (msg != null) {
                    e.getWhoClicked().sendMessage(msg);
                }
                break;
            }
        }
    }
}
