package com.shampaggon.crackshot;

import com.shampaggon.crackshot.events.WeaponExplodeEvent;
import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Vehicle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CSMinion
{
    private final CSDirector plugin;
    public String heading = "§7░ §c[-§l¬§cº§lc§7§ls§7] §c- §7";

    public CSMinion(CSDirector plugin)
    {
        this.plugin = plugin;
    }

    public void clearRecipes()
    {
        plugin.parentlist.values().stream().filter(parent -> plugin.getBoolean(parent + ".Crafting.Enable")).forEach(parent ->
        {
            ItemStack weapon = vendingMachine(parent);
            NamespacedKey key = NamespacedKey.fromString(parent.toLowerCase(), plugin);

            if (key != null)
            {
                Bukkit.removeRecipe(key);
            }

            // Fallback method in case the crafting key is null
            for (Recipe rec : Bukkit.getRecipesFor(weapon))
            {
                Iterator<Recipe> it = Bukkit.recipeIterator();

                while (it.hasNext())
                {
                    if (it.next().getResult().isSimilar(rec.getResult()))
                    {
                        it.remove();
                    }
                }
            }
        });
    }


    public void customRecipes()
    {
        weapon:
        for (String parent : this.plugin.parentlist.values().stream().filter(parent -> plugin.getBoolean(parent + ".Crafting.Enable")).toList())
        {
            boolean shaped = this.plugin.getBoolean(parent + ".Crafting.Shaped");
            int quantity = Math.min(this.plugin.getInt(parent + ".Crafting.Quantity"), 1);
            String ingredients = this.plugin.getString(parent + ".Crafting.Ingredients");
            String[] args = ingredients.split(",");

            ItemStack weapon = vendingMachine(parent);
            weapon.setAmount(quantity);

            NamespacedKey key = NamespacedKey.fromString(parent.toLowerCase(), plugin);
            Recipe recipe = null;

            if (shaped)
            {
                if (args.length == 9)
                {
                    if (key != null)
                    {
                        recipe = new ShapedRecipe(key, weapon);
                    }
                    else
                    {
                        plugin.getSLF4JLogger().warn("Weapon " + parent + " is using an internal name in the configuration that does not comply with Bukkit namespace requirements. We're going to use legacy methods, but for the love of god, use something else!");
                        recipe = new ShapedRecipe(weapon);
                    }

                    ((ShapedRecipe) recipe).shape("ABC", "DEF", "GHI");

                    // Weird char-based recipe system, wtf?
                    for (int c = 65, j = 0; c < 74; c++, j++)
                    {
                        Material material = Material.matchMaterial(args[j]);

                        if (material == null)
                        {
                            plugin.getSLF4JLogger().error("Failed to build crafting recipe for weapon " + parent + " - '" + args[j] + "' is not a valid material.");
                            continue weapon;
                        }

                        ItemStack stack = new ItemStack(material);
                        ((ShapedRecipe) recipe).setIngredient((char) c, stack);
                    }

                }
                else
                {
                    plugin.getSLF4JLogger().error("The crafting recipe (" + ingredients + ") of weapon " + parent + " has " + args.length + " value(s) instead of 9.");
                    continue;
                }
            }
            else
            {
                if (key != null)
                {
                    recipe = new ShapelessRecipe(key, weapon);
                }
                else
                {
                    plugin.getSLF4JLogger().warn("Weapon " + parent + " is using an internal name in the configuration that does not comply with Bukkit namespace requirements. We're going to use legacy methods, but for the love of god, use something else!");
                    recipe = new ShapelessRecipe(weapon);
                }

                for (Material material : Arrays.stream(args).map(Material::matchMaterial).filter(material -> material != null && material != Material.AIR).toList())
                {
                    ((ShapelessRecipe) recipe).addIngredient(material);
                }
            }

            Bukkit.addRecipe(recipe);
        }
    }


    public ItemStack vendingMachine(String parent_node)
    {

        boolean remote = false;

        boolean trap = false;

        boolean startGiven = false;

        String iName = this.plugin.getString(parent_node + ".Item_Information.Item_Name");

        String iLore = this.plugin.getString(parent_node + ".Item_Information.Item_Lore");

        boolean dualWield = this.plugin.getBoolean(parent_node + ".Shooting.Dual_Wield");

        boolean reload = this.plugin.getBoolean(parent_node + ".Reload.Enable");

        boolean keepUselessTag = !this.plugin.getBoolean(parent_node + ".Item_Information.Remove_Unused_Tag");

        boolean rdeEnable = this.plugin.getBoolean(parent_node + ".Explosive_Devices.Enable");

        String rdeInfo = this.plugin.getString(parent_node + ".Explosive_Devices.Device_Type");

        int reloadAmt = this.plugin.getInt(parent_node + ".Reload.Reload_Amount");

        Integer startAmt = CSDirector.ints.get(parent_node + ".Reload.Starting_Amount");

        String actType = this.plugin.getString(parent_node + ".Firearm_Action.Type");

        String itemInfo = this.plugin.getString(parent_node + ".Item_Information.Item_Type");

        String attachType = this.plugin.getString(parent_node + ".Item_Information.Attachments.Type");

        String attachInfo = this.plugin.getString(parent_node + ".Item_Information.Attachments.Info");

        char BLACK_LEFT_TRI = '◀', WHITE_RIGHT_TRI = '▷', INFINITY = '×';


        if (startAmt != null && startAmt.intValue() <= reloadAmt)
        {

            startGiven = true;

            if (startAmt.intValue() < 0)
            {
                reloadAmt = 0;
            }
            else
            {
                reloadAmt = startAmt.intValue();
            }


        }

        if (actType != null && (actType.equalsIgnoreCase("bolt") || actType.equalsIgnoreCase("lever")) &&
                reloadAmt > reloadAmt - 1)
        {

            reloadAmt = (reloadAmt - 1 < 0) ? 0 : (reloadAmt - 1);

        }


        if (rdeInfo != null)
        {

            if (rdeInfo.equalsIgnoreCase("remote") || rdeInfo.equalsIgnoreCase("itembomb"))
            {

                remote = true;

            }
            else if (rdeInfo.equalsIgnoreCase("trap"))
            {

                trap = true;

            }

        }


        if (itemInfo == null)
        {

            System.out.println("[CrackShot] The weapon '" + parent_node + "' has no value provided for Item_Type!");

            return null;

        }


        ItemStack sniperID = parseItemStack(itemInfo);

        if (sniperID == null)
        {

            System.out.println("[CrackShot] The weapon '" + parent_node + "' has an invalid value for Item_Type!");

            return null;

        }

        ItemMeta snipermeta = sniperID.getItemMeta();


        if (reload && !rdeEnable)
        {

            if (dualWield)
            {

                iName = iName + " «" + reloadAmt + " | " + reloadAmt + "»";

            }
            else if (attachType != null && attachType.equalsIgnoreCase("main"))
            {

                boolean attachRelEnable = this.plugin.getBoolean(attachInfo + ".Reload.Enable");

                int attachRelAmt = this.plugin.getInt(attachInfo + ".Reload.Reload_Amount");


                Integer attStartAmt = CSDirector.ints.get(attachInfo + ".Reload.Starting_Amount");

                if (attStartAmt != null && attStartAmt.intValue() <= attachRelAmt)
                {

                    attachRelAmt = (attStartAmt.intValue() < 0) ? 0 : attStartAmt.intValue();

                }


                if (attachRelEnable)
                {

                    iName = iName + " «" + reloadAmt + " " + BLACK_LEFT_TRI + WHITE_RIGHT_TRI + " " + attachRelAmt + "»";

                }
                else
                {

                    iName = iName + " «" + reloadAmt + " " + BLACK_LEFT_TRI + WHITE_RIGHT_TRI + " " + INFINITY + "»";

                }

            }
            else
            {

                iName = iName + " «" + reloadAmt + "»";

            }

        }
        else if (remote)
        {

            String rdeCapacity = "N/A";

            String deviceInfo = this.plugin.getString(parent_node + ".Explosive_Devices.Device_Info");

            String[] refinedOre = (rdeInfo.equalsIgnoreCase("itembomb") && deviceInfo != null) ? deviceInfo.split(",") : returnRefinedOre(null, parent_node);

            if (refinedOre != null) rdeCapacity = refinedOre[0];

            iName = iName + " «" + rdeCapacity + "»";

        }
        else if (trap)
        {

            iName = iName + " «?»";

        }

        else if (dualWield)
        {

            iName = iName + " «" + INFINITY + " | " + INFINITY + "»";

        }
        else if (attachType != null && attachType.equalsIgnoreCase("main"))
        {

            boolean attachRelEnable = this.plugin.getBoolean(attachInfo + ".Reload.Enable");

            int attachRelAmt = this.plugin.getInt(attachInfo + ".Reload.Reload_Amount");


            Integer attStartAmt = CSDirector.ints.get(attachInfo + ".Reload.Starting_Amount");

            if (attStartAmt != null && attStartAmt.intValue() <= attachRelAmt)
            {

                attachRelAmt = (attStartAmt.intValue() < 0) ? 0 : attStartAmt.intValue();

            }


            if (attachRelEnable)
            {

                iName = iName + " «" + INFINITY + " " + BLACK_LEFT_TRI + WHITE_RIGHT_TRI + " " + attachRelAmt + "»";

            }
            else
            {

                iName = iName + " «" + INFINITY + " " + BLACK_LEFT_TRI + WHITE_RIGHT_TRI + " " + INFINITY + "»";

            }

        }
        else if (keepUselessTag || actType != null)
        {

            iName = iName + " «" + INFINITY + "»";

        }


        if (actType != null && !dualWield)
        {

            if (actType.equalsIgnoreCase("bolt") || actType.equalsIgnoreCase("lever") || actType.equalsIgnoreCase("pump") || actType.equalsIgnoreCase("slide"))
            {

                if (startGiven && startAmt.intValue() < 1)
                {

                    iName = iName.replaceAll("«", "▫ «");

                }
                else
                {

                    iName = iName.replaceAll("«", "▪ «");

                }

            }
            else if (actType.equalsIgnoreCase("revolver") || actType.equalsIgnoreCase("break"))
            {

                iName = iName.replaceAll("«", "▪ «");

            }

        }


        if (iLore != null)
        {

            ArrayList<String> lore = new ArrayList<String>();

            String[] lines = iLore.split("\\|");
            byte b;
            int i;
            String[] arrayOfString1;

            for (i = (arrayOfString1 = lines).length, b = 0; b < i; )
            {
                String line = arrayOfString1[b];
                lore.add(line);
                b++;
            }

            snipermeta.setLore(lore);

        }


        snipermeta.setDisplayName(iName);

        sniperID.setItemMeta(snipermeta);


        return sniperID;

    }


    public String identifyWeapon(String weapon)
    {

        String closestParent = null;


        for (String parentNode : this.plugin.parentlist.values())
        {

            if (weapon.equalsIgnoreCase(parentNode))
                return parentNode;

            if (closestParent == null && parentNode.toUpperCase().startsWith(weapon.toUpperCase()))
            {

                closestParent = parentNode;

            }

        }


        return closestParent;

    }


    public void oneTime(Player player)
    {

        if (player.getItemInHand().getAmount() == 1)
        {

            player.getInventory().clear(player.getInventory().getHeldItemSlot());

        }
        else
        {

            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);

        }

        this.plugin.unscopePlayer(player);

        player.updateInventory();

    }


    public void getWeaponCommand(Player player, String weapon, boolean spawned, String amount, boolean given, boolean byAPI)
    {

        String parent_node = identifyWeapon(weapon);

        if (parent_node != null)
        {


            String attachType = this.plugin.getString(parent_node + ".Item_Information.Attachments.Type");

            if (attachType == null || !attachType.equalsIgnoreCase("accessory"))
            {

                getWeaponHelper(player, parent_node, spawned, amount, given, byAPI);

                return;

            }

        }

        player.sendMessage(this.heading + "No weapon matches '" + weapon + "'.");

    }


    public void getWeaponHelper(Player player, String parentNode, boolean spawned, String amount, boolean given, boolean byAPI)
    {

        if (spawned && !player.hasPermission("crackshot.get." + parentNode) && !player.hasPermission("crackshot.get.all"))
        {

            player.sendMessage(this.heading + "You do not have permission to get this item.");


            return;

        }

        ItemStack sniperID = vendingMachine(parentNode);

        if (sniperID == null)
        {

            player.sendMessage(this.heading + "You have failed to provide a value for 'Item_Type'.");


            return;

        }


        int intAmount = 1;

        if (amount != null) try
        {
            intAmount = Integer.valueOf(amount).intValue();
        }
        catch (NumberFormatException numberFormatException)
        {
        }

        if (intAmount > 64) intAmount = 64;

        if (intAmount < 1)
        {

            player.sendMessage(this.heading + "'" + intAmount + "' is not a valid amount.");


            return;

        }

        if (player.getInventory().firstEmpty() == -1)
        {

            player.sendMessage(this.heading + "Your inventory is full.");


            return;

        }


        String multiplier = "";

        if (intAmount > 1) multiplier = " ✕" + intAmount;


        for (int count = 0; count < intAmount; )
        {
            player.getInventory().addItem(sniperID);
            count++;
        }


        String publicName = parentNode;

        if (publicName.length() > 19) publicName = publicName.substring(0, 19) + "...";

        if (spawned)
        {

            player.sendMessage(this.heading + "Successfully grabbed - " + publicName + multiplier);

        }
        else if (given && !byAPI)
        {

            String itemName = this.plugin.getString(parentNode + ".Item_Information.Item_Name");

            CSMessages.sendMessage(player, this.heading, CSMessages.Message.WEAPON_RECEIVED.getMessage(itemName, String.valueOf('✕'), intAmount));

        }

        if (!byAPI) this.plugin.playSoundEffects(player, parentNode, ".Item_Information.Sounds_Acquired", false, null);


    }


    public Vector getAlignedDirection(Location locA, Location locB)
    {

        Vector vector = locB.toVector().subtract(locA.toVector()).normalize();

        return vector;

    }


    public void loadGeneralConfig()
    {

        String path = this.plugin.getDataFolder() + "/general.yml";

        File tag = new File(path);


        if (!tag.exists())
        {

            File dFile = getDefaultConfig("general.yml");

            if (dFile != null)
            {

                try
                {
                    dFile.createNewFile();
                }
                catch (IOException iOException)
                {
                }

            }

            System.out.println("[CrackShot] General configuration added!");

        }


        if (tag != null)
        {

            try
            {

                this.plugin.weaponConfig = YamlConfiguration.loadConfiguration(tag);

                if (this.plugin.weaponConfig.getList("Disabled_Worlds") != null)
                {

                    this.plugin.disWorlds = (String[]) this.plugin.weaponConfig.getList("Disabled_Worlds").toArray((Object[]) new String[]{"0"});

                }


                ConfigurationSection invCtrl = this.plugin.weaponConfig.getConfigurationSection("Inventory_Control");

                if (invCtrl != null)
                {

                    for (String group : invCtrl.getKeys(false))
                    {

                        CSDirector.ints.put(group + ".Limit", this.plugin.weaponConfig.getInt("Inventory_Control." + group + ".Limit"));

                        CSDirector.strings.put(group + ".Message_Exceeded", this.plugin.weaponConfig.getString("Inventory_Control." + group + ".Message_Exceeded").replace("&", "§"));

                        CSDirector.strings.put(group + ".Sounds_Exceeded", this.plugin.weaponConfig.getString("Inventory_Control." + group + ".Sounds_Exceeded"));

                    }

                }


                CSDirector.bools.put("Merged_Reload.Disable", this.plugin.weaponConfig.getBoolean("Merged_Reload.Disable"));

                CSDirector.strings.put("Merged_Reload.Message_Denied", this.plugin.weaponConfig.getString("Merged_Reload.Message_Denied").replace("&", "§"));

                CSDirector.strings.put("Merged_Reload.Sounds_Denied", this.plugin.weaponConfig.getString("Merged_Reload.Sounds_Denied"));

            }
            catch (Exception ex)
            {

                System.out.println("[CrackShot] " + tag.getName() + " could not be loaded.");

            }

        }

    }


    public void loadMessagesConfig()
    {

        String path = this.plugin.getDataFolder() + "/messages.yml";

        File tag = new File(path);


        if (!tag.exists())
        {

            File dFile = getDefaultConfig("messages.yml");

            if (dFile != null)
            {

                try
                {
                    dFile.createNewFile();
                }
                catch (IOException iOException)
                {
                }

            }

            System.out.println("[CrackShot] Message configuration added!");

        }


        if (tag != null)
        {

            try
            {

                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(tag);


                for (String key : yamlConfiguration.getKeys(true))
                {

                    CSMessages.messages.put(key, yamlConfiguration.getString(key));

                }

            }
            catch (Exception ex)
            {

                System.out.println("[CrackShot] " + tag.getName() + " could not be loaded.");

            }

        }

    }


    public File getDefaultConfig(String fileName)
    {

        File file = new File(this.plugin.getDataFolder() + "/" + fileName);

        InputStream inputStream = CSDirector.class.getResourceAsStream("/" + fileName);

        if (inputStream == null) return null;


        try
        {

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];


            int read = 0;

            for (; (read = inputStream.read(buffer)) > 0; output.write(buffer, 0, read)) ;

            inputStream.close();

            output.close();


            return file;

        }

        catch (Exception exception)
        {


            return null;

        }

    }


    public void loadWeapons(Player player)
    {

        String path = this.plugin.getDataFolder() + "/weapons";

        File tag = new File(path);

        File[] fileList = tag.listFiles();


        if (fileList == null || fileList.length == 0)
        {
            String[] specials = {"defaultWeapons.yml", "defaultExplosives.yml", "defaultAttachments.yml"};
            byte b1;
            int j;
            String[] arrayOfString1;

            for (j = (arrayOfString1 = specials).length, b1 = 0; b1 < j; )
            {
                String spec = arrayOfString1[b1];

                File dFile = grabDefaults(spec);

                if (dFile != null)
                {
                    try
                    {
                        dFile.createNewFile();
                    }

                    catch (IOException iOException)
                    {
                    }

                }

                b1++;
            }


            fileList = tag.listFiles();

            System.out.println("[CrackShot] Default weapons added!");

        }


        if (fileList == null)
        {
            System.out.println("[CrackShot] No weapons were loaded!");
            return;
        }

        byte b;

        int i;

        File[] arrayOfFile1;

        for (i = (arrayOfFile1 = fileList).length, b = 0; b < i; )
        {
            File file = arrayOfFile1[b];

            if (file.getName().endsWith(".yml"))
            {

                this.plugin.weaponConfig = loadConfig(file, player);

                this.plugin.fillHashMaps(this.plugin.weaponConfig);

            }


            b++;
        }


        completeList();

    }


    public File grabDefaults(String defaultWeap)
    {

        File file = new File(this.plugin.getDataFolder() + "/weapons/" + defaultWeap);

        File directories = new File(this.plugin.getDataFolder() + "/weapons");

        if (!directories.exists()) directories.mkdirs();


        InputStream inputStream = CSDirector.class.getResourceAsStream("/resources/" + defaultWeap);

        if (inputStream == null) return null;


        try
        {

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];


            int read = 0;

            for (; (read = inputStream.read(buffer)) > 0; output.write(buffer, 0, read)) ;

            inputStream.close();

            output.close();


            return file;

        }

        catch (Exception exception)
        {


            return null;

        }

    }


    public YamlConfiguration loadConfig(File file, Player player)
    {

        YamlConfiguration config = new YamlConfiguration();


        try
        {

            config.load(file);

        }
        catch (FileNotFoundException fileNotFoundException)
        {


        }
        catch (IOException ex)
        {

            if (player != null)
            {

                player.sendMessage(this.heading + "The file '" + file.getName() + "' could not be loaded.");

            }

            ex.printStackTrace();

        }
        catch (InvalidConfigurationException ex)
        {

            if (player != null)
            {

                player.getWorld().playSound(player.getLocation(), SoundManager.get("VILLAGER_HAGGLE"), 1.0F, 1.0F);

                player.sendMessage(this.heading + "The file '" + file.getName() + "' is incorrectly configured. View the error report in the console and fix it!");

            }

            ex.printStackTrace();

        }


        return config;

    }


    public void completeList()
    {

        int counter = 1;

        for (String parent_node : this.plugin.parentlist.values())
        {


            String attachType = this.plugin.getString(parent_node + ".Item_Information.Attachments.Type");

            if (!this.plugin.getBoolean(parent_node + ".Item_Information.Hidden_From_List") && (attachType == null || !attachType.equalsIgnoreCase("accessory")))
            {
                this.plugin.wlist.put(counter, parent_node);

                counter++;

            }

        }


        CSDirector.ints.put("totalPages", (int) Math.ceil((counter - 1) / 18.0D));

    }


    public void listWeapons(Player sender, String[] args)
    {
        int pageNumber = 1;
        int start = 1;

        int page = 1;

        int finalChapter = this.plugin.getInt("totalPages");

        if (finalChapter == 0) finalChapter = 1;

        pageNumber = finalChapter * 18;
        sender.sendMessage("§7░ §cWeapons:");

        for (int i = start; i < pageNumber; i += 2)
        {
            String weapon = this.plugin.wlist.get(i);

            if (weapon == null)
                break;

            String weapon2 = this.plugin.wlist.get(i + 1);

            sender.sendMessage(makePretty(weapon, weapon2));
        }

    }


    public String makePretty(String weapon, String weapon2)
    {

        weapon = (weapon.length() > 18) ? (weapon.toUpperCase().substring(0, 18) + "...") : weapon.toUpperCase();

        String tripleDot = weapon.replace("...", "O").replace("I", "");


        int officialLength = weapon.replace("...", "O").length();

        int count = officialLength - tripleDot.length();

        String padding = (officialLength % 2 == 0) ? "||" : " |";

        int spaceLimit = 34 - (officialLength + 1) / 2;


        if (count != 0 && count % 2 != 0)
        {

            padding = (officialLength % 2 != 0) ? " ||" : " |";

        }


        for (int a = officialLength + 1; a < spaceLimit + count / 2; a++)
        {

            padding = " " + padding;

        }


        if (weapon2 != null)
        {

            if (weapon2.length() > 18)
            {

                weapon2 = weapon2.substring(0, 18) + "...";

            }

            weapon = "§7░ §c - §7" + weapon + padding + "§7░ §c - §7" + weapon2.toUpperCase();

        }
        else
        {

            weapon = "§7░ §c - §7" + weapon + padding + "§7░";

        }


        return weapon;

    }


    public void removeEnchantments(ItemStack item)
    {

        for (Enchantment e : item.getEnchantments().keySet())
        {

            item.removeEnchantment(e);

        }

    }


    public String extractReading(String name)
    {

        if (!name.contains("«")) return String.valueOf('×');

        String[] nameDigger = name.split("«");

        return nameDigger[1].split("»")[0];

    }


    public void replaceBrackets(ItemStack item, String gapFiller, String parent_node)
    {

        String attachType = this.plugin.getAttachment(parent_node, item)[0];

        try
        {

            if (attachType != null)
            {

                String[] ammoReading = extractReading(item.getItemMeta().getDisplayName()).split(" ");

                if (attachType.equalsIgnoreCase("main"))
                {

                    gapFiller = gapFiller + " " + ammoReading[1] + " " + ammoReading[2];

                }
                else if (attachType.equalsIgnoreCase("accessory"))
                {

                    gapFiller = ammoReading[0] + " " + ammoReading[1] + " " + gapFiller;

                }

            }

        }
        catch (IndexOutOfBoundsException ex)
        {


            resetItemName(item, parent_node);


            return;

        }

        String refinedOre = item.getItemMeta().getDisplayName().replaceAll("(?<=«).*?(?=»)", gapFiller);

        setItemName(item, refinedOre);

    }


    public void resetItemName(ItemStack item, String parentNode)
    {

        ItemStack correctItem = vendingMachine(parentNode);

        setItemName(item, correctItem.getItemMeta().getDisplayName());

    }


    public void setItemName(ItemStack item, String name)
    {

        ItemMeta m = item.getItemMeta();

        m.setDisplayName(name);

        item.setItemMeta(m);

    }


    public boolean isHesh(Projectile proj, LivingEntity victim, double projSpeed)
    {

        boolean retVal = false;


        World regionWorld = victim.getWorld();

        Location vicEyeLoc = victim.getEyeLocation();

        Location locOne = new Location(regionWorld, vicEyeLoc.getX() + 0.5D, vicEyeLoc.getY() + 0.5D, vicEyeLoc.getZ() + 0.5D);

        Location locTwo = new Location(regionWorld, vicEyeLoc.getX() - 0.5D, vicEyeLoc.getY() - 0.5D, vicEyeLoc.getZ() - 0.5D);

        if (projSpeed > 256.0D) projSpeed = 256.0D;


        for (double i = 0.0D; i <= projSpeed; i += 0.8D)
        {

            Location finalLoc = proj.getLocation();

            Vector direction = proj.getVelocity().normalize();

            direction.multiply(i);

            finalLoc.add(direction);


            if (isInsideCuboid(finalLoc, locOne, locTwo, regionWorld))
            {

                retVal = true;


                break;

            }

        }

        return retVal;

    }


    public boolean durabilityCheck(String item)
    {

        String[] list = {"346", "398", "359"};

        int i;

        for (i = 256; i <= 259; )
        {
            if (item.contains(String.valueOf(i))) return true;
            i++;
        }

        for (i = 267; i <= 279; )
        {
            if (item.contains(String.valueOf(i))) return true;
            i++;
        }

        for (i = 283; i <= 286; )
        {
            if (item.contains(String.valueOf(i))) return true;
            i++;
        }

        for (i = 290; i <= 294; )
        {
            if (item.contains(String.valueOf(i))) return true;
            i++;
        }

        for (i = 298; i <= 317; )
        {
            if (item.contains(String.valueOf(i))) return true;
            i++;
        }

        byte b;
        int j;
        String[] arrayOfString1;
        for (j = (arrayOfString1 = list).length, b = 0; b < j; )
        {
            String it = arrayOfString1[b];
            if (item.contains(it)) return true;
            b++;
        }


        return false;

    }


    public void projectileLightning(Location loc, boolean zapNoDam)
    {

        if (zapNoDam)
        {

            loc.getWorld().strikeLightningEffect(loc);

        }
        else
        {

            loc.getWorld().strikeLightning(loc);

        }

    }


    public void explosionPackage(LivingEntity victim, String parent_node, Player player)
    {

        if (parent_node != null)
        {

            String vicName = victim.getType().getName();

            String shooterName = (player == null) ? "<shooter>" : player.getName();


            boolean spawnedEnts = this.plugin.spawnEntities(victim, parent_node, ".Spawn_Entity_On_Hit.EntityType_Baby_Explode_Amount", player);

            givePotionEffects(victim, parent_node, ".Explosions.Explosion_Potion_Effect", "explosion");

            int inc = this.plugin.getInt(parent_node + ".Explosions.Ignite_Victims");

            if (inc != 0) victim.setFireTicks(inc);

            this.plugin.playSoundEffects(victim, parent_node, ".Explosions.Sounds_Victim", false, null);


            if (victim == player)
                return;

            if (victim instanceof Player)
            {

                if (spawnedEnts)
                {

                    this.plugin.sendPlayerMessage(victim, parent_node, ".Spawn_Entity_On_Hit.Message_Victim", shooterName, vicName, "<flight>", "<damage>");

                }

                vicName = victim.getName();

                this.plugin.sendPlayerMessage(victim, parent_node, ".Explosions.Message_Victim", shooterName, vicName, "<flight>", "<damage>");

            }


            if (player != null)
            {

                if (spawnedEnts)
                {

                    this.plugin.sendPlayerMessage(player, parent_node, ".Spawn_Entity_On_Hit.Message_Shooter", shooterName, vicName, "<flight>", "<damage>");

                }

                this.plugin.sendPlayerMessage(player, parent_node, ".Explosions.Message_Shooter", shooterName, vicName, "<flight>", "<damage>");

                this.plugin.playSoundEffects(player, parent_node, ".Explosions.Sounds_Shooter", false, null);

            }

        }

    }


    public void callAndResponse(final Player victim, final Player fisherman, final Vehicle vehicle, final String[] mineInfo, final boolean shot)
    {

        if (victim.hasMetadata("CS_trigDelay"))
        {

            return;

        }

        if (fisherman == null)
        {

            if (vehicle == null)
            {

                detonateRDE(fisherman, victim, mineInfo, false);

            }
            else
            {

                mineAction(vehicle, mineInfo, fisherman, shot, null, victim);

            }


            return;

        }


        victim.setMetadata("CS_trigDelay", new FixedMetadataValue(this.plugin, Boolean.valueOf(false)));

        tempVars(victim, "CS_trigDelay", Long.valueOf(200L));


        victim.setMetadata("CS_singed", new FixedMetadataValue(this.plugin, Boolean.valueOf(false)));

        illegalSlap(fisherman, victim, 0);


        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()

        {

            public void run()
            {

                if (victim.hasMetadata("CS_singed") && victim.getMetadata("CS_singed").get(0).asBoolean())
                {

                    victim.removeMetadata("CS_singed", CSMinion.this.plugin);

                    victim.removeMetadata("CS_trigDelay", CSMinion.this.plugin);

                    if (vehicle == null)
                    {

                        CSMinion.this.detonateRDE(fisherman, victim, mineInfo, false);

                    }
                    else
                    {

                        CSMinion.this.mineAction(vehicle, mineInfo, fisherman, shot, victim.getName(), victim);

                    }

                }

            }

        }, 1L);

    }


    public void reseatTag(Item item)
    {

        if (item.getVehicle() instanceof Entity)
            return;
        for (Entity veh : item.getNearbyEntities(1.0D, 10.0D, 1.0D))
        {

            if (veh instanceof org.bukkit.entity.Minecart && !(veh.getPassenger() instanceof Entity))
            {

                veh.setPassenger(item);

                break;

            }

        }

    }


    public void reseatTag(Vehicle vehicle)
    {

        if (vehicle.getPassenger() instanceof Entity)
            return;
        for (Entity ent : vehicle.getNearbyEntities(1.0D, 10.0D, 1.0D))
        {

            if (ent instanceof Item && !(ent.getVehicle() instanceof Entity))
            {

                ItemStack itemFuse = ((Item) ent).getItemStack();

                if (this.plugin.itemIsSafe(itemFuse) && itemFuse.getItemMeta().getDisplayName().startsWith("§cS3AGULLL~"))
                {

                    vehicle.setPassenger(ent);

                    break;

                }

            }

        }

    }


    public void mineAction(Vehicle vehicle, String[] mineInfo, Player fisherman, boolean shot, String vicName, Entity victim)
    {

        if (fisherman != null && vicName != null)
        {

            this.plugin.sendPlayerMessage(fisherman, mineInfo[2], ".Explosive_Devices.Message_Trigger_Placer", mineInfo[1], vicName, "<flight>", "<damage>");

            this.plugin.playSoundEffects(fisherman, mineInfo[2], ".Explosive_Devices.Sounds_Alert_Placer", false, null);

        }


        if (victim instanceof Player && !mineInfo[1].equals(victim.getName()))
        {

            this.plugin.sendPlayerMessage((LivingEntity) victim, mineInfo[2], ".Explosive_Devices.Message_Trigger_Victim", mineInfo[1], vicName, "<flight>", "<damage>");

        }


        this.plugin.projectileExplosion(vehicle, mineInfo[2], shot, fisherman, true, false, null, null, false, 0);

        if (!shot) this.plugin.playSoundEffects(vehicle, mineInfo[2], ".Explosive_Devices.Sounds_Trigger", false, null);

        vehicle.getPassenger().remove();

    }


    public void tempVars(final Player player, final String metaData, Long delay)
    {

        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()

        {

            public void run()
            {

                player.removeMetadata(metaData, CSMinion.this.plugin);

            }

        }, delay.longValue());

    }


    public void illegalSlap(Player player, LivingEntity victim, int dmg)
    {

        PermissionAttachment attachment = player.addAttachment(this.plugin);

        attachment.setPermission("nocheatplus", true);

        attachment.setPermission("anticheat.check.exempt", true);

        victim.damage(dmg, player);

        player.removeAttachment(attachment);

    }


    public String[] fastenSeatbelts(Item psngr)
    {

        if (this.plugin.itemIsSafe(psngr.getItemStack()))
        {

            String itemName = psngr.getItemStack().getItemMeta().getDisplayName();

            if (itemName.contains("§cS3AGULLL"))
            {

                return itemName.split("~");

            }

        }

        return null;

    }


    public boolean bombIsInvalid(Player player, String[] deviceInfo, String parentNode)
    {

        boolean retVal = false;

        String debugMsg = this.heading + "The 'Device_Info' node of the weapon " + parentNode;

        String debugEnd = null;


        if (deviceInfo == null || deviceInfo.length != 4)
        {

            debugEnd = " is incorrectly formatted.";

            retVal = true;

        }
        else if (parseItemStack(deviceInfo[2]) == null)
        {

            debugEnd = " contains the value '" + deviceInfo[2] + "', which is not a valid item ID.";

            retVal = true;

        }
        else if (parseItemStack(deviceInfo[3]) == null)
        {

            debugEnd = " contains the value '" + deviceInfo[3] + "', which is not a valid item ID.";

            retVal = true;

        }
        else
        {

            try
            {

                if (Integer.valueOf(deviceInfo[0]).intValue() <= 0)
                {

                    debugEnd = " contains the value '" + deviceInfo[0] + "', which is not a number greater than 0.";

                    retVal = true;

                }

                Double.valueOf(deviceInfo[1]);

            }
            catch (NumberFormatException ex)
            {

                debugEnd = " contains an invalid number.";

                retVal = true;

            }

        }


        if (retVal)
        {

            player.sendMessage(debugMsg + debugEnd);

        }


        return retVal;

    }


    public String[] returnRefinedOre(Player player, String parent_node)
    {

        String rdeOre = this.plugin.getString(parent_node + ".Explosive_Devices.Device_Info");

        boolean playerExists = (player != null);

        String msgToSend = null;

        if (rdeOre != null)
        {

            String[] rdeRefined = rdeOre.split("-");

            if (rdeRefined.length == 3)
            {

                try
                {

                    if (Integer.valueOf(rdeRefined[0]).intValue() < 1)
                    {

                        msgToSend = "'" + rdeRefined[0] + "' in '" + rdeOre + "' of weapon '" + parent_node + "' must be a positive number.";

                    }
                    else if (rdeRefined[1].length() != 2)
                    {

                        msgToSend = "'" + rdeRefined[1] + "' in '" + rdeOre + "' of weapon '" + parent_node + "' must be 2 characters long, not " + rdeRefined[1].length() + ".";

                    }
                    else
                    {

                        return rdeRefined;

                    }

                }
                catch (NumberFormatException ex)
                {

                    msgToSend = "'" + rdeRefined[0] + "' in '" + rdeOre + "' of weapon '" + parent_node + "' is not a valid number.";

                }

            }
            else
            {

                msgToSend = "'" + rdeOre + "' of weapon '" + parent_node + "' has an incorrect format! The correct format is: Amount-UniqueID-Headname!";

            }

        }

        if (playerExists && msgToSend != null)
        {

            player.sendMessage(this.heading + msgToSend);

        }

        return null;

    }


    public void removeNamedItem(Player player, String itemInfo, int totalAmt, String weaponTitle, boolean shop)
    {

        int removed = 0;

        ItemStack item = parseItemStack(itemInfo);

        if (item == null)
            return;

        ItemStack[] inv = player.getInventory().getContents();

        String ammoName = this.plugin.getString(weaponTitle + ".Ammo.Ammo_Name_Check");

        boolean checkName = (ammoName != null);


        for (int i = 0; removed <= totalAmt && i < inv.length; i++)
        {

            if (inv[i] != null && inv[i].getType() == item.getType() && inv[i].getDurability() == item.getDurability() && (
                    !checkName || (this.plugin.itemIsSafe(inv[i]) && inv[i].getItemMeta().getDisplayName().contains(ammoName))))
            {

                if (inv[i].getAmount() > totalAmt - removed)
                {

                    inv[i].setAmount(inv[i].getAmount() - totalAmt - removed);

                    removed = totalAmt;

                }
                else
                {

                    removed += inv[i].getAmount();

                    inv[i] = null;

                }

            }

        }


        player.getInventory().setContents(inv);

        player.updateInventory();


        if (!containsItemStack(player, itemInfo, 1, weaponTitle) && !shop)
        {

            this.plugin.playSoundEffects(player, weaponTitle, ".Ammo.Sounds_Out_Of_Ammo", false, null);

        }

    }


    public int countItemStacks(Player player, String itemInfo, String weaponTitle)
    {

        int count = 0;

        ItemStack item = parseItemStack(itemInfo);


        if (item == null)
        {

            count = 0;

        }
        else
        {

            String ammoName = this.plugin.getString(weaponTitle + ".Ammo.Ammo_Name_Check");

            boolean checkName = (ammoName != null);
            byte b;
            int i;

            ItemStack[] arrayOfItemStack;

            for (i = (arrayOfItemStack = player.getInventory().getContents()).length, b = 0; b < i; )
            {
                ItemStack itemSlot = arrayOfItemStack[b];

                if (itemSlot != null && itemSlot.getType() == item.getType() && itemSlot.getDurability() == item.getDurability() && (
                        !checkName || (this.plugin.itemIsSafe(itemSlot) && itemSlot.getItemMeta().getDisplayName().contains(ammoName))))
                {

                    count += itemSlot.getAmount();

                }


                b++;
            }


        }

        return count;

    }


    public boolean containsItemStack(Player player, String itemInfo, int minAmount, String weaponTitle)
    {

        ItemStack item = parseItemStack(itemInfo);

        return (item != null && countItemStacks(player, itemInfo, weaponTitle) >= minAmount);

    }


    public double getSuperDamage(EntityType victimType, String parent_node, double totalDmg)
    {

        String superEffect = this.plugin.getString(parent_node + ".Abilities.Super_Effective");

        if (superEffect != null)
        {

            String[] mobList = superEffect.split(",");
            byte b;
            int i;
            String[] arrayOfString1;

            for (i = (arrayOfString1 = mobList).length, b = 0; b < i; )
            {
                String mob = arrayOfString1[b];

                mob = mob.replace(" ", "");

                String[] args = mob.split("-");

                try
                {

                    if (args.length == 2 && victimType == EntityType.valueOf(args[0]))
                    {

                        totalDmg = Math.round(totalDmg * Double.valueOf(args[1]).doubleValue());

                    }

                }
                catch (IllegalArgumentException ex)
                {

                    plugin.getSLF4JLogger().error("The value provided for the Super_Effective node of the weapon '" + parent_node + "' is incorrect.");

                }
                b++;
            }


        }

        return totalDmg;

    }


    public void displayFireworks(Entity entity, String parentNode, String child_node)
    {

        if (!this.plugin.getBoolean(parentNode + ".Fireworks.Enable") || this.plugin.getString(parentNode + child_node) == null)
        {

            return;

        }


        String[] fwList = this.plugin.getString(parentNode + child_node).split(",");
        byte b;
        int i;
        String[] arrayOfString1;

        for (i = (arrayOfString1 = fwList).length, b = 0; b < i; )
        {
            String fwInfo = arrayOfString1[b];

            fwInfo = fwInfo.replace(" ", "");

            String[] args = fwInfo.split("-");

            if (args.length == 6)
            {

                try
                {

                    Firework fireWork;

                    if (entity instanceof LivingEntity)
                    {

                        fireWork = entity.getWorld().spawn(((LivingEntity) entity).getEyeLocation(), Firework.class);

                    }
                    else
                    {

                        fireWork = entity.getWorld().spawn(entity.getLocation(), Firework.class);

                    }

                    FireworkMeta fireWorkMeta = fireWork.getFireworkMeta();

                    FireworkEffect effect = FireworkEffect.builder().trail(Boolean.parseBoolean(args[1])).flicker(Boolean.parseBoolean(args[2])).withColor(Color.fromRGB(Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]))).with(FireworkEffect.Type.valueOf(args[0].toUpperCase())).build();

                    fireWorkMeta.addEffects(effect);

                    fireWork.setFireworkMeta(fireWorkMeta);

                }
                catch (IllegalArgumentException ex)
                {

                    System.out.println("[CrackShot] '" + fwInfo + "' of weapon '" + parentNode + "' has an incorrect value for firework type, flicker, trail, or colour!");

                }

            }
            else
            {


                System.out.println("[CrackShot] '" + fwInfo + "' of weapon '" + parentNode + "' has an invalid format! The correct format is: Type-Trail-Flicker-Red-Blue-Green!");

            }

            b++;
        }


    }


    public void givePotionEffects(LivingEntity player, String parentNode, String childNode, String event)
    {

        if (!event.equals("explosion"))
        {

            String eventInfo = this.plugin.getString(parentNode + ".Potion_Effects.Activation");

            if (eventInfo == null || !eventInfo.toLowerCase().contains(event))
            {

                return;

            }

        }


        if (this.plugin.getString(parentNode + childNode) == null)
            return;
        String[] effectList = this.plugin.getString(parentNode + childNode).split(",");
        byte b;
        int i;
        String[] arrayOfString1;

        for (i = (arrayOfString1 = effectList).length, b = 0; b < i; )
        {
            String potFX = arrayOfString1[b];

            potFX = potFX.replace(" ", "");

            String[] args = potFX.split("-");

            if (args.length == 3)
            {

                try
                {

                    PotionEffectType potionType = PotionEffectType.getByName(args[0].toUpperCase());

                    int duration = Integer.parseInt(args[1]);


                    if (potionType.getDurationModifier() != 1.0D)
                    {

                        double maths = duration * 1.0D / potionType.getDurationModifier();

                        duration = (int) maths;

                    }


                    player.removePotionEffect(potionType);

                    player.addPotionEffect(potionType.createEffect(duration, Integer.parseInt(args[2]) - 1));

                }
                catch (Exception ex)
                {

                    System.out.println("[CrackShot] '" + potFX + "' of weapon '" + parentNode + "' has an incorrect potion type, duration or level!");

                }

            }
            else
            {

                System.out.println("[CrackShot] '" + potFX + "' of weapon '" + parentNode + "' has an invalid format! The correct format is: Potion-Duration-Level!");

            }

            b++;
        }


    }


    public void giveParticleEffects(Entity player, String parentNode, String childNode, boolean muzzleFlash, Location givenCoord)
    {

        if ((!this.plugin.getBoolean(parentNode + ".Particles.Enable") && givenCoord == null) || this.plugin.getString(parentNode + childNode) == null)
        {

            return;

        }


        Location loc = (player != null) ? player.getLocation() : givenCoord;

        World world = loc.getWorld();


        if (muzzleFlash)
        {

            Location eyeLoc = ((LivingEntity) player).getEyeLocation();

            loc = eyeLoc.toVector().add(eyeLoc.getDirection().multiply(1.5D)).toLocation(world);

        }


        String[] partList = this.plugin.getString(parentNode + childNode).split(",");
        byte b;
        int i;
        String[] arrayOfString1;

        for (i = (arrayOfString1 = partList).length, b = 0; b < i; )
        {
            String partFX = arrayOfString1[b];

            partFX = partFX.replace(" ", "");

            String[] args = partFX.split("-");


            if (args.length == 1)
            {

                if (args[0].equalsIgnoreCase("smoke"))
                {

                    for (int j = 0; j < 8; j++)
                    {

                        world.playEffect(loc, Effect.SMOKE, j);

                    }

                }
                else if (args[0].equalsIgnoreCase("lightning"))
                {

                    world.strikeLightningEffect(loc);

                }
                else if (args[0].equalsIgnoreCase("explosion"))
                {

                    world.createExplosion(loc, 0.0F);

                }

            }
            else if (args.length == 2)
            {

                try
                {

                    if (args[0].equalsIgnoreCase("potion_splash"))
                    {

                        world.playEffect(loc, Effect.POTION_BREAK, Integer.parseInt(args[1]));

                    }
                    else if (args[0].equalsIgnoreCase("block_break"))
                    {

                        int blockID = Integer.parseInt(args[1]);

                        if (blockID < 256)
                        {

                            world.playEffect(loc, Effect.STEP_SOUND, blockID);

                        }
                        else
                        {

                            plugin.getSLF4JLogger().error("'" + partFX + "' was provided as a particle effect for the weapon '" + parentNode + "'. It contains '" + blockID + "', which is not a valid block ID.");

                        }

                    }
                    else if (args[0].equalsIgnoreCase("flames"))
                    {

                        world.playEffect(loc, Effect.MOBSPAWNER_FLAMES, Integer.parseInt(args[1]));

                    }

                }
                catch (NumberFormatException ex)
                {

                    plugin.getSLF4JLogger().error("'" + partFX + "' was provided as a particle effect for the weapon '" + parentNode + "'. It contains '" + args[1] + "', which is not a valid number.");

                }

            }

            b++;
        }


    }


    public boolean isInsideCuboid(Location locPoint, Location loc1, Location loc2, World world)
    {

        double[] dim = new double[2];


        if (!locPoint.getWorld().equals(world)) return false;


        dim[0] = loc1.getX();

        dim[1] = loc2.getX();

        Arrays.sort(dim);

        if (locPoint.getX() > dim[1] || locPoint.getX() < dim[0]) return false;


        dim[0] = loc1.getY();

        dim[1] = loc2.getY();

        Arrays.sort(dim);

        if (locPoint.getY() > dim[1] || locPoint.getY() < dim[0]) return false;


        dim[0] = loc1.getZ();

        dim[1] = loc2.getZ();

        Arrays.sort(dim);

        return !(locPoint.getZ() > dim[1]) && !(locPoint.getZ() < dim[0]);


    }


    public boolean regionCheck(Entity player, String parent_node)
    {

        if (!this.plugin.getBoolean(parent_node + ".Region_Check.Enable")) return true;

        String region_info = this.plugin.getString(parent_node + ".Region_Check.World_And_Coordinates");

        String[] regions = region_info.split("\\|");

        boolean retVal = false, relevance = false;
        byte b;
        int i;

        String[] arrayOfString1;

        for (i = (arrayOfString1 = regions).length, b = 0; b < i; )
        {
            String region = arrayOfString1[b];

            region = region.replace(" ", "");

            String[] args = region.split(",");

            if (args != null && (args.length == 7 || args.length == 8))
            {

                boolean blackList = args.length == 8 && Boolean.parseBoolean(args[7]);


                try
                {

                    World regionWorld = Bukkit.getWorld(args[0]);

                    Location locPoint = player.getLocation();

                    Location locOne = new Location(regionWorld, Double.valueOf(args[1]).doubleValue(), Double.valueOf(args[2]).doubleValue(), Double.valueOf(args[3]).doubleValue());

                    Location locTwo = new Location(regionWorld, Double.valueOf(args[4]).doubleValue(), Double.valueOf(args[5]).doubleValue(), Double.valueOf(args[6]).doubleValue());

                    if (player.getWorld().equals(regionWorld))
                    {

                        relevance = true;

                        if (isInsideCuboid(locPoint, locOne, locTwo, regionWorld))
                        {

                            if (blackList)
                            {

                                return false;

                            }

                            retVal = true;

                        }

                        else if (blackList)
                        {

                            retVal = true;

                        }

                    }

                }
                catch (NumberFormatException ex)
                {

                    if (player instanceof Player)
                    {

                        player.sendMessage(this.heading + "The value provided for the 'World_And_Coordinates' node of the weapon '" + parent_node + "' is incorrect. Double check the coordinates.");

                    }

                }


            }
            else if (player instanceof Player)
            {

                player.sendMessage(this.heading + "The 'World_And_Coordinates' node of the weapon '" + parent_node + "' has an incorrect number of arguments.");

            }

            b++;
        }


        return !relevance || retVal;

    }


    public void weaponInteraction(Player shooter, String parent_node, boolean leftClick)
    {

        String projType = this.plugin.getString(parent_node + ".Shooting.Projectile_Type");

        boolean underwater = this.plugin.getBoolean(parent_node + ".Extras.Disable_Underwater");

        String[] validTypes = {"arrow", "snowball", "egg", "grenade", "flare", "fireball", "witherskull", "energy", "splash"};


        if (underwater)
        {

            Location loc = shooter.getEyeLocation();

            if (loc.getBlock().getType().toString().toUpperCase().endsWith("WATER"))
            {

                return;

            }

        }

        if (projType != null)
        {

            byte b;
            int i;
            String[] arrayOfString;
            for (i = (arrayOfString = validTypes).length, b = 0; b < i; )
            {
                String type = arrayOfString[b];

                if (projType.equalsIgnoreCase(type))
                {

                    WeaponPrepareShootEvent prepareEvent = new WeaponPrepareShootEvent(shooter, parent_node);

                    this.plugin.getServer().getPluginManager().callEvent(prepareEvent);

                    if (!prepareEvent.isCancelled()) this.plugin.fireProjectile(shooter, parent_node, leftClick);
                    return;

                }

                b++;
            }


            shooter.sendMessage(this.heading + "'" + projType + "' is not a valid type of projectile!");

        }

    }


    public void callAirstrike(final Entity mark, final String parent_node, final Player player)
    {

        final int height = this.plugin.getInt(parent_node + ".Airstrikes.Height_Dropped");

        final int area = this.plugin.getInt(parent_node + ".Airstrikes.Area");

        final int spacing = this.plugin.getInt(parent_node + ".Airstrikes.Distance_Between_Bombs");

        int strikeNo = this.plugin.getInt(parent_node + ".Airstrikes.Multiple_Strikes.Number_Of_Strikes");

        int strikeDelay = this.plugin.getInt(parent_node + ".Airstrikes.Multiple_Strikes.Delay_Between_Strikes");

        boolean multiStrike = this.plugin.getBoolean(parent_node + ".Airstrikes.Multiple_Strikes.Enable");

        final double coordinator = (area - 1) * spacing / 2.0D;

        final Location loc = mark.getLocation();

        final int y = loc.getBlockY();


        if (!multiStrike)
        {


            strikeNo = 1;

            strikeDelay = 1;

        }


        final Random r = new Random();

        final int vVar = this.plugin.getInt(parent_node + ".Airstrikes.Vertical_Variation");

        final int hVar = this.plugin.getInt(parent_node + ".Airstrikes.Horizontal_Variation");


        String block = this.plugin.getString(parent_node + ".Airstrikes.Block_Type");

        if (block == null)
            return;
        String[] blockInfo = block.split("~");

        if (blockInfo.length < 2) blockInfo = new String[]{blockInfo[0], "0"};


        try
        {

            final Material blockMat = MaterialManager.getMaterial(block);

            final Byte secondaryData = Byte.valueOf(blockInfo[1]);


            this.plugin.sendPlayerMessage(player, parent_node, ".Airstrikes.Message_Call_Airstrike", player.getName(), "<victim>", "<flight>", "<damage>");

            giveParticleEffects(null, parent_node, ".Airstrikes.Particle_Call_Airstrike", false, loc);


            WeaponExplodeEvent explodeEvent = new WeaponExplodeEvent(player, loc, parent_node, false, true);

            this.plugin.getServer().getPluginManager().callEvent(explodeEvent);


            for (int delay = 0; delay < strikeDelay * strikeNo; delay += strikeDelay)
            {


                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this.plugin, new Runnable()

                {

                    public void run()
                    {

                        CSMinion.this.plugin.playSoundEffects(mark, parent_node, ".Airstrikes.Sounds_Airstrike", false, null);

                        for (int iOne = 0; iOne < area; iOne++)
                        {


                            double x = (loc.getBlockX() + iOne * spacing) - coordinator;

                            for (int iTwo = 0; iTwo < area; iTwo++)
                            {


                                double z = (loc.getBlockZ() + iTwo * spacing) - coordinator;


                                int hD = y + height;

                                if (vVar != 0) hD += r.nextInt(vVar);

                                if (hVar != 0)
                                {


                                    x += (r.nextInt(hVar) - r.nextInt(hVar));

                                    z += (r.nextInt(hVar) - r.nextInt(hVar));

                                }


                                FallingBlock bomb = loc.getWorld().spawnFallingBlock(new Location(loc.getWorld(), x, hD, z), blockMat, secondaryData.byteValue());

                                bomb.setDropItem(false);

                                bomb.setMetadata("CS_strike", new FixedMetadataValue(CSMinion.this.plugin, parent_node + "~" + player.getName()));

                            }

                        }
                    }

                }, delay);


                if (!multiStrike)
                    break;

            }

        }
        catch (IllegalArgumentException ex)
        {


            player.sendMessage(this.heading + "'" + block + "' in the 'Airstrikes' module of weapon '" + parent_node + "' is not a valid block-type.");


        }

    }


    public void detonateRDE(Player player, Player victim, String[] itemInfo, boolean clacker)
    {

        World world = Bukkit.getServer().getWorld(itemInfo[1]);

        Location loc = new Location(world, Integer.valueOf(itemInfo[2]).intValue() + 0.5D, Integer.valueOf(itemInfo[3]).intValue() + 0.5D, Integer.valueOf(itemInfo[4]).intValue() + 0.5D);

        Block c4 = world.getBlockAt(loc);


        if (MaterialManager.isSkullBlock(c4) && c4.getState() instanceof Skull)
        {

            Skull c4Block;


            String uniqueID = null;

            String storedPlayerName = clacker ? player.getName() : "Anonymous";

            try
            {
                c4Block = (Skull) c4.getState();
            }
            catch (ClassCastException ex)
            {
                return;
            }

            boolean hasOwner = c4Block.hasOwner();


            if (clacker)
            {

                String playerName = player.getName();

                Map<String, String> placedHeads = this.plugin.c4_backup.get(playerName);

                if (placedHeads != null)
                {

                    String key = c4.getWorld().getName() + "," + c4.getX() + "," + c4.getY() + "," + c4.getZ();

                    if (placedHeads.containsKey(key))
                    {

                        uniqueID = placedHeads.get(key);

                        placedHeads.remove(key);

                    }

                }

            }


            if (hasOwner || uniqueID != null)
            {


                if (hasOwner)
                {

                    String grabInfo = c4Block.getOwner();

                    String[] blockInfo = grabInfo.split("،");

                    if (blockInfo.length < 1)
                        return;
                    uniqueID = blockInfo[0];

                    storedPlayerName = blockInfo[1];

                }


                for (String ids : this.plugin.rdelist.keySet())
                {


                    if (ids.equalsIgnoreCase(uniqueID))
                    {


                        String parent_node = this.plugin.rdelist.get(ids);


                        String[] refinedOre = returnRefinedOre(player, parent_node);

                        if (refinedOre != null)
                        {


                            c4Block.setOwner(refinedOre[2]);

                            c4Block.update(false);

                        }


                        if (!clacker)
                        {


                            if (player != null)
                            {


                                this.plugin.sendPlayerMessage(player, parent_node, ".Explosive_Devices.Message_Trigger_Placer", storedPlayerName.replace(String.valueOf('ظ'), "..."), victim.getName(), "<flight>", "<damage>");

                                this.plugin.playSoundEffects(player, parent_node, ".Explosive_Devices.Sounds_Alert_Placer", false, null);

                            }


                            this.plugin.sendPlayerMessage(victim, parent_node, ".Explosive_Devices.Message_Trigger_Victim", storedPlayerName.replace(String.valueOf('ظ'), "..."), victim.getName(), "<flight>", "<damage>");

                        }


                        c4Block.setMetadata("CS_transformers", new FixedMetadataValue(this.plugin, Boolean.valueOf(true)));

                        this.plugin.playSoundEffects(null, parent_node, ".Explosive_Devices.Sounds_Trigger", false, loc);

                        this.plugin.projectileExplosion(null, parent_node, false, player, false, true, loc, c4, false, 0);

                        break;

                    }

                }

            }

        }

    }


    public boolean boobyAction(Block block, Entity victim, ItemStack item)
    {

        if (item == null || !this.plugin.itemIsSafe(item))
        {

            return false;

        }


        String itemName = item.getItemMeta().getDisplayName();

        String actualName = this.plugin.getPureName(itemName);


        String parent_node = this.plugin.boobs.get(actualName);

        if (parent_node == null)
        {

            return false;

        }


        String vicName = "Santa Claus";

        if (victim != null)
        {

            vicName = (victim instanceof Player) ? victim.getName() : victim.getType().getName();

        }


        if (!getBoobean(3, parent_node)) return false;

        String detectedName = extractReading(item.getItemMeta().getDisplayName());

        if (detectedName.equals("?")) return false;

        Player planter = Bukkit.getServer().getPlayer(detectedName);


        if (victim != null)
        {


            if (planter != null)
            {


                if (planter == victim) return false;

                this.plugin.sendPlayerMessage(planter, parent_node, ".Explosive_Devices.Message_Trigger_Placer", detectedName, vicName, "<flight>", "<damage>");

                this.plugin.playSoundEffects(planter, parent_node, ".Explosive_Devices.Sounds_Alert_Placer", false, null);

            }


            if (victim instanceof Player)

            {

                this.plugin.sendPlayerMessage((LivingEntity) victim, parent_node, ".Explosive_Devices.Message_Trigger_Victim", detectedName, vicName, "<flight>", "<damage>");

            }

        }


        this.plugin.playSoundEffects(null, parent_node, ".Explosive_Devices.Sounds_Trigger", false, block.getLocation().add(0.5D, 0.5D, 0.5D));

        this.plugin.projectileExplosion(null, parent_node, false, planter, false, true, null, block, true, 0);


        return true;

    }


    public boolean getBoobean(int entry, String parent_node)
    {

        String ore = this.plugin.getString(parent_node + ".Explosive_Devices.Device_Info");

        if (ore == null)
        {

            return false;

        }


        String[] refinedOre = ore.split("-");

        if (refinedOre.length != 5)
        {

            return false;

        }


        return Boolean.parseBoolean(refinedOre[entry - 1]);

    }


    public ItemStack parseItemStack(String ore)
    {
        ItemStack item = null;


        if (ore != null)
        {
            String[] refinedOre = ore.split("~");

            if (refinedOre.length == 1)
            {
                refinedOre = new String[]{refinedOre[0], "0"};
            }

            try
            {
                item = new ItemStack(MaterialManager.getMaterial(ore), 1, Short.valueOf(refinedOre[1]).shortValue());
            }
            catch (Exception exception)
            {
            }
        }
        return item;
    }

    public void runCommand(Player player, String weaponTitle)
    {
        String commands = this.plugin.getString(weaponTitle + ".Extras.Run_Command");

        if (commands != null)
        {
            commands = commands.replaceAll("<shooter>", player.getName());
            Server server = this.plugin.getServer();
            String delimiter = "่๋້";
            byte b;

            int i;

            String[] arrayOfString;

            for (i = (arrayOfString = commands.split(delimiter)).length, b = 0; b < i; )
            {
                String command = arrayOfString[b];

                if (command.startsWith("@"))
                {
                    server.dispatchCommand(server.getConsoleSender(), command.substring(1).trim());
                }

                else
                {
                    server.dispatchCommand(player, command.trim());
                }

                b++;
            }
        }
    }
}