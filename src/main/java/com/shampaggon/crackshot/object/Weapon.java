package com.shampaggon.crackshot.object;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class Weapon implements ConfigurationSerializable
{
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final ItemInformation itemInformation;

    private SignShops signShops;

    private RiotShield riotShield;

    private Shooting shooting;

    private CustomDeathMessage customDeathMessage;

    private Sneak sneak;

    private FullyAutomatic fullyAutomatic;

    private Burstfire burstfire;

    private Ammo ammo;

    private Reload reload;

    private FirearmAction firearmAction;

    private Headshot headshot;

    private Abilities abilities;

    private HitEvents hitEvents;

    private DamageBasedOnFlightTime damageBasedOnFlightTime;

    private Particles particles;

    private Fireworks fireworks;

    private Scope scope;

    private Extras extras;

    @Override
    public @NotNull Map<String, Object> serialize()
    {
        return new HashMap<>();
    }

    public static Weapon deserialize(Map<String, Object> map)
    {
        final WeaponBuilder builder = new WeaponBuilder();

        // ITEM INFORMATION
        if (!map.containsKey("Item_Information"))
        {
            throw new IllegalArgumentException("Weapon entries are required to have an \"Item_Information\" entry.");
        }
        else
        {
            ItemInformation.ItemInformationBuilder iBuilder = ItemInformation.builder();
            final ConfigurationSection section = (ConfigurationSection) map.get("Item_Information");

            // Name
            if (section.isString("Item_Name"))
            {
                iBuilder = iBuilder.itemName(miniMessage.deserialize(section.getString("Item_Name")));
            }

            // Item Type
            if (section.isString("Item_Type"))
            {
                iBuilder = iBuilder.itemType(Material.getMaterial(section.getString("Item_Type")));
            }

            // Item Lore
            if (section.isList("Item_Lore"))
            {
                iBuilder = iBuilder.itemLore(section.getStringList("Item_Lore").stream().map(miniMessage::deserialize).toList());
            }
            else if (section.isString("Item_Lore"))
            {
                iBuilder = iBuilder.itemLore(List.of(miniMessage.deserialize(section.getString("Item_Lore"))));
            }

            // Melee Mode?
            if (section.isBoolean("Melee_Mode"))
            {
                iBuilder = iBuilder.meleeMode(section.getBoolean("Melee_Mode"));
            }

            // Melee Attachment
            if (section.isString("Melee_Attachment"))
            {
                iBuilder = iBuilder.meleeAttachment(section.getString("Melee_Attachment"));
            }

            // Sounds (Acquired)
            if (section.isString("Sounds_Acquired"))
            {
                iBuilder = iBuilder.soundsAcquired(Arrays.stream(section.getString("Sounds_Acquired").split(",")).map(soundString -> {
                    SoundMetadata.SoundMetadataBuilder soundMeta = SoundMetadata.builder();
                    String[] soundArgs = soundString.split("-");

                    // Sound, Volume, Pitch, Delay
                    return soundMeta.sound(Sound.valueOf(soundArgs[0]))
                            .volume(Float.parseFloat(soundArgs[1]))
                            .pitch(Float.parseFloat(soundArgs[2]))
                            .delay(Long.parseLong(soundArgs[3]))
                            .build();
                }).toList());
            }

            // Hidden from list?
            if (section.isBoolean("Hidden_From_List"))
            {
                iBuilder.hiddenFromList(section.getBoolean("Hidden_From_List"));
            }

            // Remove unused tag?
            if (section.isBoolean("Remove_Unused_Tag"))
            {
                iBuilder.removeUnusedTag(section.getBoolean("Remove_Unused_Tag"));
            }

            builder.itemInformation(iBuilder.build());
        }

        // EXTRAS
        if (map.containsKey("Extras"))
        {
            final Extras.ExtrasBuilder extras = Extras.builder();
            ConfigurationSection section = (ConfigurationSection) map.get("Extras");

            if (section.isBoolean("One_Time_Use"))
            {
                extras.oneTimeUse(section.getBoolean("One_Time_Use", false));
            }

            builder.extras(extras.build());
        }

        return builder.build();
    }

    @Builder
    public static class ItemInformation
    {
        private Component itemName;

        private Material itemType;

        private List<Component> itemLore;

        private boolean meleeMode;

        private String meleeAttachment;

        private List<SoundMetadata> soundsAcquired;

        private boolean hiddenFromList;

        private boolean removeUnusedTag;
    }

    @Builder
    public static class SignShops
    {
        private boolean enabled;

        private Price price;

        private Material signGunID;

        @Builder
        @RequiredArgsConstructor
        public static class Price
        {
            private int wtf;

            private int amount;
        }
    }

    @Builder
    public static class RiotShield
    {
        private boolean enabled;

        private int durabilityLossPerHit;

        private List<SoundMetadata> soundsBlocked;

        private List<SoundMetadata> soundsBreak;
    }

    @Builder
    public static class Shooting
    {
        private boolean rightClickToShootEnabled;

        private boolean leftClickBlockDamageCancelled;

        private boolean rightClickBlockInteractionsCancelled;

        private int delayBetweenShots;

        private int recoilAmount;

        private int projectileAmount;

        // TODO: MAKE AN ENUM FOR ALL THE TYPES OF PROJECTILE TYPES
        private String projectileType;

        // TODO: WHAT THE FUCK IS A PROJECTILE "SUBTYPE"?
        private int projectileSubtype;

        private int projectileSpeed;

        private int projectileDamage;

        private boolean projectileFlames;

        private ProjectileIncendiary projectileIncendiary;

        private double bulletSpread;

        private boolean resetFallDamage;

        private List<SoundMetadata> soundsShoot;

        @Builder
        public static class ProjectileIncendiary implements ConfigurationSerializable
        {
            private boolean enabled;

            private int duration;

            public static ProjectileIncendiary deserialize(Map<String, Object> map)
            {
                return ProjectileIncendiary.builder()
                        .enabled((Boolean) map.getOrDefault("Enable", false))
                        .duration((Integer) map.getOrDefault("Duration", 0))
                        .build();
            }

            @Override
            public @NotNull Map<String, Object> serialize()
            {
                return new HashMap<>();
            }
        }
    }

    @Builder
    public static class CustomDeathMessage
    {
        private String normal;
    }

    @Builder
    public static class Sneak
    {
        private boolean enabled;

        private boolean noRecoilEnabled;

        private int bulletSpread;
    }

    @Builder
    public static class Burstfire
    {
        private boolean enabled;

        private int shotsPerBurst;

        private int delayBetweenShotsInBurst;
    }

    @Builder
    private static class Ammo
    {
        private boolean enabled;

        private Material ammoItemID;

        private List<SoundMetadata> soundsOutOfAmmo;

        private List<SoundMetadata> soundsShootWithNoAmmo;
    }

    @Builder
    public static class FullyAutomatic
    {
        private boolean enabled;

        private int fireRate;
    }

    @Builder
    public static class Reload
    {
        private boolean enabled;

        private int startingAmount;

        private int reloadAmount;

        private boolean takeAmmoOnReload;

        private boolean reloadingBulletsIndividually;

        private int reloadDuration;

        private List<SoundMetadata> soundsReloading;
    }

    @Builder
    public static class FirearmAction
    {
        // TODO: TYPE ENUM
        private String type;

        private int openDuration;

        private int closeDuration;

        private int closeShootDelay;

        private List<SoundMetadata> soundsOpen;

        private List<SoundMetadata> soundsClose;
    }

    @Builder
    public static class Headshot
    {
        private boolean enabled;

        private int bonusDamage;

        private List<SoundMetadata> soundsShooter;

        private List<SoundMetadata> soundsVictim;
    }

    @Builder
    public static class Abilities
    {
        private boolean resetHitCooldown;

        private int knockback;

        private boolean noFallDamage;
    }

    @Builder
    public static class HitEvents
    {
        private boolean enabled;

        private List<SoundMetadata> soundsShooter;
    }

    @Builder
    public static class DamageBasedOnFlightTime
    {
        private boolean enabled;

        private int bonusDamagePerTick;

        private int maximumDamage;
    }

    @Builder
    public static class Particles
    {
        private boolean enabled;

        private List<ParticleMetadata> particlePlayerShoot;

        private boolean particleTerrain;

        private List<ParticleMetadata> particleImpactAnything;
    }

    @Builder
    public static class Fireworks
    {
        private boolean enabled;

        private List<FireworkMetadata> fireworksHeadshot;
    }

    @Builder
    public static class Scope
    {
        private boolean enabled;

        private boolean nightVision;

        private int zoomAmount;

        private int zoomBulletSpread;

        private List<SoundMetadata> soundsToggleZoom;
    }

    @Builder
    public static class Extras
    {
        private boolean oneTimeUse;
    }

    //-- NON-CONFIGURATION SECTION DATA GOES HERE --//

    @Builder
    @RequiredArgsConstructor
    public static class SoundMetadata
    {
        private final Sound sound;

        private final float volume;

        private final float pitch;

        private final long delay;
    }

    @Builder
    @RequiredArgsConstructor
    public static class ParticleMetadata
    {
        private final Effect effect;

        private final int data;
    }

    @Builder
    @RequiredArgsConstructor
    public static class FireworkMetadata
    {
        private final FireworkEffect.Type type;

        private final boolean trail;

        private final boolean flicker;

        private int red;

        private int green;

        private int blue;
    }
}
