package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;

public class PassiveSet {

    private final String name;
    private final int baseCustomModelData;
    private final int baseLevel;
    private final RPGGearType gearType;

    public PassiveSet(String name, int baseCustomModelData, int baseLevel, RPGGearType gearType) {
        this.name = name;
        this.baseCustomModelData = baseCustomModelData;
        this.baseLevel = baseLevel;
        this.gearType = gearType;
    }

    public String getName(RPGSlotType slotType) {
        return name + " " + slotType.getDisplayName();
    }

    public int getCustomModelData(RPGSlotType slotType) {
        return baseCustomModelData + slotType.getCustomModelDataOffset();
    }

    public int getLevel(RPGSlotType slotType) {
        return baseLevel + slotType.getReqLevelOffset();
    }

    public RPGGearType getGearType() {
        return gearType;
    }

    public int getBaseLevel() {
        return baseLevel;
    }
}
