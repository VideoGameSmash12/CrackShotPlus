 package com.shampaggon.crackshot.events;
 
 import org.bukkit.Location;
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class WeaponExplodeEvent extends Event {
   private static final HandlerList handlers = new HandlerList();
   private final Player player;
   private final Location location;
   private final String weaponTitle;
   private final boolean isSplit;
   private final boolean isAirstrike;
   
   public WeaponExplodeEvent(Player player, Location location, String weaponTitle, boolean isSplit, boolean isAirstrike) {
     this.player = player;
     this.location = location;
     this.weaponTitle = weaponTitle;
     this.isSplit = isSplit;
     this.isAirstrike = isAirstrike;
   }
   
   public Player getPlayer() {
     return this.player;
   }
   
   public Location getLocation() {
     return this.location;
   }
   
   public String getWeaponTitle() {
     return this.weaponTitle;
   }
   
   public boolean isSplit() {
     return this.isSplit;
   }
   
   public boolean isAirstrike() {
     return this.isAirstrike;
   }
   
   public HandlerList getHandlers() {
     return handlers;
   }
   
   public static HandlerList getHandlerList() {
     return handlers;
   }
 }


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponExplodeEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */