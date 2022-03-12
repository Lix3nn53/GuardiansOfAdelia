package io.github.lix3nn53.guardiansofadelia.menu.main.compass;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.menu.main.GuiCompass;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiCompassActiveQuests extends GuiBookGeneric {

    public GuiCompassActiveQuests(GuardianData guardianData) {
        super(CustomCharacterGui.MENU_54_FLAT.toString() + ChatPalette.BLACK + "Compass Quests", 0);

        ItemStack backButton = OtherItems.getBackButton("Compass Menu");
        this.addToFirstAvailableWord(backButton);
        this.disableLine(0, 0);

        RPGCharacter character = guardianData.getActiveCharacter();
        List<Quest> questList = character.getQuestList();

        for (Quest quest : questList) {
            ItemStack itemStack = compassQuestItem(quest);
            this.addToFirstAvailableWord(itemStack);
        }
    }

    private static ItemStack compassQuestItem(Quest quest) {
        ItemStack itemStack = new ItemStack(Material.MAGENTA_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to auto track this quest!");
        itemMeta.setLore(lore);

        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + quest.getName() + " #" + quest.getQuestID());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ItemStack item = event.getCurrentItem();
        ItemMeta itemMeta = item.getItemMeta();
        Material currentType = item.getType();

        GuardianData guardianData;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);
        } else {
            return;
        }

        if (event.getSlot() == 0) {
            GuiCompass gui = new GuiCompass(guardianData);
            gui.openInventory(player);
        }
        if (currentType.equals(Material.MAGENTA_WOOL)) {
            String displayName = itemMeta.getDisplayName();
            String[] split = displayName.split("#");

            int questID = Integer.parseInt(split[1]);

            CompassManager.startAutoTrackQuest(player, questID);
        }
    }
}
