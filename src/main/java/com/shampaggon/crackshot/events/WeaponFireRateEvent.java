 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 import org.bukkit.inventory.ItemStack;
 
 public class WeaponFireRateEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final String weaponTitle;
   private final ItemStack item;
   private int fireRate;
   
   public WeaponFireRateEvent(Player player, String weaponTitle, ItemStack item, int fireRate) {
     this.player = player;
     this.weaponTitle = weaponTitle;
     this.item = item;
     this.fireRate = fireRate;
   }
   
   public int getFireRate() {
     return this.fireRate;
   }
   
   public void setFireRate(int fireRate) {
     if (fireRate <= 0 || fireRate > 16) {
       throw new IllegalArgumentException("Fire rate not in range [1..16]: " + fireRate);
     }
     this.fireRate = fireRate;
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
   
   public HandlerList getHandlers() {
     return handlers;
   }
   
   public static HandlerList getHandlerList() {
     return handlers;
   }
 }


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponFireRateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */