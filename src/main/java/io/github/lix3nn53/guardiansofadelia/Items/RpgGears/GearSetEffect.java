package io.github.lix3nn53.guardiansofadelia.Items.RpgGears;

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

    public void applySetEffect(Player player) {
        player.sendMessage("Gear set effect activated:");
        switch (this) {
            case KNOCKBACK_RESISTANCE:
                player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.7);
                player.sendMessage("knockback resistance");
                break;
            case SLOW_FALLING:
                PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                player.sendMessage("SLOW_FALLING");
                break;
            case SWIMMING_SPEED:
                potionEffect = new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                player.sendMessage("SWIMMING_SPEED");
                break;
            case ATTACK_SPEED_INCREASE:
                potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                player.sendMessage("ATTACK_SPEED_INCREASE");
                break;
            case ATTACK_SPEED_DECREASE:
                potionEffect = new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1);
                player.addPotionEffect(potionEffect);
                player.sendMessage("ATTACK_SPEED_DECREASE");
                break;
        }
    }

    public void clearSetEffect(Player player) {
        player.sendMessage("Gear set effect deactivated:");
        switch (this) {
            case KNOCKBACK_RESISTANCE:
                player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0);
                player.sendMessage("knockback resistance");
                break;
            case SLOW_FALLING:
                player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                player.sendMessage("SLOW_FALLING");
                break;
            case SWIMMING_SPEED:
                player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
                player.sendMessage("SLOW_FALLING");
                player.sendMessage("SWIMMING_SPEED");
                break;
            case ATTACK_SPEED_INCREASE:
                player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                player.sendMessage("SLOW_FALLING");
                player.sendMessage("ATTACK_SPEED_INCREASE");
                break;
            case ATTACK_SPEED_DECREASE:
                player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                player.sendMessage("SLOW_FALLING");
                player.sendMessage("ATTACK_SPEED_DECREASE");
                break;
        }
    }
}
