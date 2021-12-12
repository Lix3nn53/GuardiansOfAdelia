package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class GuiHelper {

    private static final List<Integer> Big8Button0 = Arrays.asList(18, 19, 9, 10);
    private static final List<Integer> Big8Button1 = Arrays.asList(20, 21, 11, 12);
    private static final List<Integer> Big8Button2 = Arrays.asList(23, 24, 14, 15);
    private static final List<Integer> Big8Button3 = Arrays.asList(25, 26, 16, 17);
    private static final List<Integer> Big8Button4 = Arrays.asList(45, 46, 36, 37);
    private static final List<Integer> Big8Button5 = Arrays.asList(47, 48, 38, 39);
    private static final List<Integer> Big8Button6 = Arrays.asList(50, 51, 41, 42);
    private static final List<Integer> Big8Button7 = Arrays.asList(52, 53, 43, 44);

    public static void formBig8ButtonGui(GuiGeneric guiGeneric, ItemStack[] buttons, String backTo) {
        if (backTo != null) {
            ItemStack backButton = OtherItems.getBackButton(backTo);
            guiGeneric.setItem(0, backButton);
        }

        int i = 0;
        for (ItemStack button : buttons) {
            if (i > 7) break;
            if (button != null) {
                addBig8Button(guiGeneric, button, getBig8ButtonGuiIndexes(i));
                i++;
            }
        }
    }

    private static void addBig8Button(GuiGeneric guiGeneric, ItemStack button, List<Integer> indexes) {
        ItemMeta itemMeta = button.getItemMeta();
        String displayName = itemMeta.getDisplayName();
        List<String> lore = itemMeta.getLore();
        guiGeneric.setItem(indexes.get(0), button);

        ItemStack empty = button;
        if (button.getItemMeta().hasCustomModelData()) {
            empty = OtherItems.getEmpty(displayName, lore);
        }

        for (int i = 1; i < indexes.size(); i++) {
            guiGeneric.setItem(indexes.get(i), empty);
        }
    }

    public static List<Integer> getBig8ButtonGuiIndexes(int itemIndex) {
        if (itemIndex == 0) {
            return Big8Button0;
        } else if (itemIndex == 1) {
            return Big8Button1;
        } else if (itemIndex == 2) {
            return Big8Button2;
        } else if (itemIndex == 3) {
            return Big8Button3;
        } else if (itemIndex == 4) {
            return Big8Button4;
        } else if (itemIndex == 5) {
            return Big8Button5;
        } else if (itemIndex == 6) {
            return Big8Button6;
        } else if (itemIndex == 7) {
            return Big8Button7;
        }

        return null;
    }
}
