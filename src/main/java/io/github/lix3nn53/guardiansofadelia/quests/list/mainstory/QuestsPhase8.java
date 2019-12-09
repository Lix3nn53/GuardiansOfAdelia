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

public class QuestsPhase8 {

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
        story.add("A guardian is lost! Talk with his");
        story.add("party, they are camping near the");
        story.add("2nd entrance of Uruga.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(59);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(60, "Lost guardian", story,
                startMsg, "Go to guardian camp and talk with Guardian Raignald",
                "", tasks, itemPrizes, 10, 50000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 42, 44);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("The party thinks goblins are keeping");
        story.add("Esobel as prisoner. Hunt some goblins");
        story.add("to find clues.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(60);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Fighter Goblin", 64);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.YELLOW + "Rogue Goblin", 64);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Guardian Raignald";
        Quest quest = new Quest(61, "Goblin hunt 1", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 440000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 44, 44);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("The party thinks goblins are keeping");
        story.add("Esobel as prisoner. Hunt some goblins");
        story.add("to find clues.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(60);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Jockey Goblin", 50);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nThen talk back to Guardian Raignald";
        Quest quest = new Quest(62, "Goblin hunt 2", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 440000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 44, 44);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("The party thinks goblins are keeping");
        story.add("Esobel as prisoner. Hunt some goblins");
        story.add("to find clues.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(60);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Mage Goblin", 50);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.YELLOW + "Shaman Goblin", 50);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Guardian Iohne";
        Quest quest = new Quest(63, "Goblin hunt 3", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 440000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 45, 45);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("Collect some of the flags goblins");
        story.add("are carrying to identify.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(60);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Fighter Goblin");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Rogue Goblin");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Shaman Goblin");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Mage Goblin");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Jockey Goblin");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.64, QuestItems.getQuestItem(64), 47);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nfrom goblins then talk back to Guardian Raignald";
        Quest quest = new Quest(64, "Goblin", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 440000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 44, 44);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("Some dark magic users are");
        story.add("walking around goblins.");
        story.add("Maybe they are the ones controlling");
        story.add("goblins and bringing them together?");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(60);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.DARK_PURPLE + "Witch", 41);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nthen talk to Guardian Afra";
        Quest quest = new Quest(65, "Witch hunt", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 440000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 46, 46);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("We have found where they are");
        story.add("keeping Esobel hostage.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(61);
        requiredQuests.add(62);
        requiredQuests.add(63);
        requiredQuests.add(64);
        requiredQuests.add(65);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.BLUE + "Guardian Esobel", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nthen talk to Guardian Raignald";
        Quest quest = new Quest(66, "The betrayal", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 880000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 44, 44);
    }

    private static void createQuestEight() {
        List<String> story = new ArrayList<>();
        story.add("Report back that Esobel's lust for power");
        story.add("made him a pray for darkness.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(66);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Commander Erwin that Esobel is stopped";
        Quest quest = new Quest(67, "Swamp of Dawn report", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 60000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 44, 42);
    }
}
