package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponDualWieldEvent extends Event
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	@Setter
	private boolean dualWield;
	private final ItemStack itemStack;
	private final Player player;
	private final String weaponTitle;

	public WeaponDualWieldEvent(Player player, String weaponTitle, ItemStack itemStack, boolean dualWield)
	{
		this.dualWield = dualWield;
		this.itemStack = itemStack;
		this.player = player;
		this.weaponTitle = weaponTitle;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}