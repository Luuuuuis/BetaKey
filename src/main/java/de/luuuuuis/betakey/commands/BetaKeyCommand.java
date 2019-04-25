/*
 * Developed by Luuuuuis on 23.04.19 20:53.
 * Last modified 23.04.19 20:53.
 * Copyright (c) 2019.
 */

package de.luuuuuis.betakey.commands;

import de.luuuuuis.betakey.BetaKey;
import de.luuuuuis.betakey.database.querys.BetaPlayer;
import de.luuuuuis.betakey.database.querys.Key;
import de.luuuuuis.betakey.misc.MojangUUIDResolve;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class BetaKeyCommand extends Command {

    private BetaKey betaKey;

    public BetaKeyCommand(BetaKey betaKey) {
        super("BetaKey", "betakey.command", "bk");
        this.betaKey = betaKey;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (strings.length != 2) {
            sender.sendMessage(betaKey.getServerConfig().getPrefix() + "/betakey padd [PLAYER]");
            sender.sendMessage(betaKey.getServerConfig().getPrefix() + "/betakey premove [PLAYER]");
            sender.sendMessage(betaKey.getServerConfig().getPrefix() + "/betakey kadd <true/false>");
            sender.sendMessage(betaKey.getServerConfig().getPrefix() + "/betakey kremove [KEY]");
            return;
        }

        if (strings[0].equalsIgnoreCase("padd")) {
            BetaPlayer betaPlayer = new BetaPlayer(UUID.fromString(MojangUUIDResolve.getUUIDResult(strings[1]).getValue()));
            betaPlayer.create(null);

            sender.sendMessage(betaKey.getServerConfig().getPrefix() + "§aPlayer successfully added!");
        } else if (strings[0].equalsIgnoreCase("premove")) {
            BetaPlayer betaPlayer = new BetaPlayer(UUID.fromString(MojangUUIDResolve.getUUIDResult(strings[1]).getValue()));
            betaPlayer.remove();

            sender.sendMessage(betaKey.getServerConfig().getPrefix() + "§cPlayer successfully removed!");
        } else if (strings[0].equalsIgnoreCase("kadd")) {
            String rndKey = Key.createRandomKey();
            Key key = new Key(rndKey);
            key.create(((sender instanceof ProxiedPlayer) ? sender.getName() : "Console"), strings[1].equals("true"));

            TextComponent msg = new TextComponent(betaKey.getServerConfig().getPrefix() + "§aKey successfully added!\n§8> §6§l" + rndKey + " §7[Copy]");
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to copy the key!").create()));
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rndKey));

            sender.sendMessage(msg);
        } else if(strings[0].equalsIgnoreCase("kremove")) {
            String rndKey = strings[1];
            Key key = new Key(rndKey);
            key.remove();

            if(!key.isValid()) return;

            sender.sendMessage(betaKey.getServerConfig().getPrefix() + "§cKey of §6§l" + key.getKeyInfo().getCreator() + " §cwith §6§l" + key.getKeyInfo().getUses() + " Uses §csuccessfully removed!");
        }


    }
}
