package io.github.lix3nn53.guardiansofadelia.quests;

import eu.endercentral.crazy_advancements.advancement.AdvancementDisplay;
import eu.endercentral.crazy_advancements.advancement.ToastNotification;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Material;

public class QuestAdvancements {

    public static ToastNotification getOnCompleteAdvancement(int questID, String name, Material advencementMaterial) {
        return new ToastNotification(advencementMaterial, ChatPalette.PURPLE_LIGHT + "Quest Complete: " + name, AdvancementDisplay.AdvancementFrame.CHALLENGE);
    }

    public static ToastNotification getOnTurnInAdvancement(int questID, String name, Material advencementMaterial) {
        return new ToastNotification(advencementMaterial, ChatPalette.PURPLE_LIGHT + "Quest Turn In: " + name, AdvancementDisplay.AdvancementFrame.CHALLENGE);
    }

    public static ToastNotification getOnAcceptAdvancement(int questID, String name, Material advencementMaterial) {
        return new ToastNotification(advencementMaterial, ChatPalette.PURPLE_LIGHT + "Quest Accept: " + name, AdvancementDisplay.AdvancementFrame.CHALLENGE);
    }
}
