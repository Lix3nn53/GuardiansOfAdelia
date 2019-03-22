package io.github.lix3nn53.guardiansofadelia.quests.list;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.quests.actions.ClearPotionEffectAction;
import io.github.lix3nn53.guardiansofadelia.quests.actions.PotionEffectAction;
import io.github.lix3nn53.guardiansofadelia.quests.actions.TeleportAction;
import io.github.lix3nn53.guardiansofadelia.quests.task.Task;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskDealDamage;
import io.github.lix3nn53.guardiansofadelia.quests.task.TaskKill;
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
        QuestNPCManager questNpcManager = GuardiansOfAdelia.getQuestNpcManager();

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
                ChatColor.LIGHT_PURPLE + "Hold" + ChatColor.BOLD + " TAB KEY " + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "to see you first quest!", "Talk to Arnas",
                "Learn your skills to get ready for fight. Press E, click on MenuBook on Top-Right of your inventory. Click to your character then skills. Give points by left clicking to learn skills.", tasks, itemPrizes, 0, 0, 90, 0,
                Material.RED_NETHER_BRICKS);
        questNpcManager.addQuest(quest1, 0, 5);

        //arnas to elysea
        List<String> story2 = new ArrayList<>();
        story2.add("We need more guardians to deal");
        story2.add("with darkness' foes!");
        List<Task> tasks2 = new ArrayList<>();
        TaskKill taskKill2 = new TaskKill(ChatColor.RED + "Malephar's Cavalry", 5);
        tasks2.add(taskKill2);
        Quest quest2 = new Quest(2, "To the rescue", story2, "Elysea needs your aid!", "Kill TASK_PROGRESS_1/5 Malephar's Cavalry then talk to Elysea",
                "Use your points to gain elemental power. Go to your character menu. Click elements then give points by left clicking.", tasks2, itemPrizes, 0, 0, 90, 1, Material.RED_NETHER_BRICKS);
        questNpcManager.addQuest(quest2, 5, 6);

        //elysea to syvia
        List<String> story3 = new ArrayList<>();
        story3.add("Syvia found Malephar, the darkness returns?");
        List<Task> tasks3 = new ArrayList<>();
        TaskKill taskKill3 = new TaskKill(ChatColor.RED + "Malephar's Guard", 5);
        tasks3.add(taskKill3);
        Quest quest3 = new Quest(3, "Syvia's gift", story3, "Time to test power of elements!", "Kill TASK_PROGRESS_1/5 Malephar's Guard then talk to Syvia",
                "More element power?! Press E then click bag icon on top of inventory. Equip your jewelries to gain bonus element powers.", tasks3, itemPrizes, 0, 0, 90, 2, Material.RED_NETHER_BRICKS);
        questNpcManager.addQuest(quest3, 6, 7);

        //syvia to malephar then to king of roumen
        List<String> story4 = new ArrayList<>();
        story4.add("We need to stop this before it harms Adelia!");
        List<Task> tasks4 = new ArrayList<>();
        TaskDealDamage taskDealDamage = new TaskDealDamage(ChatColor.RED + "Malephar", 200);
        tasks4.add(taskDealDamage);
        List<ItemStack> itemPrizes4 = new ArrayList<>();
        Quest quest4 = new Quest(4, "Hall of Darkness", story4, "Now you seem ready to face Malephar.. or are you?", "Time to face Malephar?!",
                "What **** happened?", tasks4, itemPrizes4, 0, 0, 90, 3, Material.RED_NETHER_BRICKS);

        PotionEffectAction potionEffectAction = new PotionEffectAction(PotionEffectType.BLINDNESS, 5 * 20, 3);
        Location teleportAfterTutorial = new Location(Bukkit.getWorld("world"), -3018.5, 95.5, 4920, -175.6f, -9.2f);
        TeleportAction teleportAction = new TeleportAction(teleportAfterTutorial, 10L);
        ClearPotionEffectAction clearPotionEffectAction = new ClearPotionEffectAction(PotionEffectType.WITHER);

        quest4.addOnCompleteAction(potionEffectAction);
        quest4.addOnCompleteAction(teleportAction);
        quest4.addOnCompleteAction(clearPotionEffectAction);
        questNpcManager.addQuest(quest4, 7, 19);
    }

}
