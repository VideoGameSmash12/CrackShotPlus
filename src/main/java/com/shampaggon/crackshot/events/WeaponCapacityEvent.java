package com.shampaggon.crackshot.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponCapacityEvent extends Event
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final String weaponTitle;
	private final ItemStack itemStack;
	private int capacity;

	public WeaponCapacityEvent(Player player, String weaponTitle, ItemStack item, int capacity)
	{
		this.player = player;
		this.weaponTitle = weaponTitle;
		this.itemStack = item;
		this.capacity = capacity;
	}

	public void setCapacity(int capacity)
	{
		this.capacity = Math.max(capacity, 1);
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}