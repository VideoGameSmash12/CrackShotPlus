package com.shampaggon.crackshot.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponReloadCompleteEvent extends Event
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final String weaponTitle;

	public WeaponReloadCompleteEvent(Player player, String weaponTitle)
	{
		this.player = player;
		this.weaponTitle = weaponTitle;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}