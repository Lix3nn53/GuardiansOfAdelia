package io.github.lix3nn53.guardiansofadelia.quests.list;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.PotionType;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.list.consumables.Potions;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.actions.GiveItemAction;
import io.github.lix3nn53.guardiansofadelia.quests.actions.GiveWeaponAction;
import io.github.lix3nn53.guardiansofadelia.quests.actions.SendTitleAction;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskInteract;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MainStoryQuests {

    public static void createQuests() {

        //king of roumen first quest
        List<String> story = new ArrayList<>();
        story.add("You need to master power of elements and join");
        story.add("Guardians of Adelia to end darkness.");
        story.add("But this is a long journey, let's start");
        story.add("with meeting villagers in Roumen.");
        List<Task> tasks = new ArrayList<>();

        Task task = new TaskInteract(17);
        ItemStack boat = OtherItems.getBoat();
        GiveItemAction giveItemAction = new GiveItemAction(boat);
        SendTitleAction sendTitleAction = new SendTitleAction("", ChatColor.GREEN + "Obtained Boat");
        task.addOnCompleteAction(giveItemAction);
        task.addOnCompleteAction(sendTitleAction);
        tasks.add(task);

        Task task1 = new TaskInteract(14);
        GiveWeaponAction giveWeaponAction = new GiveWeaponAction(1, ItemTier.RARE, "Newbie", 1, 5, 2);
        SendTitleAction sendTitleAction1 = new SendTitleAction("", ChatColor.GREEN + "Obtained Weapon");
        task1.addOnCompleteAction(giveWeaponAction);
        task1.addOnCompleteAction(sendTitleAction1);
        tasks.add(task1);

        Task task2 = new TaskInteract(16);
        ItemStack hpPotion = Potions.getItemStack(PotionType.HEALTH, 1);
        hpPotion.setAmount(10);
        ItemStack manaPotion = Potions.getItemStack(PotionType.MANA, 1);
        manaPotion.setAmount(10);
        GiveItemAction giveItemAction1 = new GiveItemAction(hpPotion);
        GiveItemAction giveItemAction2 = new GiveItemAction(manaPotion);
        task2.addOnCompleteAction(giveItemAction1);
        task2.addOnCompleteAction(giveItemAction2);
        SendTitleAction sendTitleAction2 = new SendTitleAction("", ChatColor.GREEN + "Obtained Potions");
        task2.addOnCompleteAction(sendTitleAction2);
        tasks.add(task2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String obj = "Talk to Item Merchant TASK_PROGRESS_1/1\n" + "Talk to Blacksmith TASK_PROGRESS_2/1\n" +
                "Talk to Magical Item Merchant TASK_PROGRESS_3/1\n"
                + "Then go back to King of Roumen\n";
        Quest quest1 = new Quest(6, "Another newbie?", story,
                "Time to meet villagers!", obj,
                "", tasks, itemPrizes, 10, 10, 0, 5,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest1, 31, 31);

        //king of roumen 2nd quest
    }
}
