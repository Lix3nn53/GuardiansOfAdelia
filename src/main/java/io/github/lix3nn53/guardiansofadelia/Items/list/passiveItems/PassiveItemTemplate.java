package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;

public class PassiveItemTemplate {

    private final String name;
    private final int customModelData;
    private final int level;
    private final RPGSlotType rpgSlotType;
    private final RPGGearType gearType;

    public PassiveItemTemplate(String name, int customModelData, int level, RPGSlotType rpgSlotType, RPGGearType gearType) {
        this.name = name;
        this.customModelData = customModelData;
        this.level = level;
        this.rpgSlotType = rpgSlotType;
        this.gearType = gearType;
    }

    public String getName() {
        return name;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public int getLevel() {
        return level;
    }

    public RPGSlotType getRpgSlotType() {
        return rpgSlotType;
    }

    public RPGGearType getGearType() {
        return gearType;
    }
}
