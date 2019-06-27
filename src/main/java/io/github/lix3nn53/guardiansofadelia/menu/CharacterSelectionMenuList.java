package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CharacterSelectionMenuList {

    public static GuiGeneric getCharacterCreationMenu(int charNo) {
        GuiGeneric guiGeneric = new GuiGeneric(54, ChatColor.YELLOW + "Character " + charNo + " Creation", 0);

        ItemStack s1 = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = s1.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(10000009);
        itemMeta.setDisplayName(ChatColor.AQUA + "Knight");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "❤ Health: ▋▋▋▋▋");
            add(ChatColor.AQUA + "✧ Mana: ▋▋");
            add(ChatColor.RED + "➹ Damage: ▋▋▋");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋");
            add(ChatColor.AQUA + "■ Defense: ▋▋▋▋▋");
            add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
            add("");
            add(ChatColor.YELLOW + "The powerhouse and mainstay of any party.");
            add(ChatColor.YELLOW + "Knights battle foes at close range while");
            add(ChatColor.YELLOW + "allies support them from all sides.");
        }});
        s1.setItemMeta(itemMeta);
        guiGeneric.setItem(20, s1);

        ItemStack s2 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(10000008);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Paladin");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "❤ Health: ▋▋▋▋");
            add(ChatColor.AQUA + "✧ Mana: ▋▋▋▋");
            add(ChatColor.RED + "➹ Damage: ▋▋");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋");
            add(ChatColor.AQUA + "■ Defense: ▋▋▋");
            add(ChatColor.BLUE + "✦ Magic Defense: ▋▋▋▋");
            add("");
            add(ChatColor.YELLOW + "Paladins aids fellow adventurers with");
            add(ChatColor.YELLOW + "the holy power of light and beats foes");
            add(ChatColor.YELLOW + "by channeling holy wrath.");
        }});
        s2.setItemMeta(itemMeta);
        guiGeneric.setItem(6, s2);

        ItemStack s3 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(10000012);
        itemMeta.setDisplayName(ChatColor.RED + "Warrior");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "❤ Health: ▋▋▋▋");
            add(ChatColor.AQUA + "✧ Mana: ▋▋");
            add(ChatColor.RED + "➹ Damage: ▋▋▋▋▋▋");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋");
            add(ChatColor.AQUA + "■ Defense: ▋▋▋");
            add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
            add("");
            add(ChatColor.YELLOW + "Thanks to their strength, fearlessness and");
            add(ChatColor.YELLOW + "superior combat skills, warriors are");
            add(ChatColor.YELLOW + "always ready to take on any enemy.");
        }});
        s3.setItemMeta(itemMeta);
        guiGeneric.setItem(2, s3);

        ItemStack s4 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(10000011);
        itemMeta.setDisplayName(ChatColor.BLUE + "Rogue");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "❤ Health: ▋▋▋");
            add(ChatColor.AQUA + "✧ Mana: ▋▋▋");
            add(ChatColor.RED + "➹ Damage: ▋▋▋▋▋");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋");
            add(ChatColor.AQUA + "■ Defense: ▋▋▋");
            add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
            add("");
            add(ChatColor.YELLOW + "Due to their incredible speed and agility,");
            add(ChatColor.YELLOW + "Rogues are an expert at dealing");
            add(ChatColor.YELLOW + "damage in a short amount of time!");
        }});
        s4.setItemMeta(itemMeta);
        guiGeneric.setItem(22, s4);

        ItemStack s5 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(10000007);
        itemMeta.setDisplayName(ChatColor.GREEN + "Archer");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "❤ Health: ▋▋");
            add(ChatColor.AQUA + "✧ Mana: ▋▋▋▋");
            add(ChatColor.RED + "➹ Damage: ▋▋▋▋");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋▋");
            add(ChatColor.AQUA + "■ Defense: ▋▋▋");
            add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
            add("");
            add(ChatColor.YELLOW + "The Archer has lots of movement and");
            add(ChatColor.YELLOW + "dexterity, allowing you to kite your target");
            add(ChatColor.YELLOW + "and deal damage from long distances.");
        }});
        s5.setItemMeta(itemMeta);
        guiGeneric.setItem(24, s5);

        ItemStack s6 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(10000010);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mage");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "❤ Health: ▋▋");
            add(ChatColor.AQUA + "✧ Mana: ▋▋▋▋▋");
            add(ChatColor.RED + "➹ Damage: ▋");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋▋▋▋");
            add(ChatColor.AQUA + "■ Defense: ▋▋");
            add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
            add("");
            add(ChatColor.YELLOW + "The Mage may be frail and weak in body,");
            add(ChatColor.YELLOW + "but that is a worthy sacrifice for");
            add(ChatColor.YELLOW + "the strength of the mind.");
        }});
        s6.setItemMeta(itemMeta);
        guiGeneric.setItem(4, s6);

        ItemStack s7 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(10000015);
        itemMeta.setDisplayName(ChatColor.GOLD + "Monk");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "❤ Health: ▋▋▋▋");
            add(ChatColor.AQUA + "✧ Mana: ▋▋▋");
            add(ChatColor.RED + "➹ Damage: ▋▋▋▋");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋");
            add(ChatColor.AQUA + "■ Defense: ▋▋▋");
            add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
            add("");
            add(ChatColor.YELLOW + "Monks have such a strong will that never");
            add(ChatColor.YELLOW + "falls into despair in any situation.");
            add(ChatColor.YELLOW + "Due to their unique weapon Monks are");
            add(ChatColor.YELLOW + "feared opponents at any distance");
        }});
        s7.setItemMeta(itemMeta);
        guiGeneric.setItem(38, s7);

        ItemStack s8 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(10000016);
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "Hunter");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "❤ Health: ▋▋▋");
            add(ChatColor.AQUA + "✧ Mana: ▋▋▋▋");
            add(ChatColor.RED + "➹ Damage: ▋▋▋▋");
            add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋");
            add(ChatColor.AQUA + "■ Defense: ▋▋▋");
            add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
            add("");
            add(ChatColor.YELLOW + "The Hunter uses a slow but strong ranged");
            add(ChatColor.YELLOW + "weapon, crossbows. Their hunting techniques");
            add(ChatColor.YELLOW + "makes anyone a prey for the deadly arrows.");
        }});
        s8.setItemMeta(itemMeta);
        guiGeneric.setItem(42, s8);

        return guiGeneric;
    }

    public static GuiGeneric characterSelectionMenu(int charNo) {
        GuiGeneric guiGeneric = new GuiGeneric(36, ChatColor.YELLOW + "Character " + charNo + " Selection", 0);

        ItemStack lastLocation = new ItemStack(Material.MAGENTA_WOOL);
        ItemMeta itemMeta = lastLocation.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport to your last location");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        lastLocation.setItemMeta(itemMeta);
        guiGeneric.setItem(11, lastLocation);

        ItemStack city1 = new ItemStack(Material.LIGHT_BLUE_WOOL);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Roumen");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Required level: 1");
        }});
        city1.setItemMeta(itemMeta);
        guiGeneric.setItem(13, city1);

        ItemStack city2 = new ItemStack(Material.LIGHT_BLUE_WOOL, 2);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Port Veloa");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Required level: 10");
        }});
        city2.setItemMeta(itemMeta);
        guiGeneric.setItem(15, city2);

        ItemStack city3 = new ItemStack(Material.LIGHT_BLUE_WOOL, 3);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Elderine");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Required level: 20");
        }});
        city3.setItemMeta(itemMeta);
        guiGeneric.setItem(20, city3);

        ItemStack city4 = new ItemStack(Material.LIGHT_BLUE_WOOL, 4);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Uruga");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Required level: 40");
        }});
        city4.setItemMeta(itemMeta);
        guiGeneric.setItem(22, city4);

        ItemStack city5 = new ItemStack(Material.LIGHT_BLUE_WOOL, 5);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Arberstol Ruins");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Required level: 70");
        }});
        city5.setItemMeta(itemMeta);
        guiGeneric.setItem(24, city5);

        return guiGeneric;
    }
}
