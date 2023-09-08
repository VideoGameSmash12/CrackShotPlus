package com.shampaggon.crackshot;

import com.shampaggon.crackshot.object.Weapon;
import com.shampaggon.crackshot.util.CSPMessages;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CSCommand implements TabExecutor
{
    private final String header = "<gray>░ <red>[-<b>¬</b>º<b>c<gray>s</gray></b><gray>] <red>-</red> ";
    private final CSDirector plugin;

    protected CSCommand(CSDirector plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (args.length == 0 || args[0].equalsIgnoreCase("help"))
        {
            /*sender.sendMessage("§7░ §c§l------§r§c[ -§l¬§cºcrack§7shot §c]§l------");
            sender.sendMessage("§7░ §cAuthors: §7Shampaggon, videogamesm12");
            sender.sendMessage("§7░ §cVersion: §7" + Bukkit.getPluginManager().getPlugin("CrackShot").getDescription().getVersion());
            sender.sendMessage("§7░ §cAliases: §7/shot, /cra, /cs");
            sender.sendMessage("§7░ §cCommands:");
            sender.sendMessage("§7░ §c- §7/shot list");
            sender.sendMessage("§7░ §c- §7/shot give <user> <weapon> <#>");
            sender.sendMessage("§7░ §c- §7/shot get <weapon> <#>");
            sender.sendMessage("§7░ §c- §7/shot reload");
            sender.sendMessage("§7░ §c- §7/shot config reload");*/

            final PluginDescriptionFile descriptionFile = plugin.getDescription();

            // These sections cannot be configurable.
            sender.sendRichMessage("<gray>░ <red><b>------</b>[ -<b>¬</b><red>ºcrack</red><gray>shot</gray><dark_gray>+<dark_gray> <red>]<bold>------");
            sender.sendRichMessage("<gray>░ <red>Authors: </red>" + String.join(", ", descriptionFile.getAuthors()));
            sender.sendRichMessage("<gray>░ <red>Version: </red>" + descriptionFile.getVersion());
            sender.sendRichMessage("<gray>░ <red>Aliases: </red>" + String.join(", ", command.getAliases().stream().map(alias -> "/" + alias).toList()));
            sender.sendRichMessage("<gray>░ <red>Commands:</red>");
            sender.sendRichMessage("<gray>░ <red>- </red>/shot help");
            sender.sendRichMessage("<gray>░ <red>- </red>/shot config stats");
            sender.sendRichMessage("<gray>░ <red>- </red>/shot config reload");

            return true;
        }
        else if (args.length == 1)
        {
            switch (args[0].toLowerCase())
            {
                case "list" ->
                {
                    if (!sender.hasPermission("crackshot.list"))
                    {
                        CSPMessages.sendMessage(sender, "command.permission.no_permission");
                        return true;
                    }
                }
                case "reload" ->
                {
                    if (!(sender instanceof Player))
                    {
                        CSPMessages.sendMessage(sender, "command.permission.in_game_only");
                        return true;
                    }

                    // TODO: OH JESUS CHRIST THIS IS AWFUL PLEASE FOR THE LOVE OF CHRIST REWRITE HOW GUNS ARE HANDLED
                    //  BEFORE I BURN DOWN AN ORPHANAGE
                    final String weapon = plugin.returnParentNode((Player) sender);

                    if (weapon == null)
                    {
                        CSPMessages.sendMessage(sender, "command.reload_disabled");
                        return true;
                    }
                    Weapon.builder().itemInformation()

                    if (!sender.hasPermission("crackshot.use." + weapon) && !sender.hasPermission("crackshot.use.all"))
                    {
                        CSPMessages.sendMessage(sender, "weapon.permission.use");
                        return true;
                    }

                    plugin.reloadAnimation((Player) sender, weapon);
                }
            }
        }
        else if (args.length == 2)
        {
            switch (args[0].toLowerCase())
            {
                case "get" ->
                {
                    if (!(sender instanceof Player))
                    {
                        CSPMessages.sendMessage(sender, "command.permission.in_game_only");
                        return true;
                    }

                    final String weaponName = args[1].toUpperCase();

                    // TODO: Implement
                    sender.sendRichMessage(header + "You requested the weapon " + weaponName + ", and you shall receive it later");
                    return true;
                }
                case "config" ->
                {
                    switch (args[1].toLowerCase())
                    {
                        case "reload" ->
                        {
                            if (!sender.hasPermission("crackshot.reloadplugin"))
                            {
                                CSPMessages.sendMessage(sender, "command.permission.no_permission");
                                return true;
                            }

                            // TODO: Add reloading code here
                            CSPMessages.loadMessages();
                            //--
                            CSPMessages.sendMessage(sender, "command.configuration_reloaded");
                            return true;
                        }
                        case "stats" ->
                        {
                            CSPMessages.sendMessage(sender, "command.configuration_stats",
                                    Placeholder.unparsed("weapons", "42,069"),
                                    Placeholder.unparsed("messages", String.valueOf(CSPMessages.getMessageCount())));
                            return true;
                        }
                    }
                }
            }
        }

        CSPMessages.sendMessage(sender, "command.invalid", Placeholder.unparsed("alias", label));
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        final List<String> response = new ArrayList<>();

        if (args.length <= 1)
        {
            if (sender.hasPermission("crackshot.reloadplugin")) response.add("config");
            if (sender.hasPermission("crackshot.list")) response.add("list");
        }
        else
        {
            if (args[0].equalsIgnoreCase("config") && sender.hasPermission("crackshot.reloadplugin")) response.add("reload");
        }

        return response;
    }
}
