/*
 * Developed by Luuuuuis on 23.04.19 18:35.
 * Last modified 23.04.19 18:35.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey;

import de.luuuuuis.betakey.database.querys.BetaPlayer;
import de.luuuuuis.betakey.database.querys.Key;

import java.util.UUID;

public class Test {

    public static void test() {
        Key key = new Key("123456789");
        System.out.println("Key isvalid " + key.isValid());
        if(key.isValid()) {
            System.out.println("Key Owner " + key.getKeyInfo().getCreator());
            System.out.println("Key Uses " + key.getKeyInfo().getUses());
            System.out.println("Key Permanent " + key.getKeyInfo().isPermanent());
        }
        key.create("Luis", true);



        UUID uuid = UUID.fromString("39e307f5-a448-4384-bd03-8c66ac84f92c");
        System.out.println("Player UUID " + uuid.toString());

        BetaPlayer betaPlayer = new BetaPlayer(uuid);
        if(betaPlayer.isValid()) {
            System.out.println("Player Key " + betaPlayer.getBetaPlayerInfo().getBetaKey());
        }
        betaPlayer.create("123456789");

    }
}
