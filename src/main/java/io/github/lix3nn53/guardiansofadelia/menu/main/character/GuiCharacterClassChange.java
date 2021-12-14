package io.github.lix3nn53.guardiansofadelia.menu.main.character;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.menu.GuiHelper;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiCharacterClassChange extends GuiGeneric {

    private final List<RPGClass> values;

    public GuiCharacterClassChange(Player player, GuardianData guardianData, int classTier) {
        super(54, CustomCharacterGui.MENU_54.toString() + ChatPalette.BLACK + Translation.t(guardianData, "character.class.change"), 0);

        ItemStack backButton = OtherItems.getBackButton("Character Menu");
        this.setItem(0, backButton);

        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

        int highestUnlockedClassTier = rpgCharacter.getHighestUnlockedClassTier(player);

        values = RPGClassManager.getClassesAtTier(classTier);

        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            RPGClass value = values.get(i);

            ItemStack itemStack = RPGClassManager.getPersonalIcon(value, highestUnlockedClassTier, rpgCharacter);

            items.add(itemStack);
        }

        ItemStack[] array = new ItemStack[items.size()];
        items.toArray(array); // fill the array

        GuiHelper.form54Big(this, array, "Class Manager");
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
            GuiCharacterClassManager gui = new GuiCharacterClassManager(guardianData);
            gui.openInventory(player);
        } else {
            String rpgClassStr = null;

            int size = values.size();
            if (size > 0 && GuiHelper.get54BigButtonIndexes(0).contains(slot)) {
                rpgClassStr = values.get(0).getClassStringNoColor();
            } else if (size > 1 && GuiHelper.get54BigButtonIndexes(1).contains(slot)) {
                rpgClassStr = values.get(1).getClassStringNoColor();
            } else if (size > 2 && GuiHelper.get54BigButtonIndexes(2).contains(slot)) {
                rpgClassStr = values.get(2).getClassStringNoColor();
            } else if (size > 3 && GuiHelper.get54BigButtonIndexes(3).contains(slot)) {
                rpgClassStr = values.get(3).getClassStringNoColor();
            } else if (size > 4 && GuiHelper.get54BigButtonIndexes(4).contains(slot)) {
                rpgClassStr = values.get(4).getClassStringNoColor();
            } else if (size > 5 && GuiHelper.get54BigButtonIndexes(5).contains(slot)) {
                rpgClassStr = values.get(5).getClassStringNoColor();
            } else if (size > 6 && GuiHelper.get54BigButtonIndexes(6).contains(slot)) {
                rpgClassStr = values.get(6).getClassStringNoColor();
            } else if (size > 7 && GuiHelper.get54BigButtonIndexes(7).contains(slot)) {
                rpgClassStr = values.get(7).getClassStringNoColor();
            }

            if (rpgClassStr == null) return;

            boolean b = rpgCharacter.changeClass(player, rpgClassStr, guardianData.getLanguage());
            if (b) player.closeInventory();
        }
    }
}
