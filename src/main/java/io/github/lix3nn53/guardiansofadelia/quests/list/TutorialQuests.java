package io.github.lix3nn53.guardiansofadelia.quests.list;

public class TutorialQuests {

    public static void createQuests() {

        /*//start to arnas
        List<String> story = new ArrayList<>();
        story.add("Adelia had been living in peace for a long time so");
        story.add("the need for Guardians decreased over time.");
        story.add("You are one of a few Guardians of this era.");
        story.add("Suddenly a strange dark castle appeared");
        story.add("in wild lands of Adelia.");
        story.add("Can Adelia overcome this threat after such a long time?");
        List<Task> tasks = new ArrayList<>();
        List<ItemStack> itemPrizes = new ArrayList<>();
        List<Integer> requiredQuests = new ArrayList<>();
        Quest quest1 = new Quest(1, "Entrance to the unknown", story,
                ChatColor.YELLOW + "Hold" + ChatColor.BOLD + ChatColor.GOLD + " TAB KEY " + ChatColor.RESET + ChatColor.YELLOW + "to see your quest list!", "Talk to Arnas!",
                "Accept your new quest from Arnas",
                tasks, itemPrizes, 0, 0, 90, requiredQuests, Material.RED_NETHER_BRICKS);
        QuestNPCManager.addQuest(quest1, 0, 9);

        //arnas to elysea
        List<Integer> requiredQuests2 = new ArrayList<>();
        requiredQuests2.add(1);
        List<String> story2 = new ArrayList<>();
        story2.add("We need more guardians to deal");
        story2.add("with darkness' foes!");
        List<Task> tasks2 = new ArrayList<>();
        TaskKill taskKill2 = new TaskKill(ChatColor.RED + "Aleesia's Soldier", 5);
        tasks2.add(taskKill2);
        Quest quest2 = new Quest(2, "To the rescue", story2, "Learn your skills to get ready! Open menu by clicking Menu-Book from your inventory(E key), then learn your skills from character menu to get ready to fight!", "Open menu by clicking Menu-Book from your inventory(E key), then.." +
                "\n..learn your skills from character menu to get ready to fight!" +
                "\nTASK_PROGRESS_1 then talk to " +
                "Elysea\n",
                "Accept your new quest from Elysea",
                tasks2, itemPrizes, 0, 0, 90, requiredQuests2, Material.RED_NETHER_BRICKS);
        QuestNPCManager.addQuest(quest2, 9, 10);

        //elysea to syvia
        List<Integer> requiredQuests3 = new ArrayList<>();
        requiredQuests3.add(2);
        List<ItemStack> itemPrizes1 = new ArrayList<>();
        itemPrizes1.add(PassiveItemList.get(9, 0, RPGSlotType.PARROT, ItemTier.LEGENDARY, "Tutorial", 10, 20, 2));
        itemPrizes1.add(PassiveItemList.get(9, 0, RPGSlotType.EARRING, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        itemPrizes1.add(PassiveItemList.get(9, 0, RPGSlotType.GLOVE, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        itemPrizes1.add(PassiveItemList.get(9, 0, RPGSlotType.RING, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        itemPrizes1.add(PassiveItemList.get(9, 0, RPGSlotType.NECKLACE, ItemTier.COMMON, "Tutorial", 10, 20, 2));
        List<String> story3 = new ArrayList<>();
        story3.add("Syvia saw Aleesia, the darkness returns?");
        List<Task> tasks3 = new ArrayList<>();
        TaskKill taskKill3 = new TaskKill(ChatColor.RED + "Aleesia's Ranger", 5);
        tasks3.add(taskKill3);
        Quest quest3 = new Quest(3, "Syvia's gift", story3, "Spend your element points from character-menu to get stronger.", "Spend your element points from character-menu to get stronger." +
                "\nTASK_PROGRESS_1 then talk to Syvia\n",
                "Equip jewelries from RPG-Inventory to gain even more elemental power! To open RPG-Inventory click one of crafting slots from your inventory.",
                tasks3, itemPrizes1, 0, 0, 90, requiredQuests3, Material.RED_NETHER_BRICKS);
        QuestNPCManager.addQuest(quest3, 10, 11);

        //syvia to Aleesia
        List<Integer> requiredQuests4 = new ArrayList<>();
        requiredQuests4.add(3);
        List<String> story4 = new ArrayList<>();
        story4.add("We need to stop this before it harms Adelia!");
        List<Task> tasks4 = new ArrayList<>();
        TaskDealDamage taskDealDamage = new TaskDealDamage(ChatColor.DARK_PURPLE + "Aleesia", 200);
        tasks4.add(taskDealDamage);
        Quest quest4 = new Quest(4, "Hall of Darkness", story4, "Now you seem ready to face Aleesia.. or are you?", "Equip jewelries from RPG-Inventory to gain even more elemental power!" +
                "\nTo open RPG-Inventory click one of crafting slots from your inventory.\nNow you seem ready to face Aleesia.. or are you?\n" + ChatColor.LIGHT_PURPLE + "Enter the portal(Left-Mouse-Click when nearby)",
                "", tasks4, itemPrizes, 0, 0, 90, requiredQuests4, Material.RED_NETHER_BRICKS);

        InvincibleGiveAction invincibleGiveAction = new InvincibleGiveAction(40);
        PotionEffectAction potionEffectAction = new PotionEffectAction(PotionEffectType.BLINDNESS, 5 * 20, 3);
        Location teleportAfterTutorial = new Location(Bukkit.getWorld("world"), -3018.5, 95.5, 4920, -175.6f, -9.2f);
        TeleportAction teleportAction = new TeleportAction(teleportAfterTutorial, 10L);
        ClearPotionEffectAction clearPotionEffectAction = new ClearPotionEffectAction(PotionEffectType.WITHER);

        quest4.addOnCompleteAction(invincibleGiveAction);
        quest4.addOnCompleteAction(potionEffectAction);
        quest4.addOnCompleteAction(teleportAction);
        quest4.addOnCompleteAction(clearPotionEffectAction);
        quest4.addOnCompleteAction(new SendTitleAction(ChatColor.DARK_PURPLE + "Waking up..", ""));
        quest4.addOnCompleteAction(new FinishQuestAction(4));
        quest4.addOnCompleteAction(new StartQuestAction(5));
        quest4.addOnCompleteAction(new TutorialEndAction());
        QuestNPCManager.addQuest(quest4, 11, 0);

        //Aleesia to king of roumen
        List<Integer> requiredQuests5 = new ArrayList<>();
        requiredQuests5.add(4);
        List<String> story5 = new ArrayList<>();
        story5.add("You woke up from a dream of the past. In that dream,");
        story5.add("Guardians lost the battle against Aleesia. Darkness");
        story5.add("is slowly conquering the lands of Adelia...");
        List<Task> tasks5 = new ArrayList<>();
        List<ItemStack> itemPrizes5 = new ArrayList<>();
        Quest quest5 = new Quest(5, "A fresh start", story5,
                "Get out of the ship and follow the path to meet King of Roumen", "Talk with King of Roumen to learn what happened",
                "",
                tasks5, itemPrizes5, 0, 0, 1, requiredQuests5, Material.GRASS_BLOCK);
        QuestNPCManager.addQuest(quest5, 0, 31);*/
    }

}
