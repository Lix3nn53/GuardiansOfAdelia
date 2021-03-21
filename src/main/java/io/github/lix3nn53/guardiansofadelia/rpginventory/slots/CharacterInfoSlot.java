package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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
                skullMeta.setDisplayName(ChatColor.YELLOW + "Character Info");

                int charNo = guardianData.getActiveCharacterNo();
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

                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute bonusDamage = rpgCharacterStats.getBonusElementDamage();
                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute bonusHealth = rpgCharacterStats.getBonusMaxHealth();
                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute bonusDefense = rpgCharacterStats.getBonusElementDefense();
                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute bonusMana = rpgCharacterStats.getBonusMaxMana();
                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute bonusCriticalChance = rpgCharacterStats.getBonusCriticalChance();

                final int totalDefense = rpgCharacterStats.getTotalElementDefense();
                double phyReduction = StatUtils.getDefenseReduction(totalDefense);

                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Class: " + ChatColor.GRAY + "" + className);
                lore.add(ChatColor.GOLD + "Level: " + ChatColor.GRAY + "" + level);
                lore.add(ChatColor.YELLOW + "Experience: " + ChatColor.GRAY + exp + "/" + expReq);
                lore.add(ChatColor.GREEN + "❤ Health: " + ChatColor.GRAY + "" + health + "/" + maxHealth);
                lore.add(ChatColor.AQUA + "✧ Mana: " + ChatColor.GRAY + "" + mana + "/" + maxMana);
                lore.add("");
                lore.add(ChatColor.RED + "⸸ Element Damage: " + ChatColor.GRAY + rpgCharacterStats.getTotalElementDamage(player, rpgClassStr));
                lore.add(ChatColor.AQUA + "■ Element Defense: " + ChatColor.GRAY + totalDefense + " (" + (int) (((1.0 - phyReduction) * 100) + 0.5) + "% reduction)");
                lore.add(ChatColor.GOLD + "⚝ Critical chance: " + ChatColor.GRAY + criticalChance + "%");
                lore.add("");
                lore.add(ChatColor.GRAY + "(equipment + level + invested points)");
                lore.add(AttributeType.BONUS_ELEMENT_DAMAGE.getCustomName() + " : " + ChatColor.GRAY + bonusDamage.getBonusFromEquipment() + " + " + bonusDamage.getBonusFromLevel(level, rpgClassStr) + " + " + bonusDamage.getInvested());
                lore.add(AttributeType.BONUS_ELEMENT_DEFENSE.getCustomName() + ": " + ChatColor.GRAY + bonusDefense.getBonusFromEquipment() + " + " + bonusDefense.getBonusFromLevel(level, rpgClassStr) + " + " + bonusDefense.getInvested());
                lore.add(AttributeType.BONUS_MAX_HEALTH.getCustomName() + ": " + ChatColor.GRAY + bonusHealth.getBonusFromEquipment() + " + " + bonusHealth.getBonusFromLevel(level, rpgClassStr) + " + " + bonusHealth.getInvested());
                lore.add(AttributeType.BONUS_MAX_MANA.getCustomName() + ": " + ChatColor.GRAY + bonusMana.getBonusFromEquipment() + " + " + bonusMana.getBonusFromLevel(level, rpgClassStr) + " + " + bonusMana.getInvested());
                lore.add(AttributeType.BONUS_CRITICAL_CHANCE.getCustomName() + ": " + ChatColor.GRAY + bonusCriticalChance.getBonusFromEquipment() + " + " + bonusCriticalChance.getBonusFromLevel(level, rpgClassStr) + " + " + bonusCriticalChance.getInvested());
                skullMeta.setLore(lore);
                itemStack.setItemMeta(skullMeta);
                return itemStack;
            }
        }
        return new ItemStack(Material.AIR);
    }
}
