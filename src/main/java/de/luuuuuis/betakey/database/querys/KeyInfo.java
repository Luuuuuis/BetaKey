/*
 *  Developed by Luuuuuis.
 *  Last modified 23.04.21, 23:31.
 *  Copyright (c) 2021.
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

    public static int count() {
        try (ResultSet rs = BetaKey.instance.getDbManager().getResult("SELECT COUNT(*) FROM betakey")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
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
