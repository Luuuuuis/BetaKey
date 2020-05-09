/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 19:31.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.bungee.commands;

import de.luuuuuis.betakey.database.querys.BetaPlayer;
import de.luuuuuis.betakey.database.querys.Key;
import de.luuuuuis.betakey.misc.Config;
import de.luuuuuis.betakey.misc.MojangUUIDResolve;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class BetaKeyCommand extends Command {

    public BetaKeyCommand() {
        super("BetaKey", "betakey.command", "bk", "beta");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (strings.length != 2) {
            sender.sendMessage(Config.getInstance().getPrefix() + "/betakey padd [PLAYER]");
            sender.sendMessage(Config.getInstance().getPrefix() + "/betakey premove [PLAYER]");
            sender.sendMessage(Config.getInstance().getPrefix() + "/betakey kadd <true/false>");
            sender.sendMessage(Config.getInstance().getPrefix() + "/betakey kremove [KEY]");
            return;
        }

        if (strings[0].equalsIgnoreCase("padd")) {
            BetaPlayer betaPlayer = new BetaPlayer(UUID.fromString(MojangUUIDResolve.getUUIDResult(strings[1]).getValue()));
            betaPlayer.create(null);

            sender.sendMessage(Config.getInstance().getPrefix() + "§aPlayer successfully added!");
        } else if (strings[0].equalsIgnoreCase("premove")) {
            BetaPlayer betaPlayer = new BetaPlayer(UUID.fromString(MojangUUIDResolve.getUUIDResult(strings[1]).getValue()));
            betaPlayer.remove();

            // kick player if online
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(strings[1]);
            if (player != null)
                player.disconnect(ChatColor.translateAlternateColorCodes('&', Config.getInstance().getKickMessage()));

            sender.sendMessage(Config.getInstance().getPrefix() + "§cPlayer successfully removed!");
        } else if (strings[0].equalsIgnoreCase("kadd")) {
            String rndKey = Key.createRandomKey();
            Key key = new Key(rndKey);
            key.create(((sender instanceof ProxiedPlayer) ? sender.getName() : "Console"), strings[1].equals("true"));

            TextComponent msg = new TextComponent(Config.getInstance().getPrefix() + "§aKey successfully added!\n§8> §6§l" + rndKey + " §7[Copy]");
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to copy the key!").create()));
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rndKey));

            sender.sendMessage(msg);
        } else if (strings[0].equalsIgnoreCase("kremove")) {
            String rndKey = strings[1];
            Key key = new Key(rndKey);
            key.remove();

            if (!key.isValid()) return;

            sender.sendMessage(Config.getInstance().getPrefix() + "§cKey of §6§l" + key.getKeyInfo().getCreator() + " §cwith §6§l" + key.getKeyInfo().getUses() + " Uses §csuccessfully removed!");
        }


    }
}
