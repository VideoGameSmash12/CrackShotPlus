package com.shampaggon.crackshot.events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class WeaponHitBlockEvent extends Event
{
    @Getter
	private static final HandlerList handlerList = new HandlerList();

	private final Player player;
	private final Entity projectile;
	private final String weaponTitle;
	private final Block block;
	private final Block airBlock;

	public WeaponHitBlockEvent(Player player, Entity projectile, String weaponTitle, Block block, Block airBlock)
	{
		this.player = player;
		this.projectile = projectile;
		this.weaponTitle = weaponTitle;
		this.block = block;
		this.airBlock = airBlock;
	}

    @Override
	public @NotNull HandlerList getHandlers()
	{
		return handlerList;
	}
}