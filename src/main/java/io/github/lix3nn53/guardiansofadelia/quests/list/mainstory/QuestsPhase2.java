package io.github.lix3nn53.guardiansofadelia.quests.list.mainstory;

import io.github.lix3nn53.guardiansofadelia.Items.list.QuestItems;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskCollect;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskKill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QuestsPhase2 {

    public static void createQuests() {
        createQuestOne();
        createQuestTwo();
        createQuestThree();
        createQuestFour();
        createQuestFive();
        createQuestSix();
        createQuestSeven();
    }

    private static void createQuestOne() {
        List<String> story = new ArrayList<>();
        story.add("Dr. Rintarou is working to find a cure for");
        story.add("zombies. Go to the camp near Rotten City");
        story.add("and ask Dr. Rintarou how you can help.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(11);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.\nDon't forget to use your boat!";
        Quest quest = new Quest(12, "Rotten City", story,
                startMsg, "Talk with Dr. Rintarou",
                "", tasks, itemPrizes, 7, 800, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 32, 35);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("Dr. Rintarou wants you to enter");
        story.add("Rotten City and see if you can handle");
        story.add("a few zombies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(12);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.DARK_GREEN + "Zombie", 12);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.DARK_GREEN + "Zombie Villager", 8);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Time to enter Rotten City\nTASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk to Dr. Rintarou";
        Quest quest = new Quest(13, "Zombie invasion", story,
                "", objectiveText,
                "", tasks, itemPrizes, 7, 5000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 35);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("Dr. Rintarou needs zombie parts");
        story.add("for his experiments.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(12);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Zombie");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Zombie Villager");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Splitter Zombie");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Shaman Zombie");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Tank Zombie");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.72, QuestItems.getQuestItem(14), 9);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Dr. Rintarou needs zombie parts for his experiments\nTASK_PROGRESS_1\nThen talk to Dr. Rintarou";
        Quest quest = new Quest(14, "Disgusting experiments", story,
                "", objectiveText,
                "", tasks, itemPrizes, 7, 5000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 35);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("Dr. Rintarou needs zombie brains");
        story.add("for his final experiment.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(12);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Zombie");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Zombie Villager");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Splitter Zombie");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Shaman Zombie");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_GREEN + "Tank Zombie");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.64, QuestItems.getQuestItem(15), 9);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Dr. Rintarou needs zombie brains for his final experiment\nTASK_PROGRESS_1\nThen talk to Dr. Rintarou";
        Quest quest = new Quest(15, "Pink jellies", story,
                "", objectiveText,
                "", tasks, itemPrizes, 7, 5000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 35);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("Report to Sergeant Armin that you helped");
        story.add("Dr. Rintarou on his final experiment and");
        story.add("waiting for results.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(13);
        requiredQuests.add(14);
        requiredQuests.add(15);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report to Sergent Armin that you helped Dr. Rintarou on his final experiment";
        Quest quest = new Quest(16, "Experiment reports", story,
                "", objectiveText,
                "", tasks, itemPrizes, 7, 2500, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 32);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("Sergeant Armin's troops found a strange");
        story.add("zombie that seems really dangerous.");
        story.add("Stop that zombie then ask Dr. Rintarou");
        story.add("about it.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(16);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.DARK_GREEN + "Zombie Subject#471", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nThen talk to Dr. Rintarou";
        Quest quest = new Quest(17, "Zombie subjects!?", story,
                "", objectiveText,
                "", tasks, itemPrizes, 7, 11000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 32, 35);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("Dr. Rintarou created stronger zombies");
        story.add("to help the fight against darkness.");
        story.add("But now that Guardians defeated his");
        story.add("strongest experiment, he gave up and");
        story.add("started working on a cure for zombies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(17);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Sergent Armin that Dr. Rintarou started working on a cure!";
        Quest quest = new Quest(18, "Back to his senses", story,
                "", objectiveText,
                "", tasks, itemPrizes, 7, 2000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 32);
    }
}
