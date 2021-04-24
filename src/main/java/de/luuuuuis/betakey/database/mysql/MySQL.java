/*
 *  Developed by Luuuuuis.
 *  Last modified 23.06.20, 14:09.
 *  Copyright (c) 2021.
 */

package de.luuuuuis.betakey.database.mysql;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.misc.Config;

import java.util.HashMap;

public class MySQL {

    private final BetaKey betaKey;

    public MySQL(BetaKey betaKey) {
        this.betaKey = betaKey;
    }

    public void init() {

        HashMap<String, Object> getMySQLCredentials = Config.getInstance().getMySQLCredentials();

        String url = "jdbc:mysql://" + getMySQLCredentials.get("Host").toString() + ":" + ((Double) getMySQLCredentials.get("Port")).intValue() + "/"
                + getMySQLCredentials.get("database").toString() + "?autoReconnect=true&useUnicode=yes&allowMultiQueries=true";

        betaKey.getDbManager().connect(getMySQLCredentials, url);
    }

}
