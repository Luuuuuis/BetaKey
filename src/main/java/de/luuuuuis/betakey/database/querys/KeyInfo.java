/*
 * Developed by Luuuuuis on 23.04.19 18:25.
 * Last modified 23.04.19 18:23.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.database.querys;

import de.luuuuuis.betakey.BetaKey;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyInfo {

    private String creator;
    private boolean permanent;
    private int uses;

    private KeyInfo(ResultSet rs) throws SQLException {
        this.creator = rs.getString("CREATOR");
        this.permanent = rs.getBoolean("PERMANENT");
        this.uses = rs.getInt("USES");
    }

    static KeyInfo getKeyInfo(String BETAKEY) {

        try (ResultSet rs = BetaKey.getInstance().getDbManager().getResult("SELECT * FROM betakey WHERE BETAKEY='" + BETAKEY + "'")) {
            if (rs.next()) {
                return new KeyInfo(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getCreator() {
        return creator;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public int getUses() {
        return uses;
    }
}
