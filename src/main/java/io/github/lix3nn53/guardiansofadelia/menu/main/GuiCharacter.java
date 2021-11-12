package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.menu.MenuList;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiCharacter extends GuiGeneric {

    public GuiCharacter(GuardianData guardianData) {
        super(27, "Character", 0);

        ItemStack skills = new ItemStack(Material.WOODEN_PICKAXE);
        String rpgClassStr = guardianData.getActiveCharacter().getRpgClassStr();
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        int classIconCustomModelData = rpgClass.getClassIconCustomModelData();
        ItemMeta itemMeta = skills.getItemMeta();
        itemMeta.setCustomModelData(classIconCustomModelData);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatPalette.YELLOW + Translation.t(guardianData, "menu.character.class.name"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.character.class.l1"));
        lore.add("");
        lore.add("Current Class: " + rpgClass.getClassString());
        itemMeta.setLore(lore);
        skills.setItemMeta(itemMeta);
        this.setItem(9, skills);

        itemMeta.setCustomModelData(29);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + Translation.t(guardianData, "menu.character.skill.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "menu.character.skill.name"));
        itemMeta.setLore(lore);
        skills.setItemMeta(itemMeta);
        this.setItem(11, skills);

        ItemStack statpoints = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(6);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Stat Points");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Spend stat points earned by leveling");
        lore.add(ChatPalette.GRAY + "your character.");
        itemMeta.setLore(lore);
        statpoints.setItemMeta(itemMeta);
        this.setItem(13, statpoints);

        ItemStack job = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(21);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Crafting");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Manage your character's job!");
        itemMeta.setLore(lore);
        job.setItemMeta(itemMeta);
        this.setItem(15, job);

        ItemStack chat = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(4);
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Chat Tag");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Select your chat tag!");
        itemMeta.setLore(lore);
        chat.setItemMeta(itemMeta);
        this.setItem(17, chat);
    }

    @Override
    public void onClick(Player player, GuardianData guardianData, String title, int slot) {
        if (slot == 9) {
            GuiGeneric classManage = MenuList.classManager(player);
            classManage.openInventory(player);
        } else if (slot == 11) {
            GuiGeneric skill = MenuList.skill(player);
            skill.openInventory(player);
        } else if (slot == 13) {
            GuiGeneric statPoints = MenuList.statPoints(player);
            statPoints.openInventory(player);
        } else if (slot == 15) {
            GuiGeneric crafting = MenuList.crafting(player);
            crafting.openInventory(player);
        } else if (slot == 17) {
            GuiGeneric chatTag = MenuList.chatTagQuests(player);
            chatTag.openInventory(player);
        }
    }
}
