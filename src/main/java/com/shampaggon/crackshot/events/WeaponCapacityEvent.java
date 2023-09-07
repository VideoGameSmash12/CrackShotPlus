 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 import org.bukkit.inventory.ItemStack;
 
 public class WeaponCapacityEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final String weaponTitle;
   private final ItemStack item;
   private int capacity;
   
   public WeaponCapacityEvent(Player player, String weaponTitle, ItemStack item, int capacity) {
     this.player = player;
     this.weaponTitle = weaponTitle;
     this.item = item;
     this.capacity = capacity;
   }
   
   public int getCapacity() {
     return this.capacity;
   }
   
   public ItemStack getItemStack() {
     return this.item;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public void setCapacity(int capacity) {
     this.capacity = (capacity < 1) ? 1 : capacity;
   }
   
   public String getWeaponTitle() {
     return this.weaponTitle;
   }
   
   public HandlerList getHandlers() {
     return handlers;
   }
   
   public static HandlerList getHandlerList() {
     return handlers;
   }
 }


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponCapacityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */