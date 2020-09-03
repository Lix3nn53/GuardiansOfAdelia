package io.github.lix3nn53.guardiansofadelia.Items.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.TeleportScroll;
import io.github.lix3nn53.guardiansofadelia.Items.list.Eggs;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemReferenceLoader {

    public static ItemStack loadItemReference(ConfigurationSection configurationSection) {
        String itemType = configurationSection.getString("itemType");

        if (itemType == null) {
            GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NULL ITEM TYPE");
            return null;
        }

        if (itemType.equals("Armor")) {
            ArmorSlot armorSlot = ArmorSlot.valueOf(configurationSection.getString("armorSlot"));
            ArmorGearType gearType = ArmorGearType.valueOf(configurationSection.getString("armorGearType"));
            int gearLevel = configurationSection.getInt("gearLevel");
            int itemIndex = configurationSection.getInt("itemIndex");
            ItemTier itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
            String itemTag = configurationSection.getString("itemTag");

            return ArmorManager.get(armorSlot, gearType, gearLevel, itemIndex, itemTier, itemTag, false);
        } else if (itemType.equals("Egg")) {
            String petType = configurationSection.getString("petCode");
            int gearLevel = configurationSection.getInt("gearLevel");
            int petLevel = configurationSection.getInt("petLevel");

            return Eggs.get(petType, gearLevel, petLevel);
        } else if (itemType.equals("Passive")) {
            int gearLevel = configurationSection.getInt("gearLevel");
            int itemIndex = configurationSection.getInt("itemIndex");
            RPGSlotType rpgSlotType = RPGSlotType.valueOf(configurationSection.getString("rpgSlotType"));
            int reqLevelOffset = rpgSlotType.getReqLevelOffset();
            if (reqLevelOffset == 9999) {
                GuardiansOfAdelia.getInstance().getLogger().info("WRONG PASSIVE RPG-SLOT-TYPE");
                return null;
            }
            ItemTier itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
            String itemTag = configurationSection.getString("itemTag");

            return PassiveManager.get(gearLevel, itemIndex, rpgSlotType, itemTier, itemTag, false);
        } else if (itemType.equals("Weapon")) {
            WeaponGearType gearType = WeaponGearType.valueOf(configurationSection.getString("weaponGearType"));
            int gearLevel = configurationSection.getInt("gearLevel");
            int itemIndex = configurationSection.getInt("itemIndex");
            ItemTier itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
            String itemTag = configurationSection.getString("itemTag");

            return WeaponManager.get(gearType, gearLevel, itemIndex, itemTier, itemTag, false);
        } else if (itemType.equals("Shield")) {
            ShieldGearType gearType = ShieldGearType.valueOf(configurationSection.getString("shieldGearType"));
            int gearLevel = configurationSection.getInt("gearLevel");
            int itemIndex = configurationSection.getInt("itemIndex");
            ItemTier itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
            String itemTag = configurationSection.getString("itemTag");

            return ShieldManager.get(gearType, gearLevel, itemIndex, itemTier, itemTag, false);
        } else if (itemType.equals("Consumable")) {
            Consumable consumable = Consumable.valueOf(configurationSection.getString("consumable"));

            int skillLevel = configurationSection.getInt("skillLevel");
            int uses = configurationSection.getInt("uses");

            return consumable.getItemStack(skillLevel, uses);
        } else if (itemType.equals("TeleportScroll")) {
            String worldStr = configurationSection.getString("world");
            World world = Bukkit.getWorld(worldStr);
            int x = configurationSection.getInt("x");
            int y = configurationSection.getInt("y");
            int z = configurationSection.getInt("z");
            float yaw = (float) configurationSection.getDouble("yaw");
            float pitch = (float) configurationSection.getDouble("pitch");

            Location location = new Location(world, x, y, z, yaw, pitch);
            String name = configurationSection.getString("name");
            TeleportScroll teleportScroll = new TeleportScroll(location, name);

            int reqLevel = configurationSection.getInt("reqLevel");
            int amount = configurationSection.getInt("amount");

            return teleportScroll.getScroll(amount, reqLevel);
        } else if (itemType.equals("Basic")) {
            String materialStr = configurationSection.getString("material");
            Material material = Material.valueOf(materialStr);
            String name = configurationSection.getString("name");
            List<String> lore = configurationSection.getStringList("lore");

            ItemStack item = new ItemStack(material);
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(name);
            im.setLore(lore);

            if (configurationSection.contains("customModelData")) {
                int customModelData = configurationSection.getInt("customModelData");
                im.setCustomModelData(customModelData);
            }

            item.setItemMeta(im);

            return item;
        }

        GuardiansOfAdelia.getInstance().getLogger().info(ChatColor.RED + "NO SUCH ITEM TYPE IN LOADER");

        return null;
    }
}
