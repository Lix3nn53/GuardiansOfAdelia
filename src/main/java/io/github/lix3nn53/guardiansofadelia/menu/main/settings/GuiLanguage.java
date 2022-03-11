package io.github.lix3nn53.guardiansofadelia.menu.main.settings;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.GuiHelper;
import io.github.lix3nn53.guardiansofadelia.menu.main.GuiSettings;
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

public class GuiLanguage extends GuiGeneric {

    public GuiLanguage(GuardianData guardianData) {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK + Translation.t(guardianData, "menu.language.name"), 0);

        ItemStack english = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = english.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "English");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to change to en_us language.");
        itemMeta.setLore(lore);
        english.setItemMeta(itemMeta);

        ItemStack turkish = new ItemStack(Material.LIGHT_BLUE_WOOL);
        itemMeta = turkish.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Turkish");
        lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to change to tr_tr language.");
        itemMeta.setLore(lore);
        turkish.setItemMeta(itemMeta);

        GuiHelper.form27Small(this, new ItemStack[]{english, turkish}, "Settings");
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
            GuiSettings gui = new GuiSettings(guardianData);
            gui.openInventory(player);
        } else if (GuiHelper.get27SmallButtonIndex(0) == slot) {
            guardianData.setLanguage(player, "en_us");
        } else if (GuiHelper.get27SmallButtonIndex(1) == slot) {
            guardianData.setLanguage(player, "tr_tr");
        }
    }
}
