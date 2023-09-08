package com.shampaggon.crackshot.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shampaggon.crackshot.CSDirector;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class CSPMessages
{
    private static final Gson gson = new Gson();
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    private static final Map<String, String> messages = new HashMap<>();

    public static void loadMessages()
    {
        unloadMessages();

        final File file = new File(CSDirector.INSTANCE.getDataFolder(), "messages.json");

        if (!file.exists())
        {
            try
            {
                Files.copy(CSDirector.INSTANCE.getResource("messages.json"), file.toPath());
            }
            catch (Exception ex)
            {
                CSDirector.INSTANCE.getSLF4JLogger().error("BRUH MOMENT", ex);
            }
        }

        try
        {
            final Map<String, String> nevv = gson.fromJson(Files.newBufferedReader(file.toPath(),StandardCharsets.UTF_8),
                    new TypeToken<Map<String, String>>() {}.getType());

            messages.putAll(nevv);
        }
        catch (Exception ex)
        {
            CSDirector.INSTANCE.getSLF4JLogger().error("Failed to read messages file", ex);
        }
    }

    public static void unloadMessages()
    {
        messages.clear();
    }

    public static void sendMessage(CommandSender sender, String key, TagResolver... placeholders)
    {
        sender.sendMessage(getPrefix().append(miniMessage.deserialize(messages.getOrDefault(key, key), placeholders)));
    }

    public static int getMessageCount()
    {
        return messages.size();
    }

    public static Component getPrefix()
    {
        return miniMessage.deserialize(messages.getOrDefault("prefix", "<gray>░ <red>[-<b>¬</b>º<b>c<gray>s</gray></b><gray>] <red>-</red> "));
    }
}
