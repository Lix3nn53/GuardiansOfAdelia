package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import net.md_5.bungee.api.ChatColor;
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
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setDisplayName(ChatColor.YELLOW + "Character Info");

        int charNo = SkillAPIUtils.getActiveCharacterNo(player);
        String className = SkillAPIUtils.getClassName(player, charNo);
        int mana = SkillAPIUtils.getMana(player, charNo);
        int maxMana = SkillAPIUtils.getMaxMana(player, charNo);
        int exp = SkillAPIUtils.getExp(player, charNo);
        int expReq = SkillAPIUtils.getRequiredExp(player, charNo);
        int maxHealth = (int) (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 0.5);
        int health = (int) (player.getHealth() + 0.5);

        skullMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.LIGHT_PURPLE + "Class: " + ChatColor.GRAY + "" + className);
            add(ChatColor.GOLD + "Level: " + ChatColor.GRAY + "" + player.getLevel());
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
            add(ChatColor.RED + "☄" + ChatColor.GRAY + " Fire: " + SkillAPIUtils.getBonusAttribute(player, "fire") + " (+" + SkillAPIUtils.getInvestedAttribute(player, "fire") + ")");
            add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + SkillAPIUtils.getBonusAttribute(player, "water") + " (+" + SkillAPIUtils.getInvestedAttribute(player, "water") + ")");
            add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + SkillAPIUtils.getBonusAttribute(player, "earth") + "(+" + SkillAPIUtils.getInvestedAttribute(player, "earth") + ")");
            add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + SkillAPIUtils.getBonusAttribute(player, "lightning") + "(+" + SkillAPIUtils.getInvestedAttribute(player, "lightning") + ")");
            add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Air: " + SkillAPIUtils.getBonusAttribute(player, "air") + "(+" + SkillAPIUtils.getInvestedAttribute(player, "air") + ")");
        }});
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }
}
