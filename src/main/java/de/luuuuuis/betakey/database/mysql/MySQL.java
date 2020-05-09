/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 20:24.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.database.mysql;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.misc.Config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQL {

    private final BetaKey betaKey;

    public MySQL(BetaKey betaKey) {
        this.betaKey = betaKey;
    }

    public void init() {

        HashMap<String, Object> getMySQLCredentials = Config.getInstance().getMySQLCredentials();

        String url = "jdbc:mysql://" + getMySQLCredentials.get("Host").toString() + ":" + ((Double) getMySQLCredentials.get("Port")).intValue() + "/"
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
