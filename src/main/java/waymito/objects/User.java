package waymito.objects;

import java.util.UUID;

public class User {
    private UUID uuid;
    private Long lastKill;
    private boolean isMito;
    private int manyKills;

    public User(UUID uuid, Long lastKill, boolean isMito, int manyKills) {
        this.uuid = uuid;
        this.lastKill = lastKill;
        this.isMito = isMito;
        this.manyKills = manyKills;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getLastKill() {
        return lastKill;
    }

    public void setLastKill(Long lastKill) {
        this.lastKill = lastKill;
    }

    public boolean isMito() {
        return isMito;
    }

    public void setMito(boolean mito) {
        isMito = mito;
    }

    public int getManyKills() {
        return manyKills;
    }

    public void setManyKills(int manyKills) {
        this.manyKills = manyKills;
    }
}
