 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class WeaponFirearmActionEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final String weaponTitle;
   private double speed = 1.0D;
   private final boolean reload;
   
   public WeaponFirearmActionEvent(Player player, String weaponTitle, boolean reload) {
     this.player = player;
     this.weaponTitle = weaponTitle;
     this.reload = reload;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public String getWeaponTitle() {
     return this.weaponTitle;
   }
   
   public double getSpeed() {
     return this.speed;
   }
   
   public boolean isReload() {
     return this.reload;
   }
 
   
   public void setSpeed(double speed) {
     if (speed < 0.0D) speed = 0.0D; 
     this.speed = speed;
   }
   
   public HandlerList getHandlers() {
     return handlers;
   }
   
   public static HandlerList getHandlerList() {
     return handlers;
   }
 }


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponFirearmActionEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */