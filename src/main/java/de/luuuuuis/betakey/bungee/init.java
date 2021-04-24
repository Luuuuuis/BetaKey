/*
 *  Developed by Luuuuuis.
 *  Last modified 23.04.21, 23:31.
 *  Copyright (c) 2021.
 */

package de.luuuuuis.betakey.bungee;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.bungee.misc.Metrics;
import de.luuuuuis.betakey.database.querys.BetaPlayerInfo;
import de.luuuuuis.betakey.database.querys.KeyInfo;
import de.luuuuuis.betakey.misc.IPlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.File;

public class init extends Plugin implements IPlugin {

    private static init instance;
    private BetaKey betaKey;

    public static init getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        betaKey = new BetaKey(this);

        /*
         * commands
         */
        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new de.luuuuuis.betakey.bungee.commands.BetaKeyCommand());
        pm.registerListener(this, new de.luuuuuis.betakey.bungee.listener.Login());

        /*
        bStats Metrics https://github.com/Bastian/bStats-Metrics/blob/master/bstats-bungeecord/src/examples/java/ExamplePlugin.java
        Available here: https://bstats.org/plugin/bungeecord/BetaKey/11124
        to disable these metrics change the bStats config and copy it into you template folder but please don't :C
         */
        Metrics metrics = new Metrics(this, 11124);
        metrics.addCustomChart(new Metrics.SingleLineChart("betaplayers_active", BetaPlayerInfo::count));
        metrics.addCustomChart(new Metrics.SingleLineChart("betakeys_active", KeyInfo::count));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        betaKey.getDbManager().close();
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public File getFolder() {
        return getDataFolder();
    }

    @Override
    public void sendMessageConsole(String message) {
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(message));
    }
}
