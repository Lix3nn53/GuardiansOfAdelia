package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.TeleportScroll;
import io.github.lix3nn53.guardiansofadelia.Items.consumables.PotionType;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantGui;
import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.list.consumables.Potions;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiPage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum MerchantShop {
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
                return getConverGui(resourceNpc);
            case PERSONAL_STORAGE:
                return getPersonalStorageGui(player, resourceNpc);
            case GUILD_STORAGE:
                return getGuildStorageGui(player, resourceNpc);
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

    private GuiGeneric getSellGui(int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Sell Items", shopNpc);
        return guiGeneric;
    }

    private GuiGeneric getConverGui(int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Coin Convert", shopNpc);
        return guiGeneric;
    }

    private GuiGeneric getPersonalStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Personal Storage", shopNpc);
        return guiGeneric;
    }

    private GuiGeneric getGuildStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Guild Storage", shopNpc);
        return guiGeneric;
    }

    private GuiGeneric getBazaarStorageGui(Player player, int shopNpc) {
        GuiGeneric guiGeneric = new GuiGeneric(54, "Bazaar Storage", shopNpc);
        return guiGeneric;
    }

    private GuiGeneric getEnchantGui(Player player, int shopNpc) {
        return new EnchantGui();
    }

    private GuiBookGeneric getCompanionShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());

        if (shopLevel == 1) {

        } else if (shopLevel == 3) {

        } else if (shopLevel == 4) {

        } else if (shopLevel == 5) {

        }

        List<GuiPage> guiPages = new ArrayList<>();
        guiPages.add(new GuiPage());
        int guiPageIndex = 1;

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

    private GuiBookGeneric getMountShop(int shopLevel, int shopNpc) {
        List<MerchantGuiLine> lines = new ArrayList<>();
        lines.add(new MerchantGuiLine());

        if (shopLevel == 1) {

        } else if (shopLevel == 3) {

        } else if (shopLevel == 4) {

        } else if (shopLevel == 5) {

        }

        List<GuiPage> guiPages = new ArrayList<>();
        guiPages.add(new GuiPage());
        int guiPageIndex = 1;

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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 1, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                ItemStack weapon = Weapons.getWeapon(rpgClass, 2, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 2);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 4, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 4);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                ItemStack weapon = Weapons.getWeapon(rpgClass, 5, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 5);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 7, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 7);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 9, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 9);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
        int guiPageIndex = 1;

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
        lines.add(new MerchantGuiLine());

        ItemTier tier = ItemTier.COMMON;
        int minStatValue = 0;
        int maxStatValue = 0;
        int minNumberOfStats = 0;
        int lineIndex = 0;

        if (shopLevel == 1) {
            for (RPGClass rpgClass : RPGClass.values()) {
                ItemStack weapon = Weapons.getWeapon(rpgClass, 1, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                ItemStack weapon = Weapons.getWeapon(rpgClass, 2, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 2);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 4, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 4);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                ItemStack weapon = Weapons.getWeapon(rpgClass, 5, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 5);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 7, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 7);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 9, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 9);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
        int guiPageIndex = 1;

        for (MerchantGuiLine line : lines) {
            if (guiPages.get(guiPageIndex).isEmpty()) {
                guiPages.get(guiPageIndex).addLine(line);
            } else {
                guiPages.add(new GuiPage());
                guiPageIndex++;
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
            for (RPGClass rpgClass : RPGClass.values()) {
                ItemStack weapon = Weapons.getWeapon(rpgClass, 1, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 1);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                ItemStack weapon = Weapons.getWeapon(rpgClass, 2, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 2);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 4, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 4);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
                ItemStack weapon = Weapons.getWeapon(rpgClass, 5, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 5);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 7, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 7);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
                ItemStack weapon = Weapons.getWeapon(rpgClass, 9, tier, "", minStatValue, maxStatValue, minNumberOfStats);
                if (lines.get(lineIndex).isEmpty()) {
                    lines.get(lineIndex).addWord(weapon, 9);
                } else {
                    lines.add(new MerchantGuiLine());
                    lineIndex++;
                }
            }
            for (RPGClass rpgClass : RPGClass.values()) {
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
        int guiPageIndex = 1;

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
        MerchantGuiLine line1 = new MerchantGuiLine();

        ItemStack hpPotion = Potions.getItemStack(PotionType.HEALTH, shopLevel);
        line1.addWord(hpPotion, 1);
        ItemStack manaPotion = Potions.getItemStack(PotionType.MANA, shopLevel);
        line1.addWord(manaPotion, 1);

        GuiPage guiPage = new GuiPage();
        guiPage.addLine(line1);

        GuiBookGeneric potion_shop = new GuiBookGeneric("Potion Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private GuiBookGeneric getTeleportScrollShop(int shopLevel, int shopNpc) {
        MerchantGuiLine line1 = new MerchantGuiLine();

        Town town1 = TownManager.getTown(1);
        TeleportScroll roumen = new TeleportScroll(town1.getLocation(), 1, town1.getName());
        line1.addWord(roumen.getScroll(1), 1);

        Town town2 = TownManager.getTown(2);
        TeleportScroll port = new TeleportScroll(town2.getLocation(), 5, town2.getName());
        line1.addWord(port.getScroll(1), 1);

        if (shopLevel >= 2) {
            Town town3 = TownManager.getTown(3);
            TeleportScroll elderine = new TeleportScroll(town3.getLocation(), 20, town3.getName());
            line1.addWord(elderine.getScroll(1), 1);
        }
        if (shopLevel >= 3) {
            Town town4 = TownManager.getTown(4);
            TeleportScroll uruga = new TeleportScroll(town4.getLocation(), 40, town4.getName());
            line1.addWord(uruga.getScroll(1), 1);
        }
        if (shopLevel >= 4) {
            Town town5 = TownManager.getTown(5);
            TeleportScroll ruins = new TeleportScroll(town5.getLocation(), 60, town5.getName());
            line1.addWord(ruins.getScroll(1), 1);
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

        GuiBookGeneric potion_shop = new GuiBookGeneric("Potion Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }

    private GuiBookGeneric getToolShop(int shopLevel, int shopNpc) {
        MerchantGuiLine line1 = new MerchantGuiLine();

        ItemStack fishingRod = OtherItems.getFishingRod(30);
        line1.addWord(fishingRod, 1);

        GuiPage guiPage = new GuiPage();
        guiPage.addLine(line1);

        GuiBookGeneric potion_shop = new GuiBookGeneric("Potion Shop", shopNpc);
        potion_shop.addPage(guiPage);
        return potion_shop;
    }
}
