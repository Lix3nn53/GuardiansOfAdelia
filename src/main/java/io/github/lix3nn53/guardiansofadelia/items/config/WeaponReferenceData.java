package io.github.lix3nn53.guardiansofadelia.items.config;

import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.list.weapons.WeaponManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WeaponReferenceData {
    private final GearLevel gearLevel;
    private final int itemIndex;
    private final ItemTier itemTier;
    private final String gearSet;

    public WeaponReferenceData(GearLevel gearLevel, int itemIndex, ItemTier itemTier, String gearSet) {
        this.gearLevel = gearLevel;
        this.itemIndex = itemIndex;
        this.itemTier = itemTier;
        this.gearSet = gearSet;
    }

    public WeaponReferenceData(ConfigurationSection configurationSection) {
        this.gearLevel = GearLevel.values()[configurationSection.getInt("gearLevel")];
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
        List<WeaponGearType> weaponGearTypes = rpgClass.getWeaponGearTypes();

        ArrayList<ItemStack> items = new ArrayList<>();
        for (WeaponGearType type : weaponGearTypes) {
            items.add(WeaponManager.get(type, gearLevel, itemTier, true, false).get(itemIndex));
        }

        return items;
    }

    public ItemStack getItem(String rpgClassStr) {
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        WeaponGearType type = rpgClass.getWeaponGearTypes().get(0);

        return WeaponManager.get(type, gearLevel, itemTier, true, false).get(itemIndex);
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
}
