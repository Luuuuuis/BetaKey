/*
 * Developed by Luuuuuis on 23.04.19 18:25.
 * Last modified 23.04.19 18:08.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.database.querys;

import de.luuuuuis.betakey.BetaKey;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetaPlayerInfo {

    private String betaKey;

    private BetaPlayerInfo(ResultSet rs) throws SQLException {
        betaKey = rs.getString("BETAKEY");
    }

    public static BetaPlayerInfo getPlayerInfo(String uuid) {

        try (ResultSet rs = BetaKey.getInstance().getDbManager().getResult("SELECT * FROM betaplayer WHERE UUID='" + uuid + "'")) {
            if (rs.next()) {
                return new BetaPlayerInfo(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public String getBetaKey() {
        return betaKey;
    }
}
