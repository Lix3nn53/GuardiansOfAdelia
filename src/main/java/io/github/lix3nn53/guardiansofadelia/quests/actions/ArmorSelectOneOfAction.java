package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.config.ArmorReferenceData;
import io.github.lix3nn53.guardiansofadelia.menu.quest.GuiQuestTaskPrizeSelect;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmorSelectOneOfAction implements Action {

    private final ArmorReferenceData armorReferenceData;

    public ArmorSelectOneOfAction(ArmorReferenceData armorReferenceData) {
        this.armorReferenceData = armorReferenceData;
    }

    @Override
    public void perform(Player player, int questNo, int taskIndex) {
        if (taskIndex < 0) {
            player.sendMessage(ChatPalette.RED_DARK + "Configuration error, please report to a developer!");
            player.sendMessage(ChatPalette.RED_DARK + "You can't use WeaponSelectOneOfAction as quest action, only task action");
        }

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
                // GUISIZE
                int guiSize = 18;
                if (armorReferenceData != null) {
                    guiSize += 9;
                }

                // ITEM SLOTS
                List<Integer> slotsToUse = new ArrayList<>();
                slotsToUse.add(10);
                slotsToUse.add(12);
                slotsToUse.add(14);
                slotsToUse.add(16);

                slotsToUse.add(19);
                slotsToUse.add(21);
                slotsToUse.add(23);
                slotsToUse.add(25);

                slotsToUse.add(28);
                slotsToUse.add(30);
                slotsToUse.add(32);
                slotsToUse.add(34);

                slotsToUse.add(28);
                slotsToUse.add(30);
                slotsToUse.add(32);
                slotsToUse.add(34);

                List<ItemStack> items = armorReferenceData.getItems(rpgCharacter.getRpgClassStr());
                GuiQuestTaskPrizeSelect gui = new GuiQuestTaskPrizeSelect(guardianData, guiSize, questNo, 0, taskIndex, items);
                gui.openInventory(player);
            }
        }
    }

    @Override
    public boolean preventTaskCompilation() {
        return true;
    }
}
