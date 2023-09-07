 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class WeaponPlaceMineEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final Entity mine;
   private final String weaponTitle;
   
   public WeaponPlaceMineEvent(Player player, Entity mine, String weaponTitle) {
     this.player = player;
     this.mine = mine;
     this.weaponTitle = weaponTitle;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public Entity getMine() {
     return this.mine;
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


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponPlaceMineEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */