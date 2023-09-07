 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class WeaponReloadEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final String weaponTitle;
   private String soundsReload;
   private double reloadSpeed = 1.0D;
   private int reloadDuration;
   
   public WeaponReloadEvent(Player player, String weaponTitle, String reloadSounds, int reloadDuration) {
     this.player = player;
     this.weaponTitle = weaponTitle;
     this.soundsReload = reloadSounds;
     this.reloadDuration = reloadDuration;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public String getSounds() {
     return this.soundsReload;
   }
   
   public String getWeaponTitle() {
     return this.weaponTitle;
   }
   
   public int getReloadDuration() {
     return this.reloadDuration;
   }
   
   public double getReloadSpeed() {
     return this.reloadSpeed;
   }
 
   
   public void setReloadSpeed(double reloadSpeed) {
     if (reloadSpeed < 0.0D) reloadSpeed = 0.0D; 
     this.reloadSpeed = reloadSpeed;
   }
   
   public void setReloadDuration(int reloadDuration) {
     this.reloadDuration = reloadDuration;
   }
   
   public void setSounds(String soundsReload) {
     this.soundsReload = soundsReload;
   }
   
   public HandlerList getHandlers() {
     return handlers;
   }
   
   public static HandlerList getHandlerList() {
     return handlers;
   }
 }


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponReloadEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */