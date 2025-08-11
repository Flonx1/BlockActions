package wtf.flonxi.removes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import wtf.flonxi.Main;

import java.util.List;

public class Blocks implements Listener {
    private final Main plugin;

    public Blocks(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!e.getPlayer().hasPermission(plugin.blocksConfig.getString("place-permission"))) {
            List<String> blocked = plugin.blocksConfig.getStringList("PLACE-BLOCKS-BLACKLIST");
            if (blocked.contains(e.getBlock().getType().toString())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(plugin.blocksConfig.getString("place-denied-message"));
            }
        }
    }
}
