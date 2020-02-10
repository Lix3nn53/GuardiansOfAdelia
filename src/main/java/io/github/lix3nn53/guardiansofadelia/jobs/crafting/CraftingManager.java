package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.Ingredient;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CraftingManager {

    private final static HashMap<String, List<Integer>> craftingTypeAndLevelToIngredients = new HashMap<>();
    private final static HashMap<String, List<Integer>> craftingTypeAndLevelToIngredientAmounts = new HashMap<>();

    public static void putCraftingTypeAndLevelToIngredients(CraftingType craftingType, GearLevel gearLevel, List<Integer> ingredients, List<Integer> amounts) {
        String key = craftingType.toString() + gearLevel.toString();
        craftingTypeAndLevelToIngredients.put(key, ingredients);
        craftingTypeAndLevelToIngredientAmounts.put(key, amounts);
    }

    public static List<ItemStack> getCraftingTypeAndLevelToIngredients(CraftingType craftingType, int gearLevel) {
        List<ItemStack> list = new ArrayList<>();

        String key = craftingType.toString() + gearLevel;
        List<Integer> ingredients = craftingTypeAndLevelToIngredients.get(key);
        List<Integer> amounts = craftingTypeAndLevelToIngredientAmounts.get(key);

        for (int i = 0; i < ingredients.size(); i++) {
            int ingredientNo = ingredients.get(i);
            int amount = amounts.get(i);

            Ingredient ingredient = GatheringManager.getIngredient(ingredientNo);
            ItemStack itemStack = ingredient.getItemStack(amount);

            list.add(itemStack);
        }

        return list;
    }
}
