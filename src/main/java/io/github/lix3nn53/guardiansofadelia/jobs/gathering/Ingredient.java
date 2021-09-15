package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ingredient {
    private final int key;
    private final Material material;
    private final String name;
    private final int ingredientLevel;
    private final int customModelData;
    private final Color potionColor;
    private final int maxAmountPerGather;
    private final double dropRate;
    private final boolean enchant;

    private final List<String> lore;

    public Ingredient(int key, Material material, String name, int ingredientLevel, List<String> jobsCanUse, List<String> extraText,
                      int customModelData, Color potionColor, int maxAmountPerGather, double dropRate, boolean enchant) {
        this.key = key;
        this.material = material;
        this.name = name;
        this.ingredientLevel = ingredientLevel;
        this.customModelData = customModelData;
        this.potionColor = potionColor;
        this.maxAmountPerGather = maxAmountPerGather;
        this.dropRate = dropRate;
        this.enchant = enchant;

        this.lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Ingredient Level: " + ingredientLevel);
        lore.add("");
        for (String jobType : jobsCanUse) {
            lore.add(ChatPalette.GREEN_DARK + "✔ " + ChatColor.translateAlternateColorCodes('&', jobType));
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

        if (material.equals(Material.POTION)) {
            PotionMeta potionMeta = (PotionMeta) im;
            potionMeta.setColor(potionColor);
        }

        if (enchant) {
            im.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        itemStack.setItemMeta(im);

        return itemStack;
    }

    public int gather() {
        if (dropRate < 1) {
            double dropRandom = Math.random();

            if (dropRandom <= dropRate) {
                Random random = new Random();

                return random.nextInt(maxAmountPerGather) + 1;
            }
        } else {
            Random random = new Random();

            return random.nextInt(maxAmountPerGather) + 1;
        }

        return 0;
    }
}
