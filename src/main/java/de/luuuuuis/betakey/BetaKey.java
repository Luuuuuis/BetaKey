/*
 *  Developed by Luuuuuis on 23.06.20, 14:09.
 *  Last modified 23.06.20, 12:46.
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
    private final File dataFolder;


    public BetaKey(File dataFolder) {
        instance = this;
        this.dataFolder = dataFolder;
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

    public File getDataFolder() {
        return dataFolder;
    }
}
