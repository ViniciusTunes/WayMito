package waymito;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import waymito.commands.MitoCommand;
import waymito.commands.SetMitoCommand;
import waymito.commands.SetNpcCommand;
import waymito.configuration.ConfigManager;
import waymito.dao.UserDAO;
import waymito.dao.provider.ConnectionAPI;
import waymito.managers.MitoManager;
import waymito.storage.UserStorage;

public final class WayMito extends JavaPlugin {

    public static ConfigManager configManager;
    public static UserDAO userDAO;
    public static UserStorage userStorage;
    public static MitoManager mitoManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this, getLogger());
        FileConfiguration messages = configManager.getConfig("messages");
        FileConfiguration config = configManager.getConfig("config");

        ConnectionAPI.connect(getLogger(), configManager.getConfig("config"));
        userDAO = new UserDAO(this, getLogger());
        userStorage = new UserStorage();
        mitoManager = new MitoManager();
        userDAO.createTable();
        userDAO.loadMito();
        commandRegister();
        registerEvents();
    }
    @Override
    public void onDisable() {
        userStorage.saveAll();
        ConnectionAPI.disconnect(getLogger());
    }

    public void commandRegister() {
        getCommand("mito").setExecutor(new MitoCommand());
        getCommand("setmito").setExecutor(new SetMitoCommand());
        getCommand("setnpcmito").setExecutor(new SetNpcCommand());
    }
    public void registerEvents() {
    }
}
