package io.github.lix3nn53.guardiansofadelia.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NPCAvatar {
    private final static HashMap<Integer, Integer> npcNoToCustomModelData = new HashMap<>();
    private final static HashMap<Integer, List<String>> npcNoToDescription = new HashMap<>();

    public static ItemStack getAvatar(int npcId) {
        int customModelData = 1;
        if (npcNoToCustomModelData.containsKey(npcId)) customModelData = npcNoToCustomModelData.get(npcId);

        List<String> lore = new ArrayList<>();
        lore.add("NPC description");

        if (npcNoToDescription.containsKey(npcId)) lore = npcNoToDescription.get(npcId);

        ItemStack itemStack = new ItemStack(Material.STONE_SHOVEL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(customModelData);

        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        NPC byId = npcRegistry.getById(npcId);
        itemMeta.setDisplayName(byId.getFullName());

        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
