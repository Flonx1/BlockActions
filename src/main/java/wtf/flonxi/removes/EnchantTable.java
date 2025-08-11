package wtf.flonxi.removes;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import wtf.flonxi.Main;

import java.util.*;

public class EnchantTable implements Listener {
    private final Main plugin;

    public EnchantTable(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {
        List<String> blocked = plugin.enchantConfig.getStringList("REMOVE-ENCHANTS");
        Map<Enchantment, Integer> enchants = e.getEnchantsToAdd();

        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            String enchKey = entry.getKey().getKey().getKey().toUpperCase() + "_" + entry.getValue();
            if (blocked.contains(enchKey)) {
                if (!e.getEnchanter().hasPermission(plugin.enchantConfig.getString("enchant-permission"))) {
                    e.setCancelled(true);

                    Enchantment replacement = getRandomAllowedEnchantment(e.getItem(), blocked);
                    if (replacement != null) {
                        e.getItem().addEnchantment(replacement, entry.getValue());
                    }
                    return;
                }
            }
        }
    }

    private Enchantment getRandomAllowedEnchantment(ItemStack item, List<String> blocked) {
        List<Enchantment> all = Arrays.asList(Enchantment.values());
        List<Enchantment> allowed = new ArrayList<>();

        for (Enchantment ench : all) {
            if (!ench.canEnchantItem(item)) continue;

            String key = ench.getKey().getKey().toUpperCase() + "_1";
            if (!blocked.contains(key)) {
                allowed.add(ench);
            }
        }

        if (allowed.isEmpty()) return null;
        Collections.shuffle(allowed);
        return allowed.get(0);
    }
}
