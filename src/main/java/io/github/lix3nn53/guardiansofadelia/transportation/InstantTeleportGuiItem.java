package io.github.lix3nn53.guardiansofadelia.transportation;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InstantTeleportGuiItem {
    private final String name;
    private final Location location;
    private final int cost;

    public InstantTeleportGuiItem(String name, Location location, int cost) {
        this.name = name;
        this.location = location;
        this.cost = cost;
    }

    public ItemStack getItemStack(boolean isUnlocked, int questNo) {
        Material material = isUnlocked ? Material.LIME_WOOL : Material.RED_WOOL;
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        ChatPalette chatPalette = isUnlocked ? ChatPalette.GREEN_DARK : ChatPalette.RED;
        itemMeta.setDisplayName(chatPalette + this.name + " #" + questNo);

        String priceToString = EconomyUtils.priceToString(this.cost);

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GOLD + "Teleportation cost: " + priceToString);
        lore.add(chatPalette + "Required Quest: " + ChatPalette.GRAY + questNo);
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to start teleporting to this location.");
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
