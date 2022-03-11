package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.GuiHelper;
import io.github.lix3nn53.guardiansofadelia.menu.main.settings.GuiLanguage;
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

public class GuiSettings extends GuiGeneric {

    public GuiSettings(GuardianData guardianData) {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK + Translation.t(guardianData, "menu.settings.name"), 0);

        ItemStack language = new ItemStack(Material.LIGHT_BLUE_WOOL);
        ItemMeta itemMeta = language.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Language");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Click to select server language.");
        itemMeta.setLore(lore);
        language.setItemMeta(itemMeta);

        GuiHelper.form27Small(this, new ItemStack[]{language}, "Main Menu");
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
        } else if (GuiHelper.get27SmallButtonIndex(0) == slot) {
            GuiLanguage gui = new GuiLanguage(guardianData);
            gui.openInventory(player);
        }
    }
}
