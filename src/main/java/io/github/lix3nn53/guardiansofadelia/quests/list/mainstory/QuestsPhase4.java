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

public class QuestsPhase4 {

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
        requiredQuests.add(26);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(27, "Elderine", story,
                startMsg, "Go to port city Elderine then talk with Captain Leanna",
                "", tasks, itemPrizes, 10, 6000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 32, 39);
    }

    private static void createQuestTwo() {
        List<String> story = new ArrayList<>();
        story.add("Villagers are having a hard time because of");
        story.add("reduction of resources. Do some fishing and");
        story.add("give them to villagers. So you can get a");
        story.add("warm welcome to Elderine.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(27);

        List<Task> tasks = new ArrayList<>();
        ItemStack itemStack = Ingredient.FISHING_COD.getItemStack(1);
        TaskGift taskGift = new TaskGift(5, itemStack, ChatColor.GREEN + "Elderine Villager");
        tasks.add(taskGift);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nThen talk back to Captain Lenna";
        Quest quest = new Quest(28, "Fishing #1", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 36000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 39, 39);
    }

    private static void createQuestThree() {
        List<String> story = new ArrayList<>();
        story.add("Pastry chef can't make tasty");
        story.add("cakes anymore!");
        story.add("Help Pastry Chef Jasper in Candy Valley.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(28);
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        String startMsg = ChatColor.YELLOW + "Click" + ChatColor.BOLD + " Compass Icon " + ChatColor.RESET + ChatColor.YELLOW + "from menu-book and select your destination NPC.";
        Quest quest = new Quest(29, "Candy Valley", story,
                startMsg, "Talk with Pastry Chef Jasper",
                "", tasks, itemPrizes, 10, 9000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 39, 38);
    }

    private static void createQuestFour() {
        List<String> story = new ArrayList<>();
        story.add("The tastes of candies in Candy Valley");
        story.add("became really bad after Aleesia's darkness");
        story.add("came upon us. Hunt down some bad candies.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(29);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.AQUA + "Popping Rainbow", 1);
        tasks.add(taskKill);
        TaskKill taskKill2 = new TaskKill(ChatColor.LIGHT_PURPLE + "Jellybean", 1);
        tasks.add(taskKill2);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nTASK_PROGRESS_2\nThen talk back to Pastry Chef Jasper";
        Quest quest = new Quest(30, "Wipe out the baddies", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 72000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 38, 38);
    }

    private static void createQuestFive() {
        List<String> story = new ArrayList<>();
        story.add("Collect some candies and bring");
        story.add("them to Jasper. So he can try to");
        story.add("find whats wrong with them.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(29);

        List<Task> tasks = new ArrayList<>();
        List<String> nameOfMobsItemDropsFrom = new ArrayList<>();
        nameOfMobsItemDropsFrom.add(ChatColor.AQUA + "Popping Rainbow");
        nameOfMobsItemDropsFrom.add(ChatColor.LIGHT_PURPLE + "Taffy Spirit");
        nameOfMobsItemDropsFrom.add(ChatColor.LIGHT_PURPLE + "Candy Box");
        nameOfMobsItemDropsFrom.add(ChatColor.LIGHT_PURPLE + "Jellybean");
        TaskCollect taskCollect = new TaskCollect(nameOfMobsItemDropsFrom, 0.64, QuestItems.getQuestItem(31), 1);
        tasks.add(taskCollect);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nfrom candies then talk back to Pastry Chef Jasper";
        Quest quest = new Quest(31, "Candy party!", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 72000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 38, 38);
    }

    private static void createQuestSix() {
        List<String> story = new ArrayList<>();
        story.add("Flying candies? What kind of magic is this?");
        story.add("Candies are supposed to taste good not fly.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(29);

        List<Task> tasks = new ArrayList<>();
        TaskKill taskKill = new TaskKill(ChatColor.LIGHT_PURPLE + "Taffy Spirit", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "TASK_PROGRESS_1\nthen talk back to Pastry Chef Jasper";
        Quest quest = new Quest(32, "Flying candies", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 72000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 38, 38);
    }

    private static void createQuestSeven() {
        List<String> story = new ArrayList<>();
        story.add("We have found that there is a evil cook");
        story.add("that spoils tastes of sugars.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(30);
        requiredQuests.add(31);
        requiredQuests.add(32);
        List<Task> tasks = new ArrayList<>();

        TaskKill taskKill = new TaskKill(ChatColor.YELLOW + "Evil Cook", 1);
        tasks.add(taskKill);

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "<Dungeon>\nTASK_PROGRESS_1\nthen talk to Pastry Chef Jasper";
        Quest quest = new Quest(33, "Evil cook", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 144000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 38, 38);
    }

    private static void createQuestEight() {
        List<String> story = new ArrayList<>();
        story.add("Report back that candies tastes");
        story.add("amazing one again.");
        List<Integer> requiredQuests = new ArrayList<>();
        requiredQuests.add(33);
        List<Task> tasks = new ArrayList<>();

        List<ItemStack> itemPrizes = new ArrayList<>();
        String objectiveText = "Report back to Captain Senna that candies tastes amazing one again.";
        Quest quest = new Quest(34, "Tasteful again", story,
                "", objectiveText,
                "", tasks, itemPrizes, 10, 12000, 0, requiredQuests,
                Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest, 38, 39);
    }
}
