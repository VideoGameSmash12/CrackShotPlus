package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class WeaponPreShootEvent extends Event implements Cancellable
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final String weaponTitle;
	private String sounds;
	private double bulletSpread;
	private final boolean isLeftClick;
	private boolean cancelled;

	public WeaponPreShootEvent(Player player, String weaponTitle, String sounds, double bulletSpread, boolean isLeftClick)
	{
		this.player = player;
		this.weaponTitle = weaponTitle;
		this.sounds = sounds;
		this.bulletSpread = bulletSpread;
		this.isLeftClick = isLeftClick;
	}

	public void setBulletSpread(double bulletSpread)
	{
		this.bulletSpread = Math.abs(bulletSpread);
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}