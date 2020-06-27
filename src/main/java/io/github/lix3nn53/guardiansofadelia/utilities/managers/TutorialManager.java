package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.*;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;


public class TutorialManager {

    public static void startTutorial(Player player, RPGClass rpgClass, int charNo, Location startLocation) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            HashMap<RPGClass, RPGClassStats> unlockedClasses = new HashMap<>();
            RPGCharacter rpgCharacter = new RPGCharacter(rpgClass, unlockedClasses, player);
            guardianData.setActiveCharacter(rpgCharacter, charNo);

            int totalExpForLevel = RPGCharacterExperienceManager.getTotalRequiredExperience(90);
            RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();
            rpgCharacterStats.setTotalExp(totalExpForLevel);

            giveTutorialItems(player, rpgClass);
            player.teleport(startLocation);

            player.sendTitle(ChatColor.DARK_PURPLE + "Aleesia's Castle", ChatColor.GRAY + "Fall of the Adelia", 25, 35, 25);

            MessageUtils.sendCenteredMessage(player, ChatColor.GRAY + "-- " + ChatColor.DARK_PURPLE + "Aleesia's Castle" + ChatColor.GRAY + " --");
            MessageUtils.sendCenteredMessage(player, ChatColor.DARK_PURPLE + "---- " + ChatColor.GRAY + "Fall of the Adelia" + ChatColor.DARK_PURPLE + " ----");

            player.sendMessage("");

            Quest tutorialStartQuest = QuestNPCManager.getQuestCopyById(1);
            rpgCharacter.acceptQuest(tutorialStartQuest, player);

            rpgCharacter.getRpgCharacterStats().recalculateEquipment(rpgCharacter.getRpgClass());
            rpgCharacter.getRpgCharacterStats().recalculateRPGInventory(rpgCharacter.getRpgInventory());

            rpgCharacterStats.setCurrentHealth(rpgCharacterStats.getTotalMaxHealth());
            rpgCharacterStats.setCurrentMana(rpgCharacterStats.getTotalMaxMana());
        }
    }

    private static void giveTutorialItems(Player player, RPGClass rpgClass) {
        InventoryUtils.setMenuItemPlayer(player);
        ItemTier tier = ItemTier.COMMON;
        int minStatValue = 10;
        int maxStatValue = 50;
        int minNumberOfStats = 2;
        String itemTag = "Tutorial";

        ArmorGearType armorGearType = rpgClass.getDefaultArmorGearType();

        ItemStack helmet = ArmorManager.get(ArmorType.HELMET, armorGearType, 1, 0, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        ItemStack chest = ArmorManager.get(ArmorType.CHESTPLATE, armorGearType, 1, 0, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        ItemStack leggings = ArmorManager.get(ArmorType.LEGGINGS, armorGearType, 1, 0, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        ItemStack boots = ArmorManager.get(ArmorType.BOOTS, armorGearType, 1, 0, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);

        PlayerInventory playerInventory = player.getInventory();

        playerInventory.setHelmet(helmet);
        playerInventory.setChestplate(chest);
        playerInventory.setLeggings(leggings);
        playerInventory.setBoots(boots);

        WeaponGearType mainhandGearType = rpgClass.getDefaultWeaponGearType();

        ItemStack mainHand = WeaponManager.get(mainhandGearType, 1, 0, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        playerInventory.setItem(4, mainHand);

        if (rpgClass.equals(RPGClass.KNIGHT) || rpgClass.equals(RPGClass.PALADIN)) {
            ShieldGearType shieldGearType = ShieldGearType.SHIELD;

            ItemStack shield = ShieldManager.get(shieldGearType, 1, 0, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);

            playerInventory.setItemInOffHand(shield);
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            playerInventory.setItemInOffHand(mainHand);
        } else if (rpgClass.equals(RPGClass.ARCHER) || rpgClass.equals(RPGClass.HUNTER)) {
            ItemStack arrow = OtherItems.getArrow(2);
            playerInventory.setItemInOffHand(arrow);
        }

        ItemStack hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(10, 10);
        InventoryUtils.giveItemToPlayer(player, hpPotion);
        ItemStack manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(10, 10);
        InventoryUtils.giveItemToPlayer(player, manaPotion);
    }
}
