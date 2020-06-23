/*
 *  Developed by Luuuuuis on 23.06.20, 14:09.
 *  Last modified 23.06.20, 13:46.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.database.sqlite;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.misc.Config;

import java.util.HashMap;

public class SQLite {

    private final BetaKey betaKey;

    public SQLite(BetaKey betaKey) {
        this.betaKey = betaKey;
    }

    public void init() {

        HashMap<String, Object> getMySQLCredentials = Config.getInstance().getMySQLCredentials();

        String url = "jdbc:sqlite:" + betaKey.getDataFolder().getAbsolutePath() + "/betakey.sqlite";

        try {
            // use JDBC driver for SQLite
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        betaKey.getDbManager().connect(getMySQLCredentials, url);
    }

}
