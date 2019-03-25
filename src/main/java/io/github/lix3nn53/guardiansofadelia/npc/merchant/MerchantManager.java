package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchantManager {

    private static final HashMap<Integer, MerchantMenu> npcIdToMerchant = new HashMap<>();

    public static boolean isMerchant(int npcId) {
        return npcIdToMerchant.containsKey(npcId);
    }

    public static MerchantMenu getMerchantMenu(int npcId) {
        return npcIdToMerchant.get(npcId);
    }

    public static void setMerchant(int npcId, MerchantMenu merchantMenu) {
        npcIdToMerchant.put(npcId, merchantMenu);
    }

    public static void init() {
        MerchantMenu blacksmith = new MerchantMenu("Blacksmith", true, true, true, 14);

        ItemStack enchant = new ItemStack(Material.DIAMOND);
        ItemMeta itemMeta = enchant.getItemMeta();
        itemMeta.setDisplayName("Enchant Items");
        List<String> lore = new ArrayList<>();
        lore.add("Strengthen your items with magical stones!");
        itemMeta.setLore(lore);
        enchant.setItemMeta(itemMeta);

        EnchantGui enchantGui = new EnchantGui();

        blacksmith.addGuiPage(enchant, enchantGui);


    }
}
