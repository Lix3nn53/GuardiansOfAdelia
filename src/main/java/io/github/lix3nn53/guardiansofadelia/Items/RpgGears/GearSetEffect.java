package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public enum GearSetEffect {
    KNOCKBACK_RESISTANCE,
    CRITICAL_DAMAGE,
    CRITICAL_CHANCE,
    MANA_REGEN,
    SLOW_FALLING,
    SWIMMING_SPEED,
    ATTACK_SPEED_INCREASE,
    ATTACK_SPEED_DECREASE;

    public static boolean isWearingSameArmorType(ArmorGearType helmet, ArmorGearType chestplate, ArmorGearType leggings, ArmorGearType boots) {
        if (helmet == null || chestplate == null || leggings == null || boots == null) return false;

        return helmet == chestplate && helmet == leggings && helmet == boots;
    }

    public void clearSetEffect(Player player) {
        MessageUtils.sendCenteredMessage(player, ChatColor.DARK_PURPLE + "Gear Set Effect Deactivated");
        switch (this) {
            case KNOCKBACK_RESISTANCE:
                player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0);
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
        }
    }

    public void applySetEffect(Player player, String setName) {
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Gear Set Effect activated because you are wearing all pieces of "
                + ChatColor.DARK_PURPLE + setName);
        player.sendMessage(ChatColor.DARK_PURPLE + setName + ChatColor.LIGHT_PURPLE + " gives you bonus " + this.name());
        switch (this) {
            case KNOCKBACK_RESISTANCE:
                player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.7);
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
                potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                break;
            case ATTACK_SPEED_DECREASE:
                potionEffect = new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                break;
        }
    }
}
