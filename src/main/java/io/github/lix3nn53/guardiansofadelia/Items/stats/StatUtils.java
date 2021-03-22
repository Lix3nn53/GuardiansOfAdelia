package io.github.lix3nn53.guardiansofadelia.Items.stats;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.HelmetSkin;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class StatUtils {

    public static Stat getStat(ItemStack item) {
        Material mat = item.getType();
        GearStatType type = getStatType(mat);

        if (type.equals(GearStatType.ARMOR_GEAR)) {
            if (PersistentDataContainerUtil.hasInteger(item, "health")) {
                int health = PersistentDataContainerUtil.getInteger(item, "health");
                return new StatOneType(health);
            }
        } else if (type.equals(GearStatType.WEAPON_GEAR)) {
            if (PersistentDataContainerUtil.hasInteger(item, "elementDamage")) {
                int damage = PersistentDataContainerUtil.getInteger(item, "elementDamage");
                return new StatOneType(damage);
            }
        } else if (type.equals(GearStatType.PASSIVE_GEAR)) {
            HashMap<AttributeType, Integer> attributeTypeToValue = new HashMap<>();
            for (AttributeType attributeType : AttributeType.values()) {
                if (PersistentDataContainerUtil.hasInteger(item, attributeType.name())) {
                    int value = PersistentDataContainerUtil.getInteger(item, attributeType.name());
                    attributeTypeToValue.put(attributeType, value);
                }
            }

            HashMap<ElementType, Integer> elementTypeToValue = new HashMap<>();
            for (ElementType elementType : ElementType.values()) {
                if (PersistentDataContainerUtil.hasInteger(item, elementType.name())) {
                    int value = PersistentDataContainerUtil.getInteger(item, elementType.name());
                    elementTypeToValue.put(elementType, value);
                }
            }

            return new StatPassive(attributeTypeToValue, elementTypeToValue);
        }

        return new StatOneType(0);
    }

    public static GearStatType getStatType(Material mat) {
        GearStatType type = null;
        if (mat.equals(Material.DIAMOND_AXE) || //great sword
                mat.equals(Material.NETHERITE_AXE) || //battle axe
                mat.equals(Material.NETHERITE_HOE) || //dagger
                mat.equals(Material.NETHERITE_PICKAXE) || //war hammer
                mat.equals(Material.NETHERITE_SWORD) || //sword
                mat.equals(Material.TRIDENT) || //spear
                mat.equals(Material.BOW) || //bow
                mat.equals(Material.CROSSBOW) || //crossbow
                mat.equals(Material.DIAMOND_SHOVEL) //wand
                || mat.equals(Material.NETHERITE_SHOVEL) //staff
        ) {
            type = GearStatType.WEAPON_GEAR;
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
            type = GearStatType.ARMOR_GEAR;
        } else if (mat.equals(Material.SHEARS)) {
            type = GearStatType.PASSIVE_GEAR;
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
            int minNumberOfElements = itemTier.getMinNumberOfElements(false);
            int minNumberOfAttributes = itemTier.getMinNumberOfAttributes(false);
            int minStatValue = GearLevel.getMinStatValue(gearLevel, false);
            int maxStatValue = GearLevel.getMaxStatValue(gearLevel, false);
            if (type.equals(Material.SHEARS)) { //passive item
                minNumberOfElements = itemTier.getMinNumberOfElements(true);
                minNumberOfAttributes = itemTier.getMinNumberOfAttributes(true);
                minStatValue = GearLevel.getMinStatValue(gearLevel, true);
                maxStatValue = GearLevel.getMaxStatValue(gearLevel, true);
            }


            StatPassive statPassive = new StatPassive(minStatValue, maxStatValue, minNumberOfAttributes, minStatValue, maxStatValue, minNumberOfElements);

            //add persistent data before getting itemMeta so we don't lost them
            for (AttributeType attributeType : AttributeType.values()) {
                if (statPassive.getAttributeValue(attributeType) != 0) {
                    PersistentDataContainerUtil.putInteger(attributeType.name(), statPassive.getAttributeValue(attributeType), itemStack);
                }
            }
            for (ElementType elementType : ElementType.values()) {
                if (statPassive.getElementValue(elementType) != 0) {
                    PersistentDataContainerUtil.putInteger(elementType.name(), statPassive.getElementValue(elementType), itemStack);
                }
            }

            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = itemMeta.getLore();

            int i = lore.indexOf(itemTier.getTierString());

            for (AttributeType attributeType : AttributeType.values()) {
                if (statPassive.getAttributeValue(attributeType) != 0) {
                    lore.add(i, attributeType.getCustomName() + ": " + ChatColor.GRAY + "+" + statPassive.getAttributeValue(attributeType));
                    i++;
                }
            }
            lore.add(i, "");
            for (ElementType elementType : ElementType.values()) {
                if (statPassive.getElementValue(elementType) != 0) {
                    lore.add(i, elementType.getFullName() + ": " + ChatColor.GRAY + "+" + statPassive.getElementValue(elementType));
                    i++;
                }
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
