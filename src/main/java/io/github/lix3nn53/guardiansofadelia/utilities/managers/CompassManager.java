package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.ChatColor;
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
        itemMeta.setDisplayName(ChatColor.BLUE + "Compass " + ChatColor.WHITE + "( " + npc.getName() + ChatColor.WHITE + " )");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Right click while holding to select compass target");
        lore.add(ChatColor.DARK_GRAY + "NPC: #" + npcNo);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.COMPASS, 1);

        player.getInventory().addItem(itemStack);
        player.setCompassTarget(npc.getStoredLocation());
        player.sendMessage(ChatColor.BLUE + "New compass target: " + ChatColor.WHITE + npc.getName());
    }

    public static void setCompassItemLocation(Player player, String locationName, Location location) {
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE + "Compass " + ChatColor.WHITE + "( " + locationName + ChatColor.WHITE + " )");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Right click while holding this item");
        lore.add(ChatColor.GRAY + "to change compass target.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "Target: " + locationName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.COMPASS, 1);

        player.getInventory().addItem(itemStack);
        player.setCompassTarget(location);
        player.sendMessage(ChatColor.BLUE + "New compass target: " + ChatColor.WHITE + locationName);
    }
}
