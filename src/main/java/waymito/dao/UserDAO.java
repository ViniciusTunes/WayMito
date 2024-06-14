package waymito.dao;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import waymito.WayMito;
import waymito.dao.provider.ConnectionAPI;
import waymito.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

public class UserDAO {

    private final String TABLE = "waymito_users";
    private WayMito wayMito;
    private Logger logger;

    public UserDAO(WayMito wayMito, Logger logger) {
        this.wayMito = wayMito;
        this.logger = logger;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "uuid VARCHAR(100) NOT NULL PRIMARY KEY," +
                "lastkill BIGINT NOT NULL," +
                "ismito BOOLEAN NOT NULL," +
                "manykills INT NOT NULL" +
                ");";
        try (PreparedStatement statement = ConnectionAPI.connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public User getUser(String uuid) {
        String sql = "SELECT * FROM `" + TABLE + "` WHERE uuid = ?";
        try (PreparedStatement statement = ConnectionAPI.connection.prepareStatement(sql)) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                logger.info("player "+uuid+" is loaded of DataBase.");
                return new User(UUID.fromString(uuid), resultSet.getLong(2), resultSet.getBoolean(3), resultSet.getInt(4));
            } else {
                logger.info("player "+uuid+" is loaded of DataBase.");
                return new User(UUID.fromString(uuid), 0L, false, 0);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void setUser(String uuid, User user) {
        String sql = "INSERT INTO " + TABLE + " VALUES(?,?,?,?)";
        try (PreparedStatement statement = ConnectionAPI.connection.prepareStatement(sql)) {

            statement.setString(1, user.getUuid().toString());
            statement.setLong(2, user.getLastKill());
            statement.setBoolean(3, user.isMito());
            statement.setInt(4, user.getManyKills());
            statement.executeUpdate();

            logger.info("player "+uuid+" is saved on DataBase.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTime(String uuid, Long lastkill) {
        String sql = "UPDATE " + TABLE + " SET lastkill = ? WHERE uuid = ?";
        try (PreparedStatement statement = ConnectionAPI.connection.prepareStatement(sql)) {
            statement.setLong(1, lastkill);
            statement.setString(2, uuid);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update lastkill for uuid " + uuid + ": " + e.getMessage(), e);
        }
    }
    

    public void deleteUser(String uuid) {
        String sql = "DELETE FROM " + TABLE + " WHERE uuid = ?";
        try (PreparedStatement statement = ConnectionAPI.connection.prepareStatement(sql)) {

            statement.setString(1, uuid);
            statement.executeUpdate();

            logger.info("player "+uuid+" is deleted on DataBase.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete time for uuid " + uuid + ": " + e.getMessage(), e);
        }
    }

    public User loadMito() {
        String sql = "SELECT * FROM " + TABLE + " WHERE ismito = ?";
        try (PreparedStatement statement = ConnectionAPI.connection.prepareStatement(sql)) {
            statement.setBoolean(1, true);
            ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new User(UUID.fromString(resultSet.getString(1)), resultSet.getLong("lastkill"), true, resultSet.getInt(4));
                 } else {
                    String randomPlayer = gerarMitoAleatorio();
                    if (randomPlayer != null) {
                        return new User(UUID.fromString(randomPlayer), null, true, 0);
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load All " + e.getMessage(), e);
        }
        return null;
    }

    private String gerarMitoAleatorio() {
        List<Player> jogadoresOnline = new ArrayList<>(Bukkit.getOnlinePlayers());

        if (jogadoresOnline.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("Nenhum jogador online encontrado para associar o mito.");
            return null;
        }

        Random random = new Random();
        Player jogadorSelecionado = jogadoresOnline.get(random.nextInt(jogadoresOnline.size()));

        return jogadorSelecionado.getUniqueId().toString();
    }
}