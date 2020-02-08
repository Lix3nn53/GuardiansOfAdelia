package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

public class PassiveItemTemplate {

    private final String name;
    private final int customModelData;
    private final int level;

    public PassiveItemTemplate(String name, int customModelData, int level) {
        this.name = name;
        this.customModelData = customModelData;
        this.level = level;
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
}
