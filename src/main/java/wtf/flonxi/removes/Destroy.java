package wtf.flonxi.removes;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import wtf.flonxi.Main;

import java.util.List;

public class Destroy implements Listener {
    private final Main plugin;

    public Destroy(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.getPlayer().hasPermission(plugin.destroyConfig.getString("destroy-permission"))) {
            Block block = e.getBlock();
            List<String> blocked = plugin.destroyConfig.getStringList("BLOCKS_DENIED");
            if (blocked.contains(block.getType().toString())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(plugin.destroyConfig.getString("destroy-denied-message"));
            }
        }
    }
}
