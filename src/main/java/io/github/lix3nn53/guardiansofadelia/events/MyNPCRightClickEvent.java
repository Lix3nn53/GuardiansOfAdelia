package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.menu.CharacterSelectionMenuList;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantMenu;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;
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
            if (GuardiansOfAdelia.getCharLocationsForSelection().containsKey(uuid)) {
                HashMap<Integer, Location> integerLocationHashMap = GuardiansOfAdelia.getCharLocationsForSelection().get(uuid);
                if (integerLocationHashMap.containsKey(id)) {
                    GuiGeneric characterTeleportationMenu = CharacterSelectionMenuList.characterSelectionMenu(id);
                    characterTeleportationMenu.openInventory(player);
                } else {
                    GuiGeneric characterCreationMenu = CharacterSelectionMenuList.getCharacterCreationMenu(id);
                    characterCreationMenu.openInventory(player);
                }
            } else {
                GuiGeneric characterCreationMenu = CharacterSelectionMenuList.getCharacterCreationMenu(id);
                characterCreationMenu.openInventory(player);
            }
        } else {
            if (MerchantManager.isMerchant(id)) {
                MerchantMenu merchantMenu = MerchantManager.getMerchantMenu(id);
                GuiGeneric merchantMenuGui = merchantMenu.getMerchantMenuGui();
                merchantMenuGui.openInventory(player);
            } else {
                GuiGeneric questGui = QuestNPCManager.getQuestGui(player, id);
                questGui.openInventory(player);
            }
        }
    }

}
