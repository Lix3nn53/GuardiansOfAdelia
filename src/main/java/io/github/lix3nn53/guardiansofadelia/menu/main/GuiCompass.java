package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.GuiHelper;
import io.github.lix3nn53.guardiansofadelia.menu.main.compass.GuiCompassActiveQuests;
import io.github.lix3nn53.guardiansofadelia.menu.main.compass.GuiCompassDungeonGates;
import io.github.lix3nn53.guardiansofadelia.menu.main.compass.GuiCompassNPCs;
import io.github.lix3nn53.guardiansofadelia.menu.main.compass.GuiCompassTowns;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiCompass extends GuiGeneric {

    public GuiCompass(GuardianData guardianData) {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK + Translation.t(guardianData, "menu.compass.name"), 0);

        ItemStack towns = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = towns.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Towns");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a town as your compass target.");
        itemMeta.setLore(lore);
        towns.setItemMeta(itemMeta);

        ItemStack dungeons = new ItemStack(Material.PURPLE_WOOL);
        dungeons.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Dungeon Gates");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a dungeon gate as your compass target.");
        itemMeta.setLore(lore);
        dungeons.setItemMeta(itemMeta);

        ItemStack npcs = new ItemStack(Material.LIME_WOOL);
        npcs.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "NPCS");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a NPC as your compass target.");
        itemMeta.setLore(lore);
        npcs.setItemMeta(itemMeta);

        ItemStack quests = new ItemStack(Material.MAGENTA_WOOL);
        quests.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Active Quests");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to auto track a quest.");
        lore.add("Tracking a quest changes the compass target");
        lore.add("automatically as the quest progresses.");
        lore.add("");
        lore.add("What can be tracked?");
        lore.add("- Task to complete dungeon");
        lore.add("- NPC to turn in the quest");
        lore.add("- Task to interact with a NPC");
        lore.add("- Task to reach a location");
        lore.add("");
        lore.add("Other tasks can't be tracked.");
        itemMeta.setLore(lore);
        quests.setItemMeta(itemMeta);

        ItemStack clear = new ItemStack(Material.RED_WOOL);
        clear.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.RED + "Clear Target");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to clear your compass target.");
        itemMeta.setLore(lore);
        clear.setItemMeta(itemMeta);

        GuiHelper.form27Small(this, new ItemStack[]{quests, towns, dungeons, npcs, clear}, "Main Menu");
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
        if (slot == 0) {
            GuiMain gui = new GuiMain(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get27SmallButtonIndex(0) == slot) {
            GuiCompassActiveQuests gui = new GuiCompassActiveQuests(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get27SmallButtonIndex(1) == slot) {
            GuiCompassTowns gui = new GuiCompassTowns();
            gui.openInventory(player);
        } else if (GuiHelper.get27SmallButtonIndex(2) == slot) {
            GuiCompassDungeonGates gui = new GuiCompassDungeonGates();
            gui.openInventory(player);
        } else if (GuiHelper.get27SmallButtonIndex(3) == slot) {
            GuiCompassNPCs gui = new GuiCompassNPCs();
            gui.openInventory(player);
        } else if (GuiHelper.get27SmallButtonIndex(4) == slot) {
            CompassManager.clearCompassTarget(player);
            player.closeInventory();
        }
    }
}
