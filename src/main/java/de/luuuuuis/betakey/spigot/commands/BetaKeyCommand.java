/*
 *  Developed by Luuuuuis on 09.05.20, 20:35.
 *  Last modified 09.05.20, 19:32.
 *  Copyright (c) 2020.
 */

package de.luuuuuis.betakey.spigot.commands;

import de.luuuuuis.betakey.database.querys.BetaPlayer;
import de.luuuuuis.betakey.database.querys.Key;
import de.luuuuuis.betakey.misc.Config;
import de.luuuuuis.betakey.misc.MojangUUIDResolve;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BetaKeyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (strings.length != 2) {
            sender.sendMessage(Config.getInstance().getPrefix() + "/betakey padd [PLAYER]");
            sender.sendMessage(Config.getInstance().getPrefix() + "/betakey premove [PLAYER]");
            sender.sendMessage(Config.getInstance().getPrefix() + "/betakey kadd <true/false>");
            sender.sendMessage(Config.getInstance().getPrefix() + "/betakey kremove [KEY]");
            return false;
        }

        if (strings[0].equalsIgnoreCase("padd")) {
            BetaPlayer betaPlayer = new BetaPlayer(UUID.fromString(MojangUUIDResolve.getUUIDResult(strings[1]).getValue()));
            betaPlayer.create(null);

            sender.sendMessage(Config.getInstance().getPrefix() + "§aPlayer successfully added!");
        } else if (strings[0].equalsIgnoreCase("premove")) {
            BetaPlayer betaPlayer = new BetaPlayer(UUID.fromString(MojangUUIDResolve.getUUIDResult(strings[1]).getValue()));
            betaPlayer.remove();

            Player player = Bukkit.getPlayerExact(strings[1]);
            if (player != null)
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', Config.getInstance().getKickMessage()));

            sender.sendMessage(Config.getInstance().getPrefix() + "§cPlayer successfully removed!");
        } else if (strings[0].equalsIgnoreCase("kadd")) {
            String rndKey = Key.createRandomKey();
            Key key = new Key(rndKey);
            key.create(((sender instanceof Player) ? sender.getName() : "Console"), strings[1].equals("true"));

            TextComponent msg = new TextComponent(Config.getInstance().getPrefix() + "§aKey successfully added!\n§8> §6§l" + rndKey + " §7[Copy]");
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to copy the key!").create()));
            msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, rndKey));

            if (sender instanceof Player)
                ((Player) sender).spigot().sendMessage(msg);
            else
                sender.sendMessage(Config.getInstance().getPrefix() + "§aKey successfully added!\n§8> §6§l" + rndKey);

        } else if (strings[0].equalsIgnoreCase("kremove")) {
            String rndKey = strings[1];
            Key key = new Key(rndKey);
            key.remove();

            if (!key.isValid()) return false;

            sender.sendMessage(Config.getInstance().getPrefix() + "§cKey of §6§l" + key.getKeyInfo().getCreator() + " §cwith §6§l" + key.getKeyInfo().getUses() + " Uses §csuccessfully removed!");
        }

        return false;
    }
}
