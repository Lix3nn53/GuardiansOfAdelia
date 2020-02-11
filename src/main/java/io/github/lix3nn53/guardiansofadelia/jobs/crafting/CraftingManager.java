package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CraftingManager {

    private final static HashMap<String, List<CraftingLine>> craftingTypeAndLevelToCraftingLine = new HashMap<>();

    public static void putCraftingTypeAndLevelToCraftingLine(ItemStack itemStack, CraftingType craftingType, int craftingLevel, List<Integer> ingredients, List<Integer> ingredientAmounts) {
        String key = craftingType.toString() + craftingLevel;

        CraftingLine craftingLine = new CraftingLine(itemStack);

        for (int i = 0; i < ingredients.size(); i++) {
            int ingredient = ingredients.get(i);
            int amount = ingredientAmounts.get(i);
            ItemStack ingredientItem = GatheringManager.getIngredient(ingredient).getItemStack(amount);

            craftingLine.addWord(ingredientItem);
        }

        List<CraftingLine> craftingLines = new ArrayList<>();
        if (craftingTypeAndLevelToCraftingLine.containsKey(key)) {
            craftingLines = craftingTypeAndLevelToCraftingLine.get(key);
        }
        craftingLines.add(craftingLine);
        craftingTypeAndLevelToCraftingLine.put(key, craftingLines);
    }

    public static List<CraftingLine> getCraftingTypeAndLevelToCraftingLines(CraftingType craftingType, int craftingLevel) {
        String key = craftingType.toString() + craftingLevel;
        return craftingTypeAndLevelToCraftingLine.get(key);
    }
}
