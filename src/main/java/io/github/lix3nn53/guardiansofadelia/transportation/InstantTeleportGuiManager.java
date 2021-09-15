package io.github.lix3nn53.guardiansofadelia.transportation;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class InstantTeleportGuiManager {
    private static final HashMap<Integer, InstantTeleportGuiItem> questNoToTeleport = new HashMap<>();

    public static boolean contains(int questNo) {
        return questNoToTeleport.containsKey(questNo);
    }

    public static InstantTeleportGuiItem getTeleport(int questNo) {
        return questNoToTeleport.get(questNo);
    }

    public static void addTeleport(int questNo, InstantTeleportGuiItem instantTeleportGuiItem) {
        questNoToTeleport.put(questNo, instantTeleportGuiItem);
    }

    public static GuiBookGeneric getGuiBook(GuardianData guardianData) {
        GuiBookGeneric guiBookGeneric = new GuiBookGeneric(ChatPalette.PURPLE_LIGHT + "Instant Teleportation", 0);

        List<Integer> turnedInQuests = guardianData.getActiveCharacter().getTurnedInQuests();

        for (int questNo : questNoToTeleport.keySet()) {
            InstantTeleportGuiItem instantTeleportGuiItem = questNoToTeleport.get(questNo);
            boolean contains = turnedInQuests.contains(questNo);
            ItemStack itemStack = instantTeleportGuiItem.getItemStack(contains, questNo);

            guiBookGeneric.addToFirstAvailableWord(itemStack);
        }

        return guiBookGeneric;
    }
}
