package io.github.lix3nn53.guardiansofadelia.menu.main.character;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class GuiCharacterClassChange extends GuiGeneric {

    private final HashMap<Integer, String> slotToRpgClassStr = new HashMap<>();

    public GuiCharacterClassChange(Player player, GuardianData guardianData, int classTier) {
        super(27, ChatPalette.GRAY_DARK + "Class Change", 0);

        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

        int highestUnlockedClassTier = rpgCharacter.getHighestUnlockedClassTier(player);

        List<RPGClass> values = RPGClassManager.getClassesAtTier(classTier);

        int[] slots = {0, 2, 4, 6, 8, 18, 20, 22, 24, 26};
        for (int i = 0; i < values.size(); i++) {
            RPGClass value = values.get(i);

            int slot = slots[i];

            slotToRpgClassStr.put(slot, value.getClassStringNoColor());

            ItemStack itemStack = RPGClassManager.getPersonalIcon(value, highestUnlockedClassTier, rpgCharacter);

            this.setItem(slot, itemStack);
        }
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

        if (slotToRpgClassStr.containsKey(slot)) {
            String rpgClassStr = slotToRpgClassStr.get(slot);

            boolean b = rpgCharacter.changeClass(player, rpgClassStr);
            if (b) player.closeInventory();
        }
    }
}
