package io.github.lix3nn53.guardiansofadelia.Items.stats;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class StatUtils {

    public static Stat getStat(ItemStack item) {
        Material mat = item.getType();
        StatType type = getStatType(mat);

        List<String> lore = item.getItemMeta().getLore();
        if (type.equals(StatType.HEALTH)) {
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.DARK_GREEN + "❤ Health: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    return new StatOneType(currentValue);
                }
            }
        } else if (type.equals(StatType.HYBRID)) {
            int changeCounter = 0;
            int melee = 0;
            int ranged = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    melee = currentValue;
                    changeCounter++;
                } else if (line.contains(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    ranged = currentValue;
                    changeCounter++;
                }
                if (changeCounter == 2) {
                    return new StatHybrid(melee, ranged);
                }
            }
        } else if (type.equals(StatType.MAGICAL)) {
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    return new StatOneType(currentValue);
                }
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
            int changeCounter = 0;
            int fire = 0;
            int water = 0;
            int earth = 0;
            int lightning = 0;
            int air = 0;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.RED + "☄ " + ChatColor.GRAY + "Fire: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    fire = currentValue;
                    changeCounter++;
                } else if (line.contains(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    water = currentValue;
                    changeCounter++;
                } else if (line.contains(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    earth = currentValue;
                    changeCounter++;
                } else if (line.contains(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    lightning = currentValue;
                    changeCounter++;
                } else if (line.contains(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Air: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Air: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    air = currentValue;
                    changeCounter++;
                }
                if (changeCounter == 5) {
                    break;
                }
            }
            return new StatPassive(fire, water, earth, lightning, air);
        } else if (type.equals(StatType.RANGED)) {
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line.contains(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+")) {
                    String currentValueString = line.replace(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "+", "");
                    int currentValue = Integer.parseInt(currentValueString);
                    return new StatOneType(currentValue);
                }
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
}
