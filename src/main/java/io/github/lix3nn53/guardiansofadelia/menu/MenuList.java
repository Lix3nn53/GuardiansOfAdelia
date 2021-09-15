package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.chat.ChatTag;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.*;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.jobs.RPGCharacterCraftingStats;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardInfo;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.CharacterInfoSlot;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuList {

    public static GuiGeneric mainMenu() {
        GuiGeneric guiGeneric = new GuiGeneric(54, ChatPalette.GRAY_DARK + "Guardians of Adelia", 0);

        ItemStack compass = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta itemMeta = compass.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(27);
        itemMeta.setDisplayName(ChatPalette.BLUE + "Compass");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Choose where you want to go and");
        lore.add(ChatPalette.GRAY + "let the compass lead you!");
        itemMeta.setLore(lore);
        compass.setItemMeta(itemMeta);
        guiGeneric.setItem(12, compass);

        ItemStack map = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(24);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Maps");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Explore the Adelia!");
        itemMeta.setLore(lore);
        map.setItemMeta(itemMeta);
        guiGeneric.setItem(14, map);

        ItemStack character = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(2);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Character");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Manage your character");
        itemMeta.setLore(lore);
        character.setItemMeta(itemMeta);
        guiGeneric.setItem(10, character);

        ItemStack guild = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(9);
        itemMeta.setDisplayName(ChatPalette.PURPLE + "Guild");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "United we stand divided we fall!");
        itemMeta.setLore(lore);
        guild.setItemMeta(itemMeta);
        guiGeneric.setItem(16, guild);

        ItemStack bazaar = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(3);
        itemMeta.setDisplayName(ChatPalette.GOLD + "Bazaar");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Manage your bazaar!");
        itemMeta.setLore(lore);
        bazaar.setItemMeta(itemMeta);
        guiGeneric.setItem(29, bazaar);

        ItemStack minigames = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(25);
        itemMeta.setDisplayName(ChatPalette.PURPLE + "Minigames");
        lore = new ArrayList<>();
        lore.add("");
        itemMeta.setLore(lore);
        lore.add(ChatPalette.GRAY + "Have fun in different game modes!");
        minigames.setItemMeta(itemMeta);
        guiGeneric.setItem(31, minigames);

        ItemStack teleport = new ItemStack(Material.WOODEN_SHOVEL);
        itemMeta.setCustomModelData(6);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Instant Teleportation");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Teleport to locations you have unlocked");
        itemMeta.setLore(lore);
        teleport.setItemMeta(itemMeta);
        guiGeneric.setItem(33, teleport);

        ItemStack activeBoosts = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(28);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Server Boosts");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "See if the server boost are active or not");
        itemMeta.setLore(lore);
        activeBoosts.setItemMeta(itemMeta);
        guiGeneric.setItem(47, activeBoosts);

        ItemStack donation = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(10);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "WebStore ♥");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Donations are not required but helps me so");
        lore.add(ChatPalette.GRAY + "I can keep working on this project. Thanks <3");
        itemMeta.setLore(lore);
        donation.setItemMeta(itemMeta);
        guiGeneric.setItem(49, donation);

        ItemStack daily = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(10);
        itemMeta.setDisplayName(ChatPalette.GOLD + "Daily Rewards");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Gain rewards for being a regular player");
        itemMeta.setLore(lore);
        daily.setItemMeta(itemMeta);
        guiGeneric.setItem(51, daily);

        return guiGeneric;
    }

    public static GuiGeneric character(GuardianData guardianData) {
        GuiGeneric guiGeneric = new GuiGeneric(27, "Character", 0);

        ItemStack skills = new ItemStack(Material.WOODEN_PICKAXE);
        String rpgClassStr = guardianData.getActiveCharacter().getRpgClassStr();
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        int classIconCustomModelData = rpgClass.getClassIconCustomModelData();
        ItemMeta itemMeta = skills.getItemMeta();
        itemMeta.setCustomModelData(classIconCustomModelData);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Class");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Manage your character's class!");
        lore.add("");
        lore.add("Current Class: " + rpgClass.getClassString());
        itemMeta.setLore(lore);
        skills.setItemMeta(itemMeta);
        guiGeneric.setItem(9, skills);

        itemMeta.setCustomModelData(29);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Skills");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Learn and master skills!");
        itemMeta.setLore(lore);
        skills.setItemMeta(itemMeta);
        guiGeneric.setItem(11, skills);

        ItemStack statpoints = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(6);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Stat Points");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Spend stat points earned by leveling");
        lore.add(ChatPalette.GRAY + "your character.");
        itemMeta.setLore(lore);
        statpoints.setItemMeta(itemMeta);
        guiGeneric.setItem(13, statpoints);

        ItemStack job = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(21);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Crafting");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Manage your character's job!");
        itemMeta.setLore(lore);
        job.setItemMeta(itemMeta);
        guiGeneric.setItem(15, job);

        ItemStack chat = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(4);
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Chat Tag");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Select your chat tag!");
        itemMeta.setLore(lore);
        chat.setItemMeta(itemMeta);
        guiGeneric.setItem(17, chat);

        return guiGeneric;
    }

    public static GuiGeneric classManager(Player player) {
        GuiGeneric guiGeneric = null;

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Class Manager", 0);

                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                String rpgClassStr = rpgCharacter.getRpgClassStr();
                RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                int tier = rpgClass.getTier();
                int classIconCustomModelData = rpgClass.getClassIconCustomModelData();
                RPGClassStats rpgClassStats = rpgCharacter.getRPGClassStats();

                ItemStack itemStack = new ItemStack(Material.WOODEN_PICKAXE);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                itemMeta.setDisplayName(ChatPalette.YELLOW + "Your Class Info");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add("Class: " + rpgClass.getClassString());
                lore.add("Class Tier: " + tier);
                itemMeta.setLore(lore);
                itemMeta.setCustomModelData(classIconCustomModelData);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(4, itemStack);

                itemStack = new ItemStack(Material.IRON_BLOCK);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatPalette.GOLD + "Change Class Tier #1");
                lore.clear();
                lore.add("");
                lore.add("Change to a tier 1 class you have unlocked");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(20, itemStack);

                itemStack = new ItemStack(Material.GOLD_BLOCK);
                itemMeta.setDisplayName(ChatPalette.GOLD + "Change Class Tier #2");
                lore.clear();
                lore.add("");
                lore.add("Change to a tier 2 class you have unlocked");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(22, itemStack);

                itemStack = new ItemStack(Material.DIAMOND_BLOCK);
                itemMeta.setDisplayName(ChatPalette.GOLD + "Change Class Tier #3");
                lore.clear();
                lore.add("");
                lore.add("Change to a tier 3 class you have unlocked");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(24, itemStack);
            }
        }

        return guiGeneric;
    }

    public static GuiGeneric classChange(Player player, int classTier) {
        GuiGeneric guiGeneric = null;

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Class Change", 0);

                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                HashMap<String, RPGClassStats> unlockedClasses = rpgCharacter.getUnlockedClasses();

                List<RPGClass> values = RPGClassManager.getClassesAtTier(classTier);

                int modCounter = 0;
                for (int i = 0; i < values.size(); i++) {
                    RPGClass value = values.get(i);
                    ItemStack itemStack = new ItemStack(Material.RED_WOOL);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    String classString = value.getClassString();
                    itemMeta.setDisplayName(classString.toUpperCase());

                    List<String> description = value.getDescription();
                    List<WeaponGearType> weaponGearTypes = value.getWeaponGearTypes();
                    List<ArmorGearType> armorGearTypes = value.getArmorGearTypes();
                    List<ShieldGearType> shieldGearTypes = value.getShieldGearTypes();
                    HashMap<AttributeType, Integer> attributeTiers = value.getAttributeTiers();

                    List<String> lore = new ArrayList<>(description);
                    lore.add(ChatPalette.RED + "Tier: " + ChatPalette.GRAY + classTier);
                    lore.add("");

                    lore.add(ChatPalette.GREEN_DARK + "Attributes");
                    StringBuilder bonusAttributes = new StringBuilder(" ");
                    for (AttributeType attributeType : AttributeType.values()) {
                        int bonus = attributeTiers.get(attributeType);
                        bonusAttributes.append(bonus).append(" ");
                    }
                    lore.add(bonusAttributes.toString());

                    lore.add(ChatPalette.RED + "Weapons");
                    for (WeaponGearType type : weaponGearTypes) {
                        lore.add("  - " + type.getDisplayName());
                    }
                    lore.add(ChatPalette.BLUE_LIGHT + "Armors");
                    for (ArmorGearType type : armorGearTypes) {
                        lore.add("  - " + type.getDisplayName());
                    }
                    if (!shieldGearTypes.isEmpty()) {
                        lore.add(ChatPalette.BLUE + "Shields");
                        for (ShieldGearType type : shieldGearTypes) {
                            lore.add("  - " + type.getDisplayName());
                        }
                    }

                    lore.add("");

                    String valueStr = value.getClassStringNoColor();

                    if (unlockedClasses.containsKey(valueStr)) {
                        String rpgClassStr = rpgCharacter.getRpgClassStr();
                        if (valueStr.equalsIgnoreCase(rpgClassStr)) {
                            itemStack.setType(Material.PURPLE_WOOL);
                            lore.add(ChatPalette.PURPLE_LIGHT + "This is your current class");
                        } else {
                            itemStack.setType(Material.LIME_WOOL);
                            lore.add(ChatPalette.GREEN_DARK + "Click to change to this class!");
                        }
                    } else {
                        lore.add(ChatPalette.RED + "You haven't unlock this class");
                    }

                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);

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

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                SkillBar skillBar = rpgCharacter.getSkillBar();

                int pointsLeft = skillBar.getSkillPointsLeftToSpend();
                guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Skills (Points: " + pointsLeft + ")", 0);

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

    public static GuiGeneric statPoints(Player player) {
        GuiGeneric guiGeneric = null;

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();

                int pointsLeft = rpgCharacterStats.getAttributePointsLeftToSpend();
                guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Stat Points (Points: " + pointsLeft + ")", 0);

                Attribute bonusElementDamage = rpgCharacterStats.getAttribute(AttributeType.BONUS_ELEMENT_DAMAGE);
                ItemStack itemStack = new ItemStack(Material.PAPER);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(AttributeType.BONUS_ELEMENT_DAMAGE.getCustomName() + " (Invested: " + bonusElementDamage.getInvested() + ")");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatPalette.YELLOW + bonusElementDamage.getAttributeType().getDescription());
                lore.add("");
                lore.add(ChatPalette.GRAY + "Left Click: +1");
                lore.add(ChatPalette.GRAY + "Right Click: -1");
                lore.add(ChatPalette.GRAY + "Shift + Left Click: +5");
                lore.add(ChatPalette.GRAY + "Shift + Right Click: -5");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(1, itemStack);

                Attribute bonusElementDefense = rpgCharacterStats.getAttribute(AttributeType.BONUS_ELEMENT_DEFENSE);
                itemMeta.setDisplayName(AttributeType.BONUS_ELEMENT_DEFENSE.getCustomName() + " (Invested: " + bonusElementDefense.getInvested() + ")");
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatPalette.YELLOW + bonusElementDefense.getAttributeType().getDescription());
                lore.add("");
                lore.add(ChatPalette.GRAY + "Left Click: +1");
                lore.add(ChatPalette.GRAY + "Right Click: -1");
                lore.add(ChatPalette.GRAY + "Shift + Left Click: +5");
                lore.add(ChatPalette.GRAY + "Shift + Right Click: -5");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(4, itemStack);

                Attribute bonusMaxHealth = rpgCharacterStats.getAttribute(AttributeType.BONUS_MAX_HEALTH);
                itemMeta.setDisplayName(AttributeType.BONUS_MAX_HEALTH.getCustomName() + " (Invested: " + bonusMaxHealth.getInvested() + ")");
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatPalette.YELLOW + bonusMaxHealth.getAttributeType().getDescription());
                lore.add("");
                lore.add(ChatPalette.GRAY + "Left Click: +1");
                lore.add(ChatPalette.GRAY + "Right Click: -1");
                lore.add(ChatPalette.GRAY + "Shift + Left Click: +5");
                lore.add(ChatPalette.GRAY + "Shift + Right Click: -5");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(7, itemStack);

                Attribute bonusMaxMana = rpgCharacterStats.getAttribute(AttributeType.BONUS_MAX_MANA);
                itemMeta.setDisplayName(AttributeType.BONUS_MAX_MANA.getCustomName() + " (Invested: " + bonusMaxMana.getInvested() + ")");
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatPalette.YELLOW + bonusMaxMana.getAttributeType().getDescription());
                lore.add("");
                lore.add(ChatPalette.GRAY + "Left Click: +1");
                lore.add(ChatPalette.GRAY + "Right Click: -1");
                lore.add(ChatPalette.GRAY + "Shift + Left Click: +5");
                lore.add(ChatPalette.GRAY + "Shift + Right Click: -5");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(20, itemStack);

                Attribute bonusCriticalChance = rpgCharacterStats.getAttribute(AttributeType.BONUS_CRITICAL_CHANCE);
                itemMeta.setDisplayName(AttributeType.BONUS_CRITICAL_CHANCE.getCustomName() + " (Invested: " + bonusCriticalChance.getInvested() + ")");
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatPalette.YELLOW + bonusCriticalChance.getAttributeType().getDescription());
                lore.add("");
                lore.add(ChatPalette.GRAY + "Left Click: +1");
                lore.add(ChatPalette.GRAY + "Right Click: -1");
                lore.add(ChatPalette.GRAY + "Shift + Left Click: +5");
                lore.add(ChatPalette.GRAY + "Shift + Right Click: -5");
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                guiGeneric.setItem(24, itemStack);
            }
        }


        return guiGeneric;
    }

    public static GuiGeneric crafting(Player player) {
        GuiGeneric guiGeneric = new GuiGeneric(45, ChatPalette.YELLOW + "Crafting", 0);

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                RPGCharacterCraftingStats craftingStats = activeCharacter.getCraftingStats();

                int slot = 10;
                for (CraftingType craftingType : CraftingType.values()) {
                    ItemStack craftingInfo = new ItemStack(Material.NETHERITE_SWORD);
                    ItemMeta itemMeta = craftingInfo.getItemMeta();

                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    int currentLevel = craftingStats.getCurrentLevel(craftingType);
                    lore.add("Level: " + currentLevel);
                    lore.add("Experience: " + craftingStats.getTotalExperience(craftingType));
                    lore.add("Required Experience: " + craftingStats.getTotalRequiredExperience(currentLevel));

                    lore.add("");
                    lore.add(ChatPalette.YELLOW + "How to craft?");

                    if (craftingType.equals(CraftingType.WEAPON_MELEE)) {
                        //slot = 10;
                        itemMeta.setDisplayName(ChatPalette.RED + "Melee Weaponsmith");
                        lore.add(ChatPalette.GRAY + "Left click grindstone to craft melee weapons");
                    }
                    if (craftingType.equals(CraftingType.WEAPON_RANGED)) {
                        slot = 12;
                        craftingInfo.setType(Material.BOW);
                        itemMeta.setDisplayName(ChatPalette.RED + "Ranged Weaponsmith");
                        lore.add(ChatPalette.GRAY + "Left click fletching table to craft ranged weapons");
                    } else if (craftingType.equals(CraftingType.ARMOR_HEAVY)) {
                        slot = 14;
                        craftingInfo.setType(Material.NETHERITE_CHESTPLATE);
                        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Heavy Armorsmith");
                        lore.add(ChatPalette.GRAY + "Left click anvil to craft heavy armors");
                    } else if (craftingType.equals(CraftingType.ARMOR_LIGHT)) {
                        slot = 16;
                        craftingInfo.setType(Material.LEATHER_CHESTPLATE);
                        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Light Armorsmith");
                        lore.add(ChatPalette.GRAY + "Left click loom to craft light armors");
                    } else if (craftingType.equals(CraftingType.POTION)) {
                        slot = 28;
                        craftingInfo.setType(Material.POTION);
                        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Potion Alchemist");
                        lore.add(ChatPalette.GRAY + "Left click brewing stand to craft potions");
                    } else if (craftingType.equals(CraftingType.FOOD)) {
                        slot = 30;
                        craftingInfo.setType(Material.COOKED_BEEF);
                        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Food Alchemist");
                        lore.add(ChatPalette.GRAY + "Left click campfire to craft foods");
                    } else if (craftingType.equals(CraftingType.JEWEL)) {
                        slot = 32;
                        craftingInfo.setType(Material.EMERALD);
                        itemMeta.setDisplayName(ChatPalette.GOLD + "Jeweller");
                        lore.add(ChatPalette.GRAY + "Left click smithing table to craft jewels");
                    } else if (craftingType.equals(CraftingType.ENCHANT_STONE)) {
                        slot = 34;
                        craftingInfo.setType(Material.ENCHANTED_BOOK);
                        itemMeta.setDisplayName(ChatPalette.GOLD + "Enchant-Stone Crafting");
                        lore.add(ChatPalette.GRAY + "Left click enchanting table to enchant stones");
                    }
                    itemMeta.setLore(lore);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                    craftingInfo.setItemMeta(itemMeta);
                    guiGeneric.setItem(slot, craftingInfo);

                    slot += 3;
                }
            }
        }

        return guiGeneric;
    }

    public static GuiGeneric chatTagQuests(Player player) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Chat Tag", 0);

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                List<Integer> turnedInQuests = activeCharacter.getTurnedInQuests();

                int i = 0;
                for (ChatTag chatTag : ChatTag.values()) {
                    int requiredQuest = chatTag.getRequiredQuest();
                    ItemStack itemStack = new ItemStack(Material.RED_WOOL);
                    ChatPalette questColor = ChatPalette.RED;
                    if (turnedInQuests.contains(requiredQuest)) {
                        itemStack = new ItemStack(Material.LIME_WOOL);
                        questColor = ChatPalette.GREEN_DARK;
                    }

                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(chatTag.getChatPalette() + chatTag.toString());

                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(questColor + "Required quest no: " + requiredQuest);
                    lore.add("");
                    lore.add(ChatPalette.GRAY + "Click to select this chat tag");
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

        return new GuiGeneric(27, ChatPalette.GRAY_DARK + "Guides", 0);
    }

    public static GuiGeneric compass() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Compass", 0);

        ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Towns");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a town as your compass target.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        guiGeneric.setItem(11, itemStack);

        itemStack = new ItemStack(Material.PURPLE_WOOL);
        itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Dungeon Gates");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a dungeon gate as your compass target.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        guiGeneric.setItem(13, itemStack);

        itemStack = new ItemStack(Material.LIME_WOOL);
        itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "NPCS");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select a NPC as your compass target.");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        guiGeneric.setItem(15, itemStack);

        return guiGeneric;
    }

    public static GuiBookGeneric compassTowns() {
        GuiBookGeneric guiBookGeneric = new GuiBookGeneric(ChatPalette.GRAY_DARK + "Compass Towns", 0);

        HashMap<Integer, Town> towns = TownManager.getTowns();
        for (int key : towns.keySet()) {
            Town town = towns.get(key);
            ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_WOOL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to select your compass target!");
            lore.add("");
            lore.add(ChatPalette.GRAY + "If you dont have a compass this will give you one.");
            itemMeta.setLore(lore);

            itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + town.getName() + " #" + key);
            itemStack.setItemMeta(itemMeta);

            guiBookGeneric.addToFirstAvailableWord(itemStack);
        }

        return guiBookGeneric;
    }

    public static GuiBookGeneric compassDungeonGates() {
        GuiBookGeneric guiBookGeneric = new GuiBookGeneric(ChatPalette.GRAY_DARK + "Compass Dungeon Gates", 0);

        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
        for (String dungeonCode : dungeonThemes.keySet()) {
            ItemStack itemStack = new ItemStack(Material.PURPLE_WOOL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to select your compass target!");
            lore.add("");
            lore.add(ChatPalette.GRAY + "If you dont have a compass this will give you one.");
            itemMeta.setLore(lore);

            DungeonTheme dungeonTheme = dungeonThemes.get(dungeonCode);
            itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + dungeonTheme.getName() + " #" + dungeonCode);
            itemStack.setItemMeta(itemMeta);

            guiBookGeneric.addToFirstAvailableWord(itemStack);
        }

        return guiBookGeneric;
    }

    public static GuiBookGeneric compassNPCs() {
        GuiBookGeneric guiBookGeneric = new GuiBookGeneric(ChatPalette.GRAY_DARK + "Compass NPCs", 0);

        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        for (int i = 120; i < 1000; i++) {
            NPC byId = npcRegistry.getById(i);

            if (byId == null) break;

            ItemStack itemStack = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to select your compass target!");
            lore.add("");
            lore.add(ChatPalette.GRAY + "If you dont have a compass this will give you one.");
            itemMeta.setLore(lore);

            itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + byId.getName() + " #" + i);
            itemStack.setItemMeta(itemMeta);

            guiBookGeneric.addToFirstAvailableWord(itemStack);
        }

        return guiBookGeneric;
    }

    public static GuiGeneric guild() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Guild", 0);

        ItemStack guildWar = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = guildWar.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.RED + "Join Guild War");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to join a guild war");
        itemMeta.setLore(lore);
        guildWar.setItemMeta(itemMeta);
        guiGeneric.setItem(17, guildWar);

        return guiGeneric;
    }

    public static GuiGeneric bazaar(Player player) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GOLD + "Bazaar", 0);

        ItemStack info = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta itemMeta = info.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GOLD + "Your Bazaar");

        boolean hasBazaar = false;
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasBazaar()) {
                Bazaar bazaar = guardianData.getBazaar();
                if (bazaar.isOpen()) {
                    hasBazaar = true;
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatPalette.GREEN_DARK + "OPEN");
                    lore.add("");
                    lore.add(ChatPalette.GOLD + "Money earned: " + EconomyUtils.priceToString(bazaar.getMoneyEarned()));
                    lore.add("");
                    lore.add(ChatPalette.YELLOW + "Current Customers");
                    for (Player customer : bazaar.getCustomers()) {
                        lore.add(customer.getDisplayName());
                    }
                    itemMeta.setLore(lore);
                }
            }
        }
        if (!hasBazaar) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "CLOSED");
            itemMeta.setLore(lore);
        }
        info.setItemMeta(itemMeta);
        guiGeneric.setItem(12, info);

        ItemStack open = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Open/Edit your bazaar");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Set up a bazaar in your current location");
        lore.add("");
        lore.add(ChatPalette.GRAY + "Edit your bazaar if you already have one");
        itemMeta.setLore(lore);
        open.setItemMeta(itemMeta);
        guiGeneric.setItem(14, open);

        return guiGeneric;
    }

    public static GuiGeneric minigames() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "MiniGames", 0);

        ItemStack pvp = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = pvp.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.RED + "Last One Standing");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to join this minigame");
        itemMeta.setLore(lore);
        pvp.setItemMeta(itemMeta);
        guiGeneric.setItem(0, pvp);

        itemMeta.setDisplayName(ChatPalette.RED + "Win By Most Kills");
        pvp.setItemMeta(itemMeta);
        guiGeneric.setItem(2, pvp);

        return guiGeneric;
    }

    public static GuiGeneric onShiftRightClickPlayer(Player rightClicked) {
        String rightClickedName = rightClicked.getName();
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Interact with " + rightClickedName, 0);

        ItemStack infoItem = new CharacterInfoSlot(rightClicked).getItem();
        guiGeneric.setItem(10, infoItem);

        ItemStack party = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = party.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Party Invite");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to invite to party!");
        itemMeta.setLore(lore);
        party.setItemMeta(itemMeta);
        guiGeneric.setItem(12, party);

        ItemStack guild = new ItemStack(Material.PURPLE_WOOL);
        itemMeta.setDisplayName(ChatPalette.PURPLE + "Guild Invite");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to invite to guild!");
        itemMeta.setLore(lore);
        guild.setItemMeta(itemMeta);
        guiGeneric.setItem(14, guild);

        ItemStack trade = new ItemStack(Material.YELLOW_WOOL);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Trade Invite");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to invite to trade!");
        itemMeta.setLore(lore);
        trade.setItemMeta(itemMeta);
        guiGeneric.setItem(16, trade);

        return guiGeneric;
    }

    public static GuiGeneric serverBoostMenu() {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Server Boosts", 0);

        ItemStack boostExperience = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = boostExperience.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Experience Boost");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "2x exp gained from slaying monsters");
        if (BoostPremiumManager.isBoostActive(BoostPremium.EXPERIENCE)) {
            boostExperience.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.EXPERIENCE));
        }
        itemMeta.setLore(lore);
        boostExperience.setItemMeta(itemMeta);
        guiGeneric.setItem(10, boostExperience);

        ItemStack boostLoot = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Loot Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "2x chance of monsters droping item when they die");
        if (BoostPremiumManager.isBoostActive(BoostPremium.LOOT)) {
            boostLoot.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.LOOT));
        }
        itemMeta.setLore(lore);
        boostLoot.setItemMeta(itemMeta);
        guiGeneric.setItem(12, boostLoot);

        ItemStack boostEnchant = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Enchant Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Increases success rate of item enchanting by 15%");
        if (BoostPremiumManager.isBoostActive(BoostPremium.ENCHANT)) {
            boostEnchant.setType(Material.LIME_WOOL);
            lore.add("");
            lore.add("Minutes left: " + BoostPremiumManager.getMinutesLeft(BoostPremium.ENCHANT));
        }
        itemMeta.setLore(lore);
        boostEnchant.setItemMeta(itemMeta);
        guiGeneric.setItem(14, boostEnchant);

        ItemStack boostGather = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Gather Boost");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "2x gathering speed ");
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
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GRAY_DARK + "Daily Reward Claim", 0);

        if (!GuardianDataManager.hasGuardianData(player)) return guiGeneric;

        GuardianData guardianData = GuardianDataManager.getGuardianData(player);

        ItemStack pastFail = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = pastFail.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.RED + "You missed this reward");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "");
        itemMeta.setLore(lore);
        pastFail.setItemMeta(itemMeta);

        ItemStack pastSuccess = new ItemStack(Material.GREEN_WOOL);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "You claimed this reward");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "");
        itemMeta.setLore(lore);
        pastSuccess.setItemMeta(itemMeta);

        ItemStack available = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "You can claim today's reward");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "");
        itemMeta.setLore(lore);
        available.setItemMeta(itemMeta);

        ItemStack notAvailableToday = new ItemStack(Material.PURPLE_WOOL);
        itemMeta.setDisplayName(ChatPalette.PURPLE + "You can't claim today's reward");
        lore = new ArrayList<>();
        lore.add(ChatPalette.PURPLE + "because you missed yesterday's reward.");
        itemMeta.setLore(lore);
        notAvailableToday.setItemMeta(itemMeta);

        ItemStack notAvailable = new ItemStack(Material.GRAY_WOOL);
        itemMeta.setDisplayName(ChatPalette.GRAY + "Not available yet");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "");
        itemMeta.setLore(lore);
        notAvailable.setItemMeta(itemMeta);

        DailyRewardInfo dailyRewardInfo = guardianData.getDailyRewardInfo();
        LocalDate lastObtainDate = dailyRewardInfo.getLastObtainDate();

        int playerIndex = DailyRewardHandler.getIndexOfDate(lastObtainDate);
        int currentIndex = DailyRewardHandler.getCurrentIndex();

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
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Daily Rewards");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Every week, you bla bla TODO explanation");
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
