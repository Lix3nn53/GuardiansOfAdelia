package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.menu.GuiHelper;
import io.github.lix3nn53.guardiansofadelia.menu.main.character.*;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiCharacter extends GuiGeneric {

    public GuiCharacter(GuardianData guardianData) {
        super(54, CustomCharacterGui.MENU_54.toString() + ChatPalette.BLACK + Translation.t(guardianData, "character.name"), 0);

        ItemStack classItem = new ItemStack(Material.WOODEN_PICKAXE);
        String rpgClassStr = guardianData.getActiveCharacter().getRpgClassStr();
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);
        int classIconCustomModelData = rpgClass.getClassIconCustomModelData();
        ItemMeta itemMeta = classItem.getItemMeta();
        itemMeta.setCustomModelData(classIconCustomModelData);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatPalette.YELLOW + Translation.t(guardianData, "character.class.name"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "character.class.l1"));
        lore.add("");
        lore.add(Translation.t(guardianData, "character.class.name") + ": " + rpgClass.getClassString());
        itemMeta.setLore(lore);
        classItem.setItemMeta(itemMeta);

        ItemStack skills = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(29);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + Translation.t(guardianData, "character.skill.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "character.skill.l1"));
        itemMeta.setLore(lore);
        skills.setItemMeta(itemMeta);

        ItemStack statpoints = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(6);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + Translation.t(guardianData, "character.stat.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "character.stat.l1"));
        itemMeta.setLore(lore);
        statpoints.setItemMeta(itemMeta);

        ItemStack job = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(21);
        itemMeta.setDisplayName(ChatPalette.YELLOW + Translation.t(guardianData, "character.crafting.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "character.crafting.l1"));
        itemMeta.setLore(lore);
        job.setItemMeta(itemMeta);

        ItemStack chat = new ItemStack(Material.WOODEN_PICKAXE);
        itemMeta.setCustomModelData(4);
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + Translation.t(guardianData, "character.chattag.name"));
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "character.chattag.l1"));
        itemMeta.setLore(lore);
        chat.setItemMeta(itemMeta);

        GuiHelper.form54Big(this, new ItemStack[]{classItem, skills, statpoints, job, chat}, "Main Menu");
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        RPGCharacter rpgCharacter;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                rpgCharacter = guardianData.getActiveCharacter();
            } else {
                return;
            }
        } else {
            return;
        }


        int slot = event.getSlot();
        if (slot == 0) {
            GuiMain gui = new GuiMain(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(0).contains(slot)) {
            GuiCharacterClassManager gui = new GuiCharacterClassManager(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(1).contains(slot)) {
            SkillBar skillBar = rpgCharacter.getSkillBar();

            int pointsLeft = skillBar.getSkillPointsLeftToSpend();

            GuiCharacterSkills gui = new GuiCharacterSkills(player, guardianData, rpgCharacter, skillBar, pointsLeft);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(2).contains(slot)) {
            RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();

            int pointsLeft = rpgCharacterStats.getAttributePointsLeftToSpend();

            GuiCharacterStatInvest gui = new GuiCharacterStatInvest(pointsLeft, guardianData, rpgCharacterStats);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(3).contains(slot)) {
            GuiCharacterCrafting gui = new GuiCharacterCrafting(guardianData, rpgCharacter);
            gui.openInventory(player);
        } else if (GuiHelper.get54BigButtonIndexes(4).contains(slot)) {
            GuiCharacterChatTag gui = new GuiCharacterChatTag(player);
            gui.openInventory(player);
        }
    }
}
