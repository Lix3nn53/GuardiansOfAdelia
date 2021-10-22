package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.*;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class TutorialManager {

    public static void startTutorial(Player player, String rpgClassStr, int charNo, Location startLocation) {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            RPGCharacter rpgCharacter = new RPGCharacter(rpgClassStr, player);
            guardianData.setActiveCharacter(rpgCharacter, charNo);
            rpgCharacter.getSkillBar().remakeSkillBar();

            // Character level
            int totalExpForLevel = RPGCharacterExperienceManager.getTotalRequiredExperience(90);
            RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();
            rpgCharacterStats.setTotalExp(totalExpForLevel);

            int totalExpClass = RPGClassExperienceManager.getTotalRequiredExperience(20);
            RPGClassStats currentRPGClassStats = rpgCharacter.getCurrentRPGClassStats();
            currentRPGClassStats.giveExp(player, totalExpClass);

            giveTutorialItems(player, rpgClassStr);
            player.teleport(startLocation);

            player.sendTitle(ChatPalette.PURPLE + "Aleesia's Castle", ChatPalette.GRAY + "Fall of the Adelia", 25, 35, 25);

            MessageUtils.sendCenteredMessage(player, ChatPalette.GRAY + "-- " + ChatPalette.PURPLE + "Aleesia's Castle" + ChatPalette.GRAY + " --");
            MessageUtils.sendCenteredMessage(player, ChatPalette.PURPLE + "---- " + ChatPalette.GRAY + "Fall of the Adelia" + ChatPalette.PURPLE + " ----");

            Quest tutorialStartQuest = QuestNPCManager.getQuestCopyById(1);
            rpgCharacter.acceptQuest(tutorialStartQuest, player);

            rpgCharacter.getRpgCharacterStats().recalculateEquipment(rpgCharacter.getRpgClassStr());
            rpgCharacter.getRpgCharacterStats().recalculateRPGInventory(rpgCharacter.getRpgInventory());

            rpgCharacterStats.setCurrentHealth(rpgCharacterStats.getTotalMaxHealth());
            rpgCharacterStats.setCurrentMana(rpgCharacterStats.getTotalMaxMana());
        }
    }

    private static void giveTutorialItems(Player player, String rpgClassStr) {
        InventoryUtils.setMenuItemPlayer(player);
        ItemTier tier = ItemTier.LEGENDARY;
        String gearSet = "Tutorial";

        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        ArmorGearType armorGearType = rpgClass.getDefaultArmorGearType();

        ItemStack helmet = ArmorManager.get(ArmorSlot.HELMET, armorGearType, GearLevel.EIGHT, tier, false, gearSet).get(0);
        ItemStack chest = ArmorManager.get(ArmorSlot.CHESTPLATE, armorGearType, GearLevel.EIGHT, tier, false, gearSet).get(0);
        ItemStack leggings = ArmorManager.get(ArmorSlot.LEGGINGS, armorGearType, GearLevel.EIGHT, tier, false, gearSet).get(0);
        ItemStack boots = ArmorManager.get(ArmorSlot.BOOTS, armorGearType, GearLevel.EIGHT, tier, false, gearSet).get(0);

        PlayerInventory playerInventory = player.getInventory();

        playerInventory.setHelmet(helmet);
        playerInventory.setChestplate(chest);
        playerInventory.setLeggings(leggings);
        playerInventory.setBoots(boots);

        /*WeaponGearType mainhandGearType = rpgClass.getDefaultWeaponGearType();

        ItemStack mainWeapon = WeaponManager.get(mainhandGearType, 1, 0, tier, false);
        playerInventory.setItem(4, mainWeapon);

        if (rpgClass.hasDefaultOffhand()) {
            boolean defaultOffhandWeapon = rpgClass.isDefaultOffhandWeapon();
            if (defaultOffhandWeapon) {
                playerInventory.setItemInOffHand(mainWeapon);
            } else {
                List<ShieldGearType> shieldGearTypes = rpgClass.getShieldGearTypes();
                if (!shieldGearTypes.isEmpty()) {
                    ShieldGearType shieldGearType = shieldGearTypes.get(0);

                    ItemStack shield = ShieldManager.get(shieldGearType, 1, 0, tier, false);
                    playerInventory.setItemInOffHand(shield);
                }
            }
        } else if (mainhandGearType.getMaterial().equals(Material.BOW) || mainhandGearType.getMaterial().equals(Material.CROSSBOW)) {
            ItemStack arrow = OtherItems.getArrow(2);
            playerInventory.setItemInOffHand(arrow);
        }

        ItemStack hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(10, 10);
        InventoryUtils.giveItemToPlayer(player, hpPotion);
        ItemStack manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(10, 10);
        InventoryUtils.giveItemToPlayer(player, manaPotion);

         */
    }
}
