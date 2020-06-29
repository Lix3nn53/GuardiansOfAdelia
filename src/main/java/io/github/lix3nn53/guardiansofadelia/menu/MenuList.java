package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.chat.ChatTag;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.character.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.jobs.RPGCharacterCraftingStats;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.rewards.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.rewards.DailyRewardInfo;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.CharacterInfoSlot;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiLineGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiPage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.*;

public class MenuList {

    public static GuiGeneric mainMenu(GuardianData guardianData) {
        GuiGeneric guiGeneric = new GuiGeneric(54, ChatColor.GREEN + "Menu", 0);

        ItemStack compass = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = compass.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(23);
        itemMeta.setDisplayName(ChatColor.BLUE + "Compass");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Choose where you want to go and");
            add(ChatColor.GRAY + "let the compass lead you!");
        }});
        compass.setItemMeta(itemMeta);
        guiGeneric.setItem(12, compass);

        ItemStack map = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(26);
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "Maps");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Explore the Adelia!");
        }});
        map.setItemMeta(itemMeta);
        guiGeneric.setItem(14, map);

        ItemStack character = new ItemStack(Material.STONE_PICKAXE);
        String rpgClassStr = guardianData.getActiveCharacter().getRpgClassStr();
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        itemMeta.setCustomModelData(rpgClass.getClassIconCustomModelData());
        itemMeta.setDisplayName(ChatColor.GREEN + "Character");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Manage your character");
        }});
        character.setItemMeta(itemMeta);
        guiGeneric.setItem(10, character);

        ItemStack guild = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(25);
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Guild");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "United we stand divided we fall!");
        }});
        guild.setItemMeta(itemMeta);
        guiGeneric.setItem(16, guild);

        ItemStack minigames = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(29);
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Minigames");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Have fun in different game modes!");
        }});
        minigames.setItemMeta(itemMeta);
        guiGeneric.setItem(32, minigames);

        ItemStack bazaar = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(27);
        itemMeta.setDisplayName(ChatColor.GOLD + "Bazaar");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Manage your bazaar!");
        }});
        bazaar.setItemMeta(itemMeta);
        guiGeneric.setItem(30, bazaar);

        ItemStack activeBoosts = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(38);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Server Boosts");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "See if the server boost are active or not");
        }});
        activeBoosts.setItemMeta(itemMeta);
        guiGeneric.setItem(48, activeBoosts);

        ItemStack donation = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(28);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "WebStore ♥");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Donations are not required but helps me so");
            add(ChatColor.GRAY + "I can keep working on this project. Thanks <3");
        }});
        donation.setItemMeta(itemMeta);
        guiGeneric.setItem(50, donation);

        return guiGeneric;
    }

    public static GuiGeneric character() {
        GuiGeneric guiGeneric = new GuiGeneric(27, "Character", 0);

        ItemStack skills = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = skills.getItemMeta();
        itemMeta.setCustomModelData(31);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Class");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Manage your character's class!");

        }});
        skills.setItemMeta(itemMeta);
        guiGeneric.setItem(9, skills);

        itemMeta.setCustomModelData(31);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Skills");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Learn and master skills!");

        }});
        skills.setItemMeta(itemMeta);
        guiGeneric.setItem(11, skills);

        ItemStack elements = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(37);
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "Elements");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Choose where you want to go and");
            add(ChatColor.GRAY + "let the compass lead you!");
        }});
        elements.setItemMeta(itemMeta);
        guiGeneric.setItem(13, elements);

        ItemStack job = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(30);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Job");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Manage your character's job!");
        }});
        job.setItemMeta(itemMeta);
        guiGeneric.setItem(15, job);

        ItemStack chat = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(33);
        itemMeta.setDisplayName(ChatColor.AQUA + "Chat Tag");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Select your chat tag!");
        }});
        chat.setItemMeta(itemMeta);
        guiGeneric.setItem(17, chat);

        return guiGeneric;
    }

    public static GuiGeneric classManage(Player player) {
        GuiGeneric guiGeneric = null;

        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                guiGeneric = new GuiGeneric(9, ChatColor.YELLOW + "Class Manager", 0);

                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                String rpgClassStr = rpgCharacter.getRpgClassStr();
                RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                RPGClassStats rpgClassStats = rpgCharacter.getRPGClassStats(rpgClass);
                int totalExp = rpgClassStats.getTotalExp();
                int classLevel = RPGClassExperienceManager.getLevel(totalExp);

                ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                itemMeta.setCustomModelData(33);
                itemMeta.setDisplayName(ChatColor.YELLOW + "Class Info");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add("Class: " + rpgClass.getClassString());
                lore.add("Rank: " + classLevel);
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(1, itemStack);

                itemMeta.setCustomModelData(32);
                itemMeta.setDisplayName(ChatColor.GOLD + "Change Class");
                lore.clear();
                lore.add("");
                lore.add("Change to one of the classes you unlocked");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(3, itemStack);
            }
        }

        return guiGeneric;
    }

    public static GuiGeneric classChange(Player player) {
        GuiGeneric guiGeneric = null;

        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                guiGeneric = new GuiGeneric(27, ChatColor.YELLOW + "Class Change", 0);

                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                HashMap<String, RPGClassStats> unlockedClasses = rpgCharacter.getUnlockedClasses();

                Set<String> valuesSet = RPGClassManager.getClassNames();
                List<String> values = new ArrayList<>(valuesSet);

                int modCounter = 0;
                for (int i = 0; i < values.size(); i++) {
                    String valueStr = values.get(i);
                    RPGClass value = RPGClassManager.getClass(valueStr);
                    ItemStack itemStack = new ItemStack(Material.RED_WOOL);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName(value.getClassString());

                    if (unlockedClasses.containsKey(valueStr)) {
                        int totalExp = unlockedClasses.get(valueStr).getTotalExp();
                        itemStack.setType(Material.LIME_WOOL);
                        List<String> lore = new ArrayList<>();
                        lore.add("");
                        lore.add("Class: " + value.getClassString());
                        lore.add("Rank: " + RPGClassExperienceManager.getLevel(totalExp));
                        lore.add("");
                        lore.add(ChatColor.GREEN + "Click to change to this class!");
                        meta.setLore(lore);
                    }

                    itemStack.setItemMeta(meta);

                    guiGeneric.setItem(i * 2 - modCounter, itemStack);
                    int mod = (i * 2 + 1 - modCounter) % 9;
                    if (mod == 0) {
                        modCounter++;
                    }
                }
            }
        }

        return guiGeneric;
    }

    public static GuiGeneric skill(Player player) {
        GuiGeneric guiGeneric = null;

        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                SkillBar skillBar = rpgCharacter.getSkillBar();

                int pointsLeft = skillBar.getSkillPointsLeftToSpend();
                guiGeneric = new GuiGeneric(27, ChatColor.LIGHT_PURPLE + "Skills (Points: " + pointsLeft + ")", 0);

                HashMap<Integer, Skill> skillSet = rpgCharacter.getSkillBar().getSkillSet();

                if (skillSet.containsKey(0)) {
                    Skill skillOne = skillSet.get(0);
                    int investedSkillPoints = skillBar.getInvestedSkillPoints(0);
                    ItemStack icon = skillOne.getIcon(player.getLevel(), pointsLeft, investedSkillPoints);
                    ItemMeta itemMeta = icon.getItemMeta();
                    String displayName = itemMeta.getDisplayName();
                    itemMeta.setDisplayName(displayName + " (Invested: " + investedSkillPoints + ")");
                    icon.setItemMeta(itemMeta);
                    guiGeneric.setItem(1, icon);
                }

                if (skillSet.containsKey(1)) {
                    Skill skillTwo = skillSet.get(1);
                    int investedSkillPoints = skillBar.getInvestedSkillPoints(1);
                    ItemStack icon = skillTwo.getIcon(player.getLevel(), pointsLeft, investedSkillPoints);
                    ItemMeta itemMeta = icon.getItemMeta();
                    String displayName = itemMeta.getDisplayName();
                    itemMeta.setDisplayName(displayName + " (Invested: " + investedSkillPoints + ")");
                    icon.setItemMeta(itemMeta);
                    guiGeneric.setItem(4, icon);
                }

                if (skillSet.containsKey(2)) {
                    Skill skillThree = skillSet.get(2);
                    int investedSkillPoints = skillBar.getInvestedSkillPoints(2);
                    ItemStack icon = skillThree.getIcon(player.getLevel(), pointsLeft, investedSkillPoints);
                    ItemMeta itemMeta = icon.getItemMeta();
                    String displayName = itemMeta.getDisplayName();
                    itemMeta.setDisplayName(displayName + " (Invested: " + investedSkillPoints + ")");
                    icon.setItemMeta(itemMeta);
                    guiGeneric.setItem(7, icon);
                }

                if (skillSet.containsKey(3)) {
                    Skill skillFour = skillSet.get(3);
                    int investedSkillPoints = skillBar.getInvestedSkillPoints(3);
                    ItemStack icon = skillFour.getIcon(player.getLevel(), pointsLeft, investedSkillPoints);
                    ItemMeta itemMeta = icon.getItemMeta();
                    String displayName = itemMeta.getDisplayName();
                    itemMeta.setDisplayName(displayName + " (Invested: " + investedSkillPoints + ")");
                    icon.setItemMeta(itemMeta);
                    guiGeneric.setItem(20, icon);
                }

                if (skillSet.containsKey(4)) {
                    Skill skillFive = skillSet.get(4);
                    int investedSkillPoints = skillBar.getInvestedSkillPoints(4);
                    ItemStack icon = skillFive.getIcon(player.getLevel(), pointsLeft, investedSkillPoints);
                    ItemMeta itemMeta = icon.getItemMeta();
                    String displayName = itemMeta.getDisplayName();
                    itemMeta.setDisplayName(displayName + " (Invested: " + investedSkillPoints + ")");
                    icon.setItemMeta(itemMeta);
                    guiGeneric.setItem(24, icon);
                }
            }
        }

        return guiGeneric;
    }

    public static GuiGeneric element(Player player) {
        GuiGeneric guiGeneric = null;

        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();

                int pointsLeft = rpgCharacterStats.getAttributePointsLeftToSpend();
                guiGeneric = new GuiGeneric(27, ChatColor.AQUA + "Elements (Points: " + pointsLeft + ")", 0);

                Attribute fireStat = rpgCharacterStats.getFire();
                ItemStack fire = new ItemStack(Material.PAPER);
                ItemMeta ıtemMeta = fire.getItemMeta();
                ıtemMeta.setDisplayName(ChatColor.RED + "Fire (Invested: " + fireStat.getInvested() + ")");
                ıtemMeta.setLore(new ArrayList() {{
                    add("");
                    add(fireStat.getAttributeType().getDescription());
                }});
                fire.setItemMeta(ıtemMeta);
                guiGeneric.setItem(1, fire);

                Attribute waterStat = rpgCharacterStats.getWater();
                ItemStack water = new ItemStack(Material.PAPER);
                ıtemMeta.setDisplayName(ChatColor.BLUE + "Water (Invested: " + waterStat.getInvested() + ")");
                ıtemMeta.setLore(new ArrayList() {{
                    add("");
                    add(waterStat.getAttributeType().getDescription());
                }});
                water.setItemMeta(ıtemMeta);
                guiGeneric.setItem(4, water);

                Attribute earthStat = rpgCharacterStats.getEarth();
                ItemStack earth = new ItemStack(Material.PAPER);
                ıtemMeta.setDisplayName(ChatColor.DARK_GREEN + "Earth (Invested: " + earthStat.getInvested() + ")");
                ıtemMeta.setLore(new ArrayList() {{
                    add("");
                    add(earthStat.getAttributeType().getDescription());
                }});
                earth.setItemMeta(ıtemMeta);
                guiGeneric.setItem(7, earth);

                Attribute lightningStat = rpgCharacterStats.getLightning();
                ItemStack lightning = new ItemStack(Material.PAPER);
                ıtemMeta.setDisplayName(ChatColor.AQUA + "Lightning (Invested: " + lightningStat.getInvested() + ")");
                ıtemMeta.setLore(new ArrayList() {{
                    add("");
                    add(lightningStat.getAttributeType().getDescription());
                }});
                lightning.setItemMeta(ıtemMeta);
                guiGeneric.setItem(20, lightning);

                Attribute windStat = rpgCharacterStats.getWind();
                ItemStack wind = new ItemStack(Material.PAPER);
                ıtemMeta.setDisplayName(ChatColor.WHITE + "Wind (Invested: " + windStat.getInvested() + ")");
                ıtemMeta.setLore(new ArrayList() {{
                    add("");
                    add(windStat.getAttributeType().getDescription());
                }});
                wind.setItemMeta(ıtemMeta);
                guiGeneric.setItem(24, wind);
            }
        }


        return guiGeneric;
    }

    public static GuiGeneric job(Player player) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.YELLOW + "Job", 0);

        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                RPGCharacterCraftingStats craftingStats = activeCharacter.getCraftingStats();

                int slot = 9;
                for (CraftingType craftingType : CraftingType.values()) {
                    ItemStack craftingInfo = new ItemStack(Material.LIME_WOOL);
                    ItemMeta itemMeta = craftingInfo.getItemMeta();

                    itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Crafting Info");
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    int currentLevel = craftingStats.getCurrentLevel(craftingType);
                    lore.add("Level: " + currentLevel);
                    lore.add("Experience: " + craftingStats.getTotalExperience(craftingType));
                    lore.add("Required Experience: " + craftingStats.getTotalRequiredExperience(currentLevel + 1));

                    if (craftingType.equals(CraftingType.WEAPON_MELEE)) {
                        lore.add(ChatColor.RED + "Weaponsmith");
                        lore.add(ChatColor.GRAY + "Left click grindstone to craft melee weapons");
                        lore.add(ChatColor.GRAY + "Left click fletching table to craft ranged weapons");
                    } else if (craftingType.equals(CraftingType.ARMOR_LIGHT)) {
                        lore.add(ChatColor.AQUA + "Armorsmith");
                        lore.add(ChatColor.GRAY + "Left click anvil to craft heavy armors");
                        lore.add(ChatColor.GRAY + "Left click loom to craft light armors");
                    } else if (craftingType.equals(CraftingType.POTION)) {
                        lore.add(ChatColor.LIGHT_PURPLE + "Alchemist");
                        lore.add(ChatColor.GRAY + "Left click brewing stand to craft potions");
                        lore.add(ChatColor.GRAY + "Left click campfire to craft foods");
                    } else if (craftingType.equals(CraftingType.JEWEL)) {
                        lore.add(ChatColor.GOLD + "Jeweller");
                        lore.add(ChatColor.GRAY + "Left click smithing table to craft jewels");
                        lore.add(ChatColor.GRAY + "Left click enchanting table to craft enchant stones");
                    }
                    itemMeta.setLore(lore);
                    craftingInfo.setItemMeta(itemMeta);
                    guiGeneric.setItem(slot, craftingInfo);

                    slot++;
                }
            }
        }

        return guiGeneric;
    }

    public static GuiGeneric chatTagQuests(Player player) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.AQUA + "Chat Tag", 0);

        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                List<Integer> turnedInQuests = activeCharacter.getTurnedInQuests();

                int i = 0;
                for (ChatTag chatTag : ChatTag.values()) {
                    int requiredQuest = chatTag.getRequiredQuest();
                    ItemStack itemStack = new ItemStack(Material.RED_WOOL);
                    ChatColor questColor = ChatColor.RED;
                    if (turnedInQuests.contains(requiredQuest)) {
                        itemStack = new ItemStack(Material.LIME_WOOL);
                        questColor = ChatColor.GREEN;
                    }

                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(chatTag.getChatColor() + chatTag.toString());

                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(questColor + "Required quest no: " + requiredQuest);
                    lore.add("");
                    lore.add(ChatColor.GRAY + "Click to select this chat tag");
                    itemMeta.setLore(lore);

                    itemStack.setItemMeta(itemMeta);

                    guiGeneric.setItem(i, itemStack);
                    i++;
                }
            }
        }

        return guiGeneric;
    }

    public static GuiGeneric guide() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GREEN + "Guides", 0);

        return guiGeneric;
    }

    public static GuiBookGeneric compass() {
        GuiBookGeneric guiBookGeneric = new GuiBookGeneric(ChatColor.BLUE + "Compass", 0);

        GuiPage page1 = new GuiPage();
        GuiLineGeneric page1Line1 = new GuiLineGeneric();

        ItemStack city = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = city.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "Roumen #1");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to select your compass target!");
            add("");
            add(ChatColor.GRAY + "If you dont have a compass this will give you one.");
        }});
        city.setItemMeta(itemMeta);
        page1Line1.addWord(city);

        city = new ItemStack(Material.LIGHT_BLUE_WOOL);
        itemMeta.setDisplayName(ChatColor.AQUA + "Port Veloa #2");
        city.setItemMeta(itemMeta);
        page1Line1.addWord(city);

        city = new ItemStack(Material.LIGHT_BLUE_WOOL);
        itemMeta.setDisplayName(ChatColor.AQUA + "Elderine #3");
        city.setItemMeta(itemMeta);
        page1Line1.addWord(city);

        city = new ItemStack(Material.LIGHT_BLUE_WOOL);
        itemMeta.setDisplayName(ChatColor.AQUA + "Uruga #4");
        city.setItemMeta(itemMeta);
        page1Line1.addWord(city);

        city = new ItemStack(Material.LIGHT_BLUE_WOOL);
        itemMeta.setDisplayName(ChatColor.AQUA + "Alberstol Ruins #5");
        city.setItemMeta(itemMeta);
        page1Line1.addWord(city);

        GuiLineGeneric page1Line2 = new GuiLineGeneric();
        GuiLineGeneric page1Line3 = new GuiLineGeneric();
        ItemStack dungeon;

        int i = 1;
        for (DungeonTheme dungeonTheme : DungeonTheme.values()) {
            dungeon = new ItemStack(Material.MAGENTA_WOOL);
            itemMeta.setDisplayName(dungeonTheme.getName() + " #" + i);
            dungeon.setItemMeta(itemMeta);
            if (page1Line2.isEmpty()) {
                page1Line2.addWord(dungeon);
            } else {
                page1Line3.addWord(dungeon);
            }
            i++;
        }

        GuiLineGeneric page1Line4 = new GuiLineGeneric();
        ItemStack npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GOLD + "King of Roumen #31");
        npc.setItemMeta(itemMeta);
        page1Line4.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Sergeant Armin #32");
        npc.setItemMeta(itemMeta);
        page1Line4.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GREEN + "Village Elder Odo #33");
        npc.setItemMeta(itemMeta);
        page1Line4.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GREEN + "Adventurer Milo #34");
        npc.setItemMeta(itemMeta);
        page1Line4.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page1Line5 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.AQUA + "Dr. Rintarou #35");
        npc.setItemMeta(itemMeta);
        page1Line5.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page2Line1 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "Timberman Franky #36");
        npc.setItemMeta(itemMeta);
        page2Line1.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GREEN + "Forest Fairy #37");
        npc.setItemMeta(itemMeta);
        page2Line1.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page2Line2 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Pastry Chef Jasper #38");
        npc.setItemMeta(itemMeta);
        page2Line2.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiPage page2 = new GuiPage();
        GuiLineGeneric page2Line3 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.DARK_AQUA + "Captain Lenna #39");
        npc.setItemMeta(itemMeta);
        page2Line3.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.DARK_AQUA + "Sailor Skamkel #40");
        npc.setItemMeta(itemMeta);
        page2Line3.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page2Line4 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.AQUA + "Ashild #41");
        npc.setItemMeta(itemMeta);
        page2Line4.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page2Line5 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.DARK_AQUA + "Commander Erwin #42");
        npc.setItemMeta(itemMeta);
        page2Line5.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page3Line1 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Archaeologist Robin #43");
        npc.setItemMeta(itemMeta);
        page3Line1.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page3Line2 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.GOLD + "Guardian Raignald #44");
        npc.setItemMeta(itemMeta);
        page3Line2.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GOLD + "Guardian Iohne #45");
        npc.setItemMeta(itemMeta);
        page3Line2.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GOLD + "Guardian Afra #46");
        npc.setItemMeta(itemMeta);
        page3Line2.addWord(npc);


        npc = new ItemStack(Material.LIME_WOOL);
        GuiPage page3 = new GuiPage();
        GuiLineGeneric page3Line3 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Gatekeeper #47");
        npc.setItemMeta(itemMeta);
        page3Line3.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page3Line4 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "Vruhag #48");
        npc.setItemMeta(itemMeta);
        page3Line4.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        GuiLineGeneric page3Line5 = new GuiLineGeneric();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Archangel #49");
        npc.setItemMeta(itemMeta);
        page3Line5.addWord(npc);

        npc = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.BLUE + "Eohr #50");
        npc.setItemMeta(itemMeta);
        page3Line5.addWord(npc);

        page1.addLine(page1Line1);
        page1.addLine(page1Line2);
        page1.addLine(page1Line3);
        page1.addLine(page1Line4);
        page1.addLine(page1Line5);

        page2.addLine(page2Line1);
        page2.addLine(page2Line2);
        page2.addLine(page2Line3);
        page2.addLine(page2Line4);
        page2.addLine(page2Line5);

        page3.addLine(page3Line1);
        page3.addLine(page3Line2);
        page3.addLine(page3Line3);
        page3.addLine(page3Line4);
        page3.addLine(page3Line5);

        guiBookGeneric.addPage(page1);
        guiBookGeneric.addPage(page2);
        guiBookGeneric.addPage(page3);

        return guiBookGeneric;
    }

    public static GuiGeneric guild() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.DARK_PURPLE + "Guild", 0);

        ItemStack guildWar = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = guildWar.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Join Guild War");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to join a guild war");
        }});
        guildWar.setItemMeta(itemMeta);
        guiGeneric.setItem(17, guildWar);

        return guiGeneric;
    }

    public static GuiGeneric bazaar(Player player) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GOLD + "Bazaar", 0);

        ItemStack info = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta itemMeta = info.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Your Bazaar");

        boolean hasBazaar = false;
        UUID uniqueId = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uniqueId)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
            if (guardianData.hasBazaar()) {
                Bazaar bazaar = guardianData.getBazaar();
                if (bazaar.isOpen()) {
                    hasBazaar = true;
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.GREEN + "OPEN");
                    lore.add("");
                    lore.add(ChatColor.GOLD + "Money earned: " + EconomyUtils.priceToString(bazaar.getMoneyEarned()));
                    lore.add("");
                    lore.add(ChatColor.YELLOW + "Current Customers");
                    for (Player customer : bazaar.getCustomers()) {
                        lore.add(customer.getDisplayName());
                    }
                    itemMeta.setLore(lore);
                }
            }
        }
        if (!hasBazaar) {
            itemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GRAY + "CLOSED");
            }});
        }
        info.setItemMeta(itemMeta);
        guiGeneric.setItem(12, info);

        ItemStack open = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GREEN + "Open/Edit your bazaar");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Set up a bazaar in your current location");
            add("");
            add(ChatColor.GRAY + "Edit your bazaar if you already have one");
        }});
        open.setItemMeta(itemMeta);
        guiGeneric.setItem(14, open);

        return guiGeneric;
    }

    public static GuiGeneric minigames() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GOLD + "MiniGames", 0);

        ItemStack pvp = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = pvp.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Last One Standing");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to join this minigame");
        }});
        pvp.setItemMeta(itemMeta);
        guiGeneric.setItem(0, pvp);

        itemMeta.setDisplayName(ChatColor.RED + "Win By Most Kills");
        pvp.setItemMeta(itemMeta);
        guiGeneric.setItem(2, pvp);

        return guiGeneric;
    }

    public static GuiGeneric onShiftRightClickPlayer(Player rightClicked) {
        String rightClickedName = rightClicked.getName();
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.YELLOW + "Interact with " + rightClickedName, 0);

        ItemStack infoItem = new CharacterInfoSlot(rightClicked).getItem();
        guiGeneric.setItem(10, infoItem);

        ItemStack party = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = party.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "Party Invite");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to invite to party!");
        }});
        party.setItemMeta(itemMeta);
        guiGeneric.setItem(12, party);

        ItemStack guild = new ItemStack(Material.PURPLE_WOOL);
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Guild Invite");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to invite to guild!");
        }});
        guild.setItemMeta(itemMeta);
        guiGeneric.setItem(14, guild);

        ItemStack trade = new ItemStack(Material.YELLOW_WOOL);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Trade Invite");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Click to invite to trade!");
        }});
        trade.setItemMeta(itemMeta);
        guiGeneric.setItem(16, trade);

        return guiGeneric;
    }

    public static GuiGeneric serverBoostMenu() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.YELLOW + "Server Boosts", 0);

        ItemStack boostExperience = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = boostExperience.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Experience Boost");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "2x exp gained from slaying monsters");
        if (BoostPremiumManager.isBoostActive(BoostPremium.EXPERIENCE)) {
            boostExperience.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.EXPERIENCE));
        }
        itemMeta.setLore(lore);
        boostExperience.setItemMeta(itemMeta);
        guiGeneric.setItem(10, boostExperience);

        ItemStack boostLoot = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Loot Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "2x chance of monsters droping item when they die");
        if (BoostPremiumManager.isBoostActive(BoostPremium.LOOT)) {
            boostLoot.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.LOOT));
        }
        itemMeta.setLore(lore);
        boostLoot.setItemMeta(itemMeta);
        guiGeneric.setItem(12, boostLoot);

        ItemStack boostEnchant = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatColor.AQUA + "Enchant Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Increases success rate of item enchanting by 15%");
        if (BoostPremiumManager.isBoostActive(BoostPremium.ENCHANT)) {
            boostEnchant.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.ENCHANT));
        }
        itemMeta.setLore(lore);
        boostEnchant.setItemMeta(itemMeta);
        guiGeneric.setItem(14, boostEnchant);

        ItemStack boostGather = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatColor.GREEN + "Gather Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "2x gathering speed ");
        if (BoostPremiumManager.isBoostActive(BoostPremium.GATHER)) {
            boostGather.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.GATHER));
        }
        itemMeta.setLore(lore);
        boostGather.setItemMeta(itemMeta);
        guiGeneric.setItem(16, boostGather);

        return guiGeneric;
    }

    public static GuiGeneric dailyRewardsMenu(Player player) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.YELLOW + "Daily Reward Claim", 0);

        UUID uuid = player.getUniqueId();
        if (!GuardianDataManager.hasGuardianData(uuid)) return guiGeneric;

        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

        ItemStack pastFail = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = pastFail.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "You missed this reward");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "");
        itemMeta.setLore(lore);
        pastFail.setItemMeta(itemMeta);

        ItemStack pastSuccess = new ItemStack(Material.GREEN_WOOL);
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "You claimed this reward");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "");
        itemMeta.setLore(lore);
        pastSuccess.setItemMeta(itemMeta);

        ItemStack available = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GREEN + "You can claim today's reward");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "");
        itemMeta.setLore(lore);
        available.setItemMeta(itemMeta);

        ItemStack notAvailableToday = new ItemStack(Material.PURPLE_WOOL);
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "You can't claim today's reward");
        lore = new ArrayList<>();
        lore.add(ChatColor.DARK_PURPLE + "because you missed yesterday's reward.");
        itemMeta.setLore(lore);
        notAvailableToday.setItemMeta(itemMeta);

        ItemStack notAvailable = new ItemStack(Material.GRAY_WOOL);
        itemMeta.setDisplayName(ChatColor.GRAY + "Not available yet");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "");
        itemMeta.setLore(lore);
        notAvailable.setItemMeta(itemMeta);

        DailyRewardInfo dailyRewardInfo = guardianData.getDailyRewardInfo();
        LocalDate lastObtainDate = dailyRewardInfo.getLastObtainDate();

        int playerIndex = DailyRewardHandler.getIndexOfDate(lastObtainDate);
        player.sendMessage("playerIndex: " + playerIndex);
        int currentIndex = DailyRewardHandler.getCurrentIndex();
        player.sendMessage("currentIndex: " + currentIndex);

        //today
        if (currentIndex - 1 == playerIndex) {
            guiGeneric.setItem(currentIndex, available);
            guiGeneric.setItem(currentIndex + 18, available);
        } else {
            guiGeneric.setItem(currentIndex, notAvailableToday);
            guiGeneric.setItem(currentIndex + 18, notAvailableToday);
        }

        //past success
        for (int i = 1; i <= playerIndex; i++) {
            guiGeneric.setItem(i, pastSuccess);
            guiGeneric.setItem(i + 18, pastSuccess);
        }

        //past fail
        for (int i = playerIndex + 1; i < currentIndex; i++) {
            if (i == 0) continue;

            guiGeneric.setItem(i, pastFail);
            guiGeneric.setItem(i + 18, pastFail);
        }

        //future non-available
        for (int i = currentIndex + 1; i < 8; i++) {
            guiGeneric.setItem(i, notAvailable);
            guiGeneric.setItem(i + 18, notAvailable);
        }

        ItemStack filler = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Daily Rewards");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Every week, you bla bla TODO explanation");
        itemMeta.setLore(lore);
        filler.setItemMeta(itemMeta);

        int fillerIndex = 0;
        guiGeneric.setItem(fillerIndex, filler);
        guiGeneric.setItem(fillerIndex + 9, filler);
        guiGeneric.setItem(fillerIndex + 18, filler);

        fillerIndex = 8;
        guiGeneric.setItem(fillerIndex, filler);
        guiGeneric.setItem(fillerIndex + 9, filler);
        guiGeneric.setItem(fillerIndex + 18, filler);

        ItemStack[] rewards = DailyRewardHandler.getRewards();
        int i = 10;
        for (ItemStack itemStack : rewards) {
            if (itemStack == null) continue;

            guiGeneric.setItem(i, itemStack);
            i++;
        }

        guiGeneric.setLocked(true);
        guiGeneric.lockOwnInventory();
        return guiGeneric;
    }
}
