package io.github.lix3nn53.guardiansofadelia.items.config;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmorReferenceData {
    private final ArmorSlot armorSlot;
    private final GearLevel gearLevel;
    private final int itemIndex;
    private final ItemTier itemTier;

    public ArmorReferenceData(ArmorSlot armorSlot, GearLevel gearLevel, int itemIndex, ItemTier itemTier) {
        this.armorSlot = armorSlot;
        this.gearLevel = gearLevel;
        this.itemIndex = itemIndex;
        this.itemTier = itemTier;
    }

    public ArmorReferenceData(ConfigurationSection configurationSection) {
        this.armorSlot = ArmorSlot.valueOf(configurationSection.getString("armorSlot"));

        this.gearLevel = GearLevel.values()[configurationSection.getInt("gearLevel")];
        this.itemIndex = configurationSection.getInt("itemIndex");
        this.itemTier = ItemTier.valueOf(configurationSection.getString("itemTier"));
    }

    public List<ItemStack> getItems(String rpgClassStr) {
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        List<ArmorGearType> weaponGearTypes = rpgClass.getArmorGearTypes();

        ArrayList<ItemStack> items = new ArrayList<>();
        for (ArmorGearType type : weaponGearTypes) {
            items.add(ArmorManager.get(armorSlot, type, gearLevel, itemTier, true, false, itemIndex));
        }

        return items;
    }

    public ItemStack getItem(String rpgClassStr) {
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        ArmorGearType type = rpgClass.getArmorGearTypes().get(0);

        return ArmorManager.get(armorSlot, type, gearLevel, itemTier, true, false, itemIndex);
    }

    public GearLevel getGearLevel() {
        return gearLevel;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public ItemTier getItemTier() {
        return itemTier;
    }

    public ArmorSlot getArmorSlot() {
        return armorSlot;
    }
}
