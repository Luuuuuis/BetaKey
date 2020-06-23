/*
 *  Developed by Luuuuuis on 23.04.21, 23:31.
 *  Last modified 23.04.21, 21:39.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.spigot;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.database.querys.BetaPlayerInfo;
import de.luuuuuis.betakey.database.querys.KeyInfo;
import de.luuuuuis.betakey.misc.IPlugin;
import de.luuuuuis.betakey.spigot.misc.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class init extends JavaPlugin implements IPlugin {

    private BetaKey betaKey;

    @Override
    public void onEnable() {
        super.onEnable();

        betaKey = new BetaKey(this);

        /*
         * commands
         */
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new de.luuuuuis.betakey.spigot.listener.Login(), this);

        getCommand("betakey").setExecutor(new de.luuuuuis.betakey.spigot.commands.BetaKeyCommand());

        /*
        Available here: https://bstats.org/plugin/bungeecord/BetaKey/11124
        to disable these metrics change the bStats config and copy it into you template folder but please don't :C
         */
        Metrics metrics = new Metrics(this, 11125);
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
        getServer().getConsoleSender().sendMessage(message);
    }

}
