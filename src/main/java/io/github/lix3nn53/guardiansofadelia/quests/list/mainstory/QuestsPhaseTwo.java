package io.github.lix3nn53.guardiansofadelia.quests.list.mainstory;

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

public class QuestsPhaseTwo {

    public static void createQuests() {
        createQuestOne();
        createQuestTwo();
        createQuestThree();
        createQuestFour();
        createQuestFive();
        createQuestSix();
    }

    private static void createQuestOne() {
        List<String> story = new ArrayList<>();
        story.add("Dr. Rintarou is working to find a cure for");
        story.add("zombies. Go to the camp near Rotten City");
        story.add("and ask Dr. Rintarou how you can help.");
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.\nDon't forget to use your boat!";
        Quest quest = new Quest(12, "The Rotten City!", story,
                startMsg, "Talk with Dr. Rintarou",
                "", tasks, itemPrizes, 10, 10, 0, 11,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 32, 35);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("Hobbit Adventurer Milo is fighting alone in");
        story.add("Forest of Slumber. Help him to restore the");
        story.add("peace in the forest.");

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.DARK_GREEN + "Zombie", 12);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.DARK_GREEN + "Zombie Villager", 8);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Time to enter Rotten City\nKill TASK_PROGRESS_1/12 Zombie and TASK_PROGRESS_2/8 Zombie Villager\nThen talk to Dr. Rintarou";
        Quest quest = new Quest(13, "Zombie invasion!", story,
                "startMsg", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 12,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 35);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("Hobbit Adventurer Milo is fighting alone in");
        story.add("Forest of Slumber. Help him to restore the");
        story.add("peace in the forest.");

        List<Task> tasks = new ArrayList<>();
        TaskCollect taskCollect = new TaskCollect(ChatColor.DARK_GREEN + "Rotten Flesh", 12);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Dr. Rintarou needs zombie parts for his experiments\nCollect TASK_PROGRESS_1/12 Rotten Flesh\nThen talk to Dr. Rintarou";
        Quest quest = new Quest(14, "Disgusting experiments", story,
                "startMsg", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 13,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 35);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("Hobbit Adventurer Milo is fighting alone in");
        story.add("Forest of Slumber. Help him to restore the");
        story.add("peace in the forest.");

        List<Task> tasks = new ArrayList<>();
        TaskCollect taskCollect = new TaskCollect(ChatColor.LIGHT_PURPLE + "Zombie Brain", 12);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Dr. Rintarou needs zombie brains for his final experiment\nCollect TASK_PROGRESS_1/18 Zombie Brain\nThen talk to Dr. Rintarou";
        Quest quest = new Quest(15, "Disgusting experiments", story,
                "startMsg", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 14,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 35);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("Lets bring the good news to hobbit elder then");
        story.add("report to Sergeant Armin.");
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report to Sergent Armin that you helped Dr. Rintarou finish his experiments";
        Quest quest = new Quest(16, "Good news!", story,
                "startMsg", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 15,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 32);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("We have found the magical portal that");
        story.add("slimes comes from. Lets go in and stop them!");

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.DARK_GREEN + "Zombie Subject#471", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Kill the zombie Dr. Rintarou enchanted TASK_PROGRESS_1/1\nThen talk to Dr. Rintarou";
        Quest quest = new Quest(17, "Arcade dungeon!", story,
                "startMsg", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 16,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 34, 34);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("Report back to Sergeant Armin");
        story.add("report to Sergeant Armin.");
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Sergent Armin, Dr. Rintarou started working on a cure!";
        Quest quest = new Quest(18, "Good news!", story,
                "startMsg", objectiveText,
                "", tasks, itemPrizes, 10, 10, 0, 17,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 35, 32);
    }
}
