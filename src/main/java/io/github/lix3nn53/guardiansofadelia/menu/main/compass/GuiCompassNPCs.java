package io.github.lix3nn53.guardiansofadelia.menu.main.compass;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.menu.main.GuiCompass;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiCompassNPCs extends GuiBookGeneric {

    public GuiCompassNPCs() {
        super(CustomCharacterGui.MENU_54_FLAT.toString() + ChatPalette.BLACK + "Compass NPCs", 0);

        ItemStack backButton = OtherItems.getBackButton("Compass Menu");
        this.addToFirstAvailableWord(backButton);
        this.disableLine(0, 0);

        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        for (int i = 120; i < 1000; i++) {
            NPC byId = npcRegistry.getById(i);

            if (byId == null) break;

            ItemStack itemStack = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to select your compass target!");
            lore.add("");
            lore.add(ChatPalette.GRAY + "If you dont have a compass this will give you one.");
            itemMeta.setLore(lore);

            itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + byId.getName() + " #" + i);
            itemStack.setItemMeta(itemMeta);

            this.addToFirstAvailableWord(itemStack);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getSlot() == 0) {
            GuardianData guardianData;
            if (GuardianDataManager.hasGuardianData(player)) {
                guardianData = GuardianDataManager.getGuardianData(player);
            } else {
                return;
            }

            GuiCompass gui = new GuiCompass(guardianData);
            gui.openInventory(player);
        } else {
            ItemStack item = event.getCurrentItem();
            ItemMeta itemMeta = item.getItemMeta();

            String displayName = itemMeta.getDisplayName();
            String[] split = displayName.split("#");
            int i = Integer.parseInt(split[1]);
            CompassManager.setCompassItemNPC(player, i);
            player.closeInventory();
        }
    }
}
