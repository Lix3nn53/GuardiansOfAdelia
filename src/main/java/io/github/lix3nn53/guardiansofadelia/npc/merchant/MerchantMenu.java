package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchantMenu extends GuiGeneric {

    private final HashMap<ItemStack, MerchantPageType> itemToButton = new HashMap<>();

    public MerchantMenu(MerchantType merchantType, int merchantLevel, int npcNo) {
        super(27, merchantType.getName() + " #" + merchantLevel, npcNo);

        int nextSlotNo = 0;
        if (merchantType.canSell()) {
            ItemStack sellItem = new ItemStack(Material.ORANGE_WOOL);
            ItemMeta statusItemMeta = sellItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatColor.GOLD + "Sell Items");
            statusItemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.YELLOW + "Sell your item for coins");
            }});
            sellItem.setItemMeta(statusItemMeta);
            setItem(nextSlotNo, sellItem, MerchantPageType.SELL);
            nextSlotNo += 2;
        }
        if (merchantType.canCoinConvert()) {
            ItemStack coinConvertItem = new ItemStack(Material.YELLOW_WOOL);
            ItemMeta statusItemMeta = coinConvertItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatColor.YELLOW + "Convert coins");
            statusItemMeta.setLore(new ArrayList() {{
                add("");
                add(ChatColor.GREEN + "64 Bronze = " + ChatColor.WHITE + "1 Silver");
                add(ChatColor.WHITE + "64 Silver = " + ChatColor.YELLOW + "1 Gold");
            }});
            coinConvertItem.setItemMeta(statusItemMeta);
            setItem(nextSlotNo, coinConvertItem, MerchantPageType.CONVERT);
        }

        if (merchantType.equals(MerchantType.BLACKSMITH)) {
            ItemStack weapons = new ItemStack(Material.RED_WOOL);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.setDisplayName("Weapons");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.WEAPON);

            ItemStack armors = new ItemStack(Material.LIGHT_BLUE_WOOL);
            itemMeta.setDisplayName("Armors");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.ARMOR);

            ItemStack shields = new ItemStack(Material.CYAN_WOOL);
            itemMeta.setDisplayName("Shields");
            List<String> lore3 = new ArrayList<>();
            lore3.add("");
            itemMeta.setLore(lore3);
            shields.setItemMeta(itemMeta);
            setItem(22, shields, MerchantPageType.SHIELD);

            ItemStack enchant = new ItemStack(Material.DIAMOND_BLOCK);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName("Enchant Items");
            List<String> lore1 = new ArrayList<>();
            lore1.add("Strengthen your items with magical stones!");
            itemMeta.setLore(lore1);
            enchant.setItemMeta(itemMeta);
            setItem(24, enchant, MerchantPageType.ENCHANT);
        } else if (merchantType.equals(MerchantType.STORAGE_KEEPER)) {
            ItemStack personal = new ItemStack(Material.YELLOW_WOOL);
            ItemMeta itemMeta = personal.getItemMeta();
            itemMeta.setDisplayName("Personal Storage");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            personal.setItemMeta(itemMeta);
            setItem(18, personal, MerchantPageType.PERSONAL_STORAGE);

            ItemStack guild = new ItemStack(Material.PURPLE_WOOL);
            itemMeta.setDisplayName("Guild Storage");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            guild.setItemMeta(itemMeta);
            setItem(20, guild, MerchantPageType.GUILD_STORAGE);

            ItemStack bazaar = new ItemStack(Material.LIGHT_BLUE_WOOL);
            itemMeta.setDisplayName("Bazaar Storage");
            List<String> lore3 = new ArrayList<>();
            lore3.add("");
            itemMeta.setLore(lore3);
            bazaar.setItemMeta(itemMeta);
            setItem(22, bazaar, MerchantPageType.BAZAAR_STORAGE);

            ItemStack premium = new ItemStack(Material.RED_WOOL);
            itemMeta.setDisplayName("Premium Storage");
            List<String> lore4 = new ArrayList<>();
            lore4.add("");
            itemMeta.setLore(lore4);
            premium.setItemMeta(itemMeta);
            setItem(24, premium, MerchantPageType.PREMIUM_STORAGE);
        } else if (merchantType.equals(MerchantType.MAGIC_SHOP)) {
            ItemStack weapons = new ItemStack(Material.MAGENTA_WOOL);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.setDisplayName("Potions");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.POTIONS);

            ItemStack armors = new ItemStack(Material.LIGHT_BLUE_WOOL);
            itemMeta.setDisplayName("Teleport Scrolls");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.TP_SCROLL);
        } else if (merchantType.equals(MerchantType.TOOL_SHOP)) {
            ItemStack weapons = new ItemStack(Material.YELLOW_WOOL);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.setDisplayName("Tools");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.TOOL);

            ItemStack armors = new ItemStack(Material.MAGENTA_WOOL);
            itemMeta.setDisplayName("Utility");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.UTILITY);
        } else if (merchantType.equals(MerchantType.PET_SHOP)) {
            ItemStack weapons = new ItemStack(Material.YELLOW_WOOL);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.setDisplayName("Companions");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.COMPANION);

            ItemStack armors = new ItemStack(Material.PURPLE_WOOL);
            itemMeta.setDisplayName("Mounts");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.MOUNT);
        }
    }

    public void setItem(int index, ItemStack item, MerchantPageType button) {
        super.setItem(index, item);
        this.itemToButton.put(item, button);
    }

    public boolean isButton(ItemStack itemStack) {
        return this.itemToButton.containsKey(itemStack);
    }

    public MerchantPageType getButtonShop(ItemStack itemStack) {
        return this.itemToButton.get(itemStack);
    }
}
