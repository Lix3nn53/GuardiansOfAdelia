package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiLine;
import org.apache.commons.lang3.NotImplementedException;
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
        itemMeta.setDisplayName(ChatPalette.GOLD + "Crafting Guide");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.YELLOW + "You need ingredients on left side of this line");
        lore.add(ChatPalette.YELLOW + "to create the item on right side of this line.");
        lore.add("");
        lore.add(ChatPalette.GREEN_DARK + "If you have enough ingredients,");
        lore.add(ChatPalette.GREEN_DARK + "click on the item you want to craft.");
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

    @Override
    public void setDisabled(boolean disabled) {
        throw new NotImplementedException("CraftingLine#setDisabled not implemented");
    }
}
