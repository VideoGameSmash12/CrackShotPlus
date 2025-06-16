package com.shampaggon.crackshot.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponFireRateEvent extends Event
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final String weaponTitle;
	private final ItemStack itemStack;
	private int fireRate;

	public WeaponFireRateEvent(Player player, String weaponTitle, ItemStack itemStack, int fireRate)
	{
		this.player = player;
		this.weaponTitle = weaponTitle;
		this.itemStack = itemStack;
		this.fireRate = fireRate;
	}

	public void setFireRate(int fireRate)
	{
		if (fireRate <= 0 || fireRate > 16)
		{
			throw new IllegalArgumentException("Fire rate not in range [1..16]: " + fireRate);
		}

		this.fireRate = fireRate;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}