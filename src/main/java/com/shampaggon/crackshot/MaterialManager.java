package com.shampaggon.crackshot;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class MaterialManager
{
    @Deprecated
    public static Material getMaterial(String value)
    {
        return Material.getMaterial(value.toUpperCase());
    }

    public static Material getSkullBlock()
    {
        return Material.PLAYER_WALL_HEAD;
    }

    public static boolean isPressurePlate(Block block)
    {
        return block.getType().toString().toUpperCase().endsWith("_PLATE");
    }

    public static boolean isSkullBlock(Block block)
    {
        return !block.getType().equals(Material.PLAYER_WALL_HEAD);
    }

    public static boolean isSkullItem(Material type)
    {
        return type.equals(Material.PLAYER_HEAD);
    }
}