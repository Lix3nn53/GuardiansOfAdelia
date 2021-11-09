package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.items.Consumable;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.enchanting.EnchantGui;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringToolTier;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringToolType;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiPage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum MerchantPageType {
    SELL,
    CONVERT,
    PERSONAL_STORAGE,
    GUILD_STORAGE,
    BAZAAR_STORAGE,
    PREMIUM_STORAGE,
    WEAPON,
    ARMOR,
    SHIELD,
    ENCHANT,
    TOOL,
    UTILITY,
    POTIONS,
    TP_SCROLL;

    public Gui getGui(int resourceNpc, Player player, int shopLevel) {
        switch (this) {
            case SELL:
                return getSellGui(resourceNpc);
            case CONVERT:
                return getConvertGui(resourceNpc);
            case PERSONAL_STORAGE:
                return getPersonalStorageGui(player, resourceNpc);
            case GUILD_STORAGE:
                if (GuildManager.inGuild(player)) {
                    Guild guild = GuildManager.getGuild(player);
                    return getGuildStorageGui(guild);
                }
                return null;
            case BAZAAR_STORAGE:
                return getBazaarStorageGui(player, resourceNpc);
            case PREMIUM_STORAGE:
                return getPremiumStorageGui(player, resourceNpc);
            case WEAPON:
                return getWeaponShop(shopLevel, resourceNpc);
            case ARMOR:
                return getArmorShop(shopLevel, resourceNpc);
            case SHIELD:
                return getShieldShop(shopLevel, resourceNpc);
            case ENCHANT:
                return getEnchantGui(player, resourceNpc);
            /*case EGG:
                return getEggShop(shopLevel, resourceNpc);*/
            case TOOL:
                return getToolShop(shopLevel, resourceNpc);
            case UTILITY:
                return getUtilityShop(shopLevel, resourceNpc);
            case POTIONS:
                return getPotionShop(shopLevel, resourceNpc);
            case TP_SCROLL:
                return getTeleportScrollShop(shopLevel, resourceNpc);
        }
        return new GuiGeneric(9, "ERROR", resourceNpc);
    }

    private SellGui getSellGui(int shopNpc) {
        return new SellGui(shopNpc);
    }

    private GuiGeneric getConvertGui(int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatPalette.GOLD + "Coin Converter", shopNpc);

        ItemStack silverToBronze = new ItemStack(Material.IRON_INGOT, 64);
        ItemMeta itemMeta = silverToBronze.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BROWN + "Bronze Coin");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.BROWN + "64 Bronze = " + ChatPalette.GRAY + "1 Silver");
        lore.add(ChatPalette.GRAY + "64 Silver = " + ChatPalette.GOLD + "1 Gold");
        itemMeta.setLore(lore);
        silverToBronze.setItemMeta(itemMeta);
        guiGeneric.setItem(0, silverToBronze);

        ItemStack bronzeToSilver = new ItemStack(Material.GOLD_INGOT, 1);
        itemMeta.setDisplayName(ChatPalette.GRAY + "Silver Coin");
        bronzeToSilver.setItemMeta(itemMeta);
        guiGeneric.setItem(9, bronzeToSilver);

        ItemStack goldToSilver = new ItemStack(Material.GOLD_INGOT, 64);
        itemMeta.setDisplayName(ChatPalette.GRAY + "Silver Coin");
        goldToSilver.setItemMeta(itemMeta);
        guiGeneric.setItem(10, goldToSilver);

        ItemStack silverToGold = new ItemStack(Material.DIAMOND, 1);
        itemMeta.setDisplayName(ChatPalette.GOLD + "Gold Coin");
        silverToGold.setItemMeta(itemMeta);
        guiGeneric.setItem(18, silverToGold);

        return guiGeneric;
    }

    private GuiGeneric getPersonalStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Personal Storage", shopNpc);

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            return guardianData.getPersonalStorageGui();
        }
        return guiGeneric;
    }

    private GuiGeneric getGuildStorageGui(Guild guild) {
        return guild.getGuildStorageGui();
    }

    private GuiGeneric getBazaarStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Bazaar Storage", shopNpc);

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            return guardianData.getBazaarStorageGui();
        }
        return guiGeneric;
    }

    private GuiGeneric getPremiumStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Premium Storage", shopNpc);

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            return guardianData.getPremiumStorageGui();
        }
        return guiGeneric;
    }

    private GuiGeneric getEnchantGui(Player player, int shopNpc) {
        return new EnchantGui(player);
    }

    private GuiBookGeneric getWeaponShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());

        ItemTier tier = ItemTier.COMMON;
        int lineIndex = 0;

        lines.add(new MerchantGuiLine());

        for (WeaponGearType weaponGearType : WeaponGearType.values()) {
            for (int i = shopLevel; i < shopLevel + 2; i++) {
                GearLevel gearLevel = GearLevel.values()[i];

                List<ItemStack> itemStacks = WeaponManager.get(weaponGearType, gearLevel, tier, true, null);

                for (ItemStack itemStack : itemStacks) {
                    int sellValue = SellGui.getSellValue(itemStack);
                    int price = getPrice(sellValue);

                    if (!lines.get(lineIndex).isEmpty()) {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                    }
                    lines.get(lineIndex).addWord(itemStack, price);
                }
            }
        }

        List<GuiPage> guiPages = new ArrayList<>();
        guiPages.add(new GuiPage());
        int guiPageIndex = 0;

        for (MerchantGuiLine line : lines) {
            if (guiPages.get(guiPageIndex).isEmpty()) {
                guiPages.get(guiPageIndex).addLine(line);
            } else {
                guiPages.add(new GuiPage());
                guiPageIndex++;
            }
        }

        GuiBookGeneric merchantShop = new GuiBookGeneric("Weapon Shop", shopNpc);
        for (GuiPage page : guiPages) {
            merchantShop.addPage(page);
        }
        return merchantShop;
    }

    private GuiBookGeneric getArmorShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();

        ItemTier tier = ItemTier.COMMON;
        int lineIndex = 0;

        lines.add(new MerchantGuiLine());

        for (ArmorGearType armorGearType : ArmorGearType.values()) {
            for (int i = shopLevel; i < shopLevel + 2; i++) {
                GearLevel gearLevel = GearLevel.values()[i];

                for (ArmorSlot armorSlot : ArmorSlot.values()) {
                    List<ItemStack> itemStacks = ArmorManager.get(armorSlot, armorGearType, gearLevel, tier, true, null);

                    for (ItemStack itemStack : itemStacks) {
                        int sellValue = SellGui.getSellValue(itemStack);
                        int price = getPrice(sellValue);

                        if (!lines.get(lineIndex).isEmpty()) {
                            lines.add(new MerchantGuiLine());
                            lineIndex++;
                        }
                        lines.get(lineIndex).addWord(itemStack, price);
                    }
                }
            }
        }

        List<GuiPage> guiPages = new ArrayList<>();
        guiPages.add(new GuiPage());
        int guiPageIndex = 0;

        for (MerchantGuiLine line : lines) {
            if (guiPages.get(guiPageIndex).isEmpty()) {
                guiPages.get(guiPageIndex).addLine(line);
            } else {
                guiPages.add(new GuiPage());
                guiPageIndex++;
                guiPages.get(guiPageIndex).addLine(line);
            }
        }

        GuiBookGeneric merchantShop = new GuiBookGeneric("Armor Shop", shopNpc);
        for (GuiPage page : guiPages) {
            merchantShop.addPage(page);
        }
        return merchantShop;
    }

    private GuiBookGeneric getShieldShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());

        ItemTier tier = ItemTier.COMMON;
        int lineIndex = 0;

        lines.add(new MerchantGuiLine());

        for (ShieldGearType shieldGearType : ShieldGearType.values()) {
            for (int i = shopLevel; i < shopLevel + 2; i++) {
                GearLevel gearLevel = GearLevel.values()[i];

                List<ItemStack> itemStacks = ShieldManager.get(shieldGearType, gearLevel, tier, true, null);

                for (ItemStack itemStack : itemStacks) {
                    int sellValue = SellGui.getSellValue(itemStack);
                    int price = getPrice(sellValue);

                    if (!lines.get(lineIndex).isEmpty()) {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                    }
                    lines.get(lineIndex).addWord(itemStack, price);
                }
            }
        }
        List<GuiPage> guiPages = new ArrayList<>();
        guiPages.add(new GuiPage());
        int guiPageIndex = 0;

        for (MerchantGuiLine line : lines) {
            if (guiPages.get(guiPageIndex).isEmpty()) {
                guiPages.get(guiPageIndex).addLine(line);
            } else {
                guiPages.add(new GuiPage());
                guiPageIndex++;
            }
        }

        GuiBookGeneric merchantShop = new GuiBookGeneric("Shield Shop", shopNpc);
        for (GuiPage page : guiPages) {
            merchantShop.addPage(page);
        }
        return merchantShop;
    }

    private GuiBookGeneric getPotionShop(int shopLevel, int shopNpc) {
        GuiPage guiPage = new GuiPage();

        MerchantGuiLine line1 = new MerchantGuiLine();
        ItemStack hpPotion1 = Consumable.POTION_INSTANT_HEALTH.getItemStack(1, 10);
        line1.addWord(hpPotion1, 8);
        ItemStack manaPotion1 = Consumable.POTION_INSTANT_MANA.getItemStack(1, 10);
        line1.addWord(manaPotion1, 8);
        guiPage.addLine(line1);

        if (shopLevel >= 2) {
            MerchantGuiLine line = new MerchantGuiLine();
            ItemStack hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(2, 10);
            line.addWord(hpPotion, 16);
            ItemStack manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(2, 10);
            line.addWord(manaPotion, 16);
            guiPage.addLine(line);
            if (shopLevel >= 3) {
                line = new MerchantGuiLine();
                hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(3, 10);
                line.addWord(hpPotion, 32);
                manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(3, 10);
                line.addWord(manaPotion, 32);
                guiPage.addLine(line);
                if (shopLevel >= 4) {
                    line = new MerchantGuiLine();
                    hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(4, 10);
                    line.addWord(hpPotion, 48);
                    manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(4, 10);
                    line.addWord(manaPotion, 48);
                    guiPage.addLine(line);
                    if (shopLevel >= 5) {
                        line = new MerchantGuiLine();
                        hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(5, 10);
                        line.addWord(hpPotion, 60);
                        manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(5, 10);
                        line.addWord(manaPotion, 60);
                        guiPage.addLine(line);
                    }
                }
            }
        }

        GuiBookGeneric potion_shop = new GuiBookGeneric("Potion Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private GuiBookGeneric getTeleportScrollShop(int shopLevel, int shopNpc) {
        MerchantGuiLine line1 = new MerchantGuiLine();

        // TODO create scrolls for npc shop
        /*line1.addWord(TeleportScroll.ROUMEN.getScroll(1, 1), 4);

        line1.addWord(TeleportScroll.PORT_VELOA.getScroll(1, 5), 4);

        if (shopLevel >= 2) {
            line1.addWord(TeleportScroll.ELDERINE.getScroll(1, 20), 6);
            if (shopLevel >= 3) {
                line1.addWord(TeleportScroll.URUGA.getScroll(1, 40), 9);
                if (shopLevel >= 4) {
                    line1.addWord(TeleportScroll.ALBERSTOL_RUINS.getScroll(1, 70), 14);
                }
            }
        }*/

        GuiPage guiPage = new GuiPage();
        guiPage.addLine(line1);

        GuiBookGeneric potion_shop = new GuiBookGeneric("Teleport-Scroll Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private GuiBookGeneric getUtilityShop(int shopLevel, int shopNpc) {
        MerchantGuiLine line1 = new MerchantGuiLine();

        ItemStack boat = OtherItems.getBoat();
        line1.addWord(boat, 1);

        GuiPage guiPage = new GuiPage();
        guiPage.addLine(line1);

        GuiBookGeneric potion_shop = new GuiBookGeneric("Utility Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private GuiBookGeneric getToolShop(int shopLevel, int shopNpc) {
        MerchantGuiLine line1 = new MerchantGuiLine();

        ItemStack axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.WOODEN);
        ItemStack hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.WOODEN);
        ItemStack pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.WOODEN);
        ItemStack fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.WOODEN);
        ItemStack bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.WOODEN);
        int price = 10;

        if (shopLevel == 2) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.STONE);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.STONE);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.STONE);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.STONE);
            bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.STONE);
            price = 15;
        } else if (shopLevel == 3) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.IRON);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.IRON);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.IRON);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.IRON);
            bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.IRON);
            price = 20;
        } else if (shopLevel == 4) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.GOLDEN);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.GOLDEN);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.GOLDEN);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.GOLDEN);
            bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.GOLDEN);
            price = 30;
        } else if (shopLevel == 5) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.DIAMOND);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.DIAMOND);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.DIAMOND);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.DIAMOND);
            bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.DIAMOND);
            price = 50;
        }

        line1.addWord(axe, price);
        line1.addWord(hoe, price);
        line1.addWord(pickaxe, price);
        line1.addWord(fishingRod, price);
        line1.addWord(bottle, price);

        GuiPage guiPage = new GuiPage();
        guiPage.addLine(line1);

        GuiBookGeneric potion_shop = new GuiBookGeneric("Tool Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private int getPrice(int sellPrice) {
        return (int) (sellPrice * 1.6 + 0.5);
    }
}
