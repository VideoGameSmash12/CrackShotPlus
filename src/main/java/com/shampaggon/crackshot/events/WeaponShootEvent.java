 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class WeaponShootEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final Entity objProj;
   private final String weaponTitle;
   
   public WeaponShootEvent(Player player, Entity objProj, String weaponTitle) {
     this.player = player;
     this.objProj = objProj;
     this.weaponTitle = weaponTitle;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public Entity getProjectile() {
     return this.objProj;
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


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponShootEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */