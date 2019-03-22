package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
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
        skullMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GOLD + "Level: " + ChatColor.GRAY + "" + "level");
            add(ChatColor.YELLOW + "Experience: " + ChatColor.GRAY + "exp" + "/" + "maxexp");
            add(ChatColor.GREEN + "❤ Health: " + ChatColor.GRAY + "" + "hp" + "/" + "maxhp");
            add(ChatColor.AQUA + "✧ Mana: " + ChatColor.GRAY + "" + "mana" + "/" + "maxmana");
            add(ChatColor.YELLOW + "----------------");
            add(ChatColor.RED + "➹ Damage: " + ChatColor.GRAY + "" + "damage" + "");
            add(ChatColor.RED + "➹ Ranged Damage: " + ChatColor.GRAY + "" + "rangeddamage" + "");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: " + ChatColor.GRAY + "" + "m");
            add(ChatColor.AQUA + "■ Defense: " + ChatColor.GRAY + "" + "d");
            add(ChatColor.BLUE + "✦ Magic Defense: " + ChatColor.GRAY + "" + "");
            add(ChatColor.GOLD + "⚝Critical chance: " + ChatColor.GRAY + "chance" + "%");
            add(ChatColor.YELLOW + "----------------");
            add(ChatColor.RED + "☄" + ChatColor.GRAY + " Fire: " + "" + " (+" + "" + ")");
            add(ChatColor.BLUE + "◎ " + ChatColor.GRAY + "Water: " + "" + " (+" + ")");
            add(ChatColor.DARK_GREEN + "₪ " + ChatColor.GRAY + "Earth: " + "(+" + "" + ")");
            add(ChatColor.AQUA + "ϟ " + ChatColor.GRAY + "Lightning: " + "(+" + "" + ")");
            add(ChatColor.WHITE + "๑ " + ChatColor.GRAY + "Air: " + "(+" + ")");
        }});
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }
}
