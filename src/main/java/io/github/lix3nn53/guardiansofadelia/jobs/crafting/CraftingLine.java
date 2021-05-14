package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiLine;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CraftingLine implements GuiLine {

    private final List<ItemStack> ingredients = new ArrayList<>();
    private final ItemStack itemToCreate;

    public CraftingLine(ItemStack itemToCreate) {
        this.itemToCreate = itemToCreate;
    }

    public void addWord(ItemStack itemStack) {
        if (isEmpty()) {
            ingredients.add(itemStack);
        }
    }

    public List<ItemStack> getLine() {
        List<ItemStack> line = new ArrayList<>(ingredients);
        while (line.size() < 7) {
            line.add(new ItemStack(Material.AIR));
        }
        ItemStack infoGlass = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta itemMeta = infoGlass.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Crafting Guide");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "You need ingredients on left side of this line");
        lore.add(ChatColor.YELLOW + "to create the item on right side of this line.");
        lore.add("");
        lore.add(ChatColor.GREEN + "If you have enough ingredients,");
        lore.add(ChatColor.GREEN + "click on the item you want to craft.");
        itemMeta.setLore(lore);
        infoGlass.setItemMeta(itemMeta);
        line.add(infoGlass);
        line.add(itemToCreate);
        return line;
    }

    @Override
    public boolean isEmpty() {
        return ingredients.size() < 7;
    }
}
