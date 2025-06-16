package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponTriggerEvent extends Event implements Cancellable
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final LivingEntity victim;
	private final String weaponTitle;
	@Setter
	private boolean cancelled;

	public WeaponTriggerEvent(Player player, LivingEntity victim, String weaponTitle)
	{
		this.player = player;
		this.victim = victim;
		this.weaponTitle = weaponTitle;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}