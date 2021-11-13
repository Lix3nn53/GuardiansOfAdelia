package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiServerBoost extends GuiGeneric {

    public GuiServerBoost() {
        super(27, ChatPalette.GRAY_DARK + "Server Boosts", 0);

        ItemStack boostExperience = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = boostExperience.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Experience Boost");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "2x exp gained from slaying monsters");
        if (BoostPremiumManager.isBoostActive(BoostPremium.EXPERIENCE)) {
            boostExperience.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.EXPERIENCE));
        }
        itemMeta.setLore(lore);
        boostExperience.setItemMeta(itemMeta);
        this.setItem(10, boostExperience);

        ItemStack boostLoot = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Loot Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "2x chance of monsters droping item when they die");
        if (BoostPremiumManager.isBoostActive(BoostPremium.LOOT)) {
            boostLoot.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.LOOT));
        }
        itemMeta.setLore(lore);
        boostLoot.setItemMeta(itemMeta);
        this.setItem(12, boostLoot);

        ItemStack boostEnchant = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Enchant Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Increases success rate of item enchanting by 15%");
        if (BoostPremiumManager.isBoostActive(BoostPremium.ENCHANT)) {
            boostEnchant.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.ENCHANT));
        }
        itemMeta.setLore(lore);
        boostEnchant.setItemMeta(itemMeta);
        this.setItem(14, boostEnchant);

        ItemStack boostGather = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Gather Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "2x gathering speed ");
        if (BoostPremiumManager.isBoostActive(BoostPremium.GATHER)) {
            boostGather.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.GATHER));
        }
        itemMeta.setLore(lore);
        boostGather.setItemMeta(itemMeta);
        this.setItem(16, boostGather);
    }
}
