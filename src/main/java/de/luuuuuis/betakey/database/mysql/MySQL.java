/*
 * Developed by Luuuuuis on 04.05.19 18:22.
 * Last modified 04.05.19 18:15.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.database.mysql;

import de.luuuuuis.betakey.BetaKey;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQL {

    private BetaKey betaKey;

    public MySQL(BetaKey betaKey) {
        this.betaKey = betaKey;
    }

    public void init() {

        HashMap<String, Object> getMySQLCredentials = betaKey.getServerConfig().getMySQLCredentials();

        String url = "jdbc:mysql://" + getMySQLCredentials.get("Host").toString() + ":" + getMySQLCredentials.get("Port").toString() + "/"
                + getMySQLCredentials.get("database").toString() + "?autoReconnect=true&useUnicode=yes&amp;allowMultiQueries=true";
        try {

            Connection connection = DriverManager.getConnection(url, getMySQLCredentials.get("User").toString(), getMySQLCredentials.get("Password").toString());

            if (connection != null) {
                connection.setAutoCommit(true);
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println("BetaKey mysql >> Connected to " + metaData.getDatabaseProductName());

                betaKey.getDbManager().setConnection(connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
