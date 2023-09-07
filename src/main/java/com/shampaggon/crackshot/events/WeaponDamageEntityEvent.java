 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.Player;
 import org.bukkit.event.Cancellable;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class WeaponDamageEntityEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final Entity victim;
   private final Entity dmgSource;
   private final String weaponTitle;
   private double totalDmg;
   private final boolean headShot;
   private final boolean backStab;
   private final boolean critHit;
   private boolean cancelled;
   
   public WeaponDamageEntityEvent(Player player, Entity victim, Entity dmgSource, String weaponTitle, double totalDmg, boolean headShot, boolean backStab, boolean critHit) {
     this.player = player;
     this.victim = victim;
     this.dmgSource = dmgSource;
     this.weaponTitle = weaponTitle;
     this.totalDmg = totalDmg;
     this.headShot = headShot;
     this.backStab = backStab;
     this.critHit = critHit;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public Entity getVictim() {
     return this.victim;
   }
   
   public Entity getDamager() {
     return this.dmgSource;
   }
   
   public String getWeaponTitle() {
     return this.weaponTitle;
   }
   
   public double getDamage() {
     return this.totalDmg;
   }
   
   public boolean isHeadshot() {
     return this.headShot;
   }
   
   public boolean isBackstab() {
     return this.backStab;
   }
   
   public boolean isCritical() {
     return this.critHit;
   }
   
   public boolean isCancelled() {
     return this.cancelled;
   }
   
   public void setCancelled(boolean cancelled) {
     this.cancelled = cancelled;
   }
   
   public void setDamage(double totalDmg) {
     this.totalDmg = totalDmg;
   }
   
   public HandlerList getHandlers() {
     return handlers;
   }
   
   public static HandlerList getHandlerList() {
     return handlers;
   }
 }


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponDamageEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */