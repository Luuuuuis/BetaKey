/*
 * Developed by Luuuuuis on 23.04.19 18:25.
 * Last modified 23.04.19 18:23.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.database.querys;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.exceptions.NoActiveDBException;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Key {

    private String BETAKEY;
    private KeyInfo keyInfo;
    private boolean valid;

    public Key(String BETAKEY) {
        this.BETAKEY = BETAKEY;
        this.keyInfo = KeyInfo.getKeyInfo(BETAKEY);
    }

    public static String createRandomKey() {
        return RandomStringUtils.randomAlphanumeric(22);
    }

    public void create(String creator, boolean permanent) {
        if (!BetaKey.getInstance().getDbManager().isConnected())
            throw new NoActiveDBException("Not connected to any DB");
        if (keyInfo != null) {
            System.err.println("BetaKey >> Key already exists");
            return;
        }


        try (PreparedStatement preparedStatement = BetaKey.getInstance().getDbManager().getConnection().prepareStatement("INSERT INTO betakey(BETAKEY, CREATOR, PERMANENT, USES) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, BETAKEY);
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
        if (!BetaKey.getInstance().getDbManager().isConnected()) throw new NoActiveDBException("BetaKey >> Not connected to any DB");
        if (keyInfo == null) {
            System.err.println("BetaKey >> Key does not exists");
            return;
        }

        try (PreparedStatement preparedStatement = BetaKey.getInstance().getDbManager().getConnection().prepareStatement("DELETE FROM betakey WHERE BETAKEY=?")) {

            preparedStatement.setString(1, BETAKEY);

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
}
