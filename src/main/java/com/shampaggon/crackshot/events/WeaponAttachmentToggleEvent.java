package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class WeaponAttachmentToggleEvent extends Event implements Cancellable
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final String weaponTitle;
	private final ItemStack itemStack;
	private int toggleDelay;
	private boolean cancelled;

	public WeaponAttachmentToggleEvent(Player player, String weaponTitle, ItemStack item, int toggleDelay)
	{
		this.player = player;
		this.weaponTitle = weaponTitle;
		this.itemStack = item;
		this.toggleDelay = toggleDelay;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}