 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class WeaponReloadCompleteEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final String weaponTitle;
   
   public WeaponReloadCompleteEvent(Player player, String weaponTitle) {
     this.player = player;
     this.weaponTitle = weaponTitle;
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


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponReloadCompleteEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */