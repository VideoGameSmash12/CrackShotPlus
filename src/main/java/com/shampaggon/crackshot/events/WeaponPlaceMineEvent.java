package com.shampaggon.crackshot.events;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponPlaceMineEvent extends Event
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final Entity mine;
	private final String weaponTitle;

	public WeaponPlaceMineEvent(Player player, Entity mine, String weaponTitle)
	{
		this.player = player;
		this.mine = mine;
		this.weaponTitle = weaponTitle;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}