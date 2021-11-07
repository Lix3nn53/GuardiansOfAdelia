package io.github.lix3nn53.guardiansofadelia.items.stats;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.HelmetSkin;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class StatUtils {

    // Values to use while generating items
    private static final double ITEM_HEALTH = 5;
    private static final double ITEM_DEFENSE = 5;
    private static final double ITEM_DAMAGE = 2;

    // Formula
    private static final double HALF_REDUCTION_DEFENSE = 2000.0;

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
        if (WeaponGearType.fromMaterial(mat) != null) {
            type = GearStatType.WEAPON_GEAR;
        } else if (ArmorGearType.fromMaterial(mat) != null ||
                mat.equals(HelmetSkin.getHelmetMaterial()) || // Helmet skin premium
                ShieldGearType.fromMaterial(mat) != null
        ) {
            type = GearStatType.ARMOR_GEAR;
        } else if (mat.equals(Material.SHEARS)) {
            type = GearStatType.PASSIVE_GEAR;
        }
        return type;
    }

    public static boolean hasStatType(Material mat) {
        if (mat == null) return false;

        return WeaponGearType.fromMaterial(mat) != null ||
                ArmorGearType.fromMaterial(mat) != null ||
                ShieldGearType.fromMaterial(mat) != null ||
                mat.equals(HelmetSkin.getHelmetMaterial()) ||
                mat.equals(Material.SHEARS);
    }

    public static void addRandomPassiveStats(ItemStack itemStack, GearLevel gearLevel, ItemTier itemTier) {
        Material type = itemStack.getType();
        if (hasStatType(type)) {
            int minNumberOfElements = itemTier.getMinNumberOfElements(false);
            int minNumberOfAttributes = itemTier.getMinNumberOfAttributes(false);
            int minAttrValue = gearLevel.getMinStatValue(false, false);
            int maxAttrValue = gearLevel.getMaxStatValue(false, false);
            int minElemValue = gearLevel.getMinStatValue(false, true);
            int maxElemValue = gearLevel.getMaxStatValue(false, true);
            if (type.equals(Material.SHEARS)) { //passive item
                minNumberOfElements = itemTier.getMinNumberOfElements(true);
                minNumberOfAttributes = itemTier.getMinNumberOfAttributes(true);
                minAttrValue = gearLevel.getMinStatValue(true, false);
                maxAttrValue = gearLevel.getMaxStatValue(true, false);
                minElemValue = gearLevel.getMinStatValue(true, true);
                maxElemValue = gearLevel.getMaxStatValue(true, true);
            }

            StatPassive statPassive = new StatPassive(minAttrValue, maxAttrValue, minNumberOfAttributes, minElemValue, maxElemValue, minNumberOfElements);

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

            String tierString = itemTier.getTierString();
            int i = lore.indexOf(tierString);

            boolean hasElements = statPassive.isEmpty(false, true);

            if (!statPassive.isEmpty(true, false)) {
                for (AttributeType attributeType : AttributeType.values()) {
                    if (statPassive.getAttributeValue(attributeType) != 0) {
                        lore.add(i, attributeType.getCustomName() + ": " + ChatPalette.GRAY + "+" + attributeType.getIncrementLore(statPassive.getAttributeValue(attributeType)));
                        i++;
                    }
                }

                if (!hasElements) {
                    lore.add(i, "");
                    i++;
                }
            }

            if (!hasElements) {
                for (ElementType elementType : ElementType.values()) {
                    if (statPassive.getElementValue(elementType) != 0) {
                        lore.add(i, elementType.getFullName() + ": " + ChatPalette.GRAY + "+" + statPassive.getElementValue(elementType));
                        i++;
                    }
                }
                lore.add(i, "");
            }

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
    }

    public static boolean doesCharacterMeetRequirements(ItemStack itemStack, Player player, String rpgClassStr) {
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            int reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");
            if (player.getLevel() < reqLevel) {
                player.sendMessage(ChatPalette.RED + "Required level for this item is " + reqLevel);
                return false;
            }
        }

        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);

        Material type = itemStack.getType();
        WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);
        if (weaponGearType != null) {
            List<WeaponGearType> weaponGearTypes = rpgClass.getWeaponGearTypes();
            if (!weaponGearTypes.contains(weaponGearType)) {
                player.sendMessage(ChatPalette.RED + "Your class can't use " + weaponGearType.getDisplayName());
                return false;
            }

            if (weaponGearType.isTwoHanded()) {
                ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                if (StatUtils.hasStatType(itemInOffHand.getType())) {
                    player.sendMessage(ChatPalette.RED + "You can't dual wield " + weaponGearType);
                    return false;
                }
            }
        }

        ArmorGearType armorGearType = ArmorGearType.fromMaterial(type);
        if (armorGearType != null) {
            List<ArmorGearType> armorGearTypes = rpgClass.getArmorGearTypes();
            if (!armorGearTypes.contains(armorGearType)) {
                player.sendMessage(ChatPalette.RED + "Your class can't use " + armorGearType.getDisplayName());
                return false;
            }
        }

        ShieldGearType shieldGearType = ShieldGearType.fromMaterial(type);
        if (shieldGearType != null) {
            List<ShieldGearType> shieldGearTypes = rpgClass.getShieldGearTypes();
            if (!shieldGearTypes.contains(shieldGearType)) {
                player.sendMessage(ChatPalette.RED + "Your class can't use " + shieldGearType.getDisplayName());
                return false;
            }
        }

        return true;
    }

    public static double getDefenseReduction(double totalDefense) {
        return (1 - (totalDefense / (totalDefense + HALF_REDUCTION_DEFENSE))); //damage reduction formula, if totalDefense equals second paramater reduction is %50
    }

    public static int getDamageItem(int requiredLevel) {
        return (int) (10 + ITEM_DAMAGE * Math.pow(requiredLevel, 2) / 20 + 0.5);
    }

    public static int getHealthItem(int requiredLevel) {
        return (int) (10 + ITEM_HEALTH * Math.pow(requiredLevel, 2) / 20 + 0.5);
    }

    public static int getDefenseItem(int requiredLevel) {
        return (int) (10 + ITEM_DEFENSE * Math.pow(requiredLevel, 2) / 20 + 0.5);
    }
}
