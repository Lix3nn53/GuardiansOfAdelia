package io.github.lix3nn53.guardiansofadelia.menu.quest;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.stats.GearStatType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiQuestTaskPrizeSelect extends GuiGeneric {

    private final int questNo;
    private final int taskIndex;

    public GuiQuestTaskPrizeSelect(int guiSize, int questNo, int resourceNPC, int taskIndex, List<ItemStack> items) {
        super(guiSize, ChatPalette.BLACK + "Task Item Prize Selection #" + questNo + "&" + taskIndex, resourceNPC);
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
    public void onClick(Player player, GuardianData guardianData, String title, int slot) {
        ItemStack current = this.getItem(slot);
        Material currentType = current.getType();

        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

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
