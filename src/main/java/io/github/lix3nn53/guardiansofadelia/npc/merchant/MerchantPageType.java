package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.GatheringTool;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.TeleportScroll;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantGui;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Armors;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Shields;
import io.github.lix3nn53.guardiansofadelia.Items.list.eggs.Companions;
import io.github.lix3nn53.guardiansofadelia.Items.list.eggs.Mounts;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Companion;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.Mount;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guild.Guild;
import io.github.lix3nn53.guardiansofadelia.guild.GuildManager;
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
    WEAPON,
    ARMOR,
    SHIELD,
    ENCHANT,
    COMPANION,
    MOUNT,
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
            case WEAPON:
                return getWeaponShop(shopLevel, resourceNpc);
            case ARMOR:
                return getArmorShop(shopLevel, resourceNpc);
            case SHIELD:
                return getShieldShop(shopLevel, resourceNpc);
            case ENCHANT:
                return getEnchantGui(player, 0);
            case COMPANION:
                return getCompanionShop(shopLevel, resourceNpc);
            case MOUNT:
                return getMountShop(shopLevel, resourceNpc);
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
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "64 Bronze = " + ChatColor.WHITE + "1 Silver");
            add(ChatColor.WHITE + "64 Silver = " + ChatColor.YELLOW + "1 Gold");
        }});
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

    private GuiGeneric getEnchantGui(Player player, int shopNpc) {
        return new EnchantGui();
    }

    private GuiBookGeneric getCompanionShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());
        int lineIndex = 0;

        if (shopLevel == 1) {
            lines.get(lineIndex).addWord(Companions.get(Companion.WOLF, ItemTier.COMMON, 20, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.PIG, ItemTier.COMMON, 20, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.SHEEP, ItemTier.COMMON, 20, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.COW, ItemTier.COMMON, 20, 800000), 1);
        } else if (shopLevel == 3) {
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_ALL_BLACK, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_BLACK, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_BRITISH_SHORTHAIR, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_CALICO, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_JELLIE, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_PERSIAN, ItemTier.COMMON, 30, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_RAGDOLL, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_RED, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_SIAMESE, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_TABBY, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.CAT_WHITE, ItemTier.COMMON, 30, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.RABBIT_BLACK, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.RABBIT_BLACK_AND_WHITE, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.RABBIT_BROWN, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.RABBIT_GOLD, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.RABBIT_SALT_AND_PEPPER, ItemTier.COMMON, 30, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.RABBIT_WHITE, ItemTier.COMMON, 30, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.CHICKEN, ItemTier.COMMON, 30, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.CHOCOLATE, ItemTier.COMMON, 30, 800000), 1);
        } else if (shopLevel == 4) {
            lines.get(lineIndex).addWord(Companions.get(Companion.POLAR_BEAR, ItemTier.COMMON, 50, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.PANDA, ItemTier.COMMON, 50, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.TURTLE, ItemTier.COMMON, 50, 800000), 1);
        } else if (shopLevel == 5) {
            lines.get(lineIndex).addWord(Companions.get(Companion.FOX_RED, ItemTier.COMMON, 70, 800000), 1);
            lines.get(lineIndex).addWord(Companions.get(Companion.FOX_SNOW, ItemTier.COMMON, 70, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.ICE_CREAM, ItemTier.COMMON, 70, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.MINI_DRAGON, ItemTier.COMMON, 70, 800000), 1);
            lines.add(new MerchantGuiLine());
            lineIndex++;
            lines.get(lineIndex).addWord(Companions.get(Companion.VEX, ItemTier.COMMON, 70, 800000), 1);
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

        GuiBookGeneric weapon_shop = new GuiBookGeneric("Companion Shop", shopNpc);
        for (GuiPage page : guiPages) {
            weapon_shop.addPage(page);
        }
        return weapon_shop;
    }

    private GuiBookGeneric getMountShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());
        int lineIndex = 0;

        lines.get(lineIndex).addWord(Mounts.get(Mount.BLACK, ItemTier.COMMON, 40, 800000), 1);
        lines.get(lineIndex).addWord(Mounts.get(Mount.BROWN, ItemTier.COMMON, 40, 800000), 1);
        lines.get(lineIndex).addWord(Mounts.get(Mount.CHESTNUT, ItemTier.COMMON, 40, 800000), 1);
        lines.get(lineIndex).addWord(Mounts.get(Mount.CREAMY, ItemTier.COMMON, 40, 800000), 1);
        lines.get(lineIndex).addWord(Mounts.get(Mount.DARK_BROWN, ItemTier.COMMON, 40, 800000), 1);
        lines.get(lineIndex).addWord(Mounts.get(Mount.GRAY, ItemTier.COMMON, 40, 800000), 1);
        lines.get(lineIndex).addWord(Mounts.get(Mount.WHITE, ItemTier.COMMON, 40, 800000), 1);

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

        GuiBookGeneric weapon_shop = new GuiBookGeneric("Mount Shop", shopNpc);
        for (GuiPage page : guiPages) {
            weapon_shop.addPage(page);
        }
        return weapon_shop;
    }

    private GuiBookGeneric getWeaponShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());

        ItemTier tier = ItemTier.COMMON;
        int minStatValue = 0;
        int maxStatValue = 0;
        int minNumberOfStats = 0;
        int lineIndex = 0;

        if (shopLevel == 1) {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 1, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 2, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 2);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 3, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 3);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
        } else if (shopLevel == 3) {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 4, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 4);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 5, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 5);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 6, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 6);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
        } else if (shopLevel == 4) {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 7, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 7);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 8, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 8);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
        } else if (shopLevel == 5) {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 9, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 9);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                ItemStack weapon = Weapons.getWeapon(rpgClass, 10, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 10);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
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

        GuiBookGeneric weapon_shop = new GuiBookGeneric("Weapon Shop", shopNpc);
        for (GuiPage page : guiPages) {
            weapon_shop.addPage(page);
        }
        return weapon_shop;
    }

    private GuiBookGeneric getArmorShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();

        ItemTier tier = ItemTier.COMMON;
        int minStatValue = 0;
        int maxStatValue = 0;
        int minNumberOfStats = 0;
        int lineIndex = 0;

        if (shopLevel == 1) {
            lines.add(new MerchantGuiLine());
            for (ArmorType armorType : ArmorType.values()) {
                ItemStack weapon = Armors.getArmor(armorType, RPGClass.NO_CLASS, 1, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                    lines.get(lineIndex).addWord(weapon, 1);
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (ArmorType armorType : ArmorType.values()) {
                ItemStack weapon = Armors.getArmor(armorType, RPGClass.NO_CLASS, 2, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                    lines.get(lineIndex).addWord(weapon, 1);
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                lines.add(new MerchantGuiLine());
                lineIndex++;
                for (ArmorType armorType : ArmorType.values()) {
                    ItemStack weapon = Armors.getArmor(armorType, rpgClass, 1, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                    if (lines.get(lineIndex).isEmpty()) {
                        lines.get(lineIndex).addWord(weapon, 1);
                    } else {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                        lines.get(lineIndex).addWord(weapon, 1);
                    }
                }
            }
        } else if (shopLevel == 3) {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                lines.add(new MerchantGuiLine());
                lineIndex++;
                for (ArmorType armorType : ArmorType.values()) {
                    ItemStack weapon = Armors.getArmor(armorType, rpgClass, 2, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                    if (lines.get(lineIndex).isEmpty()) {
                        lines.get(lineIndex).addWord(weapon, 1);
                    } else {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                        lines.get(lineIndex).addWord(weapon, 1);
                    }
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                lines.add(new MerchantGuiLine());
                lineIndex++;
                for (ArmorType armorType : ArmorType.values()) {
                    ItemStack weapon = Armors.getArmor(armorType, rpgClass, 3, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                    if (lines.get(lineIndex).isEmpty()) {
                        lines.get(lineIndex).addWord(weapon, 1);
                    } else {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                        lines.get(lineIndex).addWord(weapon, 1);
                    }
                }
            }
        } else if (shopLevel == 4) {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                lines.add(new MerchantGuiLine());
                lineIndex++;
                for (ArmorType armorType : ArmorType.values()) {
                    ItemStack weapon = Armors.getArmor(armorType, rpgClass, 4, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                    if (lines.get(lineIndex).isEmpty()) {
                        lines.get(lineIndex).addWord(weapon, 1);
                    } else {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                        lines.get(lineIndex).addWord(weapon, 1);
                    }
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                lines.add(new MerchantGuiLine());
                lineIndex++;
                for (ArmorType armorType : ArmorType.values()) {
                    ItemStack weapon = Armors.getArmor(armorType, rpgClass, 5, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                    if (lines.get(lineIndex).isEmpty()) {
                        lines.get(lineIndex).addWord(weapon, 1);
                    } else {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                        lines.get(lineIndex).addWord(weapon, 1);
                    }
                }
            }
        } else if (shopLevel == 5) {
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                lines.add(new MerchantGuiLine());
                lineIndex++;
                for (ArmorType armorType : ArmorType.values()) {
                    ItemStack weapon = Armors.getArmor(armorType, rpgClass, 6, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                    if (lines.get(lineIndex).isEmpty()) {
                        lines.get(lineIndex).addWord(weapon, 1);
                    } else {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                        lines.get(lineIndex).addWord(weapon, 1);
                    }
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                if (rpgClass.equals(RPGClass.NO_CLASS)) {
                    continue;
                }
                lines.add(new MerchantGuiLine());
                lineIndex++;
                for (ArmorType armorType : ArmorType.values()) {
                    ItemStack weapon = Armors.getArmor(armorType, rpgClass, 7, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                    if (lines.get(lineIndex).isEmpty()) {
                        lines.get(lineIndex).addWord(weapon, 1);
                    } else {
                        lines.add(new MerchantGuiLine());
                        lineIndex++;
                        lines.get(lineIndex).addWord(weapon, 1);
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

        GuiBookGeneric weapon_shop = new GuiBookGeneric("Armor Shop", shopNpc);
        for (GuiPage page : guiPages) {
            weapon_shop.addPage(page);
        }
        return weapon_shop;
    }

    private GuiBookGeneric getShieldShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());

        ItemTier tier = ItemTier.COMMON;
        int minStatValue = 0;
        int maxStatValue = 0;
        int minNumberOfStats = 0;
        int lineIndex = 0;

        if (shopLevel == 1) {
            for (int i = 1; i <= 3; i++) {
                RPGClass rpgClass = RPGClass.KNIGHT;
                ItemStack weapon = Shields.get(rpgClass, i, tier, "", tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (int i = 1; i <= 3; i++) {
                RPGClass rpgClass = RPGClass.PALADIN;
                ItemStack weapon = Shields.get(rpgClass, i, tier, "", tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
        } else if (shopLevel == 3) {
            for (int i = 4; i <= 6; i++) {
                RPGClass rpgClass = RPGClass.KNIGHT;
                ItemStack weapon = Shields.get(rpgClass, i, tier, "", tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (int i = 4; i <= 6; i++) {
                RPGClass rpgClass = RPGClass.PALADIN;
                ItemStack weapon = Shields.get(rpgClass, i, tier, "", tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
        } else if (shopLevel == 4) {
            for (int i = 7; i <= 8; i++) {
                RPGClass rpgClass = RPGClass.KNIGHT;
                ItemStack weapon = Shields.get(rpgClass, i, tier, "", tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (int i = 7; i <= 8; i++) {
                RPGClass rpgClass = RPGClass.PALADIN;
                ItemStack weapon = Shields.get(rpgClass, i, tier, "", tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
        } else if (shopLevel == 5) {
            for (int i = 9; i <= 10; i++) {
                RPGClass rpgClass = RPGClass.KNIGHT;
                ItemStack weapon = Shields.get(rpgClass, i, tier, "", tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            lines.add(new MerchantGuiLine());
            lineIndex++;
            for (int i = 9; i <= 10; i++) {
                RPGClass rpgClass = RPGClass.PALADIN;
                ItemStack weapon = Shields.get(rpgClass, i, tier, "", tier.getBonusMultiplier(), minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
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

        GuiBookGeneric weapon_shop = new GuiBookGeneric("Shield Shop", shopNpc);
        for (GuiPage page : guiPages) {
            weapon_shop.addPage(page);
        }
        return weapon_shop;
    }

    private GuiBookGeneric getPotionShop(int shopLevel, int shopNpc) {
        GuiPage guiPage = new GuiPage();

        MerchantGuiLine line1 = new MerchantGuiLine();
        ItemStack hpPotion1 = Consumable.POTION_INSTANT_HEALTH.getItemStack(1, 3);
        line1.addWord(hpPotion1, 1);
        ItemStack manaPotion1 = Consumable.POTION_INSTANT_MANA.getItemStack(1, 3);
        line1.addWord(manaPotion1, 1);
        guiPage.addLine(line1);

        if (shopLevel >= 2) {
            MerchantGuiLine line = new MerchantGuiLine();
            ItemStack hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(2, 3);
            line.addWord(hpPotion, 1);
            ItemStack manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(2, 3);
            line.addWord(manaPotion, 1);
            guiPage.addLine(line);
            if (shopLevel >= 3) {
                line = new MerchantGuiLine();
                hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(3, 3);
                line.addWord(hpPotion, 1);
                manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(3, 3);
                line.addWord(manaPotion, 1);
                guiPage.addLine(line);
                if (shopLevel >= 4) {
                    line = new MerchantGuiLine();
                    hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(4, 3);
                    line.addWord(hpPotion, 1);
                    manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(4, 3);
                    line.addWord(manaPotion, 1);
                    guiPage.addLine(line);
                    if (shopLevel >= 5) {
                        line = new MerchantGuiLine();
                        hpPotion = Consumable.POTION_INSTANT_HEALTH.getItemStack(5, 3);
                        line.addWord(hpPotion, 1);
                        manaPotion = Consumable.POTION_INSTANT_MANA.getItemStack(5, 3);
                        line.addWord(manaPotion, 1);
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

        line1.addWord(TeleportScroll.ROUMEN.getScroll(1, 1), 1);

        line1.addWord(TeleportScroll.PORT_VELOA.getScroll(1, 5), 1);

        if (shopLevel >= 2) {
            line1.addWord(TeleportScroll.ELDERINE.getScroll(1, 20), 1);
            if (shopLevel >= 3) {
                line1.addWord(TeleportScroll.URUGA.getScroll(1, 40), 1);
                if (shopLevel >= 4) {
                    line1.addWord(TeleportScroll.ALBERSTOL_RUINS.getScroll(1, 60), 1);
                }
            }
        }

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

        ItemStack axe = GatheringTool.WOODEN_AXE.getItemStack();
        ItemStack hoe = GatheringTool.WOODEN_HOE.getItemStack();
        ItemStack pickaxe = GatheringTool.WOODEN_PICKAXE.getItemStack();
        ItemStack fishingRod = GatheringTool.WOODEN_FISHING_ROD.getItemStack();
        int price = 1;

        if (shopLevel == 2) {
            axe = GatheringTool.STONE_AXE.getItemStack();
            hoe = GatheringTool.STONE_HOE.getItemStack();
            pickaxe = GatheringTool.STONE_PICKAXE.getItemStack();
            fishingRod = GatheringTool.STONE_FISHING_ROD.getItemStack();
            price = 2;
        } else if (shopLevel == 3) {
            axe = GatheringTool.IRON_AXE.getItemStack();
            hoe = GatheringTool.IRON_HOE.getItemStack();
            pickaxe = GatheringTool.IRON_PICKAXE.getItemStack();
            fishingRod = GatheringTool.IRON_FISHING_ROD.getItemStack();
            price = 3;
        } else if (shopLevel == 4) {
            axe = GatheringTool.GOLDEN_AXE.getItemStack();
            hoe = GatheringTool.GOLDEN_HOE.getItemStack();
            pickaxe = GatheringTool.GOLDEN_PICKAXE.getItemStack();
            fishingRod = GatheringTool.GOLDEN_FISHING_ROD.getItemStack();
            price = 4;
        } else if (shopLevel == 5) {
            axe = GatheringTool.DIAMOND_AXE.getItemStack();
            hoe = GatheringTool.DIAMOND_HOE.getItemStack();
            pickaxe = GatheringTool.DIAMOND_PICKAXE.getItemStack();
            fishingRod = GatheringTool.DIAMOND_FISHING_ROD.getItemStack();
            price = 5;
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
