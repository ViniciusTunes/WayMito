package waymito.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import waymito.WayMito;

import java.util.HashMap;
import java.util.logging.Logger;

public class ConfigManager {

    private ConfigUtils configUtils;
    private HashMap<String, FileConfiguration> configurationMap = new HashMap<>();

    public ConfigManager(WayMito main, Logger logger){
        configUtils = new ConfigUtils(main, logger);

        logger.info("Loading configurations...");
        configUtils.createConfig("config");
        configUtils.createConfig("messages");
        configurationMap.put("config", configUtils.getConfig("config"));
        configurationMap.put("messages", configUtils.getConfig("messages"));
        logger.info("Configurations loaded.");
    }

    public FileConfiguration getConfig(String config){
        return configurationMap.get(config);
    }
}