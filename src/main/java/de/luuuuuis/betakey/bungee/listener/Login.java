/*
 *  Developed by Luuuuuis.
 *  Last modified 23.04.21, 23:31.
 *  Copyright (c) 2021.
 */

package de.luuuuuis.betakey.bungee.listener;

import de.luuuuuis.betakey.database.querys.BetaPlayer;
import de.luuuuuis.betakey.misc.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Login implements Listener {

    @EventHandler
    public void onLogin(LoginEvent e) {
        e.registerIntent(de.luuuuuis.betakey.bungee.init.getInstance());
        PendingConnection player = e.getConnection();
        BetaPlayer betaPlayer = new BetaPlayer(player.getUniqueId());

        if (!betaPlayer.isValid()) {
            //noinspection deprecation to support cross version
            e.setCancelReason(ChatColor.translateAlternateColorCodes('&', Config.getInstance().getKickMessage()));
            e.setCancelled(true);
            System.out.println("BetaKey DEBUG >> Not valid!");
        } else
            System.out.println("BetaKey DEBUG >> Valid!");

        e.completeIntent(de.luuuuuis.betakey.bungee.init.getInstance());

    }

}
