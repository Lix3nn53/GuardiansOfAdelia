package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private final int key;
    private final Material material;
    private final String name;
    private final int ingredientLevel;
    private final List<String> jobsCanUse;
    private final List<String> text;
    private final int customModelData;

    public Ingredient(int key, Material material, String name, int ingredientLevel, List<String> jobsCanUse, List<String> text, int customModelData) {
        this.key = key;
        this.material = material;
        this.name = name;
        this.ingredientLevel = ingredientLevel;
        this.jobsCanUse = jobsCanUse;
        this.text = text;
        this.customModelData = customModelData;
    }

    public ItemStack getItemStack(int amount) {
        ItemStack itemStack = new ItemStack(material, amount);

        PersistentDataContainerUtil.putInteger("ingredient", key, itemStack);

        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Ingredient Level: " + ingredientLevel);
        lore.add("");
        for (String jobType : jobsCanUse) {
            lore.add(ChatColor.GREEN + "✔ " + jobType);
        }
        lore.addAll(this.text);

        if (customModelData > 0) {
            im.setCustomModelData(customModelData);
        }

        itemStack.setItemMeta(im);

        return itemStack;
    }
}
