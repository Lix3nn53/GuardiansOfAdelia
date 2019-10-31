package io.github.lix3nn53.guardiansofadelia.quests.list.mainstory;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
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

public class RoumenQuests {

    public static void createQuests() {
        createQuestOne();
        createQuestTwo();
    }

    private static void createQuestOne() {
        List<String> story = new ArrayList<>();
        story.add("You need to master power of elements and become");
        story.add("a Guardian of Adelia to fight against darkness.");
        story.add("But this will be a long journey, let's start");
        story.add("with meeting villagers in Roumen.");
        List<Task> tasks = new ArrayList<>();

        Task task = new TaskInteract(8);
        ItemStack boat = OtherItems.getBoat();
        GiveItemAction giveItemAction = new GiveItemAction(boat);
        SendTitleAction sendTitleAction = new SendTitleAction("", ChatColor.GREEN + "Obtained Boat");
        task.addOnCompleteAction(giveItemAction);
        task.addOnCompleteAction(sendTitleAction);
        tasks.add(task);

        Task task1 = new TaskInteract(13);
        GiveWeaponAction giveWeaponAction = new GiveWeaponAction(1, ItemTier.RARE, "Newbie", 1, 5, 2);
        SendTitleAction sendTitleAction1 = new SendTitleAction("", ChatColor.GREEN + "Obtained Weapon");
        task1.addOnCompleteAction(giveWeaponAction);
        task1.addOnCompleteAction(sendTitleAction1);
        tasks.add(task1);

        Task task2 = new TaskInteract(22);
        ItemStack hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(1, 3);
        ItemStack manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(1, 3);
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
                + "Then meet Sergeant Armin in building near city entrance\n";
        Quest quest = new Quest(6, "Another newbie?", story,
                "Time to meet villagers!", obj,
                "", tasks, itemPrizes, 10, 10, 0, 5,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 31, 32);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("Hobbits are having difficulties because of");
        story.add("strange behavior of creatures in forest.");
        story.add("This is your first task as a Guardian!");
        story.add("Can you save the Hobbit Village?");
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.\nDon't forget to use your boat!";
        Quest quest = new Quest(7, "Meet the hobbits!", story,
                startMsg, startMsg + "\nTalk with hobbit Village Elder Odo",
                "", tasks, itemPrizes, 10, 10, 0, 6,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 32, 33);
    }
}
