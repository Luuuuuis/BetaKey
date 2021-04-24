/*
 *  Developed by Luuuuuis.
 *  Last modified 23.04.21, 23:31.
 *  Copyright (c) 2021.
 */

package de.luuuuuis.betakey;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.luuuuuis.betakey.database.DBManager;
import de.luuuuuis.betakey.misc.Config;
import de.luuuuuis.betakey.misc.IPlugin;
import de.luuuuuis.betakey.misc.Updater;
import lombok.Getter;

@Getter
public class BetaKey {

    public final static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static BetaKey instance = null;
    private final DBManager dbManager;
    private final IPlugin iPlugin;

    public BetaKey(IPlugin iPlugin) {
        instance = this;
        this.iPlugin = iPlugin;

        /*
         * Updater
         */
        Updater updater = new Updater();

        iPlugin.sendMessageConsole("§9\n" +
                " ______     ______     ______   ______     __  __     ______     __  __    \n" +
                "/\\  == \\   /\\  ___\\   /\\__  _\\ /\\  __ \\   /\\ \\/ /    /\\  ___\\   /\\ \\_\\ \\   \n" +
                "\\ \\  __<   \\ \\  __\\   \\/_/\\ \\/ \\ \\  __ \\  \\ \\  _\"-.  \\ \\  __\\   \\ \\____ \\  \n" +
                " \\ \\_____\\  \\ \\_____\\    \\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_____\\  \\/\\_____\\ \n" +
                "  \\/_____/   \\/_____/     \\/_/   \\/_/\\/_/   \\/_/\\/_/   \\/_____/   \\/_____/ \n" +
                "   by Luuuuuis (@realluuuuuis)§r\n\n" +
                "Version: " + iPlugin.getVersion() + (updater.isNew() ? "§c* outdated" : "") + "§r\n" +
                "Support: https://discord.gg/2aSSGcz\n" +
                "GitHub: https://github.com/Luuuuuis/BetaKey"
        );

        /*
         * config
         */
        Config.init(iPlugin.getFolder());

        /*
         * database Manager
         */
        dbManager = new DBManager(this);
        dbManager.connect();
    }
}
