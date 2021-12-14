package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
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

    private final MerchantType merchantType;
    private final int merchantLevel;
    private final HashMap<Integer, MerchantPageType> slotToButton = new HashMap<>();

    // state
    private String currentLang = Translation.DEFAULT_LANG;

    public MerchantMenu(MerchantType merchantType, int merchantLevel, int npcNo) {
        super(27, CustomCharacterGui.MENU_27_FLAT + merchantType.getName() + " #" + merchantLevel, npcNo);
        this.merchantType = merchantType;
        this.merchantLevel = merchantLevel;

        int nextSlotNo = 0;
        if (merchantType.canSell()) {
            ItemStack sellItem = new ItemStack(Material.GOLD_BLOCK);
            ItemMeta statusItemMeta = sellItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(currentLang, "economy.merchant.sell.name"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.YELLOW + Translation.t(currentLang, "economy.merchant.sell.l1"));
            statusItemMeta.setLore(lore);
            sellItem.setItemMeta(statusItemMeta);
            setItem(nextSlotNo, sellItem, MerchantPageType.SELL);
            nextSlotNo += 2;
        }
        if (merchantType.canCoinConvert()) {
            ItemStack coinConvertItem = new ItemStack(Material.DIAMOND);
            ItemMeta statusItemMeta = coinConvertItem.getItemMeta();
            statusItemMeta.setDisplayName(ChatPalette.YELLOW + Translation.t(currentLang, "economy.coin.convert"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.BROWN + "64 " + Translation.t(currentLang, "economy.coin.bronze") + " = " + ChatPalette.GRAY + "1 " + Translation.t(currentLang, "economy.coin.silver"));
            lore.add(ChatPalette.GRAY + "64 " + Translation.t(currentLang, "economy.coin.silver") + " = " + ChatPalette.GOLD + "1 " + Translation.t(currentLang, "economy.coin.gold"));
            statusItemMeta.setLore(lore);
            coinConvertItem.setItemMeta(statusItemMeta);
            setItem(nextSlotNo, coinConvertItem, MerchantPageType.CONVERT);
        }

        if (merchantType.equals(MerchantType.WEAPONSMITH)) {
            ItemStack weapons = new ItemStack(Material.NETHERITE_SWORD);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.weapons.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.weapons.l1"));
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);
            setItem(18, weapons, MerchantPageType.WEAPON);

            ItemStack enchant = new ItemStack(Material.DIAMOND_BLOCK);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.enchant.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.enchant.l1"));
            itemMeta.setLore(lore);
            enchant.setItemMeta(itemMeta);
            setItem(20, enchant, MerchantPageType.ENCHANT);
        } else if (merchantType.equals(MerchantType.ARMORSMITH)) {
            ItemStack armors = new ItemStack(Material.NETHERITE_CHESTPLATE);
            ItemMeta itemMeta = armors.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.armors.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.armors.l1"));
            itemMeta.setLore(lore);
            armors.setItemMeta(itemMeta);
            setItem(18, armors, MerchantPageType.ARMOR);

            ItemStack shields = new ItemStack(Material.SHIELD);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.shields.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.shields.l1"));
            itemMeta.setLore(lore);
            shields.setItemMeta(itemMeta);
            setItem(20, shields, MerchantPageType.SHIELD);

            ItemStack enchant = new ItemStack(Material.DIAMOND_BLOCK);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.enchant.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.enchant.l1"));
            itemMeta.setLore(lore);
            enchant.setItemMeta(itemMeta);
            setItem(22, enchant, MerchantPageType.ENCHANT);
        } else if (merchantType.equals(MerchantType.STORAGE_KEEPER)) {
            ItemStack personal = new ItemStack(Material.CHEST);
            ItemMeta itemMeta = personal.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.storage.personal.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.storage.personal.l1"));
            itemMeta.setLore(lore);
            personal.setItemMeta(itemMeta);
            setItem(18, personal, MerchantPageType.PERSONAL_STORAGE);

            ItemStack guild = new ItemStack(Material.ENDER_CHEST);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.storage.guild.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.storage.guild.l1"));
            itemMeta.setLore(lore);
            guild.setItemMeta(itemMeta);
            setItem(20, guild, MerchantPageType.GUILD_STORAGE);

            ItemStack bazaar = new ItemStack(Material.CHEST);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.storage.bazaar.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.storage.bazaar.l1"));
            lore.add(Translation.t(currentLang, "economy.merchant.storage.bazaar.l2"));
            itemMeta.setLore(lore);
            bazaar.setItemMeta(itemMeta);
            setItem(22, bazaar, MerchantPageType.BAZAAR_STORAGE);

            ItemStack premium = new ItemStack(Material.ENDER_CHEST);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.storage.premium.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.storage.premium.l1"));
            itemMeta.setLore(lore);
            premium.setItemMeta(itemMeta);
            setItem(24, premium, MerchantPageType.PREMIUM_STORAGE);
        } else if (merchantType.equals(MerchantType.MAGIC_SHOP)) {
            ItemStack potions = new ItemStack(Material.POTION);
            ItemMeta itemMeta = potions.getItemMeta();
            if (potions.getType().equals(Material.POTION)) {
                PotionMeta potionMeta = (PotionMeta) itemMeta;
                potionMeta.setColor(Color.RED);
            }
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.potions.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.potions.l1"));
            itemMeta.setLore(lore);
            potions.setItemMeta(itemMeta);
            setItem(18, potions, MerchantPageType.POTIONS);

            /*ItemStack armors = new ItemStack(Material.PAPER);
            itemMeta = armors.getItemMeta();
            itemMeta.setDisplayName("Teleport Scrolls");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.TP_SCROLL);*/
        } else if (merchantType.equals(MerchantType.TOOL_SHOP)) {
            ItemStack tools = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta itemMeta = tools.getItemMeta();
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.tools.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.tools.l1"));
            itemMeta.setLore(lore);
            tools.setItemMeta(itemMeta);
            setItem(18, tools, MerchantPageType.TOOL);

            ItemStack utility = new ItemStack(Material.DARK_OAK_BOAT);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.utility.l1"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.utility.l1"));
            itemMeta.setLore(lore);
            utility.setItemMeta(itemMeta);
            setItem(20, utility, MerchantPageType.UTILITY);
        }
    }

    public void translate(GuardianData guardianData) {
        String playerLang = guardianData.getLanguage();
        if (playerLang.equals(currentLang)) return;

        currentLang = playerLang;

        int nextSlotNo = 0;
        if (merchantType.canSell()) {
            ItemStack item = getItem(nextSlotNo);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(currentLang, "economy.merchant.sell.name"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.YELLOW + Translation.t(currentLang, "economy.merchant.sell.l1"));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            nextSlotNo += 2;
        }
        if (merchantType.canCoinConvert()) {
            ItemStack item = getItem(nextSlotNo);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(currentLang, "economy.coin.convert"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.BROWN + "64 " + Translation.t(currentLang, "economy.coin.bronze") + " = " + ChatPalette.GRAY + "1 " + Translation.t(currentLang, "economy.coin.silver"));
            lore.add(ChatPalette.GRAY + "64 " + Translation.t(currentLang, "economy.coin.silver") + " = " + ChatPalette.GOLD + "1 " + Translation.t(currentLang, "economy.coin.gold"));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        }

        if (merchantType.equals(MerchantType.WEAPONSMITH)) {
            ItemStack weapons = getItem(18);
            ItemMeta itemMeta = weapons.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.weapons.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.weapons.l1"));
            itemMeta.setLore(lore);
            weapons.setItemMeta(itemMeta);

            ItemStack enchant = getItem(20);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.enchant.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.enchant.l1"));
            itemMeta.setLore(lore);
            enchant.setItemMeta(itemMeta);
        } else if (merchantType.equals(MerchantType.ARMORSMITH)) {
            ItemStack armors = getItem(18);
            ItemMeta itemMeta = armors.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.armors.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.armors.l1"));
            itemMeta.setLore(lore);
            armors.setItemMeta(itemMeta);

            ItemStack shields = getItem(20);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.shields.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.shields.l1"));
            itemMeta.setLore(lore);
            shields.setItemMeta(itemMeta);

            ItemStack enchant = getItem(22);
            itemMeta = enchant.getItemMeta();
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.enchant.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.enchant.l1"));
            itemMeta.setLore(lore);
            enchant.setItemMeta(itemMeta);
        } else if (merchantType.equals(MerchantType.STORAGE_KEEPER)) {
            ItemStack personal = getItem(18);
            ItemMeta itemMeta = personal.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.storage.personal.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.storage.personal.l1"));
            itemMeta.setLore(lore);
            personal.setItemMeta(itemMeta);

            ItemStack guild = getItem(20);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.storage.guild.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.storage.guild.l1"));
            itemMeta.setLore(lore);
            guild.setItemMeta(itemMeta);

            ItemStack bazaar = getItem(22);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.storage.bazaar.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.storage.bazaar.l1"));
            lore.add(Translation.t(currentLang, "economy.merchant.storage.bazaar.l2"));
            itemMeta.setLore(lore);
            bazaar.setItemMeta(itemMeta);

            ItemStack premium = getItem(24);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.storage.premium.name"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.storage.premium.l1"));
            itemMeta.setLore(lore);
            premium.setItemMeta(itemMeta);
        } else if (merchantType.equals(MerchantType.MAGIC_SHOP)) {
            ItemStack potions = getItem(18);
            ItemMeta itemMeta = potions.getItemMeta();
            if (potions.getType().equals(Material.POTION)) {
                PotionMeta potionMeta = (PotionMeta) itemMeta;
                potionMeta.setColor(Color.RED);
            }
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.potions.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.potions.l1"));
            itemMeta.setLore(lore);
            potions.setItemMeta(itemMeta);

            /*ItemStack armors = new ItemStack(Material.PAPER);
            itemMeta = armors.getItemMeta();
            itemMeta.setDisplayName("Teleport Scrolls");
            List<String> lore2 = new ArrayList<>();
            lore2.add("");
            itemMeta.setLore(lore2);
            armors.setItemMeta(itemMeta);
            setItem(20, armors, MerchantPageType.TP_SCROLL);*/
        } else if (merchantType.equals(MerchantType.TOOL_SHOP)) {
            ItemStack tools = getItem(18);
            ItemMeta itemMeta = tools.getItemMeta();
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.tools.name"));
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.tools.l1"));
            itemMeta.setLore(lore);
            tools.setItemMeta(itemMeta);

            ItemStack utility = getItem(20);
            itemMeta.setDisplayName(Translation.t(currentLang, "economy.merchant.utility.l1"));
            lore = new ArrayList<>();
            lore.add("");
            lore.add(Translation.t(currentLang, "economy.merchant.utility.l1"));
            itemMeta.setLore(lore);
            utility.setItemMeta(itemMeta);
        }
    }

    public void setItem(int index, ItemStack item, MerchantPageType button) {
        super.setItem(index, item);
        this.slotToButton.put(index, button);
    }

    public boolean isButton(int slot) {
        return this.slotToButton.containsKey(slot);
    }

    public MerchantPageType getButtonShop(int slot) {
        return this.slotToButton.get(slot);
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
        int slot = event.getSlot();

        if (this.isButton(slot)) {
            MerchantPageType buttonShop = this.getButtonShop(slot);

            Gui gui = buttonShop.getGui(player, guardianData, this.getResourceNPC(), merchantLevel);
            if (gui != null) {
                gui.openInventory(player);
            }
        }
    }
}
