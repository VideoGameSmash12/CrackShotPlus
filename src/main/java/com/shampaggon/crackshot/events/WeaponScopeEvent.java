package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponScopeEvent extends Event implements Cancellable
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final String weaponTitle;
	private final boolean zoomIn;
	@Setter
	private boolean cancelled;

	public WeaponScopeEvent(Player player, String weaponTitle, boolean zoomIn)
	{
		this.player = player;
		this.weaponTitle = weaponTitle;
		this.zoomIn = zoomIn;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}