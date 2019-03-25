package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.CharacterSelectionMenuList;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantMenu;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.SkillAPIUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class MyNPCRightClickEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(NPCRightClickEvent event) {
        Player player = event.getClicker();
        UUID uuid = player.getUniqueId();

        NPC npc = event.getNPC();
        int id = npc.getId();

        if (id > 0 && id < 5) {
            //1-2-3-4 character selection npc
            if (SkillAPIUtils.hasValidData(player, id)) {
                GuiGeneric characterTeleportationMenu = CharacterSelectionMenuList.characterSelectionMenu(id);
                characterTeleportationMenu.openInventory(player);
            } else {
                GuiGeneric characterCreationMenu = CharacterSelectionMenuList.getCharacterCreationMenu(id);
                characterCreationMenu.openInventory(player);
            }
        } else {
            UUID uniqueId = player.getUniqueId();
            if (GuardianDataManager.hasGuardianData(uniqueId)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    //progress interact tasks
                    List<Quest> questList = activeCharacter.getQuestList();
                    for (Quest quest : questList) {
                        boolean didProgress = quest.progressInteractTasks(player, id);
                        //don't want to open Menu on interact for quest so we return
                        if (didProgress) return;
                    }
                }
            }

            if (MerchantManager.isMerchant(id)) {
                MerchantMenu merchantMenu = MerchantManager.getMerchantMenu(id);
                merchantMenu.openInventory(player);
            } else {
                GuiGeneric questGui = QuestNPCManager.getQuestGui(player, id);
                questGui.openInventory(player);
            }
        }
    }

}
