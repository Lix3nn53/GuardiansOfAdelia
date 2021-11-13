package io.github.lix3nn53.guardiansofadelia.menu.main.character;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
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
        super(27, ChatPalette.GRAY_DARK + Translation.t(guardianData, "character.class.manager"), 0);

        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

        String rpgClassStr = rpgCharacter.getRpgClassStr();
        RPGClass rpgClass = RPGClassManager.getClass(rpgClassStr);

        ItemStack itemStack = RPGClassManager.getPersonalIcon(rpgClass, 99, rpgCharacter);
        this.setItem(4, itemStack);

        itemStack = new ItemStack(Material.IRON_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "character.class.tier") + " 1");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Change to a tier 1 class you have unlocked");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.setItem(20, itemStack);

        itemStack = new ItemStack(Material.GOLD_BLOCK);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "character.class.tier") + " 2");
        lore.clear();
        lore.add("");
        lore.add("Change to a tier 2 class you have unlocked");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.setItem(22, itemStack);

        itemStack = new ItemStack(Material.DIAMOND_BLOCK);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "character.class.tier") + " 3");
        lore.clear();
        lore.add("");
        lore.add("Change to a tier 3 class you have unlocked");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        this.setItem(24, itemStack);
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

            if (slot == 20) {
                GuiCharacterClassChange gui = new GuiCharacterClassChange(player, guardianData, 1);
                gui.openInventory(player);
            } else if (slot == 22) {
                GuiCharacterClassChange gui = new GuiCharacterClassChange(player, guardianData, 2);
                gui.openInventory(player);
            } else if (slot == 24) {
                GuiCharacterClassChange gui = new GuiCharacterClassChange(player, guardianData, 3);
                gui.openInventory(player);
            }
        }
    }
}
