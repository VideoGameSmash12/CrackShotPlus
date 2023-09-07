 package com.shampaggon.crackshot.events;
 
 import org.bukkit.event.Cancellable;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 import org.bukkit.inventory.ItemStack;
 
 public class WeaponAttachmentEvent extends Event implements Cancellable {
   private static final HandlerList handlers = new HandlerList();
   private final String weaponTitle;
   private final ItemStack item;
   private String attachment;
   private boolean cancelled;
   
   public WeaponAttachmentEvent(String weaponTitle, ItemStack item, String attachment) {
     this.weaponTitle = weaponTitle;
     this.item = item;
     this.attachment = attachment;
   }
   
   public String getAttachment() {
     return this.attachment;
   }
   
   public ItemStack getItemStack() {
     return this.item;
   }
   
   public void setAttachment(String attachment) {
     this.attachment = attachment;
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


/* Location:              /home/videogamesm12/Downloads/CrackShot.jar!/com/shampaggon/crackshot/events/WeaponAttachmentEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */