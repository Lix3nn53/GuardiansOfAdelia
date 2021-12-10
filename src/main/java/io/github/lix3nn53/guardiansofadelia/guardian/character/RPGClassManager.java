package io.github.lix3nn53.guardiansofadelia.guardian.character;

import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RPGClassManager {

    private static final HashMap<String, RPGClass> rpgClassMap = new HashMap<>();

    private static final HashMap<Integer, Integer> classTierToRequiredLevel = new HashMap<>();
    public static int HIGHEST_CLASS_TIER = 1;
    private static String startingClass;

    public static void addClass(String className, RPGClass rpgClass) {
        int tier = rpgClass.getTier();
        if (tier > HIGHEST_CLASS_TIER) HIGHEST_CLASS_TIER = tier;

        rpgClassMap.put(className.toUpperCase(), rpgClass);
    }

    public static boolean hasClass(String className) {
        return rpgClassMap.containsKey(className.toUpperCase());
    }

    public static RPGClass getClass(String className) {
        return rpgClassMap.get(className.toUpperCase());
    }

    public static List<RPGClass> getClassesAtTier(int tier) {
        List<RPGClass> classes = new ArrayList<>();

        for (String classStr : rpgClassMap.keySet()) {
            RPGClass rpgClass = rpgClassMap.get(classStr);

            int tierOfCurrent = rpgClass.getTier();

            if (tierOfCurrent == tier) {
                classes.add(rpgClass);
            }
        }

        return classes;
    }

    public static void clearClasses() {
        rpgClassMap.clear();
    }

    public static int getRequiredLevelForClassTier(int classTier) {
        if (classTierToRequiredLevel.containsKey(classTier)) {
            return classTierToRequiredLevel.get(classTier);
        }

        return 9999;
    }

    public static void setRequiredLevelForClassTier(int classTier, int level) {
        classTierToRequiredLevel.put(classTier, level);
    }

    public static String getStartingClass() {
        return startingClass;
    }

    public static void setStartingClass(String startingClass) {
        RPGClassManager.startingClass = startingClass;
    }

    public static ItemStack getPersonalIcon(RPGClass rpgClass, int highestUnlockedClassTier, RPGCharacter rpgCharacter) {
        ItemStack itemStack = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setCustomModelData(rpgClass.getClassIconCustomModelData());
        String classString = rpgClass.getClassString();
        itemMeta.setDisplayName(classString.toUpperCase());

        List<String> description = rpgClass.getDescription();
        List<WeaponGearType> weaponGearTypes = rpgClass.getWeaponGearTypes();
        List<ArmorGearType> armorGearTypes = rpgClass.getArmorGearTypes();
        List<ShieldGearType> shieldGearTypes = rpgClass.getShieldGearTypes();
        HashMap<AttributeType, Integer> attributeTiers = rpgClass.getAttributeTiers();

        List<String> lore = new ArrayList<>(description);
        int classTier = rpgClass.getTier();
        lore.add(ChatPalette.RED + "Tier: " + ChatPalette.GRAY + classTier);
        lore.add("");

        lore.add(ChatPalette.GREEN_DARK + "Attributes");
        StringBuilder bonusAttributes = new StringBuilder(" ");
        for (AttributeType attributeType : AttributeType.values()) {
            int bonus = attributeTiers.get(attributeType);
            String shortName = attributeType.getShortName();
            bonusAttributes.append(shortName).append("=").append(bonus).append(" ");
        }
        lore.add(bonusAttributes.toString());

        lore.add(ChatPalette.RED + "Weapons");
        for (WeaponGearType type : weaponGearTypes) {
            lore.add("  - " + type.getDisplayName());
        }
        lore.add(ChatPalette.BLUE_LIGHT + "Armors");
        for (ArmorGearType type : armorGearTypes) {
            lore.add("  - " + type.getDisplayName());
        }
        if (!shieldGearTypes.isEmpty()) {
            lore.add(ChatPalette.BLUE + "Shields");
            for (ShieldGearType type : shieldGearTypes) {
                lore.add("  - " + type.getDisplayName());
            }
        }

        lore.add("");

        String valueStr = rpgClass.getClassStringNoColor();

        if (classTier <= highestUnlockedClassTier) {
            String rpgClassStr = rpgCharacter.getRpgClassStr();
            if (valueStr.equalsIgnoreCase(rpgClassStr)) {
                lore.add(ChatPalette.PURPLE_LIGHT + "This is your current class");
            } else {
                lore.add(ChatPalette.GREEN_DARK + "Click to change to this class!");
            }

            RPGClassStats rpgClassStats = rpgCharacter.getRPGClassStats(valueStr);
            int totalExperience = rpgClassStats.getTotalExperience();
            int level = RPGClassExperienceManager.getLevel(totalExperience);
            lore.add(ChatPalette.GOLD + "Class Level: " + ChatPalette.WHITE + level);
            int exp = RPGClassExperienceManager.getCurrentExperience(totalExperience, level);
            int expReq = RPGClassExperienceManager.getRequiredExperience(level);
            lore.add(ChatPalette.YELLOW + "Class Experience: " + ChatPalette.GRAY + exp + "/" + expReq);
        } else {
            lore.add(ChatPalette.RED + "You haven't unlocked classes at this tier yet.");
            lore.add(ChatPalette.RED + "");
            int reqLevel = RPGClassManager.getRequiredLevelForClassTier(classTier);
            lore.add(ChatPalette.RED + "Required level: " + ChatPalette.GRAY + reqLevel);
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
