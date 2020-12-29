package io.github.lix3nn53.guardiansofadelia.Items.config;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmorReferenceData {
    private final ArmorSlot armorSlot;
    private final int gearLevel;
    private final int itemIndex;
    private final ItemTier itemTier;
    private final String gearSet;

    public ArmorReferenceData(ArmorSlot armorSlot, int gearLevel, int itemIndex, ItemTier itemTier, String gearSet) {
        this.armorSlot = armorSlot;
        this.gearLevel = gearLevel;
        this.itemIndex = itemIndex;
        this.itemTier = itemTier;
        this.gearSet = gearSet;
    }

    public ArmorReferenceData(ConfigurationSection configurationSection) {
        this.armorSlot = ArmorSlot.valueOf(configurationSection.getString("armorSlot"));
        this.gearLevel = configurationSection.getInt("gearLevel");
        this.itemIndex = configurationSection.getInt("itemIndex");
        this.itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
        if (configurationSection.contains("gearSet")) {
            this.gearSet = configurationSection.getString("gearSet");
        } else {
            gearSet = null;
        }
    }

    public List<ItemStack> getItems(String rpgClassStr) {
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        List<ArmorGearType> weaponGearTypes = rpgClass.getArmorGearTypes();

        ArrayList<ItemStack> items = new ArrayList<>();
        for (ArmorGearType type : weaponGearTypes) {
            items.add(ArmorManager.get(armorSlot, type, gearLevel, itemIndex, itemTier, true, gearSet));
        }

        return items;
    }

    public int getGearLevel() {
        return gearLevel;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public ItemTier getItemTier() {
        return itemTier;
    }
}
