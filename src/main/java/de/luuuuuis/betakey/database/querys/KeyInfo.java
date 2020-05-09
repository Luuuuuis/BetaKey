/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 20:35.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.database.querys;

import de.luuuuuis.betakey.BetaKey;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyInfo {

    private final String creator;
    private final boolean permanent;
    private final int uses;

    private KeyInfo(ResultSet rs) throws SQLException {
        this.creator = rs.getString("CREATOR");
        this.permanent = rs.getBoolean("PERMANENT");
        this.uses = rs.getInt("USES");
    }

    static KeyInfo getKeyInfo(String BETAKEY) {

        try (ResultSet rs = BetaKey.instance.getDbManager().getResult("SELECT * FROM betakey WHERE BETAKEY='" + BETAKEY + "'")) {
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

    @Override
    public String toString() {
        return "KeyInfo{" +
                "creator='" + creator + '\'' +
                ", permanent=" + permanent +
                ", uses=" + uses +
                '}';
    }
}
