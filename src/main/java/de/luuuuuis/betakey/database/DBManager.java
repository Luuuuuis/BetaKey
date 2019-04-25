/*
 * Developed by Luuuuuis on 23.04.19 17:22.
 * Last modified 23.04.19 17:15.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.database;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.database.mysql.MySQL;
import de.luuuuuis.betakey.exceptions.NoActiveDBException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private BetaKey betaKey;
    private Connection connection;

    public DBManager(BetaKey betaKey) {
        this.betaKey = betaKey;
    }

    public void connect() {
        if (isConnected()) return;

        if (betaKey.getServerConfig().getMySQLCredentials().get("active").equals(true)) {

            new MySQL(betaKey).init();

        } else {
            throw new NoActiveDBException("No active DB was found");
        }

        // Create Tables
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS betakey(BETAKEY VARCHAR(22), CREATOR VARCHAR(16), PERMANENT BOOLEAN, USES INT); " +
                    "CREATE TABLE IF NOT EXISTS betaplayer(UUID VARCHAR(36), BETAKEY VARCHAR(22)); " +
                    "CREATE UNIQUE INDEX IF NOT EXISTS uuid_bk ON betaplayer (UUID);"); // Set unique index
            System.out.println("BetaKey SQL >> Successfully created all tables");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        if (!isConnected()) return;
        try {
            connection.close();
            System.out.println("BetaKey SQL >> Successfully closed the connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(String query) {
        if (!isConnected()) return null;

        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
