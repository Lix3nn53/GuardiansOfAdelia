package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.List;


public class GatheringModelData {

    // Model data
    private final int customModelData;
    private final int cooldownCustomModelData;
    private final String title;
    private final Material material;

    // Gathering data
    private final List<Integer> ingredients;
    private final GatheringToolType gatheringToolType;
    private final GatheringToolTier minGatheringToolTier;

    public GatheringModelData(int customModelData, int cooldownCustomModelData, String title, Material material,
                              List<Integer> ingredients, GatheringToolType gatheringToolType, GatheringToolTier minGatheringToolTier) {
        this.customModelData = customModelData;
        this.cooldownCustomModelData = cooldownCustomModelData;
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.material = material;
        this.ingredients = ingredients;
        this.gatheringToolType = gatheringToolType;
        this.minGatheringToolTier = minGatheringToolTier;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public int getCooldownCustomModelData() {
        return cooldownCustomModelData;
    }

    public String getTitle() {
        return title;
    }

    public Material getMaterial() {
        return material;
    }

    public List<Integer> getIngredients() {
        return ingredients;
    }

    public GatheringToolType getGatheringToolType() {
        return gatheringToolType;
    }

    public GatheringToolTier getMinGatheringToolTier() {
        return minGatheringToolTier;
    }
}
