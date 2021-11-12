package io.github.lix3nn53.guardiansofadelia.menu.main.compass;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiCompassNPCs extends GuiBookGeneric {

    public GuiCompassNPCs() {
        super(ChatPalette.GRAY_DARK + "Compass NPCs", 0);

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
    public void onClick(Player player, GuardianData guardianData, String title, int slot) {
        int pageIndex = this.getPageIndex(title);
        GuiGeneric pageInventory = this.getPageInventory(pageIndex);

        ItemStack item = pageInventory.getItem(slot);
        ItemMeta itemMeta = item.getItemMeta();

        String displayName = itemMeta.getDisplayName();
        String[] split = displayName.split("#");
        int i = Integer.parseInt(split[1]);
        CompassManager.setCompassItemNPC(player, i);
        player.closeInventory();
    }
}
