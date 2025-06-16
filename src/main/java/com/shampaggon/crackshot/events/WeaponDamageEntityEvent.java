package com.shampaggon.crackshot.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class WeaponDamageEntityEvent extends Event implements Cancellable
{
	@Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final Entity victim;
	private final Entity damager;
	private final String weaponTitle;
	private double damage;
	private final boolean headshot;
	private final boolean backstab;
	private final boolean critical;
	private boolean cancelled;

	public WeaponDamageEntityEvent(Player player, Entity victim, Entity damager, String weaponTitle, double damage, boolean headshot, boolean backstab, boolean critical)
	{
		this.player = player;
		this.victim = victim;
		this.damager = damager;
		this.weaponTitle = weaponTitle;
		this.damage = damage;
		this.headshot = headshot;
		this.backstab = backstab;
		this.critical = critical;
	}

	@Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}