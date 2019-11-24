package io.github.lix3nn53.guardiansofadelia.quests.list.mainstory;

import io.github.lix3nn53.guardiansofadelia.Items.list.QuestItems;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskCollect;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskKill;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskReach;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QuestsPhase5 {

    public static void createQuests() {
        createQuestOne();
        createQuestTwo();
        createQuestThree();
        createQuestFour();
        createQuestFive();
        createQuestSix();
        createQuestSeven();
        createQuestEight();
    }

    private static void createQuestOne() {
        List<String> story = new ArrayList<>();
        story.add("Congratulations! You improved a lot since");
        story.add("you you have become a Guardian. You are ready");
        story.add("to move the port city, Elderine.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(34);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(35, "Sea of Greed", story,
                startMsg, "Go to port city Elderine then talk with Captain Leanna",
                "", tasks, itemPrizes, 10, 12000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 39, 40);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(35);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.DARK_AQUA + "Shooter Pirate", 1);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.DARK_AQUA + "Fighter Pirate", 1);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Pastry Chef Jasper";
        Quest quest = new Quest(36, "Pirate hunt #1", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 120000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 40, 40);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(35);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.DARK_AQUA + "Drowned Pirate", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Pastry Chef Jasper";
        Quest quest = new Quest(37, "Pirate hunt #2", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 120000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 40, 40);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(35);

        List<Task> tasks = new ArrayList<>();
        Location reachLocation = new Location(Bukkit.getWorld("world"), -3018.5, 95.5, 4920);
        TaskReach taskReach = new TaskReach(reachLocation, Material.GOLD_BLOCK);
        tasks.add(taskReach);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Pastry Chef Jasper";
        Quest quest = new Quest(38, "Treasure hunt", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 120000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 40, 40);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("Collect some candies and bring");
        story.add("them to Jasper. So he can try to");
        story.add("find whats wrong with them.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(35);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_AQUA + "Shooter Pirate");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_AQUA + "Fighter Pirate");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_AQUA + "Sharpshooter Pirate");
        nameOfMobsItemDropsFrom.add(ChatColor.DARK_AQUA + "Duel Master Pirate");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.64, QuestItems.getQuestItem(31), 1);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nfrom candies then talk back to Pastry Chef Jasper";
        Quest quest = new Quest(39, "Pirate hats", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 120000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 40, 40);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("We have found that there is a evil cook");
        story.add("that spoils tastes of sugars.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(35);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.DARK_AQUA + "Sharpshooter Pirate", 1);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.DARK_AQUA + "Duel Master Pirate", 1);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nthen talk to Pastry Chef Jasper";
        Quest quest = new Quest(40, "Pirate hunt #3", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 120000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 40, 40);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("We have found that there is a evil cook");
        story.add("that spoils tastes of sugars.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(36);
        requiredQuests.add(37);
        requiredQuests.add(38);
        requiredQuests.add(39);
        requiredQuests.add(40);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.DARK_AQUA + "Captain Barbaros", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nthen talk to Pastry Chef Jasper";
        Quest quest = new Quest(41, "The captain's soul", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 240000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 40, 40);
    }

    private static void createQuestEight() {
        List<String> story = new ArrayList<>();
        story.add("Report back that candies tastes");
        story.add("amazing one again.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(41);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Captain Senna that candies tastes amazing one again.";
        Quest quest = new Quest(42, "Sea of Greed report", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 18000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 40, 39);
    }
}
