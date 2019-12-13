package io.github.lix3nn53.guardiansofadelia.Items.stats;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class StatUtils {

    public static Stat getStat(ItemStack item) {
        Material mat = item.getType();
        StatType type = getStatType(mat);

        if (type.equals(StatType.HEALTH)) {
            if (PersistentDataContainerUtil.hasInteger(item, "health")) {
                int health = PersistentDataContainerUtil.getInteger(item, "health");
                return new StatOneType(health);
            }
        } else if (type.equals(StatType.HYBRID)) {
            int melee = 0;
            int ranged = 0;
            if (PersistentDataContainerUtil.hasInteger(item, "meleeDamage")) {
                melee = PersistentDataContainerUtil.getInteger(item, "meleeDamage");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "rangedDamage")) {
                ranged = PersistentDataContainerUtil.getInteger(item, "rangedDamage");
            }
            return new StatHybrid(melee, ranged);
        } else if (type.equals(StatType.MAGICAL)) {
            int melee = 0;
            int magical = 0;
            if (PersistentDataContainerUtil.hasInteger(item, "meleeDamage")) {
                melee = PersistentDataContainerUtil.getInteger(item, "meleeDamage");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "magicDamage")) {
                magical = PersistentDataContainerUtil.getInteger(item, "magicDamage");
            }
            return new StatMagical(melee, magical);
        } else if (type.equals(StatType.MELEE)) {
            if (PersistentDataContainerUtil.hasInteger(item, "meleeDamage")) {
                int damage = PersistentDataContainerUtil.getInteger(item, "meleeDamage");
                return new StatOneType(damage);
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
        }
        return null;
    }

    public static StatType getStatType(Material mat) {
        StatType type = null;
        if (mat.equals(Material.DIAMOND_AXE) || mat.equals(Material.DIAMOND_PICKAXE) || mat.equals(Material.DIAMOND_SWORD) || mat.equals(Material.DIAMOND_HOE)) {
            type = StatType.MELEE;
        } else if (mat.equals(Material.TRIDENT) || mat.equals(Material.BOW) || mat.equals(Material.CROSSBOW)) {
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
                mat.equals(Material.BOW) || mat.equals(Material.CROSSBOW) || mat.equals(Material.TRIDENT) || mat.equals(Material.DIAMOND_SHOVEL) ||
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

    public static void addRandomPassiveStats(ItemStack itemStack, GearLevel gearLevel, ItemTier itemTier) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasLore()) {
            if (hasStatType(itemStack.getType())) {
                StatPassive statPassive = new StatPassive(gearLevel.getMinStatValue(), gearLevel.getMaxStatValue(), itemTier.getMinNumberOfStats());

                List<String> lore = itemMeta.getLore();
                if (statPassive.getFire() != 0) {
                    lore.add(ChatColor.RED + "☄ " + ChatColor.RED + "Fire: " + ChatColor.GRAY + "+" + statPassive.getFire());
                    PersistentDataContainerUtil.putInteger("fire", statPassive.getFire(), itemStack);
                }
                if (statPassive.getWater() != 0) {
                    lore.add(ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Water: " + ChatColor.GRAY + "+" + statPassive.getWater());
                    PersistentDataContainerUtil.putInteger("water", statPassive.getWater(), itemStack);
                }
                if (statPassive.getEarth() != 0) {
                    lore.add(ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Earth: " + ChatColor.GRAY + "+" + statPassive.getEarth());
                    PersistentDataContainerUtil.putInteger("earth", statPassive.getEarth(), itemStack);
                }
                if (statPassive.getLightning() != 0) {
                    lore.add(ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Lightning: " + ChatColor.GRAY + "+" + statPassive.getLightning());
                    PersistentDataContainerUtil.putInteger("lightning", statPassive.getLightning(), itemStack);
                }
                if (statPassive.getWind() != 0) {
                    lore.add(ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Wind: " + ChatColor.GRAY + "+" + statPassive.getWind());
                    PersistentDataContainerUtil.putInteger("wind", statPassive.getWind(), itemStack);
                }
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
            }
        }
    }

    public static boolean doesCharacterMeetRequirements(ItemStack itemStack, Player player, RPGClass rpgClass) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");
            if (player.getLevel() < reqLevel) {
                player.sendMessage("Required level for this item is " + reqLevel);
                return false;
            }
        }

        if (PersistentDataContainerUtil.hasString(itemStack, "reqClass")) {
            String reqClassString = PersistentDataContainerUtil.getString(itemStack, "reqClass");
            RPGClass reqClass = RPGClass.valueOf(reqClassString);
            if (!rpgClass.equals(reqClass)) {
                player.sendMessage("Required class for this item is " + reqClass.getClassString());
                return false;
            }
        }
        return true;
    }

    public static double getDefenseReduction(int totalDefense) {
        return (1 - (totalDefense / (totalDefense + 2000.0))); //damage reduction formula, if totalDefense equals second paramater reduction is %50
    }
}
