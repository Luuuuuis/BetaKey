/*
 *  Developed by Luuuuuis on 23.04.21, 23:31.
 *  Last modified 23.04.21, 15:05.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.database;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.database.mysql.MySQL;
import de.luuuuuis.betakey.database.sqlite.SQLite;
import de.luuuuuis.betakey.misc.Config;

import java.sql.*;
import java.util.HashMap;

public class DBManager {

    private final BetaKey betaKey;
    private Connection connection;

    public DBManager(BetaKey betaKey) {
        this.betaKey = betaKey;
    }

    public void connect() {
        if (isConnected()) return;

        // choose between MySQL or SQLite
        if ((boolean) Config.getInstance().getMySQLCredentials().get("use")) {
            //init connection to mysql
            new MySQL(betaKey).init();
        } else {
            new SQLite(betaKey).init();
        }

        // Create Tables
        try (Statement statement = connection.createStatement()) {

            String batch1 = "CREATE TABLE IF NOT EXISTS betakey(BETAKEY VARCHAR(22), CREATOR VARCHAR(16), PERMANENT BOOLEAN, USES INT)";
            statement.addBatch(batch1);
            String batch2 = "CREATE TABLE IF NOT EXISTS betaplayer(UUID VARCHAR(36), BETAKEY VARCHAR(22))";
            statement.addBatch(batch2);
            String batch3 = "CREATE UNIQUE INDEX IF NOT EXISTS uuid_bk ON betaplayer (UUID)"; // Set unique index
            statement.addBatch(batch3);

            statement.executeBatch();
            System.out.println("BetaKey SQL >> Successfully created all tables!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect(HashMap<String, Object> getMySQLCredentials, String url) {
        try {
            Connection connection = DriverManager.getConnection(url, getMySQLCredentials.get("User").toString(), getMySQLCredentials.get("Password").toString());

            if (connection != null) {
                connection.setAutoCommit(true);
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println("BetaKey SQL >> Connected to " + metaData.getDatabaseProductName());

                setConnection(connection);
            }

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
