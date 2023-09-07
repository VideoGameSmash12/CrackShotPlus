 package com.shampaggon.crackshot.events;
 
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.event.Cancellable;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class WeaponTriggerEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final LivingEntity victim;
   private final String weaponTitle;
   private boolean cancelled;
   
   public WeaponTriggerEvent(Player player, LivingEntity victim, String weaponTitle) {
     this.player = player;
     this.victim = victim;
     this.weaponTitle = weaponTitle;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public LivingEntity getVictim() {
     return this.victim;
   }
   
   public String getWeaponTitle() {
     return this.weaponTitle;
   }
   
   public boolean isCancelled() {
     return this.cancelled;
   }
   
   public void setCancelled(boolean cancelled) {
     this.cancelled = cancelled;
   }
   
   public HandlerList getHandlers() {
     return handlers;
   }
   
   public static HandlerList getHandlerList() {
     return handlers;
   }
 }


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponTriggerEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */