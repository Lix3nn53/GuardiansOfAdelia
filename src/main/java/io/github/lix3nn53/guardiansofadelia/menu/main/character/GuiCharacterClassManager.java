package io.github.lix3nn53.guardiansofadelia.menu.main.character;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.menu.main.GuiCharacter;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiCharacterClassManager extends GuiGeneric {

    public GuiCharacterClassManager(GuardianData guardianData) {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK + Translation.t(guardianData, "character.class.manager"), 0);

        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

        String rpgClassStr = rpgCharacter.getRpgClassStr();
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);

        ItemStack currentRPGClass = RPGClassManager.getPersonalIcon(rpgClass, 99, rpgCharacter);
        this.setItem(18, currentRPGClass);
        ItemMeta metaRpg = currentRPGClass.getItemMeta();
        ItemStack empty = OtherItems.getEmpty(metaRpg.getDisplayName(), metaRpg.getLore());
        this.setItem(19, empty);
        this.setItem(9, empty);
        this.setItem(10, empty);


        ItemStack t1 = new ItemStack(Material.IRON_BLOCK);
        ItemMeta itemMeta = t1.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "character.class.tier") + " 1");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Change to a tier 1 class you have unlocked");
        itemMeta.setLore(lore);
        t1.setItemMeta(itemMeta);
        this.setItem(21, t1);

        ItemStack t2 = new ItemStack(Material.GOLD_BLOCK);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "character.class.tier") + " 2");
        lore.clear();
        lore.add("");
        lore.add("Change to a tier 2 class you have unlocked");
        itemMeta.setLore(lore);
        t2.setItemMeta(itemMeta);
        this.setItem(23, t2);

        ItemStack t3 = new ItemStack(Material.DIAMOND_BLOCK);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "character.class.tier") + " 3");
        lore.clear();
        lore.add("");
        lore.add("Change to a tier 3 class you have unlocked");
        itemMeta.setLore(lore);
        t3.setItemMeta(itemMeta);
        this.setItem(25, t3);

        ItemStack backButton = OtherItems.getBackButton("Character Menu");
        this.setItem(0, backButton);
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

        if (rpgCharacter != null) {
            int slot = event.getSlot();

            if (slot == 0) {
                GuiCharacter gui = new GuiCharacter(guardianData);
                gui.openInventory(player);
            } else if (slot == 21) {
                GuiCharacterClassChange gui = new GuiCharacterClassChange(player, guardianData, 1);
                gui.openInventory(player);
            } else if (slot == 23) {
                GuiCharacterClassChange gui = new GuiCharacterClassChange(player, guardianData, 2);
                gui.openInventory(player);
            } else if (slot == 25) {
                GuiCharacterClassChange gui = new GuiCharacterClassChange(player, guardianData, 3);
                gui.openInventory(player);
            }
        }
    }
}
