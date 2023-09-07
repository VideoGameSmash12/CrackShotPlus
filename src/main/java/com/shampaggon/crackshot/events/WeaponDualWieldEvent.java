 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 import org.bukkit.inventory.ItemStack;
 
 public class WeaponDualWieldEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private boolean dualWield;
   private final ItemStack item;
   private final Player player;
   private final String weaponTitle;
   
   public WeaponDualWieldEvent(Player player, String weaponTitle, ItemStack item, boolean dualWield) {
     this.dualWield = dualWield;
     this.item = item;
     this.player = player;
     this.weaponTitle = weaponTitle;
   }
   
   public ItemStack getItemStack() {
     return this.item;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public String getWeaponTitle() {
     return this.weaponTitle;
   }
   
   public boolean isDualWield() {
     return this.dualWield;
   }
   
   public void setDualWield(boolean dualWield) {
     this.dualWield = dualWield;
   }
   
   public HandlerList getHandlers() {
     return handlers;
   }
   
   public static HandlerList getHandlerList() {
     return handlers;
   }
 }


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponDualWieldEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */