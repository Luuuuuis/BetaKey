/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 20:35.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.luuuuuis.betakey.database.DBManager;
import de.luuuuuis.betakey.misc.Config;

import java.io.File;

public class BetaKey {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static BetaKey instance = null;
    private final DBManager dbManager;


    public BetaKey(File dataFolder) {
        instance = this;
        /*
         * config
         */
        Config.init(dataFolder);


        /*
         * database Manager
         */
        dbManager = new DBManager(this);
        dbManager.connect();


    }

    public DBManager getDbManager() {
        return dbManager;
    }
}
