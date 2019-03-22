package io.github.lix3nn53.guardiansofadelia.utilities.managers;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.md_5.bungee.api.ChatColor;
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
        itemMeta.setDisplayName(ChatColor.GREEN + "Compass " + ChatColor.WHITE + "( " + npc.getName() + ChatColor.WHITE + " )");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Right click while holding to select compass target");
            add(ChatColor.DARK_GRAY + "NPC: #" + npcNo);
        }});
        itemStack.setItemMeta(itemMeta);
        InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.COMPASS, 1);

        player.getInventory().addItem(itemStack);
        player.setCompassTarget(npc.getStoredLocation());
        player.sendMessage(ChatColor.GREEN + "New compass target: " + ChatColor.WHITE + npc.getName());
    }

    public static void setCompassItemLocation(Player player, String locationName, Location location) {
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Compass " + ChatColor.WHITE + "( " + locationName + ChatColor.WHITE + " )");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "Right click while holding to select compass target");
            add(ChatColor.DARK_GRAY + "Location: #" + locationName);
        }});
        itemStack.setItemMeta(itemMeta);
        InventoryUtils.removeMaterialFromInventory(player.getInventory(), Material.COMPASS, 1);

        player.getInventory().addItem(itemStack);
        player.setCompassTarget(location);
        player.sendMessage(ChatColor.GREEN + "New compass target: " + ChatColor.WHITE + locationName);
    }
}
