package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.main.compass.GuiCompassActiveQuests;
import io.github.lix3nn53.guardiansofadelia.menu.main.compass.GuiCompassDungeonGates;
import io.github.lix3nn53.guardiansofadelia.menu.main.compass.GuiCompassNPCs;
import io.github.lix3nn53.guardiansofadelia.menu.main.compass.GuiCompassTowns;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiCompass extends GuiGeneric {

    public GuiCompass() {
        super(27, ChatPalette.GRAY_DARK + "Compass", 0);

        ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Towns");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a town as your compass target.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.setItem(12, itemStack);

        itemStack = new ItemStack(Material.PURPLE_WOOL);
        itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Dungeon Gates");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a dungeon gate as your compass target.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.setItem(14, itemStack);

        itemStack = new ItemStack(Material.LIME_WOOL);
        itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "NPCS");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a NPC as your compass target.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.setItem(16, itemStack);

        itemStack = new ItemStack(Material.MAGENTA_WOOL);
        itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Active Quests");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a npc relevant to your...");
        lore.add("...active quests your compass target.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.setItem(10, itemStack);

        itemStack = new ItemStack(Material.RED_WOOL);
        itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.RED + "Clear Target");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to clear your compass target.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.setItem(26, itemStack);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        RPGCharacter rpgCharacter;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            } else {
                return;
            }
        } else {
            return;
        }

        int slot = event.getSlot();
        if (slot == 10) { //quests
            GuiCompassActiveQuests gui = new GuiCompassActiveQuests(guardianData);
            gui.openInventory(player);
        } else if (slot == 12) { //towns
            GuiCompassTowns gui = new GuiCompassTowns();
            gui.openInventory(player);
        } else if (slot == 14) { //dungeon gates
            GuiCompassDungeonGates gui = new GuiCompassDungeonGates();
            gui.openInventory(player);
        } else if (slot == 16) { //npcs
            GuiCompassNPCs gui = new GuiCompassNPCs();
            gui.openInventory(player);
        } else if (slot == 26) { //npcs
            CompassManager.clearCompassTarget(player);
            player.closeInventory();
        }
    }
}
