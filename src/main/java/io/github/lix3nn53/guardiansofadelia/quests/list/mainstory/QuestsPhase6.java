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

public class QuestsPhase6 {

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
        requiredQuests.add(42);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(43, "Kahal Plains", story,
                startMsg, "Cross the Sea of Greed and talk to Ashild in Kahal Plains",
                "", tasks, itemPrizes, 10, 20000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 39, 41);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.AQUA + "Frozen Rogue", 1);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.AQUA + "Frozen Ranger", 1);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Ashild";
        Quest quest = new Quest(44, "Frozen hunt 1", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 200000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.DARK_GREEN + "Jockey Zombie", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nThen talk back to Ashild";
        Quest quest = new Quest(45, "Little jockeys", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 200000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);

        List<Task> tasks = new ArrayList<>();
        Location reachLocation = new Location(Bukkit.getWorld("world"), -2039.5, 7.5, 3133.5);
        TaskReach taskReach = new TaskReach(reachLocation, Material.EMERALD_BLOCK);
        tasks.add(taskReach);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nThen talk back to Ashild";
        Quest quest = new Quest(46, "Bottom of the cave", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 200000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("Collect some candies and bring");
        story.add("them to Jasper. So he can try to");
        story.add("find whats wrong with them.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Frozen Rogue");
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Frozen Illusioner");
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Frozen Ranger");
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Frozen Timberman");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.64, QuestItems.getQuestItem(31), 1);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nfrom frozens then talk back to Ashild";
        Quest quest = new Quest(47, "Frozen shards", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 200000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("We have found that there is a evil cook");
        story.add("that spoils tastes of sugars.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.AQUA + "Frozen Illusioner", 1);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.AQUA + "Frozen Timberman", 1);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nthen talk to Ashild";
        Quest quest = new Quest(48, "Frozen hunt 2", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 200000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("We have found that there is a evil cook");
        story.add("that spoils tastes of sugars.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(44);
        requiredQuests.add(45);
        requiredQuests.add(46);
        requiredQuests.add(47);
        requiredQuests.add(48);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.DARK_PURPLE + "Necromancer King", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nthen talk to Ashild";
        Quest quest = new Quest(49, "The necromancer", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 400000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestEight() {
        List<String> story = new ArrayList<>();
        story.add("Report back that candies tastes");
        story.add("amazing one again.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(49);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Captain Senna that Necromancer King is defeated";
        Quest quest = new Quest(50, "Kahal Plains report", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 30000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 39);
    }
}
