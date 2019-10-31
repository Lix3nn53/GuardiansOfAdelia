package io.github.lix3nn53.guardiansofadelia.quests.list;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemList;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.actions.*;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskDealDamage;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskKill;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class TutorialQuests {

    public static void createQuests() {

        //start to arnas
        List<String> story = new ArrayList<>();
        story.add("Adelia had been living in peace for a long time so");
        story.add("the need for Guardians decreased over time.");
        story.add("You are one of a few Guardians of this era.");
        story.add("Suddenly a strange dark castle appeared");
        story.add("in wild lands of Adelia.");
        story.add("Can Adelia overcome this threat after such a long time?");
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        Quest quest1 = new Quest(1, "Entrance to the unknown", story,
                ChatColor.LIGHT_PURPLE + "Hold" + ChatColor.BOLD + " TAB KEY " + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "to see your quest list!", "Talk to Arnas!",
                "Learn your skills to get ready for fight. Open menu by clicking Menu-Book from your inventory(E key), then learn your skills from character menu to get ready to fight!",
                tasks, itemPrizes, 0, 0, 90, 0, Material.RED_NETHER_BRICKS);
        QuestNPCManager.addQuest(quest1, 0, 5);

        //arnas to elysea
        List<String> story2 = new ArrayList<>();
        story2.add("We need more guardians to deal");
        story2.add("with darkness' foes!");
        List<Task> tasks2 = new ArrayList<>();
        TaskKill taskKill2 = new TaskKill(ChatColor.RED + "Aleesia's Soldier", 5);
        tasks2.add(taskKill2);
        Quest quest2 = new Quest(2, "To the rescue", story2, "Elysea needs your aid!", "Open menu by clicking Menu-Book from your inventory(E key), then.." +
                "\n..learn your skills from character menu to get ready to fight!" +
                "\nKill TASK_PROGRESS_1/5 Aleesia's Cavalry then talk to " +
                "Elysea\n",
                "Spend your element points from character-menu to get stronger.",
                tasks2, itemPrizes, 0, 0, 90, 1, Material.RED_NETHER_BRICKS);
        QuestNPCManager.addQuest(quest2, 5, 6);

        //elysea to syvia
        List<ItemStack> itemPrizes1 = new ArrayList<>();
        itemPrizes1.add(PassiveItemList.get(5, RPGSlotType.PARROT, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        itemPrizes1.add(PassiveItemList.get(10, RPGSlotType.EARRING, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        itemPrizes1.add(PassiveItemList.get(10, RPGSlotType.GLOVE, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        itemPrizes1.add(PassiveItemList.get(10, RPGSlotType.RING, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        itemPrizes1.add(PassiveItemList.get(10, RPGSlotType.NECKLACE, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        List<String> story3 = new ArrayList<>();
        story3.add("Syvia saw Aleesia, the darkness returns?");
        List<Task> tasks3 = new ArrayList<>();
        TaskKill taskKill3 = new TaskKill(ChatColor.RED + "Aleesia's Ranger", 5);
        tasks3.add(taskKill3);
        Quest quest3 = new Quest(3, "Syvia's gift", story3, "Time to test power of elements!", "Spend your element points from character-menu to get stronger." +
                "\nKill TASK_PROGRESS_1/5 " +
                "Aleesia's Ranger then talk to Syvia\n",
                "Equip jewelries from RPG-Inventory to gain even more elemental power! To open RPG-Inventory click one of crafting slots from your inventory.",
                tasks3, itemPrizes1, 0, 0, 90, 2, Material.RED_NETHER_BRICKS);
        QuestNPCManager.addQuest(quest3, 6, 7);

        //syvia to Aleesia
        List<String> story4 = new ArrayList<>();
        story4.add("We need to stop this before it harms Adelia!");
        List<Task> tasks4 = new ArrayList<>();
        TaskDealDamage taskDealDamage = new TaskDealDamage(ChatColor.DARK_PURPLE + "Aleesia", 200);
        tasks4.add(taskDealDamage);
        Quest quest4 = new Quest(4, "Hall of Darkness", story4, "Now you seem ready to face Aleesia.. or are you?", "Equip jewelries from RPG-Inventory to gain even more elemental power!" +
                "\nTo open RPG-Inventory click one of crafting slots from your inventory.\nNow you seem ready to face Aleesia.. or are you?\n" + ChatColor.LIGHT_PURPLE + "Enter the portal(Left-Mouse-Click when nearby)",
                "", tasks4, itemPrizes, 0, 0, 90, 3, Material.RED_NETHER_BRICKS);

        PotionEffectAction potionEffectAction = new PotionEffectAction(PotionEffectType.BLINDNESS, 5 * 20, 3);
        Location teleportAfterTutorial = new Location(Bukkit.getWorld("world"), -3018.5, 95.5, 4920, -175.6f, -9.2f);
        TeleportAction teleportAction = new TeleportAction(teleportAfterTutorial, 10L);
        ClearPotionEffectAction clearPotionEffectAction = new ClearPotionEffectAction(PotionEffectType.WITHER);

        quest4.addOnCompleteAction(potionEffectAction);
        quest4.addOnCompleteAction(teleportAction);
        quest4.addOnCompleteAction(clearPotionEffectAction);
        quest4.addOnCompleteAction(new SendTitleAction(ChatColor.DARK_PURPLE + "Waking up..", ""));
        quest4.addOnCompleteAction(new FinishQuestAction(4));
        quest4.addOnCompleteAction(new StartQuestAction(5));
        quest4.addOnCompleteAction(new TutorialEndAction());
        QuestNPCManager.addQuest(quest4, 7, 0);

        //Aleesia to king of roumen
        List<String> story5 = new ArrayList<>();
        story5.add("You woke up from a dream of the past.");
        story5.add("Guardians lost against Aleesia brought darkness to");
        story5.add("our world, Adelia.");
        List<Task> tasks5 = new ArrayList<>();
        List<ItemStack> itemPrizes5 = new ArrayList<>();
        Quest quest5 = new Quest(5, "A fresh start", story5,
                "Get out of the ship and follow the path to meet King of Roumen", "Talk with King of Roumen to learn what happened",
                "",
                tasks5, itemPrizes5, 0, 0, 1, 4, Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest5, 0, 31);
    }

}
