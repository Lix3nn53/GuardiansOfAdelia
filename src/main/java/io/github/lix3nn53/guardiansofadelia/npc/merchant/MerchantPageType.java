package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantGui;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.Items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringToolTier;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringToolType;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiPage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        GuiGeneric guiGeneric = new GuiGeneric(27, ChatColor.GOLD + "Coin Converter", shopNpc);

        ItemStack silverToBronze = new ItemStack(Material.IRON_INGOT, 64);
        ItemMeta itemMeta = silverToBronze.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Bronze Coin");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GREEN + "64 Bronze = " + ChatColor.WHITE + "1 Silver");
        lore.add(ChatColor.WHITE + "64 Silver = " + ChatColor.YELLOW + "1 Gold");
        itemMeta.setLore(lore);
        silverToBronze.setItemMeta(itemMeta);
        guiGeneric.setItem(0, silverToBronze);

        ItemStack bronzeToSilver = new ItemStack(Material.GOLD_INGOT, 1);
        itemMeta.setDisplayName(ChatColor.WHITE + "Silver Coin");
        bronzeToSilver.setItemMeta(itemMeta);
        guiGeneric.setItem(9, bronzeToSilver);

        ItemStack goldToSilver = new ItemStack(Material.GOLD_INGOT, 64);
        itemMeta.setDisplayName(ChatColor.WHITE + "Silver Coin");
        goldToSilver.setItemMeta(itemMeta);
        guiGeneric.setItem(10, goldToSilver);

        ItemStack silverToGold = new ItemStack(Material.DIAMOND, 1);
        itemMeta.setDisplayName(ChatColor.GOLD + "Gold Coin");
        silverToGold.setItemMeta(itemMeta);
        guiGeneric.setItem(18, silverToGold);

        return guiGeneric;
    }

    private GuiGeneric getPersonalStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Personal Storage", shopNpc);
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            return guardianData.getPersonalStorageGui();
        }
        return guiGeneric;
    }

    private GuiGeneric getGuildStorageGui(Guild guild) {
        return guild.getGuildStorageGui();
    }

    private GuiGeneric getBazaarStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Bazaar Storage", shopNpc);
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            return guardianData.getBazaarStorageGui();
        }
        return guiGeneric;
    }

    private GuiGeneric getPremiumStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Premium Storage", shopNpc);
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            return guardianData.getPremiumStorageGui();
        }
        return guiGeneric;
    }

    private GuiGeneric getEnchantGui(Player player, int shopNpc) {
        return new EnchantGui(player);
    }

    private GuiBookGeneric getEggShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());
        int lineIndex = 0;
/* TODO pet shop
        if (shopLevel == 1) {
            lines.get(lineIndex).addWord(Eggs.get(Companion.WOLF, 20), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.PIG, 20), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.SHEEP, 20), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.COW, 20), 1);
        } else if (shopLevel == 3) {
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_ALL_BLACK, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_BLACK, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_BRITISH_SHORTHAIR, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_CALICO, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_JELLIE, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_PERSIAN, 30), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_RAGDOLL, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_RED, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_SIAMESE, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_TABBY, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.CAT_WHITE, 30), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.RABBIT_BLACK, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.RABBIT_BLACK_AND_WHITE, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.RABBIT_BROWN, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.RABBIT_GOLD, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.RABBIT_SALT_AND_PEPPER, 30), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.RABBIT_WHITE, 30), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.CHICKEN, 30), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.CHOCOLATE, 30), 1);
        } else if (shopLevel == 4) {
            lines.get(lineIndex).addWord(Eggs.get(Companion.POLAR_BEAR, 50), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.PANDA, 50), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.TURTLE, 50), 1);
        } else if (shopLevel == 5) {
            lines.get(lineIndex).addWord(Eggs.get(Companion.FOX_RED, 70), 1);
            lines.get(lineIndex).addWord(Eggs.get(Companion.FOX_SNOW, 70), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.ICE_CREAM, 70), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.MINI_DRAGON, 70), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Eggs.get(Companion.VEX, 70), 1);
        }
*/
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

        GuiBookGeneric merchantShop = new GuiBookGeneric("Companion Shop", shopNpc);
        for (GuiPage page : guiPages) {
            merchantShop.addPage(page);
        }
        return merchantShop;
    }

    private GuiBookGeneric getWeaponShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());

        ItemTier tier = ItemTier.COMMON;
        int lineIndex = 0;

        lines.add(new MerchantGuiLine());

        for (WeaponGearType weaponGearType : WeaponGearType.values()) {
            int price = 99999999;

            switch (weaponGearType) {
                case SWORD:
                    price = 3;
                    break;
                case DAGGER:
                    price = 3;
                    break;
                case BATTLE_AXE:
                    price = 3;
                    break;
                case GREAT_SWORD:
                    price = 3;
                    break;
                case WAR_HAMMER:
                    price = 3;
                    break;
                case SPEAR:
                    price = 3;
                    break;
                case BOW:
                    price = 3;
                    break;
                case CROSSBOW:
                    price = 3;
                    break;
                case STAFF:
                    price = 3;
                    break;
                case WAND:
                    price = 3;
                    break;
            }

            ItemStack itemStack = WeaponManager.get(weaponGearType, shopLevel, 0, tier, "", true);

            if (lines.get(lineIndex).isEmpty()) {
                lines.get(lineIndex).addWord(itemStack, price);
            } else {
                lines.add(new MerchantGuiLine());
                lineIndex++;
                lines.get(lineIndex).addWord(itemStack, price);
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
            for (ArmorSlot armorSlot : ArmorSlot.values()) {
                int price = 2; //boots and helmet
                if (armorSlot.equals(ArmorSlot.LEGGINGS)) {
                    price = 3;
                } else if (armorSlot.equals(ArmorSlot.CHESTPLATE)) {
                    price = 4;
                }

                ItemStack itemStack = ArmorManager.get(armorSlot, armorGearType, shopLevel, 0, tier, "", true);

                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(itemStack, price);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
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
            int price = 2; //If any other ShieldGearType exists which is weaker than SHIELD
            if (shieldGearType.equals(ShieldGearType.SHIELD)) {
                price = 3;
            }

            ItemStack itemStack = ShieldManager.get(shieldGearType, shopLevel, 0, tier, "", true);

                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(itemStack, price);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                    lines.get(lineIndex).addWord(itemStack, price);
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
        ItemStack food = OtherItems.getPetFood(shopLevel);
        line1.addWord(food, 1);

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
        int price = 4;

        if (shopLevel == 2) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.STONE);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.STONE);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.STONE);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.STONE);
            price = 6;
        } else if (shopLevel == 3) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.IRON);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.IRON);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.IRON);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.IRON);
            price = 8;
        } else if (shopLevel == 4) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.GOLDEN);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.GOLDEN);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.GOLDEN);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.GOLDEN);
            price = 10;
        } else if (shopLevel == 5) {
            axe = GatheringToolType.AXE.getItemStack(GatheringToolTier.DIAMOND);
            hoe = GatheringToolType.HOE.getItemStack(GatheringToolTier.DIAMOND);
            pickaxe = GatheringToolType.PICKAXE.getItemStack(GatheringToolTier.DIAMOND);
            fishingRod = GatheringToolType.FISHING_ROD.getItemStack(GatheringToolTier.DIAMOND);
            price = 12;
        }

        line1.addWord(axe, price);
        line1.addWord(hoe, price);
        line1.addWord(pickaxe, price);
        line1.addWord(fishingRod, price);

        GuiPage guiPage = new GuiPage();
        guiPage.addLine(line1);

        GuiBookGeneric potion_shop = new GuiBookGeneric("Tool Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }
}
