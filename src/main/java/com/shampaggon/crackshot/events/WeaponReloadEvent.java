package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class WeaponReloadEvent extends Event
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();
	
	private final Player player;
	private final String weaponTitle;
	private String sounds;
	private double reloadSpeed = 1.0D;
	private int reloadDuration;

	public WeaponReloadEvent(Player player, String weaponTitle, String reloadSounds, int reloadDuration)
	{
		this.player = player;
		this.weaponTitle = weaponTitle;
		this.sounds = reloadSounds;
		this.reloadDuration = reloadDuration;
	}
	
	public void setReloadSpeed(double reloadSpeed)
	{
		this.reloadSpeed = Math.max(reloadSpeed, 0.0D);
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}