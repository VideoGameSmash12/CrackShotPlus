package com.shampaggon.crackshot.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponFirearmActionEvent extends Event
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final String weaponTitle;
	private double speed = 1.0D;
	private final boolean reload;

	public WeaponFirearmActionEvent(Player player, String weaponTitle, boolean reload)
	{
		this.player = player;
		this.weaponTitle = weaponTitle;
		this.reload = reload;
	}

	public void setSpeed(double speed)
	{
		this.speed = Math.max(speed, 0.0D);
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}