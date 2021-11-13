package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.menu.main.character.*;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
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
        if (slot == 9) {
            GuiCharacterClassManager gui = new GuiCharacterClassManager(guardianData);
            gui.openInventory(player);
        } else if (slot == 11) {
            SkillBar skillBar = rpgCharacter.getSkillBar();

            int pointsLeft = skillBar.getSkillPointsLeftToSpend();

            GuiCharacterSkills gui = new GuiCharacterSkills(player, rpgCharacter, skillBar, pointsLeft);
            gui.openInventory(player);
        } else if (slot == 13) {
            RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();

            int pointsLeft = rpgCharacterStats.getAttributePointsLeftToSpend();

            GuiCharacterStatInvest gui = new GuiCharacterStatInvest(pointsLeft, rpgCharacterStats);
            gui.openInventory(player);
        } else if (slot == 15) {
            GuiCharacterCrafting gui = new GuiCharacterCrafting(rpgCharacter);
            gui.openInventory(player);
        } else if (slot == 17) {
            GuiCharacterChatTag gui = new GuiCharacterChatTag(player);
            gui.openInventory(player);
        }
    }
}
