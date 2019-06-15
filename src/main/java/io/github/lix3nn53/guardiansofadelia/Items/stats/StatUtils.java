package io.github.lix3nn53.guardiansofadelia.Items.stats;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class StatUtils {

    public static Stat getStat(ItemStack item) {
        Material mat = item.getType();
        StatType type = getStatType(mat);

        List<String> lore = item.getItemMeta().getLore();
        if (type.equals(StatType.HEALTH)) {
            if (PersistentDataContainerUtil.hasInteger(item, "health")) {
                int health = PersistentDataContainerUtil.getInteger(item, "health");
                return new StatOneType(health);
            }
        } else if (type.equals(StatType.HYBRID)) {
            int melee = 0;
            int ranged = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    melee = currentValue;
                    break;
                }
            }
            if (PersistentDataContainerUtil.hasInteger(item, "rangedDamage")) {
                ranged = PersistentDataContainerUtil.getInteger(item, "rangedDamage");
            }
            return new StatHybrid(melee, ranged);
        } else if (type.equals(StatType.MAGICAL)) {
            if (PersistentDataContainerUtil.hasInteger(item, "magicDamage")) {
                int magicDamage = PersistentDataContainerUtil.getInteger(item, "magicDamage");
                return new StatOneType(magicDamage);
            }
        } else if (type.equals(StatType.MELEE)) {
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    return new StatOneType(currentValue);
                }
            }
        } else if (type.equals(StatType.PASSIVE)) {
            int fire = 0;
            int water = 0;
            int earth = 0;
            int lightning = 0;
            int wind = 0;

            if (PersistentDataContainerUtil.hasInteger(item, "fire")) {
                fire = PersistentDataContainerUtil.getInteger(item, "fire");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "water")) {
                water = PersistentDataContainerUtil.getInteger(item, "water");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "earth")) {
                earth = PersistentDataContainerUtil.getInteger(item, "earth");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "lightning")) {
                lightning = PersistentDataContainerUtil.getInteger(item, "lightning");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "wind")) {
                wind = PersistentDataContainerUtil.getInteger(item, "wind");
            }

            return new StatPassive(fire, water, earth, lightning, wind);
        } else if (type.equals(StatType.RANGED)) {
            if (PersistentDataContainerUtil.hasInteger(item, "rangedDamage")) {
                int rangedDamage = PersistentDataContainerUtil.getInteger(item, "rangedDamage");
                return new StatOneType(rangedDamage);
            }
        }
        return null;
    }

    public static StatType getStatType(Material mat) {
        StatType type = null;
        if (mat.equals(Material.DIAMOND_AXE) || mat.equals(Material.DIAMOND_PICKAXE) || mat.equals(Material.DIAMOND_SWORD) || mat.equals(Material.DIAMOND_HOE)) {
            type = StatType.MELEE;
        } else if (mat.equals(Material.BOW)) {
            type = StatType.RANGED;
        } else if (mat.equals(Material.TRIDENT)) {
            type = StatType.HYBRID;
        } else if (mat.equals(Material.DIAMOND_SHOVEL)) {
            type = StatType.MAGICAL;
        } else if (mat.equals(Material.DIAMOND_LEGGINGS) ||
                mat.equals(Material.GOLDEN_LEGGINGS) ||
                mat.equals(Material.IRON_LEGGINGS) ||
                mat.equals(Material.CHAINMAIL_LEGGINGS) ||
                mat.equals(Material.LEATHER_LEGGINGS) ||
                mat.equals(Material.DIAMOND_BOOTS) ||
                mat.equals(Material.GOLDEN_BOOTS) ||
                mat.equals(Material.IRON_BOOTS) ||
                mat.equals(Material.CHAINMAIL_BOOTS) ||
                mat.equals(Material.LEATHER_BOOTS) ||
                mat.equals(Material.DIAMOND_HELMET) ||
                mat.equals(Material.GOLDEN_HELMET) ||
                mat.equals(Material.IRON_HELMET) ||
                mat.equals(Material.CHAINMAIL_HELMET) ||
                mat.equals(Material.LEATHER_HELMET) ||
                mat.equals(Material.DIAMOND_CHESTPLATE) ||
                mat.equals(Material.GOLDEN_CHESTPLATE) ||
                mat.equals(Material.IRON_CHESTPLATE) ||
                mat.equals(Material.CHAINMAIL_CHESTPLATE) ||
                mat.equals(Material.LEATHER_CHESTPLATE) ||
                mat.equals(Material.IRON_SWORD)) {
            type = StatType.HEALTH;
        } else if (mat.equals(Material.SHEARS)) {
            type = StatType.PASSIVE;
        }
        return type;
    }

    public static boolean hasStatType(Material mat) {
        return mat.equals(Material.DIAMOND_AXE) || mat.equals(Material.DIAMOND_PICKAXE) || mat.equals(Material.DIAMOND_SWORD) ||
                mat.equals(Material.BOW) || mat.equals(Material.TRIDENT) || mat.equals(Material.DIAMOND_SHOVEL) ||
                mat.equals(Material.DIAMOND_HOE) || mat.equals(Material.DIAMOND_LEGGINGS) ||
                mat.equals(Material.GOLDEN_LEGGINGS) ||
                mat.equals(Material.IRON_LEGGINGS) ||
                mat.equals(Material.CHAINMAIL_LEGGINGS) ||
                mat.equals(Material.LEATHER_LEGGINGS) ||
                mat.equals(Material.DIAMOND_BOOTS) ||
                mat.equals(Material.GOLDEN_BOOTS) ||
                mat.equals(Material.IRON_BOOTS) ||
                mat.equals(Material.CHAINMAIL_BOOTS) ||
                mat.equals(Material.LEATHER_BOOTS) ||
                mat.equals(Material.DIAMOND_HELMET) ||
                mat.equals(Material.GOLDEN_HELMET) ||
                mat.equals(Material.IRON_HELMET) ||
                mat.equals(Material.CHAINMAIL_HELMET) ||
                mat.equals(Material.LEATHER_HELMET) ||
                mat.equals(Material.DIAMOND_CHESTPLATE) ||
                mat.equals(Material.GOLDEN_CHESTPLATE) ||
                mat.equals(Material.IRON_CHESTPLATE) ||
                mat.equals(Material.CHAINMAIL_CHESTPLATE) ||
                mat.equals(Material.LEATHER_CHESTPLATE) ||
                mat.equals(Material.IRON_SWORD) || mat.equals(Material.SHEARS);
    }

    public static void addRandomStats(ItemStack itemStack, GearLevel gearLevel, ItemTier itemTier) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            if (hasStatType(itemStack.getType())) {
                StatPassive statPassive = new StatPassive(gearLevel.getMinStatValue(), gearLevel.getMaxStatValue(), itemTier.getMinNumberOfStats());

                List<String> lore = itemMeta.getLore();
                if (statPassive.getFire() != 0) {
                    lore.add(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+" + statPassive.getFire());
                    PersistentDataContainerUtil.putInteger("fire", statPassive.getFire(), itemStack);
                }
                if (statPassive.getWater() != 0) {
                    lore.add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+" + statPassive.getWater());
                    PersistentDataContainerUtil.putInteger("water", statPassive.getWater(), itemStack);
                }
                if (statPassive.getEarth() != 0) {
                    lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+" + statPassive.getEarth());
                    PersistentDataContainerUtil.putInteger("earth", statPassive.getEarth(), itemStack);
                }
                if (statPassive.getLightning() != 0) {
                    lore.add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+" + statPassive.getLightning());
                    PersistentDataContainerUtil.putInteger("lightning", statPassive.getLightning(), itemStack);
                }
                if (statPassive.getWind() != 0) {
                    lore.add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Wind: " + ChatColor.GRAY + "+" + statPassive.getWind());
                    PersistentDataContainerUtil.putInteger("wind", statPassive.getWind(), itemStack);
                }
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
            }
        }
    }
}
