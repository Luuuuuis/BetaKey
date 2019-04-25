/*
 * Developed by Luuuuuis on 23.04.19 20:03.
 * Last modified 23.04.19 20:03.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.listener;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.database.querys.BetaPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Login implements Listener {

    private BetaKey betaKey;

    public Login(BetaKey betaKey) {
        this.betaKey = betaKey;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onLogin(LoginEvent e) {
        e.registerIntent(betaKey);
        PendingConnection player = e.getConnection();
        BetaPlayer betaPlayer = new BetaPlayer(player.getUniqueId());

        if (!betaPlayer.isValid()) {
            e.setCancelReason(ChatColor.translateAlternateColorCodes('&', betaKey.getServerConfig().getKickmessage()));
            e.setCancelled(true);
            System.out.println("BetaKey DEBUG >> Not valid!");
        } else
            System.out.println("BetaKey DEBUG >> Valid!");

        e.completeIntent(betaKey);

    }
}
