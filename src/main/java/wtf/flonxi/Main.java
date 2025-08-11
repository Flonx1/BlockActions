package wtf.flonxi;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.flonxi.removes.Blocks;
import wtf.flonxi.removes.Crafts;
import wtf.flonxi.removes.Destroy;
import wtf.flonxi.removes.EnchantTable;

import java.io.File;

public final class Main extends JavaPlugin {
    public FileConfiguration enchantConfig, destroyConfig, craftConfig, blocksConfig;

    @Override
    public void onEnable() {
        saveDefaultConfigs();
        loadConfigs();

        Bukkit.getPluginManager().registerEvents(new EnchantTable(this), this);
        Bukkit.getPluginManager().registerEvents(new Destroy(this), this);
        Bukkit.getPluginManager().registerEvents(new Crafts(this), this);
        Bukkit.getPluginManager().registerEvents(new Blocks(this), this);
    }

    @Override
    public void onDisable() {}

    private void saveDefaultConfigs() {
        saveResource("configs/enchanttable.yml", false);
        saveResource("configs/destroy.yml", false);
        saveResource("configs/crafts.yml", false);
        saveResource("configs/blocks.yml", false);
    }

    private void loadConfigs() {
        enchantConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "configs/enchanttable.yml"));
        destroyConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "configs/destroy.yml"));
        craftConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "configs/crafts.yml"));
        blocksConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "configs/blocks.yml"));
    }
}
