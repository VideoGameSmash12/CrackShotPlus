package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponPrepareShootEvent extends Event implements Cancellable
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final String weaponTitle;
	@Setter
	private boolean cancelled;

	public WeaponPrepareShootEvent(Player player, String weaponTitle)
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