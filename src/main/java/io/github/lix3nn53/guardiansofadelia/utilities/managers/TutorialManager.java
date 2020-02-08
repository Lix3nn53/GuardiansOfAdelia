package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Armors;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Shields;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterExperienceManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class TutorialManager {

    public static void startTutorial(Player player, RPGClass rpgClass, int charNo, Location startLocation) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            RPGCharacter rpgCharacter = new RPGCharacter(rpgClass, player);
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

        ItemStack helmet = Armors.getArmor(ArmorType.HELMET, rpgClass, 7, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        ItemStack chest = Armors.getArmor(ArmorType.CHESTPLATE, rpgClass, 7, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        ItemStack leggings = Armors.getArmor(ArmorType.LEGGINGS, rpgClass, 7, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        ItemStack boots = Armors.getArmor(ArmorType.BOOTS, rpgClass, 7, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);

        PlayerInventory playerInventory = player.getInventory();

        playerInventory.setHelmet(helmet);
        playerInventory.setChestplate(chest);
        playerInventory.setLeggings(leggings);
        playerInventory.setBoots(boots);

        ItemStack mainHand = Weapons.getWeapon(rpgClass, 10, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        playerInventory.setItem(4, mainHand);

        if (rpgClass.equals(RPGClass.KNIGHT)) {
            ItemStack shield = Shields.get(rpgClass, 10, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
            playerInventory.setItemInOffHand(shield);
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            ItemStack shield = Shields.get(rpgClass, 10, tier, itemTag, tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
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
