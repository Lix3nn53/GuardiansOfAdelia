package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CompassManager {

    public static void setCompassItemNPC(Player player, int npcNo) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        NPC npc = npcRegistry.getById(npcNo);

        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE + "Compass " + ChatPalette.WHITE + "( " + npc.getName() + ChatPalette.WHITE + " )");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Right click while holding to select compass target");
        lore.add(ChatPalette.GRAY_DARK + "NPC: #" + npcNo);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.COMPASS, 1);

        player.getInventory().addItem(itemStack);
        player.setCompassTarget(npc.getStoredLocation());
        player.sendMessage(ChatPalette.BLUE + "New compass target: " + ChatPalette.WHITE + npc.getName());
    }

    public static void setCompassItemLocation(Player player, String locationName, Location location) {
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BLUE + "Compass " + ChatPalette.WHITE + "( " + locationName + ChatPalette.WHITE + " )");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Right click while holding this item");
        lore.add(ChatPalette.GRAY + "to change compass target.");
        lore.add("");
        lore.add(ChatPalette.GRAY_DARK + "Target: " + locationName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.COMPASS, 1);

        player.getInventory().addItem(itemStack);
        player.setCompassTarget(location);
        player.sendMessage(ChatPalette.BLUE + "New compass target: " + ChatPalette.WHITE + locationName);
    }
}
