package io.github.lix3nn53.guardiansofadelia.menu.crafting;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiCraftingLevelSelection extends GuiGeneric {

    private final HashMap<Integer, Integer> slotToCraftingLevel = new HashMap<>();
    CraftingType craftingType;

    public GuiCraftingLevelSelection(CraftingType craftingType) {
        super(27, CustomCharacterGui.MENU_27_FLAT + craftingType.toString() + " Crafting Level Selection", 0);
        this.craftingType = craftingType;

        ItemStack itemStack = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(22);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to craft items of this crafting-level.");
        itemMeta.setLore(lore);

        List<Integer> craftingTypeToLevels = CraftingManager.getCraftingTypeToLevels(craftingType);

        for (int level : craftingTypeToLevels) {
            itemMeta.setDisplayName(ChatPalette.GOLD + "Level " + level);
            itemStack.setAmount(level);
            itemStack.setItemMeta(itemMeta);
            int slot = 8 + level;
            this.setItem(slot, itemStack);
            slotToCraftingLevel.put(slot, level);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);
        } else {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            ItemStack current = event.getCurrentItem();
            Material currentType = current.getType();
            if (currentType.equals(Material.WOODEN_PICKAXE)) {
                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                if (rpgCharacter != null) {
                    int currentLevel = rpgCharacter.getCraftingStats().getCurrentLevel(craftingType);

                    int craftingLevel = slotToCraftingLevel.get(event.getSlot());

                    if (currentLevel >= craftingLevel) {
                        GuiCraftingBook gui = new GuiCraftingBook(craftingType, craftingLevel);
                        gui.openInventory(player);
                    } else {
                        player.sendMessage(ChatPalette.RED + "Required crafting level to craft level-" + craftingLevel + ChatPalette.RED + " items is " + ChatPalette.GOLD + craftingLevel);
                    }
                }
            }
        }
    }
}
