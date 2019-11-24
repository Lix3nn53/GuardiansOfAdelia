package io.github.lix3nn53.guardiansofadelia.quests.list.mainstory;

import io.github.lix3nn53.guardiansofadelia.Items.Ingredient;
import io.github.lix3nn53.guardiansofadelia.Items.list.QuestItems;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskCollect;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskGift;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskKill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class QuestsPhase7 {

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
    }

    private static void createQuestOne() {
        List<String> story = new ArrayList<>();
        story.add("Congratulations! You improved a lot since");
        story.add("you you have become a Guardian. You are ready");
        story.add("to move the port city, Elderine.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(50);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(51, "Uruga", story,
                startMsg, "Go to port city Elderine then talk with Captain Leanna",
                "", tasks, itemPrizes, 10, 35000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 39, 42);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("Villagers are having a hard time because of");
        story.add("reduction of resources. Do some fishing and");
        story.add("give them to villagers. So you can get a");
        story.add("warm welcome to Elderine.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(51);

        List<Task> tasks = new ArrayList<>();
        ItemStack itemStack = Ingredient.FISHING_SALMON.getItemStack(1);
        TaskGift taskGift = new TaskGift(5, itemStack, ChatColor.GREEN + "Uruga Villager");
        tasks.add(taskGift);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nThen talk back to Captain Lenna";
        Quest quest = new Quest(52, "Fishing #2", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 160000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 42, 42);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("Congratulations! You improved a lot since");
        story.add("you you have become a Guardian. You are ready");
        story.add("to move the port city, Elderine.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(52);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(53, "Sand Hill", story,
                startMsg, "Go to port city Elderine then talk with Captain Leanna",
                "", tasks, itemPrizes, 10, 40000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 42, 43);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(53);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Mummy", 1);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.YELLOW + "Ghost Mummy", 1);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Pastry Chef Jasper";
        Quest quest = new Quest(54, "Mummy hunt #1", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 320000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 43, 43);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(53);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Ghost Spider", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Pastry Chef Jasper";
        Quest quest = new Quest(55, "Ghost Spider hunt", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 320000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 43, 43);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("Collect some candies and bring");
        story.add("them to Jasper. So he can try to");
        story.add("find whats wrong with them.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(53);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Mummy");
        nameOfMobsItemDropsFrom.add(ChatColor.YELLOW + "Ghost Mummy");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.64, QuestItems.getQuestItem(31), 1);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nfrom candies then talk back to Pastry Chef Jasper";
        Quest quest = new Quest(56, "Mummy bandages", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 320000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 43, 43);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("We have found that there is a evil cook");
        story.add("that spoils tastes of sugars.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(53);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Desert Archer Skeleton", 1);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.YELLOW + "Desert Cavalry Skeleton", 1);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nthen talk to Pastry Chef Jasper";
        Quest quest = new Quest(57, "Desert-skeleton hunt", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 320000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 43, 43);
    }

    private static void createQuestEight() {
        List<String> story = new ArrayList<>();
        story.add("We have found that there is a evil cook");
        story.add("that spoils tastes of sugars.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(54);
        requiredQuests.add(55);
        requiredQuests.add(56);
        requiredQuests.add(57);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Pharaoh", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nthen talk to Pastry Chef Jasper";
        Quest quest = new Quest(58, "The Cursed King", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 640000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 43, 43);
    }

    private static void createQuestNine() {
        List<String> story = new ArrayList<>();
        story.add("Report back that candies tastes");
        story.add("amazing one again.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(58);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Captain Senna that candies tastes amazing one again.";
        Quest quest = new Quest(59, "Sand Hill report", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 45000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 43, 42);
    }
}
