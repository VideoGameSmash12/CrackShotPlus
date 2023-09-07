 package com.shampaggon.crackshot;
 
 import com.shampaggon.crackshot.events.WeaponAttachmentEvent;
 import com.shampaggon.crackshot.events.WeaponAttachmentToggleEvent;
 import com.shampaggon.crackshot.events.WeaponCapacityEvent;
 import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
 import com.shampaggon.crackshot.events.WeaponDualWieldEvent;
 import com.shampaggon.crackshot.events.WeaponExplodeEvent;
 import com.shampaggon.crackshot.events.WeaponFireRateEvent;
 import com.shampaggon.crackshot.events.WeaponHitBlockEvent;
 import com.shampaggon.crackshot.events.WeaponPlaceMineEvent;
 import com.shampaggon.crackshot.events.WeaponPreShootEvent;
 import com.shampaggon.crackshot.events.WeaponReloadCompleteEvent;
 import com.shampaggon.crackshot.events.WeaponReloadEvent;
 import com.shampaggon.crackshot.events.WeaponScopeEvent;
 import com.shampaggon.crackshot.events.WeaponShootEvent;
 import com.shampaggon.crackshot.events.WeaponTriggerEvent;
 import java.lang.reflect.Method;
 import java.util.ArrayDeque;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Random;
 import java.util.Set;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.Effect;
 import org.bukkit.EntityEffect;
 import org.bukkit.GameMode;
 import org.bukkit.Location;
 import org.bukkit.Material;
 import org.bukkit.SkullType;
 import org.bukkit.Sound;
 import org.bukkit.World;
 import org.bukkit.block.Block;
 import org.bukkit.block.BlockFace;
 import org.bukkit.block.BlockState;
 import org.bukkit.block.Chest;
 import org.bukkit.block.DoubleChest;
 import org.bukkit.block.Sign;
 import org.bukkit.block.Skull;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandSender;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.enchantments.Enchantment;
 import org.bukkit.entity.Ageable;
 import org.bukkit.entity.AnimalTamer;
 import org.bukkit.entity.Creeper;
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.EntityType;
 import org.bukkit.entity.ExperienceOrb;
 import org.bukkit.entity.FallingBlock;
 import org.bukkit.entity.Item;
 import org.bukkit.entity.ItemFrame;
 import org.bukkit.entity.LargeFireball;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.entity.Projectile;
 import org.bukkit.entity.Skeleton;
 import org.bukkit.entity.TNTPrimed;
 import org.bukkit.entity.ThrownPotion;
 import org.bukkit.entity.Vehicle;
 import org.bukkit.entity.WitherSkull;
 import org.bukkit.entity.Wolf;
 import org.bukkit.entity.Zombie;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.EventPriority;
 import org.bukkit.event.Listener;
 import org.bukkit.event.block.Action;
 import org.bukkit.event.block.BlockBreakEvent;
 import org.bukkit.event.block.BlockDispenseEvent;
 import org.bukkit.event.block.BlockFromToEvent;
 import org.bukkit.event.block.BlockPlaceEvent;
 import org.bukkit.event.block.SignChangeEvent;
 import org.bukkit.event.entity.EntityChangeBlockEvent;
 import org.bukkit.event.entity.EntityDamageByEntityEvent;
 import org.bukkit.event.entity.EntityDamageEvent;
 import org.bukkit.event.entity.EntityDeathEvent;
 import org.bukkit.event.entity.EntityExplodeEvent;
 import org.bukkit.event.entity.EntityInteractEvent;
 import org.bukkit.event.entity.EntityShootBowEvent;
 import org.bukkit.event.entity.ItemDespawnEvent;
 import org.bukkit.event.entity.ItemSpawnEvent;
 import org.bukkit.event.entity.PlayerDeathEvent;
 import org.bukkit.event.entity.PotionSplashEvent;
 import org.bukkit.event.entity.ProjectileHitEvent;
 import org.bukkit.event.inventory.CraftItemEvent;
 import org.bukkit.event.inventory.InventoryClickEvent;
 import org.bukkit.event.inventory.InventoryOpenEvent;
 import org.bukkit.event.inventory.InventoryPickupItemEvent;
 import org.bukkit.event.inventory.InventoryType;
 import org.bukkit.event.player.PlayerDropItemEvent;
 import org.bukkit.event.player.PlayerEggThrowEvent;
 import org.bukkit.event.player.PlayerInteractEntityEvent;
 import org.bukkit.event.player.PlayerInteractEvent;
 import org.bukkit.event.player.PlayerItemHeldEvent;
 import org.bukkit.event.player.PlayerPickupItemEvent;
 import org.bukkit.event.player.PlayerQuitEvent;
 import org.bukkit.event.vehicle.VehicleDamageEvent;
 import org.bukkit.event.vehicle.VehicleEnterEvent;
 import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
 import org.bukkit.inventory.Inventory;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.PlayerInventory;
 import org.bukkit.inventory.meta.ItemMeta;
 import org.bukkit.inventory.meta.SkullMeta;
 import org.bukkit.material.Dispenser;
 import org.bukkit.material.MaterialData;
 import org.bukkit.metadata.FixedMetadataValue;
 import org.bukkit.permissions.PermissionAttachment;
 import org.bukkit.plugin.Plugin;
 import org.bukkit.plugin.java.JavaPlugin;
 import org.bukkit.potion.PotionEffect;
 import org.bukkit.potion.PotionEffectType;
 import org.bukkit.util.BlockIterator;
 import org.bukkit.util.Vector;

 public class CSDirector
   extends JavaPlugin
   implements Listener
 {
   public Map<String, Integer[]> zoomStorage = new HashMap<>();
   public Map<String, Collection<Integer>> burst_task_IDs = new HashMap<String, Collection<Integer>>();
   public Map<String, Collection<Integer>> global_reload_IDs = new HashMap<String, Collection<Integer>>();
   public Map<String, Set<String>> grouplist = new HashMap<String, Set<String>>();
   public Map<String, Boolean> morobust = new HashMap<String, Boolean>();
   public FileConfiguration weaponConfig = null;
   public Set<String> melees = new HashSet<String>();
   public CSDirector plugin = this;
   
   public Map<String, Integer> rpm_ticks = new HashMap<>();
   public Map<String, Integer> rpm_shots = new HashMap<>();
   public Map<String, Map<Integer, Long>> last_shot_list = new HashMap<>();
   public Map<String, Map<String, String>> c4_backup = new HashMap<>();
   public Map<String, Integer> delayed_reload_IDs = new HashMap<>();
   public Map<String, Map<String, Integer>> delay_list = new HashMap<>();
   public Map<String, Map<String, ArrayDeque<Item>>> itembombs = new HashMap<>();
   public Map<String, String> convIDs = new HashMap<>();
   public Map<String, String[]> enchlist = new HashMap<>();
   public Map<String, String> parentlist = new HashMap<>();
   public Map<String, String> rdelist = new HashMap<>();
   public Map<Integer, String> wlist = new HashMap<>();
   public Map<String, String> boobs = new HashMap<>();
   
   public static Map<String, Integer> ints = new HashMap<>();
   public static Map<String, Double> dubs = new HashMap<>();
   public static Map<String, Boolean> bools = new HashMap<>();
   public static Map<String, String> strings = new HashMap<>();
   
   public String[] disWorlds = new String[] { "0" };
   public String heading = "§7░ §c[-§l¬§cº§lc§7§ls§7] §c- §7";
   public String version = "0.99";

   public final CSMinion csminion = new CSMinion(this);
 
 
   
   public void onEnable() {
     try {
       Material.valueOf("SKULL");
     } catch (IllegalArgumentException e) {
       MaterialManager.pre113 = false;
     } 
 
     
     try {
       Class.forName("org.bukkit.projectiles.ProjectileSource");
     } catch (ClassNotFoundException e) {
       printM("Failed to load. Your version of CraftBukkit is outdated!");
       setEnabled(false);
       
       return;
     } 
     this.csminion.loadWeapons(null);
     this.csminion.loadGeneralConfig();
     this.csminion.loadMessagesConfig();
     this.csminion.customRecipes();
     printM("Gun-mode activated. Boop!");
     Bukkit.getPluginManager().registerEvents(this, this);
   }
 
 
   
   public void onDisable() {
     Bukkit.getScheduler().cancelTasks(this);
     for (Player player : Bukkit.getServer().getOnlinePlayers()) {
       removeInertReloadTag(player, 0, true);
       unscopePlayer(player);
       terminateAllBursts(player);
       terminateReload(player);
     } 
 
     
     for (Map<String, ArrayDeque<Item>> subList : this.itembombs.values()) {
       for (ArrayDeque<Item> subSubList : subList.values()) {
         while (!subSubList.isEmpty()) {
           subSubList.removeFirst().remove();
         }
       } 
     } 
 
     
     this.zoomStorage.clear();
     this.burst_task_IDs.clear();
     this.global_reload_IDs.clear();
     bools.clear();
     ints.clear();
     strings.clear();
     this.morobust.clear();
     this.wlist.clear();
     this.rdelist.clear();
     this.boobs.clear();
     dubs.clear();
     this.grouplist.clear();
     this.melees.clear();
     this.enchlist.clear();
     this.convIDs.clear();
     this.parentlist.clear();
     this.itembombs.clear();
     this.last_shot_list.clear();
     this.c4_backup.clear();
     this.delayed_reload_IDs.clear();
     this.delay_list.clear();
     this.rpm_ticks.clear();
     this.rpm_shots.clear();
     CSMessages.messages.clear();
 
     
     this.csminion.clearRecipes();
   }
   
   public void fillHashMaps(FileConfiguration config) {
     for (String string : config.getKeys(true)) {
       Object obj = config.get(string);
       if (obj instanceof Boolean) {
         bools.put(string, (Boolean)obj); continue;
       }  if (obj instanceof Integer) {
         ints.put(string, (Integer)obj); continue;
       }  if (obj instanceof String) {
         obj = ((String)obj).replaceAll("&", "§");
         strings.put(string, (String)obj);
       } 
     } 
     
     for (String parent_node : config.getKeys(false)) {
       
       String[] specials = { ".Item_Information.Item_Type", ".Ammo.Ammo_Item_ID", ".Shooting.Projectile_Subtype", ".Crafting.Ingredients", ".Explosive_Devices.Device_Info", ".Airstrikes.Block_Type", ".Cluster_Bombs.Bomblet_Type", ".Shrapnel.Block_Type", ".Explosions.Damage_Multiplier" }; byte b; int i; String[] arrayOfString1;
       for (i = (arrayOfString1 = specials).length, b = 0; b < i; ) { String spec = arrayOfString1[b]; strings.put(parent_node + spec, config.getString(parent_node + spec)); b++; }
 
       
       String[] spread = { ".Shooting.Bullet_Spread", ".Sneak.Bullet_Spread", ".Scope.Zoom_Bullet_Spread" }; String[] arrayOfString2;
       for (int j = (arrayOfString2 = spread).length; i < j; ) { String spre = arrayOfString2[i]; dubs.put(parent_node + spre, Double.valueOf(config.getDouble(parent_node + spre))); i++; }
 
       
       String invCtrl = getString(parent_node + ".Item_Information.Inventory_Control");
       if (invCtrl != null) {
         
         String[] groups = invCtrl.replaceAll(" ", "").split(","); byte b1; int k; String[] arrayOfString3;
         for (k = (arrayOfString3 = groups).length, b1 = 0; b1 < k; ) { String group = arrayOfString3[b1];
           if (this.grouplist.containsKey(group)) {
             Set<String> list = this.grouplist.get(group);
                          
                    list.add(parent_node);
             this.grouplist.put(group, list);
           } else {
             Set<String> list = new HashSet<String>();
             list.add(parent_node);
             this.grouplist.put(group, list);
           } 
           
           b1++; }
       
       } 
       String enchantKey = getString(parent_node + ".Item_Information.Enchantment_To_Check");
       if (enchantKey != null) {
         String[] enchantInfo = enchantKey.split("-");
         if (enchantInfo.length == 2) {
           if (Enchantment.getByName(enchantInfo[0]) == null) {
             printM("For the weapon '" + parent_node + "', the value provided for 'Enchantment_To_Check' does not contain a valid enchantment type.");
           } else {
             try {
               Integer.valueOf(enchantInfo[1]);
               this.enchlist.put(parent_node, enchantInfo);
             } catch (NumberFormatException ex) {
               printM("For the weapon '" + parent_node + "', the value provided for 'Enchantment_To_Check' does not contain a valid enchantment level.");
             } 
           } 
         }
       } 
 
       
       boolean skipName = getBoolean(parent_node + ".Item_Information.Skip_Name_Check");
       if (skipName) {
         String itemInfo = getString(parent_node + ".Item_Information.Item_Type");
         ItemStack item = this.csminion.parseItemStack(itemInfo);
         if (item != null) {
           this.convIDs.put(item.getType() + "-" + item.getDurability(), parent_node);
         }
       } 
 
       
       boolean accessory = false;
       String attachType = getString(parent_node + ".Item_Information.Attachments.Type");
       if (attachType != null && attachType.equalsIgnoreCase("accessory")) {
         accessory = true;
       }
 
       
       if (!accessory) {
         String it = config.getString(parent_node + ".Item_Information.Item_Type");
         String itemName = config.getString(parent_node + ".Item_Information.Item_Has_Durability");
         if (it == null) {
           printM("The weapon '" + parent_node + "' does not have a value for Item_Type.");
         } else if (itemName == null && this.csminion.durabilityCheck(it)) {
           this.morobust.put(parent_node, Boolean.valueOf(true));
         } 
       } 
 
       
       List<String> commandList = config.getStringList(parent_node + ".Extras.Run_Command");
       if (!commandList.isEmpty()) {
         String stringList = "";
         String delimiter = "่๋້";
         
         for (int k = 0; k < commandList.size(); k++) {
           String command = commandList.get(k).trim();
 
           
           if (k != 0) {
             stringList = stringList + delimiter;
           }
           
           if (command.startsWith("@")) {
 
 
             
             stringList = stringList + "@" + command.substring(1).trim();
           
           }
           else {
 
             
             stringList = stringList + command.trim();
           } 
         } 
 
         
         strings.put(parent_node + ".Extras.Run_Command", stringList.replaceAll("&", "§"));
       } 
 
       
       String name = config.getString(parent_node + ".Item_Information.Item_Name");
       if (accessory) name = "§f" + parent_node; 
       if (name == null) {
         printM("The weapon '" + parent_node + "' does not have a value for Item_Name.");
       } else {
         name = name.replaceAll("&", "§");
 
         
         String colorCodes = ChatColor.getLastColors(name);
         if (colorCodes.length() > 1) {
           name = name + "§" + colorCodes.substring(colorCodes.length() - 1);
         } else if (colorCodes.length() == 0) {
           name = "§f" + name + "§f";
         } 
         
         name = toDisplayForm(name);
         String existingEntry = this.parentlist.get(name);
 
         
         if (existingEntry == null) {
           this.parentlist.put(name, parent_node);
         } else if (!accessory) {
           String nameA = config.getString(parent_node + ".Item_Information.Item_Name");
           String nameB = config.getString(existingEntry + ".Item_Information.Item_Name");
           
           String msg = "The item names of '" + parent_node + "' and '" + existingEntry + "' are too similar: ";
           msg = msg + "'" + nameA + "' and '" + nameB + "'. ";
           msg = msg + "Each weapon must have a unique value for Item_Name.";
           
           printM(msg);
         } 
         
         strings.put(parent_node + ".Item_Information.Item_Name", name);
       } 
 
       
       boolean meleeMode = getBoolean(parent_node + ".Item_Information.Melee_Mode");
       String meleeAttach = getString(parent_node + ".Item_Information.Melee_Attachment");
       if (meleeAttach != null || meleeMode || (attachType != null && attachType.equalsIgnoreCase("main"))) {
         this.melees.add(parent_node);
       }
 
       
       if (config.getBoolean(parent_node + ".Explosive_Devices.Enable")) {
         String rdeOre = config.getString(parent_node + ".Explosive_Devices.Device_Info");
         if (rdeOre != null) {
           String[] rdeRefined = rdeOre.split("-");
           if (rdeRefined.length == 3) this.rdelist.put(rdeRefined[1], parent_node);
         
         } 
       } 
       
       if (config.getBoolean(parent_node + ".Explosive_Devices.Enable")) {
         String rdeInfo = config.getString(parent_node + ".Explosive_Devices.Device_Type");
         if (rdeInfo != null && rdeInfo.equalsIgnoreCase("trap")) {
           String itemName = getString(parent_node + ".Item_Information.Item_Name");
           String displayName = toDisplayForm(itemName);
           
           this.boobs.put(displayName, parent_node);
         } 
       } 
     } 
   }
 
 
   
   public boolean onCommand(CommandSender sender, Command command, String aliasUsed, String[] args) {
     if (!command.getName().equalsIgnoreCase("crackshot")) {
       return false;
     }

     if (args.length == 2 && 
       args[0].equalsIgnoreCase("config") && args[1].equalsIgnoreCase("reload")) {
       if (sender instanceof Player && !sender.hasPermission("crackshot.reloadplugin")) {
         sender.sendMessage(this.heading + "You do not have permission to do this.");
         return true;
       }

       this.disWorlds = new String[] { "0" };
       this.csminion.clearRecipes();

       bools.clear();
       ints.clear();
       strings.clear();
       this.morobust.clear();
       this.wlist.clear();
       this.rdelist.clear();
       this.boobs.clear();
       dubs.clear();
       this.grouplist.clear();
       this.melees.clear();
       this.enchlist.clear();
       this.convIDs.clear();
       this.parentlist.clear();
       CSMessages.messages.clear();
       
       Player cmdReloader = (sender instanceof Player) ? (Player)sender : null;
       this.csminion.loadWeapons(cmdReloader);
       this.csminion.loadGeneralConfig();
       this.csminion.loadMessagesConfig();
       this.csminion.customRecipes();
       
       if (sender instanceof Player) {
         sender.sendMessage(this.heading + "Configuration reloaded.");
       } else {
         printM("Configuration reloaded.");
       } 
       return true;
     } 
 
     
     if (args.length == 0) {
       if (!(sender instanceof Player)) {
         sender.sendMessage("[CrackShot] Version: " + this.version);
         return true;
       } 
       
       sender.sendMessage("§7░ §c§l------§r§c[ -§l¬§cºcrack§7shot §c]§l------");
       sender.sendMessage("§7░ §cAuthors: §7Shampaggon, videogamesm12");
       sender.sendMessage("§7░ §cVersion: §7" + this.version);
       sender.sendMessage("§7░ §cAliases: §7/shot, /cra, /cs");
       sender.sendMessage("§7░ §cCommands:");
       sender.sendMessage("§7░ §c- §7/shot list");
       sender.sendMessage("§7░ §c- §7/shot give <user> <weapon> <#>");
       sender.sendMessage("§7░ §c- §7/shot get <weapon> <#>");
       sender.sendMessage("§7░ §c- §7/shot reload");
       sender.sendMessage("§7░ §c- §7/shot config reload");
       return true;
     }

     if ((args.length == 3 || args.length == 4) && 
       args[0].equalsIgnoreCase("give")) {
       String prefix = this.heading;
       String amount = "1";
       
       if (!(sender instanceof Player)) {
         prefix = "[CrackShot] ";
       }
       
       if (args.length == 4) {
         amount = args[3];
       }
       
       String parent_node = this.csminion.identifyWeapon(args[2]);
       if (parent_node == null) {
         sender.sendMessage(prefix + "No weapon matches '" + args[2] + "'.");
         return false;
       } 
       
       if (sender instanceof Player && !sender.hasPermission("crackshot.give." + parent_node) && !sender.hasPermission("crackshot.give.all")) {
         sender.sendMessage(prefix + "You do not have permission to give this item.");
         return false;
       } 
       
       Player receiver = Bukkit.getServer().getPlayer(args[1]);
       if (receiver != null) {
         if (receiver.getInventory().firstEmpty() != -1) {
           sender.sendMessage(prefix + "Package delivered to " + receiver.getName() + ".");
           this.csminion.getWeaponCommand(receiver, parent_node, false, amount, true, false);
           return true;
         } 
         sender.sendMessage(prefix + receiver.getName() + "'s inventory is full.");
         return false;
       } 
       
       sender.sendMessage(prefix + "No player named '" + args[1] + "' was found.");
       return false;
     } 

     if (!(sender instanceof Player player)) {
       sender.sendMessage("[CrackShot] Invalid command.");
       return false;
     }
     
     if ((args.length == 2 || args.length == 3) && args[0].equalsIgnoreCase("get")) {
       String amount = "1";
       if (args.length == 3) {
         amount = args[2];
       }
       this.csminion.getWeaponCommand(player, args[1], true, amount, false, false);
       return true;
     } 

     if (args.length == 1)
     {
       if (args[0].equalsIgnoreCase("reload")) {
         String parent_node = returnParentNode(player);
         if (parent_node == null) {
           CSMessages.sendMessage(player, this.heading, CSMessages.Message.CANNOT_RELOAD.getMessage());
           return true;
         } 
         
         if (!player.hasPermission("crackshot.use." + parent_node) && !player.hasPermission("crackshot.use.all")) {
           CSMessages.sendMessage(player, this.heading, CSMessages.Message.NP_WEAPON_USE.getMessage());
           return false;
         } 
         
         reloadAnimation(player, parent_node);
         return true;
       } 
     }

     if ((args.length == 1 || args.length == 2) && args[0].equalsIgnoreCase("list")) {
       if (!player.hasPermission("crackshot.list")) {
         player.sendMessage(this.heading + "You do not have permission to do this.");
         return false;
       } 
       this.csminion.listWeapons(player, args);
       return true;
     } 
     
     player.sendMessage(this.heading + "Invalid command.");
     
     return false;
   }
 
   
   @EventHandler
   public void OnPlayerInteract(PlayerInteractEvent event) {
     if (event.getAction() != Action.PHYSICAL) {
 
       
       if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getType().toString().contains("WALL_SIGN") && shopEvent(event)) {
         return;
       }
       if (event.getAction() == Action.LEFT_CLICK_BLOCK && MaterialManager.isSkullBlock(event.getClickedBlock()) && event.getClickedBlock().hasMetadata("CS_transformers")) {
         event.setCancelled(true);
       }
       
       Player shooter = event.getPlayer();
       ItemStack item = shooter.getItemInHand();
       String parent_node = returnParentNode(shooter);
       if (parent_node == null) {
         return;
       }
       if (!getBoolean(parent_node + ".Item_Information.Melee_Mode") && !validHotbar(shooter, parent_node)) {
         return;
       }
       if (!regionAndPermCheck(shooter, parent_node, false)) {
         return;
       }
       boolean rightShoot = getBoolean(parent_node + ".Shooting.Right_Click_To_Shoot");
       boolean dualWield = isDualWield(shooter, parent_node, item);
       boolean leftClick = !(event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK);
       boolean rightClick = !(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK);
 
       
       boolean rdeEnable = getBoolean(parent_node + ".Explosive_Devices.Enable");
       String[] attachTypeAndInfo = getAttachment(parent_node, item);
       if (attachTypeAndInfo[0] != null) {
         if (attachTypeAndInfo[0].equalsIgnoreCase("accessory") && rdeEnable) {
           shooter.sendMessage(this.heading + "The weapon '" + parent_node + "' is an attachment. It cannot use the Explosive_Devices module!"); return;
         } 
         if (dualWield) {
           shooter.sendMessage(this.heading + "The weapon '" + parent_node + "' cannot use attachments and be dual wielded at the same time!");
           
           return;
         } 
       } 
       
       if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
         boolean noBlockDmg = getBoolean(parent_node + ".Shooting.Cancel_Left_Click_Block_Damage");
         if (noBlockDmg) event.setCancelled(true);
       
       } 
       if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
         boolean rightInteract = getBoolean(parent_node + ".Shooting.Cancel_Right_Click_Interactions");
         if (rightInteract) event.setCancelled(true);
       } 
       
       if (!item.getItemMeta().getDisplayName().contains("§")) {
         shooter.setItemInHand(this.csminion.vendingMachine(parent_node));
         event.setCancelled(true);
         
         return;
       } 
       
       if (!getBoolean(parent_node + ".Item_Information.Remove_Unused_Tag")) {
         checkCorruption(item, (attachTypeAndInfo[0] != null), dualWield);
       }
 
       
       if ((rightShoot && rightClick) || (!rightShoot && leftClick) || dualWield) {
         if (rdeEnable) {
           String type = getString(parent_node + ".Explosive_Devices.Device_Type");
           if (type != null) {
             if (type.equalsIgnoreCase("remote") || type.equalsIgnoreCase("itembomb")) {
               detonateC4(shooter, item, parent_node, type);
             } else if (type.equalsIgnoreCase("trap") && itemIsSafe(item) && item.getItemMeta().getDisplayName().contains("«?»")) {
               String itemName = getString(parent_node + ".Item_Information.Item_Name");
               this.csminion.setItemName(shooter.getInventory().getItemInHand(), itemName + " «" + shooter.getName() + "»");
               playSoundEffects(shooter, parent_node, ".Explosive_Devices.Sounds_Deploy", false, null);
             } else if (type.equalsIgnoreCase("landmine")) {
               this.csminion.oneTime(shooter);
               playSoundEffects(shooter, parent_node, ".Explosive_Devices.Sounds_Deploy", false, null);
               deployMine(shooter, parent_node, null);
             } 
           }
         } else if (item.getType() != Material.BOW) {
           this.csminion.weaponInteraction(shooter, parent_node, leftClick);
         }
       
       } else if (!dualWield && ((rightShoot && leftClick) || (!rightShoot && rightClick))) {
 
         
         if (getBoolean(parent_node + ".Reload.Reload_With_Mouse")) {
           reloadAnimation(shooter, parent_node);
           return;
         } 
         
         if (tossBomb(shooter, parent_node, item, rdeEnable)) {
           return;
         }
         if (attachTypeAndInfo[0] != null) {
           
           int gunSlot = shooter.getInventory().getHeldItemSlot();
           boolean hasDelay = shooter.hasMetadata("togglesnoShooting" + gunSlot);
           if (hasDelay)
             return; 
           boolean main = attachTypeAndInfo[0].equalsIgnoreCase("main");
           boolean accessory = attachTypeAndInfo[0].equalsIgnoreCase("accessory");
           if (main || accessory) {
 
             
             if (main) {
               String attachValid = getString(attachTypeAndInfo[1] + ".Item_Information.Attachments.Type");
               if (attachTypeAndInfo[1] == null) {
                 shooter.sendMessage(this.heading + "The weapon '" + parent_node + "' is missing the weapon title of an attachment!"); return;
               } 
               if (attachValid == null) {
                 shooter.sendMessage(this.heading + "The weapon '" + parent_node + "' has an invalid attachment. The weapon '" + attachTypeAndInfo[1] + "' has to be an accessory!");
                 
                 return;
               } 
             } 
             int toggleDelay = getInt(parent_node + ".Item_Information.Attachments.Toggle_Delay");
 
             
             WeaponAttachmentToggleEvent toggleEvent = new WeaponAttachmentToggleEvent(shooter, parent_node, item, toggleDelay);
             getServer().getPluginManager().callEvent(toggleEvent);
             if (toggleEvent.isCancelled())
               return; 
             playSoundEffects(shooter, parent_node, ".Item_Information.Attachments.Sounds_Toggle", false, null);
             reloadShootDelay(shooter, parent_node, gunSlot, toggleEvent.getToggleDelay(), "noShooting", "toggles");
             
             terminateAllBursts(shooter);
             terminateReload(shooter);
             removeInertReloadTag(shooter, 0, true);
             
             if (itemIsSafe(item)) {
               String itemName = item.getItemMeta().getDisplayName();
               String triOne = String.valueOf('▶'), triTwo = String.valueOf('▷'), triThree = String.valueOf('◀'), triFour = String.valueOf('◁');
               if (itemName.contains(triThree)) {
                 this.csminion.setItemName(item, itemName.replaceAll(triThree + triTwo, triFour + triOne));
               } else {
                 this.csminion.setItemName(item, itemName.replaceAll(triFour + triOne, triThree + triTwo));
               } 
             } 
             
             return;
           } 
         } 
         
         boolean zoomEnable = getBoolean(parent_node + ".Scope.Enable");
         boolean nightScope = getBoolean(parent_node + ".Scope.Night_Vision");
         if (!zoomEnable || shooter.hasMetadata("markOfTheReload")) {
           return;
         }
         int zoomAmount = getInt(parent_node + ".Scope.Zoom_Amount");
         if (zoomAmount < 0 || zoomAmount == 0 || zoomAmount > 10) {
           return;
         }
         WeaponScopeEvent scopeEvent = new WeaponScopeEvent(shooter, parent_node, !shooter.hasMetadata("ironsights"));
         getServer().getPluginManager().callEvent(scopeEvent);
         if (scopeEvent.isCancelled()) {
           return;
         }
         playSoundEffects(shooter, parent_node, ".Scope.Sounds_Toggle_Zoom", false, null);
 
         
         if (shooter.hasPotionEffect(PotionEffectType.SPEED)) {
           for (PotionEffect pe : shooter.getActivePotionEffects()) {
             if (pe.getType().toString().contains("SPEED")) {
               
               if (shooter.hasMetadata("ironsights")) {
                 unscopePlayer(shooter, true);
                 break;
               } 
               if (!shooter.hasPotionEffect(PotionEffectType.NIGHT_VISION) && nightScope) {
                 shooter.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(2400, -1));
                 shooter.setMetadata("night_scoping", new FixedMetadataValue(this, Boolean.valueOf(true)));
               } 
 
               
               shooter.setMetadata("ironsights", new FixedMetadataValue(this, parent_node));
               this.zoomStorage.put(shooter.getName(), new Integer[] { pe.getDuration(), pe.getAmplifier() });
               shooter.removePotionEffect(PotionEffectType.SPEED);
               shooter.addPotionEffect(PotionEffectType.SPEED.createEffect(2400, -zoomAmount));
 
               
               break;
             } 
           } 
         } else {
           if (!shooter.hasPotionEffect(PotionEffectType.NIGHT_VISION) && nightScope) {
             shooter.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(2400, -1));
             shooter.setMetadata("night_scoping", new FixedMetadataValue(this, Boolean.valueOf(true)));
           } 
 
           
           shooter.setMetadata("ironsights", new FixedMetadataValue(this, parent_node));
           shooter.addPotionEffect(PotionEffectType.SPEED.createEffect(2400, -zoomAmount));
         } 
       } 
     } else if (MaterialManager.isPressurePlate(event.getClickedBlock())) {
       Player victim = event.getPlayer();
       List<Entity> l = victim.getNearbyEntities(4.0D, 4.0D, 4.0D);
       for (Entity e : l) {
         if (e instanceof ItemFrame) {
           this.csminion.boobyAction(event.getClickedBlock(), victim, ((ItemFrame)e).getItem());
         }
       } 
     } 
   }
 
 
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void onDamage(EntityDamageByEntityEvent event) {
     Entity entVictim = event.getEntity();
     Entity entDmger = event.getDamager();
     boolean cancelMelee = false;
 
     
     if (entVictim instanceof Player && entVictim.hasMetadata("CS_singed")) {
       cancelMelee = true;
       if (!event.isCancelled()) {
         entVictim.setMetadata("CS_singed", new FixedMetadataValue(this, Boolean.valueOf(true)));
         event.setCancelled(true);
       } else {
         entVictim.removeMetadata("CS_singed", this);
       } 
     } 
 
     
     if (entVictim instanceof Player && entVictim.hasMetadata("deep_fr1ed")) {
       cancelMelee = true;
       String parent_node = null;
       Player pPlayer = null;
       boolean nodam = false;
       Player victim = (Player)entVictim;
       int damage = victim.getMetadata("deep_fr1ed").get(0).asInt();
       victim.removeMetadata("deep_fr1ed", this);
       if (victim.hasMetadata("CS_nodam")) nodam = true; 
       if (victim.hasMetadata("CS_potex") && victim.getMetadata("CS_potex") != null) parent_node = victim.getMetadata("CS_potex").get(0).asString();
       if (entDmger instanceof Player) pPlayer = (Player)entDmger;
       
       victim.removeMetadata("CS_potex", this);
       if (!event.isCancelled()) {
         this.csminion.explosionPackage(victim, parent_node, pPlayer);
         if (!nodam) {
           event.setDamage(damage);
         } else {
           event.setCancelled(true);
         } 
       } 
     } 
 
     
     if (entDmger instanceof Player && entVictim instanceof LivingEntity) {
       Player player = (Player)entDmger;
       Location finalLoc = player.getEyeLocation();
       Vector direction = player.getEyeLocation().getDirection().normalize().multiply(0.5D);
       for (int i = 0; i < 10; i++) {
         finalLoc.add(direction);
         if (!isAir(finalLoc.getBlock().getType())) {
           OnPlayerInteract(new PlayerInteractEvent(player, Action.LEFT_CLICK_AIR, player.getItemInHand(), null, null));
           
           break;
         } 
       } 
     } 
     
     if (entVictim instanceof LargeFireball && entVictim.hasMetadata("CS_NoDeflect")) {
       event.setCancelled(true);
       
       return;
     } 
     
     if (entDmger instanceof Player && event.getDamage() == 8.0D && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
       List<Entity> witherNet = entVictim.getNearbyEntities(4.0D, 4.0D, 4.0D);
       for (Entity closeEnt : witherNet) {
         if (closeEnt instanceof WitherSkull && ((Projectile)closeEnt).getShooter() == entDmger) event.setCancelled(true);
       
       } 
     } 
     
     if (!cancelMelee && entDmger instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && !event.isCancelled() && entVictim instanceof LivingEntity) {
       Player player = (Player)entDmger;
       String parentNode = returnParentNode(player);
       if (parentNode != null && regionAndPermCheck(player, parentNode, true)) {
         int punchDelay = getInt(parentNode + ".Shooting.Delay_Between_Shots");
         int gunSlot = player.getInventory().getHeldItemSlot();
         if (!player.hasMetadata(parentNode + "meleeDelay" + gunSlot)) {
           if (getBoolean(parentNode + ".Item_Information.Melee_Mode")) {
             ItemStack item = player.getItemInHand();
             String ammoInfo = getString(parentNode + ".Ammo.Ammo_Item_ID");
             boolean reloadOn = getBoolean(parentNode + ".Reload.Enable");
             boolean ammoPerShot = getBoolean(parentNode + ".Ammo.Take_Ammo_Per_Shot");
             boolean ammoEnable = getBoolean(parentNode + ".Ammo.Enable");
             boolean takeAmmo = getBoolean(parentNode + ".Reload.Take_Ammo_On_Reload");
             int detectedAmmo = getAmmoBetweenBrackets(player, parentNode, item);
             
             if (!validHotbar(player, parentNode))
               return; 
             player.setMetadata(parentNode + "meleeDelay" + gunSlot, new FixedMetadataValue(this, Boolean.valueOf(true)));
             this.csminion.tempVars(player, parentNode + "meleeDelay" + gunSlot, Long.valueOf(punchDelay));
             
             if (ammoEnable) {
               if (!takeAmmo && !ammoPerShot) {
                 player.sendMessage(this.heading + "The weapon '" + parentNode + "' has enabled the Ammo module, but at least one of the following nodes need to be set to true: Take_Ammo_On_Reload, Take_Ammo_Per_Shot."); return;
               } 
               if (!this.csminion.containsItemStack(player, ammoInfo, 1, parentNode) && (ammoPerShot || (takeAmmo && detectedAmmo == 0))) {
                 playSoundEffects(player, parentNode, ".Ammo.Sounds_Shoot_With_No_Ammo", false, null);
                 
                 return;
               } 
             } 
             if (itemIsSafe(item) && item.getItemMeta().getDisplayName().contains(String.valueOf('ᴿ'))) {
               if (detectedAmmo > 0) {
                 terminateReload(player);
                 removeInertReloadTag(player, 0, true);
               } else {
                 reloadAnimation(player, parentNode);
                 
                 return;
               } 
             }
             if (reloadOn) {
               if (detectedAmmo > 0) {
                 ammoOperation(player, parentNode, detectedAmmo, item);
               } else {
                 reloadAnimation(player, parentNode);
                 return;
               } 
             } else {
               String itemName = item.getItemMeta().getDisplayName();
               if (itemName.contains("«") && !itemName.contains(String.valueOf('×'))) {
                 this.csminion.replaceBrackets(item, String.valueOf('×'), parentNode);
               }
             } 
             
             dealDamage(player, (LivingEntity)entVictim, event, parentNode);
           } else {
             String meleeNode = getString(parentNode + ".Item_Information.Melee_Attachment");
             if (meleeNode != null) {
               punchDelay = getInt(meleeNode + ".Shooting.Delay_Between_Shots");
               if (this.melees.contains(meleeNode)) {
                 if (validHotbar(player, parentNode)) {
                   player.setMetadata(parentNode + "meleeDelay" + gunSlot, new FixedMetadataValue(this, Boolean.valueOf(true)));
                   this.csminion.tempVars(player, parentNode + "meleeDelay" + gunSlot, Long.valueOf(punchDelay));
                   dealDamage(player, (LivingEntity)entVictim, event, meleeNode);
                 } 
               } else {
                 player.sendMessage(this.heading + "The weapon '" + parentNode + "' has an unknown melee attachment. '" + meleeNode + "' could not be found!");
               } 
             } 
           } 
         } else {
           event.setCancelled(true);
         } 
       } 
     } 
 
     
     if ((entDmger instanceof WitherSkull || entDmger instanceof LargeFireball) && entDmger.hasMetadata("projParentNode")) {
       event.setCancelled(true);
     }
 
     
     if (entDmger instanceof Player && entVictim instanceof Player && entVictim.hasMetadata("CS_Energy") && !event.isCancelled()) {
       dealDamage(entDmger, (LivingEntity)entVictim, event, entVictim.getMetadata("CS_Energy").get(0).asString());
       entVictim.removeMetadata("CS_Energy", this);
     } 
 
     
     if ((entDmger instanceof org.bukkit.entity.Arrow || entDmger instanceof org.bukkit.entity.Egg || entDmger instanceof org.bukkit.entity.Snowball) && 
       entDmger.hasMetadata("projParentNode") && entVictim instanceof LivingEntity && !event.isCancelled()) {
       dealDamage(entDmger, (LivingEntity)entVictim, event, entDmger.getMetadata("projParentNode").get(0).asString());
     }
 
 
     
     if (entDmger instanceof TNTPrimed && entDmger.hasMetadata("CS_Label")) {
       
       if (entDmger.hasMetadata("nullify") && (
         entVictim instanceof org.bukkit.entity.Painting || entVictim instanceof ItemFrame || entVictim instanceof org.bukkit.entity.ArmorStand || entVictim instanceof Item || entVictim instanceof ExperienceOrb)) {
         event.setCancelled(true);
       }
 
 
       
       if (entDmger.hasMetadata("CS_nodam") || entVictim.hasMetadata("CS_shrapnel")) {
         
         if (entVictim instanceof Player) {
           entVictim.setMetadata("CS_nodam", new FixedMetadataValue(this, Boolean.valueOf(true)));
           this.csminion.tempVars((Player)entVictim, "CS_nodam", Long.valueOf(2L));
         } 
         event.setCancelled(true);
       } 
 
       
       String parent_node = null;
       double totalDmg = event.getDamage();
       if (entDmger.hasMetadata("CS_potex")) {
         parent_node = entDmger.getMetadata("CS_potex").get(0).asString();
 
         
         if (event.getDamage() > 1.0D && parent_node != null) {
           try {
             String multiString = getString(parent_node + ".Explosions.Damage_Multiplier");
             if (multiString != null) {
               double multiplier = Integer.valueOf(multiString).intValue() * 0.01D;
               totalDmg *= multiplier;
               totalDmg = this.csminion.getSuperDamage(entVictim.getType(), parent_node, totalDmg);
             } 
           } catch (IllegalArgumentException illegalArgumentException) {}
         }
       } 
 
       
       int knockBack = getInt(parent_node + ".Explosions.Knockback");
       if (knockBack != 0 && !entVictim.hasMetadata("CS_shrapnel")) {
         Vector vector = this.csminion.getAlignedDirection(entDmger.getLocation(), entVictim.getLocation());
         entVictim.setVelocity(vector.multiply(knockBack * 0.1D));
       } 
 
       
       String pName = "Player";
       Player pPlayer = null;
       if (entDmger.hasMetadata("CS_pName")) {
         pName = entDmger.getMetadata("CS_pName").get(0).asString();
         pPlayer = Bukkit.getServer().getPlayer(pName);
       } 
 
       
       boolean noDam = (entVictim instanceof Player && entDmger.hasMetadata("0wner_nodam") && entVictim.getName().equals(pName));
       if (noDam) {
         totalDmg = 0.0D;
       }
 
       
       WeaponDamageEntityEvent weaponEvent = new WeaponDamageEntityEvent(pPlayer, entVictim, entDmger, parent_node, totalDmg, false, false, false);
       getServer().getPluginManager().callEvent(weaponEvent);
       event.setDamage(weaponEvent.getDamage());
       
       if (weaponEvent.isCancelled()) {
         event.setCancelled(true);
       } else if (entVictim instanceof Player victim) {

            
         if (noDam) {
           event.setCancelled(true);
           
           return;
         } 
         victim.setNoDamageTicks(0);
         
         if (entDmger.hasMetadata("CS_ffcheck")) {
           if (victim.getName().equals(pName)) {
             this.csminion.explosionPackage(victim, parent_node, pPlayer);
           } else if (pPlayer != null) {
             victim.setMetadata("deep_fr1ed", new FixedMetadataValue(this, Double.valueOf(event.getDamage())));
             if (parent_node != null) victim.setMetadata("CS_potex", new FixedMetadataValue(this, parent_node));
             this.csminion.illegalSlap(pPlayer, victim, 0);
             event.setCancelled(true);
           } 
         } else {
           
           this.csminion.explosionPackage(victim, parent_node, pPlayer);
         } 
       } else if (entVictim instanceof LivingEntity) {
         ((LivingEntity)entVictim).setNoDamageTicks(0);
         this.csminion.explosionPackage((LivingEntity)entVictim, parent_node, pPlayer);
       } 
     } 
 
     
     if (entVictim instanceof Player blocker && !event.isCancelled()) {

        
       String parentNode = returnParentNode(blocker);
       if (parentNode == null)
         return; 
       int durabPerHit = getInt(parentNode + ".Riot_Shield.Durability_Loss_Per_Hit");
       boolean riotEnable = getBoolean(parentNode + ".Riot_Shield.Enable");
       boolean durabDmg = getBoolean(parentNode + ".Riot_Shield.Durablity_Based_On_Damage");
       boolean noProj = getBoolean(parentNode + ".Riot_Shield.Do_Not_Block_Projectiles");
       boolean noMelee = getBoolean(parentNode + ".Riot_Shield.Do_Not_Block_Melee_Attacks");
       boolean forceField = getBoolean(parentNode + ".Riot_Shield.Forcefield_Mode");
       boolean mustBlock = getBoolean(parentNode + ".Riot_Shield.Only_Works_While_Blocking");
 
       
       if (mustBlock && !blocker.isBlocking()) {
         return;
       }
       if (!riotEnable || !regionAndPermCheck(blocker, parentNode, false))
         return; 
       if (entDmger instanceof Projectile) {
         
         if (noProj) {
           return;
         }
         if (!forceField) {
           Projectile objProj = (Projectile)entDmger;
           double faceAngle = blocker.getLocation().getDirection().dot(((Entity)objProj.getShooter()).getLocation().getDirection());
           if (faceAngle > 0.0D)
           {
             if (!(objProj.getShooter() instanceof Skeleton))
               return; 
           }
         } 
       } else {
         if (noMelee) {
           return;
         }
         if (!forceField) {
           double faceAngle = blocker.getLocation().getDirection().dot(entDmger.getLocation().getDirection());
           if (faceAngle > 0.0D) {
             return;
           }
         } 
       } 
       if (durabDmg) durabPerHit = (int)(durabPerHit * event.getDamage());
       
       ItemStack shield = blocker.getInventory().getItemInHand();
       shield.setDurability((short)(shield.getDurability() + durabPerHit));
       playSoundEffects(blocker, parentNode, ".Riot_Shield.Sounds_Blocked", false, null);
       
       if (shield.getType().getMaxDurability() <= shield.getDurability()) {
         playSoundEffects(blocker, parentNode, ".Riot_Shield.Sounds_Break", false, null);
         blocker.getInventory().clear(blocker.getInventory().getHeldItemSlot());
         blocker.updateInventory();
       } 
       
       event.setCancelled(true);
     } 
   }
   
   public void dealDamage(Entity entDmger, LivingEntity victim, EntityDamageByEntityEvent event, String parent_node) {
     Player shooter;
     boolean energyMode = (entDmger instanceof Player);
 
     
     Projectile objProj = null;
     if (!energyMode) {
       objProj = (Projectile)entDmger;
       shooter = (Player)objProj.getShooter();
       objProj.setMetadata("Collided", new FixedMetadataValue(this, Boolean.valueOf(true)));
     } else {
       shooter = (Player)entDmger;
     } 
 
     
     if (shooter == null)
       return; 
     double projSpeed = getInt(parent_node + ".Shooting.Projectile_Speed") * 0.1D;
     boolean hitEnable = getBoolean(parent_node + ".Hit_Events.Enable");
     boolean headShots = getBoolean(parent_node + ".Headshot.Enable");
     boolean bsEnable = getBoolean(parent_node + ".Backstab.Enable");
     boolean critEnable = getBoolean(parent_node + ".Critical_Hits.Enable");
     boolean fireEnable = getBoolean(parent_node + ".Shooting.Projectile_Incendiary.Enable");
     int fireDuration = getInt(parent_node + ".Shooting.Projectile_Incendiary.Duration");
     boolean zapEnable = getBoolean(parent_node + ".Lightning.Enable");
     boolean resetHits = getBoolean(parent_node + ".Abilities.Reset_Hit_Cooldown");
     boolean flightEnable = getBoolean(parent_node + ".Damage_Based_On_Flight_Time.Enable");
     String makeSpeak = getString(parent_node + ".Extras.Make_Victim_Speak");
     String makeRunCmd = getString(parent_node + ".Extras.Make_Victim_Run_Commmand");
     String runConsole = getString(parent_node + ".Extras.Run_Console_Command");
     int knockBack = getInt(parent_node + ".Abilities.Knockback");
     String bonusDrops = getString(parent_node + ".Abilities.Bonus_Drops");
     int activTime = getInt(parent_node + ".Explosions.Projectile_Activation_Time");
 
     
     int projFlight = 0;
     double projTotalDmg = getInt(parent_node + ".Shooting.Projectile_Damage");
 
     
     boolean BS = false;
     boolean crit = false;
     boolean boomHS = false;
 
     
     if (flightEnable && !energyMode) {
       int dmgPerTick = getInt(parent_node + ".Damage_Based_On_Flight_Time.Bonus_Damage_Per_Tick");
       int flightMax = getInt(parent_node + ".Damage_Based_On_Flight_Time.Maximum_Damage");
       int flightMin = getInt(parent_node + ".Damage_Based_On_Flight_Time.Minimum_Damage");
       boolean negDmg = (dmgPerTick < 0 && flightMax < 0);
       
       projFlight = objProj.getTicksLived();
       int tickDmgTotal = projFlight * dmgPerTick;
       
       if (tickDmgTotal < flightMin && flightMin != 0) tickDmgTotal = 0; 
       if (!negDmg) {
         if (tickDmgTotal > flightMax && flightMax != 0) tickDmgTotal = flightMax; 
       } else if (tickDmgTotal < flightMax && flightMax != 0) {
         tickDmgTotal = flightMax;
       } 
       
       projTotalDmg += tickDmgTotal;
     } 
 
     
     if (bsEnable) {
       int bsBonusDmg = getInt(parent_node + ".Backstab.Bonus_Damage");
       
       double faceAngle = victim.getLocation().getDirection().dot(shooter.getLocation().getDirection());
       if (faceAngle > 0.0D) {
         BS = true;
         projTotalDmg += bsBonusDmg;
       } 
     } 
 
     
     if (critEnable) {
       int critBonus = getInt(parent_node + ".Critical_Hits.Bonus_Damage");
       int critChance = getInt(parent_node + ".Critical_Hits.Chance");
       
       Random ranGen = new Random();
       int Chance = ranGen.nextInt(100);
       if (Chance <= critChance) {
         crit = true;
         projTotalDmg += critBonus;
       } 
     } 
 
     
     if (headShots && !energyMode && this.csminion.isHesh(objProj, victim, projSpeed)) {
       boomHS = true;
       projTotalDmg += getInt(parent_node + ".Headshot.Bonus_Damage");
     } 
 
     
     projTotalDmg = this.csminion.getSuperDamage(victim.getType(), parent_node, projTotalDmg);
 
     
     if (projTotalDmg < 0.0D) projTotalDmg = 0.0D;
 
     
     WeaponDamageEntityEvent weaponEvent = new WeaponDamageEntityEvent(shooter, victim, objProj, parent_node, projTotalDmg, boomHS, BS, crit);
     getServer().getPluginManager().callEvent(weaponEvent);
 
     
     if (weaponEvent.isCancelled()) {
       if (event != null) {
         event.setCancelled(true);
       }
       
       return;
     } 
     
     if (resetHits) setTempVulnerability(victim);
 
     
     if (event != null) {
       event.setDamage(weaponEvent.getDamage());
     } else {
       victim.damage(weaponEvent.getDamage(), shooter);
     } 
 
     
     if (knockBack != 0) victim.setVelocity(shooter.getLocation().getDirection().multiply(knockBack));
 
     
     if (energyMode || objProj.getTicksLived() >= activTime) {
       projectileExplosion(victim, parent_node, false, shooter, false, false, null, null, false, 0);
     }
 
     
     if (zapEnable) {
       boolean zapNoDmg = getBoolean(parent_node + ".Lightning.No_Damage");
       this.csminion.projectileLightning(victim.getLocation(), zapNoDmg);
     } 
 
     
     if (fireEnable && fireDuration != 0) victim.setFireTicks(fireDuration);
 
     
     String flyTime = String.valueOf(projFlight);
     String dmgTotal = String.valueOf(projTotalDmg);
     String nameShooter = shooter.getName();
     String nameVic = "Entity";
 
     
     if (victim instanceof Player) {
       nameVic = victim.getName();
     } else if (victim instanceof LivingEntity) {
       nameVic = victim.getType().getName();
     } 
     
     if (boomHS) {
       sendPlayerMessage(shooter, parent_node, ".Headshot.Message_Shooter", nameShooter, nameVic, flyTime, dmgTotal);
       sendPlayerMessage(victim, parent_node, ".Headshot.Message_Victim", nameShooter, nameVic, flyTime, dmgTotal);
       playSoundEffects(shooter, parent_node, ".Headshot.Sounds_Shooter", false, null);
       playSoundEffects(victim, parent_node, ".Headshot.Sounds_Victim", false, null);
       this.csminion.giveParticleEffects(victim, parent_node, ".Particles.Particle_Headshot", false, null);
       this.csminion.displayFireworks(victim, parent_node, ".Fireworks.Firework_Headshot");
       this.csminion.givePotionEffects(shooter, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "head");
       this.csminion.givePotionEffects(victim, parent_node, ".Potion_Effects.Potion_Effect_Victim", "head");
     } 
     
     if (crit) {
       sendPlayerMessage(shooter, parent_node, ".Critical_Hits.Message_Shooter", nameShooter, nameVic, flyTime, dmgTotal);
       sendPlayerMessage(victim, parent_node, ".Critical_Hits.Message_Victim", nameShooter, nameVic, flyTime, dmgTotal);
       playSoundEffects(shooter, parent_node, ".Critical_Hits.Sounds_Shooter", false, null);
       playSoundEffects(victim, parent_node, ".Critical_Hits.Sounds_Victim", false, null);
       this.csminion.giveParticleEffects(victim, parent_node, ".Particles.Particle_Critical", false, null);
       this.csminion.displayFireworks(victim, parent_node, ".Fireworks.Firework_Critical");
       this.csminion.givePotionEffects(shooter, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "crit");
       this.csminion.givePotionEffects(victim, parent_node, ".Potion_Effects.Potion_Effect_Victim", "crit");
     } 
     
     if (BS) {
       sendPlayerMessage(shooter, parent_node, ".Backstab.Message_Shooter", nameShooter, nameVic, flyTime, dmgTotal);
       sendPlayerMessage(victim, parent_node, ".Backstab.Message_Victim", nameShooter, nameVic, flyTime, dmgTotal);
       playSoundEffects(shooter, parent_node, ".Backstab.Sounds_Shooter", false, null);
       playSoundEffects(victim, parent_node, ".Backstab.Sounds_Victim", false, null);
       this.csminion.giveParticleEffects(victim, parent_node, ".Particles.Particle_Backstab", false, null);
       this.csminion.displayFireworks(victim, parent_node, ".Fireworks.Firework_Backstab");
       this.csminion.givePotionEffects(shooter, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "back");
       this.csminion.givePotionEffects(victim, parent_node, ".Potion_Effects.Potion_Effect_Victim", "back");
     } 
     
     if (hitEnable) {
       sendPlayerMessage(shooter, parent_node, ".Hit_Events.Message_Shooter", nameShooter, nameVic, flyTime, dmgTotal);
       sendPlayerMessage(victim, parent_node, ".Hit_Events.Message_Victim", nameShooter, nameVic, flyTime, dmgTotal);
       playSoundEffects(shooter, parent_node, ".Hit_Events.Sounds_Shooter", false, null);
       playSoundEffects(victim, parent_node, ".Hit_Events.Sounds_Victim", false, null);
     } 
 
     
     this.csminion.giveParticleEffects(victim, parent_node, ".Particles.Particle_Impact_Anything", false, null);
     this.csminion.giveParticleEffects(victim, parent_node, ".Particles.Particle_Hit", false, null);
     this.csminion.displayFireworks(victim, parent_node, ".Fireworks.Firework_Hit");
     this.csminion.givePotionEffects(shooter, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "hit");
     this.csminion.givePotionEffects(victim, parent_node, ".Potion_Effects.Potion_Effect_Victim", "hit");
 
     
     if (spawnEntities(victim, parent_node, ".Spawn_Entity_On_Hit.EntityType_Baby_Explode_Amount", shooter)) {
       sendPlayerMessage(shooter, parent_node, ".Spawn_Entity_On_Hit.Message_Shooter", nameShooter, nameVic, flyTime, dmgTotal);
       sendPlayerMessage(victim, parent_node, ".Spawn_Entity_On_Hit.Message_Victim", nameShooter, nameVic, flyTime, dmgTotal);
     } 
 
     
     if (victim instanceof Player) {
       if (makeSpeak != null) {
         ((Player)victim).chat(variableParser(makeSpeak, nameShooter, nameVic, flyTime, dmgTotal));
       }
       if (makeRunCmd != null) {
         executeCommands(victim, parent_node, ".Extras.Make_Victim_Run_Commmand", nameShooter, nameVic, flyTime, dmgTotal, false);
       }
     } 
 
     
     if (runConsole != null) {
       executeCommands(shooter, parent_node, ".Extras.Run_Console_Command", nameShooter, nameVic, flyTime, dmgTotal, true);
     }
 
     
     if (!(victim instanceof Player) && victim.getHealth() <= 0.0D && bonusDrops != null) {
       String[] dropInfo = bonusDrops.split(","); byte b; int i; String[] arrayOfString1;
       for (i = (arrayOfString1 = dropInfo).length, b = 0; b < i; ) { String drop = arrayOfString1[b];
         try {
           shooter.getWorld().dropItemNaturally(victim.getLocation(), new ItemStack(MaterialManager.getMaterial(drop)));
         } catch (IllegalArgumentException ex) {
           printM("'" + drop + "' of weapon '" + parent_node + "' for 'Bonus_Drops' is not a valid item ID!");
           break;
         } 
         b++; }
     
     } 
   }
   
   public void setTempVulnerability(final LivingEntity ent) {
     final int maxNoDamageTicks = ent.getMaximumNoDamageTicks();
     ent.setMaximumNoDamageTicks(0);
     ent.setNoDamageTicks(0);
     
     if (!ent.hasMetadata("[CS] NHC")) {
       ent.setMetadata("[CS] NHC", new FixedMetadataValue(this, Boolean.valueOf(true)));
       
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () ->
        {
                       ent.setMaximumNoDamageTicks(maxNoDamageTicks);
                       ent.setNoDamageTicks(0);
                       ent.removeMetadata("[CS] NHC", CSDirector.this.plugin);
                     }, 2L);
             }
   }
 
   
   @EventHandler
   public void onProjectileHit(ProjectileHitEvent event) {
     if ((event.getEntity() instanceof org.bukkit.entity.Arrow || event.getEntity() instanceof org.bukkit.entity.Egg || event.getEntity() instanceof org.bukkit.entity.Snowball) && 
       event.getEntity().hasMetadata("projParentNode") && event.getEntity().getShooter() instanceof Player shooter) {

               Projectile objProj = event.getEntity();
       String parentNode = objProj.getMetadata("projParentNode").get(0).asString();
       Location destLoc = objProj.getLocation();
 
       
       objProj.removeMetadata(parentNode, this);
       
       boolean collided = event.getEntity().hasMetadata("Collided");
       boolean terrain = getBoolean(parentNode + ".Particles.Particle_Terrain");
       boolean airstrike = getBoolean(parentNode + ".Airstrikes.Enable");
       boolean zapEnable = getBoolean(parentNode + ".Lightning.Enable");
       boolean zapNoDam = getBoolean(parentNode + ".Lightning.No_Damage");
       boolean zapImpact = getBoolean(parentNode + ".Lightning.On_Impact_With_Anything");
       boolean arrowImpact = getBoolean(parentNode + ".Shooting.Remove_Arrows_On_Impact");
       boolean explodeImpact = getBoolean(parentNode + ".Explosions.On_Impact_With_Anything");
       int actTime = getInt(parentNode + ".Explosions.Projectile_Activation_Time");
       String breakBlocks = getString(parentNode + ".Abilities.Break_Blocks");
       String[] blockList = (breakBlocks == null) ? null : breakBlocks.split("-");

       Block hitBlock = objProj.getLocation().getBlock();
       if (!collided) {
         double projSpeed = getInt(parentNode + ".Shooting.Projectile_Speed") * 0.1D;
         if (projSpeed > 256.0D) projSpeed = 256.0D; 
         for (double i = 0.0D; i <= projSpeed; i++) {
           Location finalLoc = objProj.getLocation();
           Vector direction = objProj.getVelocity().normalize();
           direction.multiply(i);
           finalLoc.add(direction);
           hitBlock = finalLoc.getBlock();
           
           if (!isAir(hitBlock.getType())) {
             
             if (terrain) {
               objProj.getWorld().playEffect(finalLoc, Effect.STEP_SOUND, hitBlock.getType());
             }
 
             
             if (blockList != null && blockList.length == 2) {
               boolean passWhiteList = false;
               boolean whiteList = Boolean.parseBoolean(blockList[0]);
               String blockMat = hitBlock.getType().toString();
               if (this.csminion.regionCheck(objProj, parentNode)) {
                 byte b; int j; String[] arrayOfString; for (j = (arrayOfString = blockList[1].split(",")).length, b = 0; b < j; ) { String compMat = arrayOfString[b];
                   boolean hasSecdat = compMat.contains("~");
                   (new String[2])[0] = ""; (new String[2])[1] = ""; String[] secdat = hasSecdat ? compMat.split("~") : new String[2];
 
                   
                   Material mat = MaterialManager.getMaterial(compMat);
                   if (mat != null) {
                     secdat[0] = mat.toString();
                     compMat = mat.toString();
                   } 
                   
                   if (blockMat.equals(hasSecdat ? secdat[0] : compMat) && (!hasSecdat || hitBlock.getData() == Byte.valueOf(secdat[1]).byteValue())) {
                     if (!whiteList) {
                       
                       List<Block> brokenBlocks = new ArrayList<Block>();
                       brokenBlocks.add(hitBlock);
                       EntityExplodeEvent breakBlockEvent = new EntityExplodeEvent(objProj, objProj.getLocation(), brokenBlocks, 0.0F);
                       getServer().getPluginManager().callEvent(breakBlockEvent);
                       
                       hitBlock.setType(Material.AIR);
                       break;
                     } 
                     passWhiteList = true;
                   } 
                   
                   b++; }
                 
                 if (whiteList && !passWhiteList) {
                   
                   List<Block> brokenBlocks = new ArrayList<Block>();
                   brokenBlocks.add(hitBlock);
                   EntityExplodeEvent breakBlockEvent = new EntityExplodeEvent(objProj, objProj.getLocation(), brokenBlocks, 0.0F);
                   getServer().getPluginManager().callEvent(breakBlockEvent);
                   
                   hitBlock.setType(Material.AIR);
                 } 
               } 
             } 
             break;
           } 
           destLoc = finalLoc;
         } 
 
 
         
         if (explodeImpact && objProj.getTicksLived() >= actTime) {
           Entity tempOrb = objProj.getWorld().spawn(destLoc, ExperienceOrb.class);
           projectileExplosion(tempOrb, parentNode, false, shooter, false, false, null, null, false, 0);
           tempOrb.remove();
         } 
 
         
         if (zapEnable && zapImpact) this.csminion.projectileLightning(destLoc, zapNoDam);
 
         
         this.csminion.giveParticleEffects(null, parentNode, ".Particles.Particle_Impact_Anything", false, destLoc);
       } 
 
       
       playSoundEffects(null, parentNode, ".Hit_Events.Sounds_Impact", false, destLoc);
       this.csminion.giveParticleEffects(null, parentNode, ".Airstrikes.Particle_Call_Airstrike", false, destLoc);
       
       if (airstrike) this.csminion.callAirstrike(event.getEntity(), parentNode, shooter);
       if (arrowImpact && objProj.getType() == EntityType.ARROW) objProj.remove();
 
       
       WeaponHitBlockEvent blockHitEvent = new WeaponHitBlockEvent(shooter, objProj, parentNode, hitBlock, destLoc.getBlock());
       getServer().getPluginManager().callEvent(blockHitEvent);
     } 
   }
 
   
   @EventHandler
   public void onEntityExplode(EntityExplodeEvent event) {
     Entity boomer = event.getEntity();
     if (boomer instanceof TNTPrimed) {
       
       if (boomer.hasMetadata("CS_potex")) {
         String parent_node = boomer.getMetadata("CS_potex").get(0).asString();
         playSoundEffects(boomer, parent_node, ".Explosions.Sounds_Explode", false, null);
       } 
 
       
       if (boomer.hasMetadata("nullify") && event.blockList() != null) event.blockList().clear();
 
       
       if (MaterialManager.isSkullBlock(boomer.getLocation().getBlock()) && !boomer.hasMetadata("C4_Friendly")) {
         BlockState state = boomer.getLocation().getBlock().getState();
         if (state instanceof Skull) {
           Skull skull; 
           try { skull = (Skull)state; } catch (ClassCastException ex) { return; }
            if (skull.getOwner().contains("،")) {
             boomer.getLocation().getBlock().removeMetadata("CS_transformers", this);
             boomer.getLocation().getBlock().setType(Material.AIR);
           } 
         } 
       } 
     } else if ((boomer instanceof WitherSkull || boomer instanceof LargeFireball) && boomer.hasMetadata("projParentNode") && (
                   (Projectile) boomer).getShooter() instanceof Player shooter) {

               String parent_node = boomer.getMetadata("projParentNode").get(0).asString();
       if (boomer.getTicksLived() >= getInt(parent_node + ".Explosions.Projectile_Activation_Time")) {
         projectileExplosion(boomer, parent_node, false, shooter, false, false, null, null, false, 0);
       }
       event.setCancelled(true);
     } 
   }
 
 
   
   public void playSoundEffectsScaled(final Entity player, String parentNode, String childNode, boolean reload, double scale, String... customSounds) {
     String soundList = (customSounds.length == 0) ? getString(parentNode + childNode) : customSounds[0];
     if (soundList == null)
       return;  byte b; int i; String[] arrayOfString;
     for (i = (arrayOfString = soundList.replaceAll(" ", "").split(",")).length, b = 0; b < i; ) { String soundStrip = arrayOfString[b];
       String[] soundInfo = soundStrip.split("-");
       
       if (soundInfo.length != 4) {
         printM("'" + soundStrip + "' of weapon '" + parentNode + "' has an invalid format! The correct format is: Sound-Volume-Pitch-Delay!");
       } else {
         try {
           final Sound sound = SoundManager.get(soundInfo[0].toUpperCase());
           final float volume = Float.parseFloat(soundInfo[1]);
           final float pitch = Float.parseFloat(soundInfo[2]);
           long delay = (long)(Long.parseLong(soundInfo[3]) * scale);
           
           int taskID = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable()
               {
                 public void run() {
                   player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
                 }
               }, delay);
 
           
           if (reload) {
             String playerName = player.getName();
             Collection<Integer> taskIDs = this.global_reload_IDs.get(playerName);
             
             if (taskIDs == null) {
               taskIDs = new ArrayList<Integer>();
               this.global_reload_IDs.put(playerName, taskIDs);
             } 
             
             taskIDs.add(Integer.valueOf(taskID));
           } 
         } catch (IllegalArgumentException ex) {
           printM("'" + soundStrip + "' of weapon '" + parentNode + "' contains either an invalid number or sound!");
         } 
       } 
       b++; }
   
   }
   
   public void playSoundEffects(final Entity player, String parentNode, String childNode, boolean reload, final Location givenCoord, String... customSounds) {
     String soundList = (customSounds.length == 0) ? getString(parentNode + childNode) : customSounds[0];
     if (soundList == null)
       return;  byte b; int i; String[] arrayOfString;
     for (i = (arrayOfString = soundList.replaceAll(" ", "").split(",")).length, b = 0; b < i; ) { String soundStrip = arrayOfString[b];
       String[] soundInfo = soundStrip.split("-");
       
       if (soundInfo.length != 4) {
         printM("'" + soundStrip + "' of weapon '" + parentNode + "' has an invalid format! The correct format is: Sound-Volume-Pitch-Delay!");
       } else {
         try {
           final Sound sound = SoundManager.get(soundInfo[0].toUpperCase());
           final float volume = Float.parseFloat(soundInfo[1]);
           final float pitch = Float.parseFloat(soundInfo[2]);
           long delay = Long.parseLong(soundInfo[3]);
           
           int taskID = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable()
               {
                 public void run() {
                   if (player == null) {
                     givenCoord.getWorld().playSound(givenCoord, sound, volume, pitch);
                   } else {
                     player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
                   } 
                 }
               }, delay);
 
           
           if (reload) {
             String playerName = player.getName();
             Collection<Integer> taskIDs = this.global_reload_IDs.get(playerName);
             
             if (taskIDs == null) {
               taskIDs = new ArrayList<Integer>();
               this.global_reload_IDs.put(playerName, taskIDs);
             } 
             
             taskIDs.add(Integer.valueOf(taskID));
           } 
         } catch (IllegalArgumentException ex) {
           printM("'" + soundStrip + "' of weapon '" + parentNode + "' contains either an invalid number or sound!");
         } 
       } 
       b++; }
   
   }
 
   
   public void fireProjectile(final Player player, final String parentNode, final boolean leftClick) {
     int gunSlot = player.getInventory().getHeldItemSlot();
     int shootDelay = getInt(parentNode + ".Shooting.Delay_Between_Shots");
     
     final int projAmount = getInt(parentNode + ".Shooting.Projectile_Amount");
     final boolean ammoEnable = getBoolean(parentNode + ".Ammo.Enable");
     final boolean oneTime = getBoolean(parentNode + ".Extras.One_Time_Use");
     String deviceType = getString(parentNode + ".Explosive_Devices.Device_Type");
     final String proType = getString(parentNode + ".Shooting.Projectile_Type");
     
     ItemStack item = player.getInventory().getItemInHand();
     final boolean isFullyAuto = getBoolean(parentNode + ".Fully_Automatic.Enable");
     int fireRate = getInt(parentNode + ".Fully_Automatic.Fire_Rate");
     boolean burstEnable = getBoolean(parentNode + ".Burstfire.Enable");
     int burstShots = getInt(parentNode + ".Burstfire.Shots_Per_Burst");
     int burstDelay = getInt(parentNode + ".Burstfire.Delay_Between_Shots_In_Burst");
     boolean meleeMode = getBoolean(parentNode + ".Item_Information.Melee_Mode");
     boolean shootDisable = getBoolean(parentNode + ".Shooting.Disable");
     final boolean reloadOn = getBoolean(parentNode + ".Reload.Enable");
     final boolean dualWield = isDualWield(player, parentNode, item);
     if (shootDisable || meleeMode)
       return; 
     Vector shiftVector = determinePosition(player, dualWield, leftClick);
     final Location projLoc = player.getEyeLocation().toVector().add(shiftVector.multiply(0.2D)).toLocation(player.getWorld());
 
     
     final String actType = getString(parentNode + ".Firearm_Action.Type");
     final boolean tweakyAction = (actType != null && (actType.toLowerCase().contains("bolt") || actType.toLowerCase().contains("lever") || actType.toLowerCase().contains("pump")));
 
     
     if (player.hasMetadata(parentNode + "shootDelay" + gunSlot + leftClick)) {
       return;
     }
     if (player.hasMetadata(parentNode + "noShooting" + gunSlot)) {
       return;
     }
     if (player.hasMetadata("togglesnoShooting" + gunSlot))
       return; 
     if (oneTime && ammoEnable) {
       player.sendMessage(this.heading + "For '" + parentNode + "' - the 'One_Time_Use' node is incompatible with weapons using the Ammo module.");
       
       return;
     } 
     if (proType != null && (proType.equalsIgnoreCase("grenade") || proType.equalsIgnoreCase("flare")) && projAmount == 0) {
       player.sendMessage(this.heading + "The weapon '" + parentNode + "' is missing a value for 'Projectile_Amount'.");
       
       return;
     } 
     
     if (isFullyAuto) {
       if (burstEnable) {
         player.sendMessage(this.heading + "The weapon '" + parentNode + "' is using Fully_Automatic and Burstfire at the same time. Pick one; you cannot enable both!"); return;
       } 
       if (shootDelay > 1) {
         player.sendMessage(this.heading + "For '" + parentNode + "' - the Fully_Automatic module can only be used if 'Delay_Between_Shots' is removed or set to a value no greater than 1."); return;
       } 
       if (fireRate <= 0 || fireRate > 16) {
         player.sendMessage(this.heading + "The weapon '" + parentNode + "' has an invalid value for 'Fire_Rate'. The accepted values are 1 to 16.");
         
         return;
       } 
     } 
     
     if (itemIsSafe(item) && item.getItemMeta().getDisplayName().contains("ᴿ")) {
       if (getAmmoBetweenBrackets(player, parentNode, item) > 0) {
         if (!dualWield) {
           
           terminateReload(player);
 
           
           removeInertReloadTag(player, 0, true);
         } else {
           
           int[] ammoReading = grabDualAmmo(item, parentNode);
           if ((ammoReading[0] > 0 && leftClick) || (ammoReading[1] > 0 && !leftClick)) {
             terminateReload(player);
             removeInertReloadTag(player, 0, true);
           } 
         } 
       } else {
         
         reloadAnimation(player, parentNode);
         
         return;
       } 
     }
     
     if (player.hasMetadata(parentNode + "reloadShootDelay" + gunSlot)) {
       return;
     }
     
     if (!tweakyAction && (actType == null || !actType.equalsIgnoreCase("slide") || !item.getItemMeta().getDisplayName().contains("▫"))) {
       player.setMetadata(parentNode + "shootDelay" + gunSlot + leftClick, new FixedMetadataValue(this, Boolean.valueOf(true)));
       this.csminion.tempVars(player, parentNode + "shootDelay" + gunSlot + leftClick, Long.valueOf(shootDelay));
     } 
 
     
     final String ammoInfo = getString(parentNode + ".Ammo.Ammo_Item_ID");
     final boolean ammoPerShot = getBoolean(parentNode + ".Ammo.Take_Ammo_Per_Shot");
     final double zoomAcc = getDouble(parentNode + ".Scope.Zoom_Bullet_Spread");
     final boolean sneakOn = getBoolean(parentNode + ".Sneak.Enable");
     boolean sneakToShoot = getBoolean(parentNode + ".Sneak.Sneak_Before_Shooting");
     final boolean sneakNoRec = getBoolean(parentNode + ".Sneak.No_Recoil");
     final double sneakAcc = getDouble(parentNode + ".Sneak.Bullet_Spread");
     final boolean exploDevs = getBoolean(parentNode + ".Explosive_Devices.Enable");
     boolean takeAmmo = getBoolean(parentNode + ".Reload.Take_Ammo_On_Reload");
 
     
     String dragRemInfo = getString(parentNode + ".Shooting.Removal_Or_Drag_Delay");
     final String[] dragRem = (dragRemInfo == null) ? null : dragRemInfo.split("-");
     if (dragRem != null) {
       try {
         Integer.valueOf(dragRem[0]);
       } catch (NumberFormatException ex) {
         player.sendMessage(this.heading + "For the weapon '" + parentNode + "', the 'Removal_Or_Drag_Delay' node is incorrectly configured.");
         
         return;
       } 
     }
     
     if (getBoolean(parentNode + ".Ammo.Take_Ammo_On_Reload")) {
       player.sendMessage(this.heading + "For the weapon '" + parentNode + "', the Ammo module does not support the 'Take_Ammo_On_Reload' node. Did you mean to place it in the Reload module?");
       
       return;
     } 
     
     if (ammoEnable) {
       if (!takeAmmo && !ammoPerShot) {
         player.sendMessage(this.heading + "The weapon '" + parentNode + "' has enabled the Ammo module, but at least one of the following nodes need to be set to true: Take_Ammo_On_Reload, Take_Ammo_Per_Shot."); return;
       } 
       if (!this.csminion.containsItemStack(player, ammoInfo, 1, parentNode)) {
 
         
         boolean isPumpOrBolt = (actType != null && !actType.equalsIgnoreCase("pump") && !actType.equalsIgnoreCase("bolt"));
         boolean hasLoadedChamber = item.getItemMeta().getDisplayName().contains("▪ «");
         
         if (ammoPerShot || (takeAmmo && getAmmoBetweenBrackets(player, parentNode, item) == 0 && (isPumpOrBolt || !hasLoadedChamber))) {
           playSoundEffects(player, parentNode, ".Ammo.Sounds_Shoot_With_No_Ammo", false, null);
           
           return;
         } 
       } 
     } 
     
     if (sneakToShoot && (!player.isSneaking() || isAir(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType()))) {
       return;
     }
     if (checkBoltPosition(player, parentNode)) {
       return;
     }
     if (!burstEnable) burstShots = 1;
 
     
     if (isFullyAuto) {
       burstShots = 5;
       burstDelay = 1;
     } 
 
     
     final double projSpeed = getInt(parentNode + ".Shooting.Projectile_Speed") * 0.1D;
 
     
     final boolean setOnFire = getBoolean(parentNode + ".Shooting.Projectile_Flames");
 
     
     final boolean noBulletDrop = getBoolean(parentNode + ".Shooting.Remove_Bullet_Drop");
 
     
     if (getBoolean(parentNode + ".Scope.Zoom_Before_Shooting") && !player.hasMetadata("ironsights")) {
       return;
     }
     int shootReloadBuffer = getInt(parentNode + ".Reload.Shoot_Reload_Buffer");
     if (shootReloadBuffer > 0) {
       Map<Integer, Long> lastShot = this.last_shot_list.get(player.getName());
       if (lastShot == null) {
         lastShot = new HashMap<Integer, Long>();
         this.last_shot_list.put(player.getName(), lastShot);
       } 
       lastShot.put(Integer.valueOf(gunSlot), Long.valueOf(System.currentTimeMillis()));
     } 
 
     
     int burstStart = 0;
     if (isFullyAuto) {
 
       
       WeaponFireRateEvent event = new WeaponFireRateEvent(player, parentNode, item, fireRate);
       getServer().getPluginManager().callEvent(event);
       fireRate = event.getFireRate();
 
       
       String playerName = player.getName();
       if (!this.rpm_ticks.containsKey(playerName)) {
         this.rpm_ticks.put(playerName, Integer.valueOf(1));
       }
 
       
       if (!this.rpm_shots.containsKey(playerName)) {
         this.rpm_shots.put(playerName, Integer.valueOf(0));
       }
 
       
       burstStart = this.rpm_shots.get(playerName).intValue();
 
       
       this.rpm_shots.put(playerName, Integer.valueOf(5));
     } 
 
     
     final int fireRateFinal = fireRate;
 
     
     final int itemSlot = player.getInventory().getHeldItemSlot();
     
     for (int burst = burstStart; burst < burstShots; burst++) {
       final boolean isLastShot = (burst >= burstShots - 1);
       
       int task_ID = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable()
           {
             public void run()
             {
               if (isFullyAuto) {
 
                 
                 String playerName = player.getName();
                 int shotsLeft = CSDirector.this.rpm_shots.get(playerName).intValue() - 1;
                 CSDirector.this.rpm_shots.put(playerName, Integer.valueOf(shotsLeft));
 
                 
                 int tick = CSDirector.this.rpm_ticks.get(playerName).intValue();
                 CSDirector.this.rpm_ticks.put(playerName, Integer.valueOf((tick >= 20) ? 1 : (tick + 1)));
 
                 
                 if (shotsLeft == 0) {
                   CSDirector.this.burst_task_IDs.remove(playerName);
                 }
 
                 
                 if (!CSDirector.this.isValid(tick, fireRateFinal))
                 {
                   return;
                 
                 }
               
               }
               else if (isLastShot) {
                 CSDirector.this.burst_task_IDs.remove(player.getName());
               } 
 
 
               
               ItemStack item = player.getInventory().getItemInHand();
 
               
               if (!oneTime) {
                 
                 if (CSDirector.this.switchedTheItem(player, parentNode) || itemSlot != player.getInventory().getHeldItemSlot()) {
                   CSDirector.this.unscopePlayer(player);
                   CSDirector.this.terminateAllBursts(player);
                   
                   return;
                 } 
                 
                 boolean normalAction = false;
                 if (actType == null) {
                   normalAction = true;
                   String attachType = CSDirector.this.getAttachment(parentNode, item)[0];
                   String filter = item.getItemMeta().getDisplayName();
                   if (attachType == null || !attachType.equalsIgnoreCase("accessory")) {
                     if (filter.contains("▪ «")) {
                       CSDirector.this.csminion.setItemName(item, filter.replaceAll("▪ «", "«"));
                     } else if (filter.contains("▫ «")) {
                       CSDirector.this.csminion.setItemName(item, filter.replaceAll("▫ «", "«"));
                     } else if (filter.contains("˗ «")) {
                       CSDirector.this.csminion.setItemName(item, filter.replaceAll("˗ «", "«"));
                     } 
                   }
                 } else if (!tweakyAction) {
                   normalAction = true;
                 } 
 
                 
                 if (ammoEnable && ammoPerShot && !CSDirector.this.csminion.containsItemStack(player, ammoInfo, 1, parentNode)) {
                   CSDirector.this.burst_task_IDs.remove(player.getName());
 
                   
                   return;
                 } 
                 
                 if (reloadOn) {
                   if (item.getItemMeta().getDisplayName().contains("ᴿ"))
                     return;  int detectedAmmo = CSDirector.this.getAmmoBetweenBrackets(player, parentNode, item);
                   
                   if (normalAction) {
                     if (detectedAmmo > 0) {
                       if (!dualWield) {
                         CSDirector.this.ammoOperation(player, parentNode, detectedAmmo, item);
                       }
                       else if (!CSDirector.this.ammoSpecOps(player, parentNode, detectedAmmo, item, leftClick)) {
                         return;
                       } 
                     } else {
                       CSDirector.this.reloadAnimation(player, parentNode);
                       return;
                     } 
                   }
                 } else {
                   String itemName = item.getItemMeta().getDisplayName();
                   if (itemName.contains("«") && !itemName.contains(String.valueOf('×')) && !exploDevs) {
                     CSDirector.this.csminion.replaceBrackets(item, String.valueOf('×'), parentNode);
                   }
                 } 
               } 
 
               
               double bulletSpread = CSDirector.this.getDouble(parentNode + ".Shooting.Bullet_Spread");
               if (player.isSneaking() && sneakOn) bulletSpread = sneakAcc; 
               if (player.hasMetadata("ironsights")) bulletSpread = zoomAcc; 
               if (bulletSpread == 0.0D) bulletSpread = 0.1D;
 
               
               boolean noVertRecoil = CSDirector.this.getBoolean(parentNode + ".Abilities.No_Vertical_Recoil");
               boolean jetPack = CSDirector.this.getBoolean(parentNode + ".Abilities.Jetpack_Mode");
               double recoilAmount = CSDirector.this.getInt(parentNode + ".Shooting.Recoil_Amount") * 0.1D;
               if (recoilAmount != 0.0D && (!sneakOn || !sneakNoRec || !player.isSneaking())) {
                 if (!jetPack) {
                   Vector velToAdd = player.getLocation().getDirection().multiply(-recoilAmount);
                   if (noVertRecoil) velToAdd.multiply(new Vector(1, 0, 1)); 
                   player.setVelocity(velToAdd);
                 } else {
                   
                   player.setVelocity(new Vector(0.0D, recoilAmount, 0.0D));
                 } 
               }
 
               
               boolean clearFall = CSDirector.this.getBoolean(parentNode + ".Shooting.Reset_Fall_Distance");
               if (clearFall) player.setFallDistance(0.0F);
 
               
               CSDirector.this.csminion.giveParticleEffects(player, parentNode, ".Particles.Particle_Player_Shoot", true, null);
 
               
               CSDirector.this.csminion.givePotionEffects(player, parentNode, ".Potion_Effects.Potion_Effect_Shooter", "shoot");
 
               
               CSDirector.this.csminion.displayFireworks(player, parentNode, ".Fireworks.Firework_Player_Shoot");
 
               
               CSDirector.this.csminion.runCommand(player, parentNode);
 
               
               if (CSDirector.this.getBoolean(parentNode + ".Abilities.Hurt_Effect")) {
                 player.playEffect(EntityEffect.HURT);
               }
 
               
               String projectile_type = CSDirector.this.getString(parentNode + ".Shooting.Projectile_Type");
 
 
               
               int timer = CSDirector.this.getInt(parentNode + ".Explosions.Explosion_Delay");
               boolean airstrike = CSDirector.this.getBoolean(parentNode + ".Airstrikes.Enable");
               if (airstrike) timer = CSDirector.this.getInt(parentNode + ".Airstrikes.Flare_Activation_Delay");
 
               
               String soundsShoot = CSDirector.this.getString(parentNode + ".Shooting.Sounds_Shoot");
               WeaponPreShootEvent event = new WeaponPreShootEvent(player, parentNode, soundsShoot, bulletSpread, leftClick);
               CSDirector.this.plugin.getServer().getPluginManager().callEvent(event);
 
               
               CSDirector.this.playSoundEffects(player, parentNode, null, false, null, event.getSounds());
 
               
               if (event.isCancelled())
                 return;  bulletSpread = event.getBulletSpread();
 
               
               for (int i = 0; i < projAmount; i++) {
                 Random r = new Random();
                 double yaw = Math.toRadians((-player.getLocation().getYaw() - 90.0F));
                 double pitch = Math.toRadians(-player.getLocation().getPitch());
                 double[] spread = { 1.0D, 1.0D, 1.0D };
                 for (int t = 0; t < 3; ) { spread[t] = (r.nextDouble() - r.nextDouble()) * bulletSpread * 0.1D; t++; }
                  double x = Math.cos(pitch) * Math.cos(yaw) + spread[0];
                 double y = Math.sin(pitch) + spread[1];
                 double z = -Math.sin(yaw) * Math.cos(pitch) + spread[2];
                 Vector dirVel = new Vector(x, y, z);
                 
                 if (proType != null && (proType.equalsIgnoreCase("grenade") || proType.equalsIgnoreCase("flare"))) {
                   CSDirector.this.launchGrenade(player, parentNode, timer, dirVel.multiply(projSpeed), null, 0);
                 } else if (proType.equalsIgnoreCase("energy")) {
                   int range, hitLimit; double radius; PermissionAttachment attachment = player.addAttachment(CSDirector.this.plugin);
                   attachment.setPermission("nocheatplus", true);
                   attachment.setPermission("anticheat.check.exempt", true);
 
                   
                   String proOre = CSDirector.this.getString(parentNode + ".Shooting.Projectile_Subtype");
                   if (proOre == null) {
                     player.sendMessage(CSDirector.this.heading + "The weapon '" + parentNode + "' does not have a value for 'Projectile_Subtype'.");
                     return;
                   } 
                   String[] proInfo = proOre.split("-");
                   if (proInfo.length != 4) {
                     player.sendMessage(CSDirector.this.heading + "The value provided for 'Projectile_Subtype' of the weapon '" + parentNode + "' has an incorrect format.");
 
                     
                     return;
                   } 
 
                   
                   int wallLimit = 0;
                   
                   int hitCount = 0;
                   int wallCount = 0;
                   try {
                     range = Integer.valueOf(proInfo[0]).intValue();
                     hitLimit = Integer.valueOf(proInfo[3]).intValue();
                     if (proInfo[2].equalsIgnoreCase("all")) {
                       wallLimit = -1;
                     } else if (!proInfo[2].equalsIgnoreCase("none")) {
                       wallLimit = Integer.valueOf(proInfo[2]).intValue();
                     } 
                     radius = Double.valueOf(proInfo[1]).doubleValue();
                   } catch (NumberFormatException ex) {
                     player.sendMessage(CSDirector.this.heading + "The value provided for 'Projectile_Subtype' of the weapon '" + parentNode + "' contains an invalid number.");
                     
                     break;
                   } 
                   Set<Block> hitBlocks = new HashSet<Block>();
                   Set<Integer> hitMobs = new HashSet<Integer>();
                   Vector vecShift = dirVel.normalize().multiply(radius);
                   Location locStart = player.getEyeLocation();
                   
                   double k;
                   label198: for (k = 0.0D; k < range; k += radius) {
                     locStart.add(vecShift);
                     Block hitBlock = locStart.getBlock();
                     
                     if (CSDirector.this.isAir(hitBlock.getType())) {
 
                       
                       FallingBlock tempEnt = player.getWorld().spawnFallingBlock(locStart, Material.AIR, (byte)0);
                       
                       for (Entity ent : tempEnt.getNearbyEntities(radius, radius, radius)) {
                         if (ent instanceof LivingEntity && ent != player && !hitMobs.contains(Integer.valueOf(ent.getEntityId())) && !ent.isDead()) {
                           if (ent instanceof Player) {
                             ent.setMetadata("CS_Energy", new FixedMetadataValue(CSDirector.this.plugin, parentNode));
                             ((LivingEntity)ent).damage(0.0D, player);
                           } else {
                             CSDirector.this.dealDamage(player, (LivingEntity)ent, null, parentNode);
                           } 
                           
                           hitMobs.add(Integer.valueOf(ent.getEntityId()));
                           hitCount++;
 
                           
                           if (hitLimit != 0 && hitCount >= hitLimit) {
                             break label198;
                           }
                         } 
                       } 
 
                       
                       tempEnt.remove();
                     }
                     else if (wallLimit != -1 && !hitBlocks.contains(hitBlock)) {
 
                       
                       wallCount++;
                       if (wallCount > wallLimit) {
                         break;
                       }
                       hitBlocks.add(hitBlock);
                     } 
                   } 
 
                   
                   CSDirector.this.callShootEvent(player, null, parentNode);
                   CSDirector.this.playSoundEffects(player, parentNode, ".Shooting.Sounds_Projectile", false, null);
                   
                   player.removeAttachment(attachment);
                 } else if (proType.equalsIgnoreCase("splash")) {
                   ThrownPotion splashPot = player.getWorld().spawn(projLoc, ThrownPotion.class);
                   ItemStack potType = CSDirector.this.csminion.parseItemStack(CSDirector.this.getString(parentNode + ".Shooting.Projectile_Subtype"));
                   if (potType != null) {
                     try {
                       splashPot.setItem(potType);
                     } catch (IllegalArgumentException ex) {
                       player.sendMessage(CSDirector.this.heading + "The value for 'Projectile_Subtype' of weapon '" + parentNode + "' is not a splash potion!");
                     } 
                   }
                   if (setOnFire) splashPot.setFireTicks(6000); 
                   if (noBulletDrop) CSDirector.this.noArcInArchery(splashPot, dirVel.multiply(projSpeed));
                   splashPot.setShooter(player);
                   splashPot.setMetadata("projParentNode", new FixedMetadataValue(CSDirector.this.plugin, parentNode));
                   splashPot.setVelocity(dirVel.multiply(projSpeed));
                   CSDirector.this.callShootEvent(player, splashPot, parentNode);
                   if (dragRem != null) CSDirector.this.prepareTermination(splashPot, Boolean.parseBoolean(dragRem[1]), Long.valueOf(dragRem[0]));
                 } else {
                   Projectile snowball; if (projectile_type.equalsIgnoreCase("arrow")) {
                     snowball = (Projectile)player.getWorld().spawnEntity(projLoc, EntityType.ARROW);
                   } else if (projectile_type.equalsIgnoreCase("egg")) {
                     snowball = (Projectile)player.getWorld().spawnEntity(projLoc, EntityType.EGG);
                     snowball.setMetadata("CS_Hardboiled", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
                   } else if (projectile_type.equalsIgnoreCase("fireball")) {
                     snowball = player.launchProjectile(LargeFireball.class);
 
                     
                     if (Boolean.parseBoolean(CSDirector.this.getString(parentNode + ".Shooting.Projectile_Subtype"))) {
                       snowball.setMetadata("CS_NoDeflect", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
                     }
                   } else if (projectile_type.equalsIgnoreCase("witherskull")) {
                     snowball = player.launchProjectile(WitherSkull.class);
                   } else {
                     snowball = (Projectile)player.getWorld().spawnEntity(projLoc, EntityType.SNOWBALL);
                   } 
                   
                   if (setOnFire) snowball.setFireTicks(6000); 
                   if (noBulletDrop) CSDirector.this.noArcInArchery(snowball, dirVel.multiply(projSpeed)); 
                   snowball.setShooter(player);
                   snowball.setVelocity(dirVel.multiply(projSpeed));
                   snowball.setMetadata("projParentNode", new FixedMetadataValue(CSDirector.this.plugin, parentNode));
                   CSDirector.this.callShootEvent(player, snowball, parentNode);
                   CSDirector.this.playSoundEffects(snowball, parentNode, ".Shooting.Sounds_Projectile", false, null);
                   if (dragRem != null) CSDirector.this.prepareTermination(snowball, Boolean.parseBoolean(dragRem[1]), Long.valueOf(dragRem[0]));
                 } 
               }  }
           }, (long) burstDelay * burst + 1L);
 
       
       if (oneTime && burst == 0 && (deviceType == null || (!deviceType.equalsIgnoreCase("remote") && !deviceType.equalsIgnoreCase("trap")))) this.csminion.oneTime(player);
 
       
       String user = player.getName();
       Collection<Integer> values = this.burst_task_IDs.get(user);
       if (values == null) {
         values = new ArrayList<Integer>();
         this.burst_task_IDs.put(user, values);
       } 
       values.add(Integer.valueOf(task_ID));
     } 
   }
 
   
   public void noArcInArchery(final Projectile proj, final Vector direction) {
     Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
         {
           public void run() {
             if (!proj.isDead()) {
               proj.setVelocity(direction);
               CSDirector.this.noArcInArchery(proj, direction);
             } 
           }
         }, 1L);
       }
 
   
   public void callShootEvent(Player player, Entity objProj, String weaponTitle) {
     WeaponShootEvent event = new WeaponShootEvent(player, objProj, weaponTitle);
     getServer().getPluginManager().callEvent(event);
   }
 
 
 
 
   
   public void reloadAnimation(final Player player, final String parent_node, boolean... reloadStart) {
     if (!getBoolean(parent_node + ".Reload.Enable") || player.hasMetadata("markOfTheReload")) {
       return;
     }
     String playerName = player.getName();
     if (this.delayed_reload_IDs.containsKey(playerName)) {
       Bukkit.getScheduler().cancelTask(this.delayed_reload_IDs.get(playerName).intValue());
       this.delayed_reload_IDs.remove(playerName);
     } 
 
     
     int relDuration = getInt(parent_node + ".Reload.Reload_Duration");
     ItemStack held = player.getItemInHand();
     boolean isStart = (reloadStart.length == 0);
     final boolean takeAsMag = getBoolean(parent_node + ".Reload.Take_Ammo_As_Magazine");
     final boolean takeAmmo = getBoolean(parent_node + ".Reload.Take_Ammo_On_Reload");
     final boolean reloadIndie = getBoolean(parent_node + ".Reload.Reload_Bullets_Individually");
     final boolean ammoEnable = getBoolean(parent_node + ".Ammo.Enable");
     final String ammoInfo = getString(parent_node + ".Ammo.Ammo_Item_ID");
     int openTime = getInt(parent_node + ".Firearm_Action.Open_Duration");
     final int closeTime = getInt(parent_node + ".Firearm_Action.Close_Duration") + getInt(parent_node + ".Firearm_Action.Reload_Close_Delay");
     boolean akimboSingleReload = false;
     
     String reloadSound = ".Reload.Sounds_Reloading";
     final boolean dualWield = isDualWield(player, parent_node, held);
     final int reloadAmt = dualWield ? (getReloadAmount(player, parent_node, held) * 2) : getReloadAmount(player, parent_node, held);
     final String replacer = dualWield ? (reloadAmt / 2 + " | " + (reloadAmt / 2)) : String.valueOf(reloadAmt);
     String actionType = dualWield ? null : getString(parent_node + ".Firearm_Action.Type");
 
     
     if (reloadAmt <= 0) {
       player.sendMessage(this.heading + "The weapon '" + parent_node + "' is using the Reload module, but is missing a valid value for 'Reload_Amount'.");
       
       return;
     } 
     
     if (getBoolean(parent_node + ".Reload.Destroy_When_Empty") && held != null && held.getType() != Material.AIR && held.hasItemMeta()) {
       if (getAmmoBetweenBrackets(player, parent_node, held) == 0) {
         boolean validAction = !(actionType != null && !actionType.equalsIgnoreCase("slide") && !actionType.equalsIgnoreCase("break") && !actionType.equalsIgnoreCase("revolver"));
         if (validAction || !held.getItemMeta().getDisplayName().contains("▪")) {
           player.setItemInHand(null);
         }
       } 
       
       return;
     } 
     
     if (getBoolean("Merged_Reload.Disable") && held.getAmount() > 1) {
       String deniedMsg = getString("Merged_Reload.Message_Denied");
       if (deniedMsg != null) player.sendMessage(deniedMsg); 
       playSoundEffects(player, "Merged_Reload", "Sounds_Denied", false, null);
       
       return;
     } 
     
     boolean boltAct = false;
     final boolean pumpAct = (actionType != null && actionType.equalsIgnoreCase("pump"));
     boolean breakAct = false;
     boolean slide = false;
     if (actionType != null) {
       if (actionType.equalsIgnoreCase("break") || actionType.equalsIgnoreCase("revolver")) {
         breakAct = true;
       } else if (actionType.equalsIgnoreCase("slide")) {
         slide = true;
       } else if (actionType.equalsIgnoreCase("bolt") || actionType.equalsIgnoreCase("lever")) {
         boltAct = true;
       } 
     }
     final boolean finalBreakAct = breakAct;
 
     
     boolean isSwitched = switchedTheItem(player, parent_node);
     boolean isOutOfAmmo = (takeAmmo && ammoEnable && !this.csminion.containsItemStack(player, ammoInfo, 1, parent_node));
     if (isSwitched || isOutOfAmmo) {
       removeInertReloadTag(player, 0, true);
       
       if (isOutOfAmmo) {
         player.removeMetadata("markOfTheReload", this);
 
         
         if (boltAct && !held.getItemMeta().getDisplayName().contains("▪") && !held.getItemMeta().getDisplayName().contains("«0")) {
           correctBoltPosition(player, parent_node, false, closeTime, false, true, pumpAct, false);
         }
       } 
       
       return;
     } 
     
     if (!dualWield) {
 
       
       String attachType = getAttachment(parent_node, held)[0];
       String displayName = held.getItemMeta().getDisplayName();
       boolean isAccessory = (attachType != null && attachType.equalsIgnoreCase("accessory"));
       boolean boltFull = (boltAct && displayName.contains("▪ «" + (reloadAmt - 1)) && !isAccessory);
 
       
       if (boltFull) {
         player.removeMetadata("markOfTheReload", this);
         
         return;
       } 
       if (displayName.contains("«" + reloadAmt + "»") || (isAccessory && displayName.contains(reloadAmt + "»")) || (attachType != null && attachType.equalsIgnoreCase("main") && displayName.contains("«" + reloadAmt))) {
         
         if (finalBreakAct) {
           
           checkBoltPosition(player, parent_node);
 
 
         
         }
         else if (!displayName.contains("▪")) {
           correctBoltPosition(player, parent_node, false, closeTime, false, true, pumpAct, false);
         } 
         
         player.removeMetadata("markOfTheReload", this);
 
         
         return;
       } 
       
       if (slide && displayName.contains("▫") && openTime > 0) {
         correctBoltPosition(player, parent_node, true, openTime, true, false, false, false);
         
         return;
       } 
       
       if (!pumpAct && !slide && !isAccessory) {
         
         if (!finalBreakAct && (displayName.contains("▪") || displayName.contains("▫"))) {
           
           correctBoltPosition(player, parent_node, true, openTime, true, false, false, false);
           return;
         } 
         if (displayName.contains("▪")) {
           
           correctBoltPosition(player, parent_node, true, openTime, true, false, false, false);
 
 
 
           
           return;
         } 
       } 
     } else {
       int[] ammoReading = grabDualAmmo(held, parent_node);
       if (ammoReading[0] + ammoReading[1] >= reloadAmt) {
         player.removeMetadata("markOfTheReload", this);
         
         return;
       } 
       
       boolean oneIsFull = !(ammoReading[0] != reloadAmt / 2 && ammoReading[1] != reloadAmt / 2);
       boolean oneAmmoOnly = (takeAmmo && ammoEnable && this.csminion.countItemStacks(player, ammoInfo, parent_node) == 1);
       if (!reloadIndie && (oneIsFull || oneAmmoOnly)) {
         relDuration = getInt(parent_node + ".Reload.Dual_Wield.Single_Reload_Duration");
         reloadSound = ".Reload.Dual_Wield.Sounds_Single_Reload";
         akimboSingleReload = true;
       } 
     } 
 
     
     terminateReload(player);
     removeInertReloadTag(player, 0, true);
     unscopePlayer(player);
 
     
     player.setMetadata("markOfTheReload", new FixedMetadataValue(this, Boolean.valueOf(true)));
 
     
     terminateAllBursts(player);
 
     
     if (!held.getItemMeta().getDisplayName().contains("ᴿ")) {
       this.csminion.setItemName(held, held.getItemMeta().getDisplayName() + 'ᴿ');
     }
     
     if (reloadIndie && isStart) {
       relDuration += getInt(parent_node + ".Reload.First_Reload_Delay");
     }
 
     
     int shootReloadBuffer = getInt(parent_node + ".Reload.Shoot_Reload_Buffer");
     if (shootReloadBuffer > 0) {
       Map<Integer, Long> map = this.last_shot_list.get(playerName);
       if (map != null) {
         Long lastShot = map.get(Integer.valueOf(player.getInventory().getHeldItemSlot()));
         if (lastShot != null) {
           int ticksPassed = (int)((System.currentTimeMillis() - lastShot.longValue()) / 50L);
           int ticksToWait = shootReloadBuffer - ticksPassed;
           
           if (ticksToWait > 0) {
             relDuration += ticksToWait;
           }
         } 
       } 
     } 
 
     
     WeaponReloadEvent event = new WeaponReloadEvent(player, parent_node, getString(parent_node + reloadSound), relDuration);
     this.plugin.getServer().getPluginManager().callEvent(event);
 
     
     final String soundsReload = event.getSounds();
     relDuration = event.getReloadDuration();
 
     
     if (event.getReloadSpeed() != 1.0D)
     { double reloadSpeed = event.getReloadSpeed();
       relDuration = (int)(relDuration * reloadSpeed);
       if (!reloadIndie) playSoundEffectsScaled(player, parent_node, null, true, reloadSpeed, soundsReload);
        }
     
     else if (!reloadIndie) { playSoundEffects(player, parent_node, null, true, null, soundsReload); }
 
 
     
     final int reloadShootDelay = akimboSingleReload ? getInt(parent_node + ".Reload.Dual_Wield.Single_Reload_Shoot_Delay") : getInt(parent_node + ".Reload.Reload_Shoot_Delay");
     
     int task_ID = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable()
         {
           public void run()
           {
             if (takeAmmo && ammoEnable && !CSDirector.this.csminion.containsItemStack(player, ammoInfo, 1, parent_node)) {
 
               
               CSDirector.this.removeInertReloadTag(player, 0, true);
               
               return;
             } 
             
             CSDirector.this.terminateReload(player);
 
             
             if (CSDirector.this.switchedTheItem(player, parent_node)) {
               return;
             }
             ItemStack item = player.getInventory().getItemInHand();
             if (item.getItemMeta().getDisplayName().contains("ᴿ")) {
               
               CSDirector.this.csminion.givePotionEffects(player, parent_node, ".Potion_Effects.Potion_Effect_Shooter", "reload");
               CSDirector.this.removeInertReloadTag(player, 0, true);
               int currentAmmo = CSDirector.this.getAmmoBetweenBrackets(player, parent_node, item);
               
               if (takeAmmo && ammoEnable) {
 
                 
                 if (reloadIndie) {
                   if (!dualWield) {
                     currentAmmo++;
                     CSDirector.this.csminion.replaceBrackets(item, String.valueOf(currentAmmo), parent_node);
                   } else {
                     int[] ammoReading = CSDirector.this.grabDualAmmo(item, parent_node);
                     int leftGun = ammoReading[0];
                     int rightGun = ammoReading[1];
                     
                     if (leftGun == reloadAmt / 2 || leftGun > rightGun) {
                       rightGun++;
                     } else if (rightGun == reloadAmt / 2 || rightGun > leftGun || leftGun == rightGun) {
                       leftGun++;
                     } 
                     
                     CSDirector.this.csminion.replaceBrackets(item, leftGun + " | " + rightGun, parent_node);
                   } 
                   
                   CSDirector.this.reloadShootDelay(player, parent_node, player.getInventory().getHeldItemSlot(), reloadShootDelay);
                   CSDirector.this.playSoundEffects(player, parent_node, null, false, null, soundsReload);
                   CSDirector.this.csminion.removeNamedItem(player, ammoInfo, 1, parent_node, false);
 
                   
                   WeaponReloadCompleteEvent weaponReloadCompleteEvent = new WeaponReloadCompleteEvent(player, parent_node);
                   CSDirector.this.plugin.getServer().getPluginManager().callEvent(weaponReloadCompleteEvent);
                   
                   CSDirector.this.reloadAnimation(player, parent_node, false);
                   
                   return;
                 } 
                 
                 if (!takeAsMag) {
                   
                   int invAmmo = CSDirector.this.csminion.countItemStacks(player, ammoInfo, parent_node);
                   int fillAmt = reloadAmt - currentAmmo;
                   currentAmmo += invAmmo;
                   if (currentAmmo > reloadAmt) currentAmmo = reloadAmt; 
                   if (!dualWield) {
                     
                     CSDirector.this.csminion.replaceBrackets(item, String.valueOf(currentAmmo), parent_node);
 
 
                   
                   }
                   else if (currentAmmo < reloadAmt) {
                     
                     int[] ammoReading = CSDirector.this.grabDualAmmo(item, parent_node);
                     int leftGun = ammoReading[0];
                     int rightGun = ammoReading[1];
 
                     
                     while (invAmmo > 0) {
                       
                       if (leftGun == reloadAmt / 2 || leftGun > rightGun) { rightGun++; }
                       else if (rightGun == reloadAmt / 2 || rightGun > leftGun || leftGun == rightGun) { leftGun++; }
                        invAmmo--;
                     } 
                     
                     CSDirector.this.csminion.replaceBrackets(item, leftGun + " | " + rightGun, parent_node);
                   }
                   else {
                     
                     CSDirector.this.csminion.replaceBrackets(item, replacer, parent_node);
                   } 
                   
                   CSDirector.this.csminion.removeNamedItem(player, ammoInfo, fillAmt, parent_node, false);
 
                 
                 }
                 else if (!dualWield) {
                   
                   CSDirector.this.csminion.replaceBrackets(item, replacer, parent_node);
                   CSDirector.this.csminion.removeNamedItem(player, ammoInfo, 1, parent_node, false);
                 
                 }
                 else {
                   
                   int invAmmo = CSDirector.this.csminion.countItemStacks(player, ammoInfo, parent_node);
                   int[] ammoReading = CSDirector.this.grabDualAmmo(item, parent_node);
                   int amtToRemove = 0;
                   for (int i = 0; i < 2; i++) {
                     
                     if (ammoReading[i] != reloadAmt / 2 && invAmmo > 0) {
                       
                       ammoReading[i] = reloadAmt / 2;
                       amtToRemove++;
                       invAmmo--;
                     } 
                   } 
                   CSDirector.this.csminion.replaceBrackets(item, String.valueOf(ammoReading[0]) + " | " + ammoReading[1], parent_node);
                   CSDirector.this.csminion.removeNamedItem(player, ammoInfo, amtToRemove, parent_node, false);
                 } 
 
 
                 
                 CSDirector.this.reloadShootDelay(player, parent_node, player.getInventory().getHeldItemSlot(), reloadShootDelay);
 
                 
                 if (finalBreakAct) {
                   CSDirector.this.checkBoltPosition(player, parent_node);
                 } else if (!item.getItemMeta().getDisplayName().contains("▪ «")) {
                   CSDirector.this.correctBoltPosition(player, parent_node, false, closeTime, false, true, pumpAct, false);
                 } 
 
                 
                 CSDirector.this.removeInertReloadTag(player, 0, true);
 
                 
                 WeaponReloadCompleteEvent event = new WeaponReloadCompleteEvent(player, parent_node);
                 CSDirector.this.plugin.getServer().getPluginManager().callEvent(event);
               
               }
               else {
                 
                 if (reloadIndie) {
                   if (!dualWield) {
                     currentAmmo++;
                     CSDirector.this.csminion.replaceBrackets(item, String.valueOf(currentAmmo), parent_node);
                   } else {
                     int[] ammoReading = CSDirector.this.grabDualAmmo(item, parent_node);
                     int leftGun = ammoReading[0];
                     int rightGun = ammoReading[1];
                     
                     if (leftGun == reloadAmt / 2 || leftGun > rightGun) {
                       rightGun++;
                     } else if (rightGun == reloadAmt / 2 || rightGun > leftGun || leftGun == rightGun) {
                       leftGun++;
                     } 
                     
                     CSDirector.this.csminion.replaceBrackets(item, leftGun + " | " + rightGun, parent_node);
                   } 
                   
                   CSDirector.this.reloadShootDelay(player, parent_node, player.getInventory().getHeldItemSlot(), reloadShootDelay);
                   CSDirector.this.playSoundEffects(player, parent_node, null, false, null, soundsReload);
 
                   
                   WeaponReloadCompleteEvent weaponReloadCompleteEvent = new WeaponReloadCompleteEvent(player, parent_node);
                   CSDirector.this.plugin.getServer().getPluginManager().callEvent(weaponReloadCompleteEvent);
                   
                   CSDirector.this.reloadAnimation(player, parent_node, false);
                   return;
                 } 
                 player.removeMetadata("markOfTheReload", CSDirector.this.plugin);
 
 
                 
                 CSDirector.this.reloadShootDelay(player, parent_node, player.getInventory().getHeldItemSlot(), reloadShootDelay);
 
                 
                 CSDirector.this.csminion.replaceBrackets(item, replacer, parent_node);
 
                 
                 if (finalBreakAct) {
                   CSDirector.this.checkBoltPosition(player, parent_node);
                 } else if (!item.getItemMeta().getDisplayName().contains("▪ «")) {
                   CSDirector.this.correctBoltPosition(player, parent_node, false, closeTime, false, true, pumpAct, false);
                 } 
 
                 
                 WeaponReloadCompleteEvent event = new WeaponReloadCompleteEvent(player, parent_node);
                 CSDirector.this.plugin.getServer().getPluginManager().callEvent(event);
               } 
             }  }
         }, (long) relDuration);
 
     
     String user = player.getName();
     Collection<Integer> values_reload = this.global_reload_IDs.get(user);
     if (values_reload == null) {
       
       values_reload = new ArrayList<Integer>();
       this.global_reload_IDs.put(user, values_reload);
     } 
     values_reload.add(Integer.valueOf(task_ID));
   }
 
 
 
 
 
   
   public void reloadShootDelay(final Player player, String parentNode, int gunSlot, int delay, String... customTag) {
     if (delay < 1) {
       return;
     }
     final String playerName = player.getName();
     Map<String, Integer> tagsAndDelays = this.delay_list.get(playerName);
 
     
     if (tagsAndDelays == null) {
       tagsAndDelays = new HashMap<String, Integer>();
       this.delay_list.put(playerName, tagsAndDelays);
     } 
 
     
     final String metadataTag = ((customTag.length > 1) ? customTag[1] : parentNode) + ((customTag.length > 0) ? customTag[0] : "reloadShootDelay") + gunSlot;
     Integer prevTaskID = tagsAndDelays.get(metadataTag);
     if (prevTaskID != null) {
       Bukkit.getScheduler().cancelTask(prevTaskID.intValue());
     }
 
     
     player.setMetadata(metadataTag, new FixedMetadataValue(this, Boolean.valueOf(true)));
 
     
     int newTaskID = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable()
         {
           public void run() {
             player.removeMetadata(metadataTag, CSDirector.this.plugin);
 
             
             Map<String, Integer> tagsAndDelays = CSDirector.this.delay_list.get(playerName);
             if (tagsAndDelays != null) {
               tagsAndDelays.remove(metadataTag);
             }
           }
         }, (long) delay);
 
     
     tagsAndDelays.put(metadataTag, Integer.valueOf(newTaskID));
   }
 
 
   
   public void projectileExplosion(final Entity objProj, final String parent_node, boolean grenade, final Player player, final boolean landmine, final boolean rde, final Location loc, final Block c4, final boolean trap, final int cTimes) {
     if (!getBoolean(parent_node + ".Explosions.Enable") || (!rde && !this.csminion.regionCheck(objProj, parent_node)))
       return;  int delay = grenade ? 0 : getInt(parent_node + ".Explosions.Explosion_Delay");
     
     Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable()
         {
           public void run() {
             Location location = null;
             World world = null;
             if (!rde) {
               world = objProj.getWorld();
               location = objProj.getLocation().getBlock().getLocation().add(0.5D, 0.5D, 0.5D);
               if (objProj instanceof WitherSkull || objProj instanceof LargeFireball) {
                 BlockIterator checker = new BlockIterator(world, objProj.getLocation().toVector(), objProj.getVelocity().normalize().multiply(-1), 0.0D, 4);
                 Block block = null;
                 while (checker.hasNext()) {
                   block = checker.next();
                   if (CSDirector.this.isAir(block.getType())) {
                     location = block.getLocation().add(0.5D, 0.5D, 0.5D);
                     break;
                   } 
                 } 
               } 
               if (landmine) objProj.remove(); 
             } else if (!trap) {
               c4.removeMetadata("CS_transformers", CSDirector.this.plugin);
               c4.setType(Material.AIR);
               location = loc;
               world = loc.getWorld();
             } else {
               c4.removeMetadata("CS_btrap", CSDirector.this.plugin);
               location = c4.getRelative(BlockFace.UP).getLocation().add(0.5D, 0.5D, 0.5D);
               world = c4.getLocation().getWorld();
             } 
 
             
             boolean airstrike = CSDirector.this.getBoolean(parent_node + ".Airstrikes.Enable");
             boolean cEnable = CSDirector.this.getBoolean(parent_node + ".Cluster_Bombs.Enable");
             int cOfficialTimes = CSDirector.this.getInt(parent_node + ".Cluster_Bombs.Number_Of_Splits");
             if (cEnable && !airstrike && cTimes < cOfficialTimes) {
               
               int cAmount = CSDirector.this.getInt(parent_node + ".Cluster_Bombs.Number_Of_Bomblets");
               int cSpeed = CSDirector.this.getInt(parent_node + ".Cluster_Bombs.Speed_Of_Bomblets");
               
               int timer = CSDirector.this.getInt(parent_node + ".Cluster_Bombs.Delay_Before_Detonation");
               Random r = new Random();
               
               int totalAmount = (int)Math.pow(cAmount, cOfficialTimes);
               if (totalAmount > 1000) {
                 
                 if (player != null)
                 {
                   player.sendMessage(String.valueOf(CSDirector.this.heading) + cAmount + " to the power of " + cOfficialTimes + " equates to " + totalAmount + " bomblets and consequent explosions! For your safety, CrackShot does not accept total bomblet amounts of over 1000. Please lower the value for 'Number_Of_Splits' and/or 'Number_Of_Bomblets' for the weapon '" + parent_node + "'.");
                 }
                 
                 return;
               } 
               for (int i = 0; i < cAmount; i++) {
                 
                 location.setPitch(-(r.nextInt(90) + r.nextInt(90)));
                 location.setYaw(r.nextInt(360));
                 double cSpeedF = cSpeed * (100 - r.nextInt(25) - r.nextInt(25)) * 0.001D;
                 CSDirector.this.launchGrenade(player, parent_node, timer, location.getDirection().multiply(cSpeedF), location, cTimes + 1);
               } 
               
               CSDirector.this.csminion.giveParticleEffects(null, parent_node, ".Cluster_Bombs.Particle_Release", false, location);
               CSDirector.this.playSoundEffects(null, parent_node, ".Cluster_Bombs.Sounds_Release", false, location);
 
               
               WeaponExplodeEvent weaponExplodeEvent = new WeaponExplodeEvent(player, location, parent_node, true, false);
               CSDirector.this.plugin.getServer().getPluginManager().callEvent(weaponExplodeEvent);
               
               return;
             } 
             
             boolean shrapEnable = CSDirector.this.getBoolean(parent_node + ".Shrapnel.Enable");
             if (shrapEnable) {
               Byte secData;
               String shrapType = CSDirector.this.getString(parent_node + ".Shrapnel.Block_Type");
               int shrapAmount = CSDirector.this.getInt(parent_node + ".Shrapnel.Amount");
               int shrapSpeed = CSDirector.this.getInt(parent_node + ".Shrapnel.Speed");
               
               boolean placeBlocks = CSDirector.this.getBoolean(parent_node + ".Shrapnel.Place_Blocks");
 
               
               String[] blockInfo = shrapType.split("~");
               if (blockInfo.length < 2) blockInfo = new String[] { blockInfo[0], "0" };
               
               Material blockMat = MaterialManager.getMaterial(shrapType);
               if (blockMat == null) {
                 player.sendMessage(CSDirector.this.heading + "'" + shrapType + "' of weapon '" + parent_node + "' is not a valid block-type.");
                 
                 return;
               } 
               
               try {
                 secData = Byte.valueOf(blockInfo[1]);
               } catch (NumberFormatException ex) {
                 player.sendMessage(CSDirector.this.heading + "'" + shrapType + "' of weapon '" + parent_node + "' has an invalid secondary data value.");
                 
                 return;
               } 
               Random r = new Random();
               for (int i = 0; i < shrapAmount; i++) {
                 
                 location.setPitch(-(r.nextInt(90) + r.nextInt(90)));
                 location.setYaw(r.nextInt(360));
                 FallingBlock shrapnel = location.getWorld().spawnFallingBlock(location, blockMat, secData.byteValue());
                 if (!placeBlocks) shrapnel.setMetadata("CS_shrapnel", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
                 shrapnel.setDropItem(false);
                 double shrapSpeedF = shrapSpeed * (100 - r.nextInt(25) - r.nextInt(25)) * 0.001D;
                 shrapnel.setVelocity(location.getDirection().multiply(shrapSpeedF));
               } 
             } 
 
             
             WeaponExplodeEvent explodeEvent = new WeaponExplodeEvent(player, location, parent_node, false, false);
             CSDirector.this.plugin.getServer().getPluginManager().callEvent(explodeEvent);
             
             CSDirector.this.csminion.displayFireworks(objProj, parent_node, ".Fireworks.Firework_Explode");
             boolean ownerNoDam = CSDirector.this.getBoolean(parent_node + ".Explosions.Enable_Owner_Immunity");
             boolean noDam = CSDirector.this.getBoolean(parent_node + ".Explosions.Explosion_No_Damage");
             boolean frenFire = CSDirector.this.getBoolean(parent_node + ".Explosions.Enable_Friendly_Fire");
             boolean noGrief = CSDirector.this.getBoolean(parent_node + ".Explosions.Explosion_No_Grief");
             boolean isFire = CSDirector.this.getBoolean(parent_node + ".Explosions.Explosion_Incendiary");
             int boomRadius = CSDirector.this.getInt(parent_node + ".Explosions.Explosion_Radius");
             if (boomRadius > 20) boomRadius = 20;
             
             TNTPrimed tnt = location.getWorld().spawn(location, TNTPrimed.class);
             tnt.setYield(boomRadius);
             tnt.setIsIncendiary(isFire);
             tnt.setFuseTicks(0);
             tnt.setMetadata("CS_Label", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
             tnt.setMetadata("CS_potex", new FixedMetadataValue(CSDirector.this.plugin, parent_node));
             
             if (!rde) tnt.setMetadata("C4_Friendly", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
             if (noGrief) tnt.setMetadata("nullify", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
             if (noDam) tnt.setMetadata("CS_nodam", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
             
             if (player != null) {
               tnt.setMetadata("CS_pName", new FixedMetadataValue(CSDirector.this.plugin, player.getName()));
               if (!frenFire) tnt.setMetadata("CS_ffcheck", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
               if (ownerNoDam) tnt.setMetadata("0wner_nodam", new FixedMetadataValue(CSDirector.this.plugin, Boolean.valueOf(true)));
             } 
           }
         }, Math.abs(delay));
   }
 
   
   public void prepareTermination(final Entity proj, final boolean remove, Long delay) {
     Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
         {
           public void run() {
             if (remove) {
               proj.remove();
             } else {
               proj.setVelocity(proj.getVelocity().multiply(0.25D));
             } 
           }
         },  delay.longValue());
   }
 
   
   @EventHandler
   public void onPlayerItemHeld(PlayerItemHeldEvent event) {
     Player player = event.getPlayer();
     removeInertReloadTag(player, event.getPreviousSlot(), false);
     removeInertReloadTag(player, event.getNewSlot(), false);
     unscopePlayer(player);
     terminateAllBursts(player);
     terminateReload(player);
 
     
     ItemStack heldItem = player.getInventory().getItem(event.getNewSlot());
     if (heldItem != null) {
       String[] pc = itemParentNode(heldItem, player);
       if (pc == null || !Boolean.valueOf(pc[1]).booleanValue())
         return;  ItemStack weapon = this.csminion.vendingMachine(pc[0]);
       weapon.setAmount(player.getInventory().getItem(event.getNewSlot()).getAmount());
       player.getInventory().setItem(event.getNewSlot(), weapon);
     } 
   }
 
   
   @EventHandler
   public void onPlayerDisconnect(PlayerQuitEvent event) {
     Player player = event.getPlayer();
     removeInertReloadTag(player, 0, true);
     unscopePlayer(player);
     terminateAllBursts(player);
     terminateReload(player);
 
     
     String playerName = player.getName();
     if (this.itembombs.containsKey(playerName)) {
       Map<String, ArrayDeque<Item>> subList = this.itembombs.get(playerName);
       for (ArrayDeque<Item> subSubList : subList.values()) {
         while (!subSubList.isEmpty()) {
           subSubList.removeFirst().remove();
         }
       } 
       this.itembombs.remove(playerName);
     } 
 
     
     this.delay_list.remove(playerName);
     this.delayed_reload_IDs.remove(playerName);
     this.c4_backup.remove(playerName);
     this.last_shot_list.remove(playerName);
 
     
     this.rpm_ticks.remove(playerName);
   }
 
 
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void onGunThrow(PlayerDropItemEvent event) {
     ItemStack trash = event.getItemDrop().getItemStack();
     String[] pc = itemParentNode(trash, event.getPlayer());
     if (pc == null)
       return;  if (!getBoolean(pc[0] + ".Reload.Enable"))
       return;  if (getBoolean(pc[0] + ".Reload.Reload_With_Mouse"))
       return; 
     Player player = event.getPlayer();
     player.getInventory().getHeldItemSlot();
 
     
     if (!player.hasMetadata("dr0p_authorised")) {
       event.setCancelled(true);
       delayedReload(player, pc[0]);
     } 
   }
 
   
   @EventHandler
   public void onPlayerDeath(PlayerDeathEvent event) {
     final Player player = event.getEntity();
     removeInertReloadTag(player, 0, true);
     unscopePlayer(player);
     terminateAllBursts(player);
     terminateReload(player);
 
     
     List<ItemStack> newInv = new ArrayList<ItemStack>();
     Iterator<ItemStack> it = event.getDrops().iterator();
     while (it.hasNext()) {
       ItemStack item = it.next();
       if (item != null && itemIsSafe(item)) {
         String[] parent_node = itemParentNode(item, player);
         if (parent_node != null && getBoolean(parent_node[0] + ".Abilities.Death_No_Drop")) {
           newInv.add(item);
           it.remove();
         } 
       } 
     } 
 
     
     if (!newInv.isEmpty()) {
       final ItemStack[] newStack = newInv.toArray(new ItemStack[newInv.size()]);
       Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
           {
             public void run() {
               player.getInventory().setContents(newStack);
             }
           });
     } 
 
     
     if (event.getDeathMessage() != null) {
       String message = event.getDeathMessage().replaceAll("(?<=«).*?(?=»)", "");
       message = message.replaceAll(" «", "");
       message = message.replaceAll(String.valueOf('ᴿ'), "");
       message = message.replaceAll("[»▪▫˗]", "");
       event.setDeathMessage(message);
     } 
 
     
     if (event.getEntity().getKiller() instanceof Player) {
       Player shooter = event.getEntity().getKiller();
       String parent_node = returnParentNode(shooter);
       if (parent_node == null)
         return; 
       String msg = getString(parent_node + ".Custom_Death_Message.Normal");
       if (msg == null)
         return;  msg = msg.replaceAll("<shooter>", shooter.getName());
       msg = msg.replaceAll("<victim>", player.getName());
       event.setDeathMessage(msg);
     } 
   }
 
   
   @EventHandler
   public void clickGun(InventoryClickEvent event) {
     if (event.getWhoClicked() instanceof Player player) {
       
       ItemStack currentItem = event.getCurrentItem();

               if (event.getSlotType() == InventoryType.SlotType.QUICKBAR) {
         removeInertReloadTag(player, event.getSlot(), false);
         unscopePlayer(player);
         terminateAllBursts(player);
         terminateReload(player);
       } 
 
       
       if (event.getSlot() != -1 && currentItem != null) {
         String[] pc = itemParentNode(currentItem, player);
         if (pc == null) {
           return;
         }
         if (event.getInventory().getType() == InventoryType.ANVIL && event.getSlot() == 2 && event.getSlotType() == InventoryType.SlotType.RESULT) {
           player.playSound(player.getLocation(), SoundManager.get("WOOD_CLICK"), 0.5F, 2.0F);
           event.setCancelled(true);
           
           return;
         } 
         
         if (!Boolean.valueOf(pc[1]).booleanValue())
           return; 
         ItemStack weapon = this.csminion.vendingMachine(pc[0]);
         weapon.setAmount(currentItem.getAmount());
         event.setCurrentItem(weapon);
       } 
 
       
       if (event.getSlot() == -999) {
         ItemStack trash = event.getCursor();
         String[] pc = itemParentNode(trash, player);
         if (pc == null)
           return; 
         player.setMetadata("dr0p_authorised", new FixedMetadataValue(this, Boolean.valueOf(true)));
         this.csminion.tempVars(player, "dr0p_authorised", Long.valueOf(1L));
       } 
     } 
   }
 
   
   public void unscopePlayer(Player player, boolean... manual) {
     if (player.hasMetadata("ironsights")) {
       String pName = player.getName();
       String parentNode = player.getMetadata("ironsights").get(0).asString();
 
       
       if (manual.length == 0) {
         WeaponScopeEvent scopeEvent = new WeaponScopeEvent(player, parentNode, false);
         getServer().getPluginManager().callEvent(scopeEvent);
         if (scopeEvent.isCancelled())
           return; 
       } 
       player.removeMetadata("ironsights", this);
       player.removePotionEffect(PotionEffectType.SPEED);
       
       if (player.hasMetadata("night_scoping")) {
         player.removeMetadata("night_scoping", this);
         player.removePotionEffect(PotionEffectType.NIGHT_VISION);
       } 
       
       if (this.zoomStorage.containsKey(pName)) {
         Integer[] durAmp = this.zoomStorage.get(pName);
         player.addPotionEffect(PotionEffectType.SPEED.createEffect(durAmp[0], durAmp[1]));
       } 
       
       this.zoomStorage.remove(pName);
     } 
   }
 
 
   
   public void removeInertReloadTag(Player player, int item_slot, boolean no_slot) {
     ItemStack item = player.getInventory().getItem(item_slot);
     if (no_slot) item = player.getInventory().getItemInHand();
     
     if (item != null && itemIsSafe(item) && item.getItemMeta().getDisplayName().contains(String.valueOf('ᴿ'))) {
       
       String cleaner = item.getItemMeta().getDisplayName().replaceAll(String.valueOf('ᴿ'), "");
       if (no_slot) { this.csminion.setItemName(player.getInventory().getItemInHand(), cleaner); }
       else { this.csminion.setItemName(player.getInventory().getItem(item_slot), cleaner); }
     
     } 
   }
   
   public boolean switchedTheItem(Player player, String parent_node) {
     ItemStack item = player.getInventory().getItemInHand();
     String attachType = getAttachment(parent_node, item)[0];
     boolean attachment = (attachType != null && attachType.equalsIgnoreCase("accessory"));
     return !(item != null && itemIsSafe(item) && (attachment || !isDifferentItem(item, parent_node)));
   }
 
   
   public void terminateAllBursts(Player player) {
     Collection<Integer> values = this.burst_task_IDs.get(player.getName());
     
     if (values != null) {
       for (Iterator<Integer> iterator = values.iterator(); iterator.hasNext(); ) { int taskID = iterator.next().intValue();
         Bukkit.getScheduler().cancelTask(taskID); }
     
     }
 
     
     this.burst_task_IDs.remove(player.getName());
     this.rpm_shots.remove(player.getName());
   }
 
 
   
   public void terminateReload(Player player) {
     String playerName = player.getName();
     Collection<Integer> values = this.global_reload_IDs.get(playerName);
     if (values != null)
     {
       for (Integer value : values)
       {
         Bukkit.getScheduler().cancelTask(value.intValue());
       }
     }
 
     
     this.global_reload_IDs.remove(playerName);
     player.removeMetadata("markOfTheReload", this);
 
     
     if (this.delayed_reload_IDs.containsKey(playerName)) {
       Bukkit.getScheduler().cancelTask(this.delayed_reload_IDs.get(playerName).intValue());
       this.delayed_reload_IDs.remove(playerName);
     } 
   }
 
 
   
   public int getAmmoBetweenBrackets(Player player, String parent_node, ItemStack item) {
     boolean reloadEnable = getBoolean(parent_node + ".Reload.Enable");
     boolean dualWield = isDualWield(player, parent_node, item);
     int reloadAmt = getReloadAmount(player, parent_node, item);
     String replacer = dualWield ? (reloadAmt + " | " + reloadAmt) : String.valueOf(reloadAmt);
     if (dualWield) reloadAmt *= 2; 
     String attachType = getAttachment(parent_node, item)[0];
     
     String bracketInfo = this.csminion.extractReading(item.getItemMeta().getDisplayName());
     int detectedAmmo = reloadAmt;
     
     try {
       if (attachType != null) {
         int[] ammoReading = grabDualAmmo(item, parent_node);
         if (attachType.equalsIgnoreCase("main")) {
           detectedAmmo = ammoReading[0];
         } else if (attachType.equalsIgnoreCase("accessory")) {
           detectedAmmo = ammoReading[1];
         } 
       } else if (dualWield) {
         String strInBracks = bracketInfo;
         strInBracks = strInBracks.replaceAll(" ", "");
         String[] dualAmmo = strInBracks.split("\\|");
         if (dualAmmo[0].equals(String.valueOf('×')) || dualAmmo[1].equals(String.valueOf('×'))) return 125622; 
         detectedAmmo = Integer.valueOf(dualAmmo[0]).intValue() + Integer.valueOf(dualAmmo[1]).intValue();
       } else {
         if (bracketInfo.equals(String.valueOf('×')) && !reloadEnable) return 125622; 
         detectedAmmo = Integer.valueOf(bracketInfo).intValue();
       } 
     } catch (Exception ex) {
       
       this.csminion.replaceBrackets(item, replacer, parent_node);
     } 
     
     if (detectedAmmo > reloadAmt)
     {
       this.csminion.replaceBrackets(item, replacer, parent_node);
     }
     
     return detectedAmmo;
   }
 
 
   
   public void executeCommands(LivingEntity player, String parentNode, String childNode, String shooterName, String vicName, String flightTime, String totalDmg, boolean console) {
     String[] commandList = getString(parentNode + childNode).split("\\|"); byte b; int i; String[] arrayOfString1;
     for (i = (arrayOfString1 = commandList).length, b = 0; b < i; ) { String cmd = arrayOfString1[b];
       if (console) {
         getServer().dispatchCommand(getServer().getConsoleSender(), variableParser(cmd, shooterName, vicName, flightTime, totalDmg));
       } else {
         ((Player)player).performCommand(variableParser(cmd, shooterName, vicName, flightTime, totalDmg));
       } 
       b++; }
   
   }
   
   public String variableParser(String filter, String shooter, String victim, String flightTime, String totalDmg) {
     filter = filter.replaceAll("<shooter>", shooter).replaceAll("<victim>", victim).replaceAll("<damage>", totalDmg).replaceAll("<flight>", flightTime);
     return filter;
   }
 
 
   
   public void sendPlayerMessage(LivingEntity player, String parentNode, String childNode, String shooterName, String vicName, String flightTime, String totalDmg) {
     String message = getString(parentNode + childNode);
     if (message == null)
       return;  if (player instanceof Player) player.sendMessage(variableParser(message, shooterName, vicName, flightTime, totalDmg));
   
   }
   
   public boolean spawnEntities(LivingEntity player, String parentNode, String childNode, LivingEntity tamer) {
     if (!getBoolean(parentNode + ".Spawn_Entity_On_Hit.Enable")) return false;
     String entName = getString(parentNode + ".Spawn_Entity_On_Hit.Mob_Name");
     String proType = getString(parentNode + ".Shooting.Projectile_Type");
     boolean targetVictim = getBoolean(parentNode + ".Spawn_Entity_On_Hit.Make_Entities_Target_Victim");
     boolean noDrop = getBoolean(parentNode + ".Spawn_Entity_On_Hit.Entity_Disable_Drops");
     int timedDeath = getInt(parentNode + ".Spawn_Entity_On_Hit.Timed_Death");
     int spawnChance = getInt(parentNode + ".Spawn_Entity_On_Hit.Chance");
     if (getString(parentNode + childNode) == null) return false;
     if (proType.equalsIgnoreCase("energy")) {
       printM("For the weapon '" + parentNode + "', the 'energy' projectile-type does not support the Spawn_Entity_On_Hit module.");
       return false;
     } 
     
     Random generator = new Random();
     int diceRoll = generator.nextInt(100);
     if (diceRoll > spawnChance) return false;
     
     String[] entList = getString(parentNode + childNode).split(","); byte b; int i; String[] arrayOfString1;
     for (i = (arrayOfString1 = entList).length, b = 0; b < i; ) { String entity = arrayOfString1[b];
       String spaceFilter = entity.replace(" ", "");
       String[] args = spaceFilter.split("-");
       if (args.length == 4) {
         int entAmount = 0;
         try {
           entAmount = Integer.parseInt(args[3]);
         } catch (NumberFormatException ex) {
           printM("'" + entAmount + "' in the node 'EntityType_Baby_Explode_Amount' of weapon '" + parentNode + "' is not a valid number!");
           
           break;
         } 
         for (int j = 0; j < entAmount; j++) {
           EntityType entType;
           String mobEnum = args[0].toUpperCase();
           if (args[0].equals("ZOMBIE_VILLAGER")) {
             mobEnum = "ZOMBIE";
           } else if (args[0].equals("WITHER_SKELETON")) {
             mobEnum = "SKELETON";
           } else if (args[0].equals("TAMED_WOLF")) {
             mobEnum = "WOLF";
           } 
 
           
           try {
             entType = EntityType.valueOf(mobEnum);
           } catch (IllegalArgumentException ex) {
             printM("'" + args[0] + "' of weapon '" + parentNode + "' is not a valid entity!");
             
             break;
           } 
           final LivingEntity spawnedMob = (LivingEntity)player.getWorld().spawnEntity(player.getLocation(), entType);
           
           if (Boolean.parseBoolean(args[1])) {
             if (spawnedMob instanceof Zombie) {
               ((Zombie)spawnedMob).setBaby(true);
             } else if (spawnedMob instanceof Creeper) {
               ((Creeper)spawnedMob).setPowered(true);
             } else if (spawnedMob instanceof Ageable) {
               ((Ageable)spawnedMob).setBaby();
             } 
           }
           
           if (args[0].equalsIgnoreCase("ZOMBIE_VILLAGER")) {
             ((Zombie)spawnedMob).setVillager(true);
           } else if (args[0].equalsIgnoreCase("WITHER_SKELETON")) {
             ((Skeleton)spawnedMob).setSkeletonType(Skeleton.SkeletonType.WITHER);
           } else if (args[0].equalsIgnoreCase("TAMED_WOLF") && tamer instanceof AnimalTamer) {
             ((Wolf)spawnedMob).setOwner((AnimalTamer)tamer);
           } 
           
           if (entName != null) {
             spawnedMob.setCustomName(entName);
             spawnedMob.setCustomNameVisible(true);
           } 
           if (Boolean.parseBoolean(args[2])) spawnedMob.setMetadata("CS_Boomer", new FixedMetadataValue(this, Boolean.valueOf(true)));
           if (noDrop) spawnedMob.setMetadata("CS_NoDrops", new FixedMetadataValue(this, Boolean.valueOf(true)));
           if (targetVictim) spawnedMob.damage(0.0D, player);
           
           if (timedDeath != 0) {
             Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
                 {
                   public void run() {
                     spawnedMob.damage(400.0D);
                   }
                 },  Long.valueOf(timedDeath).longValue());
           }
         } 
       } else {
         printM("'" + spaceFilter + "' of weapon '" + parentNode + "' has an invalid format!");
       }  b++; }
     
     return true;
   }
 
   
   @EventHandler
   public void onSpawnedEntityDeath(EntityDeathEvent event) {
     if (event.getEntity().hasMetadata("CS_Boomer")) {
       TNTPrimed tnt = event.getEntity().getWorld().spawn(event.getEntity().getLocation(), TNTPrimed.class);
       tnt.setYield(2.0F);
       tnt.setFuseTicks(0);
       tnt.setMetadata("nullify", new FixedMetadataValue(this, Boolean.valueOf(true)));
     } 
     
     if (event.getEntity().hasMetadata("CS_NoDrops")) {
       event.setDroppedExp(0);
       event.getDrops().clear();
     } 
   }
   
   @EventHandler
   public void createGunShop(SignChangeEvent event) {
     String lineOne = event.getLine(0);
     if (!lineOne.contains("[CS]"))
       return;  String filter = lineOne.replaceAll(Pattern.quote("[CS]"), "");
     
     try {
       Integer.valueOf(filter);
     }
     catch (NumberFormatException ex) {
       return;
     } 
     
     for (String parent_node : this.parentlist.values()) {
       if (getBoolean(parent_node + ".SignShops.Enable")) {
         
         if (!event.getPlayer().hasPermission("crackshot.shops." + parent_node) && !event.getPlayer().hasPermission("crackshot.shops.all")) {
           CSMessages.sendMessage(event.getPlayer(), this.heading, CSMessages.Message.NP_STORE_CREATE.getMessage());
           
           return;
         } 
         int gunID = getInt(parent_node + ".SignShops.Sign_Gun_ID");
         if (gunID != 0 && gunID == Integer.valueOf(filter).intValue()) {
           event.setLine(0, "§fStore No᎐ " + gunID);
           CSMessages.sendMessage(event.getPlayer(), this.heading, CSMessages.Message.STORE_CREATED.getMessage());
           break;
         } 
       } 
     } 
   }
 
   
   public boolean shopEvent(PlayerInteractEvent event) {
     boolean retVal = false;
     Sign signState = (Sign)event.getClickedBlock().getState();
     if (signState.getLine(0).contains("§fStore No᎐")) {
       Player player = event.getPlayer();
       String signLineOne = signState.getLine(0).replaceAll("§fStore No᎐ ", "");
       
       for (String parentNode : this.parentlist.values()) {
         if (getBoolean(parentNode + ".SignShops.Enable") && getString(parentNode + ".SignShops.Price") != null) {
           String currency; int amount, shopID, gunID = getInt(parentNode + ".SignShops.Sign_Gun_ID");
           String priceInfo = getString(parentNode + ".SignShops.Price");
           String[] signInfo = priceInfo.split("-");
 
 
 
           
           try {
             shopID = Integer.valueOf(signLineOne).intValue();
           } catch (NumberFormatException ex) {
             break;
           } 
           
           try {
             currency = signInfo[0];
             amount = Integer.valueOf(signInfo[1]).intValue();
           } catch (NumberFormatException ex) {
             player.sendMessage(this.heading + "'Price: " + priceInfo + "' of weapon '" + parentNode + "' does not contain a valid item ID and/or amount!");
             
             break;
           } 
           if (gunID == shopID) {
             boolean creativeMode = (player.getGameMode() != GameMode.CREATIVE);
 
             
             if (creativeMode || (!player.hasPermission("crackshot.store." + parentNode) && !player.hasPermission("crackshot.store.all"))) {
               event.setCancelled(true);
             }
 
             
             if (player.hasPermission("crackshot.buy." + parentNode) || player.hasPermission("crackshot.buy.all")) {
               if (creativeMode) {
                 if (this.csminion.countItemStacks(player, signInfo[0], parentNode) < amount) {
                   CSMessages.sendMessage(player, this.heading, CSMessages.Message.STORE_CANNOT_AFFORD.getMessage());
 
                   
                   CSMessages.sendMessage(player, this.heading, CSMessages.Message.STORE_ITEMS_NEEDED.getMessage(amount, MaterialManager.getMaterial(currency).toString())); break;
                 }  if (player.getInventory().firstEmpty() != -1) {
                   this.csminion.removeNamedItem(player, signInfo[0], amount, parentNode, true);
                   this.csminion.getWeaponCommand(player, parentNode, false, null, false, false);
 
                   
                   String milk = getString(parentNode + ".Item_Information.Item_Name");
                   
                   CSMessages.sendMessage(player, this.heading, CSMessages.Message.STORE_PURCHASED.getMessage(milk));
                   retVal = true;
                 } 
               }  break;
             } 
             CSMessages.sendMessage(player, this.heading, CSMessages.Message.NP_STORE_PURCHASE.getMessage());
             
             break;
           } 
         } 
       } 
     } 
     
     return retVal;
   }
 
   
   public boolean checkBoltPosition(Player player, String parent_node) {
     ItemStack item = player.getInventory().getItemInHand();
     String actType = getString(parent_node + ".Firearm_Action.Type");
     if (actType == null || isDualWield(player, parent_node, item)) return false;
 
     
     String[] validTypes = { "bolt", "lever", "pump", "break", "revolver", "slide" }; byte b; int i; String[] arrayOfString1;
     for (i = (arrayOfString1 = validTypes).length, b = 0; b < i; ) { String str = arrayOfString1[b];
       if (actType.equalsIgnoreCase(str))
         break; 
       if (str.equals("slide")) {
         printM("'" + actType + "' of weapon '" + parent_node + "' is not a valid firearm action! The accepted values are slide, bolt, lever, pump, break or revolver!");
         return false;
       } 
       b++; }
     
     int openTime = getInt(parent_node + ".Firearm_Action.Open_Duration");
     int closeTime = getInt(parent_node + ".Firearm_Action.Close_Duration");
     
     if (!itemIsSafe(item)) return false;
     
     String itemName = item.getItemMeta().getDisplayName();
 
     
     int chamberPos = itemName.lastIndexOf("§") + 3;
     char chamber = itemName.charAt(chamberPos);
     if (chamber == '«') {
       this.csminion.setItemName(item, itemName.replace("«", "▪ «"));
     } else if (chamber != '▪' && chamber != '▫' && chamber != '˗') {
       this.csminion.setItemName(item, itemName.substring(0, chamberPos) + '▪' + itemName.substring(chamberPos + 1));
     } 
 
     
     int detectedAmmo = getAmmoBetweenBrackets(player, parent_node, item);
     if (actType.toLowerCase().contains("break") || actType.toLowerCase().contains("revolver") || actType.toLowerCase().contains("slide")) {
       if (detectedAmmo > 0) {
         if (chamber == '▫') {
           correctBoltPosition(player, parent_node, false, closeTime, false, false, false, true);
           return true;
         } 
       } else {
         reloadAnimation(player, parent_node);
         
         boolean ammoEnable = getBoolean(parent_node + ".Ammo.Enable");
         String ammoInfo = getString(parent_node + ".Ammo.Ammo_Item_ID");
         boolean takeAmmo = getBoolean(parent_node + ".Reload.Take_Ammo_On_Reload");
         if (ammoEnable && takeAmmo && !this.csminion.containsItemStack(player, ammoInfo, 1, parent_node)) {
           playSoundEffects(player, parent_node, ".Ammo.Sounds_Shoot_With_No_Ammo", false, null);
         }
         
         return true;
       } 
       return false;
     } 
 
 
 
 
 
     
     boolean chamberFired = (chamber == '▪');
     boolean chamberOpened = (chamber == '˗');
     if (chamberFired) this.csminion.setItemName(item, itemName.replace("▪", "▫")); 
     correctBoltPosition(player, parent_node, !chamberOpened, chamberOpened ? closeTime : openTime, (detectedAmmo <= 0), false, false, false);
     return !chamberFired;
   }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   
   public void correctBoltPosition(final Player player, final String parent_node, final boolean boltPull, int delay, final boolean reloadPrep, final boolean reloadFin, final boolean pumpExit, final boolean breakAct) {
     final String actType = getString(parent_node + ".Firearm_Action.Type");
     if (actType == null || isDualWield(player, parent_node, player.getItemInHand()))
       return;  String[] validTypes = { "bolt", "lever", "pump", "break", "revolver", "slide" }; byte b; int i;
     String[] arrayOfString1;
     for (i = (arrayOfString1 = validTypes).length, b = 0; b < i; ) { String str = arrayOfString1[b];
       if (actType.equalsIgnoreCase(str))
         break; 
       if (str.equals("slide")) {
         printM("'" + actType + "' of weapon '" + parent_node + "' is not a valid firearm action! The accepted values are slide, bolt, lever, pump, break or revolver!");
         return;
       } 
       b++; }
     
     final int heldSlot = player.getInventory().getHeldItemSlot();
     if (player.hasMetadata("fiddling"))
       return;  player.setMetadata("fiddling", new FixedMetadataValue(this, Boolean.valueOf(true)));
     
     Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable()
         {
           public void run() {
             player.removeMetadata("fiddling", CSDirector.this.plugin);
             ItemStack item = player.getInventory().getItemInHand();
             int currentSlot = player.getInventory().getHeldItemSlot();
             int closeTime = CSDirector.this.getInt(parent_node + ".Firearm_Action.Close_Duration");
             int closeShootDelay = CSDirector.this.getInt(parent_node + ".Firearm_Action.Close_Shoot_Delay");
             
             if (!CSDirector.this.itemIsSafe(item)) {
               return;
             }
 
             
             String itemName = item.getItemMeta().getDisplayName();
             if (CSDirector.this.isDifferentItem(item, parent_node)) {
               return;
             }
             
             int chamberPos = itemName.lastIndexOf("§") + 3;
             char chamber = itemName.charAt(chamberPos);
 
             
             if (chamber == '«') {
               CSDirector.this.csminion.setItemName(item, itemName.replace("«", "▪ «")); return;
             } 
             if (chamber != '▪' && chamber != '▫' && chamber != '˗') {
               CSDirector.this.csminion.setItemName(item, itemName.substring(0, chamberPos) + '▪' + itemName.substring(chamberPos + 1));
               
               return;
             } 
             boolean isAttachment = itemName.contains(String.valueOf('▶'));
             boolean isReloading = itemName.contains(String.valueOf('ᴿ'));
             boolean switchedItems = !(!CSDirector.this.switchedTheItem(player, parent_node) && heldSlot == currentSlot);
             boolean isCocked = (reloadFin && chamber == '▪');
             
             if (isAttachment || isReloading || switchedItems || isCocked) {
               return;
             }
             
             if (breakAct) {
               CSDirector.this.playSoundEffects(player, parent_node, ".Firearm_Action.Sound_Close", false, null);
               CSDirector.this.csminion.setItemName(item, itemName.replaceAll("▫", "▪"));
               CSDirector.this.reloadShootDelay(player, parent_node, currentSlot, closeShootDelay, "noShooting");
               
               return;
             } 
             
             if (pumpExit && chamber == '▫') {
               CSDirector.this.correctBoltPosition(player, parent_node, true, 0, false, false, false, false);
               
               return;
             } 
             if (reloadPrep) {
               boolean isBreak = !(!actType.equalsIgnoreCase("break") && !actType.equalsIgnoreCase("revolver"));
               String nameToSet = itemName.replaceAll("▪", "▫");
               if (!isBreak) {
                 nameToSet = nameToSet.replaceAll("▫", "˗");
               }
 
               
               if (!itemName.contains("ᴿ")) {
                 CSDirector.this.csminion.setItemName(item, nameToSet + 'ᴿ');
               } else {
                 CSDirector.this.csminion.setItemName(item, nameToSet);
               } 
 
               
               int reloadOpenDelay = CSDirector.this.getInt(parent_node + ".Firearm_Action.Reload_Open_Delay");
               CSDirector.this.playSoundEffects(player, parent_node, ".Firearm_Action.Sound_Open", (reloadOpenDelay > 0), null);
 
 
               
               if (reloadOpenDelay > 0) {
                 CSDirector.this.delayedReload(player, parent_node, Long.valueOf(reloadOpenDelay).longValue());
 
                 
                 CSDirector.this.reloadShootDelay(player, parent_node, currentSlot, reloadOpenDelay, "noShooting");
               } else {
                 CSDirector.this.reloadAnimation(player, parent_node);
               } 
               
               return;
             } 
             
             if (boltPull) {
               CSDirector.this.playSoundEffects(player, parent_node, ".Firearm_Action.Sound_Open", false, null);
               CSDirector.this.csminion.setItemName(item, itemName.replaceAll("▫", "˗"));
               CSDirector.this.correctBoltPosition(player, parent_node, false, closeTime, false, false, false, false);
             } else if (actType.equalsIgnoreCase("slide") && (chamber == '▫' || chamber == '˗')) {
               
               if (chamber == '▫') {
                 CSDirector.this.csminion.setItemName(item, itemName.replaceAll("▫", "▪"));
               } else {
                 CSDirector.this.csminion.setItemName(item, itemName.replaceAll("˗", "▪"));
               } 
               
               CSDirector.this.playSoundEffects(player, parent_node, ".Firearm_Action.Sound_Close", false, null);
               CSDirector.this.reloadShootDelay(player, parent_node, currentSlot, closeShootDelay, "noShooting");
             } else {
               
               int detectedAmmo = CSDirector.this.getAmmoBetweenBrackets(player, parent_node, item);
               
               if (detectedAmmo > 0) {
                 CSDirector.this.csminion.setItemName(item, itemName.replaceAll("˗", "▪"));
                 CSDirector.this.playSoundEffects(player, parent_node, ".Firearm_Action.Sound_Close", false, null);
 
                 
                 CSDirector.this.reloadShootDelay(player, parent_node, currentSlot, closeShootDelay, "noShooting");
                 
                 if (detectedAmmo != 125622) {
                   CSDirector.this.ammoOperation(player, parent_node, detectedAmmo, item);
                 }
               } else {
                 
                 CSDirector.this.reloadAnimation(player, parent_node);
               } 
             }  }
         }, (long) delay);
   }
 
   
   public void ammoOperation(Player player, String parent_node, int detectedAmmo, ItemStack item) {
     boolean ammoEnable = getBoolean(parent_node + ".Ammo.Enable");
     String ammoInfo = getString(parent_node + ".Ammo.Ammo_Item_ID");
     boolean takeAmmo = getBoolean(parent_node + ".Ammo.Take_Ammo_Per_Shot");
 
     
     detectedAmmo--;
     this.csminion.replaceBrackets(item, String.valueOf(detectedAmmo), parent_node);
 
     
     if (ammoEnable && takeAmmo) {
       this.csminion.removeNamedItem(player, ammoInfo, 1, parent_node, false);
     }
 
     
     if (detectedAmmo == 0) {
       
       String actType = getString(parent_node + ".Firearm_Action.Type");
       playSoundEffects(player, parent_node, ".Reload.Sounds_Out_Of_Ammo", false, null);
       
       if (!itemIsSafe(item)) {
         return;
       }
       
       String itemName = item.getItemMeta().getDisplayName();
       
       if (actType != null) {
         
         if (actType.equalsIgnoreCase("bolt") || actType.equalsIgnoreCase("lever") || actType.equalsIgnoreCase("pump"))
         {
           if (!itemName.contains("▪"))
           {
             delayedReload(player, parent_node);
           }
         }
         else if (actType.equalsIgnoreCase("break") || actType.equalsIgnoreCase("revolver") || actType.equalsIgnoreCase("slide"))
         {
           
           if (actType.toLowerCase().contains("slide") && itemName.contains("▪")) {
 
             
             int openTime = getInt(parent_node + ".Firearm_Action.Open_Duration");
             if (openTime < 1) playSoundEffects(player, parent_node, ".Firearm_Action.Sound_Open", false, null);
             
             this.csminion.setItemName(item, itemName.replaceAll("▪", "▫"));
           } 
           
           delayedReload(player, parent_node);
         }
       
       } else {
         
         delayedReload(player, parent_node);
       } 
     } 
   }
   
   public boolean ammoSpecOps(Player player, String parentNode, int detectedAmmo, ItemStack item, boolean leftClick) {
     int ammoAmount;
     boolean ammoEnable = getBoolean(parentNode + ".Ammo.Enable");
     boolean takeAmmo = getBoolean(parentNode + ".Ammo.Take_Ammo_Per_Shot");
     String ammoInfo = getString(parentNode + ".Ammo.Ammo_Item_ID");
 
     
     int[] ammoReading = grabDualAmmo(item, parentNode);
     if (leftClick) {
       if (ammoReading[0] > 0) {
         ammoAmount = ammoReading[0] - 1;
         this.csminion.replaceBrackets(item, ammoAmount + " | " + ammoReading[1], parentNode);
       } else {
         playSoundEffects(player, parentNode, ".Reload.Dual_Wield.Sounds_Shoot_With_No_Ammo", false, null);
         return false;
       }
     
     } else if (ammoReading[1] > 0) {
       ammoAmount = ammoReading[1] - 1;
       this.csminion.replaceBrackets(item, ammoReading[0] + " | " + ammoAmount, parentNode);
     } else {
       playSoundEffects(player, parentNode, ".Reload.Dual_Wield.Sounds_Shoot_With_No_Ammo", false, null);
       return false;
     } 
 
 
     
     if (ammoAmount <= 0) playSoundEffects(player, parentNode, ".Reload.Sounds_Out_Of_Ammo", false, null);
 
     
     if (ammoEnable && takeAmmo) this.csminion.removeNamedItem(player, ammoInfo, 1, parentNode, false);
 
     
     if (detectedAmmo - 1 == 0) reloadAnimation(player, parentNode);
     
     return true;
   }
   
   public int[] grabDualAmmo(ItemStack item, String parentNode) {
     try {
       int leftGun, rightGun;
       String strInBracks = this.csminion.extractReading(item.getItemMeta().getDisplayName());
       String[] dualAmmo = strInBracks.split(" ");
 
 
 
       
       if (dualAmmo.length != 3) {
         this.csminion.resetItemName(item, parentNode);
         strInBracks = this.csminion.extractReading(item.getItemMeta().getDisplayName());
         dualAmmo = strInBracks.split(" ");
       } 
       
       if (dualAmmo[0].equals(String.valueOf('×'))) {
         leftGun = 1;
       } else {
         leftGun = Integer.valueOf(dualAmmo[0]).intValue();
       } 
       
       if (dualAmmo[2].equals(String.valueOf('×'))) {
         rightGun = 1;
       } else {
         rightGun = Integer.valueOf(dualAmmo[2]).intValue();
       } 
       
       return new int[] { leftGun, rightGun };
     } catch (NumberFormatException ex) {
       return new int[2];
     } 
   }
 
   
   @EventHandler
   public void explosiveTipCrossbow(EntityShootBowEvent event) {
     if (event.getEntity() instanceof Player shooter && event.getForce() == 1.0F) {

               String parentNode = returnParentNode(shooter);
       if (parentNode == null)
         return;  event.setCancelled(true);
       
       if (!regionAndPermCheck(shooter, parentNode, false))
         return;  this.csminion.weaponInteraction(shooter, parentNode, false);
     } 
   }
 
   
   public String isSkipNameItem(ItemStack item) {
     String itemInfo = item.getType() + "-" + item.getDurability();
     return this.convIDs.get(itemInfo);
   }
 
   
   public String convItem(ItemStack item) {
     String retNode = isSkipNameItem(item);
     
     if (retNode == null && item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
       Map<Enchantment, Integer> enchList = item.getEnchantments();
 
 
 
       
       for (String parentNode : this.enchlist.keySet()) {
         String[] enchInfo = this.enchlist.get(parentNode);
         Enchantment givenEnch = Enchantment.getByName(enchInfo[0]);
         int enchLevel = Integer.valueOf(enchInfo[1]).intValue();
         
         ItemStack comp = this.csminion.parseItemStack(getString(parentNode + ".Item_Information.Item_Type"));
         boolean equal = (comp != null && comp.getType() == item.getType() && (comp.getDurability() == item.getDurability() || hasDurab(parentNode)));
         
         if (equal && enchList.containsKey(givenEnch) && enchList.get(givenEnch).intValue() == enchLevel) {
           retNode = parentNode;
           
           break;
         } 
       } 
     } 
     return retNode;
   }
 
   
   public String toDisplayForm(String itemName) {
     ItemMeta meta = (new ItemStack(Material.DIRT)).getItemMeta();
     meta.setDisplayName(itemName);
     return meta.getDisplayName();
   }
 
   
   public String getPureName(String itemName) {
     int nameLength = itemName.length() - 1;
     int lastIndex = itemName.lastIndexOf("§");
     if (lastIndex != -1 && lastIndex + 2 <= nameLength) {
       itemName = itemName.substring(0, lastIndex + 2);
     }
     return toDisplayForm(itemName);
   }
 
   
   public String returnParentNode(Player player) {
     String retNode = null;
     ItemStack item = player.getItemInHand();
     if (item == null) return null;
     
     if (itemIsSafe(item)) {
 
       
       String parentNode = isSkipNameItem(item);
 
       
       if (parentNode == null) {
         parentNode = this.parentlist.get(getPureName(item.getItemMeta().getDisplayName()));
       }
       
       if (parentNode != null) {
         if (player.getItemInHand().getItemMeta().getDisplayName().contains(String.valueOf('▶'))) {
           String attachInfo = getAttachment(parentNode, item)[1];
           retNode = attachInfo;
         } else {
           retNode = parentNode;
         } 
       }
     } else {
       String convNode = convItem(item);
       if (convNode != null && regionAndPermCheck(player, convNode, true)) {
         this.csminion.removeEnchantments(item);
         ItemStack weapon = this.csminion.vendingMachine(convNode);
         weapon.setAmount(player.getItemInHand().getAmount());
         player.setItemInHand(weapon);
       } 
     } 
     
     return retNode;
   }
 
   
   public String[] itemParentNode(ItemStack item, Player player) {
     String[] retVal = null;
     
     if (itemIsSafe(item)) {
 
       
       String parentNode = isSkipNameItem(item);
 
       
       if (parentNode == null) {
         parentNode = this.parentlist.get(getPureName(item.getItemMeta().getDisplayName()));
       }
       
       if (parentNode != null) {
         if (item.getItemMeta().getDisplayName().contains(String.valueOf('▶'))) {
           String attachInfo = getAttachment(parentNode, item)[1];
           retVal = new String[] { attachInfo, "false" };
         } else {
           retVal = new String[] { parentNode, "false" };
         } 
       }
     } else {
       String convNode = convItem(item);
       if (convNode != null && player != null && regionAndPermCheck(player, convNode, true)) {
         this.csminion.removeEnchantments(item);
         retVal = new String[] { convNode, "true" };
       } 
     } 
     
     return retVal;
   }
 
   
   @EventHandler
   public void onCraft(CraftItemEvent event) {
     for (String parent_node : this.parentlist.values()) {
       if (getBoolean(parent_node + ".Crafting.Enable")) {
         ItemStack weapon = this.csminion.vendingMachine(parent_node);
         if (event.getRecipe().getResult().isSimilar(weapon)) {
           
           Player crafter = (Player)event.getWhoClicked();
           if (event.getWhoClicked() instanceof Player && !crafter.hasPermission("crackshot.craft." + parent_node) && !crafter.hasPermission("crackshot.craft.all")) {
             event.setCancelled(true);
             CSMessages.sendMessage(crafter, this.heading, CSMessages.Message.NP_WEAPON_CRAFT.getMessage());
           } 
           break;
         } 
       } 
     } 
   }
 
   
   void printM(String msg) {
     System.out.println("[CrackShot] " + msg);
   }
   
   public double getDouble(String nodes) {
     Double result = dubs.get(nodes);
     return (result != null) ? result.doubleValue() : 0.0D;
   }
   
   public boolean getBoolean(String nodes) {
     Boolean result = bools.get(nodes);
     return result != null && result.booleanValue();
   }
   
   public int getInt(String nodes) {
     Integer result = ints.get(nodes);
     return (result != null) ? result.intValue() : 0;
   }
   
   public String getString(String nodes) {
     String result = strings.get(nodes);
     return result;
   }
   
   public boolean hasDurab(String nodes) {
     Boolean result = this.morobust.get(nodes);
     return result != null && result.booleanValue();
   }
   public boolean regionAndPermCheck(Player shooter, String parentNode, boolean noMsg) {
     byte b;
     int i;
     String[] arrayOfString;
     for (i = (arrayOfString = this.disWorlds).length, b = 0; b < i; ) { String worName = arrayOfString[b];
       if (worName == null)
         break;  World world = Bukkit.getWorld(worName);
       if (world == shooter.getWorld()) return false;
       
       b++; }
     
     if (!shooter.hasPermission("crackshot.use." + parentNode) && !shooter.hasPermission("crackshot.use.all")) {
       if (!noMsg) {
         CSMessages.sendMessage(shooter, this.heading, CSMessages.Message.NP_WEAPON_USE.getMessage());
       }
       return false;
     } 
 
     
     if (!shooter.hasPermission("crackshot.bypass." + parentNode) && !shooter.hasPermission("crackshot.bypass.all") && 
       !this.csminion.regionCheck(shooter, parentNode)) {
       if (!noMsg && getString(parentNode + ".Region_Check.Message_Of_Denial") != null) {
         shooter.sendMessage(getString(parentNode + ".Region_Check.Message_Of_Denial"));
       }
       return false;
     } 
     
     return true;
   }
 
   
   @EventHandler
   public void onEggSplat(PlayerEggThrowEvent event) {
     if (event.getEgg().hasMetadata("CS_Hardboiled")) event.setHatching(false);
   
   }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   
   public void launchGrenade(final Player player, final String parent_node, int delay, Vector vel, Location splitLoc, final int cTimes) {
     boolean cEnable = getBoolean(parent_node + ".Cluster_Bombs.Enable");
     int cOfficialTimes = getInt(parent_node + ".Cluster_Bombs.Number_Of_Splits");
     String itemType = getString(parent_node + ".Shooting.Projectile_Subtype");
     String nodeName = "Projectile_Subtype:";
     
     if (cEnable && cTimes != 0) {
       nodeName = "Bomblet_Type:";
       itemType = getString(parent_node + ".Cluster_Bombs.Bomblet_Type");
     } 
     
     if (itemType == null) {
       player.sendMessage(this.heading + "The '" + nodeName + "' node of '" + parent_node + "' has not been defined.");
       
       return;
     } 
     ItemStack item = this.csminion.parseItemStack(itemType);
     if (item == null) {
       player.sendMessage(this.heading + "The '" + nodeName + "' node of '" + parent_node + "' has an incorrect value.");
       
       return;
     } 
     
     Location loc = player.getEyeLocation();
     if (splitLoc != null) loc = splitLoc;
     
     final Item grenade = player.getWorld().dropItem(loc, item);
     grenade.setVelocity(vel);
     grenade.setPickupDelay(delay + 20);
 
     
     ItemStack grenStack = grenade.getItemStack();
     this.csminion.setItemName(grenStack, "૮" + grenade.getUniqueId());
     grenade.setItemStack(grenStack);
 
     
     callShootEvent(player, grenade, parent_node);
     
     final boolean airstrike = getBoolean(parent_node + ".Airstrikes.Enable");
     int cDelay = getInt(parent_node + ".Cluster_Bombs.Delay_Before_Split");
     int cDelayDiff = getInt(parent_node + ".Cluster_Bombs.Detonation_Delay_Variation");
 
     
     if (cEnable && !airstrike && cTimes < cOfficialTimes) {
       if (cTimes == 0) playSoundEffects(grenade, parent_node, ".Shooting.Sounds_Projectile", false, null);
       delay = cDelay;
     } else if (cEnable) {
       if (cDelay != 0 && cDelayDiff != 0) {
         Random r = new Random();
         delay += r.nextInt(cDelayDiff) - r.nextInt(cDelayDiff);
       } 
     } else {
       playSoundEffects(grenade, parent_node, ".Shooting.Sounds_Projectile", false, null);
     } 
     
     Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable()
         {
           public void run() {
             boolean zapEnable = CSDirector.this.getBoolean(parent_node + ".Lightning.Enable");
             boolean zapNoDam = CSDirector.this.getBoolean(parent_node + ".Lightning.No_Damage");
             if (!airstrike) {
               if (zapEnable) {
                 CSDirector.this.csminion.projectileLightning(grenade.getLocation(), zapNoDam);
               }
               CSDirector.this.projectileExplosion(grenade, parent_node, true, player, true, false, null, null, false, cTimes);
             } else {
               CSDirector.this.csminion.callAirstrike(grenade, parent_node, player);
             } 
             grenade.remove();
           }
         }, (long) delay);
   }
 
   
   @EventHandler
   public void onAnyDamage(EntityDamageEvent event) {
     if (event.getEntity() instanceof Player shooter && event.getCause() == EntityDamageEvent.DamageCause.FALL) {

               ItemStack heldItem = shooter.getItemInHand();
       if (heldItem != null && itemIsSafe(heldItem)) {
         String parentNode = returnParentNode(shooter);
         if (parentNode == null)
           return;  if (getBoolean(parentNode + ".Abilities.No_Fall_Damage")) event.setCancelled(true);
       
       } 
     } 
   }
   
   public void delayedReload(final Player player, final String parentNode, long... delay) {
     int taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
         {
           public void run() {
             CSDirector.this.reloadAnimation(player, parentNode);
             CSDirector.this.delayed_reload_IDs.remove(player.getName());
           }
         }, (delay.length == 0) ? 1L : delay[0]);
    
     
     this.delayed_reload_IDs.put(player.getName(), Integer.valueOf(taskID));
   }
 
 
   
   @EventHandler
   public void onPickUp(PlayerPickupItemEvent event) {
     if (this.csminion.fastenSeatbelts(event.getItem()) != null) {
       this.csminion.reseatTag(event.getItem());
       event.setCancelled(true);
       if (!(event.getItem().getVehicle() instanceof org.bukkit.entity.Minecart)) event.getItem().remove();
     
     } else {
       ItemStack item = event.getItem().getItemStack();
       if (itemIsSafe(item)) {
         String fullName = item.getItemMeta().getDisplayName();
         if (fullName.contains("૮")) {
           event.setCancelled(true);
           event.getItem().remove();
         } else {
           String itemName = getPureName(fullName);
           if (this.boobs.containsKey(itemName)) {
             String parentNode = this.boobs.get(itemName);
             
             if (!this.csminion.getBoobean(2, parentNode))
               return; 
             Player picker = event.getPlayer();
             String detectedName = this.csminion.extractReading(fullName);
             if (detectedName.equals("?"))
               return; 
             Player planter = Bukkit.getServer().getPlayer(detectedName);
             if (planter == picker)
               return;  event.getItem().setPickupDelay(60);
             
             slapAndReaction(picker, planter, event.getItem().getLocation().getBlock(), parentNode, null, null, detectedName, event.getItem());
             event.setCancelled(true);
           } 
         } 
       } 
     } 
   }
 
   
   @EventHandler
   public void onItemSpawn(ItemSpawnEvent event) {
     ItemStack item = event.getEntity().getItemStack();
     
     if (MaterialManager.isSkullItem(item.getType()) && item.hasItemMeta()) {
       SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
       
       if (skullMeta != null && skullMeta.hasOwner() && skullMeta.getOwner().contains("،")) {
         event.setCancelled(true);
         event.getEntity().remove();
       } 
     } 
   }
 
   
   @EventHandler
   public void onEntityInteract(PlayerInteractEntityEvent event) {
     Entity ent = event.getRightClicked();
     if (ent instanceof org.bukkit.entity.Minecart) {
       this.csminion.reseatTag((Vehicle)event.getRightClicked());
       if (ent.getPassenger() instanceof Item) {
         event.setCancelled(true);
       }
     } else if (ent instanceof org.bukkit.entity.Villager || ent instanceof org.bukkit.entity.Horse) {
       Player player = event.getPlayer();
       ItemStack heldItem = player.getItemInHand();
       String parentNode = returnParentNode(player);
       if (parentNode != null && getBoolean(parentNode + ".Shooting.Cancel_Right_Click_Interactions")) {
         OnPlayerInteract(new PlayerInteractEvent(player, Action.RIGHT_CLICK_AIR, heldItem, null, null));
         event.setCancelled(true);
       } 
     } 
   }
 
 
   
   @EventHandler
   public void tagDespawn(ItemDespawnEvent event) {
     if (this.csminion.fastenSeatbelts(event.getEntity()) != null) event.setCancelled(true);
 
     
     ItemStack item = event.getEntity().getItemStack();
     if (itemIsSafe(item)) {
       String itemName = getPureName(item.getItemMeta().getDisplayName());
       if (itemName.contains("૮૮")) {
         event.setCancelled(true);
       } else if (this.boobs.containsKey(itemName)) {
         String parentNode = this.boobs.get(itemName);
         if (this.csminion.getBoobean(5, parentNode)) {
           event.setCancelled(true);
         }
       } 
     } 
   }
 
   
   @EventHandler
   public void onMobShotgun(VehicleEnterEvent event) {
     if (event.getVehicle() instanceof org.bukkit.entity.Minecart) {
       this.csminion.reseatTag(event.getVehicle());
       if (event.getVehicle().getPassenger() instanceof Item) event.setCancelled(true);
     
     } 
   }
   
   @EventHandler
   public void onBoatMineShoot(VehicleDamageEvent event) {
     if (!(event.getVehicle() instanceof org.bukkit.entity.Minecart))
       return;  this.csminion.reseatTag(event.getVehicle());
     if (event.getVehicle().getPassenger() instanceof Item psngr) {
       Entity attacker = event.getAttacker();

               String[] seagullInfo = this.csminion.fastenSeatbelts(psngr);
       if (seagullInfo == null)
         return; 
       event.setCancelled(true);
       Player fisherman = Bukkit.getServer().getPlayer(seagullInfo[1]);
       
       if (attacker instanceof Player player) {

                     if (player.getName().equals(seagullInfo[1])) {
           this.csminion.mineAction(event.getVehicle(), seagullInfo, fisherman, true, null, attacker);
         } else {
           this.csminion.callAndResponse(player, fisherman, event.getVehicle(), seagullInfo, true);
         } 
       } else {
         this.csminion.mineAction(event.getVehicle(), seagullInfo, fisherman, true, null, attacker);
       } 
     } 
   }
 
   
   public void deployMine(Player player, String parent_node, Location loc) {
     String nodeInfo = getString(parent_node + ".Explosive_Devices.Device_Info");
     String[] deviceInfo = (nodeInfo == null) ? null : nodeInfo.split(",");
     ItemStack fuseItem = this.csminion.parseItemStack(deviceInfo[0]);
     Location spawnLoc = (loc == null) ? player.getLocation().add(0.0D, 0.75D, 0.0D) : loc;
     
     if (fuseItem == null) {
       player.sendMessage(this.heading + "No valid item-ID for 'Device_Info' of the weapon '" + parent_node + "' has been provided.");
       
       return;
     } 
     EntityType cartType = EntityType.MINECART;
     if (deviceInfo.length == 2) {
       try {
         cartType = EntityType.valueOf(deviceInfo[1].toUpperCase());
       } catch (IllegalArgumentException ex) {
         player.sendMessage(this.heading + "The 'Device_Info' node of the weapon '" + parent_node + "' contains '" + deviceInfo[1] + "', which is not a valid minecart type.");
       } 
     }
     
     Entity mine = player.getWorld().spawnEntity(spawnLoc, cartType);
     ItemMeta metaPsngr = fuseItem.getItemMeta();
     metaPsngr.setDisplayName("§cS3AGULLL~" + player.getName() + "~" + parent_node + "~" + mine.getUniqueId());
     fuseItem.setItemMeta(metaPsngr);
     
     Item item = player.getWorld().dropItem(spawnLoc, fuseItem);
     mine.setPassenger(item);
 
     
     WeaponPlaceMineEvent event = new WeaponPlaceMineEvent(player, mine, parent_node);
     getServer().getPluginManager().callEvent(event);
   }
 
   
   @EventHandler
   public void airstrikeKaboom(EntityChangeBlockEvent event) {
     if (event.getEntity().hasMetadata("CS_strike")) {
       Entity bomb = event.getEntity();
       String info = bomb.getMetadata("CS_strike").get(0).asString();
       String[] parsedInfo = info.split("~");
       Player player = Bukkit.getServer().getPlayer(parsedInfo[1]);
       
       projectileExplosion(bomb, parsedInfo[0], false, player, true, false, null, null, false, 0);
       bomb.remove();
       event.setCancelled(true);
     } else if (event.getEntity().hasMetadata("CS_shrapnel")) {
       event.getEntity().remove();
       event.setCancelled(true);
     } 
   }
 
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void onC4Place(BlockPlaceEvent event) {
     final Player placer = event.getPlayer();
     if (event.getItemInHand() == null)
       return;  final String[] parent_node = itemParentNode(event.getItemInHand(), placer);
     if (parent_node == null) {
       return;
     }
     if (!regionAndPermCheck(placer, parent_node[0], false) || !getBoolean(parent_node[0] + ".Explosive_Devices.Enable")) {
       event.setCancelled(true);
       
       return;
     } 
     
     placer.updateInventory();
     
     String type = getString(parent_node[0] + ".Explosive_Devices.Device_Type");
     if (type == null || !type.equalsIgnoreCase("remote")) {
       return;
     }
     if (itemIsSafe(event.getItemInHand()) && event.getItemInHand().getItemMeta().getDisplayName().contains("«0»")) {
       event.setCancelled(true);
       
       return;
     } 
     boolean placeAnywhere = getBoolean(parent_node[0] + ".Explosive_Devices.Remote_Bypass_Regions");
     boolean allowed = (!event.isCancelled() && event.canBuild());
     final Block block = event.getBlockPlaced();
     event.setCancelled(true);
     
     if (allowed || placeAnywhere) {
       Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
           {
             public void run() {
               CSDirector.this.setupC4(placer, block, parent_node);
             }
           });
     }
   }
   
   public void setupC4(Player placer, Block block, String[] parent_node) {
     Material mat = MaterialManager.getSkullBlock();
     if (mat == null) {
       throw new UnsupportedOperationException();
     }
     block.setType(mat);
 
 
     
     if (MaterialManager.pre113) {
       try {
         Method method = block.getClass().getMethod("setData", byte.class);
         method.invoke(block, Byte.valueOf((byte)1));
       } catch (Exception e) {
         e.printStackTrace();
         
         return;
       } 
     }
     BlockState state = block.getState();
     if (state instanceof Skull) {
       Skull skull; int capacity = 0;
       String uniqueID = null;
       
       try { skull = (Skull)state; } catch (ClassCastException ex) { return; }
        if (MaterialManager.pre113) {
         skull.setSkullType(SkullType.PLAYER);
       }
       
       String[] refinedOre = this.csminion.returnRefinedOre(placer, parent_node[0]);
       if (refinedOre != null) {
         capacity = Integer.valueOf(refinedOre[0]).intValue();
         uniqueID = refinedOre[1];
       } 
       
       String storedOwner = placer.getName();
       if (storedOwner.length() > 13) storedOwner = storedOwner.substring(0, 12) + 'ظ';
       skull.setOwner(uniqueID + "،" + storedOwner);
       if (MaterialManager.pre113) {
         skull.setRotation(getBlockDirection(placer.getLocation().getYaw()));
       }
       
       skull.update(true);
       
       String world = placer.getWorld().getName();
       String x = String.valueOf(block.getLocation().getBlockX());
       String y = String.valueOf(block.getLocation().getBlockY());
       String z = String.valueOf(block.getLocation().getBlockZ());
 
 
       
       Map<String, String> placedHeads = this.c4_backup.get(storedOwner);
 
       
       if (placedHeads == null) {
         placedHeads = new HashMap<String, String>();
         this.c4_backup.put(storedOwner, placedHeads);
       } 
 
       
       placedHeads.put(world + "," + x + "," + y + "," + z, uniqueID);
 
       
       ItemStack detonator = placer.getItemInHand();
       
       boolean ammoEnable = getBoolean(parent_node[0] + ".Ammo.Enable");
       String ammoInfo = getString(parent_node[0] + ".Ammo.Ammo_Item_ID");
       boolean takeAmmo = getBoolean(parent_node[0] + ".Ammo.Take_Ammo_Per_Shot");
       
       String bracketInfo = this.csminion.extractReading(detonator.getItemMeta().getDisplayName());
       int detectedAmmo = 0; 
       try { detectedAmmo = Integer.valueOf(bracketInfo).intValue(); } catch (NumberFormatException numberFormatException) {}
       if (detectedAmmo > 0) {
         if (ammoEnable && takeAmmo) {
           if (!this.csminion.containsItemStack(placer, ammoInfo, 1, parent_node[0])) {
             playSoundEffects(placer, parent_node[0], ".Ammo.Sounds_Shoot_With_No_Ammo", false, null);
             block.setType(Material.AIR);
             return;
           } 
           this.csminion.removeNamedItem(placer, ammoInfo, 1, parent_node[0], false);
         } 
         this.csminion.replaceBrackets(detonator, String.valueOf(detectedAmmo - 1), parent_node[0]);
       } else {
         block.setType(Material.AIR);
         
         return;
       } 
       
       if (detonator.getItemMeta().hasLore()) {
         List<String> lore = detonator.getItemMeta().getLore();
         String lastLine = lore.get(lore.size() - 1);
         if (lastLine.contains(String.valueOf('᎐'))) {
           String numInBrack = lastLine.split("\\[")[1].split("\\]")[0];
           int lastNumber = Integer.valueOf(numInBrack).intValue();
           if (lastNumber >= capacity) {
             block.setType(Material.AIR);
             return;
           } 
           lore.add("§e§l[" + (lastNumber + 1) + "]§r§e " + world.toUpperCase() + '᎐' + " " + x + ", " + y + ", " + z);
         } else {
           lore.add("§e§l[1]§r§e " + world.toUpperCase() + '᎐' + " " + x + ", " + y + ", " + z);
         } 
         
         ItemMeta detmeta = detonator.getItemMeta();
         detmeta.setLore(lore);
         detonator.setItemMeta(detmeta);
         placer.getInventory().setItemInHand(detonator);
         playSoundEffects(placer, parent_node[0], ".Explosive_Devices.Sounds_Deploy", false, null);
       } 
     } 
   }
 
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void breakC4(BlockBreakEvent event) {
     if (MaterialManager.isSkullBlock(event.getBlock())) {
       BlockState state = event.getBlock().getState();
       if (state instanceof Skull) {
         Skull skull; 
         try { skull = (Skull)state; } catch (ClassCastException ex) { return; }
          String ownerOre = skull.getOwner();
         if (ownerOre != null && ownerOre.contains("،")) {
           String[] refinedOwner = ownerOre.split("،");
           Block block = event.getBlock();
           Player breaker = event.getPlayer();
 
           
           Player placer = null;
           List<Player> candidates = Bukkit.matchPlayer(refinedOwner[1].replace(String.valueOf('ظ'), ""));
           if (candidates != null && !candidates.isEmpty()) placer = candidates.get(0);
           
           String world = block.getWorld().getName();
           String x = String.valueOf(block.getLocation().getBlockX());
           String y = String.valueOf(block.getLocation().getBlockY());
           String z = String.valueOf(block.getLocation().getBlockZ());
           String[] itemInfo = { "-", world, x, y, z };
           
           for (String exploDevID : this.rdelist.keySet()) {
             if (exploDevID.equals(refinedOwner[0])) {
               String parent_node = this.rdelist.get(exploDevID);
               boolean bypassRegions = getBoolean(parent_node + ".Explosive_Devices.Remote_Bypass_Regions");
               
               if (!event.isCancelled() || bypassRegions) {
                 if (breaker != placer) {
                   this.csminion.callAndResponse(breaker, placer, null, itemInfo, false);
                 } else {
                   
                   String msg = getString(parent_node + ".Explosive_Devices.Message_Disarm");
                   if (msg != null) breaker.sendMessage(msg); 
                   block.removeMetadata("CS_transformers", this);
                   block.setType(Material.AIR);
                 } 
                 event.setCancelled(true);
               } 
               break;
             } 
           } 
         } 
       } 
     } 
   }
 
 
   
   @EventHandler
   public void liquidContact(BlockFromToEvent event) {
     if (MaterialManager.isSkullBlock(event.getToBlock())) {
       BlockState state = event.getToBlock().getState();
       if (state instanceof Skull) {
         Skull skull; 
         try { skull = (Skull)state; } catch (ClassCastException ex) { return; }
          if (skull.getOwner() != null && skull.getOwner().contains("،")) event.setCancelled(true);
       
       } 
     } 
   }
 
   
   public Vector determinePosition(Player player, boolean dualWield, boolean leftClick) {
     int leftOrRight = 90;
     if (dualWield && leftClick) leftOrRight = -90;
     
     double playerYaw = (player.getLocation().getYaw() + 90.0F + leftOrRight) * Math.PI / 180.0D;
     double x = Math.cos(playerYaw);
     double y = Math.sin(playerYaw);
     Vector vector = new Vector(x, 0.0D, y);
     
     return vector;
   }
   
   public boolean itemIsSafe(ItemStack item) {
     return (item.hasItemMeta() && item.getItemMeta().getDisplayName() != null);
   }
   
   public float findNormal(float yaw) {
     for (; yaw <= -180.0F; yaw += 360.0F);
     for (; yaw > 180.0F; yaw -= 360.0F);
     return yaw;
   }
   
   public BlockFace getBlockDirection(float yaw) {
     yaw = findNormal(yaw);
     switch ((int)yaw) { case 0:
         return BlockFace.NORTH;
       case 90: return BlockFace.EAST;
       case 180: return BlockFace.SOUTH;
       case 270: return BlockFace.WEST; }
     
     if (yaw >= -45.0F && yaw < 45.0F)
       return BlockFace.NORTH; 
     if (yaw >= 45.0F && yaw < 135.0F)
       return BlockFace.EAST; 
     if (yaw >= -135.0F && yaw < -45.0F) {
       return BlockFace.WEST;
     }
     return BlockFace.SOUTH;
   }
 
 
   
   @EventHandler
   public void trapCard(InventoryOpenEvent event) {
     if (event.getInventory().getType() != InventoryType.CHEST || !(event.getPlayer() instanceof Player opener) || this.boobs.isEmpty())
       return; 

         Inventory chest = event.getInventory();
     Block block = null;
     
     if (chest.getHolder() instanceof Chest chestHolder) {

               if (chestHolder != null) {
         block = chestHolder.getBlock();
       }
     } else if (chest.getHolder() instanceof DoubleChest) {
       block = ((DoubleChest)chest.getHolder()).getLocation().getBlock();
     } 
 
     
     if (block == null)
       return; 
     if (block.hasMetadata("CS_btrap")) {
       event.setCancelled(true);
       
       return;
     } 
     
     ItemStack[] contents = chest.getContents(); byte b; int i; ItemStack[] arrayOfItemStack1;
     for (i = (arrayOfItemStack1 = contents).length, b = 0; b < i; ) { ItemStack susItem = arrayOfItemStack1[b];
       if (susItem != null && itemIsSafe(susItem)) {
         String weaponTitle = getPureName(susItem.getItemMeta().getDisplayName());
         if (this.boobs.containsKey(weaponTitle)) {
           String parentNode = this.boobs.get(weaponTitle);
           if (!this.csminion.getBoobean(1, parentNode))
             return; 
           String ammoReading = this.csminion.extractReading(susItem.getItemMeta().getDisplayName());
           if (ammoReading.equals("?"))
             break;  Player planter = Bukkit.getServer().getPlayer(ammoReading);
           if (planter == event.getPlayer())
             break; 
           if (!this.csminion.getBoobean(4, parentNode)) susItem.setAmount(susItem.getAmount() - 1); 
           slapAndReaction(opener, planter, block, parentNode, chest, contents, ammoReading, null);
           return;
         } 
       } 
       b++; }
   
   }
   
   public void slapAndReaction(final Player opener, final Player planter, final Block block, final String parent_node, final Inventory chest, final ItemStack[] content, final String planterName, final Item picked) {
     if (opener.hasMetadata("CS_trigDelay"))
       return; 
     if (planter == null) {
       activateTrapCard(opener, planter, block, parent_node, chest, content, planterName, picked);
       
       return;
     } 
     opener.setMetadata("CS_trigDelay", new FixedMetadataValue(this, Boolean.valueOf(false)));
     this.csminion.tempVars(opener, "CS_trigDelay", Long.valueOf(200L));
     
     opener.setMetadata("CS_singed", new FixedMetadataValue(this, Boolean.valueOf(false)));
     this.csminion.illegalSlap(planter, opener, 0);
     
     Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
         {
           public void run() {
             if (opener.hasMetadata("CS_singed") && opener.getMetadata("CS_singed").get(0).asBoolean()) {
               opener.removeMetadata("CS_singed", CSDirector.this.plugin);
               opener.removeMetadata("CS_trigDelay", CSDirector.this.plugin);
               CSDirector.this.activateTrapCard(opener, planter, block, parent_node, chest, content, planterName, picked);
             } 
           }
         }, 1L);
       }
 
   
   public void activateTrapCard(Player opener, Player planter, Block block, String parent_node, Inventory chest, ItemStack[] content, String planterName, Item picked) {
     boolean unlimited = this.csminion.getBoobean(4, parent_node);
     
     if (planter != null) {
       sendPlayerMessage(planter, parent_node, ".Explosive_Devices.Message_Trigger_Placer", planterName, opener.getName(), "<flight>", "<damage>");
       playSoundEffects(planter, parent_node, ".Explosive_Devices.Sounds_Alert_Placer", false, null);
     } 
     
     if (picked == null) {
       projectileExplosion(null, parent_node, false, planter, false, true, null, block, true, 0);
       block.setMetadata("CS_btrap", new FixedMetadataValue(this, Boolean.valueOf(false)));
       if (!unlimited) chest.setContents(content); 
     } else {
       projectileExplosion(null, parent_node, false, planter, false, true, null, block.getRelative(BlockFace.DOWN), true, 0);
       if (!unlimited) picked.remove();
     
     } 
     sendPlayerMessage(opener, parent_node, ".Explosive_Devices.Message_Trigger_Victim", planterName, opener.getName(), "<flight>", "<damage>");
     playSoundEffects(null, parent_node, ".Explosive_Devices.Sounds_Trigger", false, block.getLocation().add(0.5D, 0.5D, 0.5D));
   }
 
   
   @EventHandler
   public void onHopperGulp(InventoryPickupItemEvent event) {
     ItemStack item = event.getItem().getItemStack();
     if (itemIsSafe(item) && item.getItemMeta().getDisplayName().contains("૮")) {
       event.getItem().remove();
       event.setCancelled(true);
     } 
   }
 
   
   @EventHandler
   public void onTrapDispense(BlockDispenseEvent event) {
     Block block = event.getBlock();
     if (block.getType() != Material.DISPENSER)
       return;  MaterialData data = block.getState().getData();
     Dispenser dispenser = (Dispenser)data;
     BlockFace face = dispenser.getFacing();
     if (this.csminion.boobyAction(block.getRelative(face).getRelative(BlockFace.DOWN), null, event.getItem())) event.setCancelled(true);
   
   }
 
   
   @EventHandler
   public void onPressurePlate(EntityInteractEvent event) {
     if (MaterialManager.isPressurePlate(event.getBlock()) && event.getEntity() instanceof LivingEntity) {
       
       List<Entity> l = event.getEntity().getNearbyEntities(4.0D, 4.0D, 4.0D);
       for (Entity e : l) { if (e instanceof ItemFrame) this.csminion.boobyAction(event.getBlock(), event.getEntity(), ((ItemFrame)e).getItem());  }
     
     } 
   }
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void onSplash(PotionSplashEvent event) {
     ThrownPotion splashPot = event.getEntity();
     if (splashPot.hasMetadata("projParentNode")) {
       Entity shooter = (Entity)splashPot.getShooter();
       if (shooter != null && shooter instanceof Player) {
         PermissionAttachment attachment = shooter.addAttachment(this);
         attachment.setPermission("nocheatplus", true);
         attachment.setPermission("anticheat.check.exempt", true);
         
         String parentNode = splashPot.getMetadata("projParentNode").get(0).asString();
         boolean enableExplode = getBoolean(parentNode + ".Explosions.Enable");
         boolean impactExplode = getBoolean(parentNode + ".Explosions.On_Impact_With_Anything");
         if (enableExplode && impactExplode) {
           projectileExplosion(null, parentNode, false, (Player)shooter, false, true, null, splashPot.getLocation().getBlock(), true, 0);
         }
         
         for (Entity ent : event.getAffectedEntities()) {
           if (ent != shooter && !ent.isDead() && !event.isCancelled()) {
             if (ent instanceof Player) {
               ent.setMetadata("CS_Energy", new FixedMetadataValue(this, parentNode));
               ((LivingEntity)ent).damage(0.0D, shooter); continue;
             } 
             dealDamage(shooter, (LivingEntity)ent, null, parentNode);
           } 
         } 
         
         event.setCancelled(true);
         
         shooter.removeAttachment(attachment);
       } 
     } 
   }
   
   public boolean validHotbar(Player shooter, String parent_node) {
     boolean retVal = true;
     String invCtrl = getString(parent_node + ".Item_Information.Inventory_Control");
     if (invCtrl != null) {
       PlayerInventory playerInventory = shooter.getInventory();
       String[] groupList = invCtrl.replaceAll(" ", "").split(","); byte b; int i; String[] arrayOfString1;
       for (i = (arrayOfString1 = groupList).length, b = 0; b < i; ) { String invGroup = arrayOfString1[b];
         int groupLimit = getInt(invGroup + ".Limit");
         int groupCount = 0;
         for (int j = 0; j < 9; j++) {
           ItemStack checkItem = playerInventory.getItem(j);
           if (checkItem != null && itemIsSafe(checkItem)) {
             String[] checkParent = itemParentNode(checkItem, shooter);
             if (checkParent != null) {
               String groupCheck = getString(checkParent[0] + ".Item_Information.Inventory_Control");
               if (groupCheck != null && groupCheck.contains(invGroup)) {
                 groupCount++;
               }
             } 
           } 
         } 
         if (groupCount > groupLimit) {
           sendPlayerMessage(shooter, invGroup, ".Message_Exceeded", "<shooter>", "<victim>", "<flight>", "<damage>");
           playSoundEffects(shooter, invGroup, ".Sounds_Exceeded", false, null);
           retVal = false;
         }  b++; }
     
     } 
     return retVal;
   }
 
   
   public boolean tossBomb(Player player, String parentNode, ItemStack heldItem, boolean rdeEnable) {
     boolean retVal = false;
     String type = getString(parentNode + ".Explosive_Devices.Device_Type");
     
     if (rdeEnable && type != null && type.equalsIgnoreCase("itembomb")) {
       
       int gunSlot = player.getInventory().getHeldItemSlot();
       String metaTag = parentNode + "shootDelay" + gunSlot;
       if (player.hasMetadata(metaTag)) return false; 
       player.setMetadata(metaTag, new FixedMetadataValue(this, Boolean.valueOf(true)));
       this.csminion.tempVars(player, metaTag, Long.valueOf(getInt(parentNode + ".Shooting.Delay_Between_Shots")));
       
       String preInfo = getString(parentNode + ".Explosive_Devices.Device_Info");
       String[] deviceInfo = (preInfo == null) ? null : preInfo.split(",");
       if (this.csminion.bombIsInvalid(player, deviceInfo, parentNode)) return true;
       
       double speed = Double.valueOf(deviceInfo[1]).doubleValue() * 0.1D;
       ItemStack bombType = this.csminion.parseItemStack(deviceInfo[2]);
 
       
       boolean ammoEnable = getBoolean(parentNode + ".Ammo.Enable");
       String ammoInfo = getString(parentNode + ".Ammo.Ammo_Item_ID");
       boolean takeAmmo = getBoolean(parentNode + ".Ammo.Take_Ammo_Per_Shot");
       int detectedAmmo = 0;
       String bracketInfo = this.csminion.extractReading(heldItem.getItemMeta().getDisplayName()); 
       try { detectedAmmo = Integer.valueOf(bracketInfo).intValue(); } catch (NumberFormatException numberFormatException) {}
       
       if (detectedAmmo > 0) {
         if (ammoEnable && takeAmmo) {
           if (!this.csminion.containsItemStack(player, ammoInfo, 1, parentNode)) {
             playSoundEffects(player, parentNode, ".Ammo.Sounds_Shoot_With_No_Ammo", false, null);
             return true;
           } 
           this.csminion.replaceBrackets(heldItem, String.valueOf(detectedAmmo - 1), parentNode);
           this.csminion.removeNamedItem(player, ammoInfo, 1, parentNode, false);
         } else {
           this.csminion.replaceBrackets(heldItem, String.valueOf(detectedAmmo - 1), parentNode);
         } 
       } else {
         return true;
       } 
 
       
       Item itemBomb = player.getWorld().dropItem(player.getEyeLocation(), bombType);
       itemBomb.setVelocity(player.getEyeLocation().getDirection().multiply(speed));
       itemBomb.setPickupDelay(24000);
       playSoundEffects(player, parentNode, ".Explosive_Devices.Sounds_Deploy", false, null);
 
       
       String playerName = player.getName();
       Map<String, ArrayDeque<Item>> subList = this.itembombs.get(playerName);
       if (subList == null) {
         subList = new HashMap<String, ArrayDeque<Item>>();
         this.itembombs.put(playerName, subList);
       } 
       ArrayDeque<Item> subSubList = subList.get(parentNode);
       if (subSubList == null) {
         subSubList = new ArrayDeque<Item>();
         subList.put(parentNode, subSubList);
       } 
       subSubList.add(itemBomb);
 
       
       if (subSubList.size() > Integer.valueOf(deviceInfo[0]).intValue()) {
         subSubList.removeFirst().remove();
       }
 
       
       ItemStack grenStack = itemBomb.getItemStack();
       this.csminion.setItemName(grenStack, playerName + "૮૮" + itemBomb.getUniqueId());
       itemBomb.setItemStack(grenStack);
 
       
       callShootEvent(player, itemBomb, parentNode);
       
       retVal = true;
     } 
     
     return retVal;
   }
 
 
   
   public void detonateC4(Player shooter, ItemStack item, String parentNode, String deviceType) {
     List<String> lore = null;
     String[] deviceInfo = null;
     String playerName = shooter.getName();
     boolean rdeFound = false, itemMode = false, noneToBoom = true;
 
     
     if (deviceType.equalsIgnoreCase("itembomb")) {
       String itemName = item.getItemMeta().getDisplayName();
       String preInfo = getString(parentNode + ".Explosive_Devices.Device_Info");
       deviceInfo = (preInfo == null) ? null : preInfo.split(",");
       if (this.csminion.bombIsInvalid(shooter, deviceInfo, parentNode) || itemName.contains("«" + deviceInfo[0] + "»")) {
         return;
       }
       rdeFound = true;
       itemMode = true;
       
       if (this.itembombs.containsKey(playerName)) {
         int delay = getInt(parentNode + ".Explosions.Explosion_Delay");
         ItemStack detItem = this.csminion.parseItemStack(deviceInfo[3]);
 
         
         ArrayDeque<Item> subSubList = this.itembombs.get(playerName).get(parentNode);
         if (subSubList != null) {
           while (!subSubList.isEmpty()) {
             noneToBoom = false;
             Item bomb = subSubList.removeFirst();
             playSoundEffects(bomb, parentNode, ".Explosive_Devices.Sounds_Trigger", false, null);
             projectileExplosion(bomb, parentNode, false, shooter, false, false, null, null, false, 0);
             detItem.setItemMeta(bomb.getItemStack().getItemMeta());
             bomb.setItemStack(detItem);
             prepareTermination(bomb, true, (long) delay);
           } 
           this.itembombs.get(playerName).remove(parentNode);
         } 
       } 
     } else if (item.getItemMeta().hasLore()) {
       
       lore = item.getItemMeta().getLore();
       Iterator<String> it = lore.iterator();
       while (it.hasNext()) {
         String line = it.next();
         if (line.contains(String.valueOf('᎐'))) {
           line = line.replace(" ", "");
           line = line.replace("]§r§e", "]§e");
           String[] itemInfo = line.split("]§e|\\᎐|,");
           this.csminion.detonateRDE(shooter, null, itemInfo, true);
           it.remove();
           rdeFound = true;
         } 
       } 
     } 
     
     if (rdeFound) {
       String capacity = "0";
       String[] refinedOre = itemMode ? deviceInfo : this.csminion.returnRefinedOre(shooter, parentNode);
       if (refinedOre != null) capacity = refinedOre[0]; 
       if (!itemMode || !noneToBoom) playSoundEffects(shooter, parentNode, ".Explosive_Devices.Sounds_Alert_Placer", false, null);
       
       if (!getBoolean(parentNode + ".Extras.One_Time_Use")) {
         this.csminion.replaceBrackets(item, capacity, parentNode);
       } else if (item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().contains("«0»")) {
         shooter.getInventory().setItemInHand(null);
         shooter.updateInventory();
         
         return;
       } 
       if (!itemMode) {
         ItemMeta detmeta = item.getItemMeta();
         detmeta.setLore(lore);
         item.setItemMeta(detmeta);
         shooter.getInventory().setItemInHand(item);
       } 
     } 
   }
 
   
   public void checkCorruption(ItemStack item, boolean isAttachment, boolean isDual) {
     String itemName = item.getItemMeta().getDisplayName();
     boolean noBracket = !itemName.contains("«");
     boolean noArrow = (isAttachment && !itemName.contains(String.valueOf('◀')) && !itemName.contains(String.valueOf('◁')));
 
     
     if (noBracket || noArrow) {
       Pattern pattern = Pattern.compile("-?\\d+");
       int startingPos = (isAttachment || isDual) ? getLastChar(itemName, ' ', 3) : itemName.lastIndexOf(" ");
       String[] bracketInfo = itemName.substring(startingPos + 1).split(" ");
       String[] ammo = { "", "", "" };
       
       if (isAttachment || isDual) {
         for (int i = 0; i < 3; i += 2) {
           Matcher matcher = pattern.matcher(bracketInfo[i]);
           ammo[i] = matcher.find() ? matcher.group() : String.valueOf('×');
         } 
         String splitter = isDual ? " | " : " ◀▷ ";
         itemName = itemName.substring(0, startingPos + 1) + "«" + ammo[0] + splitter + ammo[2] + "»";
       } else {
         Matcher matcher = pattern.matcher(bracketInfo[0]);
         ammo[0] = matcher.find() ? matcher.group() : String.valueOf('×');
         itemName = itemName.substring(0, startingPos + 1) + "«" + ammo[0] + "»";
       } 
       
       this.csminion.setItemName(item, itemName);
     } 
   }
 
   
   public static int getLastChar(String str, char c, int n) {
     int pos = str.lastIndexOf(c);
     while (n-- > 1 && pos != -1)
       pos = str.lastIndexOf(c, pos - 1); 
     return pos;
   }
 
   
   public int getReloadAmount(Player player, String weaponTitle, ItemStack item) {
     int capacity = getInt(weaponTitle + ".Reload.Reload_Amount");
     
     WeaponCapacityEvent event = new WeaponCapacityEvent(player, weaponTitle, item, capacity);
     getServer().getPluginManager().callEvent(event);
     
     return event.getCapacity();
   }
 
   
   public String[] getAttachment(String weaponTitle, ItemStack item) {
     String attachType = getString(weaponTitle + ".Item_Information.Attachments.Type");
 
     
     if (attachType == null || attachType.equalsIgnoreCase("accessory")) return new String[] { attachType };
 
     
     String attachment = getString(weaponTitle + ".Item_Information.Attachments.Info");
     WeaponAttachmentEvent event = new WeaponAttachmentEvent(weaponTitle, item, attachment);
     getServer().getPluginManager().callEvent(event);
 
     
     return new String[] { event.isCancelled() ? null : attachType, event.getAttachment() };
   }
 
   
   public boolean isDualWield(Player player, String weaponTitle, ItemStack item) {
     boolean dualWield = getBoolean(weaponTitle + ".Shooting.Dual_Wield");
     
     WeaponDualWieldEvent event = new WeaponDualWieldEvent(player, weaponTitle, item, dualWield);
     getServer().getPluginManager().callEvent(event);
     
     return event.isDualWield();
   }
 
   
   public boolean isDifferentItem(ItemStack item, String weaponTitle) {
     if (getBoolean(weaponTitle + ".Item_Information.Skip_Name_Check")) {
       String itemWeaponTitle = isSkipNameItem(item);
       return !(itemWeaponTitle != null && itemWeaponTitle.equals(weaponTitle));
     } 
     String itemName = getString(weaponTitle + ".Item_Information.Item_Name");
     String heldItemName = toDisplayForm(item.getItemMeta().getDisplayName());
     return !heldItemName.startsWith(itemName);
   }
 
 
   
   public boolean isAir(Material m) {
     return !(m != Material.AIR && !m.name().endsWith("_AIR"));
   }
 
   
   public boolean isValid(int tick, int fireRate) {

    switch (fireRate)
    {
        case 1 ->
        {
            return (tick % 4 == 1);
        }
        
        case 2 ->
        {
            
            tick %= 7;
            
            return !(tick != 1 && tick != 4);
        }
        
        case 3 ->
        {
            
            return (tick % 3 == 1);
        }
        
        case 4 ->
        {
            
            tick %= 5;
            
            return !(tick != 1 && tick != 3);
        }
        
        case 5 ->
        {
            
            tick %= 7;
            
            return !(tick != 1 && tick != 3 && tick != 5);
        }
        
        case 6 ->
        {
            
            return (tick % 2 == 1);
        }
        
        case 7 ->
        {
            
            return !(tick != 2 && tick % 2 != 1);
        }
        
        case 8 ->
        {
            
            tick %= 5;
            
            return !(tick != 1 && tick != 2 && tick != 4);
        }
        
        case 9 ->
        {
            
            tick %= 6;
            
            return (tick != 2 && tick != 0);
        }
        
        case 10 ->
        {
            
            return (tick % 3 != 0);
        }
        
        case 11 ->
        {
            
            return (tick % 4 != 0);
        }
        
        case 12 ->
        {
            
            return (tick % 5 != 0);
        }
        
        case 13 ->
        {
            
            return (tick % 6 != 0);
        }
        
        case 14 ->
        {
            
            return (tick % 10 != 0);
        }
        
        case 15 ->
        {
            
            return (tick != 20);
        }
        
        case 16 ->
        {
            
            return true;
        }
        
    }
     return true;
   }
 }