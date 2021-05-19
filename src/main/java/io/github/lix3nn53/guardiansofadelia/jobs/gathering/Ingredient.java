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
    private final int customModelData;

    private final List<String> lore;

    public Ingredient(int key, Material material, String name, int ingredientLevel, List<String> jobsCanUse, List<String> extraText, int customModelData) {
        this.key = key;
        this.material = material;
        this.name = name;
        this.ingredientLevel = ingredientLevel;
        this.customModelData = customModelData;

        this.lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Ingredient Level: " + ingredientLevel);
        lore.add("");
        for (String jobType : jobsCanUse) {
            lore.add(ChatColor.GREEN + "✔ " + ChatColor.translateAlternateColorCodes('&', jobType));
        }
        if (extraText != null) {
            lore.add("");
            lore.addAll(extraText);
        }
    }

    public ItemStack getItemStack(int amount) {
        ItemStack itemStack = new ItemStack(material, amount);

        PersistentDataContainerUtil.putInteger("ingredient", key, itemStack);
        PersistentDataContainerUtil.putInteger("reqLevel", ingredientLevel * 4, itemStack);

        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);

        if (customModelData > 0) {
            im.setCustomModelData(customModelData);
        }

        itemStack.setItemMeta(im);

        return itemStack;
    }
}
