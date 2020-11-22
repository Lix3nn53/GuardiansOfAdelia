package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchantMenu extends GuiGeneric {

    private final HashMap<ItemStack, MerchantPageType> itemToButton = new HashMap<>();

    public MerchantMenu(MerchantType merchantType, int merchantLevel, int npcNo) {
        super(27, merchantType.getName() + " #" + merchantLevel, npcNo);

        int nextSlotNo = 0;
        if (merchantType.canSell()) {
            ItemStack sellItem = new ItemStack(Material.GOLD_BLOCK);
            ItemMeta statusItemMeta = sellItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatColor.GOLD + "Sell Items");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.YELLOW + "Sell your item for coins");
            statusItemMeta.setLore(lore);
            sellItem.setItemMeta(statusItemMeta);
            setItem(nextSlotNo, sellItem, MerchantPageType.SELL);
            nextSlotNo += 2;
        }
        if (merchantType.canCoinConvert()) {
            ItemStack coinConvertItem = new ItemStack(Material.DIAMOND);
            ItemMeta statusItemMeta = coinConvertItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatColor.YELLOW + "Convert coins");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GREEN + "64 Bronze = " + ChatColor.WHITE + "1 Silver");
            lore.add(ChatColor.WHITE + "64 Silver = " + ChatColor.YELLOW + "1 Gold");
            statusItemMeta.setLore(lore);
            coinConvertItem.setItemMeta(statusItemMeta);
            setItem(nextSlotNo, coinConvertItem, MerchantPageType.CONVERT);
        }

        if (merchantType.equals(MerchantType.WEAPONSMITH)) {
            ItemStack weapons = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.setDisplayName("Weapons");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.WEAPON);

            ItemStack enchant = new ItemStack(Material.DIAMOND_BLOCK);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName("Enchant Items");
            List<String> lore1 = new ArrayList<>();
            lore1.add("Strengthen your items with magical stones!");
            itemMeta.setLore(lore1);
            enchant.setItemMeta(itemMeta);
            setItem(20, enchant, MerchantPageType.ENCHANT);
        } else if (merchantType.equals(MerchantType.ARMORSMITH)) {
            ItemStack armors = new ItemStack(Material.NETHERITE_CHESTPLATE);
            ItemMeta itemMeta = armors.getItemMeta();
            itemMeta.setDisplayName("Armors");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(18, armors, MerchantPageType.ARMOR);

            ItemStack shields = new ItemStack(Material.SHIELD);
            itemMeta.setDisplayName("Shields");
            List<String> lore3 = new ArrayList<>();
            lore3.add("");
            itemMeta.setLore(lore3);
            shields.setItemMeta(itemMeta);
            setItem(20, shields, MerchantPageType.SHIELD);

            ItemStack enchant = new ItemStack(Material.DIAMOND_BLOCK);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName("Enchant Items");
            List<String> lore1 = new ArrayList<>();
            lore1.add("Strengthen your items with magical stones!");
            itemMeta.setLore(lore1);
            enchant.setItemMeta(itemMeta);
            setItem(22, enchant, MerchantPageType.ENCHANT);
        } else if (merchantType.equals(MerchantType.STORAGE_KEEPER)) {
            ItemStack personal = new ItemStack(Material.CHEST);
            ItemMeta itemMeta = personal.getItemMeta();
            itemMeta.setDisplayName("Personal Storage");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            personal.setItemMeta(itemMeta);
            setItem(18, personal, MerchantPageType.PERSONAL_STORAGE);

            ItemStack guild = new ItemStack(Material.ENDER_CHEST);
            itemMeta.setDisplayName("Guild Storage");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            guild.setItemMeta(itemMeta);
            setItem(20, guild, MerchantPageType.GUILD_STORAGE);

            ItemStack bazaar = new ItemStack(Material.CHEST);
            itemMeta.setDisplayName("Bazaar Storage");
            List<String> lore3 = new ArrayList<>();
            lore3.add("");
            itemMeta.setLore(lore3);
            bazaar.setItemMeta(itemMeta);
            setItem(22, bazaar, MerchantPageType.BAZAAR_STORAGE);

            ItemStack premium = new ItemStack(Material.ENDER_CHEST);
            itemMeta.setDisplayName("Premium Storage");
            List<String> lore4 = new ArrayList<>();
            lore4.add("");
            itemMeta.setLore(lore4);
            premium.setItemMeta(itemMeta);
            setItem(24, premium, MerchantPageType.PREMIUM_STORAGE);
        } else if (merchantType.equals(MerchantType.MAGIC_SHOP)) {
            ItemStack weapons = new ItemStack(Material.POTION);
            ItemMeta itemMeta = weapons.getItemMeta();
            if (weapons.getType().equals(Material.POTION)) {
                PotionMeta potionMeta = (PotionMeta) itemMeta;
                potionMeta.setColor(Color.RED);
            }
            itemMeta.setDisplayName("Potions");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.POTIONS);

            ItemStack armors = new ItemStack(Material.PAPER);
            itemMeta = armors.getItemMeta();
            itemMeta.setDisplayName("Teleport Scrolls");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.TP_SCROLL);
        } else if (merchantType.equals(MerchantType.TOOL_SHOP)) {
            ItemStack weapons = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.setDisplayName("Tools");
            List<String> lore = new ArrayList<>();
            lore.add("");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.TOOL);

            ItemStack armors = new ItemStack(Material.DARK_OAK_BOAT);
            itemMeta.setDisplayName("Utility");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.UTILITY);
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
