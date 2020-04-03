package io.github.lix3nn53.guardiansofadelia.Items.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ItemReferenceLoader {

    public static ItemStack loadItemReference(ConfigurationSection configurationSection) {
        /*String itemType = configurationSection.getString("itemType");

        if (itemType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NULL ITEM TYPE");
            return null;
        }

        if (itemType.equals("Armor")) {
            ArmorType armorType = ArmorType.valueOf(configurationSection.getString("armorType"));
            RPGClass rpgClass = RPGClass.valueOf(configurationSection.getString("rpgClass"));
            int gearLevel = configurationSection.getInt("gearLevel");
            int itemIndex = configurationSection.getInt("itemIndex");
            ItemTier itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
            String itemTag = configurationSection.getString("itemTag");
            int minStatValue = GearLevel.getMinStatValue(gearLevel);
            int maxStatValue = GearLevel.getMaxStatValue(gearLevel);
            int minNumberOfStats = itemTier.getMinNumberOfStatsNormal();

            return Armors.getArmor(armorType, rpgClass, gearLevel, itemIndex, itemTier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        } else if (itemType.equals("Egg")) {
            String petType = configurationSection.getString("petType");

            if (petType.equals("Companion")) {
                Companion companion = Companion.valueOf(configurationSection.getString("companion"));
                int gearLevel = configurationSection.getInt("gearLevel");

                return Companions.get(companion, gearLevel);
            }
        } else if (itemType.equals("Passive")) {
            int gearLevel = configurationSection.getInt("gearLevel");
            int itemIndex = configurationSection.getInt("itemIndex");
            RPGSlotType rpgSlotType = RPGSlotType.valueOf(configurationSection.getString("rpgSlotType"));
            ItemTier itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
            String itemTag = configurationSection.getString("itemTag");
            int minStatValue = GearLevel.getMinStatValue(gearLevel);
            int maxStatValue = GearLevel.getMaxStatValue(gearLevel);
            int minNumberOfStats = itemTier.getMinNumberOfStatsPassive();

            return PassiveManager.get(gearLevel, itemIndex, rpgSlotType, itemTier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        } else if (itemType.equals("Weapon")) {
            RPGClass rpgClass = RPGClass.valueOf(configurationSection.getString("rpgClass"));
            int gearLevel = configurationSection.getInt("gearLevel");
            int itemIndex = configurationSection.getInt("itemIndex");
            ItemTier itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
            String itemTag = configurationSection.getString("itemTag");
            int minStatValue = GearLevel.getMinStatValue(gearLevel);
            int maxStatValue = GearLevel.getMaxStatValue(gearLevel);
            int minNumberOfStats = itemTier.getMinNumberOfStatsNormal();

            return Weapons.getWeapon(rpgClass, gearLevel, itemIndex, itemTier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        } else if (itemType.equals("Consumable")) {
            Consumable consumable = Consumable.valueOf(configurationSection.getString("consumable"));

            int skillLevel = configurationSection.getInt("skillLevel");
            int uses = configurationSection.getInt("uses");

            return consumable.getItemStack(skillLevel, uses);
        } else if (itemType.equals("TeleportScroll")) {
            TeleportScroll teleportScroll = TeleportScroll.valueOf(configurationSection.getString("teleportScroll"));

            int reqLevel = configurationSection.getInt("reqLevel");
            int amount = configurationSection.getInt("amount");

            return teleportScroll.getScroll(amount, reqLevel);
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NO SUCH ITEM TYPE IN LOADER");*/

        return null;
    }
}
