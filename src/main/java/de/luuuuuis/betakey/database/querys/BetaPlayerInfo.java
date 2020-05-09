/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 19:30.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.database.querys;

import de.luuuuuis.betakey.BetaKey;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BetaPlayerInfo {

    private final String betaKey;

    private BetaPlayerInfo(ResultSet rs) throws SQLException {
        betaKey = rs.getString("BETAKEY");
    }

    public static BetaPlayerInfo getPlayerInfo(String uuid) {

        try (ResultSet rs = BetaKey.instance.getDbManager().getResult("SELECT * FROM betaplayer WHERE UUID='" + uuid + "'")) {
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
