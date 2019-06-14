package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterExperienceManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import net.md_5.bungee.api.ChatColor;
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
                String className = activeCharacter.getRpgClass().getClassString();
                int mana = rpgCharacterStats.getCurrentMana();
                int maxMana = rpgCharacterStats.getTotalMaxMana();
                int totalExp = rpgCharacterStats.getTotalExp();
                int exp = RPGCharacterExperienceManager.getCurrentExperience(totalExp);
                final int level = player.getLevel();
                int expReq = RPGCharacterExperienceManager.getRequiredExperience(level);
                int maxHealth = (int) (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 0.5);
                int health = (int) (player.getHealth() + 0.5);

                skullMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.LIGHT_PURPLE + "Class: " + ChatColor.GRAY + "" + className);
                    add(ChatColor.GOLD + "Level: " + ChatColor.GRAY + "" + level);
                    add(ChatColor.YELLOW + "Experience: " + ChatColor.GRAY + exp + "/" + expReq);
                    add(ChatColor.GREEN + "❤ Health: " + ChatColor.GRAY + "" + health + "/" + maxHealth);
                    add(ChatColor.AQUA + "✧ Mana: " + ChatColor.GRAY + "" + mana + "/" + maxMana);
                    add(ChatColor.YELLOW + "----------------");
                    add(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "" + "damage" + "");
                    add(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "" + "rangeddamage" + "");
                    add(ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + "" + "m");
                    add(ChatColor.AQUA + "■ Defense: " + ChatColor.GRAY + "" + "d");
                    add(ChatColor.BLUE + "✦ Magic Defense: " + ChatColor.GRAY + "" + "");
                    add(ChatColor.GOLD + "⚝Critical chance: " + ChatColor.GRAY + "chance" + "%");
                    add(ChatColor.YELLOW + "----------------");
                    add(ChatColor.RED + "☄" + ChatColor.GRAY + " Fire: " + rpgCharacterStats.getFire().getBonus() + " (+" + rpgCharacterStats.getFire().getInvested() + ")");
                    add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + rpgCharacterStats.getWater().getBonus() + " (+" + rpgCharacterStats.getWater().getInvested() + ")");
                    add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + rpgCharacterStats.getEarth().getBonus() + " (+" + rpgCharacterStats.getEarth().getInvested() + ")");
                    add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + rpgCharacterStats.getLightning().getBonus() + " (+" + rpgCharacterStats.getLightning().getInvested() + ")");
                    add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Wind: " + rpgCharacterStats.getWind().getBonus() + " (+" + rpgCharacterStats.getWind().getInvested() + ")");
                }});
                itemStack.setItemMeta(skullMeta);
                return itemStack;
            }
        }
        return new ItemStack(Material.AIR);
    }
}
