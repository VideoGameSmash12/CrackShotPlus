 package com.shampaggon.crackshot;
 
 import org.bukkit.Bukkit;
 import org.bukkit.Location;
 import org.bukkit.entity.EntityType;
 import org.bukkit.entity.Player;
 import org.bukkit.entity.Projectile;
 import org.bukkit.entity.TNTPrimed;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.metadata.FixedMetadataValue;
 
 public class CSUtility
 {
   private final CSDirector classOne = (CSDirector)Bukkit.getServer().getPluginManager().getPlugin("CrackShot");
   private final CSMinion classTwo = this.classOne.csminion;
 
 
   
   public boolean giveWeapon(Player receiver, String weaponTitle, int amount) {
     boolean success = false;
     if (receiver != null && receiver.getInventory().firstEmpty() != -1) {
       this.classTwo.getWeaponCommand(receiver, weaponTitle, false, String.valueOf(amount), true, true);
       success = true;
     } 
     return success;
   }
 
   
   public ItemStack generateWeapon(String weaponTitle) {
     return this.classTwo.vendingMachine(weaponTitle);
   }
 
   
   public void generateExplosion(Player player, Location loc, String weaponTitle) {
     this.classOne.projectileExplosion(null, weaponTitle, false, player, false, true, null, loc.getBlock(), true, 0);
   }
 
   
   public void spawnMine(Player player, Location loc, String weaponTitle) {
     this.classOne.deployMine(player, weaponTitle, loc);
   }
 
   
   public void setProjectile(Player player, Projectile proj, String weaponTitle) {
     if (player != null) {
       EntityType projType = proj.getType();
       switch (projType) {
         case ARROW:
         case SNOWBALL:
         case FIREBALL:
         case WITHER_SKULL:
         case EGG:
           proj.setShooter(player);
           proj.setMetadata("projParentNode", new FixedMetadataValue(this.classOne, weaponTitle));
           break;
       } 
     } 
   }
 
 
   
   public String getWeaponTitle(ItemStack item) {
     if (item == null) return null; 
     String[] weaponInfo = this.classOne.itemParentNode(item, null);
     return (weaponInfo == null) ? null : weaponInfo[0];
   }
 
   
   public String getWeaponTitle(Projectile proj) {
     return (proj != null && proj.hasMetadata("projParentNode")) ? proj.getMetadata("projParentNode").get(0).asString() : null;
   }
 
   
   public String getWeaponTitle(TNTPrimed tnt) {
     return (tnt != null && tnt.hasMetadata("CS_potex")) ? tnt.getMetadata("CS_potex").get(0).asString() : null;
   }
 
   
   public CSDirector getHandle() {
     return this.classOne;
   }
 }