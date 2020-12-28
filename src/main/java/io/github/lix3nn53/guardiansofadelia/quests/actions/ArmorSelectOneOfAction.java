package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.Items.config.ArmorReferenceData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArmorSelectOneOfAction implements Action {

    private final ArmorReferenceData armorReferenceData;

    public ArmorSelectOneOfAction(ArmorReferenceData armorReferenceData) {
        this.armorReferenceData = armorReferenceData;
    }

    @Override
    public void perform(Player player, int questNo, int taskIndex) {
        if (taskIndex < 0) {
            player.sendMessage(ChatColor.DARK_RED + "Configuration error, please report to a developer!");
            player.sendMessage(ChatColor.DARK_RED + "You can't use WeaponSelectOneOfAction as quest action, only task action");
        }

        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
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

                // CREATE GUI
                // Task Item Prize Selection #12&1
                GuiGeneric guiGeneric = new GuiGeneric(guiSize, ChatColor.BLACK + "Task Item Prize Selection #" + questNo + "&" + taskIndex, 0);

                // PLACE ITEMS
                int index = 0;

                if (armorReferenceData != null) {
                    List<ItemStack> items = armorReferenceData.getItems(rpgCharacter.getRpgClassStr());
                    for (ItemStack itemStack : items) {
                        Integer slotNo = slotsToUse.get(index);
                        guiGeneric.setItem(slotNo, itemStack);
                        index++;
                    }
                }

                guiGeneric.openInventory(player);
            }
        }
    }

    @Override
    public boolean preventTaskCompilation() {
        return true;
    }
}
