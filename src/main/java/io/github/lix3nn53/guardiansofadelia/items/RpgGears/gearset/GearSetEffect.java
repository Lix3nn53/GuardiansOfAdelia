package io.github.lix3nn53.guardiansofadelia.items.RpgGears.gearset;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public enum GearSetEffect {
    EMPTY,
    KNOCKBACK_RESISTANCE,
    CRITICAL_DAMAGE,
    CRITICAL_CHANCE,
    // MANA_REGEN,
    SLOW_FALLING,
    SWIMMING_SPEED,
    ATTACK_SPEED_INCREASE,
    ATTACK_SPEED_DECREASE,
    ABILITY_HASTE,
    JUMP_BOOST,
    MOVEMENT_SPEED,
    LIFE_STEAL,
    ELEMENT_FIRE,
    ELEMENT_WATER,
    ELEMENT_EARTH,
    ELEMENT_LIGHTNING,
    ELEMENT_AIR;

    public static boolean isWearingSameArmorType(ArmorGearType helmet, ArmorGearType chestplate, ArmorGearType leggings, ArmorGearType boots) {
        if (helmet == null || chestplate == null || leggings == null || boots == null) return false;

        return helmet == chestplate && helmet == leggings && helmet == boots;
    }

    public static String getCustomSet(ItemStack itemStack) {
        if (InventoryUtils.isAirOrNull(itemStack)) return null;

        if (PersistentDataContainerUtil.hasString(itemStack, "gearSet")) {
            return PersistentDataContainerUtil.getString(itemStack, "gearSet");
        }

        return null;
    }

    public void applySetEffect(Player player, RPGCharacterStats rpgCharacterStats) {
        player.sendMessage(this.toString());
        switch (this) {
            case EMPTY:
                break;
            case KNOCKBACK_RESISTANCE:
                Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(0.7);
                break;
            case CRITICAL_DAMAGE:
                rpgCharacterStats.addToBuffMultiplier(BuffType.CRIT_DAMAGE, 0.2f, null);
                break;
            case CRITICAL_CHANCE:
                rpgCharacterStats.addToBuffMultiplier(BuffType.CRIT_CHANCE, 0.1f, null);
                break;
            case SLOW_FALLING:
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                break;
            case SWIMMING_SPEED:
                potionEffect = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                break;
            case ATTACK_SPEED_INCREASE:
                potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2);
                player.addPotionEffect(potionEffect);
                break;
            case ATTACK_SPEED_DECREASE:
                potionEffect = new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 2);
                player.addPotionEffect(potionEffect);
                break;
            case ABILITY_HASTE:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ABILITY_HASTE, 20, null);
                break;
            case JUMP_BOOST:
                potionEffect = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2);
                player.addPotionEffect(potionEffect);
                break;
            case MOVEMENT_SPEED:
                potionEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false);
                player.addPotionEffect(potionEffect);
                break;
            case LIFE_STEAL:
                rpgCharacterStats.addToBuffMultiplier(BuffType.LIFE_STEAL, 0.1f, null);
                break;
            case ELEMENT_FIRE:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_FIRE, 0.1f, null);
                break;
            case ELEMENT_WATER:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_WATER, 0.1f, null);
                break;
            case ELEMENT_EARTH:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_EARTH, 0.1f, null);
                break;
            case ELEMENT_LIGHTNING:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_LIGHTNING, 0.1f, null);
                break;
            case ELEMENT_AIR:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_AIR, 0.1f, null);
                break;
        }
    }

    public void clearSetEffect(Player player, RPGCharacterStats rpgCharacterStats) {
        switch (this) {
            case EMPTY:
                break;
            case KNOCKBACK_RESISTANCE:
                player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0);
                break;
            case CRITICAL_DAMAGE:
                rpgCharacterStats.addToBuffMultiplier(BuffType.CRIT_DAMAGE, -0.2f, null);
                break;
            case CRITICAL_CHANCE:
                rpgCharacterStats.addToBuffMultiplier(BuffType.CRIT_CHANCE, -0.1f, null);
                break;
            case SLOW_FALLING:
                player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                break;
            case SWIMMING_SPEED:
                player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
                break;
            case ATTACK_SPEED_INCREASE:
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                break;
            case ATTACK_SPEED_DECREASE:
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                break;
            case ABILITY_HASTE:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ABILITY_HASTE, -20, null);
                break;
            case JUMP_BOOST:
                player.removePotionEffect(PotionEffectType.JUMP);
                break;
            case MOVEMENT_SPEED:
                player.removePotionEffect(PotionEffectType.SPEED);
                break;
            case LIFE_STEAL:
                rpgCharacterStats.addToBuffMultiplier(BuffType.LIFE_STEAL, -0.1f, null);
                break;
            case ELEMENT_FIRE:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_FIRE, -0.1f, null);
                break;
            case ELEMENT_WATER:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_WATER, -0.1f, null);
                break;
            case ELEMENT_EARTH:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_EARTH, -0.1f, null);
                break;
            case ELEMENT_LIGHTNING:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_LIGHTNING, -0.1f, null);
                break;
            case ELEMENT_AIR:
                rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_AIR, -0.1f, null);
                break;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case EMPTY:
                return "Empty";
            case KNOCKBACK_RESISTANCE:
                return ChatPalette.GRAY + "Knockback Resist 70%";
            case CRITICAL_DAMAGE:
                return ChatPalette.GRAY + "Critical Damage +20%";
            case CRITICAL_CHANCE:
                return ChatPalette.GRAY + "Critical Chance +10%";
            /*case MANA_REGEN:
                return ChatPalette.GRAY + "Mana Regen";*/
            case SLOW_FALLING:
                return ChatPalette.GRAY + "Slow Fallling";
            case SWIMMING_SPEED:
                return ChatPalette.GRAY + "Swimming Speed 2x";
            case ATTACK_SPEED_INCREASE:
                return ChatPalette.GRAY + "Attack Speed +20%";
            case ATTACK_SPEED_DECREASE:
                return ChatPalette.GRAY + "Attack Speed -20%";
            case ABILITY_HASTE:
                return ChatPalette.GRAY + "Ability Haste +20";
            case JUMP_BOOST:
                return ChatPalette.GRAY + "Jump +1";
            case MOVEMENT_SPEED:
                return ChatPalette.GRAY + "Movement Speed +20%";
            case LIFE_STEAL:
                return ChatPalette.GRAY + "Lifesteal +10%";
            case ELEMENT_FIRE:
                return ElementType.FIRE.getFullName(Translation.DEFAULT_LANG) + " +10%";
            case ELEMENT_WATER:
                return ElementType.WATER.getFullName(Translation.DEFAULT_LANG) + " +10%";
            case ELEMENT_EARTH:
                return ElementType.EARTH.getFullName(Translation.DEFAULT_LANG) + " +10%";
            case ELEMENT_LIGHTNING:
                return ElementType.LIGHTNING.getFullName(Translation.DEFAULT_LANG) + " +10%";
            case ELEMENT_AIR:
                return ElementType.AIR.getFullName(Translation.DEFAULT_LANG) + " +10%";
        }

        final StringBuilder sb = new StringBuilder("GearSetEffect{");
        sb.append('}');
        return sb.toString();
    }
}
