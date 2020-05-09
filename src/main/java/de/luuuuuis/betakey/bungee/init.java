/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 19:31.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.bungee;

import de.luuuuuis.betakey.BetaKey;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class init extends Plugin {

    private static init instance;
    private BetaKey betaKey;

    public static init getInstance() {
        return instance;
    }

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

        betaKey = new BetaKey(getDataFolder());

        /*
         * commands
         */
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new de.luuuuuis.betakey.bungee.commands.BetaKeyCommand());
        pm.registerListener(this, new de.luuuuuis.betakey.bungee.listener.Login());

    }

    @Override
    public void onDisable() {
        super.onDisable();
        betaKey.getDbManager().close();
    }
}
