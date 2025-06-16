package com.shampaggon.crackshot.events;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponExplodeEvent extends Event
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final Location location;
	private final String weaponTitle;
	private final boolean split;
	private final boolean airstrike;

	public WeaponExplodeEvent(Player player, Location location, String weaponTitle, boolean split, boolean airstrike)
	{
		this.player = player;
		this.location = location;
		this.weaponTitle = weaponTitle;
		this.split = split;
		this.airstrike = airstrike;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}