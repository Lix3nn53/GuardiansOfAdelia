package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchantMenu extends GuiGeneric {

    private final int merchantLevel;
    private final HashMap<ItemStack, MerchantPageType> itemToButton = new HashMap<>();

    public MerchantMenu(MerchantType merchantType, int merchantLevel, int npcNo) {
        super(27, merchantType.getName() + " #" + merchantLevel, npcNo);
        this.merchantLevel = merchantLevel;

        int nextSlotNo = 0;
        if (merchantType.canSell()) {
            ItemStack sellItem = new ItemStack(Material.GOLD_BLOCK);
            ItemMeta statusItemMeta = sellItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatPalette.GOLD + "Sell Items");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.YELLOW + "Sell your item for coins");
            statusItemMeta.setLore(lore);
            sellItem.setItemMeta(statusItemMeta);
            setItem(nextSlotNo, sellItem, MerchantPageType.SELL);
            nextSlotNo += 2;
        }
        if (merchantType.canCoinConvert()) {
            ItemStack coinConvertItem = new ItemStack(Material.DIAMOND);
            ItemMeta statusItemMeta = coinConvertItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatPalette.YELLOW + "Convert coins");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.BROWN + "64 Bronze = " + ChatPalette.GRAY + "1 Silver");
            lore.add(ChatPalette.GRAY + "64 Silver = " + ChatPalette.GOLD + "1 Gold");
            statusItemMeta.setLore(lore);
            coinConvertItem.setItemMeta(statusItemMeta);
            setItem(nextSlotNo, coinConvertItem, MerchantPageType.CONVERT);
        }

        if (merchantType.equals(MerchantType.WEAPONSMITH)) {
            ItemStack weapons = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName("Weapons");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("Buy weapons");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.WEAPON);

            ItemStack enchant = new ItemStack(Material.DIAMOND_BLOCK);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName("Enchant Items");
            lore = new ArrayList<>();
            lore.add("");
            lore.add("Strengthen your items with magical stones!");
            itemMeta.setLore(lore);
            enchant.setItemMeta(itemMeta);
            setItem(20, enchant, MerchantPageType.ENCHANT);
        } else if (merchantType.equals(MerchantType.ARMORSMITH)) {
            ItemStack armors = new ItemStack(Material.NETHERITE_CHESTPLATE);
            ItemMeta itemMeta = armors.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName("Armors");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("Buy armors");
            itemMeta.setLore(lore);
            armors.setItemMeta(itemMeta);
            setItem(18, armors, MerchantPageType.ARMOR);

            ItemStack shields = new ItemStack(Material.SHIELD);
            itemMeta.setDisplayName("Shields");
            lore = new ArrayList<>();
            lore.add("");
            lore.add("Buy shields");
            itemMeta.setLore(lore);
            shields.setItemMeta(itemMeta);
            setItem(20, shields, MerchantPageType.SHIELD);

            ItemStack enchant = new ItemStack(Material.DIAMOND_BLOCK);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName("Enchant Items");
            lore = new ArrayList<>();
            lore.add("");
            lore.add("Strengthen your items with magical stones!");
            itemMeta.setLore(lore);
            enchant.setItemMeta(itemMeta);
            setItem(22, enchant, MerchantPageType.ENCHANT);
        } else if (merchantType.equals(MerchantType.STORAGE_KEEPER)) {
            ItemStack personal = new ItemStack(Material.CHEST);
            ItemMeta itemMeta = personal.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName("Personal Storage");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("Deposit or withdraw items in your personal storage");
            itemMeta.setLore(lore);
            personal.setItemMeta(itemMeta);
            setItem(18, personal, MerchantPageType.PERSONAL_STORAGE);

            ItemStack guild = new ItemStack(Material.ENDER_CHEST);
            itemMeta.setDisplayName("Guild Storage");
            lore = new ArrayList<>();
            lore.add("");
            lore.add("Deposit or withdraw items in your guild's storage");
            itemMeta.setLore(lore);
            guild.setItemMeta(itemMeta);
            setItem(20, guild, MerchantPageType.GUILD_STORAGE);

            ItemStack bazaar = new ItemStack(Material.CHEST);
            itemMeta.setDisplayName("Bazaar Storage");
            lore = new ArrayList<>();
            lore.add("");
            lore.add("Claim your earnings from selling items on bazaar.");
            lore.add("");
            lore.add("Your items on sale are stored here for safety.");
            itemMeta.setLore(lore);
            bazaar.setItemMeta(itemMeta);
            setItem(22, bazaar, MerchantPageType.BAZAAR_STORAGE);

            ItemStack premium = new ItemStack(Material.ENDER_CHEST);
            itemMeta.setDisplayName("Premium Storage");
            lore = new ArrayList<>();
            lore.add("");
            lore.add("Claim items purchased from web store.");
            itemMeta.setLore(lore);
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
            lore.add("Buy potions");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.POTIONS);

            /*ItemStack armors = new ItemStack(Material.PAPER);
            itemMeta = armors.getItemMeta();
            itemMeta.setDisplayName("Teleport Scrolls");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.TP_SCROLL);*/
        } else if (merchantType.equals(MerchantType.TOOL_SHOP)) {
            ItemStack weapons = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.setDisplayName("Gathering Tools");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("Buy gathering tools");
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.TOOL);

            ItemStack armors = new ItemStack(Material.DARK_OAK_BOAT);
            itemMeta.setDisplayName("Utility");
            lore = new ArrayList<>();
            lore.add("");
            lore.add("Buy utility items");
            itemMeta.setLore(lore);
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

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);
        } else {
            return;
        }
        ItemStack current = this.getItem(event.getSlot());

        if (this.isButton(current)) {
            MerchantPageType buttonShop = this.getButtonShop(current);

            Gui gui = buttonShop.getGui(player, guardianData, this.getResourceNPC(), merchantLevel);
            if (gui != null) {
                gui.openInventory(player);
            }
        }
    }
}
