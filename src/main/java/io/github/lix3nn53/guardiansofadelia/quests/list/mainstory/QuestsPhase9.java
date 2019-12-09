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

public class QuestsPhase9 {

    public static void createQuests() {
        createQuestOne();
        createQuestTwo();
        createQuestThree();
        createQuestFour();
        createQuestFive();
        createQuestSix();
        createQuestSeven();
        createQuestEight();
        createQuestNine();
        createQuestTen();
        createQuestEleven();
        createQuestTwelve();
    }

    private static void createQuestOne() {
        List<String> story = new ArrayList<>();
        story.add("You have become one of the strongest");
        story.add("Guardians in Adelia. You must go");
        story.add("to Alberstol Ruins, fallen city of angels.");
        story.add("But to do so, you need permission from the Gatekeeper.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(67);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(68, "Gatekeeper", story,
                startMsg, "Talk with the Gatekeeper in Uruga",
                "", tasks, itemPrizes, 10, 53000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 42, 47);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("To be able to use the portal you");
        story.add("must complete Gatekeeper's trial.");
        story.add("Gatekeeper wants you to conquer the tower");
        story.add("at Burning Rock's entrance.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(68);

        List<Task> tasks = new ArrayList<>();
        Location reachLocation = new Location(Bukkit.getWorld("world"), -3311.5, 89.5, 2207);
        TaskReach taskReach = new TaskReach(reachLocation, Material.GOLD_BLOCK);
        tasks.add(taskReach);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nThen talk back to the Gatekeeper";
        Quest quest = new Quest(69, "Gatekeeper's trial", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 212000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 47, 47);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("You can enter the portal to Alberstol Ruins.");
        story.add("Go and talk with the Archangel.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(69);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(70, "Alberstol Ruins", story,
                startMsg, "Go to city of angels, Alberstol Ruins then talk with Archangel",
                "", tasks, itemPrizes, 10, 53000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 47, 49);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("Orcs have become very aggressive lately.");
        story.add("Talk with Vruhag, an orc guardian, to learn");
        story.add("whats happening.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(70);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(71, "Burning Rock", story,
                startMsg, "Go to the Burning Rock then talk with Vruhag",
                "", tasks, itemPrizes, 10, 53000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 49, 48);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("Fire monsters are destroying the land.");
        story.add("We have to stop them for good.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(71);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.RED + "Blaze", 60);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.RED + "Magma Cube", 60);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Vruhag";
        Quest quest = new Quest(72, "Flaming hunt", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 424000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 48, 48);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("Orcs are being controlled Aleesia's");
        story.add("dark magic. We have to stop them.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(71);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Fighter Orc", 72);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.YELLOW + "Gladiator Orc", 72);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Vruhag";
        Quest quest = new Quest(73, "Orc hunt 1", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 424000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 48, 48);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("Orcs are being controlled Aleesia's");
        story.add("dark magic. We have to stop them.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(71);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Shaman Orc", 60);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.YELLOW + "Mage Orc", 60);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Vruhag";
        Quest quest = new Quest(74, "Orc hunt 2", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 424000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 48, 48);
    }

    private static void createQuestEight() {
        List<String> story = new ArrayList<>();
        story.add("Find out what jockeys are defending");
        story.add("inside the cave.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(71);

        List<Task> tasks = new ArrayList<>();
        Location reachLocation = new Location(Bukkit.getWorld("world"), -3998.5, 23.5, 2193);
        TaskReach taskReach = new TaskReach(reachLocation, Material.DIAMOND_BLOCK);
        tasks.add(taskReach);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nThen talk back to Vruhag";
        Quest quest = new Quest(75, "gggggggggggg", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 424000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 48, 48);
    }

    private static void createQuestNine() {
        List<String> story = new ArrayList<>();
        story.add("Collect some of the flags orcs");
        story.add("are carrying to identify.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(71);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Fighter Orc");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Gladiator Orc");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Jockey Orc");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Shaman Orc");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Mage Orc");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.64, QuestItems.getQuestItem(76), 60);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nfrom orcs then talk back to Vruhag";
        Quest quest = new Quest(76, "Fire spirit", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 424000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 48, 48);
    }

    private static void createQuestTen() {
        List<String> story = new ArrayList<>();
        story.add("Orcs are being controlled Aleesia's");
        story.add("dark magic. We have to stop them.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(71);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Jockey Orc", 45);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nthen talk to Vruhag";
        Quest quest = new Quest(77, "Orc hunt 3", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 424000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 48, 48);
    }

    private static void createQuestEleven() {
        List<String> story = new ArrayList<>();
        story.add("We have found basement of the");
        story.add("orc warchief, Drogoth. If we defeat");
        story.add("him, it might remove the curse.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(72);
        requiredQuests.add(73);
        requiredQuests.add(74);
        requiredQuests.add(75);
        requiredQuests.add(76);
        requiredQuests.add(77);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.RED + "Warchief Drogoth", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nthen talk to Vruhag";
        Quest quest = new Quest(78, "The Warchief", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 848000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 48, 48);
    }

    private static void createQuestTwelve() {
        List<String> story = new ArrayList<>();
        story.add("Report back that the orcs");
        story.add("regained their will.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(78);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Archangel orcs regained their will";
        Quest quest = new Quest(79, "Burning rock report", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 53000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 48, 49);
    }
}
