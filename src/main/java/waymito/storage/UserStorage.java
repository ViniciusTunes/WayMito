package waymito.storage;

import waymito.WayMito;
import waymito.objects.User;

import java.util.HashMap;
import java.util.UUID;

public class UserStorage {
    private UUID uuid;
    private HashMap<UUID, User> userHashMap = new HashMap<>();

    public HashMap<UUID, User> getUserHashMap() {
        return userHashMap;
    }

    public void setUserHashMap(HashMap<UUID, User> userHashMap) {
        this.userHashMap = userHashMap;
    }

    public User getUser(UUID uuid) {
        if (!this.userHashMap.containsKey(uuid)) {
            this.userHashMap.put(uuid, WayMito.userDAO.getUser(uuid.toString()));
        }
        return this.userHashMap.get(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return this.userHashMap.containsKey(uuid);
    }

    public void removeUser(UUID uuid) {
        this.userHashMap.remove(uuid);
    }

    public void putUser(UUID uuid, User user) {
        this.userHashMap.put(uuid, user);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean hasMito() {
        if (this.uuid == null) {
            return false;
        }
        if (!userHashMap.containsKey(uuid)) return false;
        return true;
    }


    public void saveAll() {
        for (UUID uuid : this.userHashMap.keySet()) {
            WayMito.userDAO.setUser(uuid.toString(), this.userHashMap.get(uuid));
        }
    }
}
