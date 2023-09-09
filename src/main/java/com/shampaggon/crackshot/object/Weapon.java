package com.shampaggon.crackshot.object;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;
import java.util.List;

@Builder
public class Weapon
{
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final String internalName;

    private final ItemInformation itemInformation;

    private SignShops signShops;

    private RiotShield riotShield;

    private Shooting shooting;

    // private ExplosiveDevices explosiveDevices;

    // private ClusterBombs clusterBombs;

    // private Explosions explosions;

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

    // private Shrapnel shrapnel;

    private Fireworks fireworks;

    private Scope scope;

    // private Airstrikes airstrikes;

    private Extras extras;

    public static Weapon deserialize(String internalName, ConfigurationSection section)
    {
        WeaponBuilder builder = new WeaponBuilder().internalName(internalName);

        // Item_Information
        if (!section.isConfigurationSection("Item_Information"))
        {
            throw new IllegalArgumentException("Weapon entries are required to have an \"Item_Information\" entry.");
        }
        else
        {
            builder = builder.itemInformation(ItemInformation.fromConfigurationSection(section.getConfigurationSection("Item_Information")));
        }

        // SignShops
        if (section.isConfigurationSection("SignShops"))
            builder = builder.signShops(SignShops.fromConfigurationSection(section.getConfigurationSection("SignShops")));

        // Riot_Shield
        if (section.isConfigurationSection("Riot_Shield"))
            builder = builder.riotShield(RiotShield.fromConfigurationSection(section.getConfigurationSection("Riot_Shield")));

        // Shooting
        if (section.isConfigurationSection("Shooting"))
            builder = builder.shooting(Shooting.fromConfigurationSection(section.getConfigurationSection("Shooting")));

        // Explosive_Devices

        // Cluster_Bombs

        // Explosions

        // Custom_Death_Message
        if (section.isConfigurationSection("Custom_Death_Message"))
            builder = builder.customDeathMessage(CustomDeathMessage.fromConfigurationSection(section.getConfigurationSection("Custom_Death_Message")));

        // Sneak
        if (section.isConfigurationSection("Sneak"))
            builder = builder.sneak(Sneak.fromConfigurationSection(section.getConfigurationSection("Sneak")));

        // Fully_Automatic

        // Burstfire

        // Ammo

        // Reload

        // Firearm_Action

        // Headshot

        // Abilities

        // Critical_Hits

        // Backstab

        // Hit_Events

        // Damage_Based_On_Flight_Time

        // Particles

        // Shrapnel

        // Fireworks

        // Scope

        // Airstrikes

        // Extras
        if (section.isConfigurationSection("Extras"))
            builder = builder.extras(Extras.fromConfigurationSection(section.getConfigurationSection("Extras")));

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

        public static ItemInformation fromConfigurationSection(ConfigurationSection section)
        {
            return ItemInformation.builder()
                    .itemName(section.isString("Item_Name") ? miniMessage.deserialize(section.getString("Item_Name")) : null)
                    .itemType(section.isString("Item_Type") ? Material.matchMaterial(section.getString("Item_Type")) : null)
                    .itemLore(section.isList("Item_Lore") ?
                            section.getStringList("Item_Lore").stream().map(miniMessage::deserialize).toList() :
                            section.isString("Item_Lore") ? Arrays.stream(section.getString("Item_Lore")
                                    .split("\\|")).map(miniMessage::deserialize).toList() : null)
                    .meleeMode(section.getBoolean("Melee_Mode", false))
                    .meleeAttachment(section.getString("Melee_Attachment", null))
                    .soundsAcquired(SoundMetadata.fromConfigurationSection(section, "Sounds_Acquired"))
                    .hiddenFromList(section.getBoolean("Hidden_From_List", false))
                    .removeUnusedTag(section.getBoolean("Remove_Unused_Tag", false))
                    .build();
        }
    }

    @Builder
    public static class SignShops
    {
        private boolean enabled;

        private Price price;

        private Material signGunID;

        public static SignShops fromConfigurationSection(ConfigurationSection section)
        {
            return SignShops.builder()
                    .enabled(section.getBoolean("Enable", false))
                    .price(Price.fromString(section.getString("Price")))
                    .signGunID(section.isString("Sign_Gun_ID") ? Material.matchMaterial(section.getString("Sign_Gun_ID")) : null)
                    .build();
        }

        @Builder
        @RequiredArgsConstructor
        public static class Price
        {
            private int wtf;

            private int amount;

            public static Price fromString(String string)
            {
                if (string == null)
                {
                    return null;
                }

                final String[] split = string.split("-");

                return Price.builder()
                        .wtf(Integer.parseInt(split[0]))
                        .amount(Integer.parseInt(split[1]))
                        .build();
            }
        }
    }

    @Builder
    public static class RiotShield
    {
        private boolean enabled;

        private int durabilityLossPerHit;

        private List<SoundMetadata> soundsBlocked;

        private List<SoundMetadata> soundsBreak;

        public static RiotShield fromConfigurationSection(ConfigurationSection section)
        {
            return RiotShield.builder()
                    .enabled(section.getBoolean("Enable", false))
                    .durabilityLossPerHit(section.getInt("Durability_Loss_Per_Hit", 0))
                    .soundsBlocked(SoundMetadata.fromConfigurationSection(section, "Sounds_Blocked"))
                    .soundsBreak(SoundMetadata.fromConfigurationSection(section, "Sounds_Break"))
                    .build();
        }
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

        private ProjectileType projectileType;

        // TODO: WHAT THE FUCK IS A PROJECTILE "SUBTYPE"?
        private String projectileSubtype;

        private int projectileSpeed;

        private int projectileDamage;

        private boolean projectileFlames;

        private ProjectileIncendiary projectileIncendiary;

        private double bulletSpread;

        private boolean resetFallDamage;

        private List<SoundMetadata> soundsShoot;

        public static Shooting fromConfigurationSection(ConfigurationSection section)
        {
            return Shooting.builder()
                    .rightClickToShootEnabled(section.getBoolean("Right_Click_To_Shoot", false))
                    .leftClickBlockDamageCancelled(section.getBoolean("Cancel_Left_Click_Block_Damage", false))
                    .rightClickBlockInteractionsCancelled(section.getBoolean("Cancel_Right_Click_Interactions", false))
                    .delayBetweenShots(section.getInt("Delay_Between_Shots", 0))
                    .recoilAmount(section.getInt("Recoil_Amount", 0))
                    .projectileAmount(section.getInt("Projectile_Amount", 0))
                    .projectileType(ProjectileType.getType(section.getString("Projectile_Type", null)))
                    .projectileSubtype(section.getString("Projectile_Subtype", null))
                    .projectileSpeed(section.getInt("Projectile_Speed", 0))
                    .projectileDamage(section.getInt("Projectile_Damage", 0))
                    .projectileFlames(section.getBoolean("Projectile_Flames", false))
                    .projectileIncendiary(ProjectileIncendiary.fromConfigurationSection(section.getConfigurationSection("Projectile_Incendiary")))
                    .bulletSpread(section.getDouble("Bullet_Spread", 0.0))
                    .resetFallDamage(section.getBoolean("Reset_Fall_Damage", false))
                    .soundsShoot(SoundMetadata.fromConfigurationSection(section, "Sounds_Shoot"))
                    .build();
        }

        @Builder
        public static class ProjectileIncendiary
        {
            private boolean enabled;

            private int duration;

            public static ProjectileIncendiary fromConfigurationSection(ConfigurationSection section)
            {
                if (section == null) return null;

                return ProjectileIncendiary.builder()
                        .enabled(section.getBoolean("Enable", false))
                        .duration(section.getInt("Duration", 0))
                        .build();
            }
        }

        public enum ProjectileType
        {
            ARROW,
            EGG,
            FIREBALL,
            WITHERSKULL,
            SNOWBALL,
            NONE;

            public static ProjectileType getType(String type)
            {
                if (type == null)
                {
                    return null;
                }

                return ProjectileType.valueOf(type.toUpperCase());
            }
        }
    }

    @Builder
    public static class CustomDeathMessage
    {
        private String normal;

        public static CustomDeathMessage fromConfigurationSection(ConfigurationSection section)
        {
            if (section == null) return null;

            return CustomDeathMessage.builder()
                    .normal(section.getString("Normal", null))
                    .build();
        }
    }

    @Builder
    public static class Sneak
    {
        private boolean enabled;

        private boolean noRecoilEnabled;

        private int bulletSpread;

        public static Sneak fromConfigurationSection(ConfigurationSection section)
        {
            return Sneak.builder()
                    .enabled(section.getBoolean("Enable", false))
                    .noRecoilEnabled(section.getBoolean("No_Recoil", false))
                    .bulletSpread(section.getInt("Bullet_Spread", 0))
                    .build();
        }
    }

    @Builder
    public static class Burstfire
    {
        private boolean enabled;

        private int shotsPerBurst;

        private int delayBetweenShotsInBurst;

        public static Burstfire fromConfigurationSection(ConfigurationSection section)
        {
            return Burstfire.builder()
                    .enabled(section.getBoolean("Enable", false))
                    .shotsPerBurst(section.getInt("Shots_Per_Burst", 0))
                    .delayBetweenShotsInBurst(section.getInt("Delay_Between_Shots_In_Burst"))
                    .build();
        }
    }

    @Builder
    private static class Ammo
    {
        private boolean enabled;

        private Material ammoItemID;

        private List<SoundMetadata> soundsOutOfAmmo;

        private List<SoundMetadata> soundsShootWithNoAmmo;

        public static Ammo fromConfigurationSection(ConfigurationSection section)
        {
            return Ammo.builder()
                    .enabled(section.getBoolean("Enable"))
                    .ammoItemID(section.isString("Ammo_Item_ID") ? Material.matchMaterial(section.getString("Ammo_Item_ID")) : null)
                    .soundsOutOfAmmo(SoundMetadata.fromConfigurationSection(section, "Sounds_Out_Of_Ammo"))
                    .soundsShootWithNoAmmo(SoundMetadata.fromConfigurationSection(section, "Sounds_Shoot_With_No_Ammo"))
                    .build();
        }
    }

    @Builder
    public static class FullyAutomatic
    {
        private boolean enabled;

        private int fireRate;

        public static FullyAutomatic fromConfigurationSection(ConfigurationSection section)
        {
            return FullyAutomatic.builder()
                    .enabled(section.getBoolean("Enable"))
                    .fireRate(section.getInt("Fire_Rate"))
                    .build();
        }
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

        public static Reload fromConfigurationSection(ConfigurationSection section)
        {
            return Reload.builder()
                    .enabled(section.getBoolean("Enable"))
                    .startingAmount(section.getInt("Starting_Amount"))
                    .reloadAmount(section.getInt("Reload_Amount"))
                    .takeAmmoOnReload(section.getBoolean("Take_Ammo_On_Reload"))
                    .reloadingBulletsIndividually(section.getBoolean("Reload_Bullets_Individually"))
                    .reloadDuration(section.getInt("Reload_Duration"))
                    .soundsReloading(SoundMetadata.fromConfigurationSection(section, "Sounds_Reloading"))
                    .build();
        }
    }

    @Builder
    public static class FirearmAction
    {
        private Type type;

        private int openDuration;

        private int closeDuration;

        private int closeShootDelay;

        private List<SoundMetadata> soundsOpen;

        private List<SoundMetadata> soundsClose;

        public static FirearmAction fromConfigurationSection(ConfigurationSection section)
        {
            return FirearmAction.builder()
                    .type(Type.getType(section.getString("Type")))
                    .openDuration(section.getInt("Open_Duration"))
                    .closeDuration(section.getInt("Close_Duration"))
                    .closeShootDelay(section.getInt("Close_Shoot_Delay"))
                    .soundsOpen(SoundMetadata.fromConfigurationSection(section, "Sounds_Open"))
                    .soundsClose(SoundMetadata.fromConfigurationSection(section, "Sounds_Close"))
                    .build();
        }
        
        public enum Type
        {
            BOLT,
            LEVER,
            PUMP,
            NONE;

            public static Type getType(String type)
            {
                if (type == null)
                {
                    return null;
                }

                return Type.valueOf(type.toUpperCase());
            }
        }
    }

    @Builder
    public static class Headshot
    {
        private boolean enabled;

        private int bonusDamage;

        private List<SoundMetadata> soundsShooter;

        private List<SoundMetadata> soundsVictim;

        public static Headshot fromConfigurationSection(ConfigurationSection section)
        {
            return Headshot.builder()
                    .enabled(section.getBoolean("Enable"))
                    .bonusDamage(section.getInt("Bonus_Damage"))
                    .soundsShooter(SoundMetadata.fromConfigurationSection(section, "Sounds_Shooter"))
                    .soundsVictim(SoundMetadata.fromConfigurationSection(section, "Sounds_Victim"))
                    .build();
        }
    }

    @Builder
    public static class Abilities
    {
        private boolean resetHitCooldown;

        private int knockback;

        private boolean noFallDamage;

        public static Abilities fromConfigurationSection(ConfigurationSection section)
        {
            return Abilities.builder()
                    .resetHitCooldown(section.getBoolean("Reset_Hit_Cooldown"))
                    .knockback(section.getInt("Knockback"))
                    .noFallDamage(section.getBoolean("No_Fall_Damage"))
                    .build();
        }
    }

    @Builder
    public static class HitEvents
    {
        private boolean enabled;

        private String messageShooter;

        private String messageVictim;

        private List<SoundMetadata> soundsShooter;

        private List<SoundMetadata> soundsVictim;

        public static HitEvents fromConfigurationSection(ConfigurationSection section)
        {
            return HitEvents.builder()
                    .enabled(section.getBoolean("Enable"))
                    .messageShooter(section.getString("Message_Shooter"))
                    .messageVictim(section.getString("Message_Victim"))
                    .soundsShooter(SoundMetadata.fromConfigurationSection(section, "Sounds_Shooter"))
                    .soundsVictim(SoundMetadata.fromConfigurationSection(section, "Sounds_Victim"))
                    .build();
        }
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
        private String makeVictimSpeak;

        private String makeVictimRunCommand;

        private String runCommand;

        private String runConsoleCommand;

        private boolean oneTimeUse;

        public static Extras fromConfigurationSection(ConfigurationSection section)
        {
            return Extras.builder()
                    .makeVictimSpeak(section.getString("Make_Victim_Speak", null))
                    .makeVictimRunCommand(section.getString("Make_Victim_Run_Command", null))
                    .runCommand(section.getString("Run_Command", null))
                    .runConsoleCommand(section.getString("Run_Console_Command", null))
                    .oneTimeUse(section.getBoolean("One_Time_Use", false))
                    .build();
        }
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

        public static SoundMetadata fromStringSingle(String string)
        {
            final SoundMetadataBuilder soundMeta = SoundMetadata.builder();
            String[] soundArgs = string.split("-");

            // Sound, Volume, Pitch, Delay
            return soundMeta.sound(Sound.valueOf(soundArgs[0]))
                    .volume(Float.parseFloat(soundArgs[1]))
                    .pitch(Float.parseFloat(soundArgs[2]))
                    .delay(Long.parseLong(soundArgs[3]))
                    .build();
        }

        public static List<SoundMetadata> fromConfigurationSection(ConfigurationSection section, String name)
        {
            return section.isList(name) ?
                    fromList(section.getStringList(name)) :
                    section.isString(name) ?
                            fromList(section.getString(name)) :
                            List.of();
        }

        public static List<SoundMetadata> fromList(String list)
        {
            return Arrays.stream(list.split(",")).map(SoundMetadata::fromStringSingle).toList();
        }

        public static List<SoundMetadata> fromList(List<String> list)
        {
            return list.stream().map(SoundMetadata::fromStringSingle).toList();
        }
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
