/*
 * Developed by Luuuuuis on 04.05.19 18:00.
 * Last modified 04.05.19 17:34.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey;

import de.luuuuuis.betakey.commands.BetaKeyCommand;
import de.luuuuuis.betakey.database.DBManager;
import de.luuuuuis.betakey.listener.Login;
import de.luuuuuis.betakey.misc.ServerConfig;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BetaKey extends Plugin {

    private ServerConfig serverConfig;
    private DBManager dbManager;
    private static BetaKey instance;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        System.out.println("You are using\n" +
                " ______     ______     ______   ______     __  __     ______     __  __    \n" +
                "/\\  == \\   /\\  ___\\   /\\__  _\\ /\\  __ \\   /\\ \\/ /    /\\  ___\\   /\\ \\_\\ \\   \n" +
                "\\ \\  __<   \\ \\  __\\   \\/_/\\ \\/ \\ \\  __ \\  \\ \\  _\"-.  \\ \\  __\\   \\ \\____ \\  \n" +
                " \\ \\_____\\  \\ \\_____\\    \\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_____\\  \\/\\_____\\ \n" +
                "  \\/_____/   \\/_____/     \\/_/   \\/_/\\/_/   \\/_/\\/_/   \\/_____/   \\/_____/ \n\n\n" +
                "Version: " + getDescription().getVersion() + "\n" +
                "Support: https://discord.gg/2aSSGcz\n" +
                "GitHub: https://github.com/Luuuuuis/BetaKey"
        );


        /*
         * Config
         */
        serverConfig = new ServerConfig(this);


        /*
         * database Manager
         */
        dbManager = new DBManager(this);
        dbManager.connect();

        /*
         * commands
         */
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new BetaKeyCommand(this));
        pm.registerListener(this, new Login(this));

        //Test.test();

    }

    @Override
    public void onDisable() {
        super.onDisable();
        dbManager.close();
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public static BetaKey getInstance() {
        return instance;
    }
}
