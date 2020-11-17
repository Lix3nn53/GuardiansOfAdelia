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
        GuiGeneric guiGeneric = new GuiGeneric(54, ChatColor.DARK_GRAY + "Character " + charNo + " Creation", 0);

        ItemStack s1 = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = s1.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(9);
        itemMeta.setDisplayName(ChatColor.AQUA + "Knight");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "❤ Health: ▋▋▋▋▋");
        lore.add(ChatColor.AQUA + "✧ Mana: ▋▋");
        lore.add(ChatColor.RED + "➹ Damage: ▋▋▋");
        lore.add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋");
        lore.add(ChatColor.AQUA + "■ Defense: ▋▋▋▋▋");
        lore.add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
        lore.add("");
        lore.add(ChatColor.YELLOW + "The powerhouse and mainstay of any party.");
        lore.add(ChatColor.YELLOW + "Knights battle foes at close range while");
        lore.add(ChatColor.YELLOW + "allies support them from all sides.");
        itemMeta.setLore(lore);
        s1.setItemMeta(itemMeta);
        guiGeneric.setItem(20, s1);

        ItemStack s2 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(8);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Paladin");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "❤ Health: ▋▋▋▋");
        lore.add(ChatColor.AQUA + "✧ Mana: ▋▋▋▋");
        lore.add(ChatColor.RED + "➹ Damage: ▋▋");
        lore.add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋");
        lore.add(ChatColor.AQUA + "■ Defense: ▋▋▋");
        lore.add(ChatColor.BLUE + "✦ Magic Defense: ▋▋▋▋");
        lore.add("");
        lore.add(ChatColor.YELLOW + "Paladins aids fellow adventurers with");
        lore.add(ChatColor.YELLOW + "the holy power of light and beats foes");
        lore.add(ChatColor.YELLOW + "by channeling holy wrath.");
        itemMeta.setLore(lore);
        s2.setItemMeta(itemMeta);
        guiGeneric.setItem(6, s2);

        ItemStack s3 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(12);
        itemMeta.setDisplayName(ChatColor.RED + "Warrior");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "❤ Health: ▋▋▋▋");
        lore.add(ChatColor.AQUA + "✧ Mana: ▋▋");
        lore.add(ChatColor.RED + "➹ Damage: ▋▋▋▋▋▋");
        lore.add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋");
        lore.add(ChatColor.AQUA + "■ Defense: ▋▋▋");
        lore.add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
        lore.add("");
        lore.add(ChatColor.YELLOW + "Thanks to their strength, fearlessness and");
        lore.add(ChatColor.YELLOW + "superior combat skills, warriors are");
        lore.add(ChatColor.YELLOW + "always ready to take on any enemy.");
        itemMeta.setLore(lore);
        s3.setItemMeta(itemMeta);
        guiGeneric.setItem(2, s3);

        ItemStack s4 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(11);
        itemMeta.setDisplayName(ChatColor.BLUE + "Rogue");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "❤ Health: ▋▋▋");
        lore.add(ChatColor.AQUA + "✧ Mana: ▋▋▋");
        lore.add(ChatColor.RED + "➹ Damage: ▋▋▋▋▋");
        lore.add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋");
        lore.add(ChatColor.AQUA + "■ Defense: ▋▋▋");
        lore.add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
        lore.add("");
        lore.add(ChatColor.YELLOW + "Due to their incredible speed and agility,");
        lore.add(ChatColor.YELLOW + "Rogues are an expert at dealing");
        lore.add(ChatColor.YELLOW + "damage in a short amount of time!");
        itemMeta.setLore(lore);
        s4.setItemMeta(itemMeta);
        guiGeneric.setItem(22, s4);

        ItemStack s5 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(7);
        itemMeta.setDisplayName(ChatColor.GREEN + "Archer");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "❤ Health: ▋▋");
        lore.add(ChatColor.AQUA + "✧ Mana: ▋▋▋▋");
        lore.add(ChatColor.RED + "➹ Damage: ▋▋▋▋");
        lore.add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋▋");
        lore.add(ChatColor.AQUA + "■ Defense: ▋▋▋");
        lore.add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
        lore.add("");
        lore.add(ChatColor.YELLOW + "The Archer has lots of movement and");
        lore.add(ChatColor.YELLOW + "dexterity, allowing you to kite your target");
        lore.add(ChatColor.YELLOW + "and deal damage from long distances.");
        itemMeta.setLore(lore);
        s5.setItemMeta(itemMeta);
        guiGeneric.setItem(24, s5);

        ItemStack s6 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(10);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Mage");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "❤ Health: ▋▋");
        lore.add(ChatColor.AQUA + "✧ Mana: ▋▋▋▋▋");
        lore.add(ChatColor.RED + "➹ Damage: ▋");
        lore.add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋▋▋▋");
        lore.add(ChatColor.AQUA + "■ Defense: ▋▋");
        lore.add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
        lore.add("");
        lore.add(ChatColor.YELLOW + "The Mage may be frail and weak in body,");
        lore.add(ChatColor.YELLOW + "but that is a worthy sacrifice for");
        lore.add(ChatColor.YELLOW + "the strength of the mind.");
        itemMeta.setLore(lore);
        s6.setItemMeta(itemMeta);
        guiGeneric.setItem(4, s6);

        ItemStack s7 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(15);
        itemMeta.setDisplayName(ChatColor.GOLD + "Monk");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "❤ Health: ▋▋▋▋");
        lore.add(ChatColor.AQUA + "✧ Mana: ▋▋▋");
        lore.add(ChatColor.RED + "➹ Damage: ▋▋▋▋");
        lore.add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋");
        lore.add(ChatColor.AQUA + "■ Defense: ▋▋▋");
        lore.add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
        lore.add("");
        lore.add(ChatColor.YELLOW + "Monks have such a strong will that never");
        lore.add(ChatColor.YELLOW + "falls into despair in any situation.");
        lore.add(ChatColor.YELLOW + "Due to their unique weapon Monks are");
        lore.add(ChatColor.YELLOW + "feared opponents at any distance");
        itemMeta.setLore(lore);
        s7.setItemMeta(itemMeta);
        guiGeneric.setItem(38, s7);

        ItemStack s8 = new ItemStack(Material.STONE_PICKAXE);
        itemMeta.setCustomModelData(16);
        itemMeta.setDisplayName(ChatColor.DARK_GREEN + "Hunter");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "❤ Health: ▋▋▋");
        lore.add(ChatColor.AQUA + "✧ Mana: ▋▋▋▋");
        lore.add(ChatColor.RED + "➹ Damage: ▋▋▋▋");
        lore.add(ChatColor.DARK_AQUA + "✦ Magic Damage: ▋▋");
        lore.add(ChatColor.AQUA + "■ Defense: ▋▋▋");
        lore.add(ChatColor.BLUE + "✦ Magic Defense: ▋▋");
        lore.add("");
        lore.add(ChatColor.YELLOW + "The Hunter uses a slow but strong ranged");
        lore.add(ChatColor.YELLOW + "weapon, crossbows. Their hunting techniques");
        lore.add(ChatColor.YELLOW + "makes anyone a prey for the deadly arrows.");
        itemMeta.setLore(lore);
        s8.setItemMeta(itemMeta);
        guiGeneric.setItem(42, s8);

        return guiGeneric;
    }

    public static GuiGeneric characterSelectionMenu(int charNo) {
        GuiGeneric guiGeneric = new GuiGeneric(36, ChatColor.DARK_GRAY + "Character " + charNo + " Selection", 0);

        ItemStack lastLocation = new ItemStack(Material.MAGENTA_WOOL);
        ItemMeta itemMeta = lastLocation.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport to your last location");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        lastLocation.setItemMeta(itemMeta);
        guiGeneric.setItem(11, lastLocation);

        ItemStack city1 = new ItemStack(Material.LIGHT_BLUE_WOOL);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Roumen");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required level: 1");
        itemMeta.setLore(lore);
        city1.setItemMeta(itemMeta);
        guiGeneric.setItem(13, city1);

        ItemStack city2 = new ItemStack(Material.LIGHT_BLUE_WOOL, 2);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Port Veloa");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required level: 10");
        itemMeta.setLore(lore);
        city2.setItemMeta(itemMeta);
        guiGeneric.setItem(15, city2);

        ItemStack city3 = new ItemStack(Material.LIGHT_BLUE_WOOL, 3);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Elderine");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required level: 20");
        itemMeta.setLore(lore);
        city3.setItemMeta(itemMeta);
        guiGeneric.setItem(20, city3);

        ItemStack city4 = new ItemStack(Material.LIGHT_BLUE_WOOL, 4);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Uruga");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required level: 40");
        itemMeta.setLore(lore);
        city4.setItemMeta(itemMeta);
        guiGeneric.setItem(22, city4);

        ItemStack city5 = new ItemStack(Material.LIGHT_BLUE_WOOL, 5);
        itemMeta.setDisplayName(ChatColor.AQUA + "Teleport to Alberstol Ruins");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required level: 70");
        itemMeta.setLore(lore);
        city5.setItemMeta(itemMeta);
        guiGeneric.setItem(24, city5);

        return guiGeneric;
    }

    public static GuiGeneric tutorialSkipMenu(String rpgClass, int charNo) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.DARK_GRAY + "Play Tutorial? " + rpgClass + " " + charNo, 0);

        ItemStack yes = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = yes.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.GREEN + "Yes, play tutorial.");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        yes.setItemMeta(itemMeta);
        guiGeneric.setItem(11, yes);

        ItemStack no = new ItemStack(Material.RED_WOOL);
        itemMeta.setDisplayName(ChatColor.RED + "No, skip tutorial");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required level: 1");
        itemMeta.setLore(lore);
        no.setItemMeta(itemMeta);
        guiGeneric.setItem(15, no);

        return guiGeneric;
    }
}
