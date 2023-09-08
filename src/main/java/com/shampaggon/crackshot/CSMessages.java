package com.shampaggon.crackshot;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class CSMessages
{
    public static Map<String, String> messages = new HashMap<>();

    public enum Message
    {
        NP_WEAPON_USE("NP_Weapon_Use"),
        NP_WEAPON_CRAFT("NP_Weapon_Craft"),
        NP_STORE_CREATE("NP_Store_Create"),
        NP_STORE_PURCHASE("NP_Store_Purchase"),

        STORE_CREATED("Store_Created"),
        STORE_CANNOT_AFFORD("Store_Cannot_Afford"),
        STORE_ITEMS_NEEDED("Store_Items_Needed"),
        STORE_PURCHASED("Store_Purchased"),

        CANNOT_RELOAD("Cannot_Reload"),
        WEAPON_RECEIVED("Weapon_Received");

        private final String nodeName;

        Message(String nodeName)
        {
            this.nodeName = nodeName;
        }

        public String getNodeName()
        {
            return this.nodeName;
        }

        public String getMessage()
        {
            return CSMessages.messages.containsKey(this.nodeName) ? removeColourCodes(CSMessages.messages.get(this.nodeName)) : "";
        }

        public String getMessage(String itemName)
        {
            return getMessage().replace("<item>", removeColourCodes(itemName));
        }

        public String getMessage(int amount, String itemName)
        {
            return getMessage().replace("<amount>", String.valueOf(amount)).replace("<item>", itemName);
        }

        public String getMessage(String itemName, String crossSymbol, int amount)
        {
            return getMessage().replace("<item>", removeColourCodes(itemName)).replace("<cross>", crossSymbol).replace("<amount>", String.valueOf(amount));
        }

        public String removeColourCodes(String string)
        {
            return string.replaceAll("([§])[\\S]{0,1}", "");
        }
    }

    public static void sendMessage(Player player, String heading, String message)
    {
        if (!message.isEmpty())
            player.sendMessage(heading + message);
    }
}