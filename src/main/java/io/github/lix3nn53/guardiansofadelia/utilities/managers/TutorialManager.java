package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.PotionType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.*;
import io.github.lix3nn53.guardiansofadelia.Items.list.consumables.Potions;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


public class TutorialManager {

    public static void startTutorial(Player player, RPGClass rpgClass, int charNo, Location startLocation) {
        if (GuardianDataManager.hasGuardianData(player.getUniqueId())) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player.getUniqueId());
            RPGCharacter rpgCharacter = new RPGCharacter();
            guardianData.setActiveCharacter(rpgCharacter, charNo);

            SkillAPIUtils.createTurorialCharacter(player, charNo, rpgClass);
            SkillAPIUtils.giveLevels(player, 89);
            giveTutorialItems(player, rpgClass);
            player.teleport(startLocation);

            player.sendTitle(ChatColor.DARK_PURPLE + "Malephar's Castle", ChatColor.GRAY + "Fall of the Adelia", 25, 35, 25);

            player.sendMessage(ChatColor.GRAY + "▁▂▃▄▅▆▇ " + ChatColor.DARK_PURPLE + "Malephar's Castle" + ChatColor.GRAY + " ▇▆▅▄▃▂▁");
            player.sendMessage(ChatColor.DARK_PURPLE + "---------- " + ChatColor.GRAY + "Fall of the Adelia" + ChatColor.DARK_PURPLE + " ----------");
            player.sendMessage("");

            SkillAPIUtils.recoverMana(player);
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 1D);

            Quest tutorialStartQuest = QuestNPCManager.getQuestCopyById(1);
            rpgCharacter.addQuest(tutorialStartQuest, player);
        }
    }

    private static void giveTutorialItems(Player player, RPGClass rpgClass) {
        InventoryUtils.setMenuItemPlayer(player);
        ItemTier tier = ItemTier.COMMON;
        int minStatValue = 10;
        int maxStatValue = 50;
        int minNumberOfStats = 2;
        String itemTag = "Tutorial";
        ItemStack helmet = Helmets.get(rpgClass, 7, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        ItemStack chest = Chestplates.get(rpgClass, 7, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        ItemStack leggings = Leggings.get(rpgClass, 7, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
        ItemStack boots = Boots.get(rpgClass, 7, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);

        changeClassOfItemToTutorialClass(helmet, rpgClass);
        changeClassOfItemToTutorialClass(chest, rpgClass);
        changeClassOfItemToTutorialClass(leggings, rpgClass);
        changeClassOfItemToTutorialClass(boots, rpgClass);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        ItemStack mainHand = Weapons.getWeapon(rpgClass, 10, tier, itemTag, minStatValue, maxStatValue, minNumberOfStats);
        changeClassOfItemToTutorialClass(mainHand, rpgClass);
        player.getInventory().setItem(5, mainHand);

        if (rpgClass.equals(RPGClass.KNIGHT)) {
            ItemStack shield = Shields.get(rpgClass, 10, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
            changeClassOfItemToTutorialClass(shield, rpgClass);
            player.getInventory().setItemInOffHand(shield);
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            ItemStack shield = Shields.get(rpgClass, 10, tier, itemTag, tier.getBonusPercent(), minStatValue, maxStatValue, minNumberOfStats);
            changeClassOfItemToTutorialClass(shield, rpgClass);
            player.getInventory().setItemInOffHand(shield);
        } else if (rpgClass.equals(RPGClass.NINJA)) {
            player.getInventory().setItemInOffHand(mainHand);
        }

        ItemStack hpPotion = Potions.getItemStack(PotionType.HEALTH, 5);
        hpPotion.setAmount(20);
        InventoryUtils.giveItemToPlayer(player, hpPotion);
        ItemStack manaPotion = Potions.getItemStack(PotionType.MANA, 5);
        manaPotion.setAmount(20);
        InventoryUtils.giveItemToPlayer(player, manaPotion);
    }

    private static void changeClassOfItemToTutorialClass(ItemStack itemStack, RPGClass rpgClass) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        for (int i = 0; i < lore.size(); i++) {
            String classLore = lore.get(i);
            if (classLore.contains("Required Class")) {
                classLore = ChatColor.DARK_PURPLE + "Required Class: " + rpgClass.getClassColor() + "Tutorial" + rpgClass.getClassStringNoColor();
                lore.set(i, classLore);
                break;
            }
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }
}
