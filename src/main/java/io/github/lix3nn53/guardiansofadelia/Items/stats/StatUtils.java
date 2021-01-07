package io.github.lix3nn53.guardiansofadelia.Items.stats;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.HelmetSkin;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
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
            int strength = 0;
            int spirit = 0;
            int endurance = 0;
            int intelligence = 0;
            int dexterity = 0;

            if (PersistentDataContainerUtil.hasInteger(item, "strength")) {
                strength = PersistentDataContainerUtil.getInteger(item, "strength");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "spirit")) {
                spirit = PersistentDataContainerUtil.getInteger(item, "spirit");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "endurance")) {
                endurance = PersistentDataContainerUtil.getInteger(item, "endurance");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "intelligence")) {
                intelligence = PersistentDataContainerUtil.getInteger(item, "intelligence");
            }
            if (PersistentDataContainerUtil.hasInteger(item, "dexterity")) {
                dexterity = PersistentDataContainerUtil.getInteger(item, "dexterity");
            }

            return new StatPassive(strength, spirit, endurance, intelligence, dexterity);
        }
        return new StatOneType(0);
    }

    public static StatType getStatType(Material mat) {
        StatType type = null;
        if (mat.equals(Material.DIAMOND_AXE) || //great sword
                mat.equals(Material.NETHERITE_AXE) || //battle axe
                mat.equals(Material.NETHERITE_HOE) || //dagger
                mat.equals(Material.NETHERITE_PICKAXE) || //war hammer
                mat.equals(Material.NETHERITE_SWORD) //sword
        ) {
            type = StatType.MELEE;
        } else if (mat.equals(Material.TRIDENT) || mat.equals(Material.BOW) || mat.equals(Material.CROSSBOW)) {
            type = StatType.HYBRID;
        } else if (mat.equals(Material.DIAMOND_SHOVEL) //wand
                || mat.equals(Material.NETHERITE_SHOVEL) //staff
        ) {
            type = StatType.MAGICAL;
        } else if (mat.equals(Material.NETHERITE_LEGGINGS) ||
                mat.equals(Material.DIAMOND_LEGGINGS) ||
                mat.equals(Material.GOLDEN_LEGGINGS) ||
                mat.equals(Material.IRON_LEGGINGS) ||
                mat.equals(Material.CHAINMAIL_LEGGINGS) ||
                mat.equals(Material.LEATHER_LEGGINGS) ||
                mat.equals(Material.NETHERITE_BOOTS) ||
                mat.equals(Material.DIAMOND_BOOTS) ||
                mat.equals(Material.GOLDEN_BOOTS) ||
                mat.equals(Material.IRON_BOOTS) ||
                mat.equals(Material.CHAINMAIL_BOOTS) ||
                mat.equals(Material.LEATHER_BOOTS) ||
                mat.equals(Material.NETHERITE_HELMET) ||
                mat.equals(Material.DIAMOND_HELMET) ||
                mat.equals(Material.GOLDEN_HELMET) ||
                mat.equals(Material.IRON_HELMET) ||
                mat.equals(Material.CHAINMAIL_HELMET) ||
                mat.equals(Material.LEATHER_HELMET) ||
                mat.equals(Material.NETHERITE_CHESTPLATE) ||
                mat.equals(Material.DIAMOND_CHESTPLATE) ||
                mat.equals(Material.GOLDEN_CHESTPLATE) ||
                mat.equals(Material.IRON_CHESTPLATE) ||
                mat.equals(Material.CHAINMAIL_CHESTPLATE) ||
                mat.equals(Material.LEATHER_CHESTPLATE) ||
                mat.equals(HelmetSkin.getHelmetMaterial()) ||
                mat.equals(Material.SHIELD)
        ) {
            type = StatType.HEALTH;
        } else if (mat.equals(Material.SHEARS)) {
            type = StatType.PASSIVE;
        }
        return type;
    }

    public static boolean hasStatType(Material mat) {
        return mat.equals(Material.DIAMOND_AXE) || //great sword
                mat.equals(Material.NETHERITE_AXE) || //battle axe
                mat.equals(Material.NETHERITE_HOE) || //dagger
                mat.equals(Material.NETHERITE_PICKAXE) || //war hammer
                mat.equals(Material.NETHERITE_SWORD) || //sword
                mat.equals(Material.BOW) || mat.equals(Material.CROSSBOW) || mat.equals(Material.TRIDENT) ||
                mat.equals(Material.DIAMOND_SHOVEL) || //wand
                mat.equals(Material.NETHERITE_SHOVEL) || //staff
                mat.equals(Material.NETHERITE_LEGGINGS) ||
                mat.equals(Material.DIAMOND_LEGGINGS) ||
                mat.equals(Material.GOLDEN_LEGGINGS) ||
                mat.equals(Material.IRON_LEGGINGS) ||
                mat.equals(Material.CHAINMAIL_LEGGINGS) ||
                mat.equals(Material.LEATHER_LEGGINGS) ||
                mat.equals(Material.NETHERITE_BOOTS) ||
                mat.equals(Material.DIAMOND_BOOTS) ||
                mat.equals(Material.GOLDEN_BOOTS) ||
                mat.equals(Material.IRON_BOOTS) ||
                mat.equals(Material.CHAINMAIL_BOOTS) ||
                mat.equals(Material.LEATHER_BOOTS) ||
                mat.equals(Material.NETHERITE_HELMET) ||
                mat.equals(Material.DIAMOND_HELMET) ||
                mat.equals(Material.GOLDEN_HELMET) ||
                mat.equals(Material.IRON_HELMET) ||
                mat.equals(Material.CHAINMAIL_HELMET) ||
                mat.equals(Material.LEATHER_HELMET) ||
                mat.equals(Material.NETHERITE_CHESTPLATE) ||
                mat.equals(Material.DIAMOND_CHESTPLATE) ||
                mat.equals(Material.GOLDEN_CHESTPLATE) ||
                mat.equals(Material.IRON_CHESTPLATE) ||
                mat.equals(Material.CHAINMAIL_CHESTPLATE) ||
                mat.equals(Material.LEATHER_CHESTPLATE) ||
                mat.equals(HelmetSkin.getHelmetMaterial()) ||
                mat.equals(Material.SHIELD) ||
                mat.equals(Material.SHEARS);
    }

    public static void addRandomPassiveStats(ItemStack itemStack, int gearLevel, ItemTier itemTier) {
        Material type = itemStack.getType();
        if (hasStatType(type)) {
            int minNumberOfStats = itemTier.getMinNumberOfStatsNormal();
            if (type.equals(Material.SHEARS)) { //passive item
                minNumberOfStats = itemTier.getMinNumberOfStatsPassive();
            }

            StatPassive statPassive = new StatPassive(GearLevel.getMinStatValue(gearLevel), GearLevel.getMaxStatValue(gearLevel), minNumberOfStats);

            //add persistent data before getting itemMeta so we don't lost them
            if (statPassive.getStrength() != 0) {
                PersistentDataContainerUtil.putInteger("strength", statPassive.getStrength(), itemStack);
            }
            if (statPassive.getSpirit() != 0) {
                PersistentDataContainerUtil.putInteger("spirit", statPassive.getSpirit(), itemStack);
            }
            if (statPassive.getEndurance() != 0) {
                PersistentDataContainerUtil.putInteger("endurance", statPassive.getEndurance(), itemStack);
            }
            if (statPassive.getIntelligence() != 0) {
                PersistentDataContainerUtil.putInteger("intelligence", statPassive.getIntelligence(), itemStack);
            }
            if (statPassive.getDexterity() != 0) {
                PersistentDataContainerUtil.putInteger("dexterity", statPassive.getDexterity(), itemStack);
            }

            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();

            int i = lore.indexOf(itemTier.getTierString());

            if (statPassive.getStrength() != 0) {
                lore.add(i, ChatColor.RED + "☄ " + ChatColor.RED + "Strength: " + ChatColor.GRAY + "+" + statPassive.getStrength());
                i++;
            }
            if (statPassive.getSpirit() != 0) {
                lore.add(i, ChatColor.BLUE + "◎ " + ChatColor.BLUE + "Spirit: " + ChatColor.GRAY + "+" + statPassive.getSpirit());
                i++;
            }
            if (statPassive.getEndurance() != 0) {
                lore.add(i, ChatColor.DARK_GREEN + "₪ " + ChatColor.DARK_GREEN + "Endurance: " + ChatColor.GRAY + "+" + statPassive.getEndurance());
                i++;
            }
            if (statPassive.getIntelligence() != 0) {
                lore.add(i, ChatColor.AQUA + "ϟ " + ChatColor.AQUA + "Intelligence: " + ChatColor.GRAY + "+" + statPassive.getIntelligence());
                i++;
            }
            if (statPassive.getDexterity() != 0) {
                lore.add(i, ChatColor.WHITE + "๑ " + ChatColor.WHITE + "Dexterity: " + ChatColor.GRAY + "+" + statPassive.getDexterity());
                i++;
            }
            lore.add(i, "");

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
    }

    public static boolean doesCharacterMeetRequirements(ItemStack itemStack, Player player, String rpgClassStr) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");
            if (player.getLevel() < reqLevel) {
                player.sendMessage("Required level for this item is " + reqLevel);
                return false;
            }
        }

        if (PersistentDataContainerUtil.hasString(itemStack, "gearType")) {
            String gearTypeString = PersistentDataContainerUtil.getString(itemStack, "gearType");

            for (WeaponGearType c : WeaponGearType.values()) {
                if (c.name().equals(gearTypeString)) {
                    RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                    List<WeaponGearType> weaponGearTypes = rpgClass.getWeaponGearTypes();
                    if (!weaponGearTypes.contains(c)) {
                        player.sendMessage(ChatColor.RED + "Your class can't use " + c.getDisplayName());
                        return false;
                    }
                }
            }

            for (ArmorGearType c : ArmorGearType.values()) {
                if (c.name().equals(gearTypeString)) {
                    RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                    List<ArmorGearType> armorGearTypes = rpgClass.getArmorGearTypes();
                    if (!armorGearTypes.contains(c)) {
                        player.sendMessage(ChatColor.RED + "Your class can't use " + c.getDisplayName());
                        return false;
                    }
                }
            }

            for (ShieldGearType c : ShieldGearType.values()) {
                if (c.name().equals(gearTypeString)) {
                    RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                    List<ShieldGearType> shieldGearTypes = rpgClass.getShieldGearTypes();
                    if (!shieldGearTypes.contains(c)) {
                        player.sendMessage(ChatColor.RED + "Your class can't use " + c.getDisplayName());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static double getDefenseReduction(int totalDefense) {
        return (1 - (totalDefense / (totalDefense + 2000.0))); //damage reduction formula, if totalDefense equals second paramater reduction is %50
    }
}
