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
        story.add("Kahal Plains is a important bridge");
        story.add("that connects Elderine and Uruga.");
        story.add("But frozen monsters is a threat");
        story.add("to travelers. Find viking warrior Ashild");
        story.add("who is fighting frozens and help her.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(42);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(43, "Kahal Plains", story,
                startMsg, "Cross the Sea of Greed and talk to Ashild in Kahal Plains",
                "", tasks, itemPrizes, 27, 16000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 39, 41);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("Hunt frozens to make the bridge to Uruga safer.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.AQUA + "Frozen Rogue", 47);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.AQUA + "Frozen Ranger", 47);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Ashild";
        Quest quest = new Quest(44, "Frozen hunt 1", story,
                "", objectiveText,
                "", tasks, itemPrizes, 27, 160000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("Jockeys are defending the cave, hunt");
        story.add("them to be able to discover the cave.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.DARK_GREEN + "Jockey Zombie", 35);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nThen talk back to Ashild";
        Quest quest = new Quest(45, "Little jockeys", story,
                "", objectiveText,
                "", tasks, itemPrizes, 27, 160000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("Find out what jockeys are defending");
        story.add("inside the cave.");
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
                "", tasks, itemPrizes, 27, 160000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("Collect some frozen shards and bring");
        story.add("them to Ashild.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Frozen Rogue");
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Frozen Illusioner");
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Frozen Ranger");
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Frozen Timberman");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.64, QuestItems.getQuestItem(47), 30);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nfrom frozens then talk back to Ashild";
        Quest quest = new Quest(47, "Frozen shards", story,
                "", objectiveText,
                "", tasks, itemPrizes, 27, 160000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("Some of the frozens are more dangerous than others.");
        story.add("Hunt them to make the bridge to Uruga safer.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(43);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.AQUA + "Frozen Illusioner", 40);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.AQUA + "Frozen Timberman", 40);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nthen talk to Ashild";
        Quest quest = new Quest(48, "Frozen hunt 2", story,
                "", objectiveText,
                "", tasks, itemPrizes, 27, 160000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("Ashild discovered a necromancer cave.");
        story.add("That necromancer must be the reason of frozens.");
        story.add("Defeat him and clear frozens for good.");
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
                "", tasks, itemPrizes, 27, 320000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 41);
    }

    private static void createQuestEight() {
        List<String> story = new ArrayList<>();
        story.add("Report back that Necromancer King is defeated so");
        story.add("Kahal Plains are safe for travelers again.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(49);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Captain Lenna, Kahal Plains are safe for travelers again";
        Quest quest = new Quest(50, "Kahal Plains report", story,
                "", objectiveText,
                "", tasks, itemPrizes, 27, 24000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 41, 39);
    }
}
