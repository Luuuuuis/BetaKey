/*
 *  Developed by Luuuuuis on 23.06.20, 14:09.
 *  Last modified 23.06.20, 14:05.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.spigot;

import de.luuuuuis.betakey.BetaKey;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class init extends JavaPlugin {

    private BetaKey betaKey;

    @Override
    public void onEnable() {
        super.onEnable();

        System.out.println("You are using\n" +
                " ______     ______     ______   ______     __  __     ______     __  __    \n" +
                "/\\  == \\   /\\  ___\\   /\\__  _\\ /\\  __ \\   /\\ \\/ /    /\\  ___\\   /\\ \\_\\ \\   \n" +
                "\\ \\  __<   \\ \\  __\\   \\/_/\\ \\/ \\ \\  __ \\  \\ \\  _\"-.  \\ \\  __\\   \\ \\____ \\  \n" +
                " \\ \\_____\\  \\ \\_____\\    \\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_____\\  \\/\\_____\\ \n" +
                "  \\/_____/   \\/_____/     \\/_/   \\/_/\\/_/   \\/_/\\/_/   \\/_____/   \\/_____/ \n" +
                "   by Luuuuuis (@realluuuuuis)\n\n" +
                "Version: " + getDescription().getVersion() + "\n" +
                "Support: https://discord.gg/2aSSGcz\n" +
                "GitHub: https://github.com/Luuuuuis/BetaKey"
        );

        betaKey = new BetaKey(getDataFolder());

        /*
         * commands
         */
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new de.luuuuuis.betakey.spigot.listener.Login(), this);

        getCommand("betakey").setExecutor(new de.luuuuuis.betakey.spigot.commands.BetaKeyCommand());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        betaKey.getDbManager().close();
    }

}
