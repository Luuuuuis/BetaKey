/*
 * Developed by Luuuuuis on 23.04.19 18:25.
 * Last modified 23.04.19 18:20.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.database.querys;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.exceptions.NoActiveDBException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class BetaPlayer {

    private String uuid;
    private BetaPlayerInfo betaPlayerInfo;
    private boolean valid;

    public BetaPlayer(UUID uuid) {
        this.uuid = uuid.toString().replace("-", "");
        this.betaPlayerInfo = BetaPlayerInfo.getPlayerInfo(uuid.toString().replace("-", ""));
    }


    public void create(String betakey) {
        if (!BetaKey.getInstance().getDbManager().isConnected()) throw new NoActiveDBException("Not connected to any DB");
        if (betaPlayerInfo != null) {
            System.err.println("BetaKey >> Player already exists");
            return;
        }

        try (PreparedStatement preparedStatement = BetaKey.getInstance().getDbManager().getConnection().prepareStatement("INSERT INTO betaplayer(UUID, BETAKEY) VALUES (?, ?)")) {

            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, betakey);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("BetaKey >> Player created");

    }

    public void remove() {
        if (!BetaKey.getInstance().getDbManager().isConnected()) throw new NoActiveDBException("BetaKey >> Not connected to any DB");
        if (betaPlayerInfo == null) {
            System.err.println("BetaKey >> Player does not exists");
            return;
        }

        try (PreparedStatement preparedStatement = BetaKey.getInstance().getDbManager().getConnection().prepareStatement("DELETE FROM betaplayer WHERE UUID=?")) {

            preparedStatement.setString(1, uuid);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("BetaKey >> Player removed");

    }

    public BetaPlayerInfo getBetaPlayerInfo() {
        return betaPlayerInfo;
    }

    public boolean isValid() {
        if(this.betaPlayerInfo != null) {
            this.valid = true;
        }
        return valid;
    }
}
