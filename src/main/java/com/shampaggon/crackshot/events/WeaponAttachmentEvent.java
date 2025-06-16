package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class WeaponAttachmentEvent extends Event implements Cancellable
{
    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private final String weaponTitle;
    private final ItemStack itemStack;
    private String attachment;
    private boolean cancelled;

    public WeaponAttachmentEvent(String weaponTitle, ItemStack item, String attachment)
    {
        this.weaponTitle = weaponTitle;
        this.itemStack = item;
        this.attachment = attachment;
    }

    @Override
    public @NotNull HandlerList getHandlers()
    {
        return handlerList;
    }
}