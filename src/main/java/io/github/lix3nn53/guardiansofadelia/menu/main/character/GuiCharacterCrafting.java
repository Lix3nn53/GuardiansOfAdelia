package io.github.lix3nn53.guardiansofadelia.menu.main.character;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.RPGCharacterCraftingStats;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiCharacterCrafting extends GuiGeneric {

    public GuiCharacterCrafting(GuardianData guardianData, RPGCharacter activeCharacter) {
        super(45, ChatPalette.YELLOW + "Crafting", 0);

        RPGCharacterCraftingStats craftingStats = activeCharacter.getCraftingStats();

        int slot = 10;
        for (CraftingType craftingType : CraftingType.values()) {
            ItemStack craftingInfo = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta itemMeta = craftingInfo.getItemMeta();

            List<String> lore = new ArrayList<>();
            lore.add("");
            int currentLevel = craftingStats.getCurrentLevel(craftingType);
            lore.add(Translation.t(guardianData, "character.class.tier") + ": " + currentLevel);
            lore.add("Experience: " + craftingStats.getTotalExperience(craftingType));
            lore.add("Required Experience: " + craftingStats.getTotalRequiredExperience(currentLevel));

            lore.add("");
            lore.add(ChatPalette.YELLOW + "How to craft?");

            if (craftingType.equals(CraftingType.WEAPON_MELEE)) {
                //slot = 10;
                itemMeta.setDisplayName(ChatPalette.RED + "Melee Weaponsmith");
                lore.add(ChatPalette.GRAY + "Left click grindstone to craft melee weapons");
            }
            if (craftingType.equals(CraftingType.WEAPON_RANGED)) {
                slot = 12;
                craftingInfo.setType(Material.BOW);
                itemMeta.setDisplayName(ChatPalette.RED + "Ranged Weaponsmith");
                lore.add(ChatPalette.GRAY + "Left click fletching table to craft ranged weapons");
            } else if (craftingType.equals(CraftingType.ARMOR_HEAVY)) {
                slot = 14;
                craftingInfo.setType(Material.NETHERITE_CHESTPLATE);
                itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Heavy Armorsmith");
                lore.add(ChatPalette.GRAY + "Left click anvil to craft heavy armors");
            } else if (craftingType.equals(CraftingType.ARMOR_LIGHT)) {
                slot = 16;
                craftingInfo.setType(Material.LEATHER_CHESTPLATE);
                itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Light Armorsmith");
                lore.add(ChatPalette.GRAY + "Left click loom to craft light armors");
            } else if (craftingType.equals(CraftingType.POTION)) {
                slot = 28;
                craftingInfo.setType(Material.POTION);
                itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Potion Alchemist");
                lore.add(ChatPalette.GRAY + "Left click brewing stand to craft potions");
            } else if (craftingType.equals(CraftingType.FOOD)) {
                slot = 30;
                craftingInfo.setType(Material.COOKED_BEEF);
                itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Food Alchemist");
                lore.add(ChatPalette.GRAY + "Left click campfire to craft foods");
            } else if (craftingType.equals(CraftingType.JEWEL)) {
                slot = 32;
                craftingInfo.setType(Material.EMERALD);
                itemMeta.setDisplayName(ChatPalette.GOLD + "Jeweller");
                lore.add(ChatPalette.GRAY + "Left click smithing table to craft jewels");
            } else if (craftingType.equals(CraftingType.ENCHANT_STONE)) {
                slot = 34;
                craftingInfo.setType(Material.ENCHANTED_BOOK);
                itemMeta.setDisplayName(ChatPalette.GOLD + "Enchant-Stone Crafting");
                lore.add(ChatPalette.GRAY + "Left click enchanting table to enchant stones");
            }
            itemMeta.setLore(lore);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            craftingInfo.setItemMeta(itemMeta);
            this.setItem(slot, craftingInfo);

            slot += 3;
        }
    }
}
