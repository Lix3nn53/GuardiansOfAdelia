package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.*;
import io.github.lix3nn53.guardiansofadelia.guardian.element.Element;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CharacterInfoSlot {

    private final Player player;

    public CharacterInfoSlot(Player player) {
        this.player = player;
    }

    public ItemStack getItem() {
        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                skullMeta.setDisplayName(ChatPalette.YELLOW + "Character Info");

                String rpgClassStr = activeCharacter.getRpgClassStr();
                RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
                String className = rpgClass.getClassString();
                int mana = rpgCharacterStats.getCurrentMana();
                int maxMana = rpgCharacterStats.getTotalMaxMana();
                final int level = player.getLevel();
                int totalExp = rpgCharacterStats.getTotalExp();
                int exp = RPGCharacterExperienceManager.getCurrentExperience(totalExp, level);
                int expReq = RPGCharacterExperienceManager.getRequiredExperience(level);
                int maxHealth = (int) (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 0.5);
                int health = (int) (player.getHealth() + 0.5);
                double criticalChance = rpgCharacterStats.getTotalCriticalChance() * 100;
                double criticalDamage = rpgCharacterStats.getTotalCriticalDamage() * 100;

                final int totalDefense = rpgCharacterStats.getTotalElementDefense();
                double defenseReduction = StatUtils.getDefenseReduction(totalDefense);

                ElementType mainElement = rpgClass.getMainElement();

                double abilityHaste = rpgCharacterStats.getTotalAbilityHaste();
                double cooldownReduction = SkillBar.abilityHasteToMultiplier(abilityHaste);

                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatPalette.PURPLE_LIGHT + "Class: " + ChatPalette.GRAY + "" + className);
                lore.add(ChatPalette.PINK + "Class Element: " + mainElement.getFullName());
                lore.add(ChatPalette.GOLD + "Level: " + ChatPalette.GRAY + "" + level);
                lore.add(ChatPalette.YELLOW + "Experience: " + ChatPalette.GRAY + exp + "/" + expReq);
                lore.add(ChatPalette.GREEN_DARK + "❤ Max Health: " + ChatPalette.GRAY + "" + health + "/" + maxHealth);
                lore.add(ChatPalette.BLUE_LIGHT + "✦ Max Mana: " + ChatPalette.GRAY + "" + mana + "/" + maxMana);
                lore.add("");
                lore.add(ChatPalette.RED + "✦ Element Damage: " + ChatPalette.GRAY + rpgCharacterStats.getTotalElementDamage(player, rpgClassStr));
                lore.add(ChatPalette.BLUE_LIGHT + "■ Element Defense: " + ChatPalette.GRAY + totalDefense + " (" + new DecimalFormat("##.##").format((1.0 - defenseReduction) * 100) + "% reduction)");
                lore.add(ChatPalette.GOLD + "☆ Critical Chance: " + ChatPalette.GRAY + new DecimalFormat("##.##").format(criticalChance) + "%");
                lore.add("");
                lore.add(ChatPalette.GOLD + "? Critical Damage: " + ChatPalette.GRAY + new DecimalFormat("##.##").format(criticalDamage) + "%");
                lore.add(ChatPalette.BLUE_LIGHT + "? Ability Haste: " + ChatPalette.GRAY + abilityHaste + " (" + new DecimalFormat("##.##").format((1.0 - cooldownReduction) * 100) + "% cooldown reduction)");

                /*lore.add("");
                lore.add(ChatPalette.GRAY + "(equipment + level + invested points)");
                // Add attributes to lore (equipment + level + invested points)
                for (AttributeType attributeType : AttributeType.values()) {
                    io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute attribute = rpgCharacterStats.getAttribute(attributeType);
                    lore.add(attributeType.getCustomName() + ": " + ChatPalette.GRAY + attribute.getBonusFromEquipment() + " + " + attribute.getBonusFromLevel(level, rpgClassStr) + " + " + attribute.getInvested());
                }*/

                lore.add("");
                for (ElementType elementType : ElementType.values()) {
                    Element element = rpgCharacterStats.getElement(elementType);
                    lore.add(elementType.getFullName() + ": " + ChatPalette.GRAY + element.getTotal());
                }
                skullMeta.setLore(lore);
                itemStack.setItemMeta(skullMeta);
                return itemStack;
            }
        }
        return new ItemStack(Material.AIR);
    }
}
