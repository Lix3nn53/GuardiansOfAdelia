package io.github.lix3nn53.guardiansofadelia.quests;

import io.github.lix3nn53.guardiansofadelia.utilities.advancements.Advancement;
import io.github.lix3nn53.guardiansofadelia.utilities.advancements.AdvancementDisplay;
import io.github.lix3nn53.guardiansofadelia.utilities.advancements.AdvancementVisibility;
import io.github.lix3nn53.guardiansofadelia.utilities.advancements.NameKey;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class QuestAdvancements {

    public static Advancement getOnCompleteAdvancement(int questID, String name, Material advencementMaterial) {
        AdvancementDisplay advancementDisplay = new AdvancementDisplay(advencementMaterial, ChatColor.LIGHT_PURPLE + "Quest Complete", name,
                AdvancementDisplay.AdvancementFrame.TASK, true, false, AdvancementVisibility.ALWAYS);
        return new Advancement(null, new NameKey("questComplete" + questID), advancementDisplay);
    }

    public static Advancement getOnTurnInAdvancement(int questID, String name, Material advencementMaterial) {
        AdvancementDisplay advancementDisplay = new AdvancementDisplay(advencementMaterial, ChatColor.LIGHT_PURPLE + "Quest Turn In", name,
                AdvancementDisplay.AdvancementFrame.TASK, true, false, AdvancementVisibility.ALWAYS);
        return new Advancement(null, new NameKey("questTurnIn" + questID), advancementDisplay);
    }

    public static Advancement getOnAcceptAdvancement(int questID, String name, Material advencementMaterial) {
        AdvancementDisplay advancementDisplay = new AdvancementDisplay(advencementMaterial, ChatColor.LIGHT_PURPLE + "Quest Accept", name,
                AdvancementDisplay.AdvancementFrame.TASK, true, false, AdvancementVisibility.ALWAYS);
        return new Advancement(null, new NameKey("questAccept" + questID), advancementDisplay);
    }
}
