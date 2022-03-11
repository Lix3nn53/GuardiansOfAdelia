package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
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
import io.github.lix3nn53.guardiansofadelia.menu.GuiGenericBuyBook;
import io.github.lix3nn53.guardiansofadelia.menu.merchant.GuiCoinConverter;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiPage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
    POTIONS;

    public Gui getGui(Player player, GuardianData guardianData, int resourceNpc, int shopLevel) {
        switch (this) {
            case SELL:
                return new SellGui(resourceNpc);
            case CONVERT:
                return new GuiCoinConverter(guardianData, resourceNpc);
            case PERSONAL_STORAGE:
                return guardianData.getPersonalStorageGui();
            case GUILD_STORAGE:
                if (GuildManager.inGuild(player)) {
                    Guild guild = GuildManager.getGuild(player);
                    return guild.getGuildStorageGui();
                }
                return null;
            case BAZAAR_STORAGE:
                return guardianData.getBazaarStorageGui();
            case PREMIUM_STORAGE:
                return guardianData.getPremiumStorageGui();
            case WEAPON:
                return getWeaponShop(shopLevel, resourceNpc);
            case ARMOR:
                return getArmorShop(shopLevel, resourceNpc);
            case SHIELD:
                return getShieldShop(shopLevel, resourceNpc);
            case ENCHANT:
                return new EnchantGui(player);
            case TOOL:
                return getToolShop(shopLevel, resourceNpc);
            case UTILITY:
                return getUtilityShop(shopLevel, resourceNpc);
            case POTIONS:
                return getPotionShop(shopLevel, resourceNpc);
        }
        return new GuiGeneric(9, "ERROR", resourceNpc);
    }

    private GuiGenericBuyBook getWeaponShop(int shopLevel, int shopNpc) {
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

        GuiGenericBuyBook merchantShop = new GuiGenericBuyBook("Weapon Shop", shopNpc);
        for (GuiPage page : guiPages) {
            merchantShop.addPage(page);
        }
        return merchantShop;
    }

    private GuiGenericBuyBook getArmorShop(int shopLevel, int shopNpc) {
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

        GuiGenericBuyBook merchantShop = new GuiGenericBuyBook("Armor Shop", shopNpc);
        for (GuiPage page : guiPages) {
            merchantShop.addPage(page);
        }
        return merchantShop;
    }

    private GuiGenericBuyBook getShieldShop(int shopLevel, int shopNpc) {
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

        GuiGenericBuyBook merchantShop = new GuiGenericBuyBook("Shield Shop", shopNpc);
        for (GuiPage page : guiPages) {
            merchantShop.addPage(page);
        }
        return merchantShop;
    }

    private GuiGenericBuyBook getPotionShop(int shopLevel, int shopNpc) {
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

        GuiGenericBuyBook potion_shop = new GuiGenericBuyBook("Potion Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private GuiGenericBuyBook getUtilityShop(int shopLevel, int shopNpc) {
        MerchantGuiLine line1 = new MerchantGuiLine();

        ItemStack boat = OtherItems.getBoat();
        line1.addWord(boat, 1);

        GuiPage guiPage = new GuiPage();
        guiPage.addLine(line1);

        GuiGenericBuyBook potion_shop = new GuiGenericBuyBook("Utility Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private GuiGenericBuyBook getToolShop(int shopLevel, int shopNpc) {
        MerchantGuiLine line1 = new MerchantGuiLine();

        ItemStack axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.WOODEN);
        ItemStack hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.WOODEN);
        ItemStack pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.WOODEN);
        ItemStack fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.WOODEN);
        ItemStack bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.WOODEN);
        int price = GatheringToolTier.WOODEN.getDurability();

        if (shopLevel == 2) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.STONE);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.STONE);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.STONE);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.STONE);
            bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.STONE);
            price = GatheringToolTier.STONE.getDurability();
        } else if (shopLevel == 3) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.IRON);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.IRON);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.IRON);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.IRON);
            bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.IRON);
            price = GatheringToolTier.IRON.getDurability();
        } else if (shopLevel == 4) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.GOLDEN);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.GOLDEN);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.GOLDEN);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.GOLDEN);
            bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.GOLDEN);
            price = GatheringToolTier.GOLDEN.getDurability();
        } else if (shopLevel == 5) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.DIAMOND);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.DIAMOND);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.DIAMOND);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.DIAMOND);
            bottle = GatheringToolType.BOTTLE.getItemStack(GatheringToolTier.DIAMOND);
            price = GatheringToolTier.DIAMOND.getDurability();
        }

        line1.addWord(axe, price);
        line1.addWord(hoe, price);
        line1.addWord(pickaxe, price);
        line1.addWord(fishingRod, price);
        line1.addWord(bottle, price);

        GuiPage guiPage = new GuiPage();
        guiPage.addLine(line1);

        GuiGenericBuyBook potion_shop = new GuiGenericBuyBook("Tool Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private int getPrice(int sellPrice) {
        return (int) (sellPrice * 1.6 + 0.5);
    }
}
