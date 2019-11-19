package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterExperienceManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class CharacterInfoSlot {

    private final Player player;

    public CharacterInfoSlot(Player player) {
        this.player = player;
    }

    public ItemStack getItem() {
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                skullMeta.setDisplayName(ChatColor.YELLOW + "Character Info");

                int charNo = guardianData.getActiveCharacterNo();
                RPGClass rpgClass = activeCharacter.getRpgClass();
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

                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute fire = rpgCharacterStats.getFire();
                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute water = rpgCharacterStats.getWater();
                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute earth = rpgCharacterStats.getEarth();
                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute lightning = rpgCharacterStats.getLightning();
                final io.github.lix3nn53.guardiansofadelia.guardian.attribute.Attribute wind = rpgCharacterStats.getWind();

                skullMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.LIGHT_PURPLE + "Class: " + ChatColor.GRAY + "" + className);
                    add(ChatColor.GOLD + "Level: " + ChatColor.GRAY + "" + level);
                    add(ChatColor.YELLOW + "Experience: " + ChatColor.GRAY + exp + "/" + expReq);
                    add(ChatColor.GREEN + "❤ Health: " + ChatColor.GRAY + "" + health + "/" + maxHealth);
                    add(ChatColor.AQUA + "✧ Mana: " + ChatColor.GRAY + "" + mana + "/" + maxMana);
                    add(ChatColor.YELLOW + "----------------");
                    add(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + rpgCharacterStats.getTotalMeleeDamage(player, rpgClass));
                    add(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + rpgCharacterStats.getTotalRangedDamage(player, rpgClass));
                    add(ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + rpgCharacterStats.getTotalMagicDamage(player, rpgClass));
                    add(ChatColor.AQUA + "■ Defense: " + ChatColor.GRAY + rpgCharacterStats.getTotalDefense());
                    add(ChatColor.BLUE + "✦ Magic Defense: " + ChatColor.GRAY + rpgCharacterStats.getTotalMagicDefense());
                    add(ChatColor.GOLD + "⚝ Critical chance: " + ChatColor.GRAY + criticalChance + "%");
                    add(ChatColor.YELLOW + "----------------");
                    add(ChatColor.GRAY + "(equipment + level + points)");
                    add(ChatColor.RED + "☄" + ChatColor.GRAY + " Fire: " + fire.getBonusFromEquipment() + " + " + fire.getBonusFromLevel(level, rpgClass) + " + " + fire.getInvested());
                    add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + water.getBonusFromEquipment() + " + " + water.getBonusFromLevel(level, rpgClass) + " + " + water.getInvested());
                    add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + earth.getBonusFromEquipment() + " + " + earth.getBonusFromLevel(level, rpgClass) + " + " + earth.getInvested());
                    add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + lightning.getBonusFromEquipment() + " + " + lightning.getBonusFromLevel(level, rpgClass) + " + " + lightning.getInvested());
                    add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Wind: " + wind.getBonusFromEquipment() + " + " + wind.getBonusFromLevel(level, rpgClass) + " + " + wind.getInvested());
                }});
                itemStack.setItemMeta(skullMeta);
                return itemStack;
            }
        }
        return new ItemStack(Material.AIR);
    }
}
