package io.github.lix3nn53.guardiansofadelia.transportation;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
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
    private final int levelReq;

    public InstantTeleportGuiItem(String name, Location location, int cost, int levelReq) {
        this.name = name;
        this.location = location;
        this.cost = cost;
        this.levelReq = levelReq;
    }

    public ItemStack getItemStack(GuardianData guardianData, boolean isUnlocked, int code) {
        Material material = isUnlocked ? Material.LIME_WOOL : Material.RED_WOOL;
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        ChatPalette chatPalette = isUnlocked ? ChatPalette.GREEN_DARK : ChatPalette.RED;
        itemMeta.setDisplayName(chatPalette + this.name + " #" + code);

        String priceToString = EconomyUtils.priceToString(this.cost);

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GOLD + Translation.t(guardianData, "economy.price") + ": " + priceToString);
        lore.add(chatPalette + Translation.t(guardianData, "condition.level") + ": " + ChatPalette.GRAY + levelReq);
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.teleportation.click"));
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
