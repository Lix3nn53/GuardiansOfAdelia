package io.github.lix3nn53.guardiansofadelia.menu.quest;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.stats.GearStatType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiQuestTaskPrizeSelect extends GuiGeneric {

    private final int questNo;
    private final int taskIndex;

    public GuiQuestTaskPrizeSelect(GuardianData guardianData, int questNo, int resourceNPC,
                                   int taskIndex, List<ItemStack> items) {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK +
                Translation.t(guardianData, "quest.task.prize.selection") + " #" + questNo + "&" + taskIndex, resourceNPC);
        this.questNo = questNo;
        this.taskIndex = taskIndex;

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

        // PLACE ITEMS
        int index = 0;

        for (ItemStack itemStack : items) {
            Integer slotNo = slotsToUse.get(index);
            this.setItem(slotNo, itemStack);
            index++;
        }
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

        ItemStack current = this.getItem(event.getSlot());
        Material currentType = current.getType();

        //give item
        GearStatType gearStatType = StatUtils.getStatType(currentType);
        if (gearStatType != null) {
            GearLevel gearLevel = GearLevel.getGearLevel(current);
            ItemTier itemTier = ItemTier.getItemTierOfItemStack(current);
            StatUtils.addRandomPassiveStats(current, gearLevel, itemTier);
        }
        InventoryUtils.giveItemToPlayer(player, current);

        //complete task
        boolean didComplete = rpgCharacter.progressTaskOfQuestWithIndex(player, questNo, taskIndex);
        if (didComplete) {
            player.closeInventory();
            MessageUtils.sendCenteredMessage(player, ChatPalette.GOLD + "Obtained " + current.getItemMeta().getDisplayName());
        } else {
            player.sendMessage(ChatPalette.RED + "Couldn't complete the task ERROR report pls");
        }
    }
}
