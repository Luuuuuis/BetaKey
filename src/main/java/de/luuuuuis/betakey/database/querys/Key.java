/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 19:52.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.database.querys;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.database.DBManager;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Key {

    private final String betaKey;
    private final KeyInfo keyInfo;
    private final DBManager dbManager = BetaKey.instance.getDbManager();
    private boolean valid;

    public Key(String betaKey) {
        this.betaKey = betaKey;
        this.keyInfo = KeyInfo.getKeyInfo(betaKey);
    }

    public static String createRandomKey() {
        return RandomStringUtils.randomAlphanumeric(22);
    }

    public void create(String creator, boolean permanent) {
        if (!dbManager.isConnected()) System.err.println("Not connected to any DB");

        if (keyInfo != null) {
            System.err.println("BetaKey >> Key already exists");
            return;
        }


        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement("INSERT INTO betakey(betaKey, CREATOR, PERMANENT, USES) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, betaKey);
            preparedStatement.setString(2, creator);
            preparedStatement.setBoolean(3, permanent);
            preparedStatement.setInt(4, 0);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("BetaKey >> Key created");

    }

    public void remove() {
        if (!dbManager.isConnected()) System.err.println("Not connected to any DB");

        if (keyInfo == null) {
            System.err.println("BetaKey >> Key does not exists");
            return;
        }

        try (PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement("DELETE FROM betakey WHERE betaKey=?")) {

            preparedStatement.setString(1, betaKey);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("BetaKey >> Key removed");

    }

    public KeyInfo getKeyInfo() {
        return keyInfo;
    }

    public boolean isValid() {
        if (this.keyInfo != null) {
            this.valid = true;
        }
        return valid;
    }

    @Override
    public String toString() {
        return "Key{" +
                "betaKey='" + betaKey + '\'' +
                ", keyInfo=" + keyInfo +
                ", valid=" + valid +
                ", dbManager=" + dbManager +
                '}';
    }
}
