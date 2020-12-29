package io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
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

    public static String getCustomSet(ItemStack itemStack) {
        if (itemStack == null) return "NULL";

        if (PersistentDataContainerUtil.hasString(itemStack, "gearSet")) {
            return PersistentDataContainerUtil.getString(itemStack, "gearSet");
        }

        return "NULL";
    }

    public void applySetEffect(Player player) {
        player.sendMessage(this.toString());
        switch (this) {
            case KNOCKBACK_RESISTANCE:
                Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE)).setBaseValue(0.7);
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

    @Override
    public String toString() {
        switch (this) {
            case EMPTY:
                return "Empty";
            case KNOCKBACK_RESISTANCE:
                return ChatColor.GRAY + "Knockback Resist";
            case CRITICAL_DAMAGE:
                return ChatColor.GRAY + "Critical Damage";
            case CRITICAL_CHANCE:
                return ChatColor.GRAY + "Critical Chance";
            case MANA_REGEN:
                return ChatColor.GRAY + "Mana Regen";
            case SLOW_FALLING:
                return ChatColor.GRAY + "Slow Fallling";
            case SWIMMING_SPEED:
                return ChatColor.GRAY + "Swimming Speed";
            case ATTACK_SPEED_INCREASE:
                return ChatColor.GRAY + "Attack Speed++";
            case ATTACK_SPEED_DECREASE:
                return ChatColor.GRAY + "Attack Speed" + ChatColor.RED + "--";
        }

        final StringBuilder sb = new StringBuilder("GearSetEffect{");
        sb.append('}');
        return sb.toString();
    }
}
