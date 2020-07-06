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

    public void applySetEffect(Player player, String setName) {
        MessageUtils.sendCenteredMessage(player, ChatColor.LIGHT_PURPLE + "Gear Set Effect Activated: " + setName);
        switch (this) {
            case KNOCKBACK_RESISTANCE:
                player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.7);
                MessageUtils.sendCenteredMessage(player, "Knockback resistance");
                break;
            case SLOW_FALLING:
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                MessageUtils.sendCenteredMessage(player, "Slow falling");
                break;
            case SWIMMING_SPEED:
                potionEffect = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                MessageUtils.sendCenteredMessage(player, "Swimming speed increase");
                break;
            case ATTACK_SPEED_INCREASE:
                potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                MessageUtils.sendCenteredMessage(player, "Attack speed increase");
                break;
            case ATTACK_SPEED_DECREASE:
                potionEffect = new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                MessageUtils.sendCenteredMessage(player, "Attack speed decrease");
                break;
        }
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
}
